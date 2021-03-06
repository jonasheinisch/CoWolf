/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.LqnCore.validation;


/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.LqnCore.HistogramBinType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface HistogramBinTypeValidator {
	boolean validate();

	boolean validateBegin(Object value);
	boolean validateConf95(Object value);
	boolean validateConf99(Object value);
	boolean validateEnd(Object value);
	boolean validateProb(Object value);
}
