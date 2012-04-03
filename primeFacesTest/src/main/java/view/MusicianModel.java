/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;

/**
 *
 * @author Klaritin
 */
public class MusicianModel implements Serializable {
    
    private long id;
    private String name;
    private String link;

    public void setId(long id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Creates a new instance of MusicianModelManagedBean
     */
    public MusicianModel() {
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public MusicianModel(long id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: "+ name + " link " + link;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MusicianModel))
            return false;        
        return this.id == ((MusicianModel)obj).getId();
    }
}
