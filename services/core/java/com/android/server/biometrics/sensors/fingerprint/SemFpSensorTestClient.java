package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession;
import com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter;

import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpSensorTestClient extends SemFpBaseRequestClient implements ErrorConsumer {
    public boolean mAlreadyCancelled;

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void cancel() {
        if (this.mAlreadyCancelled) {
            Slog.w("FingerprintSensorTestClient", "Cancel was already requested");
            return;
        }
        Object obj = this.mLazyDaemon.get();
        if (obj instanceof AidlSession) {
            ISession iSession = ((AidlSession) obj).mSession;
            if (iSession instanceof HidlToAidlSessionAdapter) {
                HidlToAidlSessionAdapter hidlToAidlSessionAdapter =
                        (HidlToAidlSessionAdapter) iSession;
                hidlToAidlSessionAdapter.getClass();
                try {
                    ((IBiometricsFingerprint) hidlToAidlSessionAdapter.mSession.get()).cancel();
                } catch (RemoteException e) {
                    Slog.e(
                            "HidlToAidlSessionAdapter",
                            "Remote exception when requesting cancel",
                            e);
                }
            }
        }
        if (obj instanceof ISehBiometricsFingerprint) {
            try {
                ((ISehBiometricsFingerprint) obj).cancel();
            } catch (RemoteException e2) {
                Slog.e("FingerprintRequestClient", "Remote exception when requesting cancel", e2);
            }
        }
        this.mAlreadyCancelled = true;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void cancelWithoutStarting(ClientMonitorCallback clientMonitorCallback) {
        Slog.d("FingerprintSensorTestClient", "cancelWithoutStarting: " + this);
        super.onRequestResult(FrameworkStatsLog.CPU_TIME_PER_UID);
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return true;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        super.onRequestResult(FrameworkStatsLog.CPU_TIME_PER_UID);
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient
    public final void onRequestResult(int i) {
        super.onRequestResult(i);
        if (i == 10009) {
            this.mCallback.onClientFinished(this, true);
        }
    }
}
