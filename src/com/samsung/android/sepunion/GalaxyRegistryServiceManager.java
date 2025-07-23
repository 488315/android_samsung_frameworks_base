package com.samsung.android.sepunion;

import android.content.Context;
import com.samsung.android.sepunion.IGalaxyRegistryService;

/* loaded from: classes6.dex */
public class GalaxyRegistryServiceManager {
    private static final String TAG = "GalaxyRegistryServiceManager";
    private Context mContext;
    private IGalaxyRegistryService mService;

    public GalaxyRegistryServiceManager(Context context) {
        this.mContext = context;
    }

    private IGalaxyRegistryService getService() {
        if (this.mService == null) {
            this.mService = IGalaxyRegistryService.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_GALAXY_REGISTRY));
        }
        return this.mService;
    }
}
