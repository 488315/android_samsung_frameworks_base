package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.PromptInfo;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Slog;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.AnonymousClass6;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda18
        implements Runnable {
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ FingerprintAuthenticateOptions f$1;
    public final /* synthetic */ Bundle f$10;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ ClientMonitorCallbackConverter f$4;
    public final /* synthetic */ long f$5;
    public final /* synthetic */ boolean f$6;
    public final /* synthetic */ int f$7;
    public final /* synthetic */ int f$8;
    public final /* synthetic */ boolean f$9;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda18(
            FingerprintProvider fingerprintProvider,
            FingerprintAuthenticateOptions fingerprintAuthenticateOptions,
            IBinder iBinder,
            long j,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            long j2,
            boolean z,
            int i,
            int i2,
            boolean z2,
            Bundle bundle) {
        this.f$0 = fingerprintProvider;
        this.f$1 = fingerprintAuthenticateOptions;
        this.f$2 = iBinder;
        this.f$3 = j;
        this.f$4 = clientMonitorCallbackConverter;
        this.f$5 = j2;
        this.f$6 = z;
        this.f$7 = i;
        this.f$8 = i2;
        this.f$9 = z2;
        this.f$10 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FingerprintProvider fingerprintProvider = this.f$0;
        FingerprintAuthenticateOptions fingerprintAuthenticateOptions = this.f$1;
        IBinder iBinder = this.f$2;
        long j = this.f$3;
        ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.f$4;
        long j2 = this.f$5;
        boolean z = this.f$6;
        int i = this.f$7;
        int i2 = this.f$8;
        boolean z2 = this.f$9;
        Bundle bundle = this.f$10;
        fingerprintProvider.getClass();
        int userId = fingerprintAuthenticateOptions.getUserId();
        int sensorId = fingerprintAuthenticateOptions.getSensorId();
        final SemFingerprintAuthenticationClient semFingerprintAuthenticationClient =
                new SemFingerprintAuthenticationClient(
                        fingerprintProvider.mContext,
                        ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(sensorId))
                                .mLazySession,
                        iBinder,
                        j,
                        clientMonitorCallbackConverter,
                        j2,
                        z,
                        fingerprintAuthenticateOptions,
                        i,
                        fingerprintProvider.createLogger$1(
                                2, i2, fingerprintProvider.mAuthenticationStatsCollector),
                        fingerprintProvider.mBiometricContext,
                        Utils.isStrongBiometric(sensorId),
                        fingerprintProvider.mTaskStackListener,
                        fingerprintProvider.mAuthenticationStateListeners,
                        z2,
                        ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(sensorId))
                                .mSensorProperties,
                        Utils.getCurrentStrength(sensorId),
                        ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(sensorId))
                                .getLockoutTracker(true),
                        bundle,
                        fingerprintProvider.mPowerSinglePressedTimeStamp);
        semFingerprintAuthenticationClient.mShouldVibrate = true;
        if (semFingerprintAuthenticationClient.isBiometricPrompt()) {
            semFingerprintAuthenticationClient.mPromptInfo =
                    semFingerprintAuthenticationClient.getBiometricPromptInfo(
                            semFingerprintAuthenticationClient.mCookie);
        }
        if (semFingerprintAuthenticationClient.mIsKeyguard) {
            semFingerprintAuthenticationClient.mPrivilegedFlags |= 27;
        } else if (semFingerprintAuthenticationClient.mIsSettingApp) {
            semFingerprintAuthenticationClient.mPrivilegedFlags |= 2;
        }
        semFingerprintAuthenticationClient.mPrivilegedFlags |=
                semFingerprintAuthenticationClient.mAttribute.getInt("sem_privileged_attr", 0);
        PromptInfo promptInfo = semFingerprintAuthenticationClient.mPromptInfo;
        if (promptInfo != null) {
            int semGetPrivilegedFlag = promptInfo.semGetPrivilegedFlag();
            semFingerprintAuthenticationClient.mPromptPrivilegedFlags = semGetPrivilegedFlag;
            if ((semGetPrivilegedFlag & 1) != 0 || (semGetPrivilegedFlag & 4) != 0) {
                semFingerprintAuthenticationClient.mPrivilegedFlags |= 1;
            }
        }
        if (semFingerprintAuthenticationClient.hasPrivilegedFlag(1)) {
            semFingerprintAuthenticationClient.mCanIgnoreLockout = true;
        }
        if (semFingerprintAuthenticationClient.hasPrivilegedFlag(8)) {
            semFingerprintAuthenticationClient.mShouldVibrate = false;
        }
        if (semFingerprintAuthenticationClient.semIsAllowedBackgroundAuthentication()) {
            semFingerprintAuthenticationClient.mPrivilegedFlags |= 4;
        }
        PromptInfo promptInfo2 = semFingerprintAuthenticationClient.mPromptInfo;
        if (promptInfo2 != null) {
            promptInfo2.semGetDisplayId();
        } else {
            semFingerprintAuthenticationClient.mAttribute.getInt("EXTRA_KEY_DISPLAY_ID");
        }
        Slog.i(
                "FingerprintAuthenticationClient.Ext",
                "FingerprintClientExt: "
                        + Integer.toBinaryString(
                                semFingerprintAuthenticationClient.mPrivilegedFlags)
                        + ", "
                        + Integer.toBinaryString(
                                semFingerprintAuthenticationClient.mPromptPrivilegedFlags));
        semFingerprintAuthenticationClient.mShouldCancelIfBackgroundAuthentication = true;
        if (semFingerprintAuthenticationClient.mSensorProps.isAnyUdfpsType()) {
            SemUdfpsSysUiImpl createUdfpsSysUiImpl =
                    semFingerprintAuthenticationClient.createUdfpsSysUiImpl(
                            semFingerprintAuthenticationClient.hasPrivilegedFlag(16)
                                    || (semFingerprintAuthenticationClient.mAttribute.getInt(
                                                            "EXTRA_KEY_AUTH_FLAG", 0)
                                                    & 32768)
                                            != 0);
            semFingerprintAuthenticationClient.mUdfpsImpl = createUdfpsSysUiImpl;
            if (semFingerprintAuthenticationClient.mIsKeyguard) {
                createUdfpsSysUiImpl.mSysUiType = 4;
            }
            if (!semFingerprintAuthenticationClient.isBiometricPrompt()) {
                semFingerprintAuthenticationClient.mUdfpsImpl.checkGuideLayerAndTouchBlock(
                        semFingerprintAuthenticationClient.mPrivilegedFlags);
                SemUdfpsSysUiImpl semUdfpsSysUiImpl = semFingerprintAuthenticationClient.mUdfpsImpl;
                Bundle bundle2 = semFingerprintAuthenticationClient.mAttribute;
                int i3 = semFingerprintAuthenticationClient.mPrivilegedFlags;
                semUdfpsSysUiImpl.getClass();
                if (bundle2.getString("EXTRA_KEY_ICON_COLOR") != null
                        || bundle2.getString("EXTRA_KEY_ICON_CONTAINER_COLOR") != null
                        || (i3 & 32) != 0) {
                    Bundle bundle3 = new Bundle();
                    semUdfpsSysUiImpl.mCustomIconAttribute = bundle3;
                    bundle3.putString(
                            "EXTRA_KEY_ICON_COLOR", bundle2.getString("EXTRA_KEY_ICON_COLOR"));
                    semUdfpsSysUiImpl.mCustomIconAttribute.putString(
                            "EXTRA_KEY_ICON_CONTAINER_COLOR",
                            bundle2.getString("EXTRA_KEY_ICON_CONTAINER_COLOR"));
                    semUdfpsSysUiImpl.mCustomIconAttribute.putInt("sem_privileged_attr", i3);
                }
                semFingerprintAuthenticationClient.mUdfpsImpl.setSysUiListener(
                        new SemUdfpsSysUiImpl
                                .SysUiCallback() { // from class:
                                                   // com.android.server.biometrics.sensors.fingerprint.aidl.SemFingerprintAuthenticationClient.1
                            public AnonymousClass1() {}

                            @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
                            public final void onSysUiDismissed() {
                                SemFingerprintAuthenticationClient.this.onUserCanceled();
                            }

                            @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
                            public final void onSysUiError(int i4, int i5) {
                                SemFingerprintAuthenticationClient
                                        semFingerprintAuthenticationClient2 =
                                                SemFingerprintAuthenticationClient.this;
                                if (i4 == 2
                                        && i5 == 2
                                        && semFingerprintAuthenticationClient2.mIsKeyguard) {
                                    semFingerprintAuthenticationClient2.mErrorEscrow = 8;
                                    semFingerprintAuthenticationClient2.mVendorErrorEscrow = 5000;
                                }
                                if (semFingerprintAuthenticationClient2.mAuthAttempted) {
                                    semFingerprintAuthenticationClient2.cancel();
                                } else {
                                    semFingerprintAuthenticationClient2.onError(5, 0);
                                }
                            }
                        });
            }
        }
        fingerprintProvider.scheduleForSensor$1(
                sensorId,
                semFingerprintAuthenticationClient,
                fingerprintProvider.new AnonymousClass6(userId, sensorId, j));
    }
}
