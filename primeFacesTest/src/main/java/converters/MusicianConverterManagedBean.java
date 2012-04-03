/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.mycompany.bmp.Group;
import com.mycompany.bmp.GroupBeanRemoteHome;
import com.mycompany.bmp.Member;
import com.mycompany.bmp.MemberBeanRemoteHome;
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
import view.MusicianModel;

/**
 *
 * @author Klaritin
 */
@Named(value = "musicianConverterManagedBean")
@ApplicationScoped
public class MusicianConverterManagedBean implements Converter {

    /**
     * Creates a new instance of GroupConverterManagedBean
     */
    public MusicianConverterManagedBean() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Musician input value of musician converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        } 
        Scanner scanner = new Scanner(new StringReader(value)); 
        scanner.useDelimiter("<:>");
        int id = scanner.nextInt();
        String name = scanner.next();
        String link = scanner.next();
        return new MusicianModel(id, name, link);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER getAsString input value of musician converter = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return "" + ((MusicianModel) value).getId() + "<:>"
                      + ((MusicianModel) value).getName() + "<:>"
                      + ((MusicianModel) value).getLink() + "<:>";
        }
    }
}
