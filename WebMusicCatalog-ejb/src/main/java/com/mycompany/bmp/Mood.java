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
public interface Mood extends EJBObject {
    public Integer getId() throws RemoteException;
    public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;
}
