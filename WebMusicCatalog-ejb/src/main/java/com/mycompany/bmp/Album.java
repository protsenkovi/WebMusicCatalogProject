/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bmp;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

/**
 *
 * @author Procenko_Buldigin
 */
public interface Album extends EJBObject {
    public Long getId() throws RemoteException;

    public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;

    public Long getGroup() throws RemoteException;
    public void setGroup(Long group) throws RemoteException;

    public Long getGenre() throws RemoteException;
    public void setGenre(Long genre) throws RemoteException;
}
