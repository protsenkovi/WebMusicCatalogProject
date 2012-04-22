/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import java.io.StringReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import models.entitys.GenreModel;
import models.entitys.MusicianModel;

/**
 *
 * @author Klaritin
 */
public class GenreConverter implements Converter {

    /**
     * Creates a new instance of GroupConverterManagedBean
     */
    public GenreConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(GenreConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Genre input value of Genre converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        }
        try {
            Scanner scanner = new Scanner(new StringReader(value));
            scanner.useDelimiter("<:>");
            int id = scanner.nextInt();
            String name = scanner.next();
            return new GenreModel(id, name);
        } catch (InputMismatchException e) {
            Logger.getLogger(MusicianConverter.class.getName()).log(Level.WARNING, "VLEU CONVERTER input value of genre converter = {0}", value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(GenreConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER getAsString input value of Genre converter = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return "" + ((GenreModel) value).getId() + "<:>"
                    + ((GenreModel) value).getName() + "<:>";
        }
    }
}
