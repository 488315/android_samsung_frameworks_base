package com.android.server.biometrics;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemBiometricSysUiManager$$ExternalSyntheticLambda10
        implements Runnable {
    public final /* synthetic */ SemBiometricSysUiManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ String f$4;

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda10(
            SemBiometricSysUiManager semBiometricSysUiManager,
            int i,
            int i2,
            boolean z,
            String str) {
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
        this.f$4 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemBiometricSysUiManager semBiometricSysUiManager = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        boolean z = this.f$3;
        String str = this.f$4;
        if (semBiometricSysUiManager.findSessionId(i) == null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "onAuthenticated: No exist ID, ", "BiometricSysUiManager");
            return;
        }
        try {
            semBiometricSysUiManager.mSysUiService.onBiometricAuthenticated(i, i2, z, str);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("onAuthenticated: "), "BiometricSysUiManager");
        }
    }
}
