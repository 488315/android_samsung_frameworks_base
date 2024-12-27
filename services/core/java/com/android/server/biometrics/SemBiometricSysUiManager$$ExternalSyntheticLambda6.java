package com.android.server.biometrics;

import android.os.Bundle;
import android.util.Slog;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBiometricSysUiManager;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemBiometricSysUiManager$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda6(SemBiometricSysUiManager.AnonymousClass1 anonymousClass1, int i, int i2, byte[] bArr) {
        this.$r8$classId = 2;
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = bArr;
    }

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda6(SemBiometricSysUiManager semBiometricSysUiManager, int i, int i2) {
        this.$r8$classId = 1;
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = null;
    }

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda6(SemBiometricSysUiManager semBiometricSysUiManager, int i, int i2, Bundle bundle) {
        this.$r8$classId = 0;
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBiometricSysUiManager semBiometricSysUiManager = (SemBiometricSysUiManager) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                Bundle bundle = (Bundle) this.f$3;
                if (((ArrayList) semBiometricSysUiManager.mSessionList).isEmpty()) {
                    Slog.d("BiometricSysUiManager", "sendCommandIfSessionExist: No exist Session");
                    return;
                }
                try {
                    semBiometricSysUiManager.mSysUiService.sendCommand(0, i, i2, bundle);
                    return;
                } catch (Exception e) {
                    MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("sendCommandIfSessionExist: "), "BiometricSysUiManager");
                    return;
                }
            case 1:
                SemBiometricSysUiManager semBiometricSysUiManager2 = (SemBiometricSysUiManager) this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                Bundle bundle2 = (Bundle) this.f$3;
                semBiometricSysUiManager2.getClass();
                try {
                    semBiometricSysUiManager2.mSysUiService.sendCommand(0, i3, i4, bundle2);
                    return;
                } catch (Exception e2) {
                    MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e2, new StringBuilder("sendCommand: "), "BiometricSysUiManager");
                    return;
                }
            default:
                SemBiometricSysUiManager.AnonymousClass1 anonymousClass1 = (SemBiometricSysUiManager.AnonymousClass1) this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$2;
                byte[] bArr = (byte[]) this.f$3;
                SemBiometricSysUiManager semBiometricSysUiManager3 = anonymousClass1.this$0;
                synchronized (semBiometricSysUiManager3) {
                    Iterator it = ((ArrayList) semBiometricSysUiManager3.mSessionList).iterator();
                    while (it.hasNext()) {
                        SemBiometricSysUiManager.SysUiServiceSession sysUiServiceSession = (SemBiometricSysUiManager.SysUiServiceSession) it.next();
                        try {
                            SemBiometricSysUiManager.SysUiListener sysUiListener = sysUiServiceSession.mListener;
                            if (sysUiListener != null && (i5 == 0 || i5 == sysUiServiceSession.mId)) {
                                sysUiListener.onDismissed(i6, bArr);
                            }
                        } catch (Exception e3) {
                            Slog.w("BiometricSysUiManager", "notifySysUiDismissedEvent: " + e3.getMessage());
                        }
                    }
                }
                return;
        }
    }
}
