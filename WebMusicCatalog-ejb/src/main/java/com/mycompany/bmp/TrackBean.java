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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TrackBean implements EntityBean {

    private long id;
    private String name;
    private long album;
    private int mood;
    private int avgrate;
    private EntityContext entityContext;
    private DataSource dataSource;

    public Long getId() {
        return new Long(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAlbum() {
        return new Long(album);
    }

    public void setAlbum(Long album) {
        this.album = album.longValue();
    }

    public Integer getMood() {
        return new Integer(mood);
    }

    public void setMood(Integer mood) {
        this.mood = mood.intValue();
    }

    public Integer getAvgRate() {
        return new Integer(avgrate);
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
            statement = connection.prepareStatement("SELECT track.id as ID, track.name as Track, track.album as TrackAlbum, mood.value as ID, AVG(rate.value) as avgrate "
                    + "FROM tracks track, rates rate, moods mood "
                    + "WHERE rate.track (+)= track.id AND "
                    + "mood.value = track.mood AND track.id = ? "
                    + "GROUP BY track.id, track.name, track.album, mood.value");
            statement.setLong(1, ((Long) this.entityContext.getPrimaryKey()).longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }

            this.id = resultSet.getLong(1);
            this.name = resultSet.getString(2);
            this.album = resultSet.getLong(3);
            this.mood = resultSet.getInt(4);
            this.avgrate = resultSet.getInt(5);

        } catch (SQLException e) {
            throw new EJBException("ejbLoad SELECT\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }

    }

    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "UPDATE tracks SET name = '" + this.name + "', album = " + this.album + ", mood = " + this.mood + " WHERE id = " + this.id;
            Logger.getLogger(TrackBean.class.getName()).log(Level.INFO, "VLEU TrackBean ejbStore Executing query: " + query);
            statement.executeQuery(query);
            Logger.getLogger(TrackBean.class.getName()).log(Level.INFO, "VLEU TrackBean ejbStore Track id: " + this.id + " name: " + this.name + " mood: " + this.mood + " stored.");
        } catch (SQLException e) {
            Logger.getLogger(TrackBean.class.getName()).log(Level.WARNING, "Track id: " + this.id + " name: " + this.name + " mood: " + this.mood + " NOT stored!");
            throw new EJBException("ejbStore SELECT\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
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
            statement = connection.prepareStatement("SELECT id FROM tracks WHERE id=?");
            statement.setLong(1, aKey.longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }

            //throw new EJBException("Primary key = " + aKey + "id " + id + " name " + name);
            return aKey;
        } catch (SQLException e) {
            throw new EJBException("ejbFindByPrimaryKey TrackBean SELECT\n" + "Key = " + aKey + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindAll() throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id FROM tracks");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(1));
                keys.addElement(id);
            }
            return keys;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("ejbFindAll TrackBean SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindByName(String name) throws FinderException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT id FROM tracks WHERE name LIKE '%" + name + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(1));
                keys.addElement(id);
            }
            return keys;
        } catch (SQLException e) {
            throw new EJBException("ejbFindByName TrackBean SELECT\n " + e.getMessage());
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

    public Long ejbHomeDelete(java.lang.Long key) {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM rates "
                     + "WHERE track = " + key.longValue();
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeQuery(query);
            query = "DELETE FROM tracks "
                     + "WHERE id = " + key.longValue();
            statement.executeQuery(query);
            connection.commit();
            return key;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("ejbHomeDelete SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public Long ejbCreate(String name, Long albumId, Integer mood) {
        Connection connection = null;
        Statement statement = null;
        String query = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            query = "SELECT track_id.NEXTVAL FROM dual";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                long newId = result.getLong(1);
                Logger.getLogger(TrackBean.class.getName()).log(Level.INFO, "VLEU TrackBean ejbCreate newId: " + newId + "Executed query: " + query);
                query = "INSERT INTO tracks (id, name, album, mood) "
                      + "VALUES (" + newId + ", '" + name + "' ," + albumId.longValue() + ", " + mood.intValue() + ")";
                statement.executeQuery(query);
                connection.commit();
                this.id = newId; //????? Bean life cycle misunderstanding!
                return Long.valueOf(newId);
            }
            return null;
        } catch (SQLException e) {
            throw new EJBException("TrackBean Create\n query = " + query + "\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public void ejbPostCreate(String name, Long albumId, Integer mood) {
        this.name = name;
        this.album = albumId.longValue();
        this.mood = mood.intValue();
    }
}
