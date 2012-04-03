/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Klaritin
 */
@Named(value = "albumManagedBean")
@SessionScoped
public class AlbumModelManagedBean implements Serializable {

    private long id;
    private String name;
    private long genre;
    private long group;

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public void setGenre(long genre) {
        this.genre = genre;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGenre() {
        return genre;
    }
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AlbumModelManagedBean(long id, String name, long genre, long group) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.group = group;
    }
    /**
     * Creates a new instance of AlbumManagedBean
     */
    public AlbumModelManagedBean() {
    }
    
    
    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " group:" + group;
    }
}
