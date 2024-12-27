package com.android.server.biometrics.sensors.fingerprint;

import android.os.RemoteException;
import android.util.Slog;

import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemFpAodController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpAodController f$0;

    public /* synthetic */ SemFpAodController$$ExternalSyntheticLambda0(
            SemFpAodController semFpAodController, int i) {
        this.$r8$classId = i;
        this.f$0 = semFpAodController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ISemFingerprintAodController iSemFingerprintAodController;
        ISemFingerprintAodController iSemFingerprintAodController2;
        ISemFingerprintAodController iSemFingerprintAodController3;
        ISemFingerprintAodController iSemFingerprintAodController4;
        int i = this.$r8$classId;
        SemFpAodController semFpAodController = this.f$0;
        switch (i) {
            case 0:
                semFpAodController.mAodController = null;
                break;
            case 1:
                ISemFingerprintAodController iSemFingerprintAodController5 =
                        semFpAodController.mAodController;
                if (iSemFingerprintAodController5 != null) {
                    try {
                        iSemFingerprintAodController5.hideAodScreen();
                        break;
                    } catch (RemoteException e) {
                        Slog.w("SemFpAodController", "hideAodScreen: " + e.getMessage());
                        return;
                    }
                }
                break;
            case 2:
                if (semFpAodController.mIsHlpmMode
                        && (iSemFingerprintAodController = semFpAodController.mAodController)
                                != null) {
                    try {
                        iSemFingerprintAodController.turnOffDozeHlpmMode();
                    } catch (RemoteException e2) {
                        Slog.w("SemFpAodController", "turnOffDozeHlpmMode: " + e2.getMessage());
                    }
                }
                semFpAodController.mIsHlpmMode = false;
                break;
            case 3:
                if (semFpAodController.mIsDozeMode
                        && (iSemFingerprintAodController2 = semFpAodController.mAodController)
                                != null) {
                    try {
                        iSemFingerprintAodController2.turnOffDozeMode();
                    } catch (RemoteException e3) {
                        Slog.w("SemFpAodController", "turnOffDozeMode: " + e3.getMessage());
                    }
                }
                semFpAodController.mIsDozeMode = false;
                break;
            case 4:
                if (!semFpAodController.mIsHlpmMode
                        && (iSemFingerprintAodController3 = semFpAodController.mAodController)
                                != null) {
                    try {
                        iSemFingerprintAodController3.turnOnDozeHlpmMode();
                    } catch (RemoteException e4) {
                        Slog.w("SemFpAodController", "turnOnDozeHlpmMode: " + e4.getMessage());
                    }
                }
                semFpAodController.mIsHlpmMode = true;
                break;
            default:
                if (!semFpAodController.mIsDozeMode
                        && (iSemFingerprintAodController4 = semFpAodController.mAodController)
                                != null) {
                    try {
                        iSemFingerprintAodController4.turnOnDozeMode();
                    } catch (RemoteException e5) {
                        Slog.w("SemFpAodController", "turnOnDozeMode: " + e5.getMessage());
                    }
                }
                semFpAodController.mIsDozeMode = true;
                break;
        }
    }
}
