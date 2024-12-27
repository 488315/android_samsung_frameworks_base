package com.samsung.android.sepunion;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.IPluginManager;

/* loaded from: classes6.dex */
public class SemPluginManager {
    private static final String TAG = SemPluginManager.class.getSimpleName();
    private Context mContext;
    private IPluginManager mService;

    public SemPluginManager(Context context) {
        this.mContext = context;
    }

    private IPluginManager getService() {
        if (this.mService == null) {
            SemUnionManager um = (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService(UnionConstants.SERVICE_PLUGIN);
            this.mService = IPluginManager.Stub.asInterface(b);
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
