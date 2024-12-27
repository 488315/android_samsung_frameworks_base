package com.android.server.biometrics.sensors.fingerprint.aidl;

import com.android.server.biometrics.sensors.LockoutConsumer;

import java.util.function.Consumer;

public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ long f$0;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda2(long j, int i) {
        this.$r8$classId = i;
        this.f$0 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FingerprintGetAuthenticatorIdClient fingerprintGetAuthenticatorIdClient =
                        (FingerprintGetAuthenticatorIdClient) obj;
                fingerprintGetAuthenticatorIdClient.mAuthenticatorIds.put(
                        Integer.valueOf(fingerprintGetAuthenticatorIdClient.mTargetUserId),
                        Long.valueOf(this.f$0));
                fingerprintGetAuthenticatorIdClient.mCallback.onClientFinished(
                        fingerprintGetAuthenticatorIdClient, true);
                break;
            case 1:
                ((LockoutConsumer) obj).onLockoutTimed(this.f$0);
                break;
            default:
                ((FingerprintInvalidationClient) obj).onAuthenticatorIdInvalidated(this.f$0);
                break;
        }
    }
}
