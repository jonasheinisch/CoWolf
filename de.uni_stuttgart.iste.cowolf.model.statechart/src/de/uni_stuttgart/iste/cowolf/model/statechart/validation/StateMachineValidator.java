/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.statechart.validation;

import de.uni_stuttgart.iste.cowolf.model.statechart.State;
import de.uni_stuttgart.iste.cowolf.model.statechart.Transition;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.statechart.StateMachine}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface StateMachineValidator {
	boolean validate();

	boolean validateTransitions(EList<Transition> value);
	boolean validateTop(EList<State> value);
}