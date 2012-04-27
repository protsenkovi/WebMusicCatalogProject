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
public class MoodBean implements EntityBean {

    private int value;
    private String name;

    private EntityContext entityContext;
    private DataSource dataSource;

    public Integer getId() {
        return new Integer(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            statement = connection.prepareStatement("SELECT value, name FROM moods WHERE value=?");
            statement.setInt(1, ((Integer) this.entityContext.getPrimaryKey()).intValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }
            this.value = resultSet.getInt(1);
            this.name = resultSet.getString(2);
            Logger.getLogger(AlbumBean.class.getName()).log(Level.INFO, "VLEU MoodBean ejbLoad Name: " + name + " Value:" + value);
        } catch (SQLException e) {
            throw new EJBException("ejbLoad� SELECT\n" + e.getMessage());
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
            String query = "UPDATE moods SET name = ? WHERE value = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, value);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("ejbStore SELECT\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    // </editor-fold>
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT value FROM moods WHERE value=?");
            statement.setInt(1, aKey.intValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("... Key = " + aKey);
            }
            //throw new EJBException("Primary key = " + aKey + "id " + id + " name " + name);
            return aKey;
        } catch (SQLException e) {
            throw new EJBException("ejbFindByPrimaryKey MoodBean SELECT\n" + "Key = " + aKey + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public java.util.Collection ejbFindAll() throws FinderException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT value FROM moods");
            ResultSet resultSet = statement.executeQuery();
            Vector keys = new Vector();
            while (resultSet.next()) {
                Integer value = Integer.valueOf(resultSet.getInt(1));
                Logger.getLogger(AlbumBean.class.getName()).log(Level.INFO, "VLEU MoodBean ejbFindAll Key: " + value);
                if(value.intValue() != 0) //db logic
                    keys.addElement(value);
            }
            return keys;

        } catch (SQLException e) {
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
            String query = "SELECT value FROM moods WHERE name LIKE '%" + name + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            Vector keys = new Vector();
            while (resultSet.next()) {
                Integer value = Integer.valueOf(resultSet.getInt(1));
                Logger.getLogger(AlbumBean.class.getName()).log(Level.INFO, "VLEU MoodBean ejbFindByName Key: " + value);
                if(value.intValue() != 0) //db logic
                    keys.addElement(value);
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

    public Integer ejbHomeDelete(java.lang.Integer key) {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM moods "
                + "WHERE value = " + key.longValue();
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

    public Integer ejbCreate(Integer id, String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO moods (value, name) "
                     + "VALUES (?, ?)";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id.intValue());
            statement.setString(2, name);
            statement.executeUpdate();
            this.value = id.intValue();
            return id;
        } catch (SQLException e) {
            throw new EJBException("Ошибка Create\n query = " + query + "\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }
    public void ejbPostCreate(Integer id, String name) {
        this.name = name;
    }
}
