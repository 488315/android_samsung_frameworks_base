package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.DetectionConsumer;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FaceDetectClient extends AcquisitionClient implements DetectionConsumer {
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public ICancellationSignal mCancellationSignal;
    public final boolean mIsStrongBiometric;
    public final SensorPrivacyManager mSensorPrivacyManager;

    public FaceDetectClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            long j,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            FaceAuthenticateOptions faceAuthenticateOptions,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            AuthenticationStateListeners authenticationStateListeners,
            boolean z,
            SensorPrivacyManager sensorPrivacyManager) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                faceAuthenticateOptions.getUserId(),
                faceAuthenticateOptions.getOpPackageName(),
                0,
                faceAuthenticateOptions.getSensorId(),
                false,
                biometricLogger,
                biometricContext,
                false);
        setRequestId(j);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mIsStrongBiometric = z;
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 13;
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient,
              // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        AuthenticationStateListeners authenticationStateListeners =
                this.mAuthenticationStateListeners;
        BiometricSourceType biometricSourceType = BiometricSourceType.FACE;
        authenticationStateListeners.onAuthenticationError(
                new AuthenticationErrorInfo.Builder(
                                biometricSourceType,
                                4,
                                FaceManager.getErrorString(this.mContext, i, i2),
                                i)
                        .build());
        onErrorInternal(i, i2, true);
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(biometricSourceType, 4).build());
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        this.mAuthenticationStateListeners.onAuthenticationStarted(
                new AuthenticationStartedInfo.Builder(BiometricSourceType.FACE, 4).build());
        SensorPrivacyManager sensorPrivacyManager = this.mSensorPrivacyManager;
        if (sensorPrivacyManager != null && sensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            aidlSession.getClass();
            this.mCancellationSignal = aidlSession.mSession.detectInteraction();
        } catch (RemoteException e) {
            Slog.e("FaceDetectClient", "Remote exception when requesting face detect", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void stopHalOperation() {
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(BiometricSourceType.FACE, 4).build());
        unsubscribeBiometricContext();
        ICancellationSignal iCancellationSignal = this.mCancellationSignal;
        if (iCancellationSignal != null) {
            try {
                iCancellationSignal.cancel();
            } catch (RemoteException e) {
                Slog.e("FaceDetectClient", "Remote exception", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }
}
