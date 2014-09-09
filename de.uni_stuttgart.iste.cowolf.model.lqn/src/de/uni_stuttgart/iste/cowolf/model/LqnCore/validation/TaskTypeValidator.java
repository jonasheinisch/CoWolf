/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.LqnCore.validation;

import de.uni_stuttgart.iste.cowolf.model.LqnCore.EntryType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.OutputResultType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.ServiceType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.TaskActivityGraph;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.TaskOptionType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.TaskSchedulingType;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.LqnCore.TaskType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TaskTypeValidator {
	boolean validate();

	boolean validateResultTask(EList<OutputResultType> value);
	boolean validateEntry(EList<EntryType> value);
	boolean validateService(EList<ServiceType> value);
	boolean validateTaskActivities(TaskActivityGraph value);
	boolean validateActivityGraph(TaskOptionType value);
	boolean validateMultiplicity(BigInteger value);
	boolean validateName(String value);
	boolean validatePriority(BigInteger value);
	boolean validateQueueLength(BigInteger value);
	boolean validateReplication(BigInteger value);
	boolean validateScheduling(TaskSchedulingType value);
	boolean validateThinkTime(Object value);
}