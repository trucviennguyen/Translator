/*
 * Translator.java
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

package main;

import cimec.util.FilenameFilterImpl;
import cimec.util.SortedFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import processing.InfoProcessor;
import util.Constants;
import static util.Constants.fileSeparator;
import static util.Constants.newIDFilename;
import util.OntologyUtils;
import static util.Constants.inputFilename;
import static util.Constants.outputFilename;
import static util.Constants.LOG;
import static util.Intermediate.newPersonIDs;
import static util.Intermediate.oldPersonIDs;
import static util.Intermediate.reader;

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
public class Translator {
    
    /*
     * 
     */
    public static String content = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        inputFilename = args[0];
        outputFilename = args[1];
        process();
    }

    private static void process() {

        File odf = new File(outputFilename);
        if (!odf.exists())
            odf.mkdirs();

        System.out.println("inputFilename = " + inputFilename);

        OntologyUtils.instantiateOntology();
        processInfo();
        processPosts();
        exportnewIDs();
        OntologyUtils.saveOntology();

    }

    private static void processInfo() {
        try {
            FilenameFilterImpl filter = new FilenameFilterImpl(Constants.INFO_TAIL);
            SortedFile df = new SortedFile(inputFilename);
            File[] files = df.listFiles(filter);
            for (int i = 0; i < files.length; i++) {

                File idf = files[i];

                /** get the head of the news source */
                String st = files[i].getName();
                System.out.println("Process info file " + st);
                int index = st.lastIndexOf(Constants.INFO_TAIL);

                // we use person id as the filename + tail (.info.txt or .post.txt)
                String id = st.substring(0, index);
                oldPersonIDs.add(id);

                reader = new BufferedReader(new InputStreamReader(new FileInputStream(idf), "UTF-8"));
                InfoProcessor.processUserMarkup();
            }
        }
        catch (java.io.IOException ioEx) {
            LOG.log(Level.SEVERE, ioEx.getMessage(), ioEx);
        }

    }

    private static void processPosts() {
        try {
            FilenameFilterImpl filter = new FilenameFilterImpl(Constants.POST_TAIL);
            SortedFile df = new SortedFile(inputFilename);
            File[] files = df.listFiles(filter);
            for (int i = 0; i < files.length; i++) {

                File idf = files[i];

                /** get the head of the news source */
                String st = files[i].getName();
                if (st.equals("21723274.post"))
                    System.out.print("");
                System.out.println("Process post file " + st);
                int index = st.lastIndexOf(Constants.POST_TAIL);

                // we use person id as the filename + tail (.info.txt or .post.txt)
                String id = st.substring(0, index);
                oldPersonIDs.add(id);

                reader = new BufferedReader(new InputStreamReader(new FileInputStream(idf), "UTF-8"));
                InfoProcessor.processPostMarkup();
            }
        }
        catch (java.io.IOException ioEx) {
            LOG.log(Level.SEVERE, ioEx.getMessage(), ioEx);
        }

    }

    public static void exportnewIDs() {
        //
        try {
            newIDFilename = outputFilename + fileSeparator + newIDFilename;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                         new FileOutputStream(newIDFilename), "UTF-8"));
            for (int i = 0; i < newPersonIDs.size(); i++) {
                String id = newPersonIDs.get(i);
                writer.write(id);
                writer.write("\n");
            }
        }
        catch (java.io.FileNotFoundException fEx) {
            Constants.LOG.log(Level.SEVERE, fEx.getMessage(), fEx);
        }
        catch (java.io.IOException ioEx) {
            Constants.LOG.log(Level.SEVERE, ioEx.getMessage(), ioEx);
        }

    }

}
