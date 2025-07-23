package android.media;

import android.hardware.Camera;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class CameraProfile {
    public static final int QUALITY_HIGH = 2;
    public static final int QUALITY_LOW = 0;
    public static final int QUALITY_MEDIUM = 1;
    private static final HashMap<Integer, int[]> sCache = new HashMap<>();

    private static final native int native_get_image_encoding_quality_level(int i, int i2);

    private static final native int native_get_num_image_encoding_quality_levels(int i);

    private static final native void native_init();

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public static int getJpegEncodingQualityParameter(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 0) {
                return getJpegEncodingQualityParameter(i2, i);
            }
        }
        return 0;
    }

    public static int getJpegEncodingQualityParameter(int i, int i2) {
        int i3;
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException("Unsupported quality level: " + i2);
        }
        HashMap<Integer, int[]> hashMap = sCache;
        synchronized (hashMap) {
            int[] iArr = hashMap.get(Integer.valueOf(i));
            if (iArr == null) {
                iArr = getImageEncodingQualityLevels(i);
                hashMap.put(Integer.valueOf(i), iArr);
            }
            i3 = iArr[i2];
        }
        return i3;
    }

    private static int[] getImageEncodingQualityLevels(int i) {
        int native_get_num_image_encoding_quality_levels = native_get_num_image_encoding_quality_levels(i);
        if (native_get_num_image_encoding_quality_levels != 3) {
            throw new RuntimeException("Unexpected Jpeg encoding quality levels " + native_get_num_image_encoding_quality_levels);
        }
        int[] iArr = new int[native_get_num_image_encoding_quality_levels];
        for (int i2 = 0; i2 < native_get_num_image_encoding_quality_levels; i2++) {
            iArr[i2] = native_get_image_encoding_quality_level(i, i2);
        }
        Arrays.sort(iArr);
        return iArr;
    }
}
