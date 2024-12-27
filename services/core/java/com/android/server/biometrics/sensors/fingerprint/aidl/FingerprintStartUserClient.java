package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.StartUserClient;

public final class FingerprintStartUserClient extends StartUserClient {
    public final ISessionCallback mSessionCallback;

    public FingerprintStartUserClient(
            Context context,
            Sensor$$ExternalSyntheticLambda5 sensor$$ExternalSyntheticLambda5,
            IBinder iBinder,
            int i,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            SemFpAidlResponseHandler semFpAidlResponseHandler,
            Sensor$$ExternalSyntheticLambda4 sensor$$ExternalSyntheticLambda4) {
        super(
                context,
                sensor$$ExternalSyntheticLambda5,
                iBinder,
                i,
                i2,
                biometricLogger,
                biometricContext,
                sensor$$ExternalSyntheticLambda4);
        this.mSessionCallback = semFpAidlResponseHandler;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        try {
            IFingerprint iFingerprint = (IFingerprint) this.mLazyDaemon.get();
            int interfaceVersion = iFingerprint.getInterfaceVersion();
            ISession createSession =
                    iFingerprint.createSession(
                            this.mSensorId, this.mTargetUserId, this.mSessionCallback);
            Binder.allowBlocking(createSession.asBinder());
            this.mUserStartedCallback.onUserStarted(
                    this.mTargetUserId, interfaceVersion, createSession);
            getCallback().onClientFinished(this, true);
        } catch (RemoteException e) {
            Slog.e("FingerprintStartUserClient", "Remote exception", e);
            getCallback().onClientFinished(this, false);
        } catch (NullPointerException e2) {
            Slog.e("FingerprintStartUserClient", "np exception", e2);
            getCallback().onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
