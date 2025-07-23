package com.samsung.android.ssdid;

import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;

/* loaded from: classes6.dex */
public final class SemSsdidManager {
    private static final String TAG = "SemSsdidManager";
    private ISemSsdidManagerService mService;

    public SemSsdidManager(Context context, ISemSsdidManagerService iSemSsdidManagerService) {
        this.mService = iSemSsdidManagerService;
        Slog.d(TAG, "SemSsdidManager, constructor");
        if (iSemSsdidManagerService == null) {
            Slog.d(TAG, "ISemSsdidManagerService is null");
        }
    }

    public String getSsdid() {
        ISemSsdidManagerService iSemSsdidManagerService = this.mService;
        if (iSemSsdidManagerService == null) {
            Slog.d(TAG, "getSsdid, ISemSsdidManagerService is null");
            return "";
        }
        try {
            return iSemSsdidManagerService.getSsdid();
        } catch (RemoteException e) {
            Slog.e(TAG, "getSsdid, RemoteException", e);
            return "";
        }
    }
}
