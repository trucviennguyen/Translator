/*
 * DLQueryPrinter.java
 *
 * Converter -- a translator from facebook info to ontology
 * Copyright (c) 2012 Truc-Vien T. Nguyen. All Rights Reserved.
 *
 * Converter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Converter is distributed in the hope that it will be useful,
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
package processing;

import java.util.Iterator;
import java.util.Set;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.util.ShortFormProvider;
import util.OntologyUtils;

import static util.Constants.CLAS_BASE;
import static util.Constants.defaultPrefix;
import static util.Constants.lengthXmlBase;
import static util.Constants.ontology;
import static util.Intermediate.maxUserIndex;
import static util.Intermediate.IDs;

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
public class DLQueryPrinter {
    private DLQueryEngine dlQueryEngine;
    private ShortFormProvider shortFormProvider;

    /** @param engine
     *            the engine
     * @param shortFormProvider
     *            the short form provider */
    public DLQueryPrinter(DLQueryEngine engine, ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = engine;
    }

    /** @param classExpression
     *            the class expression to use for interrogation */
    public void getMaxIDIndividuals(String clasType) {
        if (clasType.length() == 0) {
            System.out.println("No class expression specified");
        } 
        else {
            try {
                Set<OWLClass> subClasses = dlQueryEngine.getSubClassSet(clasType, false);
                Iterator<OWLClass> clasIter = subClasses.iterator();
                while (clasIter.hasNext()) {
                    OWLClass clas = clasIter.next();
                    String clasName = clas.getIRI().toString();
                    int index = clasName.indexOf(CLAS_BASE);
                    String short_ClasName = clasName.substring(index + 1);
                    int l = clasName.length() + 1, maxID = 0;
                    Set clasIndividuals = clas.getIndividuals(ontology);
                    Iterator<OWLNamedIndividual> indIter = clasIndividuals.iterator();
                    while (indIter.hasNext()) {
                        OWLNamedIndividual ind = indIter.next();
                        
                        // add the ID to this set if it is not already present
                        // String literal = OntologyUtils.getObjectPropertyIndividuals(ind, "hasID");
                        // IDs.add(literal);
                        String st = ind.toString();
                        index = st.indexOf(defaultPrefix);
                        String literal = st.substring(index + 1);
                        IDs.add(literal);
                        
                        // get the maximum index of the class
                        st = ind.getIRI().toString().substring(l);
                        int id = Integer.parseInt(st);
                        if (id > maxID)
                            maxID = id;
                    }
                    maxUserIndex.put(short_ClasName, maxID);
                }

            }
            catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** @param classExpression
     *            the class expression to use for interrogation */
    public void askQuery(String classExpression) {
        if (classExpression.length() == 0) {
            System.out.println("No class expression specified");
        } 
        else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("\n--------------------------------------------------------------------------------\n");
                sb.append("QUERY:   ");
                sb.append(classExpression);
                sb.append("\n");
                sb.append("--------------------------------------------------------------------------------\n\n");
                // Ask for the subclasses, superclasses etc. of the specified
                // class expression. Print out the results.
                Set<OWLClass> superClasses = dlQueryEngine.getSuperClasses(
                        classExpression, true);
                printEntities("SuperClasses", superClasses, sb);
                Set<OWLClass> equivalentClasses = dlQueryEngine
                        .getEquivalentClasses(classExpression);
                printEntities("EquivalentClasses", equivalentClasses, sb);
                Set<OWLClass> subClasses = dlQueryEngine.getSubClassSet(classExpression,
                        true);
                printEntities("SubClasses", subClasses, sb);
                Set<OWLNamedIndividual> individuals = dlQueryEngine.getInstanceSet(
                        classExpression, true);
                printEntities("Instances", individuals, sb);
                System.out.println(sb.toString());
            } 
            catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printEntities(String name, Set<? extends OWLEntity> entities,
            StringBuilder sb) {
        sb.append(name);
        int length = 50 - name.length();
        for (int i = 0; i < length; i++) {
            sb.append(".");
        }
        sb.append("\n\n");
        if (!entities.isEmpty()) {
            for (OWLEntity entity : entities) {
                sb.append("\t");
                sb.append(shortFormProvider.getShortForm(entity));
                sb.append("\n");
            }
        } else {
            sb.append("\t[NONE]\n");
        }
        sb.append("\n");
    }

    /** @param classExpression
     *            the class expression to use for interrogation */
    public String getIndividualOfObjectProperty(String query) {
        String indIRI = null;
        try {

            Set<OWLNamedIndividual> instances = dlQueryEngine.getInstanceSet(query, true);
            Iterator<OWLNamedIndividual> indIter = instances.iterator();
            while (indIter.hasNext()) {
                OWLNamedIndividual ind = indIter.next();
                indIRI = ind.getIRI().toString().substring(lengthXmlBase);
            }

        }
        catch (ParserException e) {
            System.out.println(e.getMessage());
        }
        return indIRI;
    }

    /** @param classExpression
     *            the class expression to use for interrogation */
    public String getIndividualOfDataProperty(String query) {
        String indIRI = null;
        try {

            Set<OWLNamedIndividual> instances = dlQueryEngine.getInstanceSet(query, true);
            Iterator<OWLNamedIndividual> indIter = instances.iterator();
            while (indIter.hasNext()) {
                OWLNamedIndividual ind = indIter.next();
                indIRI = ind.getIRI().toString().substring(lengthXmlBase);
            }

        }
        catch (ParserException e) {
            System.out.println(e.getMessage());
        }
        return indIRI;
    }

}
