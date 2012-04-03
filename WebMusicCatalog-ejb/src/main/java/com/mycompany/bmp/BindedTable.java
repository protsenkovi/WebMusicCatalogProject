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
public interface BindedTable extends EJBObject {
    public String getAlbum()throws RemoteException;
    public String getGroup() throws RemoteException;
    public String getTrack() throws RemoteException;
    public String getMood() throws RemoteException;
    public String getGenre() throws RemoteException;
    public double getAvgRate() throws RemoteException;
    public long getIdAlbum() throws RemoteException;
    public long getIdGroup() throws RemoteException;
    public long getIdTrack() throws RemoteException;
    public long getIdGenre() throws RemoteException;
    public int getIdMood() throws RemoteException;
}
