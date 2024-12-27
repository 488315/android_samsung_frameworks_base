package com.android.server.biometrics.sensors.face.aidl;

import android.util.Slog;
import com.android.server.biometrics.sensors.BiometricScheduler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda20 implements Runnable {
    public final /* synthetic */ BiometricScheduler f$0;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda20(BiometricScheduler biometricScheduler) {
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
