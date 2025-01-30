package com.android.server.audio;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class RotationHelper {
  public static Context sContext;
  public static AudioDisplayListener sDisplayListener;
  public static Boolean sFoldState;
  public static Consumer sFoldStateCallback;
  public static DeviceStateManager.FoldStateListener sFoldStateListener;
  public static Handler sHandler;
  public static Integer sRotation;
  public static Consumer sRotationCallback;
  public static final Object sRotationLock = new Object();
  public static final Object sFoldStateLock = new Object();

  public static void init(Context context, Handler handler, Consumer consumer, Consumer consumer2) {
    if (context == null) {
      throw new IllegalArgumentException("Invalid null context");
    }
    sContext = context;
    sHandler = handler;
    sDisplayListener = new AudioDisplayListener();
    sRotationCallback = consumer;
    sFoldStateCallback = consumer2;
    enable();
  }

  public static void enable() {
    ((DisplayManager) sContext.getSystemService("display"))
        .registerDisplayListener(sDisplayListener, sHandler);
    updateOrientation();
    sFoldStateListener =
        new DeviceStateManager.FoldStateListener(
            sContext,
            new Consumer() { // from class:
              // com.android.server.audio.RotationHelper$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                RotationHelper.lambda$enable$0((Boolean) obj);
              }
            });
    ((DeviceStateManager) sContext.getSystemService(DeviceStateManager.class))
        .registerCallback(new HandlerExecutor(sHandler), sFoldStateListener);
  }

  public static /* synthetic */ void lambda$enable$0(Boolean bool) {
    updateFoldState(bool.booleanValue());
  }

  public static void disable() {
    ((DisplayManager) sContext.getSystemService("display"))
        .unregisterDisplayListener(sDisplayListener);
    ((DeviceStateManager) sContext.getSystemService(DeviceStateManager.class))
        .unregisterCallback(sFoldStateListener);
  }

  public static void updateOrientation() {
    int i = DisplayManagerGlobal.getInstance().getDisplayInfo(0).rotation;
    synchronized (sRotationLock) {
      Integer num = sRotation;
      if (num == null || num.intValue() != i) {
        Integer valueOf = Integer.valueOf(i);
        sRotation = valueOf;
        publishRotation(valueOf.intValue());
      }
    }
  }

  public static void publishRotation(int i) {
    int i2;
    if (i == 0) {
      i2 = 0;
    } else if (i == 1) {
      i2 = 90;
    } else if (i == 2) {
      i2 = 180;
    } else if (i != 3) {
      Log.e("AudioService.RotationHelper", "Unknown device rotation");
      i2 = -1;
    } else {
      i2 = FrameworkStatsLog.f392x1636cc72;
    }
    if (i2 != -1) {
      sRotationCallback.accept(Integer.valueOf(i2));
    }
  }

  public static void updateFoldState(boolean z) {
    synchronized (sFoldStateLock) {
      Boolean bool = sFoldState;
      if (bool == null || bool.booleanValue() != z) {
        sFoldState = Boolean.valueOf(z);
        sFoldStateCallback.accept(Boolean.valueOf(z));
      }
    }
  }

  public static void forceUpdate() {
    synchronized (sRotationLock) {
      sRotation = null;
    }
    updateOrientation();
    synchronized (sFoldStateLock) {
      Boolean bool = sFoldState;
      if (bool != null) {
        sFoldStateCallback.accept(bool);
      }
    }
  }

  public final class AudioDisplayListener implements DisplayManager.DisplayListener {
    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
      RotationHelper.updateOrientation();
    }
  }
}
