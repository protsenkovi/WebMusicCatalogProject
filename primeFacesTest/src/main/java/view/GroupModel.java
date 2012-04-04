/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Klaritin
 */
public class GroupModel implements Serializable {

    private long id;
    private String name;

    public GroupModel(long id, String name) {
        this.id = id;
        this.name = name;
        Logger.getLogger(GroupModel.class.getName()).log(Level.INFO, "VLEU GroupModelConstructor Parameters: id: " + id + " name:" + name);
    }

    public void setId(long id) {
        this.id = id;
        Logger.getLogger(GroupModel.class.getName()).log(Level.INFO, "VLEU GroupModelSETTER Parameters: new ID:" + id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    /**
     * Creates a new instance of GroupModelManagedBean
     */
    public GroupModel() {
        Logger.getLogger(GroupModel.class.getName()).log(Level.INFO, "VLEU GroupModelConstructor Default");
    }
    
    
    @Override
    public String toString() {
        return "id: " + id + " name: "+ name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GroupModel))
            return false;        
        return this.id == ((GroupModel)obj).getId();
    }
}
