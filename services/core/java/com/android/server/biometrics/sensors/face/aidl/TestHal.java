package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.face.EnrollmentStageConfig;
import android.hardware.biometrics.face.FaceEnrollOptions;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.common.NativeHandle;
import android.hardware.keymaster.HardwareAuthToken;
import android.util.Slog;

public final class TestHal extends IFace.Stub {
    public final ISession createSession(int i, int i2, final ISessionCallback iSessionCallback) {
        Slog.w("face.aidl.TestHal", "createSession, sensorId: " + i + " userId: " + i2);
        return new ISession
                .Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.TestHal.1

            /* renamed from: com.android.server.biometrics.sensors.face.aidl.TestHal$1$1, reason: invalid class name and collision with other inner class name */
            public final class C00121 extends ICancellationSignal.Stub {
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ AnonymousClass1 this$1;

                public /* synthetic */ C00121(AnonymousClass1 anonymousClass1, int i) {
                    this.$r8$classId = i;
                    this.this$1 = anonymousClass1;
                }

                public final void cancel() {
                    switch (this.$r8$classId) {
                        case 0:
                            iSessionCallback.onError((byte) 5, 0);
                            break;
                        case 1:
                            iSessionCallback.onError((byte) 5, 0);
                            break;
                        default:
                            iSessionCallback.onError((byte) 5, 0);
                            break;
                    }
                }

                public final String getInterfaceHash() {
                    switch (this.$r8$classId) {
                    }
                    return "8a6cd86630181a4df6f20056259ec200ffe39209";
                }

                public final int getInterfaceVersion() {
                    switch (this.$r8$classId) {
                    }
                    return 4;
                }
            }

            public final ICancellationSignal authenticate(long j) {
                Slog.w("face.aidl.TestHal", "authenticate");
                return new C00121(this, 1);
            }

            public final ICancellationSignal authenticateWithContext(
                    long j, OperationContext operationContext) {
                return authenticate(j);
            }

            public final void close() {
                Slog.w("face.aidl.TestHal", "close");
                iSessionCallback.onSessionClosed();
            }

            public final ICancellationSignal detectInteraction() {
                Slog.w("face.aidl.TestHal", "detectInteraction");
                return new C00121(this, 2);
            }

            public final ICancellationSignal detectInteractionWithContext(
                    OperationContext operationContext) {
                return detectInteraction();
            }

            public final ICancellationSignal enroll(
                    HardwareAuthToken hardwareAuthToken,
                    byte b,
                    byte[] bArr,
                    NativeHandle nativeHandle) {
                Slog.w("face.aidl.TestHal", "enroll");
                return new C00121(this, 0);
            }

            public final ICancellationSignal enrollWithContext(
                    HardwareAuthToken hardwareAuthToken,
                    byte b,
                    byte[] bArr,
                    NativeHandle nativeHandle,
                    OperationContext operationContext) {
                return enroll(hardwareAuthToken, b, bArr, nativeHandle);
            }

            public final ICancellationSignal enrollWithOptions(
                    FaceEnrollOptions faceEnrollOptions) {
                return enroll(
                        faceEnrollOptions.hardwareAuthToken,
                        faceEnrollOptions.enrollmentType,
                        faceEnrollOptions.features,
                        faceEnrollOptions.nativeHandlePreview);
            }

            public final void enumerateEnrollments() {
                Slog.w("face.aidl.TestHal", "enumerateEnrollments");
                iSessionCallback.onEnrollmentsEnumerated(new int[0]);
            }

            public final void generateChallenge() {
                Slog.w("face.aidl.TestHal", "generateChallenge");
                iSessionCallback.onChallengeGenerated(0L);
            }

            public final void getAuthenticatorId() {
                Slog.w("face.aidl.TestHal", "getAuthenticatorId");
                iSessionCallback.onAuthenticatorIdRetrieved(0L);
            }

            public final EnrollmentStageConfig[] getEnrollmentConfig(byte b) {
                return new EnrollmentStageConfig[0];
            }

            public final void getFeatures() {
                Slog.w("face.aidl.TestHal", "getFeatures");
                iSessionCallback.onFeaturesRetrieved(new byte[0]);
            }

            public final String getInterfaceHash() {
                return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
            }

            public final int getInterfaceVersion() {
                return 4;
            }

            public final void invalidateAuthenticatorId() {
                Slog.w("face.aidl.TestHal", "invalidateAuthenticatorId");
                iSessionCallback.onAuthenticatorIdInvalidated(0L);
            }

            public final void onContextChanged(OperationContext operationContext) {
                Slog.w("face.aidl.TestHal", "onContextChanged");
            }

            public final void removeEnrollments(int[] iArr) {
                Slog.w("face.aidl.TestHal", "removeEnrollments");
                iSessionCallback.onEnrollmentsRemoved(iArr);
            }

            public final void resetLockout(HardwareAuthToken hardwareAuthToken) {
                Slog.w("face.aidl.TestHal", "resetLockout");
                iSessionCallback.onLockoutCleared();
            }

            public final void revokeChallenge(long j) {
                Slog.w("face.aidl.TestHal", "revokeChallenge: " + j);
                iSessionCallback.onChallengeRevoked(j);
            }

            public final void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) {
                Slog.w("face.aidl.TestHal", "setFeature");
                iSessionCallback.onFeatureSet(b);
            }
        };
    }

    public final String getInterfaceHash() {
        return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    }

    public final int getInterfaceVersion() {
        return 4;
    }

    public final SensorProps[] getSensorProps() {
        Slog.w("face.aidl.TestHal", "getSensorProps");
        return new SensorProps[0];
    }
}
