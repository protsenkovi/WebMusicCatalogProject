/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.pages;

import models.entitys.AlbumModel;
import models.entitys.MusicianModel;
import models.entitys.MoodModel;
import models.entitys.GroupModel;
import models.entitys.TrackModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import logic.ControllerManagedBean;
import models.entitys.*;
import sun.rmi.runtime.Log;

/**
 *
 * @author Klaritin
 */
@Named(value = "addDialogModelManagedBean")
@SessionScoped
public class AddDialogModelManagedBean implements Serializable {

    public static enum Mode {

        track, album, group
    }
    @Inject
    private ControllerManagedBean controllerManagedBean;
    private List<MusicianModel> members;
    private GroupModel group;
    private AlbumModel album;
    private TrackModel track;
    private MoodModel mood;
    private GenreModel genre;
    private boolean trackCreated;
    private boolean albumCreated;
    private boolean groupCreated;
    private boolean moodCreated;
    
    
    private boolean trackCreate;
    private boolean albumCreate;
    private boolean groupCreate;
    private boolean moodCreate;
    private boolean memberCreate;
    private boolean showed;

    public boolean isMoodCreated() {
        return moodCreated;
    }

    public void setMoodCreated(boolean moodCreated) {
        this.moodCreated = moodCreated;
    }
  
    public boolean isAlbumCreate() {
        return albumCreate;
    }

    public void setAlbumCreate(boolean albumCreate) {
        this.albumCreate = albumCreate;
    }

    public boolean isAlbumCreated() {
        return albumCreated;
    }

    public void setAlbumCreated(boolean albumCreated) {
        this.albumCreated = albumCreated;
    }

    public boolean isGroupCreate() {
        return groupCreate;
    }

    public void setGroupCreate(boolean groupCreate) {
        this.groupCreate = groupCreate;
    }

    public boolean isGroupCreated() {
        return groupCreated;
    }

    public void setGroupCreated(boolean groupCreated) {
        this.groupCreated = groupCreated;
    }

    public boolean isMoodCreate() {
        return moodCreate;
    }

    public void setMoodCreate(boolean moodCreate) {
        this.moodCreate = moodCreate;
    }

    public boolean isTrackCreate() {
        return trackCreate;
    }

    public void setTrackCreate(boolean trackCreate) {
        this.trackCreate = trackCreate;
    }

    public boolean isTrackCreated() {
        return trackCreated;
    }

    public void setTrackCreated(boolean trackCreated) {
        this.trackCreated = trackCreated;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    /**
     * Creates a new instance of AddDialogModelManagedBean
     */
    public AddDialogModelManagedBean() {
        track = new TrackModel(0, "", -1, -1);
        album = new AlbumModel(-1, "", -1, -1);
        mood = new MoodModel(-1, "");
        group = new GroupModel(-1, "");
        showed = false;
    }

    public AlbumModel getAlbum() {
        return album;
    }

    public GenreModel getGenre() {
        return genre;
    }

    public void setGenre(GenreModel genre) {
        this.genre = genre;
    }

    public void setAlbum(AlbumModel album) {
        this.album = album;
    }

    public GroupModel getGroup() {
        return group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
    }

    public TrackModel getTrack() {
        return track;
    }

    public void setTrack(TrackModel track) {
        this.track = track;
    }

    public void modeTrack() {
        trackCreate = true;
        if (!showed) {
            track = new TrackModel(0, "", -1, -1);
            album = new AlbumModel(-1, "", -1, -1);
            mood = new MoodModel(-1, "");
            group = new GroupModel(-1, "");
            showed = true;
        }
        albumCreate = false;
        groupCreate = false;
        trackCreated = false;
        albumCreated = false;
        groupCreated = false;
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU modeTrack mode ={0}", "showed:" + showed);
    }

    public void modeAlbum() {
        albumCreate = true;
    }

    public void modeGroup() {
        groupCreate = true;
    }

    public boolean createTrackP() {
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createTrackP track mode ={0}", trackCreate);
        return trackCreate;
    }

    public boolean createAlbumP() {
        return albumCreate;
    }

    public boolean createGroupP() {
        return groupCreate;
    }

    public boolean createMoodP() {
        return moodCreate;
    }

    public List<MusicianModel> getMembers() {
        return members;
    }

    public void setMembers(List<MusicianModel> members) {
        this.members = members;
    }

    public MoodModel getMood() {
        return mood;
    }

    public void setMood(MoodModel mood) {
        this.mood = mood;
    }

    public void createTrackClicked() throws IOException {
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createTrackClicked Started! Showed ={0}", showed);
        if (showed) {
            createUpdate();
            trackCreated = true;
        }
        //Panel commandPanel = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:commandPanel");
        //FacesContext context = FacesContext.getCurrentInstance();
        //Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU createTrackClicked Panel: " + commandPanel);
        //commandPanel.processDecodes(context);
        // commandPanel.
    }

    public List<MoodModel> completeMoods(String query) {
        return controllerManagedBean.completeMoods(query, mood);
    }

    public List<AlbumModel> completeAlbums(String query) {
        return controllerManagedBean.completeAlbums(query, album);
    }

    public List<GroupModel> completeGroups(String query) {
        return controllerManagedBean.completeGroups(query, group);
    }

    public List<MusicianModel> completeMusicians(String query) {
        return controllerManagedBean.completeMusician(query, members);
    }

    public void backEventHandler() {
        showed = false;
        controllerManagedBean.search();
    }

    public void newAlbumEventHandler() {
        albumCreate = true;
    }

    public void newMoodEventHandler() {
        moodCreate = true;
    }

    public void newGroupEventHandler() {
        groupCreate = true;
    }
    
    public void newMemberEventHandler() {
        memberCreate = true;
    }

    public void createUpdate() {
        if (trackCreate) {
            if (!trackCreated) {
                controllerManagedBean.createTrack(track, album, mood);
                trackCreated = true;
            } else {
                controllerManagedBean.updateTrack(track, album, mood);
            }
        }
        if (albumCreate) {
            if (!albumCreated) {
                controllerManagedBean.createAlbum(album, genre, group);
                albumCreated = true;
            } else {
                controllerManagedBean.updateAlbum(album, genre, group);
            }
        }
//        if (groupCreate) {
//            if (!groupCreated) 
//                
//        }
    }

    public String getCreateUpdateButtonName() {
        if ((trackCreate && trackCreated) && (albumCreate && albumCreated) && (groupCreate && groupCreated)) {
            return "Update";
        } else {
            if ((trackCreate && trackCreated) || (albumCreate && albumCreated) || (groupCreate && groupCreated)) {
                return "Create and Update";
            }
            else 
                return "Create";
        }
    }
}
