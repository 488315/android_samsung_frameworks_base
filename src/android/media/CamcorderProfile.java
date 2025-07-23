package android.media;

import android.app.compat.CompatChanges;
import android.hardware.Camera;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class CamcorderProfile {
    public static final int QUALITY_1080P = 6;
    public static final int QUALITY_2160P = 8;
    public static final int QUALITY_2K = 12;
    public static final int QUALITY_480P = 4;
    public static final int QUALITY_4KDCI = 10;
    public static final int QUALITY_720P = 5;
    public static final int QUALITY_8KUHD = 13;
    public static final int QUALITY_CIF = 3;
    public static final int QUALITY_HIGH = 1;
    public static final int QUALITY_HIGH_SPEED_1080P = 2004;
    public static final int QUALITY_HIGH_SPEED_2160P = 2005;
    public static final int QUALITY_HIGH_SPEED_480P = 2002;
    public static final int QUALITY_HIGH_SPEED_4KDCI = 2008;
    public static final int QUALITY_HIGH_SPEED_720P = 2003;
    public static final int QUALITY_HIGH_SPEED_CIF = 2006;
    public static final int QUALITY_HIGH_SPEED_HIGH = 2001;
    private static final int QUALITY_HIGH_SPEED_LIST_END = 2008;
    private static final int QUALITY_HIGH_SPEED_LIST_START = 2000;
    public static final int QUALITY_HIGH_SPEED_LOW = 2000;
    public static final int QUALITY_HIGH_SPEED_VGA = 2007;
    private static final int QUALITY_LIST_END = 13;
    private static final int QUALITY_LIST_START = 0;
    public static final int QUALITY_LOW = 0;
    public static final int QUALITY_QCIF = 2;
    public static final int QUALITY_QHD = 11;
    public static final int QUALITY_QVGA = 7;
    public static final int QUALITY_TIME_LAPSE_1080P = 1006;
    public static final int QUALITY_TIME_LAPSE_2160P = 1008;
    public static final int QUALITY_TIME_LAPSE_2K = 1012;
    public static final int QUALITY_TIME_LAPSE_480P = 1004;
    public static final int QUALITY_TIME_LAPSE_4KDCI = 1010;
    public static final int QUALITY_TIME_LAPSE_720P = 1005;
    public static final int QUALITY_TIME_LAPSE_8KUHD = 1013;
    public static final int QUALITY_TIME_LAPSE_CIF = 1003;
    public static final int QUALITY_TIME_LAPSE_HIGH = 1001;
    private static final int QUALITY_TIME_LAPSE_LIST_END = 1013;
    private static final int QUALITY_TIME_LAPSE_LIST_START = 1000;
    public static final int QUALITY_TIME_LAPSE_LOW = 1000;
    public static final int QUALITY_TIME_LAPSE_QCIF = 1002;
    public static final int QUALITY_TIME_LAPSE_QHD = 1011;
    public static final int QUALITY_TIME_LAPSE_QVGA = 1007;
    public static final int QUALITY_TIME_LAPSE_VGA = 1009;
    public static final int QUALITY_VGA = 9;
    public static final long RETURN_ADVANCED_VIDEO_PROFILES = 206033068;
    public int audioBitRate;
    public int audioChannels;
    public int audioCodec;
    public int audioSampleRate;
    public int duration;
    public int fileFormat;
    public int quality;
    public int videoBitRate;
    public int videoCodec;
    public int videoFrameHeight;
    public int videoFrameRate;
    public int videoFrameWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Quality {
    }

    private static final native CamcorderProfile native_get_camcorder_profile(int i, int i2);

    private static final native EncoderProfiles native_get_camcorder_profiles(int i, int i2, boolean z);

    private static final native boolean native_has_camcorder_profile(int i, int i2);

    private static final native void native_init();

    public static CamcorderProfile get(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 0) {
                return get(i2, i);
            }
        }
        return null;
    }

    public static CamcorderProfile get(int i, int i2) {
        if ((i2 < 0 || i2 > 13) && ((i2 < 1000 || i2 > 1013) && (i2 < 2000 || i2 > 2008))) {
            throw new IllegalArgumentException("Unsupported quality level: " + i2);
        }
        return native_get_camcorder_profile(i, i2);
    }

    public static EncoderProfiles getAll(String str, int i) {
        if ((i < 0 || i > 13) && ((i < 1000 || i > 1013) && (i < 2000 || i > 2008))) {
            throw new IllegalArgumentException("Unsupported quality level: " + i);
        }
        try {
            return native_get_camcorder_profiles(Integer.valueOf(str).intValue(), i, CompatChanges.isChangeEnabled(RETURN_ADVANCED_VIDEO_PROFILES));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static boolean hasProfile(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 0) {
                return hasProfile(i2, i);
            }
        }
        return false;
    }

    public static boolean hasProfile(int i, int i2) {
        return native_has_camcorder_profile(i, i2);
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    private CamcorderProfile(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        this.duration = i;
        this.quality = i2;
        this.fileFormat = i3;
        this.videoCodec = i4;
        this.videoBitRate = i5;
        this.videoFrameRate = i6;
        this.videoFrameWidth = i7;
        this.videoFrameHeight = i8;
        this.audioCodec = i9;
        this.audioBitRate = i10;
        this.audioSampleRate = i11;
        this.audioChannels = i12;
    }
}
