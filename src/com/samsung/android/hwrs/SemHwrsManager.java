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

    public SemHwrsManager(Context context, ISemHwrsManager iSemHwrsManager, int i) {
        this.mContext = context;
        this.mService = iSemHwrsManager;
        this.mUserId = i;
    }

    public boolean addShare(String str, String str2, String str3, String str4, String str5) throws RemoteException {
        try {
            return this.mService.addShare(str, str2, str3, str4, str5);
        } catch (RemoteException e) {
            Log.e(TAG, "addShare failed- " + e);
            return false;
        }
    }

    public boolean addUser(String str, String str2) throws RemoteException {
        try {
            return this.mService.addUser(str, str2);
        } catch (RemoteException e) {
            Log.e(TAG, "addUser failed- " + e);
            return false;
        }
    }

    public boolean deleteUser(String str) throws RemoteException {
        try {
            return this.mService.deleteUser(str);
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
