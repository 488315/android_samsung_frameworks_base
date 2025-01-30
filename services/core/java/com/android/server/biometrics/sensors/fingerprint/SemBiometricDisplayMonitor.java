package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import com.android.server.LocalServices;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class SemBiometricDisplayMonitor implements DisplayManagerInternal.DisplayStateListener, DisplayManagerInternal.DisplayBrightnessListener {
    public final Context mContext;
    public final Handler mHandler;
    public final List mCallbacks = new CopyOnWriteArrayList();
    public DisplayManagerInternal mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);

    public interface Callback {
        default void onBrightnessChanged(float f) {
        }

        default void onDisplayOff() {
        }

        default void onDisplayOn() {
        }

        default void onFinishDisplayState(int i, int i2, int i3) {
        }

        default void onStartDisplayState(int i, int i2, int i3) {
        }
    }

    public void onChanged(float f) {
    }

    public void onFinish(int i, int i2, int i3) {
    }

    public void onStart(int i, int i2, int i3) {
    }

    public SemBiometricDisplayMonitor(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    public void registerCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            return;
        }
        this.mCallbacks.add(callback);
    }

    public void unregisterCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }
}
