package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.RemovalConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((RemovalConsumer) obj).onRemoved(null, 0);
                break;
            case 1:
                ((LockoutConsumer) obj).onLockoutPermanent();
                break;
            case 2:
                FaceDetectClient faceDetectClient = (FaceDetectClient) obj;
                faceDetectClient.getClass();
                try {
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter = faceDetectClient.mListener;
                    int i = faceDetectClient.mSensorId;
                    int i2 = faceDetectClient.mTargetUserId;
                    boolean z = faceDetectClient.mIsStrongBiometric;
                    IFaceServiceReceiver iFaceServiceReceiver = clientMonitorCallbackConverter.mFaceServiceReceiver;
                    if (iFaceServiceReceiver != null) {
                        iFaceServiceReceiver.onFaceDetected(i, i2, z);
                    } else {
                        IFingerprintServiceReceiver iFingerprintServiceReceiver = clientMonitorCallbackConverter.mFingerprintServiceReceiver;
                        if (iFingerprintServiceReceiver != null) {
                            iFingerprintServiceReceiver.onFingerprintDetected(i, i2, z);
                        }
                    }
                    faceDetectClient.mCallback.onClientFinished(faceDetectClient, true);
                    break;
                } catch (RemoteException e) {
                    Slog.e("FaceDetectClient", "Remote exception when sending onDetected", e);
                    faceDetectClient.mCallback.onClientFinished(faceDetectClient, false);
                    return;
                }
            case 3:
                FaceResetLockoutClient faceResetLockoutClient = (FaceResetLockoutClient) obj;
                int i3 = faceResetLockoutClient.mSensorId;
                int i4 = faceResetLockoutClient.mTargetUserId;
                LockoutTracker lockoutTracker = faceResetLockoutClient.mLockoutTracker;
                LockoutResetDispatcher lockoutResetDispatcher = faceResetLockoutClient.mLockoutResetDispatcher;
                ((BiometricContextProvider) faceResetLockoutClient.mBiometricContext).mAuthSessionCoordinator.resetLockoutFor(i4, faceResetLockoutClient.mBiometricStrength, faceResetLockoutClient.mRequestId);
                lockoutTracker.setLockoutModeForUser(i4, 0);
                lockoutResetDispatcher.notifyLockoutResetCallbacks(i3);
                faceResetLockoutClient.mCallback.onClientFinished(faceResetLockoutClient, true);
                break;
            case 4:
                ((EnumerateConsumer) obj).onEnumerationResult(null, 0);
                break;
            case 5:
                FaceSetFeatureClient faceSetFeatureClient = (FaceSetFeatureClient) obj;
                faceSetFeatureClient.getClass();
                try {
                    ClientMonitorCallbackConverter clientMonitorCallbackConverter2 = faceSetFeatureClient.mListener;
                    int i5 = faceSetFeatureClient.mFeature;
                    IFaceServiceReceiver iFaceServiceReceiver2 = clientMonitorCallbackConverter2.mFaceServiceReceiver;
                    if (iFaceServiceReceiver2 != null) {
                        iFaceServiceReceiver2.onFeatureSet(true, i5);
                    }
                } catch (RemoteException e2) {
                    Slog.e("FaceSetFeatureClient", "Remote exception", e2);
                }
                faceSetFeatureClient.mCallback.onClientFinished(faceSetFeatureClient, true);
                break;
            default:
                ((FaceInvalidationClient) obj).cancel();
                break;
        }
    }
}
