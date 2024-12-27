package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.ISession;
import android.util.Slog;

import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda3;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter;
import com.android.server.biometrics.sensors.fingerprint.hidl.SemHidlToAidlSehFingerprintAdapter;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AidlSession {
    public final SemFpAidlResponseHandler mAidlResponseHandler;
    public final int mHalInterfaceVersion;
    public Function mLazySehFingerprint;
    public final ISession mSession;
    public final int mUserId;

    public AidlSession(
            int i, ISession iSession, int i2, SemFpAidlResponseHandler semFpAidlResponseHandler) {
        this.mHalInterfaceVersion = i;
        this.mSession = iSession;
        this.mUserId = i2;
        this.mAidlResponseHandler = semFpAidlResponseHandler;
        HermesService$3$$ExternalSyntheticOutline0.m(
                i, "AidlSession: Ver = ", "FingerprintService");
    }

    public AidlSession(
            final HidlToAidlSensorAdapter$$ExternalSyntheticLambda3
                    hidlToAidlSensorAdapter$$ExternalSyntheticLambda3,
            int i,
            SemFpAidlResponseHandler semFpAidlResponseHandler) {
        this.mSession =
                new HidlToAidlSessionAdapter(
                        hidlToAidlSensorAdapter$$ExternalSyntheticLambda3,
                        i,
                        semFpAidlResponseHandler);
        this.mHalInterfaceVersion = 0;
        this.mUserId = i;
        this.mAidlResponseHandler = semFpAidlResponseHandler;
        this.mLazySehFingerprint =
                new Function() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return new SemHidlToAidlSehFingerprintAdapter(
                                (HidlToAidlSensorAdapter$$ExternalSyntheticLambda3)
                                        hidlToAidlSensorAdapter$$ExternalSyntheticLambda3);
                    }
                };
        Slog.i("FingerprintService", "AidlSession: Ver = 0");
    }

    public final boolean hasContextMethods() {
        return this.mHalInterfaceVersion >= 2;
    }
}
