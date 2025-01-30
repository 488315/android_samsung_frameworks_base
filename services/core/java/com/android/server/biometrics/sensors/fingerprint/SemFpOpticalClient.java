package com.android.server.biometrics.sensors.fingerprint;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.samsung.android.knox.p024zt.devicetrust.EndpointMonitorConst;

/* loaded from: classes.dex */
public class SemFpOpticalClient implements IBinder.DeathRecipient, SemBiometricSysUiManager.SysUiListener {
    public final String mCalibrationColor;
    public Callback mCallback;
    public final Handler mHandler;
    public final boolean mIsCalibrationMode;
    public boolean mIsKeyguard;
    public final String mPackageName;
    public int mSessionId;
    public final SemBiometricSysUiManager mSysUiManager;
    public final IBinder mToken;

    public interface Callback {
        void onError(SemFpOpticalClient semFpOpticalClient);
    }

    public SemFpOpticalClient(IBinder iBinder, String str) {
        this(iBinder, str, false, null);
    }

    public SemFpOpticalClient(IBinder iBinder, String str, boolean z, String str2) {
        this(iBinder, str, z, str2, SemBiometricSysUiManager.get(), SemFpMainThread.get().getHandler());
    }

    public SemFpOpticalClient(IBinder iBinder, String str, boolean z, String str2, SemBiometricSysUiManager semBiometricSysUiManager, Handler handler) {
        this.mHandler = handler;
        this.mSysUiManager = semBiometricSysUiManager;
        this.mToken = iBinder;
        this.mPackageName = str;
        this.mIsCalibrationMode = z;
        this.mCalibrationColor = str2;
        if ("com.android.systemui".equals(str)) {
            this.mIsKeyguard = true;
        }
        try {
            iBinder.linkToDeath(this, 0);
        } catch (RemoteException e) {
            Slog.w("FingerprintService", "SemFpOpticalClient: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void start(Callback callback) {
        int i;
        this.mCallback = callback;
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
        this.mSysUiManager.sendCommand(openSession, i, 1, bundle);
    }

    public void stop() {
        try {
            this.mToken.unlinkToDeath(this, 0);
        } catch (Exception e) {
            Slog.w("FingerprintService", "SemFpOpticalClient: stop: " + e.getMessage());
        }
        this.mSysUiManager.sendCommand(this.mSessionId, this.mIsCalibrationMode ? EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_INGRESS : 500, 0, null);
        this.mSysUiManager.closeSession(this.mSessionId);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Slog.i("FingerprintService", "SemFpOpticalClient: binderDied, " + this.mPackageName);
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpOpticalClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFpOpticalClient.this.lambda$binderDied$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onError(this);
        }
    }

    @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
    public void onError(final int i, int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpOpticalClient$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemFpOpticalClient.this.lambda$onError$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$1(int i) {
        Callback callback;
        if ((i == 1 || i == 2 || i == 3) && (callback = this.mCallback) != null) {
            callback.onError(this);
        }
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public String getPackageName() {
        return this.mPackageName;
    }
}
