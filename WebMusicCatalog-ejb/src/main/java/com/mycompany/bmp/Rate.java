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
public interface Rate extends EJBObject {
    public Long getId() throws RemoteException;

    public Long getTrack() throws RemoteException;
    public void setTrack(Long trackId) throws RemoteException;

    public Integer getValue() throws RemoteException;
    public void setValue(Integer value) throws RemoteException;
}
