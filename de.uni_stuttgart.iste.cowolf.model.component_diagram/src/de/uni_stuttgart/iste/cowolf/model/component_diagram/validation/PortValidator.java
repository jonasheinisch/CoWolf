/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.component_diagram.validation;

import de.uni_stuttgart.iste.cowolf.model.component_diagram.Component;
import de.uni_stuttgart.iste.cowolf.model.component_diagram.Interface;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.component_diagram.Port}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface PortValidator {
	boolean validate();

	boolean validateProtocol(String value);
	boolean validateIsBehavior(boolean value);
	boolean validateIsService(boolean value);
	boolean validateRequiredInterfaces(EList<Interface> value);
	boolean validateProvidedInterfaces(EList<Interface> value);
	boolean validatePortHost(Component value);
}