package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.PointerContext;
import android.util.Slog;

import com.android.server.biometrics.sensors.fingerprint.Udfps;

import java.util.function.Consumer;

public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda15
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ PointerContext f$1;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda15(
            FingerprintProvider fingerprintProvider, PointerContext pointerContext, int i) {
        this.$r8$classId = i;
        this.f$0 = fingerprintProvider;
        this.f$1 = pointerContext;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FingerprintProvider fingerprintProvider = this.f$0;
                PointerContext pointerContext = this.f$1;
                fingerprintProvider.getClass();
                if (!(obj instanceof Udfps)) {
                    Slog.e(
                            fingerprintProvider.getTag$1(),
                            "onPointerUp received during client: " + obj);
                    break;
                } else {
                    ((Udfps) obj).onPointerUp(pointerContext);
                    break;
                }
            default:
                FingerprintProvider fingerprintProvider2 = this.f$0;
                PointerContext pointerContext2 = this.f$1;
                fingerprintProvider2.getClass();
                if (!(obj instanceof Udfps)) {
                    Slog.e(
                            fingerprintProvider2.getTag$1(),
                            "onPointerDown received during client: " + obj);
                    break;
                } else {
                    ((Udfps) obj).onPointerDown(pointerContext2);
                    break;
                }
        }
    }
}
