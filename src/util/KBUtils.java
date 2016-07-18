/*
 * KBUtils.java
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

import java.util.ArrayList;
import kb.Comment;
import kb.Person;
import kb.Post;
import static util.Constants.isPopular;
import static util.Constants.SHARE_PHOTO;
import static util.Intermediate.IDs;
import static util.Intermediate.mapping;
import static util.Intermediate.newPersonIDs;
import static util.Intermediate.oldPersonIDs;

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
public class KBUtils {

    /** person class name */
    public static String idClassName = "ID";
    public static String birthdayClassName = "Birthday";
    public static String linkClassName = "Link";
    public static String nameClassName = "Name";
    public static String messageClassName = "Message";
    public static String personClassName = "Person";
    public static String postClassName = "Post";
    public static String commentClassName = "Comment";

    /**
     * add an individual
     */
    public static void addPersonIndividual(String id) {
        // 
        if (!IDs.contains(id)) {
            IDs.add(id);
            if (!oldPersonIDs.contains(id))
                newPersonIDs.add(id);

            int maxID = Intermediate.maxUserIndex.get(personClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(personClassName, maxID);
            String personID = personClassName + Constants.INDIVIDUAL_BASE + maxID;

            System.out.println("\t" + "Adding " + personID + ": id = " + id);

            OntologyUtils.addIndividual(personClassName, personID);
            /*
             * adding id to person individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", personID, idID);
            mapping.put(id, personID);
        }

    }

    /**
     * add an individual
     */
    public static void addPersonIndividual(String id, String name) {
        // 
        if (!IDs.contains(id)) {
            IDs.add(id);
            if (!oldPersonIDs.contains(id))
                newPersonIDs.add(id);

            int maxID = Intermediate.maxUserIndex.get(personClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(personClassName, maxID);
            String personID = personClassName + Constants.INDIVIDUAL_BASE + maxID;

            System.out.println("\t" + "Adding " + personID 
                    + ": id = " + id + "; name = " + name);

            OntologyUtils.addIndividual(personClassName, personID);
            /*
             * adding id to person individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", personID, idID);
            mapping.put(id, personID);

            /*
             * adding id to person individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(nameClassName, maxID);
            String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(nameClassName, nameID);
            OntologyUtils.addDataPropertyIndividuals("hasText", nameID, name);
            OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasName", personID, nameID);
        }

    }

    public static void addPersonIndividual(String id, String name, String firstName, String lastName, String link,
                  String username, String gender, String locale, String updatedTime) {
        // 
        if (!IDs.contains(id)) {
            IDs.add(id);
        if (!oldPersonIDs.contains(id))
            newPersonIDs.add(id);

            int maxID = Intermediate.maxUserIndex.get(personClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(personClassName, maxID);
            String personID = personClassName + Constants.INDIVIDUAL_BASE + maxID;

            System.out.println("\t" + "Adding " + personID 
                    + ": id = " + id + "; name = " + name);

            OntologyUtils.addIndividual(personClassName, personID);

            /*
             * adding id to person individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", personID, idID);
            mapping.put(id, personID);

            if (firstName != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(nameClassName, maxID);
                String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(nameClassName, nameID);
                OntologyUtils.addDataPropertyIndividuals("hasText", nameID, firstName);
            OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasFirstname", personID, nameID);
            }

            if (lastName != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(nameClassName, maxID);
                String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(nameClassName, nameID);
                OntologyUtils.addDataPropertyIndividuals("hasText", nameID, lastName);
            OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasLastname", personID, nameID);
            }

            if (link != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(linkClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(linkClassName, maxID);
                String linkID = linkClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(linkClassName, linkID);
                OntologyUtils.addDataPropertyIndividuals("hasText", linkID, link);
                OntologyUtils.addDataPropertyIndividuals("isPopular", linkID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasLink", personID, linkID);
            }

            if (name != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(nameClassName, maxID);
                String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(nameClassName, nameID);
                OntologyUtils.addDataPropertyIndividuals("hasText", nameID, name);
                OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasName", personID, nameID);
            }

            if (username != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(idClassName, maxID);
                idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(idClassName, idID);
                OntologyUtils.addDataPropertyIndividuals("hasText", idID, username);
                OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasUsername", personID, idID);
            }
        }
    }

    /**
     * add an individual
     */
    public static void addPersonIndividual(Person person) {
        // 
        String id = person.getID(),
            name = person.getName(),
            firstName = person.getFirstname(),
            lastName = person.getLastname(),
            link = person.getLink(),
            username = person.getUsername();
        if (!IDs.contains(id)) {
            IDs.add(id);
            if (!oldPersonIDs.contains(id))
                newPersonIDs.add(id);

            int maxID = Intermediate.maxUserIndex.get(personClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(personClassName, maxID);
            String personID = personClassName + Constants.INDIVIDUAL_BASE + maxID;
            
            System.out.println("\t" + "Adding " + personID 
                    + ": id = " + id + "; name = " + name);

            OntologyUtils.addIndividual(personClassName, personID);

            /*
             * adding id to person individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", personID, idID);
            mapping.put(id, personID);

            if (firstName != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(nameClassName, maxID);
                String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(nameClassName, nameID);
                OntologyUtils.addDataPropertyIndividuals("hasText", nameID, firstName);
                OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasFirstname", personID, nameID);
            }

            if (lastName != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(nameClassName, maxID);
                String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(nameClassName, nameID);
                OntologyUtils.addDataPropertyIndividuals("hasText", nameID, lastName);
                OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasLastname", personID, nameID);
            }

            if (link != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(linkClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(linkClassName, maxID);
                String linkID = linkClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(linkClassName, linkID);
                OntologyUtils.addDataPropertyIndividuals("hasText", linkID, link);
                OntologyUtils.addDataPropertyIndividuals("isPopular", linkID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasLink", personID, linkID);
            }

            if (name != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(nameClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(nameClassName, maxID);
                String nameID = nameClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(nameClassName, nameID);
                OntologyUtils.addDataPropertyIndividuals("hasText", nameID, name);
                OntologyUtils.addDataPropertyIndividuals("isPopular", nameID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasName", personID, nameID);
            }

            if (username != null) {
                /*
                 * adding id to person individual
                 * adding new alias first
                 */
                maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(idClassName, maxID);
                idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(idClassName, idID);
                OntologyUtils.addDataPropertyIndividuals("hasText", idID, username);
                OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasUsername", personID, idID);
            }
        }

    }

    /**
     * add an individual
     */
    public static void addCommentIndividual(Comment comment) {
        // 
        String id = comment.getID(),
            message = comment.getMessage();
        Person fromPerson = comment.getFromPerson();
        if (!IDs.contains(id)) {
            IDs.add(id);

            int maxID = Intermediate.maxUserIndex.get(commentClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(commentClassName, maxID);

            String commentID = commentClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(commentClassName, commentID);

            System.out.print("Adding " + commentID 
                    + ": id = " + id);
            if ((message != null) && (!message.equals("")))
                System.out.println("\t" + "; message = " + message);
            else
                System.out.println();

            String pID = fromPerson.getID();
            String personID = mapping.get(pID);

            OntologyUtils.addIndividual(commentClassName, commentID);
            System.out.println("\t" + "Connecting " + commentID + " with " + personID);
            OntologyUtils.addObjectPropertyIndividuals("makeComment", personID, commentID);

            /*
             * adding id to comment individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", commentID, idID);
            mapping.put(id, commentID);

            if (message != null) {
                /*
                 * adding message to comment individual
                 * adding new content first
                 */
                maxID = Intermediate.maxUserIndex.get(messageClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(messageClassName, maxID);
                String messageID = messageClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(messageClassName, messageID);
                OntologyUtils.addDataPropertyIndividuals("hasText", messageID, message);
                OntologyUtils.addDataPropertyIndividuals("isPopular", messageID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasMessage", commentID, messageID);
            }
        }

    }

    /**
     * add an individual
     */
    public static void addPostIndividual(String id) {
        // 
        if (!IDs.contains(id)) {
            IDs.add(id);

            int maxID = Intermediate.maxUserIndex.get(postClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(postClassName, maxID);
            String postID = postClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(postClassName, postID);

            System.out.println("\t" + "Adding " + postID 
                    + ": id = " + id);

            /*
             * adding id to post individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", postID, idID);
            mapping.put(id, postID);
        }

    }

    /**
     * add an individual
     */
    public static void addPostIndividual(Post post) {
        // 
        String id = post.getID(),
               caption = post.getCaption(),
               message = post.getMessage(),
               type = post.getType();
        
        if (!IDs.contains(id)) {
            IDs.add(id);

            Person fromPerson = post.getFromPerson();
            ArrayList<Comment> comments = post.getComments();
            ArrayList<Person> likes = post.getLikes();

            String pID = fromPerson.getID();
            String personID = mapping.get(pID);

            int maxID = Intermediate.maxUserIndex.get(postClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(postClassName, maxID);
            String postID = postClassName + Constants.INDIVIDUAL_BASE + maxID;

            System.out.print("Adding " + postID 
                    + ": id = " + id);
            if ((message != null) && (!message.equals("")))
                System.out.println("\t" + "; message = " + message);
            else
                System.out.println();

            OntologyUtils.addIndividual(postClassName, postID);
            System.out.println("\t" + "Connecting " + postID + " with " + personID);
            OntologyUtils.addObjectPropertyIndividuals("makePost", personID, postID);

            /*
             * adding id to post individual
             * adding new alias first
             */
            maxID = Intermediate.maxUserIndex.get(idClassName).intValue() + 1;
            Intermediate.maxUserIndex.put(idClassName, maxID);
            String idID = idClassName + Constants.INDIVIDUAL_BASE + maxID;
            OntologyUtils.addIndividual(idClassName, idID);
            OntologyUtils.addDataPropertyIndividuals("hasText", idID, id);
            OntologyUtils.addDataPropertyIndividuals("isPopular", idID, isPopular);
            OntologyUtils.addObjectPropertyIndividuals("hasID", postID, idID);
            mapping.put(id, postID);

            if ((type != null) && (type.equals(SHARE_PHOTO))) {
                if ((caption != null) && (!caption.equals(""))) {
                    /*
                     * adding id to post individual
                     * adding new alias first
                     */
                    maxID = Intermediate.maxUserIndex.get(messageClassName).intValue() + 1;
                    Intermediate.maxUserIndex.put(messageClassName, maxID);
                    String messageID = messageClassName + Constants.INDIVIDUAL_BASE + maxID;
                    OntologyUtils.addIndividual(messageClassName, messageID);
                    OntologyUtils.addDataPropertyIndividuals("hasText", messageID, caption);
                    OntologyUtils.addDataPropertyIndividuals("isPopular", messageID, isPopular);
                    OntologyUtils.addObjectPropertyIndividuals("hasCaption", postID, messageID);
                }
            }
            if ((message != null) && (!message.equals(""))) {
                /*
                 * adding message to comment individual
                 * adding new content first
                 */
                maxID = Intermediate.maxUserIndex.get(messageClassName).intValue() + 1;
                Intermediate.maxUserIndex.put(messageClassName, maxID);
                String messageID = messageClassName + Constants.INDIVIDUAL_BASE + maxID;
                OntologyUtils.addIndividual(messageClassName, messageID);
                OntologyUtils.addDataPropertyIndividuals("hasText", messageID, message);
                OntologyUtils.addDataPropertyIndividuals("isPopular", messageID, isPopular);
                OntologyUtils.addObjectPropertyIndividuals("hasMessage", postID, messageID);
            }
            
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);

                String cID = comment.getID();
                String commentID = mapping.get(cID);
                System.out.println("\t" + "Connecting " + postID + " with " + commentID);
                OntologyUtils.addObjectPropertyIndividuals("hasComment", postID, commentID);

            }
            
            for (int i = 0; i < likes.size(); i++) {
                Person person = likes.get(i);

                String plID = person.getID();
                String personLikeID = mapping.get(plID);
                System.out.println("\t" + "Connecting " + postID + " with " + personLikeID + " who likes the post.");
                OntologyUtils.addObjectPropertyIndividuals("like", personLikeID, postID);

            }
        }

    }

}
