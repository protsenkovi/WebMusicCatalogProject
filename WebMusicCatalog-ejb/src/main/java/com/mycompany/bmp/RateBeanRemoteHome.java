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
public interface RateBeanRemoteHome extends EJBHome {

    com.mycompany.bmp.Rate create(Long trackId, Integer value)  throws RemoteException,CreateException;

    Long delete(java.lang.Long key)  throws  RemoteException;

    java.util.Collection findAll()  throws FinderException, RemoteException;
    java.util.Collection findByTrack(Long trackId)  throws FinderException, RemoteException;
    com.mycompany.bmp.Rate findByPrimaryKey(java.lang.Long key)  throws FinderException, RemoteException;
    
}
