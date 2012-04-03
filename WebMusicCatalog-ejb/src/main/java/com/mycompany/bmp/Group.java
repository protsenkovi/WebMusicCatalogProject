/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bmp;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBObject;

/**
 *
 * @author Procenko_Buldigin
 */
public interface Group extends EJBObject {

    public Long getId() throws RemoteException;

    public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;

    public Collection getMembers() throws RemoteException;
    public void setMembers(Collection members) throws RemoteException;
}
