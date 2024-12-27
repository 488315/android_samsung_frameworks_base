package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.face.FaceManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFingerprintAuthenticationClient$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFingerprintAuthenticationClient f$0;

    public /* synthetic */ SemFingerprintAuthenticationClient$$ExternalSyntheticLambda0(
            SemFingerprintAuthenticationClient semFingerprintAuthenticationClient, int i) {
        this.$r8$classId = i;
        this.f$0 = semFingerprintAuthenticationClient;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SemFingerprintAuthenticationClient semFingerprintAuthenticationClient = this.f$0;
        switch (i) {
            case 0:
                FaceManager faceManager =
                        (FaceManager)
                                semFingerprintAuthenticationClient.mContext.getSystemService(
                                        FaceManager.class);
                if (faceManager != null) {
                    faceManager.semResumeAuth();
                    break;
                }
                break;
            case 1:
                FaceManager faceManager2 =
                        (FaceManager)
                                semFingerprintAuthenticationClient.mContext.getSystemService(
                                        FaceManager.class);
                if (faceManager2 != null) {
                    faceManager2.semPauseAuth();
                    break;
                }
                break;
            default:
                semFingerprintAuthenticationClient.startHalOperation();
                break;
        }
    }
}
