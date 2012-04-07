/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bmp;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
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
public class GenreBean implements EntityBean {

    private Long id;
    private String name;
    private EntityContext entityContext;
    private DataSource dataSource;

    public Long getId() {
        return id;
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
            statement = connection.prepareStatement("SELECT id, name FROM genres WHERE id=?");
            statement.setLong(1, ((Long) this.entityContext.getPrimaryKey()).longValue());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EJBException("Can't load data");
            }

            this.id = new Long(resultSet.getLong(1));
            this.name = resultSet.getString(2);
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
            String query = "UPDATE genres SET name = '" + this.name + "'  WHERE id = " + this.id;
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
            statement = connection.prepareStatement("SELECT id FROM genres WHERE id=?");
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
            statement = connection.prepareStatement("SELECT id FROM genres");
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
            String query = "SELECT id FROM genres WHERE name LIKE '%" + name + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            Vector keys = new Vector();
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getLong(1));
                keys.addElement(id);
            }
            return keys;
        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT\n query " + "\n" + e.getMessage());
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

    //home methods
    public Long ejbHomeAdd() {
        Connection connection = null;
        Statement statement = null;
        String name = "default";
        String query = "INSERT INTO genres (id, name) "
                + "VALUES (genre_id.NEXTVAL, '" + name + "')";
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeQuery(query);
            connection.commit();
            query = "SELECT id FROM genres WHERE name='" + name + "' ";
            statement = connection.createStatement();

            ResultSet res = statement.executeQuery(query);
            if (res.next()) {
                return new Long(res.getLong(1));
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public Long ejbHomeDelete(java.lang.Long key) {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM genres "
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

    public Long ejbHomeCopy(java.lang.Long key) {
        Connection connection = null;
        Statement statement = null;
        String query = "INSERT INTO genres (id, name) "
                + "VALUES (genre_id.NEXTVAL, (SELECT name FROM genres WHERE id=" + key + "))";
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeQuery(query);
            connection.commit();
            return null;
        } catch (SQLException e) {
            throw new EJBException("Ошибка SELECT\n " + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public Long ejbCreate(String name) {
        Connection connection = null;
        Statement statement = null;
        String query = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            query = "SELECT genre_id.NEXTVAL FROM dual";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                long newId = result.getLong(1);
                Logger.getLogger(TrackBean.class.getName()).log(Level.INFO, "VLEU GenreBean ejbCreate newId: " + newId + "Executed query: " + query);
                query = "INSERT INTO genres (id, name) "
                        + "VALUES (" + newId + ", '" + name + "')";
                statement.executeQuery(query);
                connection.commit();
                this.id = Long.valueOf(newId); //????? Bean life cycle misunderstanding!
                return Long.valueOf(newId);
            }
            return null;
        } catch (SQLException e) {
            throw new EJBException("GenreBean Create\n query = " + query + "\n" + e.getMessage());
        } finally {
            closeConnection(connection, statement);
        }
    }

    public void ejbPostCreate(String name) {
        this.name = name;
    }
}
