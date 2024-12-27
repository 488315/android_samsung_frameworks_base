package com.samsung.android.hwrs;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemHwrsManager {
    private static final String TAG = "[HWRS_SYS]SemHwrsManager";
    private final Context mContext;
    private final ISemHwrsManager mService;
    private final int mUserId;

    public SemHwrsManager(Context context, ISemHwrsManager service, int userId) {
        this.mContext = context;
        this.mService = service;
        this.mUserId = userId;
    }

    public boolean addShare(String aResName, String aResPath, String aUserName, String aGroupName, String aMaxConnections) throws RemoteException {
        try {
            return this.mService.addShare(aResName, aResPath, aUserName, aGroupName, aMaxConnections);
        } catch (RemoteException e) {
            Log.e(TAG, "addShare failed- " + e);
            return false;
        }
    }

    public boolean addUser(String aUserName, String aUserPassword) throws RemoteException {
        try {
            return this.mService.addUser(aUserName, aUserPassword);
        } catch (RemoteException e) {
            Log.e(TAG, "addUser failed- " + e);
            return false;
        }
    }

    public boolean deleteUser(String aUserName) throws RemoteException {
        try {
            return this.mService.deleteUser(aUserName);
        } catch (RemoteException e) {
            Log.e(TAG, "deleteUser failed- " + e);
            return false;
        }
    }

    public boolean startKsmbdServer() throws RemoteException {
        try {
            return this.mService.startKsmbdServer();
        } catch (RemoteException e) {
            Log.e(TAG, "startKsmbdServer failed- " + e);
            return false;
        }
    }

    public boolean stopKsmbdServer() throws RemoteException {
        try {
            return this.mService.stopKsmbdServer();
        } catch (RemoteException e) {
            Log.e(TAG, "stopKsmbdServer failed- " + e);
            return false;
        }
    }

    public boolean restartKsmbdServer() throws RemoteException {
        try {
            return this.mService.restartKsmbdServer();
        } catch (RemoteException e) {
            Log.e(TAG, "restartKsmbdServer failed- " + e);
            return false;
        }
    }

    public boolean reloadKmbdServerConfiguration() throws RemoteException {
        try {
            return this.mService.reloadKmbdServerConfiguration();
        } catch (RemoteException e) {
            Log.e(TAG, "reloadKmbdServerConfiguration failed- " + e);
            return false;
        }
    }

    public String getKsmbdServerStatus() throws RemoteException {
        try {
            return this.mService.getKsmbdServerStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "getKsmbdServerStatus failed- " + e);
            return null;
        }
    }

    public boolean ksmbdServerCleanup() throws RemoteException {
        try {
            return this.mService.ksmbdServerCleanup();
        } catch (RemoteException e) {
            Log.e(TAG, "ksmbdServerCleanup failed- " + e);
            return false;
        }
    }
}
