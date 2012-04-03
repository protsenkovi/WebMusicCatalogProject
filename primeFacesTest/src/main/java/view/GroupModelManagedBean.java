/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Klaritin
 */
public class GroupModelManagedBean implements Serializable {

    private long id;
    private String name;

    private List<MusicianModel> members;

    public GroupModelManagedBean(long id, String name, List<MusicianModel> members) {
        this.id = id;
        this.name = name;
        this.members = members;
        Logger.getLogger(GroupModelManagedBean.class.getName()).log(Level.INFO, "VLEU GroupModelConstructor Parameters: id: " + id + " name:" + name + " members: " + members);
    }

    public void setId(long id) {
        this.id = id;
        Logger.getLogger(GroupModelManagedBean.class.getName()).log(Level.INFO, "VLEU GroupModelSETTER Parameters: new ID:" + id);
    }

//    public void setMembers(List<MusicianModel> members) {
//        if (!(sun.reflect.Reflection.getCallerClass(2).equals(javax.el.BeanELResolver.class)))
//            this.members = members;
//        Logger.getLogger(GroupModelManagedBean.class.getName()).log(Level.INFO, "VLEU GroupModelSETTER Parameters:  members: " + members + " caller method is resolver " + !(sun.reflect.Reflection.getCallerClass(2).equals(javax.el.BeanELResolver.class)));
//        StringBuilder trace = new StringBuilder();
//        for (int i = 0; i < 20; i++)
//            trace.append("-> " + sun.reflect.Reflection.getCallerClass(i));
//            Logger.getLogger(GroupModelManagedBean.class.getName()).log(Level.INFO, "VLEU GroupModelSETTER Caller method trace" + trace.toString());
//    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public List<MusicianModel> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }
    
    /**
     * Creates a new instance of GroupModelManagedBean
     */
    public GroupModelManagedBean() {
        members = new ArrayList<MusicianModel>();
        Logger.getLogger(GroupModelManagedBean.class.getName()).log(Level.INFO, "VLEU GroupModelConstructor Default");
    }
    
    
    @Override
    public String toString() {
        return "id: " + id + " name: "+ name + " members " + members;
    }
}
