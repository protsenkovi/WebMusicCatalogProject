/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.mycompany.bmp.*;
import java.rmi.RemoteException;
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
import view.MusicianModelManagedBean;

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
        } else {
            try {
                int key = Integer.parseInt(value);

                InitialContext ic = new InitialContext();
                Object obj = ic.lookup("ejb/MoodBean");
                MoodBeanRemoteHome moodHome = (MoodBeanRemoteHome) PortableRemoteObject.narrow(obj, MoodBeanRemoteHome.class);
                if (moodHome != null) {
                    Mood mood = (Mood) moodHome.findByPrimaryKey(key);
                    Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Mood with id = {0} found.", mood.getId());
                    return new MoodModel(mood.getId(), mood.getName());
                }                
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid group"));
            } catch (NamingException ex) {
                Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FinderException ex) {
                Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(MoodConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Mood getAsString input value of Mood converter = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((MoodModel) value).getValue());
        }
    }
}
