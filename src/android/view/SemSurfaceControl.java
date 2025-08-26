package android.view;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.window.ScreenCapture;

/* loaded from: classes4.dex */
public class SemSurfaceControl {
    private static final String TAG = "SemSurfaceControl";

    public static IBinder getInternalDisplayToken() {
        long[] physicalDisplayIds = SurfaceControl.getPhysicalDisplayIds();
        if (physicalDisplayIds.length == 0) {
            return null;
        }
        return SurfaceControl.getPhysicalDisplayToken(physicalDisplayIds[0]);
    }

    public static Bitmap screenshot(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        Log.d(TAG, "Taking fullscreen screenshot");
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBufferCaptureDisplay = ScreenCapture.captureDisplay(new ScreenCapture.DisplayCaptureArgs.Builder(getInternalDisplayToken()).setSize(i, i2).setUseIdentityTransform(true).build());
        Bitmap bitmapAsBitmap = screenshotHardwareBufferCaptureDisplay == null ? null : screenshotHardwareBufferCaptureDisplay.asBitmap();
        if (bitmapAsBitmap != null) {
            return bitmapAsBitmap;
        }
        Log.e(TAG, "Failed to take fullscreen screenshot");
        return null;
    }

    public static Bitmap screenshot(Rect rect, int i, int i2, int i3, int i4, boolean z, int i5) {
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        Log.d(TAG, "Taking screenshot with sourceCrop");
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBufferCaptureDisplay = ScreenCapture.captureDisplay(new ScreenCapture.DisplayCaptureArgs.Builder(getInternalDisplayToken()).setSourceCrop(rect).setSize(i, i2).setUseIdentityTransform(z).build());
        Bitmap bitmapAsBitmap = screenshotHardwareBufferCaptureDisplay == null ? null : screenshotHardwareBufferCaptureDisplay.asBitmap();
        if (bitmapAsBitmap != null) {
            return bitmapAsBitmap;
        }
        Log.e(TAG, "Failed to take screenshot with sourceCrop");
        return null;
    }
}
