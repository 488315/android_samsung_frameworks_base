package com.android.server.biometrics;

import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.fingerprint.SemFingerprintServiceExtImpl$$ExternalSyntheticLambda0;

import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemBiometricDisplayStateMonitor
        implements DisplayManagerInternal.DisplayStateListener,
                DisplayManagerInternal.DisplayBrightnessListener {
    public final List mBlockingTasksWhenStartPhysicalState;
    public int mBrightness;
    public final List mBrightnessCallbacks;
    public DisplayManagerInternal mDisplayManagerInternal;
    public final List mDisplayStateCallbacks;
    public final Handler mHandler;
    public int mLogicalDisplayState;
    public int mPhysicalDisplayState;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DisplayStateCallback {
        default void onDisplayOff() {}

        default void onDisplayOn() {}

        default void onFinishDisplayState(int i, int i2, int i3) {}

        default void onStartDisplayState(int i, int i2, int i3) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class InstanceHolder {
        public static final SemBiometricDisplayStateMonitor INSTANCE =
                new SemBiometricDisplayStateMonitor();
    }

    public SemBiometricDisplayStateMonitor() {
        this.mDisplayStateCallbacks = new CopyOnWriteArrayList();
        this.mBrightnessCallbacks = new CopyOnWriteArrayList();
        this.mLogicalDisplayState = 2;
        this.mPhysicalDisplayState = 2;
        this.mBrightness = 127;
        this.mBlockingTasksWhenStartPhysicalState = new CopyOnWriteArrayList();
        this.mHandler =
                BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    }

    public SemBiometricDisplayStateMonitor(Handler handler) {
        this.mDisplayStateCallbacks = new CopyOnWriteArrayList();
        this.mBrightnessCallbacks = new CopyOnWriteArrayList();
        this.mLogicalDisplayState = 2;
        this.mPhysicalDisplayState = 2;
        this.mBrightness = 127;
        this.mBlockingTasksWhenStartPhysicalState = new CopyOnWriteArrayList();
        this.mHandler = handler;
    }

    public final void onChanged(final float f) {
        this.mHandler.post(
                new Runnable() { // from class:
                    // com.android.server.biometrics.SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor =
                                SemBiometricDisplayStateMonitor.this;
                        float f2 = f;
                        semBiometricDisplayStateMonitor.getClass();
                        int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f2);
                        if (Utils.DEBUG) {
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                    brightnessFloatToInt,
                                    "DisplayBrightnessListener#onChanged : ",
                                    "SemBiometricDisplayStateMonitor");
                        }
                        semBiometricDisplayStateMonitor.mBrightness = brightnessFloatToInt;
                        Iterator it =
                                ((CopyOnWriteArrayList)
                                                semBiometricDisplayStateMonitor
                                                        .mBrightnessCallbacks)
                                        .iterator();
                        while (it.hasNext()) {
                            SemFingerprintServiceExtImpl$$ExternalSyntheticLambda0
                                    semFingerprintServiceExtImpl$$ExternalSyntheticLambda0 =
                                            (SemFingerprintServiceExtImpl$$ExternalSyntheticLambda0)
                                                    it.next();
                            float f3 = semBiometricDisplayStateMonitor.mBrightness;
                            ISemBiometricSysUiDisplayBrightnessCallback
                                    iSemBiometricSysUiDisplayBrightnessCallback =
                                            semFingerprintServiceExtImpl$$ExternalSyntheticLambda0
                                                    .f$0
                                                    .mSysUiDisplayBrightnessCallback;
                            if (iSemBiometricSysUiDisplayBrightnessCallback != null) {
                                try {
                                    iSemBiometricSysUiDisplayBrightnessCallback.onBrightnessChanged(
                                            f3);
                                } catch (RemoteException unused) {
                                    Slog.w(
                                            "FingerprintService.Ext",
                                            "onBrightnessChanged: failed to invoke"
                                                + " onBrightnessChangedCallback");
                                }
                            }
                        }
                    }
                });
    }

    public final void onFinish(int i, int i2, int i3) {
        if (i3 != 1) {
            return;
        }
        this.mHandler.post(
                new SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda1(this, i, i2, i3, 0));
    }

    public final void onStart(int i, int i2, int i3) {
        if (i3 != 1) {
            return;
        }
        Iterator it = this.mBlockingTasksWhenStartPhysicalState.iterator();
        while (it.hasNext()) {
            ((IntConsumer) it.next()).accept(i2);
        }
        this.mHandler.post(
                new SemBiometricDisplayStateMonitor$$ExternalSyntheticLambda1(this, i, i2, i3, 1));
    }

    public final void registerStateCallback(DisplayStateCallback displayStateCallback) {
        if (this.mDisplayStateCallbacks.contains(displayStateCallback)) {
            return;
        }
        this.mDisplayStateCallbacks.add(displayStateCallback);
    }
}
