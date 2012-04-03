/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bmp;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 *
 * @author Procenko_Buldigin
 */
public interface GroupBeanRemoteHome extends EJBHome {

    com.mycompany.bmp.Group create(Long id, String name)  throws RemoteException, CreateException;

    Long add()  throws  RemoteException;
    Long delete(java.lang.Long key)  throws  RemoteException;
    Long copy(java.lang.Long key)  throws  RemoteException;
    com.mycompany.bmp.Group findByPrimaryKey(java.lang.Long key)  throws FinderException, RemoteException;
    java.util.Collection findAll()  throws FinderException, RemoteException;
    java.util.Collection findByName(String name)  throws FinderException, RemoteException;
}
