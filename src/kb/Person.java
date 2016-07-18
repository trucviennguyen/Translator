/*
 * Person.java
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
public class Person {
    private String id, name, firstName, lastName, link,
                   username, gender, locale, updatedTime;
    
    public Person(String id) {
        this.id = id;
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Person(String id, String name, String firstName, String lastName, String link,
                  String username, String gender, String locale, String updatedTime) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.link = link;
        this.username = username;
        this.gender = gender;
        this.locale = locale;
        this.updatedTime = updatedTime;
    }
    
    public Person(String id, String name, String firstName, String lastName, String username) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
    
    public String getID() {
        return id;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFirstname() {
        return firstName;
    }
    
    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastname() {
        return lastName;
    }
    
    public void setLastname(String lastName) {
        this.lastName = lastName;
    }
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
    
    public String getUpdatedTime() {
        return updatedTime;
    }

}
