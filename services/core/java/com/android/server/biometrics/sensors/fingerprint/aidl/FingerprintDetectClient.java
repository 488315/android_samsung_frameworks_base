package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.DetectionConsumer;
import com.android.server.biometrics.sensors.SensorOverlays;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintDetectClient extends AcquisitionClient implements DetectionConsumer {
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public ICancellationSignal mCancellationSignal;
    public final boolean mIsStrongBiometric;
    public final FingerprintAuthenticateOptions mOptions;
    public final SensorOverlays mSensorOverlays;

    public FingerprintDetectClient(Context context, Supplier supplier, IBinder iBinder, long j, ClientMonitorCallbackConverter clientMonitorCallbackConverter, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, BiometricLogger biometricLogger, BiometricContext biometricContext, AuthenticationStateListeners authenticationStateListeners, boolean z) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, fingerprintAuthenticateOptions.getUserId(), fingerprintAuthenticateOptions.getOpPackageName(), 0, fingerprintAuthenticateOptions.getSensorId(), true, biometricLogger, biometricContext, false);
        setRequestId(j);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mIsStrongBiometric = z;
        this.mSensorOverlays = new SensorOverlays();
        this.mOptions = fingerprintAuthenticateOptions;
    }

    public final void doDetectInteraction$1() {
        final AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
        if (!aidlSession.hasContextMethods()) {
            this.mCancellationSignal = aidlSession.mSession.detectInteraction();
            return;
        }
        OperationContextExt operationContext = getOperationContext();
        ((BiometricContextProvider) this.mBiometricContext).subscribe(operationContext, new Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FingerprintDetectClient fingerprintDetectClient = FingerprintDetectClient.this;
                AidlSession aidlSession2 = aidlSession;
                OperationContext operationContext2 = (OperationContext) obj;
                fingerprintDetectClient.getClass();
                try {
                    fingerprintDetectClient.mCancellationSignal = aidlSession2.mSession.detectInteractionWithContext(operationContext2);
                } catch (RemoteException e) {
                    Slog.e("FingerprintDetectClient", "Unable to start detect interaction", e);
                    fingerprintDetectClient.mSensorOverlays.hide(fingerprintDetectClient.mSensorId);
                    fingerprintDetectClient.mAuthenticationStateListeners.onAuthenticationStopped(new AuthenticationStoppedInfo.Builder(BiometricSourceType.FINGERPRINT, 4).build());
                    fingerprintDetectClient.mCallback.onClientFinished(fingerprintDetectClient, false);
                }
            }
        }, new Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                try {
                    AidlSession.this.mSession.onContextChanged((OperationContext) obj);
                } catch (RemoteException e) {
                    Slog.e("FingerprintDetectClient", "Unable to notify context changed", e);
                }
            }
        }, this.mOptions);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 13;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return true;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        this.mSensorOverlays.show(this.mSensorId, 4, this);
        this.mAuthenticationStateListeners.onAuthenticationStarted(new AuthenticationStartedInfo.Builder(BiometricSourceType.FINGERPRINT, 4).build());
        try {
            doDetectInteraction$1();
        } catch (RemoteException e) {
            Slog.e("FingerprintDetectClient", "Remote exception when requesting finger detect", e);
            this.mSensorOverlays.hide(this.mSensorId);
            this.mAuthenticationStateListeners.onAuthenticationStopped(new AuthenticationStoppedInfo.Builder(BiometricSourceType.FINGERPRINT, 4).build());
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void stopHalOperation() {
        this.mSensorOverlays.hide(this.mSensorId);
        this.mAuthenticationStateListeners.onAuthenticationStopped(new AuthenticationStoppedInfo.Builder(BiometricSourceType.FINGERPRINT, 4).build());
        unsubscribeBiometricContext();
        ICancellationSignal iCancellationSignal = this.mCancellationSignal;
        if (iCancellationSignal != null) {
            try {
                iCancellationSignal.cancel();
            } catch (RemoteException e) {
                Slog.e("FingerprintDetectClient", "Remote exception", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void vibrateSuccess() {
        throw null;
    }
}
