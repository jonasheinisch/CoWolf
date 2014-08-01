package de.uni_stuttgart.iste.cowolf.model.dtmc;

import de.uni_stuttgart.iste.cowolf.model.IQoSModel;
import de.uni_stuttgart.iste.cowolf.model.ModelTypeInfo;

public class DTMCModel implements IQoSModel {

	private final ModelTypeInfo info;
	
	public DTMCModel() {
		// TODO Auto-generated constructor stub
		info = new ModelTypeInfo("DTMC");
	}

	@Override
	public ModelTypeInfo getModelTypeInfo() {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public void certificate() {
		// TODO Auto-generated method stub

	}

}