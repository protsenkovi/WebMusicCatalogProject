/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bmp;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 *
 * @author Procenko_Buldigin
 */
public interface BindedTableBeanRemoteHome extends EJBHome {

    java.util.Collection findByGroup(String name)  throws FinderException, RemoteException;
    java.util.Collection findByAlbum(String name)  throws FinderException, RemoteException;
    java.util.Collection findByTrack(String name)  throws FinderException, RemoteException;
    java.util.Collection findByGenre(String name)  throws FinderException, RemoteException;
    java.util.Collection findByMood(String name)  throws FinderException, RemoteException;
    java.util.Collection findAll()  throws FinderException, RemoteException;
    com.mycompany.bmp.BindedTable findByPrimaryKey(java.lang.Long key)  throws FinderException, RemoteException;
    
}
