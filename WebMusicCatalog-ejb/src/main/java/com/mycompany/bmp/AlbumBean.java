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
public class AlbumBean implements EntityBean {

    private long id;
    private String name;
    private long group;
    private long genre;
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

    public Long getGroup() {
        return new Long(group);
    }

    public void setGroup(Long group) {
        this.group = group.longValue();
    }

    public Long getGenre() {
        return new Long(genre);
    }

    public void setGenre(Long genre) {
        this.genre = genre.longValue();
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
            statement = connection.prepareStatement("SELECT id, name, genre, \"group\" FROM albums WHERE id=?");
            statement.setLong(1, ((Long) this.entityContext.getPrimaryKey()).longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }

            this.id = resultSet.getLong(1);
            this.name = resultSet.getString(2);
            this.genre = resultSet.getLong(3);
            this.group = resultSet.getLong(4);

        } catch (SQLException e) {
            throw new EJBException("ejbLoad AlbumBean SELECT\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }

    }

    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            String query = "UPDATE albums SET name = ?, \"group\" = ?, genre = ? WHERE id = ?";
            statement = connection.prepareStatement(query);            
            statement.setString(1, name);
            statement.setLong(2, group);
            statement.setLong(3, genre);
            statement.setLong(4, id);
            Logger.getLogger(AlbumBean.class.getName()).log(Level.INFO, "VLEU AlbumBean ejbStore Executing query: " + query);
            statement.executeUpdate();
            Logger.getLogger(AlbumBean.class.getName()).log(Level.INFO, "VLEU AlbumBean ejbStore Album id: " + this.id + " name: " + this.name + " stored.");
        } catch (SQLException e) {
            Logger.getLogger(AlbumBean.class.getName()).log(Level.WARNING, "Album id: " + this.id + " name: " + this.name + " NOT stored!");
            throw new EJBException("VLEU AlbumBean ejbStore  UPDATE FAILED. Stack trace: \n" + e.getMessage());
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
            statement = connection.prepareStatement("SELECT id FROM albums WHERE id=?");
            statement.setLong(1, aKey.longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }

            //throw new EJBException("Primary key = " + aKey + "id " + id + " name " + name);
            return aKey;
        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT\n" + "Key = " + aKey + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindAll() throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id FROM albums");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(1));
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

    public java.util.Collection ejbFindByName(String name) throws FinderException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT id FROM albums WHERE name LIKE '%" + name + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(1));
                keys.addElement(id);
            }
            return keys;
        } catch (SQLException e) {
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

    public Long ejbHomeDelete(java.lang.Long key) {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM albums "
                + "WHERE id = " + key;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeQuery(query);
            connection.commit();
            return key;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public Long ejbCreate(String name, long groupId, long genreId) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "";
        try {
            connection = dataSource.getConnection();            
            query = "SELECT album_id.NEXTVAL FROM dual";            
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                long newId = result.getLong(1);
                Logger.getLogger(AlbumBean.class.getName()).log(Level.INFO, "VLEU AlbumBean ejbCreate newId: " + newId + "Executed query: " + query);
                query = "INSERT INTO albums (id, name, \"group\", genre) "
                      + "VALUES (? ,? ,? ,? )";
                statement = connection.prepareStatement(query);
                statement.setLong(1, newId);
                statement.setString(2, name);
                statement.setLong(3, groupId);
                statement.setLong(4, genreId);
                statement.execute();
                this.id = newId; //????? Bean life cycle misunderstanding!
                return Long.valueOf(newId);
            }
            return null;
        } catch (SQLException e) {
            throw new EJBException("VLEU AlbumBean ejbCreate Create\n query = " + query + "\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public void ejbPostCreate(String name, long groupId, long genreId) {
        this.name = name;
        this.group = groupId;
        this.genre = genreId;
    }
}
