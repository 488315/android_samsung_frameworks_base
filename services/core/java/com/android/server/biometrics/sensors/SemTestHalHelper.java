package com.android.server.biometrics.sensors;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.SemBiometricFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SemTestHalHelper {
    public final int mBiometricType;
    public final Callback mCallback;
    public long mDelayAuthAction;
    public final List mEnrollActionList = new ArrayList();
    public final List mAuthActionList = new ArrayList();

    public final class Action implements Runnable {
        public final Callback callback;
        public final CallbackType callbackType;
        public final int code;
        public long delay;
        public final int value;
        public final int vendorCode;

        public Action(CallbackType callbackType, Callback callback, int i) {
            this(callbackType, callback, 0, 0);
            this.value = i;
        }

        public Action(CallbackType callbackType, Callback callback, int i, int i2) {
            this.callbackType = callbackType;
            this.callback = callback;
            this.code = i;
            this.vendorCode = i2;
            this.delay = new Random().nextInt(1000);
        }

        @Override // java.lang.Runnable
        public final void run() {
            StringBuilder sb = new StringBuilder("Run: ");
            sb.append(
                    this.callbackType.name()
                            + ": "
                            + this.code
                            + ", "
                            + this.vendorCode
                            + ", "
                            + this.value
                            + ", delay = "
                            + this.delay);
            Slog.d("SemTestHalHelper", sb.toString());
            int ordinal = this.callbackType.ordinal();
            if (ordinal == 0) {
                this.callback.deliverAcquiredEvent(this.code, this.vendorCode);
                return;
            }
            if (ordinal == 1) {
                this.callback.deliverErrorEvent(this.code, this.vendorCode);
                return;
            }
            if (ordinal == 2) {
                this.callback.deliverEnrollResult(this.value);
                return;
            }
            if (ordinal == 3) {
                this.callback.deliverAuthenticationResult(this.value);
            } else if (ordinal == 5) {
                this.callback.getClass();
            } else {
                if (ordinal != 6) {
                    return;
                }
                this.callback.deliverTspEvent(this.value);
            }
        }
    }

    public interface Callback {
        void deliverAcquiredEvent(int i, int i2);

        void deliverAuthenticationResult(int i);

        void deliverEnrollResult(int i);

        void deliverErrorEvent(int i, int i2);

        default void deliverTspEvent(int i) {}
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class CallbackType {
        public static final /* synthetic */ CallbackType[] $VALUES;
        public static final CallbackType ACQUIRED;
        public static final CallbackType AUTHENTICATED;
        public static final CallbackType ENROLL_RESULT;
        public static final CallbackType ERROR;
        public static final CallbackType TSP_FOD;

        static {
            CallbackType callbackType = new CallbackType("ACQUIRED", 0);
            ACQUIRED = callbackType;
            CallbackType callbackType2 = new CallbackType("ERROR", 1);
            ERROR = callbackType2;
            CallbackType callbackType3 = new CallbackType("ENROLL_RESULT", 2);
            ENROLL_RESULT = callbackType3;
            CallbackType callbackType4 = new CallbackType("AUTHENTICATED", 3);
            AUTHENTICATED = callbackType4;
            CallbackType callbackType5 = new CallbackType("REMOVED", 4);
            CallbackType callbackType6 = new CallbackType("ENUMERATE", 5);
            CallbackType callbackType7 = new CallbackType("TSP_FOD", 6);
            TSP_FOD = callbackType7;
            $VALUES =
                    new CallbackType[] {
                        callbackType,
                        callbackType2,
                        callbackType3,
                        callbackType4,
                        callbackType5,
                        callbackType6,
                        callbackType7
                    };
        }

        public static CallbackType valueOf(String str) {
            return (CallbackType) Enum.valueOf(CallbackType.class, str);
        }

        public static CallbackType[] values() {
            return (CallbackType[]) $VALUES.clone();
        }
    }

    public SemTestHalHelper(int i, Callback callback) {
        this.mBiometricType = i;
        this.mCallback = callback;
    }

    public final void addFpCaptureFailedAction(int i, int i2, List list) {
        CallbackType callbackType = CallbackType.ACQUIRED;
        Callback callback = this.mCallback;
        list.add(new Action(callbackType, callback, 6, 10001));
        boolean z = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
        CallbackType callbackType2 = CallbackType.TSP_FOD;
        if (z) {
            list.add(new Action(callbackType2, callback, 2));
        }
        list.add(new Action(callbackType, callback, 6, 10002));
        list.add(new Action(callbackType, callback, 6, 10003));
        if (z) {
            list.add(new Action(callbackType2, callback, 1));
        }
        list.add(new Action(callbackType, callback, 6, FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER));
        list.add(new Action(callbackType, callback, i, i2));
        list.add(new Action(callbackType, callback, 6, 10004));
    }

    public final void addFpDefaultCaptureSuccessAction(List list) {
        CallbackType callbackType = CallbackType.ACQUIRED;
        Callback callback = this.mCallback;
        list.add(new Action(callbackType, callback, 6, 10001));
        boolean z = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
        CallbackType callbackType2 = CallbackType.TSP_FOD;
        if (z) {
            list.add(new Action(callbackType2, callback, 2));
        }
        list.add(new Action(callbackType, callback, 6, 10002));
        list.add(new Action(callbackType, callback, 6, 10003));
        if (z) {
            list.add(new Action(callbackType2, callback, 1));
        }
        list.add(new Action(callbackType, callback, 6, FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE));
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:96|97|98|99|(5:100|101|(2:103|105)|(2:106|107)|108)|109|110|111) */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0171, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0172, code lost:

       r0.printStackTrace();
       r0 = null;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x02a2 A[Catch: JSONException -> 0x021b, TryCatch #5 {JSONException -> 0x021b, blocks: (B:40:0x020e, B:41:0x0223, B:43:0x0235, B:55:0x02a2, B:57:0x02a8, B:58:0x02ae, B:62:0x0255, B:63:0x0263, B:65:0x0277, B:66:0x0283, B:67:0x0290), top: B:39:0x020e }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02b5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02ca A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r12v17, types: [int] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:139:0x013e -> B:94:0x0167). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initActions() {
        /*
            Method dump skipped, instructions count: 738
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.SemTestHalHelper.initActions():void");
    }
}
