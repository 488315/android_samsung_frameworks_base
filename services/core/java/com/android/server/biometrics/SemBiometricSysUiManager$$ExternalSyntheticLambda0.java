package com.android.server.biometrics;

import android.util.Slog;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemBiometricSysUiManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemBiometricSysUiManager f$0;

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda0(
            SemBiometricSysUiManager semBiometricSysUiManager, int i) {
        this.$r8$classId = i;
        this.f$0 = semBiometricSysUiManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBiometricSysUiManager semBiometricSysUiManager = this.f$0;
                semBiometricSysUiManager.notifySysUiErrorEvent(0, 1, 0);
                semBiometricSysUiManager.cleanup();
                return;
            case 1:
                SemBiometricSysUiManager semBiometricSysUiManager2 = this.f$0;
                synchronized (semBiometricSysUiManager2) {
                    try {
                        Iterator it =
                                ((ArrayList) semBiometricSysUiManager2.mPendingCommand).iterator();
                        while (it.hasNext()) {
                            ((Runnable) it.next()).run();
                        }
                        ((ArrayList) semBiometricSysUiManager2.mPendingCommand).clear();
                    } finally {
                    }
                }
                return;
            case 2:
                SemBiometricSysUiManager semBiometricSysUiManager3 = this.f$0;
                synchronized (semBiometricSysUiManager3) {
                    try {
                        if (((ArrayList) semBiometricSysUiManager3.mSessionList).isEmpty()
                                && semBiometricSysUiManager3.mIsConnected
                                && !semBiometricSysUiManager3.mKeepBind.get()) {
                            semBiometricSysUiManager3.unBind();
                        }
                    } finally {
                    }
                }
                return;
            default:
                SemBiometricSysUiManager semBiometricSysUiManager4 = this.f$0;
                semBiometricSysUiManager4.getClass();
                Slog.w("BiometricSysUiManager", "handle binding timeout");
                semBiometricSysUiManager4.notifySysUiErrorEvent(0, 2, 2);
                semBiometricSysUiManager4.unBind();
                return;
        }
    }
}
