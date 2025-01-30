package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticationFrame;
import android.hardware.face.FaceEnrollFrame;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.hidl.Face10;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/* loaded from: classes.dex */
public class BiometricTestSessionImpl extends ITestSession.Stub {
    public final ITestSessionCallback mCallback;
    public final Context mContext;
    public final Face10 mFace10;
    public final Face10.HalResultController mHalResultController;
    public final int mSensorId;
    public final IFaceServiceReceiver mReceiver = new IFaceServiceReceiver.Stub() { // from class: com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.1
        public void onAcquired(int i, int i2) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) {
        }

        public void onAuthenticationSucceeded(Face face, int i, boolean z) {
        }

        public void onChallengeGenerated(int i, int i2, long j) {
        }

        public void onEnrollResult(Face face, int i) {
        }

        public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) {
        }

        public void onError(int i, int i2) {
        }

        public void onFaceDetected(int i, int i2, boolean z) {
        }

        public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) {
        }

        public void onFeatureSet(boolean z, int i) {
        }

        public void onRemoved(Face face, int i) {
        }

        public void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) {
        }

        public void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) {
        }

        public void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
        }

        public void onSemStatusUpdate(int i, String str) {
        }
    };
    public final Set mEnrollmentIds = new HashSet();
    public final Random mRandom = new Random();

    public BiometricTestSessionImpl(Context context, int i, ITestSessionCallback iTestSessionCallback, Face10 face10, Face10.HalResultController halResultController) {
        this.mContext = context;
        this.mSensorId = i;
        this.mCallback = iTestSessionCallback;
        this.mFace10 = face10;
        this.mHalResultController = halResultController;
    }

    public void setTestHalEnabled(boolean z) {
        super.setTestHalEnabled_enforcePermission();
        this.mFace10.setTestHalEnabled(z);
    }

    public void startEnroll(int i) {
        super.startEnroll_enforcePermission();
        this.mFace10.scheduleEnroll(this.mSensorId, new Binder(), new byte[69], i, this.mReceiver, this.mContext.getOpPackageName(), new int[0], null, false);
    }

    public void finishEnroll(int i) {
        super.finishEnroll_enforcePermission();
        int nextInt = this.mRandom.nextInt();
        while (true) {
            if (this.mEnrollmentIds.contains(Integer.valueOf(nextInt)) || nextInt <= 0) {
                nextInt = this.mRandom.nextInt();
            } else {
                this.mEnrollmentIds.add(Integer.valueOf(nextInt));
                this.mHalResultController.onEnrollResult(0L, nextInt, i, 0);
                return;
            }
        }
    }

    public void acceptAuthentication(int i) {
        super.acceptAuthentication_enforcePermission();
        List biometricsForUser = FaceUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
        if (biometricsForUser.isEmpty()) {
            Slog.w("BiometricTestSessionImpl", "No faces, returning");
            return;
        }
        this.mHalResultController.onAuthenticated(0L, ((Face) biometricsForUser.get(0)).getBiometricId(), i, new ArrayList(Collections.nCopies(69, (byte) 0)));
    }

    public void rejectAuthentication(int i) {
        super.rejectAuthentication_enforcePermission();
        this.mHalResultController.onAuthenticated(0L, 0, i, null);
    }

    public void notifyAcquired(int i, int i2) {
        super.notifyAcquired_enforcePermission();
        this.mHalResultController.onAcquired(0L, i, i2, 0);
    }

    public void notifyError(int i, int i2) {
        super.notifyError_enforcePermission();
        this.mHalResultController.onError(0L, i, i2, 0);
    }

    public void cleanupInternalState(int i) {
        super.cleanupInternalState_enforcePermission();
        this.mFace10.scheduleInternalCleanup(this.mSensorId, i, new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(BaseClientMonitor baseClientMonitor) {
                try {
                    BiometricTestSessionImpl.this.mCallback.onCleanupStarted(baseClientMonitor.getTargetUserId());
                } catch (RemoteException e) {
                    Slog.e("BiometricTestSessionImpl", "Remote exception", e);
                }
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                try {
                    BiometricTestSessionImpl.this.mCallback.onCleanupFinished(baseClientMonitor.getTargetUserId());
                } catch (RemoteException e) {
                    Slog.e("BiometricTestSessionImpl", "Remote exception", e);
                }
            }
        });
    }

    public void notifyVendorAcquired(int i, int i2) {
        super.notifyVendorAcquired_enforcePermission();
        this.mHalResultController.onAcquired(0L, i, 22, i2);
    }

    public void notifyVendorError(int i, int i2) {
        super.notifyVendorError_enforcePermission();
        this.mHalResultController.onError(0L, i, 8, i2);
    }
}
