package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.biometrics.face.V1_0.OptionalUint64;
import android.hardware.face.Face;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFaceClientCallback;

public final class SehTestHal extends ISehBiometricsFace.Stub {
    public static SehTestHal mSehTestHal;
    public SemTestHalHelper.Action currentAction;
    public List currentActionList;
    public int currentActionListPos;
    public boolean isEnrollSessionOpen;
    public ISehBiometricsFaceClientCallback mCallback;
    public final Context mContext;
    public int mFaceId;
    public boolean mGlassesOn = false;
    public final AnonymousClass1 mH;
    public final int mSensorId;
    public final SemTestHalHelper mTestHalHelper;
    public int mUserId;

    public SehTestHal(Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
        ?? r1 = new Handler(Watchdog$$ExternalSyntheticOutline0.m(-2, "face.hidl.SehTestHal", true).getLooper()) { // from class: com.android.server.biometrics.sensors.face.hidl.SehTestHal.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 100) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown message:"), message.what, "face.hidl.SehTestHal");
                    return;
                }
                SehTestHal sehTestHal = SehTestHal.this;
                SemTestHalHelper.Action action = sehTestHal.currentAction;
                if (action == null) {
                    Slog.d("face.hidl.SehTestHal", "doAction : currentAction is null");
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
        this.mTestHalHelper = new SemTestHalHelper(8, new SemTestHalHelper.Callback() { // from class: com.android.server.biometrics.sensors.face.hidl.SehTestHal.2
            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverAcquiredEvent(int i2, int i3) {
                try {
                    SehTestHal sehTestHal = SehTestHal.this;
                    sehTestHal.mCallback.onAcquired(sehTestHal.mUserId, i2, i3, 0L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverAuthenticationResult(int i2) {
                try {
                    SehTestHal sehTestHal = SehTestHal.this;
                    sehTestHal.mCallback.onAuthenticated(0L, i2, sehTestHal.mUserId, new ArrayList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverEnrollResult(int i2) {
                try {
                    SehTestHal sehTestHal = SehTestHal.this;
                    sehTestHal.mCallback.onEnrollResult(sehTestHal.mFaceId, sehTestHal.mUserId, i2, 0L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public final void deliverErrorEvent(int i2, int i3) {
                try {
                    SehTestHal sehTestHal = SehTestHal.this;
                    sehTestHal.mCallback.onError(sehTestHal.mUserId, i2, i3, 0L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        r1.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.SehTestHal$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemTestHalHelper semTestHalHelper = SehTestHal.this.mTestHalHelper;
                if (semTestHalHelper != null) {
                    semTestHalHelper.initActions();
                }
            }
        });
        if (Utils.DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "SehTestHal: constructed, ", "face.hidl.SehTestHal");
        }
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int authenticate(long j) {
        Slog.w("face.hidl.SehTestHal", "authenticate");
        try {
            sehAuthenticate(0, j, null);
        } catch (Exception unused) {
        }
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int cancel() {
        Slog.w("face.hidl.SehTestHal", "cancel");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        iSehBiometricsFaceClientCallback.onError(this.mUserId, 5, 0, 0L);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int enroll(ArrayList arrayList, int i, ArrayList arrayList2) {
        Slog.w("face.hidl.SehTestHal", "enroll");
        if (this.isEnrollSessionOpen) {
            List biometricsForUser = FaceUtils.getInstance(this.mSensorId, null).getBiometricsForUser(this.mContext, this.mUserId);
            boolean z = false;
            int i2 = 1;
            while (!z) {
                Iterator it = ((ArrayList) biometricsForUser).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    if (i2 == ((Face) it.next()).getBiometricId()) {
                        i2++;
                        z = false;
                        break;
                    }
                }
            }
            this.mFaceId = i2;
            List list = this.mTestHalHelper.mEnrollActionList;
            this.currentActionList = list;
            this.currentActionListPos = 0;
            this.mGlassesOn = false;
            SemTestHalHelper.Action action = (SemTestHalHelper.Action) ((ArrayList) list).get(0);
            this.currentAction = action;
            sendEmptyMessageDelayed(100, action.delay);
            Slog.d("face.hidl.SehTestHal", "start enroll: " + this.mFaceId + ", action size = " + ((ArrayList) this.currentActionList).size());
        } else {
            Slog.e("face.hidl.SehTestHal", "enroll : generateChallenge not done");
        }
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int enumerate() {
        Slog.w("face.hidl.SehTestHal", "enumerate");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        iSehBiometricsFaceClientCallback.onEnumerate(this.mUserId, 0L, new ArrayList());
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final synchronized OptionalUint64 generateChallenge(int i) {
        OptionalUint64 optionalUint64;
        Slog.w("face.hidl.SehTestHal", "generateChallenge");
        optionalUint64 = new OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        this.isEnrollSessionOpen = true;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalUint64 getAuthenticatorId() {
        OptionalUint64 optionalUint64 = new OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalBool getFeature(int i, int i2) {
        OptionalBool optionalBool = new OptionalBool();
        optionalBool.status = 0;
        optionalBool.value = true;
        return optionalBool;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int remove(int i) {
        Slog.w("face.hidl.SehTestHal", "remove");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        if (i != 0) {
            iSehBiometricsFaceClientCallback.onRemoved(this.mUserId, 0L, new ArrayList(Collections.singletonList(Integer.valueOf(i))));
            return 0;
        }
        List biometricsForUser = FaceUtils.getInstance(this.mSensorId, null).getBiometricsForUser(this.mContext, this.mUserId);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) biometricsForUser).iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((Face) it.next()).getBiometricId()));
        }
        this.mCallback.onRemoved(this.mUserId, 0L, arrayList);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int resetLockout(ArrayList arrayList) {
        Slog.w("face.hidl.SehTestHal", "resetLockout");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final synchronized int revokeChallenge() {
        Slog.w("face.hidl.SehTestHal", "revokeChallenge");
        this.isEnrollSessionOpen = false;
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehAuthenticate(int i, long j, ArrayList arrayList) {
        Slog.w("face.hidl.SehTestHal", "sehAuthenticate");
        List list = this.mTestHalHelper.mAuthActionList;
        this.currentActionList = list;
        this.currentActionListPos = 0;
        SemTestHalHelper.Action action = (SemTestHalHelper.Action) ((ArrayList) list).get(0);
        this.currentAction = action;
        sendEmptyMessageDelayed(100, action.delay);
        Slog.d("face.hidl.SehTestHal", "start authenticate: " + this.mFaceId + ", action size = " + ((ArrayList) this.currentActionList).size());
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
    public final int sehAuthenticateForIssuance(int i, long j, ArrayList arrayList, boolean z, boolean z2) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final synchronized int sehCloseTaSession() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final String sehGetEngineVersion() {
        return "";
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final void sehGetSecurityLevel(ISehBiometricsFace.sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
        sehgetsecuritylevelcallback.onValues(0, 2);
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final String sehGetTaInfo() {
        return "";
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final boolean sehIsTaSessionClosed() {
        return true;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final synchronized int sehOpenTaSession() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehPauseEnrollment() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehResumeEnrollment() {
        sendEmptyMessageDelayed(100, 0L);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int setActiveUser(int i, String str) {
        Slog.w("face.hidl.SehTestHal", "setActiveUser");
        this.mUserId = i;
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalUint64 setCallback(IBiometricsFaceClientCallback iBiometricsFaceClientCallback) {
        this.mCallback = (ISehBiometricsFaceClientCallback) iBiometricsFaceClientCallback;
        return new OptionalUint64();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int setFeature(int i, boolean z, ArrayList arrayList, int i2) {
        Slog.w("face.hidl.SehTestHal", "setFeature");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final void userActivity() {
        Slog.w("face.hidl.SehTestHal", "userActivity");
    }
}
