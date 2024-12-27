package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.face.EnrollmentStageConfig;
import android.hardware.biometrics.face.FaceEnrollOptions;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.V1_0.IBiometricsFace;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.common.NativeHandle;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.face.aidl.AidlConversionUtils;
import com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler;
import com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda9;
import com.android.server.biometrics.sensors.face.aidl.FaceInvalidationClient;

import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HidlToAidlSessionAdapter implements ISession {
    static final int CHALLENGE_TIMEOUT_SEC = 600;
    static final int ENROLL_TIMEOUT_SEC = 75;
    public final Clock mClock;
    public final Context mContext;
    public int mFeature;
    public long mGenerateChallengeCreatedAt;
    public long mGenerateChallengeResult;
    public final List mGeneratedChallengeCount;
    public final HidlToAidlCallbackConverter mHidlToAidlCallbackConverter;
    public int mSecurityLevel;
    public final Supplier mSession;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Cancellation extends ICancellationSignal.Stub {
        public Cancellation() {}

        public final void cancel() {
            try {
                ((IBiometricsFace) HidlToAidlSessionAdapter.this.mSession.get()).cancel();
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

    public HidlToAidlSessionAdapter(
            Context context, Supplier supplier, int i, AidlResponseHandler aidlResponseHandler) {
        Clock systemUTC = Clock.systemUTC();
        this.mGeneratedChallengeCount = new ArrayList();
        this.mGenerateChallengeCreatedAt = -1L;
        this.mGenerateChallengeResult = -1L;
        this.mFeature = -1;
        this.mSecurityLevel = -1;
        this.mSession = supplier;
        this.mUserId = i;
        this.mContext = context;
        this.mClock = systemUTC;
        this.mHidlToAidlCallbackConverter = new HidlToAidlCallbackConverter(aidlResponseHandler);
        try {
            if (supplier.get() != null) {
                long semSetSehCallback = semSetSehCallback();
                Slog.d("HidlToAidlSessionAdapter", "Face HAL ready, HAL ID: " + semSetSehCallback);
                if (semSetSehCallback == 0) {
                    Slog.d("HidlToAidlSessionAdapter", "Unable to set HIDL callback.");
                }
            } else {
                Slog.e(
                        "HidlToAidlSessionAdapter",
                        "Unable to set HIDL callback. HIDL daemon is null.");
            }
        } catch (RemoteException unused) {
            Slog.d("HidlToAidlSessionAdapter", "Failed to set callback");
        }
    }

    public final IBinder asBinder() {
        return null;
    }

    public final ICancellationSignal authenticate(long j) {
        ((IBiometricsFace) this.mSession.get()).authenticate(j);
        return new Cancellation();
    }

    public final ICancellationSignal authenticateWithContext(
            long j, OperationContext operationContext) {
        Slog.e("HidlToAidlSessionAdapter", "authenticateWithContext unsupported in HIDL");
        return authenticate(j);
    }

    public final void close() {
        Slog.e("HidlToAidlSessionAdapter", "close unsupported in HIDL");
    }

    public final ICancellationSignal detectInteraction() {
        ((IBiometricsFace) this.mSession.get()).authenticate(0L);
        return new Cancellation();
    }

    public final ICancellationSignal detectInteractionWithContext(
            OperationContext operationContext) {
        Slog.e("HidlToAidlSessionAdapter", "detectInteractionWithContext unsupported in HIDL");
        return detectInteraction();
    }

    public final ICancellationSignal enroll(
            HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) {
        ArrayList arrayList = new ArrayList();
        byte[] byteArray = HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
        for (int i = 0; i < 69; i++) {
            arrayList.add(Byte.valueOf(byteArray[i]));
        }
        ArrayList arrayList2 = new ArrayList();
        for (byte b2 : bArr) {
            arrayList2.add(Integer.valueOf(AidlConversionUtils.convertAidlToFrameworkFeature(b2)));
        }
        ((IBiometricsFace) this.mSession.get()).enroll(arrayList, 75, arrayList2);
        return new Cancellation();
    }

    public final ICancellationSignal enrollWithContext(
            HardwareAuthToken hardwareAuthToken,
            byte b,
            byte[] bArr,
            NativeHandle nativeHandle,
            OperationContext operationContext) {
        Slog.e("HidlToAidlSessionAdapter", "enrollWithContext unsupported in HIDL");
        return enroll(hardwareAuthToken, b, bArr, nativeHandle);
    }

    public final ICancellationSignal enrollWithOptions(FaceEnrollOptions faceEnrollOptions) {
        Slog.e("HidlToAidlSessionAdapter", "enrollWithOptions unsupported in HIDL");
        return null;
    }

    public final void enumerateEnrollments() {
        ((IBiometricsFace) this.mSession.get()).enumerate();
    }

    public final void generateChallenge() {
        ((ArrayList) this.mGeneratedChallengeCount).add(0, Long.valueOf(this.mClock.millis()));
        if (this.mGenerateChallengeCreatedAt == -1
                || this.mGenerateChallengeResult == -1
                || this.mClock.millis() - this.mGenerateChallengeCreatedAt >= 60000) {
            this.mGenerateChallengeCreatedAt = this.mClock.millis();
            long j = ((IBiometricsFace) this.mSession.get()).generateChallenge(600).value;
            this.mGenerateChallengeResult = j;
            this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onChallengeGenerated(j);
            return;
        }
        Slog.d("HidlToAidlSessionAdapter", "Current challenge is cached and will be reused");
        HidlToAidlCallbackConverter hidlToAidlCallbackConverter = this.mHidlToAidlCallbackConverter;
        hidlToAidlCallbackConverter.mAidlResponseHandler.onChallengeGenerated(
                this.mGenerateChallengeResult);
    }

    public final void getAuthenticatorId() {
        this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onAuthenticatorIdRetrieved(
                ((IBiometricsFace) this.mSession.get()).getAuthenticatorId().value);
    }

    public final EnrollmentStageConfig[] getEnrollmentConfig(byte b) {
        Slog.e("HidlToAidlSessionAdapter", "getEnrollmentConfig unsupported in HIDL");
        return null;
    }

    public final int getFaceId() {
        List enrolledFaces =
                ((FaceManager) this.mContext.getSystemService(FaceManager.class))
                        .getEnrolledFaces(this.mUserId);
        if (!enrolledFaces.isEmpty()) {
            return ((Face) enrolledFaces.get(0)).getBiometricId();
        }
        Slog.d("HidlToAidlSessionAdapter", "No faces to get feature from.");
        this.mHidlToAidlCallbackConverter.onError(this.mUserId, 11, 0, 0L);
        return -1;
    }

    public final void getFeatures() {
        int faceId = getFaceId();
        if (faceId == -1 || this.mFeature == -1) {
            return;
        }
        OptionalBool feature =
                ((IBiometricsFace) this.mSession.get()).getFeature(this.mFeature, faceId);
        int i = feature.status;
        if (i == 0 && feature.value) {
            this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onFeaturesRetrieved(
                    new byte[] {AidlConversionUtils.convertFrameworkToAidlFeature(this.mFeature)});
        } else if (i == 0) {
            this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onFeaturesRetrieved(new byte[0]);
        } else {
            this.mHidlToAidlCallbackConverter.onError(this.mUserId, 17, 0, 0L);
        }
        this.mFeature = -1;
    }

    public final String getInterfaceHash() {
        Slog.e("HidlToAidlSessionAdapter", "getInterfaceHash unsupported in HIDL");
        return null;
    }

    public final int getInterfaceVersion() {
        Slog.e("HidlToAidlSessionAdapter", "getInterfaceVersion unsupported in HIDL");
        return 0;
    }

    public final void invalidateAuthenticatorId() {
        Slog.e("HidlToAidlSessionAdapter", "invalidateAuthenticatorId unsupported in HIDL");
        AidlResponseHandler aidlResponseHandler =
                this.mHidlToAidlCallbackConverter.mAidlResponseHandler;
        aidlResponseHandler.getClass();
        Slog.e("AidlResponseHandler", "FaceInvalidationClient is not supported in the HAL.");
        aidlResponseHandler.handleResponse(
                FaceInvalidationClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda9(6),
                null);
    }

    public final void onContextChanged(OperationContext operationContext) {
        Slog.e("HidlToAidlSessionAdapter", "onContextChanged unsupported in HIDL");
    }

    public final void removeEnrollments(int[] iArr) {
        ((IBiometricsFace) this.mSession.get()).remove(iArr[0]);
    }

    public final void resetLockout(HardwareAuthToken hardwareAuthToken) {
        ArrayList arrayList = new ArrayList();
        byte[] byteArray = HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
        for (int i = 0; i < 69; i++) {
            arrayList.add(Byte.valueOf(byteArray[i]));
        }
        ((IBiometricsFace) this.mSession.get()).resetLockout(arrayList);
    }

    public final void revokeChallenge(long j) {
        final long millis = this.mClock.millis();
        ((ArrayList) this.mGeneratedChallengeCount)
                .removeIf(
                        new Predicate() { // from class:
                                          // com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return millis - ((Long) obj).longValue() > 600000;
                            }
                        });
        if (!((ArrayList) this.mGeneratedChallengeCount).isEmpty()) {
            ((ArrayList) this.mGeneratedChallengeCount).remove(0);
        }
        if (((ArrayList) this.mGeneratedChallengeCount).size() == 0) {
            this.mGenerateChallengeCreatedAt = -1L;
            this.mGenerateChallengeResult = -1L;
            ((IBiometricsFace) this.mSession.get()).revokeChallenge();
            this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onChallengeRevoked(0L);
            return;
        }
        Slog.w(
                "HidlToAidlSessionAdapter",
                "scheduleRevokeChallenge skipped - challenge still in use: "
                        + this.mGeneratedChallengeCount);
        this.mHidlToAidlCallbackConverter.onError(this.mUserId, 2, 0, 0L);
    }

    public final int semResumeEnroll() {
        try {
            return ((ISehBiometricsFace) this.mSession.get()).sehResumeEnrollment();
        } catch (RemoteException e) {
            Slog.e("HidlToAidlSessionAdapter", "semResumeEnroll HIDL : ", e);
            return -1;
        }
    }

    public final long semSetSehCallback() {
        long currentTimeMillis = System.currentTimeMillis();
        long j =
                SemBiometricFeature.FEATURE_JDM_HAL
                        ? ((vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace)
                                        this.mSession.get())
                                .sehSetCallback(this.mHidlToAidlCallbackConverter)
                                .value
                        : ((ISehBiometricsFace) this.mSession.get())
                                .sehSetCallbackEx(this.mHidlToAidlCallbackConverter)
                                .value;
        Slog.w(
                "HidlToAidlSessionAdapter",
                "SetCallback FINISH ("
                        + (System.currentTimeMillis() - currentTimeMillis)
                        + "ms) RESULT(HAL id): "
                        + j);
        long currentTimeMillis2 = System.currentTimeMillis();
        ((vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace) this.mSession.get())
                .sehGetSecurityLevel(
                        new ISehBiometricsFace
                                .sehGetSecurityLevelCallback() { // from class:
                                                                 // com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter$$ExternalSyntheticLambda1
                            @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace.sehGetSecurityLevelCallback
                            public final void onValues(int i, int i2) {
                                HidlToAidlSessionAdapter hidlToAidlSessionAdapter =
                                        HidlToAidlSessionAdapter.this;
                                if (i != 0) {
                                    hidlToAidlSessionAdapter.getClass();
                                    Slog.w("HidlToAidlSessionAdapter", "SecurityLevel fail");
                                } else {
                                    hidlToAidlSessionAdapter.mSecurityLevel = i2;
                                    StringBuilder sb = new StringBuilder("SecurityLevel : ");
                                    sb.append(i2);
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            sb,
                                            Utils.DEBUG ? " (Strong=1,2,3)" : "",
                                            "HidlToAidlSessionAdapter");
                                }
                            }
                        });
        Slog.w(
                "HidlToAidlSessionAdapter",
                "SecurityLevel FINISH ("
                        + (System.currentTimeMillis() - currentTimeMillis2)
                        + "ms) ");
        return j;
    }

    public final void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) {
        int faceId = getFaceId();
        if (faceId == -1) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        byte[] byteArray = HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
        for (int i = 0; i < 69; i++) {
            arrayList.add(Byte.valueOf(byteArray[i]));
        }
        if (((IBiometricsFace) this.mSession.get())
                        .setFeature(
                                AidlConversionUtils.convertAidlToFrameworkFeature(b),
                                z,
                                arrayList,
                                faceId)
                == 0) {
            this.mHidlToAidlCallbackConverter.mAidlResponseHandler.onFeatureSet(b);
        } else {
            this.mHidlToAidlCallbackConverter.onError(this.mUserId, 17, 0, 0L);
        }
    }
}
