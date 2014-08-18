/**
 */
package activity;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Split</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link activity.Split#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 * </p>
 *
 * @see activity.ActivityPackage#getSplit()
 * @model
 * @generated
 */
public interface Split extends NodesWithoutJoin {
	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link activity.SplitArrow}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see activity.ActivityPackage#getSplit_Outgoing()
	 * @model lower="2"
	 * @generated
	 */
	EList<SplitArrow> getOutgoing();

} // Split
