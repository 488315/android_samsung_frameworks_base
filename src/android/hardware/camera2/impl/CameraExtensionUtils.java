package android.hardware.camera2.impl;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.Handler;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import com.android.internal.camera.flags.Flags;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
public final class CameraExtensionUtils {
    public static final int JPEG_DEFAULT_QUALITY = 100;
    public static final int JPEG_DEFAULT_ROTATION = 0;
    public static HashSet<Integer> SUPPORTED_CAPTURE_OUTPUT_FORMATS = null;
    private static final String TAG = "CameraExtensionUtils";

    public static class SurfaceInfo {
        public int mWidth = 0;
        public int mHeight = 0;
        public int mFormat = 1;
        public long mUsage = 0;
    }

    static {
        HashSet<Integer> hashSet = new HashSet<>();
        SUPPORTED_CAPTURE_OUTPUT_FORMATS = hashSet;
        hashSet.addAll(Arrays.asList(35, 256, 54, 4101));
        if (Flags.depthJpegExtensions()) {
            SUPPORTED_CAPTURE_OUTPUT_FORMATS.add(Integer.valueOf(ImageFormat.DEPTH_JPEG));
        }
    }

    public static final class HandlerExecutor implements Executor {
        private final Handler mHandler;

        public HandlerExecutor(Handler handler) {
            this.mHandler = handler;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            try {
                this.mHandler.post(runnable);
            } catch (RejectedExecutionException unused) {
                Log.w(CameraExtensionUtils.TAG, "Handler thread unavailable, skipping message!");
            }
        }
    }

    public static SurfaceInfo querySurface(Surface surface) {
        SurfaceInfo surfaceInfo = new SurfaceInfo();
        int detectSurfaceFormat = SurfaceUtils.detectSurfaceFormat(surface);
        int surfaceDataspace = SurfaceUtils.getSurfaceDataspace(surface);
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        surfaceInfo.mFormat = detectSurfaceFormat;
        surfaceInfo.mWidth = surfaceSize.getWidth();
        surfaceInfo.mHeight = surfaceSize.getHeight();
        surfaceInfo.mUsage = SurfaceUtils.getSurfaceUsage(surface);
        if (detectSurfaceFormat == 33 && surfaceDataspace == 146931712) {
            surfaceInfo.mFormat = 256;
            return surfaceInfo;
        }
        if (detectSurfaceFormat == 33 && surfaceDataspace == 4101) {
            surfaceInfo.mFormat = 4101;
            return surfaceInfo;
        }
        if (Flags.depthJpegExtensions() && detectSurfaceFormat == 33 && surfaceDataspace == 4098) {
            surfaceInfo.mFormat = ImageFormat.DEPTH_JPEG;
        }
        return surfaceInfo;
    }

    public static Surface getPostviewSurface(OutputConfiguration outputConfiguration, HashMap<Integer, List<Size>> hashMap, int i) {
        if (outputConfiguration == null) {
            return null;
        }
        SurfaceInfo querySurface = querySurface(outputConfiguration.getSurface());
        if (hashMap.get(Integer.valueOf(querySurface.mFormat)).contains(new Size(querySurface.mWidth, querySurface.mHeight))) {
            return outputConfiguration.getSurface();
        }
        throw new IllegalArgumentException("Postview size not supported!");
    }

    public static Surface getBurstCaptureSurface(List<OutputConfiguration> list, HashMap<Integer, List<Size>> hashMap) {
        Integer[] numArr = (Integer[]) SUPPORTED_CAPTURE_OUTPUT_FORMATS.toArray(new Integer[SUPPORTED_CAPTURE_OUTPUT_FORMATS.size()]);
        for (OutputConfiguration outputConfiguration : list) {
            SurfaceInfo querySurface = querySurface(outputConfiguration.getSurface());
            for (Integer num : numArr) {
                if (querySurface.mFormat == num.intValue()) {
                    Size size = new Size(querySurface.mWidth, querySurface.mHeight);
                    if (hashMap.containsKey(num)) {
                        if (hashMap.get(Integer.valueOf(querySurface.mFormat)).contains(size)) {
                            return outputConfiguration.getSurface();
                        }
                        throw new IllegalArgumentException("Capture size not supported!");
                    }
                    return outputConfiguration.getSurface();
                }
            }
        }
        return null;
    }

    public static Surface getRepeatingRequestSurface(List<OutputConfiguration> list, List<Size> list2) {
        for (OutputConfiguration outputConfiguration : list) {
            SurfaceInfo querySurface = querySurface(outputConfiguration.getSurface());
            if (querySurface.mFormat == 34 || (querySurface.mUsage & 2048) != 0 || querySurface.mFormat == 1) {
                Size size = new Size(querySurface.mWidth, querySurface.mHeight);
                if (list2 == null || !list2.contains(size)) {
                    throw new IllegalArgumentException("Repeating request surface size " + size + " not supported!");
                }
                return outputConfiguration.getSurface();
            }
        }
        return null;
    }

    public static Map<String, CameraMetadataNative> getCharacteristicsMapNative(Map<String, CameraCharacteristics> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, CameraCharacteristics> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().getNativeMetadata());
        }
        return hashMap;
    }
}
