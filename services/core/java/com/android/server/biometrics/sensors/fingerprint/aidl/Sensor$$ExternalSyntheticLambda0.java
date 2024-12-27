package com.android.server.biometrics.sensors.fingerprint.aidl;

import com.android.server.biometrics.sensors.StopUserClient;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Sensor$$ExternalSyntheticLambda0
        implements SemFpAidlLockoutHalImpl.LockoutResetCallback,
                StopUserClient.UserStoppedCallback {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Sensor$$ExternalSyntheticLambda0(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
    public void onUserStopped() {
        Sensor.this.mCurrentSession = null;
    }
}
