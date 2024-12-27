package com.android.server.cover;

import android.content.Context;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.SemPluginManagerLocal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CoverManagerService extends SystemService {
    public final CoverManagerServiceImpl mCoverManagerServiceImpl;

    public CoverManagerService(Context context) {
        super(context);
        CoverManagerServiceImpl coverManagerServiceImpl = new CoverManagerServiceImpl();
        new CoverState();
        coverManagerServiceImpl.mSystemReady = false;
        coverManagerServiceImpl.mUnionLocal = (SemPluginManagerLocal) LocalServices.getService(SemPluginManagerLocal.class);
        this.mCoverManagerServiceImpl = coverManagerServiceImpl;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 600) {
            CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
            if (coverManagerServiceImpl.mSystemReady) {
                return;
            }
            coverManagerServiceImpl.mSystemReady = true;
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("cover", this.mCoverManagerServiceImpl);
    }
}
