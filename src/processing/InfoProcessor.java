/*
 * InfoProcessor.java
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

package processing;

import java.util.ArrayList;
import java.util.logging.Level;
import kb.Comment;
import kb.Person;
import kb.Post;
import util.KBUtils;
import static util.Constants.LOG;
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
public class InfoProcessor {
    
    private static enum DumpTag {id, first_name, last_name,
                            name, username, ignorable, msg, fromPersonID};

/**
 * @param markup personal information in parentheses
 * @return 
 */
    public static Person processUserMarkup() {
        Person person = null;
        int index = 0;

        // process template properties
        String id, name, first_name, last_name, username;

        id = name = first_name = last_name = username = null;
        String st = null;

        try {
            while ( (st = reader.readLine()) != null) {
                index = st.indexOf("\t");
                String att = st.substring(0, index).trim();
                String val = st.substring(index + 1).trim();

                switch (resolveDumpTag(att)) {

                    case id:
                        id = val;
                        break;

                    case name:
                        name = val;
                        break;

                    case first_name:
                        first_name = val;
                        break;

                    case last_name:
                        last_name = val;
                        break;

                    case username:
                        username = val;
                        break;

                }
            }

            reader.close();
        }
        catch (java.io.IOException ioEx) {
            LOG.log(Level.SEVERE, ioEx.getMessage(), ioEx);
        }
        
        person = new Person(id, name, first_name, last_name, username);
        KBUtils.addPersonIndividual(person);
        /* String query = "Person and hasID value \"" + id + "\"";
        dlQueryPrinter.getIndividualOfDataProperty(query);*/
        // KBUtils.addPersonIndividual(id, name, first_name, last_name, link, username, gender, locale, updated_time);

        return person;
    }


/** 
 */
    public static void processPostMarkup() {
        Person fromPerson, toPerson;
        Post post = null;
        Comment comment = null;
        ArrayList<Person> likes = new ArrayList();
        ArrayList<Comment> comments = new ArrayList();
        int index = 0;

        // process template properties
        String id, name, postText, commentText, fromPersonID, val;
        String st = null;

        id = name = postText = commentText= fromPersonID = "";

        try {
            st = reader.readLine().trim();
            index = st.indexOf("\t");
            String att = st.substring(0, index).trim();
            id = st.substring(index + 1).trim();

            st = reader.readLine().trim();
            index = st.indexOf("\t");
            att = st.substring(0, index).trim();
            name = st.substring(index + 1).trim();

            fromPerson = new Person(id, name);
            KBUtils.addPersonIndividual(fromPerson);
            if ( ((st = reader.readLine()) != null) && ((st = st.trim()).equals("Posts")) ) {
                while ( (st = reader.readLine()) != null) {
                    st = st.trim();
                    index = st.indexOf("\t");
                    if ((index == -1) && (!st.equals("Likes"))) {
                        postText += "\n" + st;
                    }
                    else {
                        if (!st.equals("Likes")) {
                            att = st.substring(0, index).trim();
                            id = st.substring(index + 1).trim();

                            st = reader.readLine().trim();
                            index = st.indexOf("\t");
                            postText = "";
                            if (index > -1) {
                                att = st.substring(0, index).trim();
                                postText = st.substring(index + 1).trim();
                            }

                            post = new Post(id, postText);
                            post.setFromPerson(fromPerson);
                            likes.clear();
                            comments.clear();
                            st = reader.readLine();
                        }
                        if ( (st != null) && ((st = st.trim()).equals("Likes")) ) {
                            while ( ((st = reader.readLine()) != null) && (!(st = st.trim()).equals("")) 
                                    && (!st.equals("Comments"))) {
                                index = st.indexOf("\t");
                                att = st.substring(0, index).trim();
                                val = st.substring(index + 1).trim();

                                switch (resolveDumpTag(att)) {
                                    case id:
                                        id = val;
                                        break;

                                    case name:
                                        name = val;
                                        Person person = new Person(id, name);
                                        KBUtils.addPersonIndividual(person);
                                        likes.add(person);
                                        break;
                                }
                            }
                        }

                        if ( (st != null) && ((st = st.trim()).equals("Comments")) ) {
                            while ( ((st = reader.readLine()) != null) && (!(st = st.trim()).equals("")) ) {
                                index = st.indexOf("\t");
                                if (index == -1) {
                                    switch (resolveDumpTag(att)) {
                                        case msg:
                                            commentText += "\n" + st;
                                            break;
                                    }
                                }
                                else {
                                    att = st.substring(0, index).trim();
                                    val = st.substring(index + 1).trim();

                                    switch (resolveDumpTag(att)) {
                                        case id:
                                            id = val;
                                            break;

                                        case msg:
                                            commentText = val;
                                            break;

                                        case fromPersonID:
                                            fromPersonID = val;
                                            break;

                                        case name:
                                            name = val;
                                            Person person = new Person(fromPersonID, name);
                                            KBUtils.addPersonIndividual(person);
                                            comment = new Comment(id, person, commentText);
                                            KBUtils.addCommentIndividual(comment);
                                            comments.add(comment);
                                            break;
                                    }
                                }
                            }

                        }

                        post.setMessage(postText);
                        post.setLikes(likes);
                        post.setComments(comments);
                        KBUtils.addPostIndividual(post);
                    }

                    // person = new Person(id, name);
                }
            }

            reader.close();
        }
        catch (java.io.IOException ioEx) {
            LOG.log(Level.SEVERE, ioEx.getMessage(), ioEx);
        }
    }

    private static DumpTag resolveDumpTag(String tagName) {
        try {
            return DumpTag.valueOf(tagName);
        }
        catch (IllegalArgumentException e) {
            return DumpTag.ignorable;
        }
    }
    
}
