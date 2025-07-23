package com.samsung.android.knox.ex.knoxAI;

import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KnoxAiManager {
    public static final String TAG = "KnoxAiManager";
    public static KnoxAiManager sKnoxAiManager;
    public KnoxAiManagerInternal mKnoxAiManagerInternal;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum ErrorCodes {
        KNOX_AI_UNKNOWN_ERROR(-1),
        SUCCESS(0),
        DEVICE_NOT_PROVISIONED(1),
        DEVICE_PROVISION_FAILED(2),
        KNOX_AI_SERVICE_FAILURE(3),
        KNOX_AI_INTERNAL_ERROR(4),
        KNOX_AI_UNSUPPORTED_OP(5),
        KNOX_AI_MODEL_POLICY_VIOLATION(6),
        KNOX_AI_MODEL_PACKAGE_ERROR(7),
        KNOX_AI_UNSUPPORTED_COMPUTEUNIT(8),
        KNOX_AI_UNSUPPORTED_MODEL_TYPE(9),
        KNOX_AI_INVALID_ARGUMENTS(10),
        KNOX_AI_MODEL_KEY_REVOKED(11),
        KNOX_AI_INCOMPATIBLE_DEVICE_MODEL(12);

        public static Map<Integer, ErrorCodes> valueMap = new HashMap();
        private final int value;

        static {
            for (ErrorCodes errorCodes : values()) {
                valueMap.put(Integer.valueOf(errorCodes.value), errorCodes);
            }
        }

        ErrorCodes(int i) {
            this.value = i;
        }

        public static ErrorCodes getCodeFromValue(int i) {
            return valueMap.get(Integer.valueOf(i));
        }

        public int getValue() {
            return this.value;
        }
    }

    private KnoxAiManager(Context context) {
        this.mKnoxAiManagerInternal = null;
        Log.d(TAG, "KnoxAiManager Constructor called: " + context.toString());
        this.mKnoxAiManagerInternal = KnoxAiManagerInternal.getInstance(context);
    }

    public static synchronized KnoxAiManager getInstance(Context context) {
        KnoxAiManager knoxAiManager;
        synchronized (KnoxAiManager.class) {
            try {
                if (sKnoxAiManager == null) {
                    sKnoxAiManager = new KnoxAiManager(context);
                }
                knoxAiManager = sKnoxAiManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxAiManager;
    }

    public KnoxAiSession createKnoxAiSession() {
        String str = TAG;
        Log.d(str, "createKnoxAiSession entry");
        long createKnoxAiSession = this.mKnoxAiManagerInternal.createKnoxAiSession();
        if (createKnoxAiSession >= 100) {
            return new KnoxAiSession(this.mKnoxAiManagerInternal, createKnoxAiSession);
        }
        Log.e(str, "createKnoxAiSession failed : " + createKnoxAiSession);
        return null;
    }

    public ErrorCodes destroyKnoxAiSession(KnoxAiSession knoxAiSession) {
        return this.mKnoxAiManagerInternal.destroyKnoxAiSession(knoxAiSession.mSessionID);
    }

    public void getKeyProvisioning(KeyProvisioningResultCallback keyProvisioningResultCallback) {
        this.mKnoxAiManagerInternal.getKeyProvisioning(keyProvisioningResultCallback);
    }
}
