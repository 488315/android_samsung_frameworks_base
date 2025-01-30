package com.samsung.android.sepunion;

import android.content.Context;
import android.p009os.IBinder;
import com.samsung.android.sepunion.IGalaxyRegistryService;

/* loaded from: classes5.dex */
public class GalaxyRegistryServiceManager {
    private static final String TAG = GalaxyRegistryServiceManager.class.getSimpleName();
    private Context mContext;
    private IGalaxyRegistryService mService;

    public GalaxyRegistryServiceManager(Context context) {
        this.mContext = context;
    }

    private IGalaxyRegistryService getService() {
        if (this.mService == null) {
            SemUnionManager um = (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService(UnionConstants.SERVICE_GALAXY_REGISTRY);
            this.mService = IGalaxyRegistryService.Stub.asInterface(b);
        }
        return this.mService;
    }
}
