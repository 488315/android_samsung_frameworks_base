package com.android.server.biometrics.sensors.face.aidl;

import com.android.server.biometrics.sensors.SemTestHalHelper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SehTestHal$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SehTestHal f$0;

    @Override // java.lang.Runnable
    public final void run() {
        SemTestHalHelper semTestHalHelper = this.f$0.mTestHalHelper;
        if (semTestHalHelper != null) {
            semTestHalHelper.initActions();
        }
    }
}
