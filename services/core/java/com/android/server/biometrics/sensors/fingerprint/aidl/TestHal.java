package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.hardware.keymaster.HardwareAuthToken;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class TestHal extends IFingerprint.Stub {
    public ISession createSession(int i, int i2, final ISessionCallback iSessionCallback) {
        Slog.w("fingerprint.aidl.TestHal", "createSession, sensorId: " + i + " userId: " + i2);
        return new ISession
                .Stub() { // from class:
                          // com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.1

            /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
            /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.TestHal$1$1, reason: invalid class name and collision with other inner class name */
            public final class C00141 extends ICancellationSignal.Stub {
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ AnonymousClass1 this$1;

                public /* synthetic */ C00141(AnonymousClass1 anonymousClass1, int i) {
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
                Slog.w("fingerprint.aidl.TestHal", "authenticate");
                return new C00141(this, 1);
            }

            public final ICancellationSignal authenticateWithContext(
                    long j, OperationContext operationContext) {
                return authenticate(j);
            }

            public final void close() {
                Slog.w("fingerprint.aidl.TestHal", "close");
                iSessionCallback.onSessionClosed();
            }

            public final ICancellationSignal detectInteraction() {
                Slog.w("fingerprint.aidl.TestHal", "detectInteraction");
                return new C00141(this, 2);
            }

            public final ICancellationSignal detectInteractionWithContext(
                    OperationContext operationContext) {
                return detectInteraction();
            }

            public final ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) {
                Slog.w("fingerprint.aidl.TestHal", "enroll");
                return new C00141(this, 0);
            }

            public final ICancellationSignal enrollWithContext(
                    HardwareAuthToken hardwareAuthToken, OperationContext operationContext) {
                return enroll(hardwareAuthToken);
            }

            public final void enumerateEnrollments() {
                Slog.w("fingerprint.aidl.TestHal", "enumerateEnrollments");
                iSessionCallback.onEnrollmentsEnumerated(new int[0]);
            }

            public final void generateChallenge() {
                Slog.w("fingerprint.aidl.TestHal", "generateChallenge");
                iSessionCallback.onChallengeGenerated(0L);
            }

            public final void getAuthenticatorId() {
                Slog.w("fingerprint.aidl.TestHal", "getAuthenticatorId");
                iSessionCallback.onAuthenticatorIdRetrieved(0L);
            }

            public final String getInterfaceHash() {
                return "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
            }

            public final int getInterfaceVersion() {
                return 4;
            }

            public final void invalidateAuthenticatorId() {
                Slog.w("fingerprint.aidl.TestHal", "invalidateAuthenticatorId");
                iSessionCallback.onAuthenticatorIdInvalidated(0L);
            }

            public final void onContextChanged(OperationContext operationContext) {
                Slog.w("fingerprint.aidl.TestHal", "onContextChanged");
            }

            public final void onPointerCancelWithContext(PointerContext pointerContext) {
                Slog.w("fingerprint.aidl.TestHal", "onPointerCancelWithContext");
            }

            public final void onPointerDown(int i3, int i4, int i5, float f, float f2) {
                Slog.w("fingerprint.aidl.TestHal", "onPointerDown");
            }

            public final void onPointerDownWithContext(PointerContext pointerContext) {
                onPointerDown(
                        pointerContext.pointerId,
                        (int) pointerContext.x,
                        (int) pointerContext.y,
                        pointerContext.minor,
                        pointerContext.major);
            }

            public final void onPointerUp(int i3) {
                Slog.w("fingerprint.aidl.TestHal", "onPointerUp");
            }

            public final void onPointerUpWithContext(PointerContext pointerContext) {
                onPointerUp(pointerContext.pointerId);
            }

            public final void onUiReady() {
                Slog.w("fingerprint.aidl.TestHal", "onUiReady");
            }

            public final void removeEnrollments(int[] iArr) {
                Slog.w("fingerprint.aidl.TestHal", "removeEnrollments");
                iSessionCallback.onEnrollmentsRemoved(iArr);
            }

            public final void resetLockout(HardwareAuthToken hardwareAuthToken) {
                Slog.w("fingerprint.aidl.TestHal", "resetLockout");
                iSessionCallback.onLockoutCleared();
            }

            public final void revokeChallenge(long j) {
                Slog.w("fingerprint.aidl.TestHal", "revokeChallenge: " + j);
                iSessionCallback.onChallengeRevoked(j);
            }

            public final void setIgnoreDisplayTouches(boolean z) {
                Slog.w("fingerprint.aidl.TestHal", "setIgnoreDisplayTouches");
            }
        };
    }

    public final String getInterfaceHash() {
        return "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
    }

    public final int getInterfaceVersion() {
        return 4;
    }

    public final SensorProps[] getSensorProps() {
        Slog.w("fingerprint.aidl.TestHal", "getSensorProps");
        return new SensorProps[0];
    }
}
