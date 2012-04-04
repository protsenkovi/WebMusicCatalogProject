/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Klaritin
 */
public class AlbumModel implements Serializable {

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

    public AlbumModel(long id, String name, long genre, long group) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.group = group;
    }
    /**
     * Creates a new instance of AlbumManagedBean
     */
    public AlbumModel() {
    }
    
    
    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " group:" + group;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AlbumModel))
            return false;        
        return this.id == ((AlbumModel)obj).getId();
    }
}
