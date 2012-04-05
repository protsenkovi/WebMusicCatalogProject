/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.pages;

import models.entitys.AlbumModel;
import models.entitys.MoodModel;
import models.entitys.TrackModel;
import models.entitys.GroupModel;
import models.entitys.MusicianModel;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
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

    private List<MusicianModel> members;
    private GroupModel group;
    private AlbumModel album;
    private TrackModel track;
    private MoodModel mood;
    @Inject
    private ControllerManagedBean controllerManagedBean;

    public MoodModel getMood() {
        return mood;
    }

    public void setMood(MoodModel mood) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU EditPageModelManagedBean SETTER Mood:  {0}", mood);
        this.mood = mood;
    }
    
    public List<MusicianModel> getMembers() {
        return members;
    }

    public void setMembers(List<MusicianModel> members) {
        this.members = members;
    }

    public void setAlbum(AlbumModel album) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU EditPageModelManagedBean SETTER Album:  {0}", album);
        this.album = album;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
    }

    public void setTrack(TrackModel track) {
        this.track = track;
    }

    public AlbumModel getAlbum() {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU EditPageModelManagedBean GETTER Album:  {0}", album);
        return album;
    }

    public GroupModel getGroup() {
        return group;
    }

    public TrackModel getTrack() {
        return track;
    }

    /**
     * Creates a new instance of EditPageViewManagedBean
     */
    public EditPageModelManagedBean() {
    }

    public void albumChanged(SelectEvent event) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU albumChanged ");
        album = (AlbumModel) event.getObject();
        group = controllerManagedBean.getGroupModelById(album.getGroup());
        members = controllerManagedBean.getMembersByGroupId(group.getId());
        AutoComplete ac = (AutoComplete) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:membersAutoComplete"); //solving problem with update fail
        ac.resetValue();                                                                                                            //after focusing autocomplete
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.WARNING, "VLEU albumChanged: " + ac);
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU Group: " + group + " Album: " + album);
    }

    public void groupChanged(SelectEvent event) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU groupChanged ");
        group = (GroupModel) event.getObject();
        members = controllerManagedBean.getMembersByGroupId(group.getId());
    }
    
    public void moodCompleted() {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU moodCompleted ");
    }

    public List<MusicianModel> completeMusicians(String query) {
        return controllerManagedBean.completeMusician(query, this.members);
    }

    public List<GroupModel> completeGroups(String query) {
        return controllerManagedBean.completeGroups(query, this.group);
    }

    public List<AlbumModel> completeAlbums(String query) {
        return controllerManagedBean.completeAlbums(query, this.album);
    }

    public List<MoodModel> completeMoods(String query) {
        return controllerManagedBean.completeMoods(query, this.mood);
    }
}
