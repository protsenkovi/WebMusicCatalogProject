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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import view.GroupModelManagedBean;
import view.MusicianModelManagedBean;

/**
 *
 * @author Klaritin
 */
@Named(value = "groupConverterManagedBean")
@ApplicationScoped
public class GroupConverterManagedBean implements Converter {

    /**
     * Creates a new instance of GroupConverterManagedBean
     */
    public GroupConverterManagedBean() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER input value of group converter = {0}", value);
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                long key = Long.parseLong(value);

                InitialContext ic = new InitialContext();
                Object obj = ic.lookup("ejb/GroupBean");
                GroupBeanRemoteHome groupTableHome = (GroupBeanRemoteHome) PortableRemoteObject.narrow(obj, GroupBeanRemoteHome.class);
                if (groupTableHome == null) {
                    return null;
                }
                Group group = groupTableHome.findByPrimaryKey(key);
                Logger.getLogger(MusicianConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Group with id = {0} found.", group.getId());
                List<MusicianModelManagedBean> members = new ArrayList<MusicianModelManagedBean>();
                obj = ic.lookup("ejb/MemberBean");
                MemberBeanRemoteHome memberHome = (MemberBeanRemoteHome) PortableRemoteObject.narrow(obj, MemberBeanRemoteHome.class);
                Collection memberst = memberHome.findByGroup(group.getId());
                for (Object membert : memberst) {
                    Member member = (Member) membert;
                    members.add(new MusicianModelManagedBean(member.getId(), member.getName(), member.getLink()));
                }
                Logger.getGlobal().log(Level.INFO, "VLEU CONVERTER Group members {0}", members);
                return new GroupModelManagedBean(group.getId(), group.getName(), members);
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
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((GroupModelManagedBean) value).getId());
        }
    }
}
