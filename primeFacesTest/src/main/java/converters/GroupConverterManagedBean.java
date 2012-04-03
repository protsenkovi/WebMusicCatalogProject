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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import view.GroupModelManagedBean;
import view.MusicianModel;

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
        } 
        Scanner scanner = new Scanner(new StringReader(value)); 
        scanner.useDelimiter("<:>");
        long id = scanner.nextLong();
        String name = scanner.next();
        String command = scanner.next();
        List<MusicianModel> members = new ArrayList<MusicianModel>();
        if ("list".equals(command)) {
            long memberId;
            String memberName;
            String memberLink;
            
            while (scanner.hasNext()) {
                memberId = scanner.nextLong();
                memberName = scanner.next();
                memberLink = scanner.next();
                members.add(new MusicianModel(memberId, memberName, memberLink));
            }
        }
        GroupModelManagedBean group = new GroupModelManagedBean(id, name, members);
        Logger.getLogger(GroupConverterManagedBean.class.getName()).log(Level.INFO, "VLEU CONVERTER Album converted = {0}", group);
        return group;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder(); 
            builder.append(((GroupModelManagedBean) value).getId()); builder.append("<:>");
            builder.append(((GroupModelManagedBean) value).getName()); builder.append("<:>");
            builder.append("list"); builder.append("<:>");
            for (MusicianModel member : ((GroupModelManagedBean) value).getMembers()) {
                builder.append(member.getId()); builder.append("<:>");
                builder.append(member.getName()); builder.append("<:>");
                builder.append(member.getLink()); builder.append("<:>");
            }
            Logger.getGlobal().log(Level.INFO, "VLEU CONVERTER Group getAsString {0}", builder);
            return builder.toString();
        }
    }
}
