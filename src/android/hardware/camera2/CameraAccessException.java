package android.hardware.camera2;

import android.util.AndroidException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class CameraAccessException extends AndroidException {
    public static final int CAMERA_DEPRECATED_HAL = 1000;
    public static final int CAMERA_DISABLED = 1;
    public static final int CAMERA_DISCONNECTED = 2;
    public static final int CAMERA_ERROR = 3;
    public static final int CAMERA_IN_USE = 4;
    public static final int MAX_CAMERAS_IN_USE = 5;
    private static final long serialVersionUID = 5630338637471475675L;
    private final int mReason;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessError {
    }

    public final int getReason() {
        return this.mReason;
    }

    public CameraAccessException(int i) {
        super(getDefaultMessage(i));
        this.mReason = i;
    }

    public CameraAccessException(int i, String str) {
        super(getCombinedMessage(i, str));
        this.mReason = i;
    }

    public CameraAccessException(int i, String str, Throwable th) {
        super(getCombinedMessage(i, str), th);
        this.mReason = i;
    }

    public CameraAccessException(int i, Throwable th) {
        super(getDefaultMessage(i), th);
        this.mReason = i;
    }

    public static String getDefaultMessage(int i) {
        if (i == 1) {
            return "The camera is disabled due to a device policy, and cannot be opened.";
        }
        if (i == 2) {
            return "The camera device is removable and has been disconnected from the Android device, or the camera service has shut down the connection due to a higher-priority access request for the camera device.";
        }
        if (i == 3) {
            return "The camera device is currently in the error state; no further calls to it will succeed.";
        }
        if (i == 4) {
            return "The camera device is in use already";
        }
        if (i != 5) {
            return null;
        }
        return "The system-wide limit for number of open cameras has been reached, and more camera devices cannot be opened until previous instances are closed.";
    }

    private static String getCombinedMessage(int i, String str) {
        return String.format("%s (%d): %s", getProblemString(i), Integer.valueOf(i), str);
    }

    private static String getProblemString(int i) {
        if (i == 1) {
            return "CAMERA_DISABLED";
        }
        if (i == 2) {
            return "CAMERA_DISCONNECTED";
        }
        if (i == 3) {
            return "CAMERA_ERROR";
        }
        if (i == 4) {
            return "CAMERA_IN_USE";
        }
        if (i == 5) {
            return "MAX_CAMERAS_IN_USE";
        }
        if (i == 1000) {
            return "CAMERA_DEPRECATED_HAL";
        }
        return "<UNKNOWN ERROR>";
    }
}
