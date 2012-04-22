/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

import java.io.Serializable;

/**
 *
 * @author Klaritin
 */
public class BindedModel implements Serializable, Comparable {

//группа | альбом | трек | средний рейтинг | настроение | жанр
    private long trackId;
    private long albumId;
    private long groupId;
    private long genreId;
    private String trackName;
    private String albumName;
    private String groupName;
    private double avrrate;
    private double rate;
    private int mood;
    private String moodName;
    private String genreName;

    /**
     * Creates a new instance of BindedModelByTrack
     */
    public BindedModel() {
    }

    public BindedModel(long trackId, long albumId, long groupid, long genreId, String trackName, String albumName, String groupName, double avrrate, int mood, String moodName, String genreName) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.groupId = groupid;
        this.genreId = genreId;
        this.trackName = trackName;
        this.albumName = albumName;
        this.groupName = groupName;
        this.avrrate = avrrate;
        this.mood = mood;
        this.moodName = moodName;
        this.genreName = genreName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAvrrate(double avrrate) {
        this.avrrate = avrrate;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupid(long groupid) {
        this.groupId = groupid;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public double getAvrrate() {
        return avrrate;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getGroupName() {
        return groupName;
    }

    public long getGroupid() {
        return groupId;
    }

    public int getMood() {
        return mood;
    }

    public String getMoodName() {
        return moodName;
    }

    public long getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    @Override
    public int compareTo(Object o) {
        BindedModel compareTo = (BindedModel) o;
        if (compareTo.groupName.compareTo(this.groupName) != 0) {
            return -compareTo.groupName.compareTo(this.groupName);
        }
        if (compareTo.albumName.compareTo(this.albumName) != 0) {
            return -compareTo.albumName.compareTo(this.albumName);
        }
        if (compareTo.trackName.compareTo(this.trackName) != 0) {
            return -compareTo.trackName.compareTo(this.trackName);
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BindedModel))
            return false;        
        return this.trackId == ((BindedModel)obj).getTrackId();
    }

    @Override
    public String toString() {
        return "track = " + this.trackId + " " + this.trackName;
    }
}
