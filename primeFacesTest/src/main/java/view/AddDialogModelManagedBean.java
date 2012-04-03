/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.mycompany.bmp.Mood;
import com.mycompany.bmp.MoodBeanRemoteHome;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.FinderException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import logic.ControllerManagedBean;
import org.primefaces.component.panel.Panel;


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
    private GroupModelManagedBean group;
    private AlbumModelManagedBean album;
    private TrackModelManagedBean track;
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
        track = new TrackModelManagedBean(0, "", new MoodModel(-1, ""), -1);
        album = new AlbumModelManagedBean(-1, "", -1, -1);
    }

    public AlbumModelManagedBean getAlbum() {
        return album;
    }

    public void setAlbum(AlbumModelManagedBean album) {
        this.album = album;
    }

    public GroupModelManagedBean getGroup() {
        return group;
    }

    public void setGroup(GroupModelManagedBean group) {
        this.group = group;
    }

    public TrackModelManagedBean getTrack() {
        return track;
    }

    public void setTrack(TrackModelManagedBean track) {
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
    
    
    public void createTrackClicked() throws IOException {
        controllerManagedBean.createTrack();
        trackCreated = true;
        //Panel commandPanel = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:commandPanel");
        //FacesContext context = FacesContext.getCurrentInstance();
        //Logger.getLogger(EditPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU createTrackClicked Panel: " + commandPanel);
        //commandPanel.processDecodes(context);
       // commandPanel.
    }
}
