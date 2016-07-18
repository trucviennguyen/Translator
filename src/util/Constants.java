/*
 * Constants.java
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

import java.util.logging.Logger;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import processing.DLQueryPrinter;

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
public class Constants {

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
/**
 * General constants
 * 
 */
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    /** The log */
    public static final Logger LOG = Logger.getLogger("util.Constants");

    /** Local fashion for file separators. */
    public static String fileSeparator = System.getProperty("file.separator");

    /** encoding */
    public static String encoding = "UTF-8";

    /* filename for relation type + entity types */
    public static String inputFilename = "input";

    /* filename for relation type + entity types */
    public static String outputFilename = "output";

    /*  */
    public static String INFO_TAIL = ".info";
    public static String POST_TAIL = ".post";

    /*  */
    public static final String SHARE_PHOTO = "photo";

    /*  */
    public static final boolean isPopular = false;


/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
/**
 * Constants for ontology and OWL API functionalities
 * 
 */
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    /*  */
    public static String ontologyFilename = "";
    /*  */
    // public static String ontologyCoreFilename = "ontology.owl";
    public static String ontologyCoreFilename = "ontology.owl";
    /*  */
    public static String newIDFilename = "newIDs.txt";
    /*  */
    public static String upperClass = "Thing";
    
    /*  */
    public static IRI ontologyIRI = null;
    
    /* base */
    public static String xmlBase = "#";
    public static int lengthXmlBase = -1;
    
    /* base */
    public static String CLAS_BASE = "#";
    
    /* base */
    public static String INDIVIDUAL_BASE = "_";
    
    /* prefix */
    public static String ontologyPrefix = "";
    public static final String defaultPrefix = "http://www.semanticweb.org/tvnguyen/ontologies/WebDataNet#";
    
    /*  */
    public static OWLOntologyManager manager = null;
    
    /*  */
    public static OWLDataFactory factory = null;
    
    /*  */
    public static PrefixManager pm = null;
    
    /*  */
    public static OWLOntology ontology = null;
    
    /*  */
    public static OWLReasonerFactory reasonerFactory = null;
    public static OWLReasoner reasoner = null;
    
    /*  */
    public static ShortFormProvider shortFormProvider = null;
    
    /*  */
    public static DLQueryPrinter dlQueryPrinter = null;
    
    /*  */
    public static OWLXMLOntologyFormat owlxmlFormat = null;
    
    public static OWLObjectProperty hasID;
    public static OWLDataProperty hasText;


/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
/**
 * Constants for names
 * 
 */
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    
    /*  */
    public static String clas_query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX wd: <http://www.semanticweb.org/tvnguyen/ontologies/WebDataNet#>\n" +
            "SELECT ?X\n" +
            "	WHERE { ?X rdf:type wd:Person }";

}
