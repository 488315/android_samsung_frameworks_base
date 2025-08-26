package android.security;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keymaster.KeymasterDefs;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class KeyStoreException extends Exception {
    public static final int ERROR_ATTESTATION_CHALLENGE_TOO_LARGE = 9;
    public static final int ERROR_ATTESTATION_KEYS_UNAVAILABLE = 16;
    public static final int ERROR_DEVICE_REQUIRES_UPGRADE_FOR_ATTESTATION = 17;
    public static final int ERROR_ID_ATTESTATION_FAILURE = 8;
    public static final int ERROR_INCORRECT_USAGE = 13;
    public static final int ERROR_INTERNAL_SYSTEM_ERROR = 4;
    public static final int ERROR_KEYMINT_FAILURE = 10;
    public static final int ERROR_KEYSTORE_FAILURE = 11;
    public static final int ERROR_KEYSTORE_UNINITIALIZED = 3;
    public static final int ERROR_KEY_CORRUPTED = 7;
    public static final int ERROR_KEY_DOES_NOT_EXIST = 6;
    public static final int ERROR_KEY_NOT_TEMPORALLY_VALID = 14;
    public static final int ERROR_KEY_OPERATION_EXPIRED = 15;
    public static final int ERROR_OTHER = 1;
    public static final int ERROR_PERMISSION_DENIED = 5;
    public static final int ERROR_UNIMPLEMENTED = 12;
    public static final int ERROR_USER_AUTHENTICATION_REQUIRED = 2;
    private static final PublicErrorInformation GENERAL_KEYMINT_ERROR;
    private static final PublicErrorInformation GENERAL_KEYSTORE_ERROR;
    private static final int IS_SYSTEM_ERROR = 2;
    private static final int IS_TRANSIENT_ERROR = 4;
    private static final PublicErrorInformation KEYMINT_INCORRECT_USAGE_ERROR;
    private static final PublicErrorInformation KEYMINT_RETRYABLE_ERROR;
    private static final PublicErrorInformation KEYMINT_TEMPORAL_VALIDITY_ERROR;
    private static final PublicErrorInformation KEYMINT_UNIMPLEMENTED_ERROR;
    private static final int REQUIRES_USER_AUTHENTICATION = 8;
    public static final int RETRY_AFTER_NEXT_REBOOT = 4;
    public static final int RETRY_NEVER = 1;
    public static final int RETRY_WHEN_CONNECTIVITY_AVAILABLE = 3;
    public static final int RETRY_WITH_EXPONENTIAL_BACKOFF = 2;
    public static final int RKP_FETCHING_PENDING_CONNECTIVITY = 3;
    public static final int RKP_FETCHING_PENDING_SOFTWARE_REBOOT = 4;
    public static final int RKP_SERVER_REFUSED_ISSUANCE = 2;
    public static final int RKP_SUCCESS = 0;
    public static final int RKP_TEMPORARILY_UNAVAILABLE = 1;
    private static final String TAG = "KeyStoreException";
    private static final Map<Integer, PublicErrorInformation> sErrorCodeToFailureInfo;
    private final int mErrorCode;
    private final int mRkpStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PublicErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RetryPolicy {
    }

    private static int initializeRkpStatusForRegularErrors(int i) {
        if (i != 22) {
            return 0;
        }
        Log.e(TAG, "RKP error code without RKP status");
        return 2;
    }

    public KeyStoreException(int i, String str) {
        super(str);
        this.mErrorCode = i;
        this.mRkpStatus = initializeRkpStatusForRegularErrors(i);
    }

    public KeyStoreException(int i, String str, String str2) {
        super(str + " (internal Keystore code: " + i + " message: " + str2 + NavigationBarInflaterView.KEY_CODE_END);
        this.mErrorCode = i;
        this.mRkpStatus = initializeRkpStatusForRegularErrors(i);
    }

    public KeyStoreException(int i, String str, int i2) {
        super(str);
        this.mErrorCode = i;
        this.mRkpStatus = i2;
        if (i != 22) {
            Log.e(TAG, "Providing RKP status for error code " + i + " has no effect.");
        }
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getNumericErrorCode() {
        return getErrorInformation(this.mErrorCode).errorCode;
    }

    public boolean isTransientFailure() {
        PublicErrorInformation errorInformation = getErrorInformation(this.mErrorCode);
        int i = this.mRkpStatus;
        return (i == 0 || this.mErrorCode != 22) ? (errorInformation.indicators & 4) != 0 : i == 1 || i == 3 || i == 4;
    }

    public boolean requiresUserAuthentication() {
        return (getErrorInformation(this.mErrorCode).indicators & 8) != 0;
    }

    public boolean isSystemError() {
        return (getErrorInformation(this.mErrorCode).indicators & 2) != 0;
    }

    public int getRetryPolicy() {
        PublicErrorInformation errorInformation = getErrorInformation(this.mErrorCode);
        int i = this.mRkpStatus;
        if (i == 0) {
            int i2 = this.mErrorCode;
            if (i2 == 23) {
                return 4;
            }
            if (i2 != 24) {
                return (errorInformation.indicators & 4) != 0 ? 2 : 1;
            }
            return 3;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 3;
        }
        if (i != 4) {
            return (errorInformation.indicators & 4) != 0 ? 2 : 1;
        }
        return 4;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return super.toString() + String.format(" (public error code: %d internal Keystore code: %d)", Integer.valueOf(getNumericErrorCode()), Integer.valueOf(this.mErrorCode));
    }

    private static PublicErrorInformation getErrorInformation(int i) {
        PublicErrorInformation publicErrorInformation = sErrorCodeToFailureInfo.get(Integer.valueOf(i));
        if (publicErrorInformation != null) {
            return publicErrorInformation;
        }
        if (i > 0) {
            return GENERAL_KEYSTORE_ERROR;
        }
        return GENERAL_KEYMINT_ERROR;
    }

    private static final class PublicErrorInformation {
        public final int errorCode;
        public final int indicators;

        PublicErrorInformation(int i, int i2) {
            this.indicators = i;
            this.errorCode = i2;
        }
    }

    static {
        PublicErrorInformation publicErrorInformation = new PublicErrorInformation(0, 10);
        GENERAL_KEYMINT_ERROR = publicErrorInformation;
        GENERAL_KEYSTORE_ERROR = new PublicErrorInformation(0, 11);
        PublicErrorInformation publicErrorInformation2 = new PublicErrorInformation(2, 12);
        KEYMINT_UNIMPLEMENTED_ERROR = publicErrorInformation2;
        PublicErrorInformation publicErrorInformation3 = new PublicErrorInformation(6, 10);
        KEYMINT_RETRYABLE_ERROR = publicErrorInformation3;
        PublicErrorInformation publicErrorInformation4 = new PublicErrorInformation(0, 13);
        KEYMINT_INCORRECT_USAGE_ERROR = publicErrorInformation4;
        PublicErrorInformation publicErrorInformation5 = new PublicErrorInformation(0, 14);
        KEYMINT_TEMPORAL_VALIDITY_ERROR = publicErrorInformation5;
        HashMap map = new HashMap();
        sErrorCodeToFailureInfo = map;
        map.put(0, publicErrorInformation);
        map.put(-1, publicErrorInformation);
        map.put(-2, publicErrorInformation4);
        map.put(-3, publicErrorInformation4);
        map.put(-4, publicErrorInformation2);
        map.put(-5, publicErrorInformation4);
        map.put(-6, publicErrorInformation2);
        map.put(-7, publicErrorInformation2);
        map.put(-8, publicErrorInformation4);
        map.put(-9, publicErrorInformation2);
        map.put(-10, publicErrorInformation4);
        map.put(-11, publicErrorInformation4);
        map.put(-12, publicErrorInformation2);
        map.put(-13, publicErrorInformation4);
        map.put(-14, publicErrorInformation4);
        map.put(-15, publicErrorInformation);
        map.put(-16, publicErrorInformation4);
        map.put(-17, publicErrorInformation4);
        map.put(-18, publicErrorInformation4);
        map.put(-19, publicErrorInformation2);
        map.put(-20, publicErrorInformation2);
        map.put(-21, publicErrorInformation4);
        map.put(-22, publicErrorInformation4);
        map.put(-23, publicErrorInformation);
        map.put(-24, publicErrorInformation5);
        map.put(-25, publicErrorInformation5);
        map.put(-26, new PublicErrorInformation(8, 2));
        map.put(-27, publicErrorInformation);
        map.put(-28, new PublicErrorInformation(6, 15));
        map.put(-29, publicErrorInformation);
        map.put(-30, publicErrorInformation);
        map.put(-31, publicErrorInformation);
        map.put(-32, publicErrorInformation);
        map.put(-33, publicErrorInformation);
        map.put(-34, publicErrorInformation4);
        map.put(-35, publicErrorInformation4);
        map.put(-36, publicErrorInformation4);
        map.put(-37, publicErrorInformation4);
        map.put(-38, publicErrorInformation);
        map.put(-39, publicErrorInformation2);
        map.put(-40, publicErrorInformation4);
        map.put(-41, publicErrorInformation);
        map.put(-44, publicErrorInformation);
        map.put(-45, publicErrorInformation);
        map.put(-46, publicErrorInformation);
        map.put(-47, publicErrorInformation);
        map.put(-48, publicErrorInformation3);
        map.put(-49, publicErrorInformation3);
        map.put(-50, publicErrorInformation2);
        map.put(-51, publicErrorInformation4);
        map.put(-52, publicErrorInformation4);
        map.put(-53, publicErrorInformation4);
        map.put(-54, publicErrorInformation3);
        map.put(-55, publicErrorInformation);
        map.put(-56, publicErrorInformation);
        map.put(-57, publicErrorInformation4);
        map.put(-58, publicErrorInformation4);
        map.put(-59, publicErrorInformation4);
        map.put(-60, publicErrorInformation2);
        map.put(-61, publicErrorInformation2);
        map.put(-63, publicErrorInformation4);
        map.put(-64, new PublicErrorInformation(2, 10));
        map.put(-65, publicErrorInformation3);
        map.put(-66, new PublicErrorInformation(2, 8));
        map.put(-67, publicErrorInformation2);
        map.put(-68, publicErrorInformation2);
        map.put(-69, publicErrorInformation4);
        map.put(-70, publicErrorInformation4);
        map.put(-71, publicErrorInformation4);
        map.put(-72, new PublicErrorInformation(10, 2));
        map.put(-73, publicErrorInformation);
        map.put(-74, publicErrorInformation);
        map.put(-75, publicErrorInformation);
        map.put(-76, publicErrorInformation);
        map.put(-77, publicErrorInformation2);
        map.put(-78, publicErrorInformation4);
        map.put(-79, publicErrorInformation2);
        map.put(-80, publicErrorInformation4);
        map.put(-81, publicErrorInformation4);
        map.put(-82, publicErrorInformation4);
        map.put(-83, publicErrorInformation4);
        map.put(-84, publicErrorInformation4);
        map.put(-85, publicErrorInformation);
        map.put(-100, publicErrorInformation2);
        map.put(-1000, new PublicErrorInformation(2, 10));
        map.put(-101, publicErrorInformation);
        map.put(Integer.valueOf(KeymasterDefs.KM_ERROR_SAK_NOT_EXIST), publicErrorInformation2);
        map.put(Integer.valueOf(KeymasterDefs.KM_ERROR_GAK_NOT_EXIST), publicErrorInformation2);
        map.put(2, new PublicErrorInformation(8, 2));
        map.put(3, new PublicErrorInformation(2, 3));
        map.put(4, new PublicErrorInformation(2, 4));
        map.put(6, new PublicErrorInformation(0, 5));
        map.put(7, new PublicErrorInformation(0, 6));
        map.put(8, new PublicErrorInformation(0, 7));
        map.put(17, new PublicErrorInformation(0, 6));
        map.put(22, new PublicErrorInformation(2, 16));
        map.put(23, new PublicErrorInformation(6, 17));
        map.put(27, new PublicErrorInformation(6, 4));
        map.put(24, new PublicErrorInformation(6, 16));
        map.put(25, new PublicErrorInformation(6, 16));
        map.put(26, new PublicErrorInformation(2, 16));
    }

    public static boolean hasFailureInfoForError(int i) {
        return sErrorCodeToFailureInfo.containsKey(Integer.valueOf(i));
    }
}
