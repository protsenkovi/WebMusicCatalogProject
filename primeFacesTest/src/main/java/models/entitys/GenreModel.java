/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

/**
 *
 * @author Klaritin
 */
public class GenreModel {
    private long id;
    private String name;

    public GenreModel() {
    }

    public GenreModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
