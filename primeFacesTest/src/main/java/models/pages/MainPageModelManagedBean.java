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
import java.util.ArrayList;
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
@Named(value = "mainPageViewManagedBean")
@SessionScoped
public class MainPageModelManagedBean implements Serializable {

    private MusicianModel selectedMusician;
    @Inject
    private ControllerManagedBean controllerManagedBean;

    public MusicianModel getSelectedMusician() {
        Logger.getLogger(MainPageModelManagedBean.class.getName()).log(Level.INFO, "get musician");
        return selectedMusician;
    }

    public void setSelectedMusician(MusicianModel selectedMusician) {
        Logger.getLogger(MainPageModelManagedBean.class.getName()).log(Level.INFO, "set musician " + selectedMusician);
        this.selectedMusician = selectedMusician;
    }
    
    /**
     * Creates a new instance of EditPageViewManagedBean
     */
    public MainPageModelManagedBean() {
    }

    public List<MusicianModel> completeMusicians(String query) {
        List<MusicianModel> selected = new ArrayList<MusicianModel>();
        selected.add(selectedMusician);
        return controllerManagedBean.completeMusician(query, selected);
    }
    
    public List<String> searchMusicianAlbums() {
        Logger.getLogger(MainPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU selectedMusician " + selectedMusician);
        if (selectedMusician != null)
            return controllerManagedBean.searchMusicianAlbums(selectedMusician.getName());
        else
            return null;
    }
    
    public void musicianSelect(SelectEvent event) {
        selectedMusician = (MusicianModel) event.getObject();
        Logger.getLogger(MainPageModelManagedBean.class.getName()).log(Level.INFO, "VLEU selectedMusician " + selectedMusician);
    }
}
