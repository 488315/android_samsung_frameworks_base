package com.samsung.android.sepunion;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;

/* loaded from: classes6.dex */
public class SemCustomDumpManager {
    private static final String TAG = SemCustomDumpManager.class.getSimpleName();
    private Context mContext;
    private IUnionManager mService;

    public SemCustomDumpManager(Context context) {
        this.mContext = context;
    }

    private IUnionManager getService() {
        if (this.mService == null) {
            this.mService =
                    IUnionManager.Stub.asInterface(
                            ServiceManager.getService(Context.SEP_UNION_SERVICE));
            if (this.mService == null) {
                Log.i(TAG, "IUnionManager is NULL");
            }
        }
        return this.mService;
    }

    public void setDumpState(String label, String path) {
        if (this.mService == null) {
            this.mService = getService();
        }
        try {
            Log.i(TAG, "setDumpState : " + path);
            this.mService.setDumpEnabled(label, path);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
