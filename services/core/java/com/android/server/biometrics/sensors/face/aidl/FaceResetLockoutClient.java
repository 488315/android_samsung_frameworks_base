package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.face.ISession;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;

import java.util.function.Supplier;

public final class FaceResetLockoutClient extends HalClientMonitor implements ErrorConsumer {
    public final int mBiometricStrength;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final LockoutTracker mLockoutTracker;

    public FaceResetLockoutClient(
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
        this.mLockoutTracker = lockoutTracker;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mBiometricStrength = i3;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 12;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return false;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        NandswapManager$$ExternalSyntheticOutline0.m(
                i, "Error during resetLockout: ", "FaceResetLockoutClient");
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            ISession iSession = ((AidlSession) this.mLazyDaemon.get()).mSession;
            AidlResponseHandler aidlResponseHandler =
                    ((AidlSession) this.mLazyDaemon.get()).mAidlResponseHandler;
            if (aidlResponseHandler != null) {
                aidlResponseHandler.onLockoutCleared();
            }
            if (iSession instanceof HidlToAidlSessionAdapter) {
                this.mCallback.onClientFinished(this, true);
            }
        } catch (RemoteException e) {
            Slog.e("FaceResetLockoutClient", "Unable to reset lockout", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
