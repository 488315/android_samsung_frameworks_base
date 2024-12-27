package com.android.server.biometrics.sensors.fingerprint;

import android.os.SystemClock;
import android.util.Slog;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintService$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintService.AnonymousClass1 f$0;

    public /* synthetic */ FingerprintService$1$$ExternalSyntheticLambda0(
            FingerprintService.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FingerprintService.AnonymousClass1 anonymousClass1 = this.f$0;
        anonymousClass1.getClass();
        switch (i) {
            case 0:
                if (Utils.DEBUG) {
                    Slog.d("FingerprintService", "onPowerSinglePressed");
                }
                Iterator it = anonymousClass1.this$0.mRegistry.getProviders().iterator();
                while (it.hasNext()) {
                    FingerprintProvider fingerprintProvider =
                            (FingerprintProvider) ((ServiceProvider) it.next());
                    fingerprintProvider.getClass();
                    fingerprintProvider.mPowerSinglePressedTimeStamp =
                            SystemClock.elapsedRealtime();
                }
                break;
            default:
                if (Utils.DEBUG) {
                    Slog.d("FingerprintService", "onPowerPressed");
                }
                Iterator it2 = anonymousClass1.this$0.mRegistry.getProviders().iterator();
                while (it2.hasNext()) {
                    ((FingerprintProvider) ((ServiceProvider) it2.next())).onPowerPressed();
                }
                break;
        }
    }
}
