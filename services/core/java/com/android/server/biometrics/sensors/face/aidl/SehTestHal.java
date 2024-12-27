package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.face.AuthenticationFrame;
import android.hardware.biometrics.face.BaseFrame;
import android.hardware.biometrics.face.EnrollmentFrame;
import android.hardware.biometrics.face.EnrollmentStageConfig;
import android.hardware.biometrics.face.FaceEnrollOptions;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.common.NativeHandle;
import android.hardware.face.Face;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SehTestHal extends IFace.Stub {
    public static SehTestHal mSehTestHal;
    public SemTestHalHelper.Action currentAction;
    public List currentActionList;
    public int currentActionListPos;
    public boolean isEnrollSessionOpen;
    public ISessionCallback mCallback;
    public final Context mContext;
    public int mFaceId;
    public boolean mGlassesOn = false;
    public final AnonymousClass1 mH;
    public final int mSensorId;
    public final SemTestHalHelper mTestHalHelper;

    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.Handler, com.android.server.biometrics.sensors.face.aidl.SehTestHal$1] */
    public SehTestHal(Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
        ?? r1 = new Handler(Watchdog$$ExternalSyntheticOutline0.m(-2, "face.aidl.SehTestHal", true).getLooper()) { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 100) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown message:"), message.what, "face.aidl.SehTestHal");
                    return;
                }
                SehTestHal sehTestHal = SehTestHal.this;
                SemTestHalHelper.Action action = sehTestHal.currentAction;
                if (action == null) {
                    Slog.d("face.aidl.SehTestHal", "doAction : currentAction is null");
                    return;
                }
                action.run();
                SemTestHalHelper.Action action2 = sehTestHal.currentAction;
                SemTestHalHelper.CallbackType callbackType = action2.callbackType;
                if (callbackType == SemTestHalHelper.CallbackType.ACQUIRED && action2.code == 22 && action2.vendorCode == 1016) {
                    sehTestHal.mGlassesOn = true;
                } else if (sehTestHal.mGlassesOn && callbackType == SemTestHalHelper.CallbackType.ENROLL_RESULT && action2.value == 30) {
                    List list = sehTestHal.currentActionList;
                    int i2 = sehTestHal.currentActionListPos + 1;
                    sehTestHal.currentActionListPos = i2;
                    sehTestHal.currentAction = (SemTestHalHelper.Action) ((ArrayList) list).get(i2);
                    return;
                }
                if (sehTestHal.currentActionListPos + 1 < ((ArrayList) sehTestHal.currentActionList).size()) {
                    List list2 = sehTestHal.currentActionList;
                    int i3 = sehTestHal.currentActionListPos + 1;
                    sehTestHal.currentActionListPos = i3;
                    SemTestHalHelper.Action action3 = (SemTestHalHelper.Action) ((ArrayList) list2).get(i3);
                    sehTestHal.currentAction = action3;
                    sehTestHal.mH.sendEmptyMessageDelayed(100, action3.delay);
                }
            }
        };
        this.mH = r1;
        this.mTestHalHelper = new SemTestHalHelper(8, new SemTestHalHelper.Callback() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.3
            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverAcquiredEvent(int i2, int i3) {
                SehTestHal sehTestHal = SehTestHal.this;
                try {
                    if (sehTestHal.isEnrollSessionOpen) {
                        EnrollmentFrame enrollmentFrame = new EnrollmentFrame();
                        BaseFrame baseFrame = enrollmentFrame.data;
                        baseFrame.acquiredInfo = (byte) i2;
                        baseFrame.vendorCode = i3;
                        sehTestHal.mCallback.onEnrollmentFrame(enrollmentFrame);
                    } else {
                        AuthenticationFrame authenticationFrame = new AuthenticationFrame();
                        BaseFrame baseFrame2 = authenticationFrame.data;
                        baseFrame2.acquiredInfo = (byte) i2;
                        baseFrame2.vendorCode = i3;
                        sehTestHal.mCallback.onAuthenticationFrame(authenticationFrame);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverAuthenticationResult(int i2) {
                SehTestHal sehTestHal = SehTestHal.this;
                try {
                    if (i2 > 0) {
                        sehTestHal.mCallback.onAuthenticationSucceeded(i2, HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
                    } else {
                        sehTestHal.mCallback.onAuthenticationFailed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverEnrollResult(int i2) {
                try {
                    SehTestHal sehTestHal = SehTestHal.this;
                    sehTestHal.mCallback.onEnrollmentProgress(sehTestHal.mFaceId, i2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverErrorEvent(int i2, int i3) {
                try {
                    SehTestHal.this.mCallback.onError((byte) i2, i3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        r1.post(new SehTestHal$$ExternalSyntheticLambda0(this));
        if (Utils.DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "constructed, ", "face.aidl.SehTestHal");
        }
    }

    public final ISession createSession(int i, int i2, final ISessionCallback iSessionCallback) {
        PendingIntentController$$ExternalSyntheticOutline0.m(i, i2, "createSession, sensorId: ", " userId: ", "face.aidl.SehTestHal");
        this.mCallback = iSessionCallback;
        return new ISession.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.2

            /* renamed from: com.android.server.biometrics.sensors.face.aidl.SehTestHal$2$1, reason: invalid class name */
            public final class AnonymousClass1 extends ICancellationSignal.Stub {
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ AnonymousClass2 this$1;

                public /* synthetic */ AnonymousClass1(AnonymousClass2 anonymousClass2, int i) {
                    this.$r8$classId = i;
                    this.this$1 = anonymousClass2;
                }

                public final void cancel() {
                    switch (this.$r8$classId) {
                        case 0:
                            iSessionCallback.onError((byte) 5, 0);
                            break;
                        case 1:
                            iSessionCallback.onError((byte) 5, 0);
                            break;
                        case 2:
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
                Slog.w("face.aidl.SehTestHal", "authenticate");
                if (((ArrayList) FaceUtils.getInstance(SehTestHal.this.mSensorId, null).getBiometricsForUser(SehTestHal.this.mContext, 0)).size() < 1) {
                    try {
                        SehTestHal.this.mCallback.onError((byte) 8, 0);
                    } catch (Exception e) {
                        BootReceiver$$ExternalSyntheticOutline0.m(e, "enroll no enrolled : ", "face.aidl.SehTestHal");
                    }
                    return null;
                }
                SehTestHal sehTestHal = SehTestHal.this;
                List list = sehTestHal.mTestHalHelper.mAuthActionList;
                sehTestHal.currentActionList = list;
                sehTestHal.currentActionListPos = 0;
                sehTestHal.currentAction = (SemTestHalHelper.Action) ((ArrayList) list).get(0);
                SehTestHal sehTestHal2 = SehTestHal.this;
                sehTestHal2.mH.sendEmptyMessageDelayed(100, sehTestHal2.currentAction.delay);
                Slog.d("face.aidl.SehTestHal", "start authenticate: " + SehTestHal.this.mFaceId + ", action size = " + ((ArrayList) SehTestHal.this.currentActionList).size());
                return new AnonymousClass1(this, 1);
            }

            public final ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
                return authenticate(j);
            }

            public final void close() {
                Slog.w("face.aidl.SehTestHal", "close");
                iSessionCallback.onSessionClosed();
            }

            public final ICancellationSignal detectInteraction() {
                Slog.w("face.aidl.SehTestHal", "detectInteraction");
                return new AnonymousClass1(this, 2);
            }

            public final ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
                return detectInteraction();
            }

            public final ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) {
                Slog.w("face.aidl.SehTestHal", "enroll");
                SehTestHal sehTestHal = SehTestHal.this;
                if (sehTestHal.isEnrollSessionOpen) {
                    List biometricsForUser = FaceUtils.getInstance(sehTestHal.mSensorId, null).getBiometricsForUser(SehTestHal.this.mContext, 0);
                    int i3 = 1;
                    loop0: while (true) {
                        for (boolean z = false; !z; z = true) {
                            Iterator it = ((ArrayList) biometricsForUser).iterator();
                            while (it.hasNext()) {
                                if (i3 == ((Face) it.next()).getBiometricId()) {
                                    break;
                                }
                            }
                        }
                        i3++;
                    }
                    sehTestHal.mFaceId = i3;
                    SehTestHal sehTestHal2 = SehTestHal.this;
                    List list = sehTestHal2.mTestHalHelper.mEnrollActionList;
                    sehTestHal2.currentActionList = list;
                    sehTestHal2.currentActionListPos = 0;
                    sehTestHal2.mGlassesOn = false;
                    sehTestHal2.currentAction = (SemTestHalHelper.Action) ((ArrayList) list).get(0);
                    SehTestHal sehTestHal3 = SehTestHal.this;
                    sehTestHal3.mH.sendEmptyMessageDelayed(100, sehTestHal3.currentAction.delay);
                    Slog.d("face.aidl.SehTestHal", "start enroll: " + SehTestHal.this.mFaceId + ", action size = " + ((ArrayList) SehTestHal.this.currentActionList).size());
                } else {
                    Slog.e("face.aidl.SehTestHal", "enroll : generateChallenge not done");
                }
                return new AnonymousClass1(this, 0);
            }

            public final ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle, OperationContext operationContext) {
                return enroll(hardwareAuthToken, b, bArr, nativeHandle);
            }

            public final ICancellationSignal enrollWithOptions(FaceEnrollOptions faceEnrollOptions) {
                Slog.w("face.aidl.SehTestHal", "enrollWithOptions");
                return new AnonymousClass1(this, 3);
            }

            public final void enumerateEnrollments() {
                Slog.w("face.aidl.SehTestHal", "enumerateEnrollments");
                iSessionCallback.onEnrollmentsEnumerated(new int[0]);
            }

            public final void generateChallenge() {
                Slog.w("face.aidl.SehTestHal", "generateChallenge");
                SehTestHal.this.isEnrollSessionOpen = true;
                iSessionCallback.onChallengeGenerated(0L);
            }

            public final void getAuthenticatorId() {
                Slog.w("face.aidl.SehTestHal", "getAuthenticatorId");
                iSessionCallback.onAuthenticatorIdRetrieved(0L);
            }

            public final EnrollmentStageConfig[] getEnrollmentConfig(byte b) {
                return new EnrollmentStageConfig[0];
            }

            public final void getFeatures() {
                Slog.w("face.aidl.SehTestHal", "getFeatures");
                iSessionCallback.onFeaturesRetrieved(new byte[0]);
            }

            public final String getInterfaceHash() {
                return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
            }

            public final int getInterfaceVersion() {
                return 4;
            }

            public final void invalidateAuthenticatorId() {
                Slog.w("face.aidl.SehTestHal", "invalidateAuthenticatorId");
                iSessionCallback.onAuthenticatorIdInvalidated(0L);
            }

            public final void onContextChanged(OperationContext operationContext) {
                Slog.w("face.aidl.SehTestHal", "onContextChanged");
            }

            public final void removeEnrollments(int[] iArr) {
                Slog.w("face.aidl.SehTestHal", "removeEnrollments");
                iSessionCallback.onEnrollmentsRemoved(iArr);
            }

            public final void resetLockout(HardwareAuthToken hardwareAuthToken) {
                Slog.w("face.aidl.SehTestHal", "resetLockout");
                iSessionCallback.onLockoutCleared();
            }

            public final void revokeChallenge(long j) {
                Slog.w("face.aidl.SehTestHal", "revokeChallenge: " + j);
                SehTestHal.this.isEnrollSessionOpen = false;
                iSessionCallback.onChallengeRevoked(j);
            }

            public final void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) {
                Slog.w("face.aidl.SehTestHal", "setFeature");
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
        Slog.w("face.aidl.SehTestHal", "getSensorProps");
        return new SensorProps[0];
    }
}
