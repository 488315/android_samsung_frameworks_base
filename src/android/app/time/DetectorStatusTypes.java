package android.app.time;

import android.text.TextUtils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes.dex */
public final class DetectorStatusTypes {
    public static final int DETECTION_ALGORITHM_STATUS_NOT_RUNNING = 2;
    public static final int DETECTION_ALGORITHM_STATUS_NOT_SUPPORTED = 1;
    public static final int DETECTION_ALGORITHM_STATUS_RUNNING = 3;
    public static final int DETECTION_ALGORITHM_STATUS_UNKNOWN = 0;
    public static final int DETECTOR_STATUS_NOT_RUNNING = 2;
    public static final int DETECTOR_STATUS_NOT_SUPPORTED = 1;
    public static final int DETECTOR_STATUS_RUNNING = 3;
    public static final int DETECTOR_STATUS_UNKNOWN = 0;

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DetectionAlgorithmStatus {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DetectorStatus {
    }

    private DetectorStatusTypes() {
    }

    public static int requireValidDetectorStatus(int i) {
        if (i >= 0 && i <= 3) {
            return i;
        }
        throw new IllegalArgumentException("Invalid detector status: " + i);
    }

    public static String detectorStatusToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "NOT_SUPPORTED";
        }
        if (i == 2) {
            return "NOT_RUNNING";
        }
        if (i == 3) {
            return "RUNNING";
        }
        throw new IllegalArgumentException("Unknown status: " + i);
    }

    public static int detectorStatusFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty status: " + str);
        }
        str.hashCode();
        switch (str) {
            case "RUNNING":
                return 3;
            case "UNKNOWN":
                return 0;
            case "NOT_SUPPORTED":
                return 1;
            case "NOT_RUNNING":
                return 2;
            default:
                throw new IllegalArgumentException("Unknown status: " + str);
        }
    }

    public static int requireValidDetectionAlgorithmStatus(int i) {
        if (i >= 0 && i <= 3) {
            return i;
        }
        throw new IllegalArgumentException("Invalid detection algorithm: " + i);
    }

    public static String detectionAlgorithmStatusToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "NOT_SUPPORTED";
        }
        if (i == 2) {
            return "NOT_RUNNING";
        }
        if (i == 3) {
            return "RUNNING";
        }
        throw new IllegalArgumentException("Unknown status: " + i);
    }

    public static int detectionAlgorithmStatusFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty status: " + str);
        }
        str.hashCode();
        switch (str) {
            case "RUNNING":
                return 3;
            case "UNKNOWN":
                return 0;
            case "NOT_SUPPORTED":
                return 1;
            case "NOT_RUNNING":
                return 2;
            default:
                throw new IllegalArgumentException("Unknown status: " + str);
        }
    }
}
