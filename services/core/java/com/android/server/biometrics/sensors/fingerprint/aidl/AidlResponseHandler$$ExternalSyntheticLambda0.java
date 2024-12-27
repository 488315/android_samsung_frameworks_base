package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.RemovalConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FingerprintDetectClient fingerprintDetectClient = (FingerprintDetectClient) obj;
                fingerprintDetectClient.getClass();
                try {
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter = fingerprintDetectClient.mListener;
                    int i = fingerprintDetectClient.mSensorId;
                    int i2 = fingerprintDetectClient.mTargetUserId;
                    boolean z = fingerprintDetectClient.mIsStrongBiometric;
                    IFaceServiceReceiver iFaceServiceReceiver = clientMonitorCallbackConverter.mFaceServiceReceiver;
                    if (iFaceServiceReceiver != null) {
                        iFaceServiceReceiver.onFaceDetected(i, i2, z);
                    } else {
                        IFingerprintServiceReceiver iFingerprintServiceReceiver = clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                        if (iFingerprintServiceReceiver != null) {
                            iFingerprintServiceReceiver.onFingerprintDetected(i, i2, z);
                        }
                    }
                    fingerprintDetectClient.mCallback.onClientFinished(fingerprintDetectClient, true);
                    break;
                } catch (RemoteException e) {
                    Slog.e("FingerprintDetectClient", "Remote exception when sending onDetected", e);
                    fingerprintDetectClient.mCallback.onClientFinished(fingerprintDetectClient, false);
                    return;
                }
            case 1:
                ((RemovalConsumer) obj).onRemoved(null, 0);
                break;
            case 2:
                ((EnumerateConsumer) obj).onEnumerationResult(null, 0);
                break;
            case 3:
                ((BaseClientMonitor) obj).cancel();
                break;
            default:
                FingerprintResetLockoutClient fingerprintResetLockoutClient = (FingerprintResetLockoutClient) obj;
                int i3 = fingerprintResetLockoutClient.mSensorId;
                int i4 = fingerprintResetLockoutClient.mTargetUserId;
                LockoutTracker lockoutTracker = fingerprintResetLockoutClient.mLockoutCache;
                LockoutResetDispatcher lockoutResetDispatcher = fingerprintResetLockoutClient.mLockoutResetDispatcher;
                AuthSessionCoordinator authSessionCoordinator = ((BiometricContextProvider) fingerprintResetLockoutClient.mBiometricContext).mAuthSessionCoordinator;
                int i5 = fingerprintResetLockoutClient.mBiometricStrength;
                long j = fingerprintResetLockoutClient.mRequestId;
                lockoutTracker.resetFailedAttemptsForUser(i4, true);
                lockoutTracker.setLockoutModeForUser(i4, 0);
                lockoutResetDispatcher.notifyLockoutResetCallbacks(i3);
                authSessionCoordinator.resetLockoutFor(i4, i5, j);
                fingerprintResetLockoutClient.mCallback.onClientFinished(fingerprintResetLockoutClient, true);
                break;
        }
    }
}
