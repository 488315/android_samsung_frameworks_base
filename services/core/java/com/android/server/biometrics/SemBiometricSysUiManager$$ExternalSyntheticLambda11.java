package com.android.server.biometrics;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemBiometricSysUiManager$$ExternalSyntheticLambda11
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemBiometricSysUiManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ String f$5;

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda11(
            SemBiometricSysUiManager semBiometricSysUiManager, int i, int i2, int i3, int i4) {
        this.$r8$classId = 0;
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = i4;
        this.f$5 = null;
    }

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda11(
            SemBiometricSysUiManager semBiometricSysUiManager,
            int i,
            int i2,
            int i3,
            int i4,
            String str) {
        this.$r8$classId = 1;
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = i4;
        this.f$5 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBiometricSysUiManager semBiometricSysUiManager = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = this.f$3;
                int i4 = this.f$4;
                String str = this.f$5;
                if (semBiometricSysUiManager.findSessionId(i) == null) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            i, "onBiometricError: No exist ID, ", "BiometricSysUiManager");
                    break;
                } else {
                    try {
                        semBiometricSysUiManager.mSysUiService.onBiometricError(i, i2, i3, i4, str);
                        break;
                    } catch (Exception e) {
                        MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                                e,
                                new StringBuilder("onBiometricError: "),
                                "BiometricSysUiManager");
                        return;
                    }
                }
            default:
                SemBiometricSysUiManager semBiometricSysUiManager2 = this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$2;
                int i7 = this.f$3;
                int i8 = this.f$4;
                String str2 = this.f$5;
                semBiometricSysUiManager2.getClass();
                if (Utils.DEBUG) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i5, "onBiometricHelp: session Id = [", "]", "BiometricSysUiManager");
                }
                if (semBiometricSysUiManager2.findSessionId(i5) == null) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            i5, "onBiometricHelp: No exist ID, ", "BiometricSysUiManager");
                    break;
                } else {
                    try {
                        semBiometricSysUiManager2.mSysUiService.onBiometricHelp(
                                i5, i6, i7, i8, str2);
                        break;
                    } catch (Exception e2) {
                        MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                                e2,
                                new StringBuilder("onBiometricHelp: "),
                                "BiometricSysUiManager");
                    }
                }
        }
    }
}
