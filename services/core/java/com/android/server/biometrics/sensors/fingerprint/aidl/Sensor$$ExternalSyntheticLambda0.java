package com.android.server.biometrics.sensors.fingerprint.aidl;

import com.android.server.biometrics.sensors.StopUserClient;

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
