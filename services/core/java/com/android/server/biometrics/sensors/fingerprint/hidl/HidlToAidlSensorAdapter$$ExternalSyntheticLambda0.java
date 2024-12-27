package com.android.server.biometrics.sensors.fingerprint.hidl;

import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.StopUserClient;

public final /* synthetic */ class HidlToAidlSensorAdapter$$ExternalSyntheticLambda0
        implements StartUserClient.UserStartedCallback,
                LockoutFrameworkImpl.LockoutResetCallback,
                StopUserClient.UserStoppedCallback {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
    public void onUserStarted(int i, int i2, Object obj) {
        HidlToAidlSensorAdapter hidlToAidlSensorAdapter = (HidlToAidlSensorAdapter) this.f$0;
        if (hidlToAidlSensorAdapter.mCurrentUserId != i) {
            hidlToAidlSensorAdapter.handleUserChanged(i);
        }
    }

    @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
    public void onUserStopped() {
        HidlToAidlSensorAdapter hidlToAidlSensorAdapter = HidlToAidlSensorAdapter.this;
        hidlToAidlSensorAdapter.mCurrentUserId = -10000;
        hidlToAidlSensorAdapter.mSession = null;
    }
}
