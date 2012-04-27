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
public class MoodModel implements Serializable{
    private int value;
    private String name;

    public MoodModel() {
    }

    public MoodModel(int value, String name) {
        //this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "value: " + value + " name:" + name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MoodModel))
            return false;        
        return this.value == ((MoodModel)obj).getValue();
    }
}
