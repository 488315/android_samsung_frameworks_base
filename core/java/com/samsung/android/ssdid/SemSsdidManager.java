package com.samsung.android.ssdid;

import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;

public final class SemSsdidManager {
    private static final String TAG = "SemSsdidManager";
    private ISemSsdidManagerService mService;

    public SemSsdidManager(Context context, ISemSsdidManagerService service) {
        this.mService = service;
        Slog.d(TAG, "SemSsdidManager, constructor");
        if (service == null) {
            Slog.d(TAG, "ISemSsdidManagerService is null");
        }
    }

    public String getSsdid() {
        if (this.mService == null) {
            Slog.d(TAG, "getSsdid, ISemSsdidManagerService is null");
            return "";
        }
        try {
            return this.mService.getSsdid();
        } catch (RemoteException e) {
            Slog.e(TAG, "getSsdid, RemoteException", e);
            return "";
        }
    }
}
