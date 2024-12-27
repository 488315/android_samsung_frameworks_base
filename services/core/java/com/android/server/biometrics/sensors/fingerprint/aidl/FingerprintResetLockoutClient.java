package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FingerprintResetLockoutClient extends HalClientMonitor implements ErrorConsumer {
    public final int mBiometricStrength;
    public final LockoutTracker mLockoutCache;
    public final LockoutResetDispatcher mLockoutResetDispatcher;

    public FingerprintResetLockoutClient(
            Context context,
            Supplier supplier,
            int i,
            String str,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            byte[] bArr,
            LockoutTracker lockoutTracker,
            LockoutResetDispatcher lockoutResetDispatcher,
            int i3) {
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
        if (bArr != null) {
            HardwareAuthTokenUtils.toHardwareAuthToken(bArr);
        }
        this.mLockoutCache = lockoutTracker;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mBiometricStrength = i3;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 12;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return true;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        NandswapManager$$ExternalSyntheticOutline0.m(
                i, "Error during resetLockout: ", "FingerprintResetLockoutClient");
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        SemFpAidlResponseHandler semFpAidlResponseHandler =
                ((AidlSession) this.mLazyDaemon.get()).mAidlResponseHandler;
        if (semFpAidlResponseHandler != null) {
            semFpAidlResponseHandler.onLockoutCleared();
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
