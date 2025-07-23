package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.visual.ai.sdkcommon.IDpsC2pa;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paClientReleaseRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientSaveManifestsToCacheRunnable";
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClientReleaseRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public void execute() {
        Log.d(TAG, "execute release()");
        try {
            ((IDpsC2pa.Stub.Proxy) this.mServiceExecutor.getC2PAService()).clearAllManifestsFromCache();
            this.mSource.setResult(Boolean.TRUE);
        } catch (RemoteException e) {
            e.printStackTrace();
            this.mSource.setException(e);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }
}
