package com.android.server.input;

import android.os.IBinder;
import android.os.InputConstants;
import android.util.Slog;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes2.dex */
public class GestureMonitorSpyWindow {
  public static final String TAG = "GestureMonitorSpyWindow";
  public final InputApplicationHandle mApplicationHandle;
  public final InputChannel mClientChannel;
  public final SurfaceControl mInputSurface;
  public final IBinder mMonitorToken;
  public final InputWindowHandle mWindowHandle;

  public GestureMonitorSpyWindow(
      IBinder iBinder,
      String str,
      int i,
      int i2,
      int i3,
      SurfaceControl surfaceControl,
      InputChannel inputChannel) {
    this.mMonitorToken = iBinder;
    this.mClientChannel = inputChannel;
    this.mInputSurface = surfaceControl;
    InputApplicationHandle inputApplicationHandle =
        new InputApplicationHandle(
            (IBinder) null, str, InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS);
    this.mApplicationHandle = inputApplicationHandle;
    InputWindowHandle inputWindowHandle = new InputWindowHandle(inputApplicationHandle, i);
    this.mWindowHandle = inputWindowHandle;
    inputWindowHandle.name = str;
    inputWindowHandle.token = inputChannel.getToken();
    inputWindowHandle.layoutParamsType = 2015;
    inputWindowHandle.dispatchingTimeoutMillis = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    inputWindowHandle.ownerPid = i2;
    inputWindowHandle.ownerUid = i3;
    inputWindowHandle.scaleFactor = 1.0f;
    inputWindowHandle.replaceTouchableRegionWithCrop((SurfaceControl) null);
    inputWindowHandle.inputConfig = 16644;
    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
    transaction.setInputWindowInfo(surfaceControl, inputWindowHandle);
    transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
    transaction.setLayer(surfaceControl, 1);
    transaction.setPosition(
        surfaceControl,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    transaction.setCrop(surfaceControl, null);
    transaction.show(surfaceControl);
    transaction.apply();
  }

  public void remove() {
    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
    transaction.hide(this.mInputSurface);
    transaction.remove(this.mInputSurface);
    transaction.apply();
    this.mClientChannel.dispose();
  }

  public void configureSurface(
      SurfaceControl.Transaction transaction, int i, SurfaceControl surfaceControl) {
    if (this.mWindowHandle.displayId == i && this.mInputSurface.isValid()) {
      if (CoreRune.SAFE_DEBUG) {
        Slog.d(
            TAG,
            "configureSurface, mInputSurface="
                + this.mInputSurface
                + ", parentSurface="
                + surfaceControl);
      }
      transaction.reparent(this.mInputSurface, surfaceControl);
    }
  }

  public String dump() {
    return "name='"
        + this.mWindowHandle.name
        + "', inputChannelToken="
        + this.mClientChannel.getToken()
        + " displayId="
        + this.mWindowHandle.displayId;
  }
}
