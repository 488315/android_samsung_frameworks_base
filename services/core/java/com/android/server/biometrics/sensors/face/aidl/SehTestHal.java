package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.face.AuthenticationFrame;
import android.hardware.biometrics.face.BaseFrame;
import android.hardware.biometrics.face.EnrollmentFrame;
import android.hardware.biometrics.face.EnrollmentStageConfig;
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
import com.android.server.ServiceThread;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SehTestHal extends IFace.Stub {
    public static SehTestHal mSehTestHal;
    public SemTestHalHelper.Action currentAction;
    public List currentActionList;
    public int currentActionListPos;
    public boolean isEnrollSessionOpen;
    public ISessionCallback mCallback;
    public final Context mContext;
    public int mFaceId;
    public boolean mGlassesOn = false;

    /* renamed from: mH */
    public Handler f1654mH;
    public final int mSensorId;
    public SemTestHalHelper mTestHalHelper;
    public ServiceThread mThread;
    public int mUserId;

    @Override // android.hardware.biometrics.face.IFace
    public String getInterfaceHash() {
        return "74b0b7cb149ee205b12cd2254d216725c6e5429c";
    }

    @Override // android.hardware.biometrics.face.IFace
    public int getInterfaceVersion() {
        return 2;
    }

    public static synchronized SehTestHal getInstance(Context context, int i) {
        SehTestHal sehTestHal;
        synchronized (SehTestHal.class) {
            if (mSehTestHal == null) {
                mSehTestHal = new SehTestHal(context, i);
            }
            sehTestHal = mSehTestHal;
        }
        return sehTestHal;
    }

    public SehTestHal(Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
        ServiceThread serviceThread = new ServiceThread("face.aidl.SehTestHal", -2, true);
        this.mThread = serviceThread;
        serviceThread.start();
        this.f1654mH = new Handler(this.mThread.getLooper()) { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 100) {
                    SehTestHal.this.doAction();
                    return;
                }
                Slog.w("face.aidl.SehTestHal", "Unknown message:" + message.what);
            }
        };
        InitTPA(i);
    }

    @Override // android.hardware.biometrics.face.IFace
    public SensorProps[] getSensorProps() {
        Slog.w("face.aidl.SehTestHal", "getSensorProps");
        return new SensorProps[0];
    }

    @Override // android.hardware.biometrics.face.IFace
    public ISession createSession(int i, int i2, final ISessionCallback iSessionCallback) {
        Slog.w("face.aidl.SehTestHal", "createSession, sensorId: " + i + " userId: " + i2);
        this.mCallback = iSessionCallback;
        return new ISession.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.2
            @Override // android.hardware.biometrics.face.ISession
            public EnrollmentStageConfig[] getEnrollmentConfig(byte b) {
                return new EnrollmentStageConfig[0];
            }

            @Override // android.hardware.biometrics.face.ISession
            public String getInterfaceHash() {
                return "74b0b7cb149ee205b12cd2254d216725c6e5429c";
            }

            @Override // android.hardware.biometrics.face.ISession
            public int getInterfaceVersion() {
                return 2;
            }

            @Override // android.hardware.biometrics.face.ISession
            public void generateChallenge() {
                Slog.w("face.aidl.SehTestHal", "generateChallenge");
                SehTestHal.this.isEnrollSessionOpen = true;
                iSessionCallback.onChallengeGenerated(0L);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void revokeChallenge(long j) {
                Slog.w("face.aidl.SehTestHal", "revokeChallenge: " + j);
                SehTestHal.this.isEnrollSessionOpen = false;
                iSessionCallback.onChallengeRevoked(j);
            }

            public final boolean isEnrolledFace() {
                List biometricsForUser = FaceUtils.getInstance(SehTestHal.this.mSensorId).getBiometricsForUser(SehTestHal.this.mContext, SehTestHal.this.mUserId);
                return biometricsForUser != null && biometricsForUser.size() >= 1;
            }

            public final int getEmptyFaceIdForEnroll() {
                List biometricsForUser = FaceUtils.getInstance(SehTestHal.this.mSensorId).getBiometricsForUser(SehTestHal.this.mContext, SehTestHal.this.mUserId);
                boolean z = false;
                int i3 = 1;
                while (!z) {
                    Iterator it = biometricsForUser.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = true;
                            break;
                        }
                        if (i3 == ((Face) it.next()).getBiometricId()) {
                            i3++;
                            z = false;
                            break;
                        }
                    }
                }
                return i3;
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) {
                Slog.w("face.aidl.SehTestHal", "enroll");
                if (SehTestHal.this.isEnrollSessionOpen) {
                    SehTestHal.this.mFaceId = getEmptyFaceIdForEnroll();
                    SehTestHal sehTestHal = SehTestHal.this;
                    sehTestHal.currentActionList = sehTestHal.mTestHalHelper.getEnrollActionList();
                    SehTestHal sehTestHal2 = SehTestHal.this;
                    sehTestHal2.currentActionListPos = 0;
                    sehTestHal2.mGlassesOn = false;
                    SehTestHal sehTestHal3 = SehTestHal.this;
                    sehTestHal3.currentAction = (SemTestHalHelper.Action) sehTestHal3.currentActionList.get(sehTestHal3.currentActionListPos);
                    SehTestHal.this.f1654mH.sendEmptyMessageDelayed(100, SehTestHal.this.currentAction.getDelay());
                    Slog.d("face.aidl.SehTestHal", "start enroll: " + SehTestHal.this.mFaceId + ", action size = " + SehTestHal.this.currentActionList.size());
                } else {
                    Slog.e("face.aidl.SehTestHal", "enroll : generateChallenge not done");
                }
                return new ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.2.1
                    public String getInterfaceHash() {
                        return "a9ebb97f5abea1dc6800b69d821ef61944e80e65";
                    }

                    public int getInterfaceVersion() {
                        return 3;
                    }

                    public void cancel() {
                        iSessionCallback.onError((byte) 5, 0);
                    }
                };
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal authenticate(long j) {
                Slog.w("face.aidl.SehTestHal", "authenticate");
                if (!isEnrolledFace()) {
                    try {
                        SehTestHal.this.mCallback.onError((byte) 8, 0);
                        return null;
                    } catch (Exception e) {
                        Slog.e("face.aidl.SehTestHal", "enroll no enrolled : " + e);
                        return null;
                    }
                }
                SehTestHal sehTestHal = SehTestHal.this;
                sehTestHal.currentActionList = sehTestHal.mTestHalHelper.getAuthActionList();
                SehTestHal sehTestHal2 = SehTestHal.this;
                sehTestHal2.currentActionListPos = 0;
                sehTestHal2.currentAction = (SemTestHalHelper.Action) sehTestHal2.currentActionList.get(0);
                SehTestHal.this.f1654mH.sendEmptyMessageDelayed(100, SehTestHal.this.currentAction.getDelay());
                Slog.d("face.aidl.SehTestHal", "start authenticate: " + SehTestHal.this.mFaceId + ", action size = " + SehTestHal.this.currentActionList.size());
                return new ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.2.2
                    public String getInterfaceHash() {
                        return "a9ebb97f5abea1dc6800b69d821ef61944e80e65";
                    }

                    public int getInterfaceVersion() {
                        return 3;
                    }

                    public void cancel() {
                        iSessionCallback.onError((byte) 5, 0);
                    }
                };
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal detectInteraction() {
                Slog.w("face.aidl.SehTestHal", "detectInteraction");
                return new ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.2.3
                    public String getInterfaceHash() {
                        return "a9ebb97f5abea1dc6800b69d821ef61944e80e65";
                    }

                    public int getInterfaceVersion() {
                        return 3;
                    }

                    public void cancel() {
                        iSessionCallback.onError((byte) 5, 0);
                    }
                };
            }

            @Override // android.hardware.biometrics.face.ISession
            public void enumerateEnrollments() {
                Slog.w("face.aidl.SehTestHal", "enumerateEnrollments");
                iSessionCallback.onEnrollmentsEnumerated(new int[0]);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void removeEnrollments(int[] iArr) {
                Slog.w("face.aidl.SehTestHal", "removeEnrollments");
                iSessionCallback.onEnrollmentsRemoved(iArr);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getFeatures() {
                Slog.w("face.aidl.SehTestHal", "getFeatures");
                iSessionCallback.onFeaturesRetrieved(new byte[0]);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) {
                Slog.w("face.aidl.SehTestHal", "setFeature");
                iSessionCallback.onFeatureSet(b);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getAuthenticatorId() {
                Slog.w("face.aidl.SehTestHal", "getAuthenticatorId");
                iSessionCallback.onAuthenticatorIdRetrieved(0L);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void invalidateAuthenticatorId() {
                Slog.w("face.aidl.SehTestHal", "invalidateAuthenticatorId");
                iSessionCallback.onAuthenticatorIdInvalidated(0L);
            }

            @Override // android.hardware.biometrics.face.ISession
            public void resetLockout(HardwareAuthToken hardwareAuthToken) {
                Slog.w("face.aidl.SehTestHal", "resetLockout");
                iSessionCallback.onLockoutCleared();
            }

            @Override // android.hardware.biometrics.face.ISession
            public void close() {
                Slog.w("face.aidl.SehTestHal", "close");
                iSessionCallback.onSessionClosed();
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
                return authenticate(j);
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle, OperationContext operationContext) {
                return enroll(hardwareAuthToken, b, bArr, nativeHandle);
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
                return detectInteraction();
            }

            @Override // android.hardware.biometrics.face.ISession
            public void onContextChanged(OperationContext operationContext) {
                Slog.w("face.aidl.SehTestHal", "onContextChanged");
            }
        };
    }

    public void postUpdateAction() {
        this.f1654mH.post(new SehTestHal$$ExternalSyntheticLambda0(this));
    }

    public final void initActions() {
        SemTestHalHelper semTestHalHelper = this.mTestHalHelper;
        if (semTestHalHelper != null) {
            semTestHalHelper.initActions();
        }
    }

    public final void InitTPA(int i) {
        this.mTestHalHelper = new SemTestHalHelper(8, new SemTestHalHelper.Callback() { // from class: com.android.server.biometrics.sensors.face.aidl.SehTestHal.3
            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverAcquiredEvent(int i2, int i3) {
                try {
                    if (SehTestHal.this.isEnrollSessionOpen) {
                        EnrollmentFrame enrollmentFrame = new EnrollmentFrame();
                        BaseFrame baseFrame = enrollmentFrame.data;
                        baseFrame.acquiredInfo = (byte) i2;
                        baseFrame.vendorCode = i3;
                        SehTestHal.this.mCallback.onEnrollmentFrame(enrollmentFrame);
                    } else {
                        AuthenticationFrame authenticationFrame = new AuthenticationFrame();
                        BaseFrame baseFrame2 = authenticationFrame.data;
                        baseFrame2.acquiredInfo = (byte) i2;
                        baseFrame2.vendorCode = i3;
                        SehTestHal.this.mCallback.onAuthenticationFrame(authenticationFrame);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverErrorEvent(int i2, int i3) {
                try {
                    SehTestHal.this.mCallback.onError((byte) i2, i3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverEnrollResult(int i2) {
                try {
                    SehTestHal.this.mCallback.onEnrollmentProgress(SehTestHal.this.mFaceId, i2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverAuthenticationResult(int i2) {
                try {
                    if (i2 > 0) {
                        SehTestHal.this.mCallback.onAuthenticationSucceeded(i2, HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
                    } else {
                        SehTestHal.this.mCallback.onAuthenticationFailed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.f1654mH.post(new SehTestHal$$ExternalSyntheticLambda0(this));
        if (Utils.DEBUG) {
            Slog.d("face.aidl.SehTestHal", "constructed, " + i);
        }
    }

    public final void doAction() {
        SemTestHalHelper.Action action = this.currentAction;
        if (action == null) {
            Slog.d("face.aidl.SehTestHal", "doAction : currentAction is null");
            return;
        }
        action.run();
        if (this.currentAction.getCallbackType() == SemTestHalHelper.CallbackType.ACQUIRED && this.currentAction.getCode() == 22 && this.currentAction.getVendorCode() == 1016) {
            this.mGlassesOn = true;
        } else if (this.mGlassesOn && this.currentAction.getCallbackType() == SemTestHalHelper.CallbackType.ENROLL_RESULT && this.currentAction.getValue() == 30) {
            List list = this.currentActionList;
            int i = this.currentActionListPos + 1;
            this.currentActionListPos = i;
            this.currentAction = (SemTestHalHelper.Action) list.get(i);
            return;
        }
        if (this.currentActionListPos + 1 < this.currentActionList.size()) {
            List list2 = this.currentActionList;
            int i2 = this.currentActionListPos + 1;
            this.currentActionListPos = i2;
            SemTestHalHelper.Action action2 = (SemTestHalHelper.Action) list2.get(i2);
            this.currentAction = action2;
            this.f1654mH.sendEmptyMessageDelayed(100, action2.getDelay());
        }
    }
}
