/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.LqnCore.validation;

import de.uni_stuttgart.iste.cowolf.model.LqnCore.OutputResultType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.SchedulingType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.TaskType;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.LqnCore.ProcessorType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ProcessorTypeValidator {
	boolean validate();

	boolean validateResultProcessor(EList<OutputResultType> value);
	boolean validateTask(EList<TaskType> value);
	boolean validateMultiplicity(BigInteger value);
	boolean validateName(String value);
	boolean validateQuantum(Object value);
	boolean validateReplication(BigInteger value);
	boolean validateScheduling(SchedulingType value);
	boolean validateSpeedFactor(Object value);
}
