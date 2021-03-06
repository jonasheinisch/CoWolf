package de.uni_stuttgart.iste.cowolf.ui.navigator.handlers;

import java.util.HashMap;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni_stuttgart.iste.cowolf.model.AbstractModelManager;
import de.uni_stuttgart.iste.cowolf.model.AbstractQoSModelManager;
import de.uni_stuttgart.iste.cowolf.model.ModelRegistry;
import de.uni_stuttgart.iste.cowolf.ui.model.analyze.AbstractQoSAnalyzeWizard;
import de.uni_stuttgart.iste.cowolf.ui.model.analyze.AnalyzeWizardHandler;
import de.uni_stuttgart.iste.cowolf.ui.model.analyze.FileOpenAnalysisListener;

public class Analyze implements IHandler {

	
	private final static Logger LOGGER = LoggerFactory.getLogger(Analyze.class);
	
	@Override
	public void addHandlerListener(final IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

    	if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
    		return null;
    	}
		
    	LOGGER.debug("Doing Analyze");
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();

		Object selectedElement = selection.getFirstElement();

		if (selectedElement != null && selectedElement instanceof IFile) {
			IFile iFile = (IFile) selectedElement;
			ResourceSet resSet = new ResourceSetImpl();
			Resource resource;
			URI uri = URI.createPlatformResourceURI(iFile.getFullPath().toString(), true);
			resource = resSet.getResource(uri, true);
			AbstractModelManager modelManager = ModelRegistry.getInstance().getModelManager(resource);

			if (modelManager != null && modelManager instanceof AbstractQoSModelManager) {
				AbstractQoSModelManager qosModelManager = (AbstractQoSModelManager) modelManager;
				HashMap<String, Object> properties = new HashMap<String, Object>();
				AbstractQoSAnalyzeWizard wizard = AnalyzeWizardHandler.getInstance().getQosAnalyzeWizard((AbstractQoSModelManager) modelManager);
	
				if (wizard != null) {
					// Call UI to define Properties
					wizard.initialize(qosModelManager, resource, properties);
					if (!wizard.checkConditions()) {
						return null;
					}
					WizardDialog wizardDialog = new WizardDialog(window.getShell(), wizard);
					wizardDialog.setBlockOnOpen(true);
					FileOpenAnalysisListener fileOpenListener = new FileOpenAnalysisListener();
					if (wizardDialog.open() == Window.OK) {
						LOGGER.debug("Ok pressed");
						qosModelManager.analyze(resource, properties, fileOpenListener);
					} else {
						LOGGER.debug("Cancel pressed");
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(final IHandlerListener handlerListener) {
		
	}

}
