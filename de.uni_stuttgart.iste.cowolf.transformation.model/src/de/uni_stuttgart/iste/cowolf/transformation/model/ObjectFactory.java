//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.15 at 11:35:54 AM CEST 
//


package de.uni_stuttgart.iste.cowolf.transformation.model;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.wolf.co.iste.uni_stuttgart.transformationmapping package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.wolf.co.iste.uni_stuttgart.transformationmapping
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Mappings }
     * 
     */
    public Mappings createMappings() {
        return new Mappings();
    }

    /**
     * Create an instance of {@link Mapping }
     * 
     */
    public Mapping createMapping() {
        return new Mapping();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link Params }
     * 
     */
    public Params createParams() {
        return new Params();
    }

    /**
     * Create an instance of {@link Param }
     * 
     */
    public Param createParam() {
        return new Param();
    }

}
