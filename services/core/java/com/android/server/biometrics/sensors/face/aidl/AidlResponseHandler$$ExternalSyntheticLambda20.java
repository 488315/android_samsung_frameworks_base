package com.android.server.biometrics.sensors.face.aidl;

import android.util.Slog;

import com.android.server.biometrics.sensors.BiometricScheduler;

public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda20
        implements Runnable {
    public final /* synthetic */ BiometricScheduler f$0;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda20(
            BiometricScheduler biometricScheduler) {
        this.f$0 = biometricScheduler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BiometricScheduler biometricScheduler = this.f$0;
        if (biometricScheduler.mStopUserClient == null) {
            Slog.e("BiometricScheduler", "Unexpected onUserStopped");
            return;
        }
        Slog.d("BiometricScheduler", "[OnUserStopped]: " + biometricScheduler.mStopUserClient);
        biometricScheduler.mStopUserClient.onUserStopped();
        biometricScheduler.mStopUserClient = null;
    }
}
