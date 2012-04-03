/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.mycompany.bmp.Album;
import com.mycompany.bmp.AlbumBeanRemoteHome;
import com.mycompany.bmp.Group;
import com.mycompany.bmp.GroupBeanRemoteHome;
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
import view.AlbumModelManagedBean;
import view.GroupModelManagedBean;

/**
 *
 * @author Klaritin
 */
@Named(value = "albumConverterManagedBean")
@ApplicationScoped
public class AlbumConverterManagedBean implements Converter {

    /**
     * Creates a new instance of AlbumConverterManagedBean
     */
    public AlbumConverterManagedBean()  {
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(AlbumConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Album input value of album converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        } 
        Scanner scanner = new Scanner(new StringReader(value)); 
        scanner.useDelimiter("<:>");
        long id = scanner.nextLong();
        String name = scanner.next();
        long group = scanner.nextLong();
        long genre = scanner.nextLong();
        Logger.getLogger(AlbumConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Album converted = {0}", "" + id + name + group + genre);
        return new AlbumModelManagedBean(id, name, group, genre);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(AlbumConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Album getAsString input value = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return "" + ((AlbumModelManagedBean) value).getId() + "<:>" 
                      + ((AlbumModelManagedBean) value).getName() + "<:>"
                      + ((AlbumModelManagedBean) value).getGroup() + "<:>"
                      + ((AlbumModelManagedBean) value).getGenre() + "<:>";
        }
    }
}
