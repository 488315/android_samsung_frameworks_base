package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.biometrics.face.V1_0.OptionalUint64;
import android.hardware.face.Face;
import android.os.Handler;
import android.os.HidlMemory;
import android.os.Message;
import android.util.Slog;
import com.android.server.ServiceThread;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback;

/* loaded from: classes.dex */
public class SehTestHal extends ISehBiometricsFace.Stub {
    public static SehTestHal mSehTestHal;
    public SemTestHalHelper.Action currentAction;
    public List currentActionList;
    public int currentActionListPos;
    public boolean isEnrollSessionOpen;
    public boolean isTASessionOpen;
    public ISehBiometricsFaceClientCallback mCallback;
    public final Context mContext;
    public int mFaceId;
    public boolean mGlassesOn = false;

    /* renamed from: mH */
    public Handler f1655mH;
    public final int mSensorId;
    public SemTestHalHelper mTestHalHelper;
    public ServiceThread mThread;
    public int mUserId;

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehConfigurePreview(int i, ArrayList arrayList) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehFinishTaInstallation(ArrayList arrayList) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public String sehGetEngineVersion() {
        return "";
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public void sehGetFaceTag(int i, ISehBiometricsFace.sehGetFaceTagCallback sehgetfacetagcallback) {
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public void sehGetFaceTagList(ISehBiometricsFace.sehGetFaceTagListCallback sehgetfacetaglistcallback) {
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehGetServicePid() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public String sehGetTaInfo() {
        return "";
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehInstallTaDataChunk(HidlMemory hidlMemory) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public boolean sehIsTaSessionClosed() {
        return true;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehPauseEnrollment() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehPrepareTaInstallation() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehSetFaceTag(int i, ArrayList arrayList) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehSetRotation(int i) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehSetSecurityLevel(int i) {
        return 0;
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
        ServiceThread serviceThread = new ServiceThread("face.hidl.SehTestHal", -2, true);
        this.mThread = serviceThread;
        serviceThread.start();
        this.f1655mH = new Handler(this.mThread.getLooper()) { // from class: com.android.server.biometrics.sensors.face.hidl.SehTestHal.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 100) {
                    SehTestHal.this.doAction();
                    return;
                }
                Slog.w("face.hidl.SehTestHal", "Unknown message:" + message.what);
            }
        };
        InitTPA(i);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public OptionalUint64 setCallback(IBiometricsFaceClientCallback iBiometricsFaceClientCallback) {
        this.mCallback = (ISehBiometricsFaceClientCallback) iBiometricsFaceClientCallback;
        new OptionalUint64().status = 0;
        return new OptionalUint64();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int setActiveUser(int i, String str) {
        Slog.w("face.hidl.SehTestHal", "setActiveUser");
        this.mUserId = i;
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public synchronized OptionalUint64 generateChallenge(int i) {
        OptionalUint64 optionalUint64;
        Slog.w("face.hidl.SehTestHal", "generateChallenge");
        optionalUint64 = new OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        this.isEnrollSessionOpen = true;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int enroll(ArrayList arrayList, int i, ArrayList arrayList2) {
        Slog.w("face.hidl.SehTestHal", "enroll");
        if (this.isEnrollSessionOpen) {
            this.mFaceId = getEmptyFaceIdForEnroll();
            List enrollActionList = this.mTestHalHelper.getEnrollActionList();
            this.currentActionList = enrollActionList;
            this.currentActionListPos = 0;
            this.mGlassesOn = false;
            SemTestHalHelper.Action action = (SemTestHalHelper.Action) enrollActionList.get(0);
            this.currentAction = action;
            this.f1655mH.sendEmptyMessageDelayed(100, action.getDelay());
            Slog.d("face.hidl.SehTestHal", "start enroll: " + this.mFaceId + ", action size = " + this.currentActionList.size());
        } else {
            Slog.e("face.hidl.SehTestHal", "enroll : generateChallenge not done");
        }
        return 0;
    }

    public final int getEmptyFaceIdForEnroll() {
        List biometricsForUser = FaceUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, this.mUserId);
        boolean z = false;
        int i = 1;
        while (!z) {
            Iterator it = biometricsForUser.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                if (i == ((Face) it.next()).getBiometricId()) {
                    i++;
                    z = false;
                    break;
                }
            }
        }
        return i;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public synchronized int revokeChallenge() {
        Slog.w("face.hidl.SehTestHal", "revokeChallenge");
        this.isEnrollSessionOpen = false;
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int setFeature(int i, boolean z, ArrayList arrayList, int i2) {
        Slog.w("face.hidl.SehTestHal", "setFeature");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public OptionalBool getFeature(int i, int i2) {
        OptionalBool optionalBool = new OptionalBool();
        optionalBool.status = 0;
        optionalBool.value = true;
        return optionalBool;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public OptionalUint64 getAuthenticatorId() {
        OptionalUint64 optionalUint64 = new OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int cancel() {
        Slog.w("face.hidl.SehTestHal", "cancel");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        iSehBiometricsFaceClientCallback.onError(0L, this.mUserId, 5, 0);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int enumerate() {
        Slog.w("face.hidl.SehTestHal", "enumerate");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        iSehBiometricsFaceClientCallback.onEnumerate(0L, new ArrayList(), this.mUserId);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int remove(int i) {
        Slog.w("face.hidl.SehTestHal", "remove");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        if (i == 0) {
            List biometricsForUser = FaceUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, this.mUserId);
            ArrayList arrayList = new ArrayList();
            Iterator it = biometricsForUser.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((Face) it.next()).getBiometricId()));
            }
            this.mCallback.onRemoved(0L, arrayList, this.mUserId);
            return 0;
        }
        iSehBiometricsFaceClientCallback.onRemoved(0L, new ArrayList(Collections.singletonList(Integer.valueOf(i))), this.mUserId);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int authenticate(long j) {
        Slog.w("face.hidl.SehTestHal", "authenticate");
        try {
            sehAuthenticate(j, 0, null);
        } catch (Exception unused) {
        }
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int userActivity() {
        Slog.w("face.hidl.SehTestHal", "userActivity");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int resetLockout(ArrayList arrayList) {
        Slog.w("face.hidl.SehTestHal", "resetLockout");
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public void sehGetSecurityLevel(ISehBiometricsFace.sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
        sehgetsecuritylevelcallback.onValues(0, 2);
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public OptionalUint64 sehSetCallback(ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
        this.mCallback = iSehBiometricsFaceClientCallback;
        new OptionalUint64().status = 0;
        return new OptionalUint64();
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehAuthenticate(long j, int i, ArrayList arrayList) {
        Slog.w("face.hidl.SehTestHal", "sehAuthenticate");
        List authActionList = this.mTestHalHelper.getAuthActionList();
        this.currentActionList = authActionList;
        this.currentActionListPos = 0;
        SemTestHalHelper.Action action = (SemTestHalHelper.Action) authActionList.get(0);
        this.currentAction = action;
        this.f1655mH.sendEmptyMessageDelayed(100, action.getDelay());
        Slog.d("face.hidl.SehTestHal", "start authenticate: " + this.mFaceId + ", action size = " + this.currentActionList.size());
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public synchronized int sehOpenTaSession() {
        this.isTASessionOpen = true;
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public synchronized int sehCloseTaSession() {
        this.isTASessionOpen = false;
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public int sehResumeEnrollment() {
        this.f1655mH.sendEmptyMessageDelayed(100, 0L);
        return 0;
    }

    public void postUpdateAction() {
        this.f1655mH.post(new SehTestHal$$ExternalSyntheticLambda0(this));
    }

    public final void initActions() {
        SemTestHalHelper semTestHalHelper = this.mTestHalHelper;
        if (semTestHalHelper != null) {
            semTestHalHelper.initActions();
        }
    }

    public final void InitTPA(int i) {
        this.mTestHalHelper = new SemTestHalHelper(8, new SemTestHalHelper.Callback() { // from class: com.android.server.biometrics.sensors.face.hidl.SehTestHal.2
            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverAcquiredEvent(int i2, int i3) {
                try {
                    SehTestHal.this.mCallback.onAcquired(0L, SehTestHal.this.mUserId, i2, i3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverErrorEvent(int i2, int i3) {
                try {
                    SehTestHal.this.mCallback.onError(0L, SehTestHal.this.mUserId, i2, i3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverEnrollResult(int i2) {
                try {
                    SehTestHal.this.mCallback.onEnrollResult(0L, SehTestHal.this.mFaceId, SehTestHal.this.mUserId, i2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
            public void deliverAuthenticationResult(int i2) {
                try {
                    SehTestHal.this.mCallback.onAuthenticated(0L, i2, SehTestHal.this.mUserId, new ArrayList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.f1655mH.post(new SehTestHal$$ExternalSyntheticLambda0(this));
        if (Utils.DEBUG) {
            Slog.d("face.hidl.SehTestHal", "SehTestHal: constructed, " + i);
        }
    }

    public final void doAction() {
        SemTestHalHelper.Action action = this.currentAction;
        if (action == null) {
            Slog.d("face.hidl.SehTestHal", "doAction : currentAction is null");
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
            this.f1655mH.sendEmptyMessageDelayed(100, action2.getDelay());
        }
    }
}
