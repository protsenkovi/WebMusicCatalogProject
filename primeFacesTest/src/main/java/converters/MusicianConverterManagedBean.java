/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.mycompany.bmp.Group;
import com.mycompany.bmp.GroupBeanRemoteHome;
import com.mycompany.bmp.Member;
import com.mycompany.bmp.MemberBeanRemoteHome;
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
import view.MusicianModelManagedBean;

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
        } else {
            try {
                long key = Long.parseLong(value);

                InitialContext ic = new InitialContext();
                Object obj = ic.lookup("ejb/MemberBean");
                MemberBeanRemoteHome memberTableHome = (MemberBeanRemoteHome) PortableRemoteObject.narrow(obj, MemberBeanRemoteHome.class);
                if (memberTableHome == null) {
                    return null;
                }
                Member member = memberTableHome.findByPrimaryKey(key);
                Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Musician with id = {0} found.", member.getId());
                return new MusicianModelManagedBean(member.getId(), member.getName(), member.getLink());
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
        Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER getAsString input value of musician converter = {0}", value);
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((MusicianModelManagedBean) value).getId());
        }
    }
}
