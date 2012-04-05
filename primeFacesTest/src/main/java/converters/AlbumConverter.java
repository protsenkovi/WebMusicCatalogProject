/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import java.io.StringReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import models.entitys.AlbumModel;

/**
 *
 * @author Klaritin
 */
public class AlbumConverter implements Converter {

    /**
     * Creates a new instance of AlbumConverterManagedBean
     */
    public AlbumConverter()  {
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(AlbumConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Album input value of album converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        } 
        Scanner scanner = new Scanner(new StringReader(value)); 
        scanner.useDelimiter("<:>");
        long id = scanner.nextLong();
        String name = scanner.next();
        long group = scanner.nextLong();
        long genre = scanner.nextLong();
        Logger.getLogger(AlbumConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Album converted = {0}", "" + id + name + group + genre);
        return new AlbumModel(id, name, group, genre);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(AlbumConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Album getAsString input value = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return "" + ((AlbumModel) value).getId() + "<:>" 
                      + ((AlbumModel) value).getName() + "<:>"
                      + ((AlbumModel) value).getGroup() + "<:>"
                      + ((AlbumModel) value).getGenre() + "<:>";
        }
    }
}
