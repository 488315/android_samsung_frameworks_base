package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemTpaTestHal$1$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemTpaTestHal.AnonymousClass1 f$0;
    public final /* synthetic */ ISessionCallback f$1;

    public /* synthetic */ SemTpaTestHal$1$$ExternalSyntheticLambda2(
            SemTpaTestHal.AnonymousClass1 anonymousClass1,
            ISessionCallback iSessionCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = iSessionCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemTpaTestHal.AnonymousClass1 anonymousClass1 = this.f$0;
                ISessionCallback iSessionCallback = this.f$1;
                if (anonymousClass1.this$0.mEnrolledIds.isEmpty()) {
                    anonymousClass1.this$0.deliverErrorEvent(11, 0);
                    break;
                } else {
                    try {
                        iSessionCallback.onInteractionDetected();
                        break;
                    } catch (RemoteException | NullPointerException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            default:
                SemTpaTestHal.AnonymousClass1 anonymousClass12 = this.f$0;
                ISessionCallback iSessionCallback2 = this.f$1;
                anonymousClass12.getClass();
                try {
                    iSessionCallback2.onChallengeGenerated(anonymousClass12.this$0.mChallenge);
                    break;
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
        }
    }
}
