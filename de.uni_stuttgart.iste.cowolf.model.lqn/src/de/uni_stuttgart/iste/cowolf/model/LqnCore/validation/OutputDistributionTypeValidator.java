/**
 *
 * $Id$
 */
package de.uni_stuttgart.iste.cowolf.model.LqnCore.validation;

import de.uni_stuttgart.iste.cowolf.model.LqnCore.AxisType;
import de.uni_stuttgart.iste.cowolf.model.LqnCore.HistogramBinType;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.uni_stuttgart.iste.cowolf.model.LqnCore.OutputDistributionType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface OutputDistributionTypeValidator {
	boolean validate();

	boolean validateUnderflowBin(HistogramBinType value);
	boolean validateHistogramBin(EList<HistogramBinType> value);
	boolean validateOverflowBin(HistogramBinType value);
	boolean validateBinSize(Object value);
	boolean validateKurtosis(Object value);
	boolean validateMax(Object value);
	boolean validateMean(Object value);
	boolean validateMidPoint(Object value);
	boolean validateMin(Object value);
	boolean validateNumberBins(BigInteger value);
	boolean validateSkew(Object value);
	boolean validateStdDev(Object value);
	boolean validateXSamples(AxisType value);
}
