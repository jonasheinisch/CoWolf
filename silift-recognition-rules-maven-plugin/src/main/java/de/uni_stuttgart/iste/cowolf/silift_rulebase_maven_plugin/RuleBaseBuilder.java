package de.uni_stuttgart.iste.cowolf.silift_rulebase_maven_plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.difference.rulebase.extension.AbstractProjectRuleBase;
import org.sidiff.difference.rulebase.nature.RuleBaseProjectNature;
import org.sidiff.difference.rulebase.wrapper.RuleBaseWrapper;
import org.sidiff.difference.rulebase.wrapper.util.Edit2RecognitionException;
import org.sidiff.editrule.validation.EditRuleValidation;
import org.sidiff.editrule.validation.EditRuleValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Builds the SiLift rulebase and generates the recognition rules that are
 * needed for the comparison of two model versions.
 * 
 * @author Rene Trefft
 */
@Mojo(name = "build", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class RuleBaseBuilder extends AbstractMojo {

	private RuleBaseWrapper ruleBaseWrapper = null;

	private final String ECLIPSE_PROJECT_DESCRIPTION_FILE_NAME = ".project";

	/**
	 * The project itself. This parameter is set by Maven.
	 */
	@Parameter(property = "project", readonly = true, required = true)
	private MavenProject project;

	/**
	 * @param projectRoot
	 * @return
	 * @throws MojoExecutionException
	 */
	private boolean isRulebaseProject(File projectRoot)
			throws MojoExecutionException {

		getLog().debug(
				"Checking if artifact \"" + project.getArtifactId()
						+ "\" is a Rulebase project...");

		File projectDescriptionFile = new File(projectRoot,
				ECLIPSE_PROJECT_DESCRIPTION_FILE_NAME);

		if (projectDescriptionFile.isFile()) {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			try {

				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(projectDescriptionFile);

				doc.getDocumentElement().normalize();

				NodeList naturesList = doc.getElementsByTagName("natures");

				Node naturesNode = naturesList.item(0);

				if (naturesNode != null
						&& naturesNode.getNodeType() == Node.ELEMENT_NODE) {

					Element naturesElement = (Element) naturesNode;
					NodeList natureList = naturesElement
							.getElementsByTagName("nature");

					for (int s = 0; s < natureList.getLength(); s++) {

						Node natureNode = natureList.item(s);
						NodeList natureChildNodes = natureNode.getChildNodes();

						if (RuleBaseProjectNature.NATURE_ID
								.equals(natureChildNodes.item(0).getNodeValue())) {
							getLog().info(
									"Artifact \"" + project.getArtifactId()
											+ "\" is a Rulebase project.");
							return true;
						}

					}

				}

				getLog().debug(
						"Rulebase nature \""
								+ RuleBaseProjectNature.NATURE_ID
								+ "\" does not exist in project description file \""
								+ ECLIPSE_PROJECT_DESCRIPTION_FILE_NAME + "\".");

			} catch (ParserConfigurationException | SAXException | IOException exc) {

				throw new MojoExecutionException(exc.getLocalizedMessage(), exc);

			}

		} else {

			getLog().debug(
					"Artifact \""
							+ project.getArtifactId()
							+ "\" is not a Eclipse project, because project description file \""
							+ ECLIPSE_PROJECT_DESCRIPTION_FILE_NAME
							+ "\" is missing.");

		}

		getLog().info(
				"Artifact \"" + project.getArtifactId()
						+ "\" is not a Rulebase project.");

		return false;

	}


	/**
	 * 
	 */
	private void registerHenshinEcoreModel() {

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"henshin", new HenshinResourceFactory());
		
	};

	/**
	 * @param editRule
	 * @return
	 * @throws MojoExecutionException
	 */
	private boolean validateEditRule(File editRule)
			throws MojoExecutionException {

		getLog().debug("Validating editrule \"" + editRule.getPath() + "\"...");

		HenshinResourceSet resourceSet = new HenshinResourceSet();
		Module editModule = resourceSet.getModule(editRule.getPath());

		List<EditRuleValidation> validations = new ArrayList<EditRuleValidation>();
		validations = EditRuleValidator
				.calculateEditRuleValidations(editModule);

		boolean noValidationErrors = true;

		for (EditRuleValidation validation : validations) {

			switch (validation.severity) {

			case Diagnostic.ERROR:
				getLog().error(
						validation.infoMessage + " (Type: "
								+ validation.validationType + ")");
				noValidationErrors = false;
			case Diagnostic.WARNING:
				getLog().warn(
						validation.infoMessage + " (Type: "
								+ validation.validationType + ")");
			case Diagnostic.INFO:
				getLog().info(
						validation.infoMessage + " (Type: "
								+ validation.validationType + ")");
			}

		}

		getLog().debug(
				"Validating editrule \"" + editRule.getPath() + "\" completed.");

		return noValidationErrors;

	}

	/**
	 * @param editRules
	 * @throws MojoExecutionException
	 */
	private void validateAndGenerateRecognitionRules(List<File> editRules)
			throws MojoExecutionException {

		getLog().info(
				"Validating edit rules and generating recognition rules of them...");

		for (File editRule : editRules) {

			boolean noValidationErrors = validateEditRule(editRule);

			if (noValidationErrors) {

				try {
					generateRecognitionRule(editRule);
				} catch (Edit2RecognitionException exc) {
					throw new MojoExecutionException(exc.getLocalizedMessage(),
							exc);
				}

			} else {
				getLog().error(
						"Edit rule \""
								+ editRule.getPath()
								+ "\" is not valid, so no recognition rule will be generated of them. Skip to next edit rule.");
			}

		}

		getLog().info(
				"Validating edit rules and generating recognition rules of them completed.");

	}

	/**
	 * 
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		File projectRoot = project.getBasedir();

		if (!isRulebaseProject(projectRoot)) {
			return;
		}

		File editRulesDir = new File(projectRoot,
				AbstractProjectRuleBase.SOURCE_FOLDER);

		if (!editRulesDir.isDirectory()) {
			getLog().warn(
					"Artifact "
							+ project.getArtifactId()
							+ " is a Rulebase project, but has no editrules directory.");
			return;
		}

		List<File> editRules = new ArrayList<File>();
		listHenshinFilesRecursively(editRulesDir, editRules);

		if (editRules.isEmpty()) {
			getLog().warn(
					"Artifact "
							+ project.getArtifactId()
							+ " is a Rulebase project, but has no rules in the editrules directory.");
			return;
		}

		registerHenshinEcoreModel();

		validateAndGenerateRecognitionRules(editRules);

		try {
			buildRuleBase();
		} catch (IOException exc) {
			throw new MojoExecutionException(exc.getLocalizedMessage(), exc);
		}

	}

	/**
	 * @throws IOException
	 */
	private void buildRuleBase() throws IOException {
		getLog().info(
				"Building rulebase file \"" + project.getBasedir() + "\"...");
		getRuleBaseWrapper().saveRuleBase();
		getLog().info(
				"Building rulebase file \"" + project.getBasedir()
						+ "\" completed.");
	}

	/**
	 * @return
	 */
	private RuleBaseWrapper getRuleBaseWrapper() {

		if (ruleBaseWrapper == null) {

			URI ruleBaseURI = URI.createFileURI(project.getBasedir()
					+ File.separator + AbstractProjectRuleBase.RULEBASE_FILE);
			URI editRulesDir = URI.createFileURI(project.getBasedir()
					+ File.separator + AbstractProjectRuleBase.SOURCE_FOLDER);
			URI recognitionRulesDir = URI.createFileURI(project.getBasedir()
					+ File.separator + AbstractProjectRuleBase.BUILD_FOLDER);

			ruleBaseWrapper = new RuleBaseWrapper(ruleBaseURI,
					recognitionRulesDir, editRulesDir, false);

			ruleBaseWrapper.setName(chooseRuleBaseName());

		}

		return ruleBaseWrapper;

	}

	private void generateRecognitionRule(File editRule)
			throws Edit2RecognitionException {
		getLog().debug(
				"Generating recognition rule of edit rule \""
						+ editRule.getPath() + "\"...");

		getRuleBaseWrapper().generateItemFromFile(
				URI.createFileURI(editRule.getPath()));
	}

	/**
	 * @return
	 */
	private String chooseRuleBaseName() {

		File bundleManifestFile = new File(project.getBasedir()
				+ File.separator + "META-INF" + File.separator + "MANIFEST.MF");

		if (bundleManifestFile.isFile()) {

			BufferedReader br = null;

			try {

				br = new BufferedReader(new FileReader(bundleManifestFile));

				StringBuilder bundleManifestContent = new StringBuilder();

				String currentLine;

				while ((currentLine = br.readLine()) != null) {
					System.out.println("+++++++ " + currentLine);
					bundleManifestContent.append(currentLine);
				}

				String pattern = ".*Bundle-Name:\\s*([(\\w|/[^\\S\\n]/)]*);?.*";
				Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL)
						.matcher(bundleManifestContent);
				if (matcher.matches()) {
					String bundleName = matcher.group(1);
					getLog().debug(
							"Bundle name \"" + bundleName
									+ "\" used as name of rulebase.");
					return matcher.group(1);
				} else {
					getLog().debug("Bundle name not defined in manifest.");
				}

			} catch (IOException exc) {
				getLog().error(exc);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException exc) {
						getLog().error(exc);
					}
				}

			}

		}

		getLog().warn(
				"Retrieving bundle name from manifest not possible. Try to use Maven artifact name.");

		String artifactName = project.getName();

		if (artifactName != null) {
			getLog().debug(
					"Maven artifact name \"" + artifactName
							+ "\" used as name of rulebase.");
			return artifactName;
		}

		getLog().warn(
				"Maven artifact name is not defined. Using an empty string as rulebase name.");

		return "";

	}

	/**
	 * @param dir
	 * @param henshinFiles
	 */
	private void listHenshinFilesRecursively(File dir, List<File> henshinFiles) {

		File[] files = dir.listFiles();

		for (File file : files) {

			if (file.isDirectory())
				listHenshinFilesRecursively(file, henshinFiles);
			else if (file.getName().toLowerCase().endsWith(".henshin")) {
				henshinFiles.add(file);
			}
		}

	}

}
