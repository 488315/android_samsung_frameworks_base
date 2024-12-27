package com.android.server.biometrics;

import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.util.Slog;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

public final /* synthetic */ class SemBiometricSysUiManager$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ SemBiometricSysUiManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ String f$5;
    public final /* synthetic */ long f$6;
    public final /* synthetic */ Bundle f$7;
    public final /* synthetic */ PromptInfo f$8;

    public /* synthetic */ SemBiometricSysUiManager$$ExternalSyntheticLambda5(
            SemBiometricSysUiManager semBiometricSysUiManager,
            int i,
            int i2,
            boolean z,
            int i3,
            String str,
            long j,
            Bundle bundle,
            PromptInfo promptInfo) {
        this.f$0 = semBiometricSysUiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
        this.f$4 = i3;
        this.f$5 = str;
        this.f$6 = j;
        this.f$7 = bundle;
        this.f$8 = promptInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemBiometricSysUiManager semBiometricSysUiManager = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        boolean z = this.f$3;
        int i3 = this.f$4;
        String str = this.f$5;
        long j = this.f$6;
        Bundle bundle = this.f$7;
        PromptInfo promptInfo = this.f$8;
        semBiometricSysUiManager.getClass();
        if (Utils.DEBUG) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "show: ", ", ", ", ");
            m.append(z);
            m.append(", ");
            m.append(i3);
            m.append(", ");
            m.append(str);
            m.append(", ");
            m.append(j);
            Slog.d("BiometricSysUiManager", m.toString());
        }
        if (semBiometricSysUiManager.findSessionId(i) == null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "show: No exist ID, ", "BiometricSysUiManager");
            return;
        }
        try {
            semBiometricSysUiManager.mSysUiService.showBiometricDialog(
                    i,
                    i2,
                    bundle,
                    semBiometricSysUiManager.mSysUiServiceReceiver,
                    z,
                    i3,
                    str,
                    j,
                    promptInfo);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "show: " + e.getMessage());
            semBiometricSysUiManager.sendConnectionError(-1);
        }
    }
}
