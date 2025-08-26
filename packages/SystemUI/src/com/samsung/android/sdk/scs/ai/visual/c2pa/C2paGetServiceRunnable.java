package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;

/* loaded from: classes4.dex */
public class C2paGetServiceRunnable extends TaskRunnable {
    private static final String TAG = "C2paGetServiceRunnable";
    private final C2paServiceExecutor mServiceExecutor;

    public C2paGetServiceRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public void execute() {
        this.mSource.setResult(this.mServiceExecutor.getC2PAService());
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }
}
