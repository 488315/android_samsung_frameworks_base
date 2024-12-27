package com.android.server.biometrics.sensors.fingerprint.hidl;

import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.StopUserClient;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
