package com.android.server.biometrics;

import android.util.Slog;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemBiometricSysUiManager$$ExternalSyntheticLambda8
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda8(
            SemBiometricSysUiManager.AnonymousClass1 anonymousClass1,
            int i,
            int i2,
            int i3,
            int i4) {
        this.$r8$classId = i4;
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda8(
            SemBiometricSysUiManager semBiometricSysUiManager, int i) {
        this.$r8$classId = 0;
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = 0;
        this.f$3 = 0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBiometricSysUiManager semBiometricSysUiManager =
                        (SemBiometricSysUiManager) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = this.f$3;
                semBiometricSysUiManager.getClass();
                if (Utils.DEBUG) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "hide: ", ", ", ", "),
                            i3,
                            "BiometricSysUiManager");
                }
                if (semBiometricSysUiManager.findSessionId(i) == null) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            i, "hide: No exist ID, ", "BiometricSysUiManager");
                    return;
                }
                try {
                    semBiometricSysUiManager.mSysUiService.hideBiometricDialog(i, i2, i3);
                    return;
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "hide: " + e.getMessage());
                    semBiometricSysUiManager.sendConnectionError(-1);
                    return;
                }
            case 1:
                SemBiometricSysUiManager.AnonymousClass1 anonymousClass1 =
                        (SemBiometricSysUiManager.AnonymousClass1) this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                SemBiometricSysUiManager semBiometricSysUiManager2 = anonymousClass1.this$0;
                synchronized (semBiometricSysUiManager2) {
                    try {
                        Iterator it =
                                ((ArrayList) semBiometricSysUiManager2.mSessionList).iterator();
                        while (it.hasNext()) {
                            SemBiometricSysUiManager.SysUiServiceSession sysUiServiceSession =
                                    (SemBiometricSysUiManager.SysUiServiceSession) it.next();
                            try {
                                SemBiometricSysUiManager.SysUiListener sysUiListener =
                                        sysUiServiceSession.mListener;
                                if (sysUiListener != null
                                        && (i4 == 0 || i4 == sysUiServiceSession.mId)) {
                                    if (i5 != 1001) {
                                        sysUiListener.onEvent(i5, i6);
                                    } else {
                                        sysUiListener.onTryAgainPressed(i6);
                                    }
                                }
                            } catch (Exception e2) {
                                Slog.w(
                                        "BiometricSysUiManager",
                                        "notifySysUiEvent: " + e2.getMessage());
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                ((SemBiometricSysUiManager.AnonymousClass1) this.f$0)
                        .this$0.notifySysUiErrorEvent(this.f$1, this.f$2, this.f$3);
                return;
        }
    }
}
