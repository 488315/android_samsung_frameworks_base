package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextSessionInfo;
import com.android.server.biometrics.log.BiometricFrameworkStatsLogger;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.OperationContextExt;

import java.util.function.Supplier;

public abstract class AcquisitionClient extends HalClientMonitor implements ErrorConsumer {
    public boolean mAlreadyCancelled;
    public final PowerManager mPowerManager;
    public boolean mShouldSendErrorToClient;
    public boolean mShouldVibrate;

    static {
        VibrationAttributes.createForUsage(50);
        VibrationEffect.get(0);
        VibrationEffect.get(1);
    }

    public AcquisitionClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            String str,
            int i2,
            int i3,
            boolean z,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            boolean z2) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                str,
                i2,
                i3,
                biometricLogger,
                biometricContext,
                z2);
        this.mShouldSendErrorToClient = true;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mShouldVibrate = z;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void cancel() {
        if (this.mAlreadyCancelled) {
            Slog.w("Biometrics/AcquisitionClient", "Cancel was already requested");
        } else {
            stopHalOperation();
            this.mAlreadyCancelled = true;
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void cancelWithoutStarting(ClientMonitorCallback clientMonitorCallback) {
        Slog.d("Biometrics/AcquisitionClient", "cancelWithoutStarting: " + this);
        try {
            this.mListener.onError(this.mSensorId, this.mCookie, 5, 0);
        } catch (RemoteException e) {
            Slog.w("Biometrics/AcquisitionClient", "Failed to invoke sendError", e);
        }
        clientMonitorCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean isInterruptable() {
        return true;
    }

    public void onAcquired(int i, int i2) {
        onAcquiredInternal(i, i2, true);
    }

    public final void onAcquiredInternal(int i, int i2, boolean z) {
        boolean z2;
        BiometricLogger biometricLogger = this.mLogger;
        Context context = this.mContext;
        OperationContextExt operationContext = getOperationContext();
        int i3 = this.mTargetUserId;
        if (biometricLogger.mShouldLogMetrics) {
            int i4 = biometricLogger.mStatsModality;
            boolean z3 = i4 == 4;
            boolean z4 = i4 == 1;
            if (z3 || z4) {
                if ((z4 && (i == 7 || i2 == 10002)) || (z3 && i == 20)) {
                    biometricLogger.mFirstAcquireTimeMs = System.currentTimeMillis();
                }
            } else if (i == 0 && biometricLogger.mFirstAcquireTimeMs == 0) {
                biometricLogger.mFirstAcquireTimeMs = System.currentTimeMillis();
            }
            if (BiometricLogger.DEBUG) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i4, i3, "Acquired! Modality: ", ", User: ", ", IsCrypto: ");
                m.append(operationContext.mAidlContext.isCrypto);
                m.append(", Action: ");
                m.append(biometricLogger.mStatsAction);
                m.append(", Client: ");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        biometricLogger.mStatsClient, i, ", AcquiredInfo: ", ", VendorCode: ", m);
                GmsAlarmManager$$ExternalSyntheticOutline0.m(m, i2, "BiometricLogger");
            }
            if (!biometricLogger.shouldSkipLogging()) {
                boolean isDebugEnabled = Utils.isDebugEnabled(context, i3);
                biometricLogger.mSink.getClass();
                OperationContext operationContext2 = operationContext.mAidlContext;
                boolean z5 = operationContext2.isCrypto;
                int i5 = operationContext2.id;
                byte b = operationContext2.reason;
                int i6 = b == 1 ? 2 : b == 2 ? 1 : 0;
                boolean z6 = operationContext2.isAod;
                boolean z7 = operationContext.mIsDisplayOn;
                int i7 = operationContext.mDockState;
                int orientationType =
                        BiometricFrameworkStatsLogger.orientationType(
                                operationContext.mOrientation);
                int i8 = operationContext.mFoldState;
                int i9 = i8 != 1 ? i8 != 2 ? i8 != 3 ? 0 : 2 : 1 : 3;
                BiometricContextSessionInfo biometricContextSessionInfo =
                        operationContext.mSessionInfo;
                FrameworkStatsLog.write(
                        87,
                        biometricLogger.mStatsModality,
                        i3,
                        z5,
                        biometricLogger.mStatsAction,
                        biometricLogger.mStatsClient,
                        i,
                        i2,
                        isDebugEnabled,
                        -1,
                        i5,
                        i6,
                        z6,
                        z7,
                        i7,
                        orientationType,
                        i9,
                        biometricContextSessionInfo != null
                                ? biometricContextSessionInfo.mOrder.getAndIncrement()
                                : -1,
                        BiometricFrameworkStatsLogger.toProtoWakeReason(operationContext));
            }
        }
        ProxyManager$$ExternalSyntheticOutline0.m(
                "Biometrics/AcquisitionClient",
                ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Acquired: ", " ", ", shouldSend: "),
                z);
        if (i == 0) {
            z2 = false;
            this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
        } else {
            z2 = false;
        }
        if (z) {
            try {
                ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
                int i10 = this.mSensorId;
                IBiometricSensorReceiver iBiometricSensorReceiver =
                        clientMonitorCallbackConverter.mSensorReceiver;
                if (iBiometricSensorReceiver != null) {
                    iBiometricSensorReceiver.onAcquired(i10, i, i2);
                } else {
                    IFaceServiceReceiver iFaceServiceReceiver =
                            clientMonitorCallbackConverter.mFaceServiceReceiver;
                    if (iFaceServiceReceiver != null) {
                        iFaceServiceReceiver.onAcquired(i, i2);
                    } else {
                        IFingerprintServiceReceiver iFingerprintServiceReceiver =
                                clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                        if (iFingerprintServiceReceiver != null) {
                            iFingerprintServiceReceiver.onAcquired(i, i2);
                        }
                    }
                }
            } catch (RemoteException e) {
                Slog.w("Biometrics/AcquisitionClient", "Failed to invoke sendAcquired", e);
                this.mCallback.onClientFinished(this, z2);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        onErrorInternal(i, i2, true);
    }

    public final void onErrorInternal(int i, int i2, boolean z) {
        Slog.d("Biometrics/AcquisitionClient", "onErrorInternal code: " + i + ", finish: " + z);
        if (this.mShouldSendErrorToClient) {
            this.mLogger.logOnError(
                    this.mContext, getOperationContext(), i, i2, this.mTargetUserId);
            try {
                this.mShouldSendErrorToClient = false;
                this.mListener.onError(this.mSensorId, this.mCookie, i, i2);
            } catch (RemoteException e) {
                Slog.w("Biometrics/AcquisitionClient", "Failed to invoke sendError", e);
            }
        }
        if (z) {
            ClientMonitorCallback clientMonitorCallback = this.mCallback;
            if (clientMonitorCallback == null) {
                Slog.e(
                        "Biometrics/AcquisitionClient",
                        "Callback is null, perhaps the client hasn't been started yet?");
            } else {
                clientMonitorCallback.onClientFinished(this, false);
            }
        }
    }

    public final void onUserCanceled() {
        Slog.d("Biometrics/AcquisitionClient", "onUserCanceled");
        onErrorInternal(10, 0, false);
        stopHalOperation();
    }

    public abstract void stopHalOperation();

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
        try {
            this.mListener.onError(this.mSensorId, this.mCookie, 1, 0);
        } catch (RemoteException e) {
            Slog.e("Biometrics/AcquisitionClient", "Unable to send error", e);
        }
    }

    public abstract void vibrateError();

    public void vibrateSuccess() {}
}
