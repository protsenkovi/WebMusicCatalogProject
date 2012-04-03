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
public interface Track extends EJBObject {
    public Long getId() throws RemoteException;

    public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;

    public Long getAlbum() throws RemoteException;
    public void setAlbum(Long album) throws RemoteException;

    public Integer getMood() throws RemoteException;
    public void setMood(Integer value) throws RemoteException;

    public Integer getAvgRate() throws RemoteException;
}
