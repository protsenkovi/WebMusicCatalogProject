/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.mycompany.bmp.*;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.FinderException;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import view.MoodModel;
import view.MusicianModel;

/**
 *
 * @author Klaritin
 */
@Named(value = "moodConverterManagedBean")
@ApplicationScoped
public class MoodConverterManagedBean implements Converter {

    /**
     * Creates a new instance of GroupConverterManagedBean
     */
    public MoodConverterManagedBean() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Mood input value of Mood converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        }
        Scanner scanner = new Scanner(new StringReader(value)); 
        scanner.useDelimiter("<:>");
        int id = scanner.nextInt();
        String name = scanner.next();
        return new MoodModel(id, name);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Mood getAsString input value of Mood converter = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return "" + ((MoodModel) value).getValue() + "<:>" + ((MoodModel) value).getName() + "<:>";
        }
    }
}
