package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.face.Face;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;

import java.util.Arrays;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class EnrollClient extends AcquisitionClient implements EnrollmentModifier {
    public final BiometricUtils mBiometricUtils;
    public final int mEnrollReason;
    public long mEnrollmentStartTimeMs;
    public final byte[] mHardwareAuthToken;
    public final boolean mHasEnrollmentsBeforeStarting;
    public int mPrevRemaining;

    public EnrollClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            byte[] bArr,
            String str,
            BiometricUtils biometricUtils,
            int i2,
            boolean z,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            int i3) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                str,
                0,
                i2,
                z,
                biometricLogger,
                biometricContext,
                false);
        this.mBiometricUtils = biometricUtils;
        this.mHardwareAuthToken = Arrays.copyOf(bArr, bArr.length);
        this.mHasEnrollmentsBeforeStarting = hasEnrollments();
        this.mEnrollReason = i3;
    }

    public static int getRequestReasonFromFaceEnrollReason(int i) {
        return (i == 1 || i == 2 || i == 3) ? 2 : 0;
    }

    public static int getRequestReasonFromFingerprintEnrollReason(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 2;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public final boolean hasEnrollmentStateChanged() {
        return hasEnrollments() != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public final boolean hasEnrollments() {
        return !this.mBiometricUtils
                .getBiometricsForUser(this.mContext, this.mTargetUserId)
                .isEmpty();
    }

    public abstract boolean hasReachedEnrollmentLimit();

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return true;
    }

    public void onEnrollResult(BiometricAuthenticator.Identifier identifier, int i) {
        if (this.mShouldVibrate) {
            if (i < this.mPrevRemaining) {
                vibrateSuccess();
            }
            this.mPrevRemaining = i;
        }
        ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
        try {
            IFaceServiceReceiver iFaceServiceReceiver =
                    clientMonitorCallbackConverter.mFaceServiceReceiver;
            if (iFaceServiceReceiver != null) {
                iFaceServiceReceiver.onEnrollResult((Face) identifier, i);
            } else {
                IFingerprintServiceReceiver iFingerprintServiceReceiver =
                        clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                if (iFingerprintServiceReceiver != null) {
                    iFingerprintServiceReceiver.onEnrollResult((Fingerprint) identifier, i);
                }
            }
        } catch (RemoteException e) {
            Slog.e("Biometrics/EnrollClient", "Remote exception", e);
        }
        if (i == 0) {
            this.mBiometricUtils.addBiometricForUser(this.mContext, this.mTargetUserId, identifier);
            this.mLogger.logOnEnrolled(
                    this.mTargetUserId,
                    this.mEnrollReason,
                    true,
                    System.currentTimeMillis() - this.mEnrollmentStartTimeMs);
            if (Utils.isStrongBiometric(this.mSensorId)
                    && Settings.Global.getInt(this.mContext.getContentResolver(), "auto_time", 0)
                            > 0) {
                Slog.d("Biometrics/EnrollClient", "onEnrollResult: set timestamp");
                Utils.putLongDb(this.mContext, System.currentTimeMillis(), this.mTargetUserId);
            }
            this.mCallback.onClientFinished(this, true);
        }
        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        if (hasReachedEnrollmentLimit()) {
            Slog.e("Biometrics/EnrollClient", "Reached enrollment limit");
            clientMonitorCallback.onClientFinished(this, false);
        } else {
            this.mEnrollmentStartTimeMs = System.currentTimeMillis();
            startHalOperation();
            this.mPrevRemaining = 100;
        }
    }
}
