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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import logic.ControllerManagedBean;
import models.entitys.*;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.event.SelectEvent;
import javax.faces.event.ActionEvent;

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
    private GenreModel genre;
    private MoodModel mood;
    @Inject
    private ControllerManagedBean controllerManagedBean;

    public GenreModel getGenre() {
        return genre;
    }

    public void setGenre(GenreModel genre) {
        this.genre = genre;
    }

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

    public void albumSelected(SelectEvent event) {
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU albumChanged ");
        album = (AlbumModel) event.getObject();
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU album " + album);
        group = controllerManagedBean.getGroupModelById(album.getGroup());
        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU group " + group);
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

    public List<GenreModel> completeGenres(String query) {
        return controllerManagedBean.completeGenres(query, genre);
    }

    public void saveHandler(ActionEvent event) {
        controllerManagedBean.updateGroup(group, members);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Group " + group.getName()+ " saved."));
        controllerManagedBean.updateAlbum(album, genre, group);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Album " + album.getName()+ " saved."));
        controllerManagedBean.updateTrack(track, album, mood);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Track " + track.getName()+ " saved."));
    }
    
//    public void albumValueChanged() {
//        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU album value changed ");
//        AutoComplete ac = (AutoComplete) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:trackAlbums"); //solving problem with update fail
//        ac.resetValue();
//    }
//
//    public void moodValueChanged() {
//        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU mood value changed ");
//        AutoComplete ac = (AutoComplete) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:trackMood"); //solving problem with update fail
//        ac.resetValue();
//        ac.processUpdates(FacesContext.getCurrentInstance());
//    }
//
//    public void genreValueChanged() {
//        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU genre value changed ");
//        AutoComplete ac = (AutoComplete) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:albumGenre"); //solving problem with update fail
//        ac.resetValue();
//    }
//
//    public void groupValueChanged() {
//        Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU group value changed ");
//        AutoComplete ac = (AutoComplete) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:albumGroups"); //solving problem with update fail
//        ac.resetValue();
//    }
}
