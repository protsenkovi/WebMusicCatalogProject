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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import models.entitys.MoodModel;

/**
 *
 * @author Klaritin
 */
public class MoodConverter implements Converter {

    /**
     * Creates a new instance of GroupConverterManagedBean
     */
    public MoodConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(MoodConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Mood input value of Mood converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        }
        try {
            Scanner scanner = new Scanner(new StringReader(value));
            scanner.useDelimiter("<:>");
            int id = scanner.nextInt();
            String name = scanner.next();
            return new MoodModel(id, name);
        } catch (InputMismatchException e) {
            Logger.getLogger(MusicianConverter.class.getName()).log(Level.WARNING, "VLEU CONVERTER input value of mood converter = {0}", value);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mood has no been selected from list!"));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(MoodConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Mood getAsString input value of Mood converter = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return "" + ((MoodModel) value).getValue() + "<:>" + ((MoodModel) value).getName() + "<:>";
        }
    }
}
