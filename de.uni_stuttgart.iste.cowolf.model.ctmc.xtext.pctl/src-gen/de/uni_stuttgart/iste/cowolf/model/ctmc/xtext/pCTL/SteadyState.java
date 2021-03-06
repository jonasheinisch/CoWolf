/**
 */
package de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Steady State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.SteadyState#getBound <em>Bound</em>}</li>
 *   <li>{@link de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.SteadyState#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.PCTLPackage#getSteadyState()
 * @model
 * @generated
 */
public interface SteadyState extends Rule
{
  /**
   * Returns the value of the '<em><b>Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bound</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bound</em>' containment reference.
   * @see #setBound(CompareProbability)
   * @see de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.PCTLPackage#getSteadyState_Bound()
   * @model containment="true"
   * @generated
   */
  CompareProbability getBound();

  /**
   * Sets the value of the '{@link de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.SteadyState#getBound <em>Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bound</em>' containment reference.
   * @see #getBound()
   * @generated
   */
  void setBound(CompareProbability value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(StateFormula)
   * @see de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.PCTLPackage#getSteadyState_Value()
   * @model containment="true"
   * @generated
   */
  StateFormula getValue();

  /**
   * Sets the value of the '{@link de.uni_stuttgart.iste.cowolf.model.ctmc.xtext.pCTL.SteadyState#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(StateFormula value);

} // SteadyState
