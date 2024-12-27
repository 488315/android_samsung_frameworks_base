package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.face.AuthenticationFrame;
import android.hardware.biometrics.face.BaseFrame;
import android.hardware.biometrics.face.EnrollmentFrame;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticationFrame;
import android.hardware.face.FaceEnrollFrame;
import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.face.FaceUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricTestSessionImpl extends ITestSession.Stub {
    public final ITestSessionCallback mCallback;
    public final Context mContext;
    public final FaceProvider mProvider;
    public final Sensor mSensor;
    public final int mSensorId;
    public final AnonymousClass1 mReceiver = new AnonymousClass1();
    public final Set mEnrollmentIds = new HashSet();
    public final Random mRandom = new Random();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends IFaceServiceReceiver.Stub {
        public final void onAcquired(int i, int i2) {}

        public final void onAuthenticationFailed() {}

        public final void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) {}

        public final void onAuthenticationSucceeded(Face face, int i, boolean z) {}

        public final void onChallengeGenerated(int i, int i2, long j) {}

        public final void onEnrollResult(Face face, int i) {}

        public final void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) {}

        public final void onError(int i, int i2) {}

        public final void onFaceDetected(int i, int i2, boolean z) {}

        public final void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) {}

        public final void onFeatureSet(boolean z, int i) {}

        public final void onRemoved(Face face, int i) {}

        public final void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) {}

        public final void onSemAuthenticationSucceededWithBundle(
                Face face, int i, boolean z, Bundle bundle) {}

        public final void onSemImageProcessed(
                byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {}

        public final void onSemStatusUpdate(int i, String str) {}
    }

    public BiometricTestSessionImpl(
            Context context,
            int i,
            ITestSessionCallback iTestSessionCallback,
            FaceProvider faceProvider,
            Sensor sensor) {
        this.mContext = context;
        this.mSensorId = i;
        this.mCallback = iTestSessionCallback;
        this.mProvider = faceProvider;
        this.mSensor = sensor;
    }

    public final void acceptAuthentication(int i) {
        acceptAuthentication_enforcePermission();
        List biometricsForUser =
                FaceUtils.getInstance(this.mSensorId, null).getBiometricsForUser(this.mContext, i);
        if (biometricsForUser.isEmpty()) {
            Slog.w("face/aidl/BiometricTestSessionImpl", "No faces, returning");
        } else {
            this.mSensor
                    .getSessionForUser(i)
                    .mAidlResponseHandler
                    .onAuthenticationSucceeded(
                            ((Face) biometricsForUser.get(0)).getBiometricId(),
                            HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
        }
    }

    public final void cleanupInternalState(int i) {
        cleanupInternalState_enforcePermission();
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "cleanupInternalState: ", "face/aidl/BiometricTestSessionImpl");
        this.mProvider.scheduleInternalCleanup(
                this.mSensorId,
                i,
                new ClientMonitorCallback() { // from class:
                                              // com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.2
                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z) {
                        try {
                            Slog.d(
                                    "face/aidl/BiometricTestSessionImpl",
                                    "onClientFinished: " + baseClientMonitor);
                            BiometricTestSessionImpl.this.mCallback.onCleanupFinished(
                                    baseClientMonitor.mTargetUserId);
                        } catch (RemoteException e) {
                            Slog.e("face/aidl/BiometricTestSessionImpl", "Remote exception", e);
                        }
                    }

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
                        try {
                            Slog.d(
                                    "face/aidl/BiometricTestSessionImpl",
                                    "onClientStarted: " + baseClientMonitor);
                            BiometricTestSessionImpl.this.mCallback.onCleanupStarted(
                                    baseClientMonitor.mTargetUserId);
                        } catch (RemoteException e) {
                            Slog.e("face/aidl/BiometricTestSessionImpl", "Remote exception", e);
                        }
                    }
                });
    }

    public final void finishEnroll(int i) {
        finishEnroll_enforcePermission();
        int nextInt = this.mRandom.nextInt();
        while (true) {
            if (!((HashSet) this.mEnrollmentIds).contains(Integer.valueOf(nextInt))) {
                ((HashSet) this.mEnrollmentIds).add(Integer.valueOf(nextInt));
                this.mSensor
                        .getSessionForUser(i)
                        .mAidlResponseHandler
                        .onEnrollmentProgress(nextInt, 0);
                return;
            }
            nextInt = this.mRandom.nextInt();
        }
    }

    public final void notifyAcquired(int i, int i2) {
        notifyAcquired_enforcePermission();
        BaseFrame baseFrame = new BaseFrame();
        baseFrame.acquiredInfo = (byte) i2;
        if (this.mSensor.mScheduler.getCurrentClient() instanceof EnrollClient) {
            EnrollmentFrame enrollmentFrame = new EnrollmentFrame();
            enrollmentFrame.data = baseFrame;
            this.mSensor
                    .getSessionForUser(i)
                    .mAidlResponseHandler
                    .onEnrollmentFrame(enrollmentFrame);
        } else {
            AuthenticationFrame authenticationFrame = new AuthenticationFrame();
            authenticationFrame.data = baseFrame;
            this.mSensor
                    .getSessionForUser(i)
                    .mAidlResponseHandler
                    .onAuthenticationFrame(authenticationFrame);
        }
    }

    public final void notifyError(int i, int i2) {
        notifyError_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onError((byte) i2, 0);
    }

    public final void notifyVendorAcquired(int i, int i2) {
        notifyVendorAcquired_enforcePermission();
        BaseFrame baseFrame = new BaseFrame();
        baseFrame.acquiredInfo = (byte) 23;
        baseFrame.vendorCode = i2;
        AuthenticationFrame authenticationFrame = new AuthenticationFrame();
        authenticationFrame.data = baseFrame;
        this.mSensor
                .getSessionForUser(i)
                .mAidlResponseHandler
                .onAuthenticationFrame(authenticationFrame);
    }

    public final void notifyVendorError(int i, int i2) {
        notifyVendorError_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onError((byte) 7, i2);
    }

    public final void rejectAuthentication(int i) {
        rejectAuthentication_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onAuthenticationFailed();
    }

    public final void setTestHalEnabled(boolean z) {
        setTestHalEnabled_enforcePermission();
        try {
            Thread.sleep(500L);
        } catch (Exception e) {
            Slog.e("face/aidl/BiometricTestSessionImpl", "exception", e);
        }
        this.mProvider.mTestHalEnabled = z;
        this.mSensor.setTestHalEnabled(z);
    }

    public final void startEnroll(int i) {
        startEnroll_enforcePermission();
        FaceProvider faceProvider = this.mProvider;
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        FaceEnrollOptions build = new FaceEnrollOptions.Builder().build();
        faceProvider.mHandler.post(
                new FaceProvider$$ExternalSyntheticLambda12(
                        faceProvider,
                        this.mSensorId,
                        i,
                        new Binder(),
                        anonymousClass1,
                        new byte[69],
                        this.mContext.getOpPackageName(),
                        faceProvider.mRequestCounter.incrementAndGet(),
                        new int[0],
                        null,
                        false,
                        build));
    }
}
