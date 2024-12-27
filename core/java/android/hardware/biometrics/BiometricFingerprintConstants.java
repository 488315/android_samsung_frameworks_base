package android.hardware.biometrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface BiometricFingerprintConstants {
    public static final int BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL = 14;
    public static final int BIOMETRIC_ERROR_POWER_PRESSED = 19;
    public static final int BIOMETRIC_ERROR_RE_ENROLL = 16;
    public static final int BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED = 15;
    public static final int FINGERPRINT_ACQUIRED_GOOD = 0;
    public static final int FINGERPRINT_ACQUIRED_IMAGER_DIRTY = 3;
    public static final int FINGERPRINT_ACQUIRED_IMMOBILE = 9;
    public static final int FINGERPRINT_ACQUIRED_INSUFFICIENT = 2;
    public static final int FINGERPRINT_ACQUIRED_PARTIAL = 1;
    public static final int FINGERPRINT_ACQUIRED_POWER_PRESSED = 11;
    public static final int FINGERPRINT_ACQUIRED_RE_ENROLL_FORCED = 13;
    public static final int FINGERPRINT_ACQUIRED_RE_ENROLL_OPTIONAL = 12;
    public static final int FINGERPRINT_ACQUIRED_START = 7;
    public static final int FINGERPRINT_ACQUIRED_TOO_BRIGHT = 10;
    public static final int FINGERPRINT_ACQUIRED_TOO_FAST = 5;
    public static final int FINGERPRINT_ACQUIRED_TOO_SLOW = 4;
    public static final int FINGERPRINT_ACQUIRED_UNKNOWN = 8;
    public static final int FINGERPRINT_ACQUIRED_VENDOR = 6;
    public static final int FINGERPRINT_ACQUIRED_VENDOR_BASE = 1000;
    public static final int FINGERPRINT_ERROR_BAD_CALIBRATION = 18;
    public static final int FINGERPRINT_ERROR_CANCELED = 5;
    public static final int FINGERPRINT_ERROR_HW_NOT_PRESENT = 12;
    public static final int FINGERPRINT_ERROR_HW_UNAVAILABLE = 1;
    public static final int FINGERPRINT_ERROR_LOCKOUT = 7;
    public static final int FINGERPRINT_ERROR_LOCKOUT_PERMANENT = 9;
    public static final int FINGERPRINT_ERROR_NEGATIVE_BUTTON = 13;
    public static final int FINGERPRINT_ERROR_NO_FINGERPRINTS = 11;
    public static final int FINGERPRINT_ERROR_NO_SPACE = 4;
    public static final int FINGERPRINT_ERROR_TIMEOUT = 3;
    public static final int FINGERPRINT_ERROR_UNABLE_TO_PROCESS = 2;
    public static final int FINGERPRINT_ERROR_UNABLE_TO_REMOVE = 6;
    public static final int FINGERPRINT_ERROR_UNKNOWN = 17;
    public static final int FINGERPRINT_ERROR_USER_CANCELED = 10;
    public static final int FINGERPRINT_ERROR_VENDOR = 8;
    public static final int FINGERPRINT_ERROR_VENDOR_BASE = 1000;
    public static final int SEM_FEATURE_NAVIGATION = 1;
    public static final int SEM_FEATURE_SWIPE_ENROLL = 2;
    public static final int SEM_FEATURE_WOF_DEFAULT_OFF = 3;
    public static final int SEM_FINGERPRINT_ACQUIRED_DUPLICATED_IMAGE = 1002;
    public static final int SEM_FINGERPRINT_ACQUIRED_LIGHT_TOUCH = 1003;
    public static final int SEM_FINGERPRINT_ACQUIRED_TSP_BLOCK = 1004;
    public static final int SEM_FINGERPRINT_ACQUIRED_TSP_UNBLOCK = 1005;
    public static final int SEM_FINGERPRINT_ACQUIRED_UNINTENDED_TOUCH = 1006;
    public static final int SEM_FINGERPRINT_ACQUIRED_WET_FINGER = 1001;
    public static final int SEM_FINGERPRINT_ERROR_CALIBRATION = 1001;
    public static final int SEM_FINGERPRINT_ERROR_DISABLED_BIOMETRICS = 5002;
    public static final int SEM_FINGERPRINT_ERROR_INVALID_HW = 1005;
    public static final int SEM_FINGERPRINT_ERROR_NEED_TO_RETRY = 5000;
    public static final int SEM_FINGERPRINT_ERROR_ONE_HAND_MODE = 5001;
    public static final int SEM_FINGERPRINT_ERROR_PATTERN_DETECTED = 1007;
    public static final int SEM_FINGERPRINT_ERROR_SERVICE_FAILURE = 1003;
    public static final int SEM_FINGERPRINT_ERROR_SMART_VIEW = 5003;
    public static final int SEM_FINGERPRINT_ERROR_SYSTEM_FAILURE = 1002;
    public static final int SEM_FINGERPRINT_ERROR_TA_UPDATE = -100;
    public static final int SEM_FINGERPRINT_ERROR_TEMPLATE_CORRUPTED = 1004;
    public static final int SEM_FINGERPRINT_ERROR_TEMPLATE_FORMAT_CHANGED = 1006;
    public static final int SEM_FINGERPRINT_ERROR_WIRELESS_CHARGING = 5004;
    public static final int SEM_FINGERPRINT_EVENT_AUTHENTICATION_FAILED_REASON_END = 49999;
    public static final int SEM_FINGERPRINT_EVENT_AUTHENTICATION_FAILED_REASON_START = 40000;
    public static final int SEM_FINGERPRINT_EVENT_BASE = 10000;
    public static final int SEM_FINGERPRINT_EVENT_CAPTURE_COMPLETED = 10003;
    public static final int SEM_FINGERPRINT_EVENT_CAPTURE_FAILED = 10006;
    public static final int SEM_FINGERPRINT_EVENT_CAPTURE_READY = 10001;
    public static final int SEM_FINGERPRINT_EVENT_CAPTURE_STARTED = 10002;
    public static final int SEM_FINGERPRINT_EVENT_CAPTURE_SUCCESS = 10005;
    public static final int SEM_FINGERPRINT_EVENT_FACTORY_SNSR_SCRIPT_END = 10009;
    public static final int SEM_FINGERPRINT_EVENT_FACTORY_SNSR_SCRIPT_START = 10008;
    public static final int SEM_FINGERPRINT_EVENT_FINGER_LEAVE = 10004;
    public static final int SEM_FINGERPRINT_EVENT_FINGER_LEAVE_TIMEOUT = 10007;
    public static final int SEM_FINGERPRINT_EVENT_GESTURE_DTAP = 20003;
    public static final int SEM_FINGERPRINT_EVENT_GESTURE_LPRESS = 20004;
    public static final int SEM_FINGERPRINT_EVENT_GESTURE_SWIPE_DOWN = 20002;
    public static final int SEM_FINGERPRINT_EVENT_GESTURE_SWIPE_UP = 20001;
    public static final int SEM_FINGERPRINT_EVENT_INTERRUPT_CATCH = 10011;
    public static final int SEM_FINGERPRINT_EVENT_POINTER_UP = 70001;
    public static final int SEM_FINGERPRINT_EVENT_SPEN_CONTROL_OFF = 30002;
    public static final int SEM_FINGERPRINT_EVENT_SPEN_CONTROL_ON = 30001;
    public static final int SEM_FINGERPRINT_EVENT_SWIPE_AUTHENTICATION = 10012;
    public static final int SEM_FINGERPRINT_EVENT_TOUCH_ENROLLMENT = 10013;
    public static final int SEM_SENSOR_STATUS_CALIBRATION_ERROR = 100045;
    public static final int SEM_SENSOR_STATUS_ERROR = 100042;
    public static final int SEM_SENSOR_STATUS_OK = 100040;
    public static final int SEM_SENSOR_STATUS_WORKING = 100041;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintAcquired {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintFeature {
    }

    public @interface FingerprintGestureEvent {
    }

    public @interface FingerprintInternalEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintSensorStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintVendorAcquired {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintVendorError {
    }

    static boolean shouldDisableUdfpsDisplayMode(int acquiredInfo) {
        switch (acquiredInfo) {
        }
        return false;
    }

    static int reasonToMetric(int reason) {
        switch (reason) {
            case 1:
                return 3;
            case 2:
                return 2;
            case 3:
                return 1;
            default:
                return 0;
        }
    }
}
