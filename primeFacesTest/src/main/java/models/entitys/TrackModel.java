/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

import java.io.Serializable;

/**
 *
 * @author Klaritin
 */
public class TrackModel implements Serializable {

    private long id;
    private String name;
    private int mood;
    private double avgrate;

    public void setAvgrate(double avgrate) {
        this.avgrate = avgrate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double getAvgrate() {
        return avgrate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TrackModel(long id, String name, int mood, double avgrate) {
        this.id = id;
        this.name = name;
        this.mood = mood;
        this.avgrate = avgrate;
    }
    
    /**
     * Creates a new instance of TrackModelManagedBean
     */
    public TrackModel() {
    }

    @Override
    public String toString() {
        return "id: " + id + " name: "+ name + " mood: " + mood + " avgrate: " + avgrate;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TrackModel))
            return false;        
        return this.id == ((TrackModel)obj).getId();
    }
}
