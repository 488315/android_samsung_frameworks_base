package com.android.server.biometrics.sensors.face.aidl;

import com.android.server.biometrics.sensors.SemTestHalHelper;

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
