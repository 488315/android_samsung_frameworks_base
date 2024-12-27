package com.android.server.biometrics.sensors.face.aidl;

import com.android.server.biometrics.sensors.LockoutConsumer;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ long f$0;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda3(long j, int i) {
        this.$r8$classId = i;
        this.f$0 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FaceGetAuthenticatorIdClient faceGetAuthenticatorIdClient =
                        (FaceGetAuthenticatorIdClient) obj;
                faceGetAuthenticatorIdClient.mAuthenticatorIds.put(
                        Integer.valueOf(faceGetAuthenticatorIdClient.mTargetUserId),
                        Long.valueOf(this.f$0));
                faceGetAuthenticatorIdClient.mCallback.onClientFinished(
                        faceGetAuthenticatorIdClient, true);
                break;
            case 1:
                ((FaceInvalidationClient) obj).onAuthenticatorIdInvalidated(this.f$0);
                break;
            default:
                ((LockoutConsumer) obj).onLockoutTimed(this.f$0);
                break;
        }
    }
}
