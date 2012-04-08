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
public interface MoodBeanRemoteHome extends EJBHome {

    com.mycompany.bmp.Mood create(Integer id, String name)  throws RemoteException,CreateException;

    Integer delete(java.lang.Integer key)  throws  RemoteException;

    java.util.Collection findAll()  throws FinderException, RemoteException;
    java.util.Collection findByName(String name)  throws FinderException, RemoteException;
    com.mycompany.bmp.Mood findByPrimaryKey(java.lang.Integer key)  throws FinderException, RemoteException;    
}
