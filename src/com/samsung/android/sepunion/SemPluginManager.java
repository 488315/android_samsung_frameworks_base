package com.samsung.android.sepunion;

import android.content.Context;
import android.os.RemoteException;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.IPluginManager;

/* loaded from: classes6.dex */
public class SemPluginManager {
    private static final String TAG = "SemPluginManager";
    private Context mContext;
    private IPluginManager mService;

    public SemPluginManager(Context context) {
        this.mContext = context;
    }

    private IPluginManager getService() {
        if (this.mService == null) {
            this.mService = IPluginManager.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_PLUGIN));
        }
        return this.mService;
    }

    public CoverState getCoverState() {
        try {
            IPluginManager service = getService();
            if (service != null) {
                return service.getCoverState();
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
