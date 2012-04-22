/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bmp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.sql.DataSource;

/**
 *
 * @author Procenko_Buldigin
 */
public class BindedTableBean implements EntityBean {

    public long idGroup;
    public long idAlbum;
    public long idTrack;
    public long idGenre;
    public int idMood;
    public String group;
    public String album;
    public String track;
    public String mood;
    public double avgrate;
    public String genre;
    private EntityContext entityContext;
    private DataSource dataSource;

    public String getAlbum() {
        return album;
    }

    public String getGroup() {
        return group;
    }

    public long getIdAlbum() {
        return idAlbum;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public long getIdTrack() {
        return idTrack;
    }

    public String getTrack() {
        return track;
    }

    public int getIdMood() {
        return idMood;
    }

    public String getMood() {
        return mood;
    }

    public long getIdGenre() {
        return idGenre;
    }

    public String getGenre() {
        return genre;
    }

    public double getAvgRate() {
        return avgrate;
    }

    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext aContext) {
        entityContext = aContext;
        try {
            javax.naming.Context context =
                    new javax.naming.InitialContext();
            try {
                dataSource = (DataSource) context.lookup("jdbc/OracleDataSource");
            } catch (Exception e) {
                throw new EJBException("No comments...  datasource not found." + e.getMessage());
            }
        } catch (Exception e) {
            throw new EJBException("No comments..." + e.getMessage());
        }
    }

    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate() {
    }

    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate() {
    }

    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove() {
    }

    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() {
        this.entityContext = null;
    }

    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT  AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id AND track.id = ?) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood AND track.id = ?) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "track.id = ? AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id");
            statement.setLong(1, ((Long) this.entityContext.getPrimaryKey()).longValue());
            statement.setLong(2, ((Long) this.entityContext.getPrimaryKey()).longValue());
            statement.setLong(3, ((Long) this.entityContext.getPrimaryKey()).longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }

            this.idGroup = resultSet.getLong(1);
            this.idAlbum = resultSet.getLong(2);
            this.idTrack = resultSet.getLong(3);
            this.idGenre = resultSet.getLong(4);
            this.idMood = resultSet.getInt(5);
            this.group = resultSet.getString(6);
            this.album = resultSet.getString(7);
            this.track = resultSet.getString(8);
            this.genre = resultSet.getString(9);
            this.mood = resultSet.getString(10);
            this.avgrate = resultSet.getDouble(11);

        } catch (SQLException e) {
            throw new EJBException("EjbLoad  SELECT\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }

    }

    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        // TODO add code to persist data
    }

    // </editor-fold>
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     */
    public java.lang.Long ejbFindByPrimaryKey(java.lang.Long aKey) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT  AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id AND track.id = ?) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood AND track.id = ?) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "track.id = ? AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id");
            statement.setLong(1, aKey.longValue());
            statement.setLong(2, aKey.longValue());
            statement.setLong(3, aKey.longValue());

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }

            //throw new EJBException("Primary key = " + aKey + "id " + id + " name " + name);
            return aKey;
        } catch (SQLException e) {
            throw new EJBException("Binded table ejbFindByPrimaryKey SELECT\n" + "Key = " + aKey + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindAll() throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT  track.id as track_id, MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood GROUP BY track.id) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "rated_tracks.id (+)= track.id AND "
                    + "mooded_tracks.track_id = track.id AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(3));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindByGroup(String name) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT  track.id as track_id, MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood GROUP BY track.id) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "rated_tracks.id (+)= track.id AND "
                    + "mooded_tracks.track_id = track.id AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id AND lower(\"group\".name) LIKE lower(?)");
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(3));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindByAlbum(String name) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT  track.id as track_id, MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood GROUP BY track.id) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "rated_tracks.id (+)= track.id AND "
                    + "mooded_tracks.track_id = track.id AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id AND lower(album.name) LIKE lower(?)");
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(3));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindByTrack(String name) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT  track.id as track_id, MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood GROUP BY track.id) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "rated_tracks.id (+)= track.id AND "
                    + "mooded_tracks.track_id = track.id AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id AND lower(track.name) LIKE lower(?)");
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(3));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindByGenre(String name) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT  track.id as track_id, MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood GROUP BY track.id) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "rated_tracks.id (+)= track.id AND "
                    + "mooded_tracks.track_id = track.id AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id AND lower(genre.name) LIKE lower(?)");
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(3));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindByMood(String name) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT \"group\".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, \"group\".name as \"Group\", album.name as Album, track.name as Track, genre.name as Genre, "
                    + "mood.name as Mood, rated_tracks.avgrate as AvgRate "
                    + "FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "  FROM groups \"group\", albums album, tracks track, rates rate "
                    + "  WHERE "
                    + "  album.\"group\" = \"group\".id AND "
                    + "  track.album = album.id AND "
                    + "  rate.track = track.id GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track, moods mood, genres genre, "
                    + "  (SELECT  track.id as track_id, MIN(value) as moodvalue "
                    + "   FROM moods mood, tracks track WHERE mood.value >= track.mood GROUP BY track.id) mooded_tracks "
                    + "WHERE mooded_tracks.moodvalue = mood.value AND "
                    + "rated_tracks.id (+)= track.id AND "
                    + "mooded_tracks.track_id = track.id AND "
                    + "track.album = album.id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "album.genre = genre.id AND lower(mood.name) LIKE lower(?)");
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(3));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    void closeConnection(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
        }
    }
}
