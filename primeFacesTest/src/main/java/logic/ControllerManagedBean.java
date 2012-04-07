/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import models.entitys.BindedModel;
import models.entitys.AlbumModel;
import models.entitys.GroupModel;
import models.entitys.MusicianModel;
import models.pages.AddDialogModelManagedBean;
import models.entitys.MoodModel;
import models.pages.EditPageModelManagedBean;
import models.entitys.TrackModel;
import com.mycompany.bmp.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import models.entitys.*;

/**
 *
 * @author Klaritin
 */
@Named(value = "controllerManagedBean")
@SessionScoped
public class ControllerManagedBean implements Serializable {

    private List<BindedModel> maintable;
    private BindedModel[] selectedRows; // why array?
    @Inject
    private EditPageModelManagedBean editPageViewManagedBean;
    @Inject
    private AddDialogModelManagedBean addDialogModelManagedBean;
    private String searchString;

    public BindedModel getSelectedRow() {
        if (selectedRows == null) {
            return null;
        }
        return selectedRows[0];
    }

    public void setSelectedRow(BindedModel selectedRow) {
        if (selectedRows == null) {
            return;
        }
        this.selectedRows = new BindedModel[1];
        this.selectedRows[0] = selectedRow;
    }

    public BindedModel[] getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(BindedModel[] selectedRows) {
        this.selectedRows = selectedRows;
    }

