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
public interface MemberBeanRemoteHome extends EJBHome {

    com.mycompany.bmp.Member create(String name, String link)  throws RemoteException,CreateException;

    Long delete(java.lang.Long key)  throws  RemoteException;
    
    java.util.Collection findAll()  throws FinderException, RemoteException;
    java.util.Collection findByName(String name)  throws FinderException, RemoteException;
    java.util.Collection findByGroup(java.lang.Long key)  throws FinderException, RemoteException;
    com.mycompany.bmp.Member findByPrimaryKey(java.lang.Long key)  throws FinderException, RemoteException;
    
}
