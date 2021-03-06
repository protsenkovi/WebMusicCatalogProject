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
public class RateBean implements EntityBean {

    private long id;
    private long track;
    private int value;
    private EntityContext entityContext;
    private DataSource dataSource;

    public Long getId() {
        return new Long(id);
    }

    public Long getTrack() {
        return new Long(track);
    }

    public void setTrack(Long trackId) {
        this.track = trackId.longValue();
    }

    public Integer getValue() {
        return new Integer(value);
    }

    public void setValue(Integer value) {
        this.value = value.intValue();
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
            statement = connection.prepareStatement("SELECT id, track, value FROM rates WHERE id=?");
            statement.setLong(1, ((Long) this.entityContext.getPrimaryKey()).longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }

            this.id = resultSet.getLong(1);
            this.track = resultSet.getLong(2);
            this.value = resultSet.getInt(3);

        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT\n" + e.getMessage());
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
            String query = "UPDATE rates SET track = '" + this.track + "', value = " + this.value + " WHERE id = " + this.id;
            ResultSet resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT\n" + e.getMessage());
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
            statement = connection.prepareStatement("SELECT id FROM rates WHERE id=?");
            statement.setLong(1, aKey.longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("...");
            }

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
            statement = connection.prepareStatement("SELECT id FROM rates");
            ResultSet resultSet = statement.executeQuery();
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

    public java.util.Collection ejbFindByTrack(Long trackId) throws FinderException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT id FROM rates WHERE track =" + trackId.longValue();
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
        String query = "DELETE FROM rates "
                + "WHERE id = " + key.longValue();
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

    public Long ejbCreate(Long trackId, Integer value) {
        Connection connection = null;
        Statement statement = null;
        String query = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            query = "SELECT rate_id.NEXTVAL FROM dual";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                long newId = result.getLong(1);
                Logger.getLogger(RateBean.class.getName()).log(Level.INFO, "VLEU RateBean ejbCreate newId: " + newId + "Executed query: " + query);
                query = "INSERT INTO rates (id, track, value) "
                        + "VALUES (" + newId + ", " + trackId + " , " + value + ")";
                statement.executeQuery(query);
                connection.commit();
                this.id = newId; //????? Bean life cycle misunderstanding!
                return Long.valueOf(newId);
            }
            return null;
        } catch (SQLException e) {
            throw new EJBException("RateBean Create\n query = " + query + "\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public void ejbPostCreate(Long trackId, Integer value) {
        this.track = trackId.longValue();
        this.value = value.intValue();
    }
}
