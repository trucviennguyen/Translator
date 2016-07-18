/*
 * Post.java
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

import java.util.ArrayList;

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
public class Post {
    private String id = "", caption = "", created_time = "", icon = "", description = "", 
            link = "", message = "", name = "", object_id = "", picture = "", 
            status_type = "", story = "", type = "",
            updated_time = "";
    private Person fromPerson, toPerson;
    private ArrayList<Comment> comments = new ArrayList();
    private ArrayList<Person> likes = new ArrayList();
    
    public Post(String id) {
        this.id = id;
    }
    
    public Post(String id, String message) {
        this.id = id;
        this.message = message;
    }
    
    public Post(String id, Person fromPerson, String message) {
        this.id = id;
        this.fromPerson = fromPerson;
        this.message = message;
    }
    
    public Post(String id, String message, ArrayList<Comment> comments) {
        this.id = id;
        this.message = message;
        this.comments = comments;
    }
    
    public Post(String id, String caption, String created_time, String description,
            String icon, String link, String message, String name,
            String object_id, String picture, String status_type, String story,
            String type, String updated_time) {
        this.id = id;
        this.message = message;
        this.caption = caption;
        this.created_time = created_time;
        this.description = description;
        this.icon = icon;
        this.link = link;
        this.name = name;
        this.object_id = object_id;
        this.picture = picture;
        this.status_type = status_type;
        this.story = story;
        this.type = type;
        this.updated_time = updated_time;
    }
    
    public Post(String id, Person fromPerson, String caption, String created_time,
            String description, String icon, String link, String message, String name,
            String object_id, String picture, String status_type, String story,
            String type, String updated_time, ArrayList<Comment> comments) {
        this.id = id;
        this.fromPerson = fromPerson;
        this.message = message;
        this.caption = caption;
        this.created_time = created_time;
        this.description = description;
        this.icon = icon;
        this.link = link;
        this.name = name;
        this.object_id = object_id;
        this.picture = picture;
        this.status_type = status_type;
        this.story = story;
        this.type = type;
        this.updated_time = updated_time;
        this.comments = comments;
    }
    
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
    
    public Person getFromPerson() {
        return fromPerson;
    }

    public void setFromPerson(Person fromPerson) {
        this.fromPerson = fromPerson;
    }
    
    public Person getToPerson() {
        return toPerson;
    }

    public void setToPerson(Person toPerson) {
        this.toPerson = toPerson;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
    
    public ArrayList<Person> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Person> likes) {
        this.likes = likes;
    }
    
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }
    
    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

}
