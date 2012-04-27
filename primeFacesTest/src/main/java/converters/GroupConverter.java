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
import models.entitys.GroupModel;

/**
 *
 * @author Klaritin
 */
public class GroupConverter implements Converter {

    /**
     * Creates a new instance of GroupConverterManagedBean
     */
    public GroupConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(MusicianConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER input value of group converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        }
        try {
            Scanner scanner = new Scanner(new StringReader(value));
            scanner.useDelimiter("<:>");
            long id = scanner.nextLong();
            String name = scanner.next();
            GroupModel group = new GroupModel(id, name);
            Logger.getLogger(GroupConverter.class.getName()).log(Level.INFO, "VLEU CONVERTER Group converted = {0}", group);
            return group;
        } catch (InputMismatchException e) {
            Logger.getLogger(MusicianConverter.class.getName()).log(Level.WARNING, "VLEU CONVERTER input value of group converter = {0}", value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(((GroupModel) value).getId());
            builder.append("<:>");
            builder.append(((GroupModel) value).getName());
            builder.append("<:>");
            Logger.getGlobal().log(Level.INFO, "VLEU CONVERTER Group getAsString {0}", builder);
            return builder.toString();
        }
    }
}
