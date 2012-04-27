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
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Klaritin
 */
@Named(value = "addDialogModelManagedBean")
@SessionScoped
public class AddDialogModelManagedBean implements Serializable {

    @Inject
    private ControllerManagedBean controllerManagedBean;
    private List<MusicianModel> members;
    private GroupModel group;
    private AlbumModel album;
    private TrackModel track;
    private MoodModel mood;
    private GenreModel genre;
    private MusicianModel musician;
    private boolean trackCreated;
    private boolean albumCreated;
    private boolean groupCreated;
    private boolean moodCreated;
    private boolean genreCreated;
    private boolean memberCreated;
    private boolean trackCreate;
    private boolean albumCreate;
    private boolean groupCreate;
    private boolean moodCreate;
    private boolean memberCreate;
    private boolean genreCreate;
    private boolean showed;

    public MusicianModel getMusician() {
        return musician;
    }

    public void setMusician(MusicianModel musician) { //
        members.remove(this.musician);
        members.add(musician);
        this.musician = musician;
    }

    public boolean isGenreCreate() {
        return genreCreate;
    }

    public void setGenreCreate(boolean genreCreate) {
        this.genreCreate = genreCreate;
    }

    public boolean isGenreCreated() {
        return genreCreated;
    }

    public void setGenreCreated(boolean genreCreated) {
        this.genreCreated = genreCreated;
    }

    public boolean isMemberCreate() {
        return memberCreate;
    }

    public void setMemberCreate(boolean memberCreate) {
        this.memberCreate = memberCreate;
    }

    public boolean isMemberCreated() {
        return memberCreated;
    }

