/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.dtmc.validation;

import de.uni_stuttgart.iste.cowolf.model.dtmc.State;
import de.uni_stuttgart.iste.cowolf.model.dtmc.Transition;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.dtmc.DTMC}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DTMCValidator {
	boolean validate();

	boolean validateName(String value);
	boolean validateStates(EList<State> value);
	boolean validateInitialState(State value);
	boolean validateTransitions(EList<Transition> value);
}