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
@Named(value = "trackModelManagedBean")
@SessionScoped
public class TrackModelManagedBean implements Serializable {

    private long id;
    private String name;
    private MoodModel mood;
    private double avgrate;

    public void setAvgrate(double avgrate) {
        this.avgrate = avgrate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MoodModel getMood() {
        return mood;
    }

    public void setMood(MoodModel mood) {
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

    public TrackModelManagedBean(long id, String name, MoodModel mood, double avgrate) {
        this.id = id;
        this.name = name;
        this.mood = mood;
        this.avgrate = avgrate;
    }
    
    /**
     * Creates a new instance of TrackModelManagedBean
     */
    public TrackModelManagedBean() {
        this.mood = new MoodModel();
    }

    @Override
    public String toString() {
        return "id: " + id + " name: "+ name + " mood: " + mood + " avgrate: " + avgrate;
    }
}
