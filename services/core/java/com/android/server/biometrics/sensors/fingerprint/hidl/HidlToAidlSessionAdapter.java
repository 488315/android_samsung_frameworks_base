package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGetAuthenticatorIdClient;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInvalidationClient;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HidlToAidlSessionAdapter implements ISession {
    static final int ENROLL_TIMEOUT_SEC = 0;
    public final HidlToAidlCallbackConverter mHidlToAidlCallbackConverter;
    public final Supplier mSession;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Cancellation extends ICancellationSignal.Stub {
        public Cancellation() {
        }

        public final void cancel() {
            try {
                ((IBiometricsFingerprint) HidlToAidlSessionAdapter.this.mSession.get()).cancel();
            } catch (RemoteException e) {
                Slog.e("HidlToAidlSessionAdapter", "Remote exception when requesting cancel", e);
            }
        }

        public final String getInterfaceHash() {
            return null;
        }

        public final int getInterfaceVersion() {
            return 0;
        }
    }

    public HidlToAidlSessionAdapter(HidlToAidlSensorAdapter$$ExternalSyntheticLambda3 hidlToAidlSensorAdapter$$ExternalSyntheticLambda3, int i, SemFpAidlResponseHandler semFpAidlResponseHandler) {
        this.mSession = hidlToAidlSensorAdapter$$ExternalSyntheticLambda3;
        this.mUserId = i;
        this.mHidlToAidlCallbackConverter = new HidlToAidlCallbackConverter(semFpAidlResponseHandler);
        try {
            if (hidlToAidlSensorAdapter$$ExternalSyntheticLambda3.f$0.getIBiometricsFingerprint() != null) {
                long notify = hidlToAidlSensorAdapter$$ExternalSyntheticLambda3.f$0.getIBiometricsFingerprint().setNotify(this.mHidlToAidlCallbackConverter);
                Slog.d("HidlToAidlSessionAdapter", "Fingerprint HAL ready, HAL ID: " + notify);
                if (notify == 0) {
                    Slog.d("HidlToAidlSessionAdapter", "Unable to set HIDL callback.");
                }
            } else {
                Slog.e("HidlToAidlSessionAdapter", "Unable to set HIDL callback. HIDL daemon is null.");
            }
        } catch (RemoteException unused) {
            Slog.d("HidlToAidlSessionAdapter", "Failed to set callback");
        }
    }

    public final IBinder asBinder() {
        return null;
    }

    public final ICancellationSignal authenticate(long j) {
        ((IBiometricsFingerprint) this.mSession.get()).authenticate(this.mUserId, j);
        return new Cancellation();
    }

    public final ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
        Log.e("HidlToAidlSessionAdapter", "authenticateWithContext unsupported in HIDL");
        return authenticate(j);
    }

    public final void close() {
        Log.e("HidlToAidlSessionAdapter", "close unsupported in HIDL");
    }

    public final ICancellationSignal detectInteraction() {
        ((IBiometricsFingerprint) this.mSession.get()).authenticate(this.mUserId, 0L);
        return new Cancellation();
    }

    public final ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
        Log.e("HidlToAidlSessionAdapter", "enrollWithContext unsupported in HIDL");
        return detectInteraction();
    }

    public final ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) {
        ((IBiometricsFingerprint) this.mSession.get()).enroll(this.mUserId, 0, HardwareAuthTokenUtils.toByteArray(hardwareAuthToken));
        return new Cancellation();
    }

    public final ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, OperationContext operationContext) {
        Log.e("HidlToAidlSessionAdapter", "enrollWithContext unsupported in HIDL");
        return enroll(hardwareAuthToken);
    }

    public final void enumerateEnrollments() {
        ((IBiometricsFingerprint) this.mSession.get()).enumerate();
    }

    public final void generateChallenge() {
        this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onChallengeGenerated(((IBiometricsFingerprint) this.mSession.get()).preEnroll());
    }

    public final void getAuthenticatorId() {
        Log.e("HidlToAidlSessionAdapter", "getAuthenticatorId unsupported in HIDL");
        this.mHidlToAidlCallbackConverter.unsupportedClientScheduled(FingerprintGetAuthenticatorIdClient.class);
    }

    public final String getInterfaceHash() {
        Log.e("HidlToAidlSessionAdapter", "getInterfaceHash unsupported in HIDL");
        return null;
    }

    public final int getInterfaceVersion() {
        Log.e("HidlToAidlSessionAdapter", "getInterfaceVersion unsupported in HIDL");
        return 0;
    }

    public final void invalidateAuthenticatorId() {
        Log.e("HidlToAidlSessionAdapter", "invalidateAuthenticatorId unsupported in HIDL");
        this.mHidlToAidlCallbackConverter.unsupportedClientScheduled(FingerprintInvalidationClient.class);
    }

    public final void onContextChanged(OperationContext operationContext) {
        Log.e("HidlToAidlSessionAdapter", "onContextChanged unsupported in HIDL");
    }

    public final void onPointerCancelWithContext(PointerContext pointerContext) {
        Log.e("HidlToAidlSessionAdapter", "onPointerCancelWithContext unsupported in HIDL");
    }

    public final void onPointerDown(int i, int i2, int i3, float f, float f2) {
        IBiometricsFingerprint iBiometricsFingerprint = (IBiometricsFingerprint) this.mSession.get();
        if (iBiometricsFingerprint instanceof ISehBiometricsFingerprint) {
            return;
        }
        android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint castFrom = android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint.castFrom(iBiometricsFingerprint);
        if (castFrom == null) {
            Slog.v("UdfpsHelper", "onFingerDown | failed to cast the HIDL to V2_3");
            return;
        }
        try {
            castFrom.onFingerDown(i2, f, f2, i3);
        } catch (RemoteException e) {
            Slog.e("UdfpsHelper", "onFingerDown | RemoteException: ", e);
        }
    }

    public final void onPointerDownWithContext(PointerContext pointerContext) {
        Log.e("HidlToAidlSessionAdapter", "onPointerDownWithContext unsupported in HIDL");
        onPointerDown(pointerContext.pointerId, (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
    }

    public final void onPointerUp(int i) {
        IBiometricsFingerprint iBiometricsFingerprint = (IBiometricsFingerprint) this.mSession.get();
        if (iBiometricsFingerprint instanceof ISehBiometricsFingerprint) {
            return;
        }
        android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint castFrom = android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint.castFrom(iBiometricsFingerprint);
        if (castFrom == null) {
            Slog.v("UdfpsHelper", "onFingerUp | failed to cast the HIDL to V2_3");
            return;
        }
        try {
            castFrom.onFingerUp();
        } catch (RemoteException e) {
            Slog.e("UdfpsHelper", "onFingerUp | RemoteException: ", e);
        }
    }

    public final void onPointerUpWithContext(PointerContext pointerContext) {
        Log.e("HidlToAidlSessionAdapter", "onPointerUpWithContext unsupported in HIDL");
        onPointerUp(pointerContext.pointerId);
    }

    public final void onUiReady() {
        Log.e("HidlToAidlSessionAdapter", "onUiReady unsupported in HIDL");
    }

    public final void removeEnrollments(int[] iArr) {
        if (iArr.length != 1) {
            ((IBiometricsFingerprint) this.mSession.get()).remove(this.mUserId, 0);
        } else {
            ((IBiometricsFingerprint) this.mSession.get()).remove(this.mUserId, iArr[0]);
        }
    }

    public final void resetLockout(HardwareAuthToken hardwareAuthToken) {
        this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onLockoutCleared();
    }

    public final void revokeChallenge(long j) {
        ((IBiometricsFingerprint) this.mSession.get()).postEnroll();
        this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onChallengeRevoked(0L);
    }

    public final void setIgnoreDisplayTouches(boolean z) {
        Log.e("HidlToAidlSessionAdapter", "setIgnoreDisplayTouches unsupported in HIDL");
    }
}