    public List<BindedModel> getMaintable() {
        return maintable;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * Creates a new instance of ControllerManagedBean
     */
    public ControllerManagedBean() {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CONSTRUCTOR ControllerManagedBean!");
        searchString = "";
        maintable = new ArrayList<BindedModel>();
    }

    public void search() {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SEARCH: Started!");
        maintable.clear();
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/BindedTableBean");
            BindedTableBeanRemoteHome bindedTableHome = (BindedTableBeanRemoteHome) PortableRemoteObject.narrow(obj, BindedTableBeanRemoteHome.class);
            if (bindedTableHome == null) {
                maintable.add(new BindedModel());
            } else {
                Collection colByGroup = bindedTableHome.findByGroup(searchString);
                Collection colByAlbum = bindedTableHome.findByAlbum(searchString);
                Collection colByTrack = bindedTableHome.findByTrack(searchString);

                colByGroup.removeAll(colByTrack);
                colByTrack.removeAll(colByAlbum);
                colByGroup.addAll(colByTrack);

                colByGroup.removeAll(colByAlbum);
                colByGroup.addAll(colByAlbum);


                for (Object groupt : colByGroup) {
                    com.mycompany.bmp.BindedTable bt = (com.mycompany.bmp.BindedTable) groupt;
                    maintable.add(new BindedModel(bt.getIdTrack(),
                            bt.getIdAlbum(),
                            bt.getIdGroup(),
                            bt.getIdGenre(),
                            bt.getTrack(),
                            bt.getAlbum(),
                            bt.getGroup(),
                            bt.getAvgRate(),
                            bt.getIdMood(),
                            bt.getMood(),
                            bt.getGenre()));
                }
                Collections.sort(maintable);
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SEARCH: Ended!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MusicianModel> completeMusician(String query, List<MusicianModel> alreadyChoosen) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMusician: Started!");
        List<MusicianModel> suggestions = new ArrayList<MusicianModel>();
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/MemberBean");
            MemberBeanRemoteHome memberTableHome = (MemberBeanRemoteHome) PortableRemoteObject.narrow(obj, MemberBeanRemoteHome.class);
            if (memberTableHome == null) {
                suggestions.add(new MusicianModel());
            } else {
                Collection col = memberTableHome.findByName(query);

                for (Object membert : col) {
                    com.mycompany.bmp.Member member = (com.mycompany.bmp.Member) membert;
                    suggestions.add(new MusicianModel(member.getId(), member.getName(), member.getLink()));
                }
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMusician: Ended!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMusician: alreadyChoosen: {0}", alreadyChoosen);
        if (alreadyChoosen != null) {
            suggestions.removeAll(alreadyChoosen);
        }
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMusician: Final {0}", suggestions);
        return suggestions;
    }

    public List<GroupModel> completeGroups(String query, GroupModel alreadyChoosen) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeGroups: Started!");
        List<GroupModel> suggestions = new ArrayList<GroupModel>();
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/GroupBean");
            GroupBeanRemoteHome groupHome = (GroupBeanRemoteHome) PortableRemoteObject.narrow(obj, GroupBeanRemoteHome.class);
            if (groupHome != null) {
                Collection col;
                if ("".equals(query)) {
                    col = groupHome.findAll();
                } else {
                    col = groupHome.findByName(query);
                }

                for (Object groupt : col) {
                    com.mycompany.bmp.Group group = (com.mycompany.bmp.Group) groupt;
                    List<MusicianModel> members = new ArrayList<MusicianModel>();
                    obj = ic.lookup("ejb/MemberBean");
                    MemberBeanRemoteHome memberHome = (MemberBeanRemoteHome) PortableRemoteObject.narrow(obj, MemberBeanRemoteHome.class);
                    Collection memberst = memberHome.findByGroup(group.getId());
                    for (Object membert : memberst) {
                        Member member = (Member) membert;
                        members.add(new MusicianModel(member.getId(), member.getName(), member.getLink()));
                    }
                    suggestions.add(new GroupModel(group.getId(), group.getName()));
                }
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeGroups: Ended!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (alreadyChoosen != null) {
            suggestions.remove(alreadyChoosen);
        }
        return suggestions;
    }

    public List<AlbumModel> completeAlbums(String query, AlbumModel alreadyChoosen) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeAlbums: Started!");
        List<AlbumModel> suggestions = new ArrayList<AlbumModel>();
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/AlbumBean");
            AlbumBeanRemoteHome albumHome = (AlbumBeanRemoteHome) PortableRemoteObject.narrow(obj, AlbumBeanRemoteHome.class);
            if (albumHome != null) {
                Collection col;
                if ("".equals(query)) {
                    col = albumHome.findAll();
                } else {
                    col = albumHome.findByName(query);
                }

                for (Object albumt : col) {
                    com.mycompany.bmp.Album album = (com.mycompany.bmp.Album) albumt;
                    suggestions.add(new AlbumModel(album.getId(), album.getName(), album.getGenre().longValue(), album.getGroup())); //danger! not unified model creation
                }
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeAlbums: Ended!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (alreadyChoosen != null) {
            suggestions.remove(alreadyChoosen);
        }
        return suggestions;
    }

    public List<MoodModel> completeMoods(String query, MoodModel alreadyChoosen) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMoods: Started!");
        List<MoodModel> suggestions = new ArrayList<MoodModel>();
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/MoodBean");
            MoodBeanRemoteHome moodHome = (MoodBeanRemoteHome) PortableRemoteObject.narrow(obj, MoodBeanRemoteHome.class);
            if (moodHome != null) {
                Collection col;
                if ("".equals(query)) {
                    col = moodHome.findAll();
                } else {
                    col = moodHome.findByName(query);
                }

                for (Object moodt : col) {
                    Mood mood = (Mood) moodt;
                    suggestions.add(new MoodModel(mood.getId(), mood.getName()));
                }
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMoods: Ended!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeMoods: alreadyChoosen: {0}", alreadyChoosen);
        if (alreadyChoosen != null) {
            suggestions.remove(alreadyChoosen);
        }
        return suggestions;
    }

    public List<GenreModel> completeGenres(String query, GenreModel alreadyChoosen) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeGenres: Started!");
        List<GenreModel> suggestions = new ArrayList<GenreModel>();
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/GenreBean");
            GenreBeanRemoteHome genreHome = (GenreBeanRemoteHome) PortableRemoteObject.narrow(obj, GenreBeanRemoteHome.class);
            if (genreHome != null) {
                Collection col;
                if ("".equals(query)) {
                    col = genreHome.findAll();
                } else {
                    col = genreHome.findByName(query);
                }

                for (Object genret : col) {
                    Genre genre = (Genre) genret;
                    suggestions.add(new GenreModel(genre.getId(), genre.getName()));
                }
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeGenres: Ended!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU completeGenres: alreadyChoosen: {0}", alreadyChoosen);
        if (alreadyChoosen != null) {
            suggestions.remove(alreadyChoosen);
        }
        return suggestions;
    }

    public void save() {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: -------Saving group-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/GroupBean");
            GroupBeanRemoteHome groupHome = (GroupBeanRemoteHome) PortableRemoteObject.narrow(obj, GroupBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: groupHome = {0}", groupHome);
            if (groupHome != null) {
                GroupModel groupModel = editPageViewManagedBean.getGroup();
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: groupModel = {0}", groupModel);
                Group group = groupHome.findByPrimaryKey(groupModel.getId());
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: group = {0}", group.getId());
                group.setName(groupModel.getName());

                Collection membersIds = new Vector();
                for (MusicianModel member : editPageViewManagedBean.getMembers()) {
                    Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Group id: " + groupModel.getId() + " Member id: " + member.getId() + " name: " + member.getName() + " added to list");
                    membersIds.add(new Long(member.getId()));
                }
                group.setMembers(membersIds);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Group has not been saved!"));
            }

            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: -------Saving album-------");
            obj = ic.lookup("ejb/AlbumBean");
            AlbumBeanRemoteHome albumHome = (AlbumBeanRemoteHome) PortableRemoteObject.narrow(obj, AlbumBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: albumHome = {0}", albumHome);
            if (albumHome != null) {
                AlbumModel albumModel = editPageViewManagedBean.getAlbum();
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: albumModel = {0}", albumModel);
                Album album = albumHome.findByPrimaryKey(albumModel.getId());
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: album = {0}", album.getId());
                album.setName(albumModel.getName());
                album.setGroup(editPageViewManagedBean.getGroup().getId());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Album has not been saved!"));
            }

            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: -------Saving track-------");
            obj = ic.lookup("ejb/TrackBean");
            TrackBeanRemoteHome trackHome = (TrackBeanRemoteHome) PortableRemoteObject.narrow(obj, TrackBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: trackHome = {0}", trackHome);
            if (trackHome != null) {
                TrackModel trackModel = editPageViewManagedBean.getTrack();
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: trackModel = {0}", trackModel);
                Track track = trackHome.findByPrimaryKey(trackModel.getId());
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: track = {0}", track.getId());
                track.setName(trackModel.getName());
                track.setAlbum(editPageViewManagedBean.getAlbum().getId());
                track.setMood(editPageViewManagedBean.getMood().getValue());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Track has not been saved!"));
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Ended!!!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long createTrack(TrackModel trackModel, AlbumModel albumModel, MoodModel moodModel) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE TRACK: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: -------Creating track-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/TrackBean");
            TrackBeanRemoteHome trackHome = (TrackBeanRemoteHome) PortableRemoteObject.narrow(obj, TrackBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: trackHome = {0}", trackHome);
            if (trackHome != null) {
                Track track = trackHome.create(trackModel.getName(), albumModel.getId(), moodModel.getValue());
                return track.getId().longValue();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Track has not been created!"));
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE TRACK: Ended!!!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void updateTrack(TrackModel trackModel, AlbumModel albumModel, MoodModel moodModel) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: -------Saving track-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/TrackBean");
            TrackBeanRemoteHome trackHome = (TrackBeanRemoteHome) PortableRemoteObject.narrow(obj, TrackBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: trackHome = {0}", trackHome);
            if (trackHome != null) {
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: trackModel = {0}", trackModel);
                Track track = trackHome.findByPrimaryKey(trackModel.getId());
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: track = {0}", track.getId());
                track.setName(trackModel.getName());
                track.setAlbum(albumModel.getId());
                track.setMood(moodModel.getValue());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Track has not been saved!"));
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Ended!!!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long createAlbum(AlbumModel albumModel, GenreModel genreModel, GroupModel groupModel) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE ALBUM: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: -------Creating album-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/AlbumBean");
            AlbumBeanRemoteHome albumHome = (AlbumBeanRemoteHome) PortableRemoteObject.narrow(obj, AlbumBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: albumHome = {0}", albumHome);
            if (albumHome != null) {
                Album album = albumHome.create(albumModel.getName(), groupModel.getId(), genreModel.getId());
                return album.getId().longValue();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Album has not been created!"));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE ALBUM: Ended!!!");

        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; //default value?
    }

    public void updateAlbum(AlbumModel albumModel, GenreModel genreModel, GroupModel groupModel) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: -------Saving album-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/AlbumBean");
            AlbumBeanRemoteHome albumHome = (AlbumBeanRemoteHome) PortableRemoteObject.narrow(obj, AlbumBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: albumHome = {0}", albumHome);
            if (albumHome != null) {
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: albumModel = {0}", albumModel);
                Album album = albumHome.findByPrimaryKey(albumModel.getId());
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: album = {0}", album.getId());
                album.setName(albumModel.getName());
                album.setGroup(groupModel.getId());
                album.setGenre(genreModel.getId());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Track has not been saved!"));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: Ended!!!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int createMood(MoodModel moodModel) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE MOOD: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: -------Creating mood-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/MoodBean");
            MoodBeanRemoteHome moodHome = (MoodBeanRemoteHome) PortableRemoteObject.narrow(obj, MoodBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: moodHome = {0}", moodHome);
            if (moodHome != null) {
                Mood mood = moodHome.create(moodModel.getValue(), moodModel.getName());
                return mood.getId().intValue();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mood has not been created!"));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE MOOD: Ended!!!");

        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; //default value?
    }

    public long createGroup(GroupModel groupModel, List<MusicianModel> members) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE GROUP: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: -------Creating group-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/GroupBean");
            GroupBeanRemoteHome groupHome = (GroupBeanRemoteHome) PortableRemoteObject.narrow(obj, GroupBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: groupHome = {0}", groupHome);
            if (groupHome != null) {
                Collection col = new Vector();
                for (MusicianModel member : members) {
                    col.add(member.getId());
                }
                Group group = groupHome.create(groupModel.getId(), groupModel.getName(), col);
                return group.getId().longValue();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mood has not been created!"));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE MOOD: Ended!!!");

        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; //default value?
    }
    
    public long createGenre(GenreModel genreModel) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE GENRE: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE: -------Creating genre-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/GenreBean");
            GenreBeanRemoteHome genreHome = (GenreBeanRemoteHome) PortableRemoteObject.narrow(obj, GenreBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: genreHome = {0}", genreHome);
            if (genreHome != null) {
                Genre genre = genreHome.create(genreModel.getName());
                return genre.getId().longValue();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Genre has not been created!"));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE GENRE: Ended!!!");

        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; //default value?
    }

//    @SuppressWarnings("unchecked")
//    public static <T> T findBean(String beanName) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
//    }
    public String edit() {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU EDIT: Started!");
        if (selectedRows == null) {
            return "";
        }
        List<MusicianModel> members = new ArrayList<MusicianModel>();
        MoodModel moodModel = null;
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/MemberBean");
            MemberBeanRemoteHome memberHome = (MemberBeanRemoteHome) PortableRemoteObject.narrow(obj, MemberBeanRemoteHome.class);
            if (memberHome != null) {
                Collection col = memberHome.findByGroup(selectedRows[0].getGroupid());
                for (Object membert : col) {
                    com.mycompany.bmp.Member member = (com.mycompany.bmp.Member) membert;
                    Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU EDIT: Member {0}  ", member.getName());
                    members.add(new MusicianModel(member.getId(), member.getName(), member.getLink()));
                }
            }

            obj = ic.lookup("ejb/MoodBean");
            MoodBeanRemoteHome moodHome = (MoodBeanRemoteHome) PortableRemoteObject.narrow(obj, MoodBeanRemoteHome.class);
            if (moodHome != null) {
                Mood mood = (Mood) moodHome.findByPrimaryKey(selectedRows[0].getMood());
                moodModel = new MoodModel(mood.getId(), mood.getName());
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (editPageViewManagedBean == null) {
            Logger.getGlobal().log(Level.SEVERE, "VLEU editEntitys is null");
        }
        GroupModel group = new GroupModel(selectedRows[0].getGroupid(), selectedRows[0].getGroupName());
        AlbumModel album = new AlbumModel(selectedRows[0].getAlbumId(), selectedRows[0].getAlbumName(), selectedRows[0].getGenreId(), group.getId());
        TrackModel track = new TrackModel(selectedRows[0].getTrackId(), selectedRows[0].getTrackName(), moodModel.getValue(), selectedRows[0].getAvrrate());
        editPageViewManagedBean.setGroup(group);
        editPageViewManagedBean.setAlbum(album);
        editPageViewManagedBean.setTrack(track);
        editPageViewManagedBean.setMembers(members);
        editPageViewManagedBean.setMood(moodModel);

        Logger.getGlobal().log(Level.INFO, "VLEU Group created = {0}", group);

        Logger.getGlobal().log(Level.INFO, "VLEU Edit method passed.");
        return "Edit";
    }

    public void deleteTracks() {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE TRACK: Started!");
        try {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: -------Creating track-------");
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/TrackBean");
            TrackBeanRemoteHome trackHome = (TrackBeanRemoteHome) PortableRemoteObject.narrow(obj, TrackBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: trackHome = {0}", trackHome);
            if (trackHome != null) {
                for (BindedModel model : selectedRows) {
                    trackHome.delete(model.getTrackId());
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Tracks have not been deleted!"));
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved!"));
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU CREATE TRACK: Ended!!!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        search(); // refactoring to main page model
    }

    public GroupModel getGroupModelById(long id) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU getGroupModelById: Started!");
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/GroupBean");
            GroupBeanRemoteHome groupHome = (GroupBeanRemoteHome) PortableRemoteObject.narrow(obj, GroupBeanRemoteHome.class);
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU SAVE: groupHome = {0}", groupHome);
            if (groupHome != null) {
                Group group = groupHome.findByPrimaryKey(id);
                Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU getGroupModelById: Ended !!!");
                return new GroupModel(group.getId(), group.getName());
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.WARNING, "VLEU getGroupModelById: Ended with null!!!");
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<MusicianModel> getMembersByGroupId(long id) {
        Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU getMembersByGroupId: Started!");
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup("ejb/MemberBean");
            MemberBeanRemoteHome memberHome = (MemberBeanRemoteHome) PortableRemoteObject.narrow(obj, MemberBeanRemoteHome.class);
            List<MusicianModel> members = new ArrayList<MusicianModel>();
            Collection memberst = memberHome.findByGroup(id);
            for (Object membert : memberst) {
                Member member = (Member) membert;
                members.add(new MusicianModel(member.getId(), member.getName(), member.getLink()));
            }
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.INFO, "VLEU getMembersByGroupId: Ended !!!");
            return members;
        } catch (NamingException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FinderException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
