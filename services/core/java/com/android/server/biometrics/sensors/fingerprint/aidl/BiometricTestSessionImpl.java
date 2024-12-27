package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintEnrollOptions;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Flags;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BiometricTestSessionImpl extends ITestSession.Stub {
    public final ITestSessionCallback mCallback;
    public final Context mContext;
    public final FingerprintProvider mProvider;
    public final Sensor mSensor;
    public final int mSensorId;
    public final AnonymousClass1 mReceiver = new AnonymousClass1();
    public final Set mEnrollmentIds = new HashSet();
    public final Random mRandom = new Random();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends IFingerprintServiceReceiver.Stub {
        public final void onAcquired(int i, int i2) {
        }

        public final void onAuthenticationFailed() {
        }

        public final void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) {
        }

        public final void onChallengeGenerated(int i, int i2, long j) {
        }

        public final void onEnrollResult(Fingerprint fingerprint, int i) {
        }

        public final void onError(int i, int i2) {
        }

        public final void onFingerprintDetected(int i, int i2, boolean z) {
        }

        public final void onRemoved(Fingerprint fingerprint, int i) {
        }

        public final void onUdfpsOverlayShown() {
        }

        public final void onUdfpsPointerDown(int i) {
        }

        public final void onUdfpsPointerUp(int i) {
        }
    }

    public BiometricTestSessionImpl(Context context, int i, ITestSessionCallback iTestSessionCallback, FingerprintProvider fingerprintProvider, Sensor sensor) {
        this.mContext = context;
        this.mSensorId = i;
        this.mCallback = iTestSessionCallback;
        this.mProvider = fingerprintProvider;
        this.mSensor = sensor;
    }

    public final void acceptAuthentication(int i) {
        acceptAuthentication_enforcePermission();
        this.mProvider.getClass();
        Flags.useVhalForTesting();
        List biometricsForUser = FingerprintUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
        if (biometricsForUser.isEmpty()) {
            Slog.w("fp/aidl/BiometricTestSessionImpl", "No fingerprints, returning");
        } else {
            this.mSensor.getSessionForUser(i).mAidlResponseHandler.onAuthenticationSucceeded(((Fingerprint) biometricsForUser.get(0)).getBiometricId(), HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
        }
    }

    public final void cleanupInternalState(int i) {
        cleanupInternalState_enforcePermission();
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "cleanupInternalState: ", "fp/aidl/BiometricTestSessionImpl");
        this.mProvider.getClass();
        Flags.useVhalForTesting();
        this.mProvider.scheduleInternalCleanup(this.mSensorId, i, new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                try {
                    Slog.d("fp/aidl/BiometricTestSessionImpl", "onClientFinished: " + baseClientMonitor);
                    BiometricTestSessionImpl.this.mCallback.onCleanupFinished(baseClientMonitor.mTargetUserId);
                } catch (RemoteException e) {
                    Slog.e("fp/aidl/BiometricTestSessionImpl", "Remote exception", e);
                }
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
                try {
                    Slog.d("fp/aidl/BiometricTestSessionImpl", "onClientStarted: " + baseClientMonitor);
                    BiometricTestSessionImpl.this.mCallback.onCleanupStarted(baseClientMonitor.mTargetUserId);
                } catch (RemoteException e) {
                    Slog.e("fp/aidl/BiometricTestSessionImpl", "Remote exception", e);
                }
            }
        });
    }

    public final void finishEnroll(int i) {
        finishEnroll_enforcePermission();
        this.mProvider.getClass();
        Flags.useVhalForTesting();
        Slog.i("fp/aidl/BiometricTestSessionImpl", "finishEnroll(): useVhalForTesting=false");
        this.mProvider.getClass();
        Flags.useVhalForTesting();
        int nextInt = this.mRandom.nextInt();
        while (true) {
            if (!((HashSet) this.mEnrollmentIds).contains(Integer.valueOf(nextInt))) {
                ((HashSet) this.mEnrollmentIds).add(Integer.valueOf(nextInt));
                this.mSensor.getSessionForUser(i).mAidlResponseHandler.onEnrollmentProgress(nextInt, 0);
                return;
            }
            nextInt = this.mRandom.nextInt();
        }
    }

    public final void notifyAcquired(int i, int i2) {
        notifyAcquired_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onAcquired((byte) i2, 0);
    }

    public final void notifyError(int i, int i2) {
        notifyError_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onError((byte) i2, 0);
    }

    public final void notifyVendorAcquired(int i, int i2) {
        notifyAcquired_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onAcquired((byte) 7, i2);
    }

    public final void notifyVendorError(int i, int i2) {
        notifyVendorError_enforcePermission();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onError((byte) 7, i2);
    }

    public final void rejectAuthentication(int i) {
        rejectAuthentication_enforcePermission();
        this.mProvider.getClass();
        Flags.useVhalForTesting();
        this.mSensor.getSessionForUser(i).mAidlResponseHandler.onAuthenticationFailed();
    }

    public final void setTestHalEnabled(boolean z) {
        setTestHalEnabled_enforcePermission();
        this.mSensor.setTestHalEnabled(z);
        FingerprintProvider fingerprintProvider = this.mProvider;
        boolean z2 = z != fingerprintProvider.mTestHalEnabled;
        fingerprintProvider.mTestHalEnabled = z;
        String tag$1 = fingerprintProvider.getTag$1();
        StringBuilder sb = new StringBuilder("setTestHalEnabled(): useVhalForTesting=false mTestHalEnabled=");
        Flags.useVhalForTesting();
        sb.append(fingerprintProvider.mTestHalEnabled);
        sb.append(" changed=");
        sb.append(z2);
        Slog.i(tag$1, sb.toString());
        if (z2) {
            Flags.useVhalForTesting();
        }
    }

    public final void startEnroll(int i) {
        startEnroll_enforcePermission();
        FingerprintProvider fingerprintProvider = this.mProvider;
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        String opPackageName = this.mContext.getOpPackageName();
        FingerprintEnrollOptions build = new FingerprintEnrollOptions.Builder().build();
        fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda25(fingerprintProvider, this.mSensorId, new Binder(), fingerprintProvider.mRequestCounter.incrementAndGet(), anonymousClass1, i, new byte[69], opPackageName, 2, build));
    }
}
