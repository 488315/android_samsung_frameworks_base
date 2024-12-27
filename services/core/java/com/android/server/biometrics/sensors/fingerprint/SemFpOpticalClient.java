package com.android.server.biometrics.sensors.fingerprint;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpOpticalClient implements IBinder.DeathRecipient, SemBiometricSysUiManager.SysUiListener {
    public final String mCalibrationColor;
    public SemUdfpsOpticalHelper$$ExternalSyntheticLambda5 mCallback;
    public final Handler mHandler;
    public final boolean mIsCalibrationMode;
    public final boolean mIsKeyguard;
    public final String mPackageName;
    public int mSessionId;
    public final SemBiometricSysUiManager mSysUiManager;
    public final IBinder mToken;

    public SemFpOpticalClient(IBinder iBinder, String str, boolean z, String str2) {
        this(iBinder, str, z, str2, SemBiometricSysUiManager.sInstance, BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler());
    }

    public SemFpOpticalClient(IBinder iBinder, String str, boolean z, String str2, SemBiometricSysUiManager semBiometricSysUiManager, Handler handler) {
        this.mHandler = handler;
        this.mSysUiManager = semBiometricSysUiManager;
        this.mToken = iBinder;
        this.mPackageName = str;
        this.mIsCalibrationMode = z;
        this.mCalibrationColor = str2;
        if (Constants.SYSTEMUI_PACKAGE_NAME.equals(str)) {
            this.mIsKeyguard = true;
        }
        try {
            iBinder.linkToDeath(this, 0);
        } catch (RemoteException e) {
            Slog.w("FingerprintService", "SemFpOpticalClient: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("SemFpOpticalClient: binderDied, "), this.mPackageName, "FingerprintService");
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpOpticalClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFpOpticalClient semFpOpticalClient = SemFpOpticalClient.this;
                SemUdfpsOpticalHelper$$ExternalSyntheticLambda5 semUdfpsOpticalHelper$$ExternalSyntheticLambda5 = semFpOpticalClient.mCallback;
                if (semUdfpsOpticalHelper$$ExternalSyntheticLambda5 != null) {
                    SemUdfpsOpticalHelper semUdfpsOpticalHelper = semUdfpsOpticalHelper$$ExternalSyntheticLambda5.f$0;
                    semUdfpsOpticalHelper.getClass();
                    semUdfpsOpticalHelper.removeMaskView(semFpOpticalClient.mToken);
                }
            }
        });
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
    public final void onError(final int i, int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpOpticalClient$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemUdfpsOpticalHelper$$ExternalSyntheticLambda5 semUdfpsOpticalHelper$$ExternalSyntheticLambda5;
                SemFpOpticalClient semFpOpticalClient = SemFpOpticalClient.this;
                int i3 = i;
                semFpOpticalClient.getClass();
                if ((i3 == 1 || i3 == 2 || i3 == 3) && (semUdfpsOpticalHelper$$ExternalSyntheticLambda5 = semFpOpticalClient.mCallback) != null) {
                    SemUdfpsOpticalHelper semUdfpsOpticalHelper = semUdfpsOpticalHelper$$ExternalSyntheticLambda5.f$0;
                    semUdfpsOpticalHelper.getClass();
                    semUdfpsOpticalHelper.removeMaskView(semFpOpticalClient.mToken);
                }
            }
        });
    }

    public final void start(SemUdfpsOpticalHelper$$ExternalSyntheticLambda5 semUdfpsOpticalHelper$$ExternalSyntheticLambda5) {
        int i;
        this.mCallback = semUdfpsOpticalHelper$$ExternalSyntheticLambda5;
        Bundle bundle = new Bundle();
        bundle.putString("KEY_PACKAGE_NAME", this.mPackageName);
        bundle.putBoolean("KEY_KEYGUARD", this.mIsKeyguard);
        if (this.mIsCalibrationMode) {
            bundle.putString("nits", this.mCalibrationColor);
            i = EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_INGRESS;
        } else {
            i = 500;
        }
        int openSession = this.mSysUiManager.openSession(toString(), this);
        this.mSessionId = openSession;
        this.mSysUiManager.sendCommand(bundle, openSession, i, 1);
    }

    public final void stop() {
        try {
            this.mToken.unlinkToDeath(this, 0);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("SemFpOpticalClient: stop: "), "FingerprintService");
        }
        this.mSysUiManager.sendCommand(null, this.mSessionId, this.mIsCalibrationMode ? EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_INGRESS : 500, 0);
        this.mSysUiManager.closeSession(this.mSessionId, 4000L);
    }
}
