package de.uni_stuttgart.iste.cowolf.core.ModelAssociationManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This class holds the connection between a project and the known associations
 * in this project
 * 
 * @author Verena Käfer
 *
 */
public class AssociationProject {

	private IProject iProject;
	ArrayList<Association> associations = new ArrayList<Association>();

	public AssociationProject(IProject iProject) {
		this.iProject = iProject;
	}

	public IProject getIProject() {
		return iProject;

	}

	public void setIProject(IProject project) {
		this.iProject = project;
	}

	/**
	 * Creates an association between these two resources
	 * 
	 * @param source
	 * @param target
	 * @return false if association already exists
	 */
	public boolean addAssociation(Resource source, Resource target) {
		for (Association association : associations) {
			if (association.getSource().getURI().equals(source.getURI())
					&& association.getTarget().getURI().equals(target.getURI())) {
				// association exists already
				return false;
			}
		}
		Association newAssociation = new Association(source, target, iProject);
		associations.add(newAssociation);
		return true;

	}

	/**
	 * Deletes an association of the two resources
	 * 
	 * @param source
	 * @param target
	 * @return True if a match of these two resources was found and deleted.
	 *         False else
	 */
	public boolean removeAssociation(Resource source, Resource target) {
		boolean foundMatch = false;
		for (Association association : associations) {
			if (association.getSource().getURI().equals(source.getURI())
					&& association.getTarget().getURI().equals(target.getURI())) {
				associations.remove(association);
				foundMatch = true;
				break;
			}
		}

		return foundMatch;
	}

	/**
	 * @param source
	 * @return all with several steps reachable associations
	 */
	public List<Resource> getReachableAssociations(Resource source) {

		ArrayList<Association> toDelete = new ArrayList<Association>();

		List<Resource> foundMatches = new ArrayList<Resource>();

		// all first level reachable resources
		for (Association association : associations) {
			if (association.getSource().getURI().equals(source.getURI())) {
				// check if target still exists
				String fileString = association.getTarget().getURI()
						.toFileString();
				File f = new File(fileString);

				if (f.exists()) {

					foundMatches.add(association.getTarget());
					List<Resource> newMatches;

					// find reachable resources for the found matches
					newMatches = getReachableAssociations(association
							.getTarget());

					// and add them to the return list
					for (Resource ressource : newMatches) {
						foundMatches.add(ressource);
					}
				} else {
					toDelete.add(association);
				}
			}
		}

		for (Association associationToDelete : toDelete) {
			associations.remove(associationToDelete);
		}

		return foundMatches;
	}

	/**
	 * @param source
	 * @return the existing targets for the given source
	 */
	public List<Resource> getDirectModelAssociations(Resource source) {
		List<Resource> foundMatches = new ArrayList<Resource>();
		ArrayList<Association> toDelete = new ArrayList<Association>();

		for (Association association : associations) {
			if (association.getSource().getURI().equals(source.getURI())) {

				// check if target still exists
				String fileString = association.getTarget().getURI()
						.toFileString();
				System.out.println(fileString);
				File f = new File(fileString);

				if (f.exists()) {

					foundMatches.add(association.getTarget());
				}
			} else {
				toDelete.add(association);
			}
		}

		for (Association associationToDelete : toDelete) {
			associations.remove(associationToDelete);
		}
		return foundMatches;
	}

	public ArrayList<Association> getAssociations() {
		return associations;
	}

}
