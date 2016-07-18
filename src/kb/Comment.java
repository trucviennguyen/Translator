/*
 * Comment.java
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

package kb;

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
public class Comment {
    private String id, message, postID;
    private Person fromPerson;
    
    public Comment(String id) {
        this.id = id;
    }
    
    public Comment(String id, String message) {
        this.id = id;
        this.message = message;
    }
    
    public Comment(String id, Person fromPerson, String message) {
        this.id = id;
        this.fromPerson = fromPerson;
        this.message = message;
    }
    
    public String getID() {
        return id;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Person getFromPerson() {
        return fromPerson;
    }
    
    public void setFromPerson(Person fromPerson) {
        this.fromPerson = fromPerson;
    }

}
