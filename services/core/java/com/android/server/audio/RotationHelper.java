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

    public final class AudioDisplayListener implements DisplayManager.DisplayListener {
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            RotationHelper.updateOrientation();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {}
    }

    public static void disable() {
        ((DisplayManager) sContext.getSystemService("display"))
                .unregisterDisplayListener(sDisplayListener);
        ((DeviceStateManager) sContext.getSystemService(DeviceStateManager.class))
                .unregisterCallback(sFoldStateListener);
    }

    public static void enable() {
        ((DisplayManager) sContext.getSystemService("display"))
                .registerDisplayListener(sDisplayListener, sHandler);
        updateOrientation();
        ((DeviceStateManager) sContext.getSystemService(DeviceStateManager.class))
                .registerCallback(new HandlerExecutor(sHandler), sFoldStateListener);
    }

    public static void forceUpdate() {
        synchronized (sRotationLock) {
            sRotation = null;
        }
        updateOrientation();
        synchronized (sFoldStateLock) {
            try {
                Boolean bool = sFoldState;
                if (bool != null) {
                    sFoldStateCallback.accept(bool);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void updateOrientation() {
        int i = 0;
        int i2 = DisplayManagerGlobal.getInstance().getDisplayInfo(0).rotation;
        synchronized (sRotationLock) {
            try {
                Integer num = sRotation;
                if (num != null) {
                    if (num.intValue() != i2) {}
                }
                Integer valueOf = Integer.valueOf(i2);
                sRotation = valueOf;
                int intValue = valueOf.intValue();
                if (intValue != 0) {
                    if (intValue == 1) {
                        i = 90;
                    } else if (intValue == 2) {
                        i = 180;
                    } else if (intValue != 3) {
                        Log.e("AudioService.RotationHelper", "Unknown device rotation");
                        i = -1;
                    } else {
                        i =
                                FrameworkStatsLog
                                        .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6;
                    }
                }
                if (i != -1) {
                    sRotationCallback.accept(Integer.valueOf(i));
                }
            } finally {
            }
        }
    }
}
