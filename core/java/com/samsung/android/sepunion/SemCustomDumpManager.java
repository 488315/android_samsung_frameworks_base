package com.samsung.android.sepunion;

import android.content.Context;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import com.samsung.android.sepunion.IUnionManager;

/* loaded from: classes5.dex */
public class SemCustomDumpManager {
    private static final String TAG = SemCustomDumpManager.class.getSimpleName();
    private Context mContext;
    private IUnionManager mService;

    public SemCustomDumpManager(Context context) {
        this.mContext = context;
    }

    private IUnionManager getService() {
        if (this.mService == null) {
            IUnionManager asInterface = IUnionManager.Stub.asInterface(ServiceManager.getService(Context.SEP_UNION_SERVICE));
            this.mService = asInterface;
            if (asInterface == null) {
                Log.m301i(TAG, "IUnionManager is NULL");
            }
        }
        return this.mService;
    }

    public void setDumpState(String label, String path) {
        if (this.mService == null) {
            this.mService = getService();
        }
        try {
            Log.m301i(TAG, "setDumpState : " + path);
            this.mService.setDumpEnabled(label, path);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
