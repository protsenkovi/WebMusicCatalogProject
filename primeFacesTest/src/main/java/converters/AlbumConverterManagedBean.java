/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.mycompany.bmp.Album;
import com.mycompany.bmp.AlbumBeanRemoteHome;
import com.mycompany.bmp.Group;
import com.mycompany.bmp.GroupBeanRemoteHome;
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
        } else {
            try {
                long key = Long.parseLong(value);

                InitialContext ic = new InitialContext();
                Object obj = ic.lookup("ejb/AlbumBean");
                AlbumBeanRemoteHome albumHome = (AlbumBeanRemoteHome) PortableRemoteObject.narrow(obj, AlbumBeanRemoteHome.class);
                if (albumHome == null) {
                    return null;
                }
                Album album = albumHome.findByPrimaryKey(key);
                Logger.getLogger(AlbumConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Album with id = {0} found.", album.getId());
                return new AlbumModelManagedBean(album.getId(), album.getName(), album.getGenre().longValue(), album.getGroup());
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid group"));
            } catch (NamingException ex) {
                Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FinderException ex) {
                Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Logger.getLogger(AlbumConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Album getAsString input value = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((AlbumModelManagedBean) value).getId());
        }
    }
}
