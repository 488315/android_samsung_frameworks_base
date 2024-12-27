package android.hardware.biometrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class BiometricFaceConstants {
    public static final int BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL = 14;
    public static final int BIOMETRIC_ERROR_POWER_PRESSED = 19;
    public static final int BIOMETRIC_ERROR_RE_ENROLL = 16;
    public static final int BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED = 15;
    public static final int FACE_ACQUIRED_BACKLIGHT = 1022;
    public static final int FACE_ACQUIRED_DARK_GLASSES_DETECTED = 25;
    public static final int FACE_ACQUIRED_EARLY_STOP = 1018;
    public static final int FACE_ACQUIRED_FABK = 100005;
    public static final int FACE_ACQUIRED_FACE_DOWN_FAIL = 1031;
    public static final int FACE_ACQUIRED_FACE_FRONT_FAIL = 1029;
    public static final int FACE_ACQUIRED_FACE_LEFT_FAIL = 1032;
    public static final int FACE_ACQUIRED_FACE_OBSCURED = 19;
    public static final int FACE_ACQUIRED_FACE_RIGHT_FAIL = 1033;
    public static final int FACE_ACQUIRED_FACE_UP_FAIL = 1030;
    public static final int FACE_ACQUIRED_FAKE_FACE = 1005;
    public static final int FACE_ACQUIRED_FALI_FATO = 100002;
    public static final int FACE_ACQUIRED_FALQ_FMLQ = 100003;
    public static final int FACE_ACQUIRED_FAMK = 100001;
    public static final int FACE_ACQUIRED_FAMO = 100006;
    public static final int FACE_ACQUIRED_FANM_FMNM = 100004;
    public static final int FACE_ACQUIRED_FIRST_FRAME_RECEIVED = 24;
    public static final int FACE_ACQUIRED_GOOD = 0;
    public static final int FACE_ACQUIRED_INSUFFICIENT = 1;
    public static final int FACE_ACQUIRED_MISALIGNED_BOTTOM = 1013;
    public static final int FACE_ACQUIRED_MISALIGNED_BOTTOM_LEFT = 1012;
    public static final int FACE_ACQUIRED_MISALIGNED_BOTTOM_RIGHT = 1014;
    public static final int FACE_ACQUIRED_MISALIGNED_LEFT = 1009;
    public static final int FACE_ACQUIRED_MISALIGNED_MIDDLE = 1010;
    public static final int FACE_ACQUIRED_MISALIGNED_RIGHT = 1011;
    public static final int FACE_ACQUIRED_MISALIGNED_TOP = 1007;
    public static final int FACE_ACQUIRED_MISALIGNED_TOP_LEFT = 1006;
    public static final int FACE_ACQUIRED_MISALIGNED_TOP_RIGHT = 1008;
    public static final int FACE_ACQUIRED_MOTION = 1023;
    public static final int FACE_ACQUIRED_MOUTH_COVERING_DETECTED = 26;
    public static final int FACE_ACQUIRED_NON_MASK = 1020;
    public static final int FACE_ACQUIRED_NOT_DETECTED = 11;
    public static final int FACE_ACQUIRED_NO_MATCH_MAX_COUNT = 1019;
    public static final int FACE_ACQUIRED_ON_MASK = 1017;
    public static final int FACE_ACQUIRED_PAN_TOO_EXTREME = 16;
    public static final int FACE_ACQUIRED_POOR_GAZE = 10;
    public static final int FACE_ACQUIRED_PROXIMITY_ALERT = 1001;
    public static final int FACE_ACQUIRED_RECALIBRATE = 13;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_DOWN = 1026;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_FRONT = 1024;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_LEFT = 1027;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_POSE_CHANGE = 1050;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_POSE_FAIL = 1051;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_RIGHT = 1028;
    public static final int FACE_ACQUIRED_REQUIRE_FACE_UP = 1025;
    public static final int FACE_ACQUIRED_ROLL_TOO_EXTREME = 18;
    public static final int FACE_ACQUIRED_SENSOR_DIRTY = 21;
    public static final int FACE_ACQUIRED_SET_BRIGHTNESS_UP = 1015;
    public static final int FACE_ACQUIRED_START = 20;
    public static final int FACE_ACQUIRED_TILT_TOO_EXTREME = 17;
    public static final int FACE_ACQUIRED_TOO_BRIGHT = 2;
    public static final int FACE_ACQUIRED_TOO_CLOSE = 4;
    public static final int FACE_ACQUIRED_TOO_DARK = 3;
    public static final int FACE_ACQUIRED_TOO_DIFFERENT = 14;
    public static final int FACE_ACQUIRED_TOO_FAR = 5;
    public static final int FACE_ACQUIRED_TOO_HIGH = 6;
    public static final int FACE_ACQUIRED_TOO_LEFT = 9;
    public static final int FACE_ACQUIRED_TOO_LOW = 7;
    public static final int FACE_ACQUIRED_TOO_MUCH_MOTION = 12;
    public static final int FACE_ACQUIRED_TOO_RIGHT = 8;
    public static final int FACE_ACQUIRED_TOO_SIMILAR = 15;
    public static final int FACE_ACQUIRED_UNKNOWN = 23;
    public static final int FACE_ACQUIRED_VENDOR = 22;
    public static final int FACE_ACQUIRED_VENDOR_BASE = 1000;
    public static final int FACE_ACQUIRED_VENDOR_FRAMEWORK_EVENT_BASE = 100000;
    public static final int FACE_ACQUIRED_WEARING_MASK = 1021;
    public static final int FACE_ACQUIRED_WITH_GLASSES = 1016;
    public static final int FACE_ERROR_CAMERA_ACCESS_SETTING_OFF = 100003;
    public static final int FACE_ERROR_CAMERA_FAILURE = 1003;
    public static final int FACE_ERROR_CAMERA_UNAVAILABLE = 1004;
    public static final int FACE_ERROR_CANCELED = 5;
    public static final int FACE_ERROR_GET_PREVIEW = 1002;
    public static final int FACE_ERROR_HW_NOT_PRESENT = 12;
    public static final int FACE_ERROR_HW_UNAVAILABLE = 1;
    public static final int FACE_ERROR_LOCKOUT = 7;
    public static final int FACE_ERROR_LOCKOUT_PERMANENT = 9;
    public static final int FACE_ERROR_NEGATIVE_BUTTON = 13;
    public static final int FACE_ERROR_NOT_ENROLLED = 11;
    public static final int FACE_ERROR_NO_SPACE = 4;
    public static final int FACE_ERROR_ON_MASK = 1006;
    public static final int FACE_ERROR_PPP_TIMEOUT = 1005;
    public static final int FACE_ERROR_SESSION_CLOSED = 1007;
    public static final int FACE_ERROR_TEMPLATE_CORRUPTED = 1001;
    public static final int FACE_ERROR_TIMEOUT = 3;
    public static final int FACE_ERROR_TOO_DARK = 100001;
    public static final int FACE_ERROR_TOO_DARK_TO_ENROLL = 100002;
    public static final int FACE_ERROR_UNABLE_TO_PROCESS = 2;
    public static final int FACE_ERROR_UNABLE_TO_REMOVE = 6;
    public static final int FACE_ERROR_UNKNOWN = 17;
    public static final int FACE_ERROR_USER_CANCELED = 10;
    public static final int FACE_ERROR_VENDOR = 8;
    public static final int FACE_ERROR_VENDOR_BASE = 1000;
    public static final int FEATURE_REQUIRE_ATTENTION = 1;
    public static final int FEATURE_REQUIRE_REQUIRE_DIVERSITY = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FaceAcquired {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FaceError {}

    public static int reasonToMetric(int reason) {
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
