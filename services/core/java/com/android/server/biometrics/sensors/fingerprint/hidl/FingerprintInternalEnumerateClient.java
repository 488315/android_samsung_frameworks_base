package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.InternalEnumerateClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import java.util.List;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* loaded from: classes.dex */
public class FingerprintInternalEnumerateClient extends InternalEnumerateClient {
    public FingerprintInternalEnumerateClient(Context context, Supplier supplier, IBinder iBinder, int i, String str, List list, BiometricUtils biometricUtils, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext) {
        super(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        if (!canUseEnumerateOperation()) {
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            ((IBiometricsFingerprint) getFreshDaemon()).enumerate();
        } catch (RemoteException e) {
            Slog.e("FingerprintInternalEnumerateClient", "Remote exception when requesting enumerate", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    public final boolean canUseEnumerateOperation() {
        return !(((IBiometricsFingerprint) getFreshDaemon()) instanceof ISehBiometricsFingerprint) || new SemFpBaseRequestClient.Builder(getContext(), getBiometricContext(), this.mLazyDaemon, getSensorId()).setCommand(11).build().startWithoutScheduler() >= 0;
    }
}
