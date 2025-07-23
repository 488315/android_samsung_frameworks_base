package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
