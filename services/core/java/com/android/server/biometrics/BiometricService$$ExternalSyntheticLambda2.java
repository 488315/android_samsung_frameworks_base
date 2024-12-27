package com.android.server.biometrics;

import android.R;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.PromptInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;

public final /* synthetic */ class BiometricService$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ PromptInfo f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ IBinder f$5;
    public final /* synthetic */ long f$6;
    public final /* synthetic */ IBiometricServiceReceiver f$7;

    public /* synthetic */ BiometricService$$ExternalSyntheticLambda2(
            BiometricService.BiometricServiceWrapper biometricServiceWrapper,
            IBinder iBinder,
            long j,
            long j2,
            int i,
            IBiometricServiceReceiver iBiometricServiceReceiver,
            String str,
            PromptInfo promptInfo) {
        this.f$0 = biometricServiceWrapper;
        this.f$5 = iBinder;
        this.f$4 = j;
        this.f$6 = j2;
        this.f$1 = i;
        this.f$7 = iBiometricServiceReceiver;
        this.f$3 = str;
        this.f$2 = promptInfo;
    }

    public /* synthetic */ BiometricService$$ExternalSyntheticLambda2(
            BiometricService biometricService,
            int i,
            PromptInfo promptInfo,
            String str,
            long j,
            IBinder iBinder,
            long j2,
            IBiometricServiceReceiver iBiometricServiceReceiver) {
        this.f$0 = biometricService;
        this.f$1 = i;
        this.f$2 = promptInfo;
        this.f$3 = str;
        this.f$4 = j;
        this.f$5 = iBinder;
        this.f$6 = j2;
        this.f$7 = iBiometricServiceReceiver;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        switch (this.$r8$classId) {
            case 0:
                BiometricService biometricService = (BiometricService) this.f$0;
                int i = this.f$1;
                PromptInfo promptInfo = this.f$2;
                String str2 = this.f$3;
                long j = this.f$4;
                IBinder iBinder = this.f$5;
                long j2 = this.f$6;
                IBiometricServiceReceiver iBiometricServiceReceiver = this.f$7;
                biometricService.getClass();
                try {
                    str = "BiometricService";
                } catch (RemoteException e) {
                    e = e;
                    str = "BiometricService";
                }
                try {
                    PreAuthInfo create =
                            PreAuthInfo.create(
                                    biometricService.mTrustManager,
                                    biometricService.mDevicePolicyManager,
                                    biometricService.mSettingObserver,
                                    biometricService.mSensors,
                                    i,
                                    promptInfo,
                                    str2,
                                    promptInfo.isDisallowBiometricsIfPolicyExists(),
                                    biometricService.getContext(),
                                    biometricService.mBiometricCameraManager);
                    if (promptInfo.isUseDefaultTitle()
                            && TextUtils.isEmpty(promptInfo.getTitle())) {
                        promptInfo.setTitle(biometricService.getContext().getString(17042805));
                    }
                    int eligibleModalities = create.getEligibleModalities();
                    boolean z = true;
                    boolean z2 = (eligibleModalities & 2) == 2;
                    if ((eligibleModalities & 8) != 8) {
                        z = false;
                    }
                    if (promptInfo.isUseDefaultSubtitle()) {
                        if (z2 && z) {
                            promptInfo.setSubtitle(
                                    biometricService
                                            .getContext()
                                            .getString(
                                                    R.string
                                                            .config_defaultProfcollectReportUploaderAction));
                        } else if (z2) {
                            promptInfo.setSubtitle(
                                    biometricService
                                            .getContext()
                                            .getString(R.string.lockscreen_sound_off_label));
                        } else if (z) {
                            promptInfo.setSubtitle(
                                    biometricService
                                            .getContext()
                                            .getString(
                                                    R.string
                                                            .language_picker_section_suggested_bilingual));
                        } else {
                            promptInfo.setSubtitle(
                                    biometricService.getContext().getString(17042723));
                        }
                    }
                    Pair preAuthenticateStatus = create.getPreAuthenticateStatus();
                    Slog.d(
                            str,
                            "handleAuthenticate: modality("
                                    + preAuthenticateStatus.first
                                    + "), status("
                                    + preAuthenticateStatus.second
                                    + "), preAuthInfo: "
                                    + create
                                    + " requestId: "
                                    + j
                                    + " promptInfo.isIgnoreEnrollmentState: "
                                    + promptInfo.isIgnoreEnrollmentState());
                    if (((Integer) preAuthenticateStatus.second).intValue() != 0) {
                        iBiometricServiceReceiver.onError(
                                ((Integer) preAuthenticateStatus.first).intValue(),
                                ((Integer) preAuthenticateStatus.second).intValue(),
                                0);
                        break;
                    } else {
                        if (create.credentialRequested
                                && create.credentialAvailable
                                && create.eligibleSensors.isEmpty()) {
                            promptInfo.setAuthenticators(32768);
                        }
                        biometricService.authenticateInternal(
                                iBinder,
                                j,
                                j2,
                                i,
                                iBiometricServiceReceiver,
                                str2,
                                promptInfo,
                                create);
                        break;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    Slog.e(str, "Remote exception", e);
                    return;
                }
            default:
                BiometricService.BiometricServiceWrapper biometricServiceWrapper =
                        (BiometricService.BiometricServiceWrapper) this.f$0;
                IBinder iBinder2 = this.f$5;
                long j3 = this.f$4;
                long j4 = this.f$6;
                int i2 = this.f$1;
                IBiometricServiceReceiver iBiometricServiceReceiver2 = this.f$7;
                String str3 = this.f$3;
                PromptInfo promptInfo2 = this.f$2;
                BiometricService biometricService2 = BiometricService.this;
                biometricService2.getClass();
                biometricService2.mHandler.post(
                        new BiometricService$$ExternalSyntheticLambda2(
                                biometricService2,
                                i2,
                                promptInfo2,
                                str3,
                                j3,
                                iBinder2,
                                j4,
                                iBiometricServiceReceiver2));
                break;
        }
    }
}
