package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.visual.ai.sdkcommon.IDpsC2pa;

/* loaded from: classes4.dex */
public class C2paClientClearManifestsFromCacheRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientSaveManifestsToCacheRunnable";
    String mParentPath;
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClientClearManifestsFromCacheRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public void execute() {
        Log.d(TAG, "execute clearManifestsFromCache()");
        try {
            this.mSource.setResult(Boolean.valueOf(((IDpsC2pa.Stub.Proxy) this.mServiceExecutor.getC2PAService()).clearManifestsFromCache(this.mParentPath)));
        } catch (RemoteException e) {
            e.printStackTrace();
            this.mSource.setException(e);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }

    public void setFilePath(String str) {
        this.mParentPath = str;
    }
}
