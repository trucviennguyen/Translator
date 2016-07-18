/*
 * java
 *
 * Translator -- a translator from facebook info to ontology
 * Copyright (c) 2012 Truc-Vien T. Nguyen. All Rights Reserved.
 *
 * Translator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Translator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, bug reports, fixes, contact:
 *    Truc-Vien T. Nguyen
 *    trucvien.nguyen@gmail.com
 *    http://sites.google.com/site/trucviennguyen/
 *
 */

package util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import processing.DLQueryEngine;
import processing.DLQueryPrinter;
import static util.Constants.defaultPrefix;
import static util.Constants.dlQueryPrinter;
import static util.Constants.factory;
import static util.Constants.fileSeparator;
import static util.Constants.hasID;
import static util.Constants.lengthXmlBase;
import static util.Constants.manager;
import static util.Constants.ontology;
import static util.Constants.ontologyCoreFilename;
import static util.Constants.ontologyFilename;
import static util.Constants.ontologyIRI;
import static util.Constants.outputFilename;
import static util.Constants.owlxmlFormat;
import static util.Constants.pm;
import static util.Constants.reasoner;
import static util.Constants.reasonerFactory;
import static util.Constants.shortFormProvider;
import static util.Constants.LOG;
import static util.Constants.hasText;
import static util.Constants.xmlBase;

/**
 * This class provides the top-level API and command-line interface 
 * <p>
 * See the package documentation for more details and examples of use.
 * See the main method documentation for details of invoking the extractor.
 * <p>
 * Note that the composite kernel integrated with dependency parse requires
 * a fair amount of memory and time.  Try -Xmx1024m.
 *
 * @author Truc-Vien T. Nguyen
 */

public class OntologyUtils {

