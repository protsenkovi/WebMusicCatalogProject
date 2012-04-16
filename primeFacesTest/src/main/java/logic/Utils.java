/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.sql.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import models.entitys.AlbumModel;
import models.entitys.GroupModel;
import models.entitys.MusicianModel;

/**
 *
 * @author Klaritin
 */
public class Utils {

    private static String connectionString = "jdbc/OracleDataSource";

    public static List<Entry<String, Double>> searchGroupRating() {
        List<Entry<String, Double>> groups = new ArrayList<Entry<String, Double>>();
        DataSource dataSource;
        Context context;
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(connectionString);
            connection = dataSource.getConnection();
            query = "SELECT \"group\".name, rated_group.rate "
                    + "FROM (SELECT \"group\".id,  AVG(rated_tracks.avgrate) as rate "
                    + " FROM "
                    + "  (SELECT track.id, AVG(rate.value) as avgrate "
                    + "   FROM groups \"group\", albums album, tracks track, rates rate "
                    + "   WHERE "
                    + "   album.\"group\" = \"group\".id AND "
                    + "   track.album = album.id AND "
                    + "   rate.track = track.id "
                    + "   GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track "
                    + " WHERE "
                    + " album.\"group\" = \"group\".id AND "
                    + " track.album = album.id AND "
                    + " rated_tracks.id = track.id "
                    + " GROUP BY \"group\".id) rated_group, groups \"group\" "
                    + "WHERE rated_group.id = \"group\".id AND rownum < 2 "
                    + "ORDER BY rated_group.rate DESC";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }
            String name = resultSet.getString(1);
            double rate = resultSet.getDouble(2);
            groups.add(new SimpleEntry(name, rate));
        } catch (NamingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection, statement);
        }
        return groups;
    }

    public static List<Entry<String, Double>> searchAlbumRating() {
        List<Entry<String, Double>> albums = new ArrayList<Entry<String, Double>>();
        DataSource dataSource;
        Context context;
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(connectionString);
            connection = dataSource.getConnection();
            query = "SELECT album.name, rated_albums.rate as rate "
                    + "FROM  (SELECT album.id, SUM(rated_tracks.avgrate) as rate   "
                    + "FROM    (SELECT track.id, AVG(rate.value) as avgrate    "
                    + "FROM groups \"group\", albums album, tracks track, rates rate    "
                    + "WHERE     album.\"group\" = \"group\".id AND     "
                    + "track.album = album.id AND    rate.track = track.id    "
                    + "GROUP BY track.id) rated_tracks, groups \"group\", albums album, tracks track  "
                    + "WHERE album.\"group\" = \"group\".id AND   "
                    + "track.album = album.id AND  rated_tracks.id = track.id  "
                    + "GROUP BY album.id) rated_albums, albums album "
                    + "WHERE album.id = rated_albums.id";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                double rate = resultSet.getDouble(2);
                albums.add(new SimpleEntry(name, rate));
            }
        } catch (NamingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection, statement);
        }
        return albums;
    }

    public static List<Entry<String, String>> searchAlbumContent(String name) {
        List<Entry<String, String>> albumContent = new ArrayList<Entry<String, String>>();
        DataSource dataSource;
        Context context;
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(connectionString);
            connection = dataSource.getConnection();
            query = "SELECT \"group\".name, track.name FROM groups \"group\", albums album, tracks track "
                    + "WHERE album.\"group\" = \"group\".id "
                    + "AND track.album = album.id "
                    + "AND album.name LIKE '%" + name + "%'";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }
            while (resultSet.next()) {
                String group = resultSet.getString(1);
                String track = resultSet.getString(2);
                albumContent.add(new SimpleEntry(group, track));
            }
        } catch (NamingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection, statement);
        }
        return albumContent;
    }

    public static List<String> searchMusicianAlbums(String name) {
        List<String> albums = new ArrayList<String>();
        DataSource dataSource;
        Context context;
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(connectionString);
            connection = dataSource.getConnection();
            query = "SELECT album.name "
                    + "FROM musicians musician, group_members groupref, groups \"group\", albums album "
                    + "WHERE musician.name LIKE '%" + name + "%' AND "
                    + "groupref.member = musician.id AND "
                    + "groupref.\"group\" = \"group\".id AND "
                    + "album.\"group\" = \"group\".id";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String album = resultSet.getString(1);
                albums.add(album);
            }
        } catch (NamingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection, statement);
        }
        Logger.getLogger(Utils.class.getName()).log(Level.INFO, "albums " + albums);
        return albums;
    }
    
    public static List<MusicianModel> searchMusiciansByGenre(Long id) {
        List<MusicianModel> musicians = new ArrayList<MusicianModel>();
        DataSource dataSource;
        Context context;
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(connectionString);
            connection = dataSource.getConnection();
            query = "SELECT musician.id, musician.name, musician.link "
                    + "FROM (SELECT musician.id "
                    + "      FROM musicians musician, group_members groupref, groups \"group\", albums album, genres genre "
                    + "      WHERE groupref.member = musician.id AND "
                    + "      groupref.\"group\" = \"group\".id AND "
                    + "      album.\"group\" = \"group\".id AND "
                    + "      album.genre = genre.id AND "
                    + "      genre.id = " + id + " GROUP BY musician.id) genre_musician, musicians musician "
                    + "WHERE musician.id = genre_musician.id";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long musid = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String link = resultSet.getString(3);
                musicians.add(new MusicianModel(musid, name, link));
            }
        } catch (NamingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection, statement);
        }
        Logger.getLogger(Utils.class.getName()).log(Level.INFO, "musicians " + musicians);
        return musicians;
    }
    
    public static List<MusicianModel> searchMusiciansByMood(Integer value) {
        List<MusicianModel> musicians = new ArrayList<MusicianModel>();
        DataSource dataSource;
        Context context;
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(connectionString);
            connection = dataSource.getConnection();
            query = "SELECT musician.id, musician.name, musician.link "
                    + "FROM (SELECT musician.id "
                    + "      FROM musicians musician, group_members groupref, groups \"group\", albums album, tracks track, "
                    + "       (SELECT MAX(value) as minvalue, m.maxvalue as maxvalue "
                    + "        FROM moods, (SELECT value as maxvalue FROM moods WHERE value = " + value + ") m "
                    + "        WHERE moods.value < m.maxvalue "
                    + "        GROUP BY m.maxvalue) mood "
                    + "WHERE groupref.member = musician.id AND groupref.\"group\" = \"group\".id AND "
                    + "album.\"group\" = \"group\".id AND "
                    + "track.album = album.id AND "
                    + "track.mood > mood.minvalue AND track.mood <= mood.maxvalue "
                    + "GROUP BY musician.id) res_musicians, musicians musician "
                    + "WHERE res_musicians.id = musician.id";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long musid = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String link = resultSet.getString(3);
                musicians.add(new MusicianModel(musid, name, link));
            }
        } catch (NamingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(connection, statement);
        }
        Logger.getLogger(Utils.class.getName()).log(Level.INFO, "musicians " + musicians);
        return musicians;
    }

    private static void closeConnection(Connection connection, Statement statement) {
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
