package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.Handler;
import android.util.IntArray;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import com.android.internal.camera.flags.Flags;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
public final class CameraExtensionUtils {
    public static final int JPEG_DEFAULT_QUALITY = 100;
    public static final int JPEG_DEFAULT_ROTATION = 0;
    public static final int[] SUPPORTED_CAPTURE_OUTPUT_FORMATS = {35, 256, 4101};
    private static final String TAG = "CameraExtensionUtils";

    public static class SurfaceInfo {
        public int mWidth = 0;
        public int mHeight = 0;
        public int mFormat = 1;
        public long mUsage = 0;
    }

    public static final class HandlerExecutor implements Executor {
        private final Handler mHandler;

        public HandlerExecutor(Handler handler) {
            this.mHandler = handler;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runCmd) {
            try {
                this.mHandler.post(runCmd);
            } catch (RejectedExecutionException e) {
                Log.w(CameraExtensionUtils.TAG, "Handler thread unavailable, skipping message!");
            }
        }
    }

    public static SurfaceInfo querySurface(Surface s) {
        SurfaceInfo surfaceInfo = new SurfaceInfo();
        int nativeFormat = SurfaceUtils.detectSurfaceFormat(s);
        int dataspace = SurfaceUtils.getSurfaceDataspace(s);
        Size surfaceSize = SurfaceUtils.getSurfaceSize(s);
        surfaceInfo.mFormat = nativeFormat;
        surfaceInfo.mWidth = surfaceSize.getWidth();
        surfaceInfo.mHeight = surfaceSize.getHeight();
        surfaceInfo.mUsage = SurfaceUtils.getSurfaceUsage(s);
        if (nativeFormat == 33 && dataspace == 146931712) {
            surfaceInfo.mFormat = 256;
            return surfaceInfo;
        }
        if (nativeFormat == 33 && dataspace == 4101) {
            surfaceInfo.mFormat = 4101;
            return surfaceInfo;
        }
        return surfaceInfo;
    }

    public static Surface getPostviewSurface(OutputConfiguration outputConfig, HashMap<Integer, List<Size>> supportedPostviewSizes, int captureFormat) {
        if (outputConfig == null) {
            return null;
        }
        SurfaceInfo surfaceInfo = querySurface(outputConfig.getSurface());
        if (Flags.extension10Bit()) {
            Size postviewSize = new Size(surfaceInfo.mWidth, surfaceInfo.mHeight);
            if (supportedPostviewSizes.get(Integer.valueOf(surfaceInfo.mFormat)).contains(postviewSize)) {
                return outputConfig.getSurface();
            }
            throw new IllegalArgumentException("Postview size not supported!");
        }
        if (surfaceInfo.mFormat == captureFormat) {
            if (!supportedPostviewSizes.containsKey(Integer.valueOf(captureFormat))) {
                return null;
            }
            Size postviewSize2 = new Size(surfaceInfo.mWidth, surfaceInfo.mHeight);
            if (supportedPostviewSizes.get(Integer.valueOf(surfaceInfo.mFormat)).contains(postviewSize2)) {
                return outputConfig.getSurface();
            }
            throw new IllegalArgumentException("Postview size not supported!");
        }
        throw new IllegalArgumentException("Postview format should be equivalent to  the capture format!");
    }

    public static Surface getBurstCaptureSurface(List<OutputConfiguration> outputConfigs, HashMap<Integer, List<Size>> supportedCaptureSizes) {
        IntArray supportedCaptureOutputFormats = new IntArray(SUPPORTED_CAPTURE_OUTPUT_FORMATS.length);
        supportedCaptureOutputFormats.addAll(SUPPORTED_CAPTURE_OUTPUT_FORMATS);
        if (Flags.extension10Bit()) {
            supportedCaptureOutputFormats.add(54);
        }
        for (OutputConfiguration config : outputConfigs) {
            SurfaceInfo surfaceInfo = querySurface(config.getSurface());
            for (int supportedFormat : supportedCaptureOutputFormats.toArray()) {
                if (surfaceInfo.mFormat == supportedFormat) {
                    Size captureSize = new Size(surfaceInfo.mWidth, surfaceInfo.mHeight);
                    if (supportedCaptureSizes.containsKey(Integer.valueOf(supportedFormat))) {
                        if (supportedCaptureSizes.get(Integer.valueOf(surfaceInfo.mFormat)).contains(captureSize)) {
                            return config.getSurface();
                        }
                        throw new IllegalArgumentException("Capture size not supported!");
                    }
                    return config.getSurface();
                }
            }
        }
        return null;
    }

    public static Surface getRepeatingRequestSurface(List<OutputConfiguration> outputConfigs, List<Size> supportedPreviewSizes) {
        for (OutputConfiguration config : outputConfigs) {
            SurfaceInfo surfaceInfo = querySurface(config.getSurface());
            if (surfaceInfo.mFormat == 34 || (surfaceInfo.mUsage & 2048) != 0 || surfaceInfo.mFormat == 1) {
                Size repeatingRequestSurfaceSize = new Size(surfaceInfo.mWidth, surfaceInfo.mHeight);
                if (supportedPreviewSizes == null || !supportedPreviewSizes.contains(repeatingRequestSurfaceSize)) {
                    throw new IllegalArgumentException("Repeating request surface size " + repeatingRequestSurfaceSize + " not supported!");
                }
                return config.getSurface();
            }
        }
        return null;
    }

    public static Map<String, CameraMetadataNative> getCharacteristicsMapNative(Map<String, CameraCharacteristics> charsMap) {
        HashMap<String, CameraMetadataNative> ret = new HashMap<>();
        for (Map.Entry<String, CameraCharacteristics> entry : charsMap.entrySet()) {
            ret.put(entry.getKey(), entry.getValue().getNativeMetadata());
        }
        return ret;
    }
}