    /**
     * 
     */
    public static void instantiateOntology() {

        /*
         * In order to get access to objects that represent entities we need a
         * data factory.
         */
        manager = OWLManager.createOWLOntologyManager();
        // We can get a reference to a data factory from an OWLOntologyManager.
        factory = manager.getOWLDataFactory();
        pm = new DefaultPrefixManager(defaultPrefix);
        /* The second is to use a prefix manager and specify abbreviated IRIs.
         * This is useful for creating lots of entities with the same prefix
         * IRIs. First create our prefix manager and specify that the default
         * prefix IRI (bound to the empty prefix name) is
         * http://www.semanticweb.org/owlapi/ontologies/ontology#
         */
        // pm = new DefaultPrefixManager("http://www.semanticweb.org/tvnguyen/ontologies/WebDataNet#");

        ontologyFilename = ontologyCoreFilename;
        File ontFile = new File(ontologyFilename);
        ontologyIRI = IRI.create(ontFile);

        try {
            /* Load an example ontology. In this case, we'll just load the pizza
             * ontology.
             */
            ontology = manager.loadOntologyFromOntologyDocument(ontologyIRI);
            System.out.println("Loaded ontology: " + ontology.getOntologyID());
            
            xmlBase = ontology.getOntologyID().getOntologyIRI().toString() + xmlBase;
            lengthXmlBase = xmlBase.length();

            /* By default ontologies are saved in the format from which they were
             * loaded. In this case the ontology was loaded from an rdf/xml file We
             * can get information about the format of an ontology from its manager
             */
            OWLOntologyFormat format = manager.getOntologyFormat(ontology);

            // We can save the ontology in a different format Lets save the ontology
            // in owl/xml format
            owlxmlFormat = new OWLXMLOntologyFormat();

            /* Some ontology formats support prefix names and prefix IRIs. In our
             * case we loaded the pizza ontology from an rdf/xml format, which
             * supports prefixes. When we save the ontology in the new format we
             * will copy the prefixes over so that we have nicely abbreviated IRIs
             * in the new ontology document
             */
            if (format.isPrefixOWLOntologyFormat()) {
                owlxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat());
            }

            // We need a reasoner to do our query answering
            reasoner = createReasoner(ontology);
            // Entities are named using IRIs. These are usually too long for use
            // in user interfaces. To solve this
            // problem, and so a query can be written using short class,
            // property, individual names we use a short form
            // provider. In this case, we'll just use a simple short form
            // provider that generates short froms from IRI
            // fragments.
            shortFormProvider = new SimpleShortFormProvider();
            // Create the DLQueryPrinter helper class. This will manage the
            // parsing of input and printing of results
            dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(
                    reasoner, shortFormProvider), shortFormProvider);
            hasID = factory.getOWLObjectProperty(":hasID", pm);
            hasText = factory.getOWLDataProperty(":hasText", pm);
            // Enter the query loop. A user is expected to enter class
            // expression on the command line.
            // doQueryLoop(dlQueryPrinter, clas_query);
            // doQuery(dlQueryPrinter, "Person");
            // dlQueryPrinter.getMaxIDIndividuals(upperClass);
            dlQueryPrinter.getMaxIDIndividuals("Entity");
            dlQueryPrinter.getMaxIDIndividuals("LexicalResource");
            /*String id = "100004224780683";
            String query = "Person and hasID value \"" + id + "\"";
            dlQueryPrinter.getIndividualOfDataProperty(query);
            dlQueryPrinter.getIndividualOfDataProperty("hasID", id);*/
        }
        catch (OWLOntologyCreationException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * 
     */
    public static OWLReasoner createReasoner(OWLOntology rootOntology) {
        /* We need to create an instance of OWLReasoner. An OWLReasoner provides
         * the basic query functionality that we need, for example the ability
         * obtain the subclasses of a class etc. To do this we use a reasoner
         * factory.
         * Create a reasoner factory.
         */
        // OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        reasonerFactory = new Reasoner.ReasonerFactory();
        return reasonerFactory.createNonBufferingReasoner(ontology);
        // return reasonerFactory.createReasoner(rootOntology);
    }

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
/**
 * Primitives to add
 * and save to an ontology
 * 
 */
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    
    /**
     * add a class to the ontology
     */
    public static void addClass(String className) {
        /* First we create
         * an IRI object
         */
        IRI iri = IRI.create(xmlBase + className);
        // Now we create the class
        OWLClass cls = factory.getOWLClass(iri);
        /* We can add a declaration axiom to the ontology, that essentially adds
         * the class to the signature of our ontology.
         * 
         */
        OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(cls);
        manager.addAxiom(ontology, declarationAxiom);
        // reasoner.flush();

    }

    /**
     * add a relationship childClass is subClass of parentClass
     * in condition that both classes exist
     */
    public static void addSubClass(String childClass, String parentClass) {
        /* Get hold of references to class A and class B. Note that the ontology
         * does not contain class A or classB, we simply get references to
         * objects from a data factory that represent class A and class B
         */
        OWLClass clsA = factory.getOWLClass(IRI.create(xmlBase + childClass));
        OWLClass clsB = factory.getOWLClass(IRI.create(xmlBase + parentClass));
        // Now create the axiom
        OWLAxiom axiom = factory.getOWLSubClassOfAxiom(clsA, clsB);
        manager.addAxiom(ontology, axiom);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static void addObjectProperty(String property, String domain, String range) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(IRI.create(xmlBase + property));

        OWLClass clsDomain = factory.getOWLClass(IRI.create(xmlBase + domain)),
                 clsRange = factory.getOWLClass(IRI.create(xmlBase + range));
        // Now create the axiom
        OWLAxiom domainAxiom = factory.getOWLObjectPropertyDomainAxiom(objectProperty, clsDomain),
                 rangeAxiom = factory.getOWLObjectPropertyRangeAxiom(objectProperty, clsRange);
        // We now use the manager to apply the change
        manager.addAxiom(ontology, domainAxiom);
        manager.addAxiom(ontology, rangeAxiom);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static void addDataProperty(String property, String domain, String range) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLDataProperty dataProperty = factory.getOWLDataProperty(IRI.create(xmlBase + property));

        /* We can add a declaration axiom to the ontology, that essentially adds
         * the class to the signature of our ontology.
         * 
         */
        OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(dataProperty);
        manager.addAxiom(ontology, declarationAxiom);

        OWLClass clsDomain = factory.getOWLClass(IRI.create(xmlBase + domain));
        OWLDatatype datatype = null;

        if (range.equals("string"))
            datatype = factory.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());

        // Now create the axiom
        OWLDataPropertyDomainAxiom domainAxiom = factory.getOWLDataPropertyDomainAxiom(dataProperty, clsDomain);
        OWLDataPropertyRangeAxiom rangeAxiom = factory.getOWLDataPropertyRangeAxiom(dataProperty, datatype);

        // We now use the manager to apply the change
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        manager.addAxiom(ontology, domainAxiom);
        manager.addAxiom(ontology, rangeAxiom);
        // reasoner.flush();

    }
    

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
/**
 * Primitives to add knowledge
 * and save to an ontology/knowledge base
 * 
 */
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /**
     * add an individual
     */
    public static void addIndividual(String className, String individualName) {
        /* First we create
         * an IRI object
         */
        IRI iri = IRI.create(xmlBase + individualName);
        // Now we create the class
        OWLNamedIndividual indv = factory.getOWLNamedIndividual(iri);
        /* We can add a declaration axiom to the ontology, that essentially adds
         * the class to the signature of our ontology.
         * 
         */
        OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(indv);
        manager.addAxiom(ontology, declarationAxiom);

        /* In this case, we would like to state that matthew has a father who is
         * peter. We need a subject and object - matthew is the subject and
         * peter is the object. We use the data factory to obtain references to
         * these individuals
         */
        OWLIndividual individual = factory.getOWLNamedIndividual(IRI.create(xmlBase + individualName));
        /* We can also specify that matthew is an instance of Person. To do this
         * we use a ClassAssertion axiom. First we need a reference to the
         * person class
         */
        OWLClass personClass = factory.getOWLClass(IRI.create(xmlBase + className));
        /* Now we will create out Class Assertion to specify that matthew is an
         * instance of Person (or rather that Person has matthew as an instance)
         */
        OWLClassAssertionAxiom axiom = factory.getOWLClassAssertionAxiom(personClass, individual);
        /* Add this axiom to our ontology. We can use a short cut method -
         * instead of creating the AddAxiom change ourselves, it will be created
         * automatically and the change applied
         */
        manager.addAxiom(ontology, axiom);
        // reasoner.flush();

    }


    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static void addObjectPropertyIndividuals(String property, String domain, String range) {
        // In this case, we would like to state that matthew has a father who is
        // peter. We need a subject and object - matthew is the subject and
        // peter is the object. We use the data factory to obtain references to
        // these individuals
        OWLIndividual domainInd = factory.getOWLNamedIndividual(IRI.create(xmlBase + domain));
        OWLIndividual rangeInd = factory.getOWLNamedIndividual(IRI.create(xmlBase + range));
        // We want to link the subject and object with the hasFather property,
        // so use the data factory to obtain a reference to this object
        // property.
        OWLObjectProperty hasFather = factory.getOWLObjectProperty(IRI.create(xmlBase + property));
        // Now create the actual assertion (triple), as an object property
        // assertion axiom matthew --> hasFather --> peter
        OWLObjectPropertyAssertionAxiom assertion = factory.getOWLObjectPropertyAssertionAxiom(hasFather, domainInd, rangeInd);
        // Finally, add the axiom to our ontology and save
        manager.addAxiom(ontology, assertion);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     * @param property
     * @param domain
     * @param range
     */
    public static void addDataPropertyIndividuals(String property, String domain, String range) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLDataProperty objectProperty = factory.getOWLDataProperty(IRI.create(xmlBase + property));
        OWLIndividual domainInd = factory.getOWLNamedIndividual(IRI.create(xmlBase + domain));
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(objectProperty, domainInd, range);
        manager.addAxiom(ontology, dataPropertyAssertion);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     * @param property
     * @param domain
     * @param range
     */
    public static void addDataPropertyIndividuals(String property, String domain, boolean range) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLDataProperty objectProperty = factory.getOWLDataProperty(IRI.create(xmlBase + property));
        OWLIndividual domainInd = factory.getOWLNamedIndividual(IRI.create(xmlBase + domain));
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(objectProperty, domainInd, range);
        manager.addAxiom(ontology, dataPropertyAssertion);
        // reasoner.flush();
    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     * @param property
     * @param domain
     * @param range
     */
    public static void addDataPropertyIndividuals(String property, String domain, URI range) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLDataProperty objectProperty = factory.getOWLDataProperty(IRI.create(xmlBase + property));
        OWLIndividual domainInd = factory.getOWLNamedIndividual(IRI.create(xmlBase + domain));
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify th    at :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLLiteral URILiteral = factory.getOWLLiteral(range.toString(), OWL2Datatype.XSD_ANY_URI);
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(objectProperty, domainInd, URILiteral);
        manager.addAxiom(ontology, dataPropertyAssertion);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     * @param objectProperty
     * @param domainInd
     * @param range
     */
    public static void addDataPropertyIndividuals(OWLDataProperty objectProperty, OWLIndividual domainInd, String range) {
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(objectProperty, domainInd, range);
        manager.addAxiom(ontology, dataPropertyAssertion);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     * @param objectProperty
     * @param domainInd
     * @param range
     */
    public static void addDataPropertyIndividuals(OWLDataProperty objectProperty, OWLIndividual domainInd, boolean range) {
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(objectProperty, domainInd, range);
        manager.addAxiom(ontology, dataPropertyAssertion);
        // reasoner.flush();

    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     * @param individual
     * @param objectProperty
     * @return 
     */
    public static OWLIndividual getObjectPropertyIndividual(OWLIndividual individual, OWLObjectProperty objectProperty) {
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify th    at :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        Set<OWLIndividual> s = individual.getObjectPropertyValues(objectProperty, ontology);
        OWLIndividual ind = null;
        if (s != null) {
            Iterator<OWLIndividual> iter = s.iterator();
            if (iter.hasNext())
                ind = iter.next();
        }
        return ind;
    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static OWLIndividual getObjectPropertyIndividual(OWLIndividual individual, String property) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(IRI.create(xmlBase + property));
        Set<OWLIndividual> s = individual.getObjectPropertyValues(objectProperty, ontology);
        OWLIndividual ind = null;
        if (s != null) {
            Iterator<OWLIndividual> iter = s.iterator();
            // Now create the axiom
            // We now use the manager to apply the change
            // To specify th    at :John has an age of 51 we create a data property
            // assertion and add it to the ontology
            if (iter.hasNext())
                ind = iter.next();
        }
        return ind;
    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static String getObjectPropertyIndividualShort(OWLNamedIndividual individual, String property) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(IRI.create(xmlBase + property));
        Set<OWLIndividual> s = individual.getObjectPropertyValues(objectProperty, ontology);
        OWLIndividual ind = null;
        if (s != null) {
            Iterator<OWLIndividual> iter = s.iterator();
            // Now create the axiom
            // We now use the manager to apply the change
            // To specify th    at :John has an age of 51 we create a data property
            // assertion and add it to the ontology
            if (iter.hasNext()) {
                ind = iter.next();
                String st = ind.toString();
                int index = st.indexOf(defaultPrefix);
                return st.substring(index + 1);
            }
        }
        return null;
    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static OWLLiteral getObjectPropertyIndividualString(OWLNamedIndividual individual, String property) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(IRI.create(xmlBase + property));
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify th    at :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        Set<OWLIndividual> s = individual.getObjectPropertyValues(objectProperty, ontology);
        if (s != null) {
            Iterator<OWLIndividual> iter = s.iterator();
            if (iter.hasNext()) {
                OWLIndividual ind = iter.next();
                // Now create the axiom
                // We now use the manager to apply the change
                // To specify th    at :John has an age of 51 we create a data property
                // assertion and add it to the ontology
                Set<OWLLiteral> literals = ind.getDataPropertyValues(hasText, ontology);
                return literals.iterator().next();
            }
        }
        return null;
    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static OWLLiteral getDataPropertyIndividual(OWLNamedIndividual ind, OWLDataProperty property) {
        Set<OWLLiteral> literals = ind.getDataPropertyValues(property, ontology);
        return literals.iterator().next();
    }

    /**
     * add an object property assertion (triple) of the
     * form prop(subject, object) for example hasPart(a, b)
     */
    public static OWLLiteral getDataPropertyIndividuals(OWLNamedIndividual ind, String property) {
        /* We want to link the subject and object with the hasFather property,
         * so use the data factory to obtain a reference to this object
         * property.
         */
        OWLDataProperty objectProperty = factory.getOWLDataProperty(IRI.create(xmlBase + property));
        // Now create the axiom
        // We now use the manager to apply the change
        // To specify th    at :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        Set<OWLLiteral> literals = ind.getDataPropertyValues(objectProperty, ontology);
        return literals.iterator().next();
    }

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
/**
 * Primitives to save to an ontology/knowledge base
 * 
 */
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /**
     * save ontology to file using a format
     */
    public static void saveOntology() {
        File file = new File(outputFilename + fileSeparator + "KB0.owl");

        try {
            manager.saveOntology(ontology, owlxmlFormat, IRI.create(file.toURI()));
        }
        catch (OWLOntologyStorageException owlEx) {
            LOG.log(Level.SEVERE, owlEx.getMessage(), owlEx);
        }
    }
    
    /**
     * 
     */
    public static void doQuery(DLQueryPrinter dlQueryPrinter, String classExpression) throws IOException {
        // Prompt the user to enter a class expression
        System.out.println("Please type a class expression in Manchester Syntax and press Enter (or press x to exit):");
        System.out.println("");
        // Check for exit condition
        if (classExpression.equalsIgnoreCase("x")) {
            return;
        }
        dlQueryPrinter.getMaxIDIndividuals(classExpression.trim());
        System.out.println();
        System.out.println();
    }

}
