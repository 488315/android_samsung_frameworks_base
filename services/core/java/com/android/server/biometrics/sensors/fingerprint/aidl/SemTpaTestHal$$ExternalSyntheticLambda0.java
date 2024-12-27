package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemTpaTestHal$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SemTpaTestHal$$ExternalSyntheticLambda0(
            ISessionCallback iSessionCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = iSessionCallback;
    }

    public /* synthetic */ SemTpaTestHal$$ExternalSyntheticLambda0(SemTpaTestHal semTpaTestHal) {
        this.$r8$classId = 0;
        this.f$0 = semTpaTestHal;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SemTpaTestHal semTpaTestHal = (SemTpaTestHal) obj;
                semTpaTestHal.mTestHalHelper.initActions();
                SemTestSehFingerprint semTestSehFingerprint = semTpaTestHal.mSehFingerprint;
                semTestSehFingerprint.mRequestActionTable.clear();
                semTestSehFingerprint.mRequestActionTable.put(6, 100040);
                break;
            case 1:
                try {
                    ((ISessionCallback) obj).onError((byte) 5, 0);
                    break;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            case 2:
                try {
                    ((ISessionCallback) obj).onError((byte) 5, 0);
                    break;
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return;
                }
            default:
                try {
                    ((ISessionCallback) obj).onError((byte) 5, 0);
                    break;
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
        }
    }
}
