/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import logic.ControllerManagedBean;


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
    private boolean trackCreated;
    private boolean albumCreated;
    private boolean groupCreated;
    
    private Mode mode;

    public boolean isAlbumCreated() {
        return albumCreated;
    }

    public void setAlbumCreated(boolean albumCreated) {
        this.albumCreated = albumCreated;
    }

    public boolean isGroupCreated() {
        return groupCreated;
    }

    public void setGroupCreated(boolean groupCreated) {
        this.groupCreated = groupCreated;
    }

    public boolean isTrackCreated() {
        return trackCreated;
    }

    public void setTrackCreated(boolean trackCreated) {
        this.trackCreated = trackCreated;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Creates a new instance of AddDialogModelManagedBean
     */
    public AddDialogModelManagedBean() {
        track = new TrackModel(0, "", -1, -1);
        album = new AlbumModel(-1, "", -1, -1);
    }

    public AlbumModel getAlbum() {
        return album;
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
        mode = Mode.track;
        trackCreated = false;
        albumCreated = false;
        groupCreated = false;
    }

    public void modeAlbum() {
        mode = Mode.album;
    }

    public void modeGroup() {
        mode = Mode.group;
    }

    public boolean isTrackR() {
        return mode == Mode.track;
    }

    public boolean isAlbumR() {
        return mode == Mode.album;
    }

    public boolean isGroupR() {
        return mode == Mode.group;
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
        //controllerManagedBean.createTrack();
        //trackCreated = true;
        //Panel commandPanel = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:commandPanel");
        //FacesContext context = FacesContext.getCurrentInstance();
        //Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU createTrackClicked Panel: " + commandPanel);
        //commandPanel.processDecodes(context);
       // commandPanel.
    }
}
