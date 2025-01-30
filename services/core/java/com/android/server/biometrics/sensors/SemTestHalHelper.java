package com.android.server.biometrics.sensors;

import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class SemTestHalHelper {
    public final int mBiometricType;
    public final Callback mCallback;
    public long mDelayAuthAction;
    public final List mEnrollActionList = new ArrayList();
    public final List mAuthActionList = new ArrayList();

    public interface Callback {
        void deliverAcquiredEvent(int i, int i2);

        void deliverAuthenticationResult(int i);

        void deliverEnrollResult(int i);

        default void deliverEnumerate(int i, int i2, int i3) {
        }

        void deliverErrorEvent(int i, int i2);

        default void deliverTspEvent(int i) {
        }
    }

    public enum CallbackType {
        ACQUIRED,
        ERROR,
        ENROLL_RESULT,
        AUTHENTICATED,
        REMOVED,
        ENUMERATE,
        TSP_FOD
    }

    public class Action implements Runnable {
        public int biometricId;
        public Callback callback;
        public CallbackType callbackType;
        public int code;
        public long delay;
        public int groupId;
        public int value;
        public int vendorCode;

        public Action(CallbackType callbackType, Callback callback, int i, int i2) {
            this.callbackType = callbackType;
            this.callback = callback;
            this.code = i;
            this.vendorCode = i2;
            this.delay = new Random().nextInt(1000);
        }

        public Action(CallbackType callbackType, Callback callback, int i) {
            this(callbackType, callback, 0, 0);
            this.value = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            Slog.d("SemTestHalHelper", "Run: " + toDebugString());
            switch (AbstractC08431.f1653x648fe824[this.callbackType.ordinal()]) {
                case 1:
                    this.callback.deliverAcquiredEvent(this.code, this.vendorCode);
                    break;
                case 2:
                    this.callback.deliverErrorEvent(this.code, this.vendorCode);
                    break;
                case 3:
                    this.callback.deliverEnrollResult(this.value);
                    break;
                case 4:
                    this.callback.deliverAuthenticationResult(this.value);
                    break;
                case 5:
                    this.callback.deliverTspEvent(this.value);
                    break;
                case 6:
                    this.callback.deliverEnumerate(this.biometricId, this.groupId, this.value);
                    break;
            }
        }

        public boolean isFinishEnroll() {
            return this.callbackType == CallbackType.ENROLL_RESULT && this.value == 0;
        }

        public Action setDelay(long j) {
            this.delay = j;
            return this;
        }

        public long getDelay() {
            return this.delay;
        }

        public CallbackType getCallbackType() {
            return this.callbackType;
        }

        public int getCode() {
            return this.code;
        }

        public int getVendorCode() {
            return this.vendorCode;
        }

        public int getValue() {
            return this.value;
        }

        public String toDebugString() {
            return this.callbackType.name() + ": " + this.code + ", " + this.vendorCode + ", " + this.value + ", delay = " + this.delay;
        }
    }

    /* renamed from: com.android.server.biometrics.sensors.SemTestHalHelper$1 */
    public abstract /* synthetic */ class AbstractC08431 {

        /* renamed from: $SwitchMap$com$android$server$biometrics$sensors$SemTestHalHelper$CallbackType */
        public static final /* synthetic */ int[] f1653x648fe824;

        static {
            int[] iArr = new int[CallbackType.values().length];
            f1653x648fe824 = iArr;
            try {
                iArr[CallbackType.ACQUIRED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1653x648fe824[CallbackType.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1653x648fe824[CallbackType.ENROLL_RESULT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1653x648fe824[CallbackType.AUTHENTICATED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1653x648fe824[CallbackType.TSP_FOD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1653x648fe824[CallbackType.ENUMERATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public SemTestHalHelper(int i, Callback callback) {
        this.mBiometricType = i;
        this.mCallback = callback;
    }

    public void initActions() {
        this.mAuthActionList.clear();
        this.mEnrollActionList.clear();
        initDefaultAction();
        JSONObject jSONObject = Utils.getJSONObject(getActionPath());
        if (jSONObject != null) {
            makeActionFromJSONObject(jSONObject);
        }
    }

    public List getEnrollActionList() {
        return this.mEnrollActionList;
    }

    public List getAuthActionList() {
        return this.mAuthActionList;
    }

    public long getDelayAuthAction() {
        return this.mDelayAuthAction;
    }

    public final void initDefaultAction() {
        addDefaultEnrollAction();
        addDefaultAuthenticateAction();
    }

    public final void addDefaultEnrollAction() {
        this.mEnrollActionList.clear();
        if (this.mBiometricType == 2) {
            for (int i = 1; i <= 5; i++) {
                addFpDefaultCaptureSuccessAction(this.mEnrollActionList);
                this.mEnrollActionList.add(new Action(CallbackType.ENROLL_RESULT, this.mCallback, 100 - (i * 20)));
                addFingerLeaveAction(this.mEnrollActionList);
            }
        }
        if (this.mBiometricType == 8) {
            addFaceDefaultEnrollSuccessAction(this.mEnrollActionList);
        }
    }

    public final void addDefaultAuthenticateAction() {
        this.mAuthActionList.clear();
        if (this.mBiometricType == 2) {
            addFpAuthenticateAction(this.mAuthActionList, false);
            addFpCaptureFailedAction(this.mAuthActionList, 1, 0);
            addFpCaptureFailedAction(this.mAuthActionList, 6, 1003);
            addFpAuthenticateAction(this.mAuthActionList, true);
        }
        if (this.mBiometricType == 8) {
            addFaceDefaultAuthSuccessAction(this.mAuthActionList);
        }
    }

    public final void addFpDefaultCaptureSuccessAction(List list) {
        CallbackType callbackType = CallbackType.ACQUIRED;
        list.add(new Action(callbackType, this.mCallback, 6, 10001));
        boolean z = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
        if (z) {
            list.add(new Action(CallbackType.TSP_FOD, this.mCallback, 2));
        }
        list.add(new Action(callbackType, this.mCallback, 6, 10002));
        list.add(new Action(callbackType, this.mCallback, 6, 10003));
        if (z) {
            list.add(new Action(CallbackType.TSP_FOD, this.mCallback, 1));
        }
        list.add(new Action(callbackType, this.mCallback, 6, FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE));
    }

    public final void addFpCaptureFailedAction(List list, int i, int i2) {
        CallbackType callbackType = CallbackType.ACQUIRED;
        list.add(new Action(callbackType, this.mCallback, 6, 10001));
        boolean z = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
        if (z) {
            list.add(new Action(CallbackType.TSP_FOD, this.mCallback, 2));
        }
        list.add(new Action(callbackType, this.mCallback, 6, 10002));
        list.add(new Action(callbackType, this.mCallback, 6, 10003));
        if (z) {
            list.add(new Action(CallbackType.TSP_FOD, this.mCallback, 1));
        }
        list.add(new Action(callbackType, this.mCallback, 6, FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER));
        list.add(new Action(callbackType, this.mCallback, i, i2));
        list.add(new Action(callbackType, this.mCallback, 6, 10004));
    }

    public final void addFingerLeaveAction(List list) {
        list.add(new Action(CallbackType.ACQUIRED, this.mCallback, 6, 10004));
    }

    public final void addFpAuthenticateAction(List list, boolean z) {
        addFpDefaultCaptureSuccessAction(list);
        list.add(new Action(CallbackType.AUTHENTICATED, this.mCallback, z ? 1 : 0));
        addFingerLeaveAction(list);
    }

    public final String getActionPath() {
        int i = this.mBiometricType;
        return i == 2 ? "/data/.biometric/fingerprint/tpa.json" : i == 8 ? "/data/.biometric/face/tpa.json" : "";
    }

    public final void addFaceDefaultEnrollSuccessAction(List list) {
        CallbackType callbackType = CallbackType.ENROLL_RESULT;
        list.add(new Action(callbackType, this.mCallback, 70).setDelay(1000L));
        list.add(new Action(callbackType, this.mCallback, 50).setDelay(1000L));
        list.add(new Action(callbackType, this.mCallback, 30).setDelay(1000L));
        list.add(new Action(callbackType, this.mCallback, 0).setDelay(1000L));
    }

    public final void addFaceDefaultAuthSuccessAction(List list) {
        list.add(new Action(CallbackType.ACQUIRED, this.mCallback, 1, 0).setDelay(1000L));
        list.add(new Action(CallbackType.AUTHENTICATED, this.mCallback, 1).setDelay(1000L));
    }

    public final void makeActionFromJSONObject(JSONObject jSONObject) {
        Action action;
        ArrayList<Pair> arrayList = new ArrayList();
        arrayList.add(new Pair("enroll", this.mEnrollActionList));
        arrayList.add(new Pair("authenticate", this.mAuthActionList));
        for (Pair pair : arrayList) {
            Slog.d("SemTestHalHelper", "makeActionFromJSONObject: parse key = " + ((String) pair.first));
            try {
                if (!jSONObject.has((String) pair.first)) {
                    Slog.d("SemTestHalHelper", "makeActionFromJSONObject: No Key, use default");
                } else {
                    JSONObject jSONObject2 = jSONObject.getJSONObject((String) pair.first);
                    if (!jSONObject2.has("actionList")) {
                        Slog.d("SemTestHalHelper", "makeActionFromJSONObject: No actionList, use default");
                    } else {
                        if (jSONObject2.has("delay")) {
                            this.mDelayAuthAction = jSONObject2.getLong("delay");
                            Slog.d("SemTestHalHelper", "makeActionFromJSONObject: key = " + ((String) pair.first) + ", delay = " + this.mDelayAuthAction);
                        }
                        JSONArray jSONArray = jSONObject2.getJSONArray("actionList");
                        int length = jSONArray.length();
                        ((List) pair.second).clear();
                        for (int i = 0; i < length; i++) {
                            JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                            int i2 = jSONObject3.getInt("type");
                            if (i2 == 1) {
                                action = new Action(CallbackType.ACQUIRED, this.mCallback, jSONObject3.getInt("acquiredInfo"), jSONObject3.getInt("vendorCode"));
                            } else if (i2 == 2) {
                                action = new Action(CallbackType.ENROLL_RESULT, this.mCallback, jSONObject3.getInt("remaining"));
                            } else if (i2 == 3) {
                                action = new Action(CallbackType.AUTHENTICATED, this.mCallback, jSONObject3.getInt("Id"));
                            } else if (i2 == 4) {
                                action = new Action(CallbackType.ERROR, this.mCallback, jSONObject3.getInt("errorCode"), jSONObject3.getInt("vendorCode"));
                            } else {
                                action = i2 != 7 ? null : new Action(CallbackType.TSP_FOD, this.mCallback, jSONObject3.getInt("TspEvent"));
                            }
                            if (action != null) {
                                if (jSONObject3.has("delay")) {
                                    action.setDelay(jSONObject3.getLong("delay"));
                                }
                                ((List) pair.second).add(action);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
