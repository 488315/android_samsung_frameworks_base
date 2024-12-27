package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.face.Face;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

import java.util.Map;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RemovalClient extends HalClientMonitor
        implements RemovalConsumer, EnrollmentModifier {
    public final Map mAuthenticatorIds;
    public final BiometricUtils mBiometricUtils;
    public final boolean mHasEnrollmentsBeforeStarting;

    public RemovalClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            String str,
            BiometricUtils biometricUtils,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            Map map) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                str,
                0,
                i2,
                biometricLogger,
                biometricContext,
                false);
        this.mBiometricUtils = biometricUtils;
        this.mAuthenticatorIds = map;
        this.mHasEnrollmentsBeforeStarting =
                !biometricUtils.getBiometricsForUser(context, i).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 4;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public final boolean hasEnrollmentStateChanged() {
        return (this.mBiometricUtils
                                .getBiometricsForUser(this.mContext, this.mTargetUserId)
                                .isEmpty()
                        ^ true)
                != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public final boolean hasEnrollments() {
        return !this.mBiometricUtils
                .getBiometricsForUser(this.mContext, this.mTargetUserId)
                .isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return true;
    }

    @Override // com.android.server.biometrics.sensors.RemovalConsumer
    public final void onRemoved(BiometricAuthenticator.Identifier identifier, int i) {
        if (identifier == null) {
            Slog.e("Biometrics/RemovalClient", "identifier was null, skipping onRemove()");
            try {
                this.mListener.onError(this.mSensorId, this.mCookie, 6, 0);
            } catch (RemoteException e) {
                Slog.w(
                        "Biometrics/RemovalClient",
                        "Failed to send error to client for onRemoved",
                        e);
            }
            this.mCallback.onClientFinished(this, false);
            return;
        }
        Slog.d(
                "Biometrics/RemovalClient",
                "onRemoved: " + identifier.getBiometricId() + " remaining: " + i);
        this.mBiometricUtils.removeBiometricForUser(
                this.mContext, this.mTargetUserId, identifier.getBiometricId());
        try {
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            IFaceServiceReceiver iFaceServiceReceiver =
                    clientMonitorCallbackConverter.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onRemoved((Face) identifier, i);
            } else {
                IFingerprintServiceReceiver iFingerprintServiceReceiver =
                        clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                if (iFingerprintServiceReceiver != null) {
                    iFingerprintServiceReceiver.onRemoved((Fingerprint) identifier, i);
                }
            }
        } catch (RemoteException e2) {
            Slog.w("Biometrics/RemovalClient", "Failed to notify Removed:", e2);
        }
        if (i == 0) {
            if (this.mBiometricUtils
                    .getBiometricsForUser(this.mContext, this.mTargetUserId)
                    .isEmpty()) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Last biometric removed for user: "),
                        this.mTargetUserId,
                        "Biometrics/RemovalClient");
                this.mAuthenticatorIds.put(Integer.valueOf(this.mTargetUserId), 0L);
            }
            this.mCallback.onClientFinished(this, true);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {}
}
