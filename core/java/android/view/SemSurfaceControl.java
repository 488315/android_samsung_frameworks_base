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

  public static Bitmap screenshot(int width, int height) {
    if (width <= 0 || height <= 0) {
      return null;
    }
    Log.m94d(TAG, "Taking fullscreen screenshot");
    IBinder displayToken = getInternalDisplayToken();
    ScreenCapture.DisplayCaptureArgs captureArgs =
        new ScreenCapture.DisplayCaptureArgs.Builder(displayToken)
            .setSize(width, height)
            .setUseIdentityTransform(true)
            .build();
    ScreenCapture.ScreenshotHardwareBuffer screenshotBuffer =
        ScreenCapture.captureDisplay(captureArgs);
    Bitmap screenShot = screenshotBuffer == null ? null : screenshotBuffer.asBitmap();
    if (screenShot == null) {
      Log.m96e(TAG, "Failed to take fullscreen screenshot");
      return null;
    }
    return screenShot;
  }

  public static Bitmap screenshot(
      Rect sourceCrop,
      int width,
      int height,
      int minLayer,
      int maxLayer,
      boolean useIdentityTransform,
      int rotation) {
    if (width <= 0 || height <= 0) {
      return null;
    }
    Log.m94d(TAG, "Taking screenshot with sourceCrop");
    IBinder displayToken = getInternalDisplayToken();
    ScreenCapture.DisplayCaptureArgs captureArgs =
        new ScreenCapture.DisplayCaptureArgs.Builder(displayToken)
            .setSourceCrop(sourceCrop)
            .setSize(width, height)
            .setUseIdentityTransform(useIdentityTransform)
            .build();
    ScreenCapture.ScreenshotHardwareBuffer screenshotBuffer =
        ScreenCapture.captureDisplay(captureArgs);
    Bitmap screenShot = screenshotBuffer == null ? null : screenshotBuffer.asBitmap();
    if (screenShot == null) {
      Log.m96e(TAG, "Failed to take screenshot with sourceCrop");
      return null;
    }
    return screenShot;
  }
}
