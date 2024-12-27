package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.HalClientMonitor;

import java.util.Map;
import java.util.function.Supplier;

public final class FingerprintGetAuthenticatorIdClient extends HalClientMonitor {
    public final Map mAuthenticatorIds;

    public FingerprintGetAuthenticatorIdClient(
            Context context,
            Supplier supplier,
            int i,
            String str,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            Map map) {
        super(
                context,
                supplier,
                null,
                null,
                i,
                str,
                0,
                i2,
                biometricLogger,
                biometricContext,
                false);
        this.mAuthenticatorIds = map;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 5;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.getAuthenticatorId();
        } catch (RemoteException e) {
            Slog.e("FingerprintGetAuthenticatorIdClient", "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