    public void setMemberCreated(boolean memberCreated) {
        this.memberCreated = memberCreated;
    }

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
        //track = new TrackModel(0, "", -1, -1);
        //album = new AlbumModel(-1, "", -1, -1);
        //mood = new MoodModel(-1, "");
        group = new GroupModel(-1, "");
        musician = new MusicianModel(-1, "", "");
        members = new ArrayList<MusicianModel>();
        showed = false;
    }

    public AlbumModel getAlbum() {
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU GetALBUM {0}", album);
        return album;
    }

    public GenreModel getGenre() {
        return genre;
    }

    public void setGenre(GenreModel genre) {
        this.genre = genre;
    }

    public void setAlbum(AlbumModel album) {
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU SetALBUM {0}", album);
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
        resetPageModel();
        trackCreate = true;
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU modeTrack mode ={0}", "showed:" + showed);
    }

    public void modeAlbum() {
        resetPageModel();
        albumCreate = true;
    }

    public void modeGroup() {
        resetPageModel();
        groupCreate = true;
    }

    public boolean createTrackP() {
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE TRACKP track mode ={0}", trackCreate);
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

    public boolean createGenreP() {
        return genreCreate;
    }

    public boolean createMusicianP() {
        return memberCreate;
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

    public List<MoodModel> completeMoods(String query) {
        return controllerManagedBean.completeMoods(query, mood);
    }

    public List<AlbumModel> completeAlbums(String query) {
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU current album " + album);
        return controllerManagedBean.completeAlbums(query, album);
    }

    public List<GroupModel> completeGroups(String query) {
        return controllerManagedBean.completeGroups(query, group);
    }

    public List<MusicianModel> completeMusicians(String query) {
        return controllerManagedBean.completeMusician(query, members);
    }

    public List<GenreModel> completeGenres(String query) {
        return controllerManagedBean.completeGenres(query, genre);
    }

    public void backEventHandler() {
        showed = false;
        controllerManagedBean.search();
    }

    public void newAlbumEventHandler() {
        albumCreate = true;
        album = new AlbumModel(-1, "", -1, -1);
        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU newAlbumEventHandler call");
    }

    public void newMoodEventHandler() {
        moodCreate = true;
        mood = new MoodModel(-1, "");
    }

    public void newGroupEventHandler() {
        groupCreate = true;
        group = new GroupModel(-1, "");
        members.clear();
    }

    public void newMemberEventHandler() {
        memberCreate = true;
        musician = new MusicianModel(-1, "", "");
    }

    public void newGenreEventHandler() {
        genreCreate = true;
        genre = new GenreModel(-1, "");
    }

    public void createUpdate() throws Exception {
        long newGroupId = -1;
        long newAlbumId = -1;
        long newTrackId = -1;
        int newMoodValue = -1;
        long newGenreId = -1;
        long newMemberId = -1;
        if (memberCreate) {
            if (!memberCreated) {
                newMemberId = controllerManagedBean.createMusician(musician);
                if (newMemberId != -1) {
                    this.musician.setId(newMemberId);
                    members.add(musician);
                    memberCreated = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Musician " + musician.getName() + " created."));
                }
                Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createUpdate newMemberId ={0}", newMemberId);
            } else {
                MusicianModel oldvalue;
                for (int i = 0; i < members.size(); i++) {
                    oldvalue = members.get(i);
                    if (oldvalue.getId() == musician.getId()) {
                        Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createUpdate found old value.");
                        members.remove(i);
                        members.add(musician);
                        break;
                    }
                }
                Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createUpdate member ={0}", musician + " members: " + members);
                controllerManagedBean.updateMusician(musician);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Musician " + musician.getName() + " saved."));
            }
        }
        if (groupCreate) {
            if (!groupCreated) {
                newGroupId = controllerManagedBean.createGroup(group, members);
                if (newGroupId != -1) {
                    this.group.setId(newGroupId);
                    groupCreated = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Group " + group.getName() + " created."));
                }
                Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createUpdate newGroupId ={0}", newGroupId);
            } else {
                controllerManagedBean.updateGroup(group, members);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Group " + group.getName() + " saved."));
            }
        }
        if (genreCreate) {
            if (!genreCreated) {
                newGenreId = controllerManagedBean.createGenre(genre);
                if (newGenreId != -1) {
                    this.genre.setId(newGenreId);
                    genreCreated = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Genre " + genre.getName() + " created."));
                }
                Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createUpdate newGenreId ={0}", newGenreId);
            } else {
                controllerManagedBean.updateGenre(genre);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Genre " + genre.getName() + " saved."));
            }
        }
        if (albumCreate) {
            if (!albumCreated) {
                newAlbumId = controllerManagedBean.createAlbum(album, genre, group);
                if (newAlbumId != -1) {
                    this.album.setId(newAlbumId);
                    albumCreated = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Album " + album.getName() + " created."));
                }
                Logger.getLogger(AddDialogModelManagedBean.class.getName()).log(Level.INFO, "VLEU createUpdate newAlbumId ={0}", newAlbumId);
            } else {
                controllerManagedBean.updateAlbum(album, genre, group);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Album " + album.getName() + " saved."));
            }
        }
        if (moodCreate) {
            if (!moodCreated) {
                newMoodValue = controllerManagedBean.createMood(mood);
                if (newMoodValue != -1) {
                    moodCreated = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Mood " + mood.getName() + " created."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Unsuccessful", "Mood " + mood.getName() + " already exists."));
                }
            } else {
                controllerManagedBean.updateMood(mood);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Mood " + mood.getName() + " saved."));
            }
        }
        if (trackCreate) {
            if (!trackCreated) {
                newTrackId = controllerManagedBean.createTrack(track, album, mood);
                if (newTrackId != -1) {
                    this.track.setId(newTrackId);
                    trackCreated = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Track " + track.getName() + " created."));
                }
            } else {
                controllerManagedBean.updateTrack(track, album, mood);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Track " + track.getName() + " saved."));
            }
        }
    }

    public String getCreateUpdateButtonName() {
        if ((!trackCreate || trackCreated) && (!albumCreate || albumCreated) && (!groupCreate || groupCreated) && 
            (! genreCreate || genreCreated) && (!memberCreate || memberCreated) && (!moodCreate || moodCreated)) 
            return "Update";
        else 
            return "Create";
    }

    private void resetPageModel() {
        trackCreate = false;
        albumCreate = false;
        groupCreate = false;
        genreCreate = false;
        moodCreate = false;
        memberCreate = false;

        trackCreated = false;
        albumCreated = false;
        groupCreated = false;
        genreCreated = false;
        moodCreated = false;
        memberCreated = false;

        if (!showed) {
            track = new TrackModel(0, "", -1, -1);
            album = null;
            mood = null;
            group = null;
            genre = null;
            musician = null;
            showed = true;
        }
    }

    public void albumSelectHandler(SelectEvent event) {
        albumCreate = false;
        albumCreated = false;
        genreCreate = false;
        genreCreated = false;
        groupCreate = false;
        groupCreated = false;
        memberCreate = false;
        memberCreated = false;
        group = null;
        genre = null;
    }

    public void moodSelectHandler(SelectEvent event) {
        moodCreate = false;
        moodCreated = false;
    }

    public void groupSelectHandler(SelectEvent event) {
        groupCreate = false;
        groupCreated = false;
        memberCreate = false;
        memberCreated = false;
    }

    public void genreSelectHandler(SelectEvent event) {
        genreCreate = false;
        genreCreated = false;
    }
}
