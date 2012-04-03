/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import logic.ControllerManagedBean;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;


/**
 *
 * @author Klaritin
 */
@Named(value = "editPageViewManagedBean")
@SessionScoped
public class EditPageModelManagedBean implements Serializable {

    private GroupModelManagedBean group;
    private AlbumModelManagedBean album;
    private TrackModelManagedBean track;
    
    @Inject
    private ControllerManagedBean controllerManagedBean;

    public void setAlbum(AlbumModelManagedBean album) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU EditPageModelManagedBean SETTER Album:  {0}", album);
        this.album = album;
    }

    public void setGroup(GroupModelManagedBean group) {
        this.group = group;
    }

    public void setTrack(TrackModelManagedBean track) {
        this.track = track;
    }

    public AlbumModelManagedBean getAlbum() {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU EditPageModelManagedBean GETTER Album:  {0}", album);
        return album;
    }

    public GroupModelManagedBean getGroup() {
        return group;
    }

    public TrackModelManagedBean getTrack() {
        return track;
    }
    
    /**
     * Creates a new instance of EditPageViewManagedBean
     */
    public EditPageModelManagedBean() {                
    }
    
    public void albumChanged(SelectEvent event) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU albumChanged ");
        album = (AlbumModelManagedBean)event.getObject();
        group = controllerManagedBean.getGroupModelById(album.getGroup()); 
        AutoComplete ac = (AutoComplete) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:membersAutoComplete"); //solving problem with update fail
        ac.resetValue();                                                                                                            //after focusing autocomplete
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.WARNING, "VLEU albumChanged: " + ac);
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU Group: " + group + " Album: " + album);
    }
    
    public void groupChanged(SelectEvent event) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU groupChanged ");
        group = (GroupModelManagedBean) event.getObject();
    }
    
    public void membersDeleted(UnselectEvent event) {
        group.getMembers().remove((MusicianModel)event.getObject());
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU membersDeleted {0}", group.getMembers());
    }
    
    public void membersAdded(SelectEvent event) {
        group.getMembers().add((MusicianModel)event.getObject());
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU membersAdded {0}", group.getMembers());
    }
    
    public List<MusicianModel> completeMusicians(String query) {
        return controllerManagedBean.completeMusician(query, group.getMembers());
    }
    
    public List<GroupModelManagedBean> completeGroups(String query) {
        return controllerManagedBean.completeGroups(query, group);
    }
    
    public List<AlbumModelManagedBean> completeAlbums(String query) {
        return controllerManagedBean.completeAlbums(query, album);
    }
    
    public List<MoodModel> completeMoods(String query) {
        return controllerManagedBean.completeMoods(query, track.getMood());
    }
}
