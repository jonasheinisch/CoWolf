package de.uni_stuttgart.iste.cowolf.ui.transformation.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.Wizard;

public class TransformationWizard extends Wizard {

	/**
	 * First model to use for evolution.
	 */
	private IFile modelA;
	/**
	 * Second model to use for evolution.
	 */
	private IFile modelB;
	/**
	 * Wizard page displaying contents of wizard.
	 */
	private TransformationWizardPage page;
	/**
	 * Class variable to determine whether first element is selected.
	 */
	private boolean isFirstElementSelected = true;

	/**
	 * Constructor setting both models.
	 *
	 * @param modelA
	 * @param modelB
	 */
	public TransformationWizard(IFile modelA, IFile modelB) {
		this.setWindowTitle("Model Evolution Wizard");
		this.modelA = modelA;
		this.modelB = modelB;

	}

	@Override
	public void addPages() {
		this.page = new TransformationWizardPage(this);
		this.addPage(this.page);
	}

	@Override
	public boolean performFinish() {
		this.isFirstElementSelected = this.page.isFirstModelSelected();
		return true;
	}

	@Override
	public boolean canFinish() {
		return super.canFinish();
	}

	/**
	 *
	 * @return true if first element is selected.
	 */
	public boolean isFirstModelSelected() {
		return this.isFirstElementSelected;
	}

	/**
	 * @return the modelA
	 */
	public IFile getModelA() {
		return modelA;
	}

	/**
	 * @param modelA
	 *            the modelA to set
	 */
	public void setModelA(IFile modelA) {
		this.modelA = modelA;
	}

	/**
	 * @return the modelB
	 */
	public IFile getModelB() {
		return modelB;
	}

	/**
	 * @param modelB
	 *            the modelB to set
	 */
	public void setModelB(IFile modelB) {
		this.modelB = modelB;
	}

}
