package android.hardware.input;

import android.app.ActivityThread;
import android.hardware.input.InputDeviceVibrator;
import android.os.Binder;
import android.os.IVibratorStateListener;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorInfo;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
final class InputDeviceVibrator extends Vibrator {
    private static final String TAG = "InputDeviceVibrator";
    private final int mDeviceId;
    private final VibratorInfo mVibratorInfo;
    private final ArrayMap<Vibrator.OnVibratorStateChangedListener, OnVibratorStateChangedListenerDelegate> mDelegates = new ArrayMap<>();
    private final InputManagerGlobal mGlobal = InputManagerGlobal.getInstance();
    private final Binder mToken = new Binder();

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        return true;
    }

    InputDeviceVibrator(int i, int i2) {
        this.mDeviceId = i;
        this.mVibratorInfo = new VibratorInfo.Builder(i2).setCapabilities(4L).setSupportedEffects(new int[0]).setSupportedBraking(new int[0]).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class OnVibratorStateChangedListenerDelegate extends IVibratorStateListener.Stub {
        private final Executor mExecutor;
        private final Vibrator.OnVibratorStateChangedListener mListener;

        OnVibratorStateChangedListenerDelegate(InputDeviceVibrator inputDeviceVibrator, Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener, Executor executor) {
            this.mExecutor = executor;
            this.mListener = onVibratorStateChangedListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVibrating$0(boolean z) {
            this.mListener.onVibratorStateChanged(z);
        }

        @Override // android.os.IVibratorStateListener
        public void onVibrating(final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.input.InputDeviceVibrator$OnVibratorStateChangedListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputDeviceVibrator.OnVibratorStateChangedListenerDelegate.this.lambda$onVibrating$0(z);
                }
            });
        }
    }

    @Override // android.os.Vibrator
    public VibratorInfo getInfo() {
        return this.mVibratorInfo;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        return this.mGlobal.isVibrating(this.mDeviceId);
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        Preconditions.checkNotNull(onVibratorStateChangedListener);
        addVibratorStateListener(ActivityThread.currentApplication().getMainExecutor(), onVibratorStateChangedListener);
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Executor executor, Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        Preconditions.checkNotNull(onVibratorStateChangedListener);
        Preconditions.checkNotNull(executor);
        synchronized (this.mDelegates) {
            if (this.mDelegates.containsKey(onVibratorStateChangedListener)) {
                Log.w(TAG, "Listener already registered.");
                return;
            }
            OnVibratorStateChangedListenerDelegate onVibratorStateChangedListenerDelegate = new OnVibratorStateChangedListenerDelegate(this, onVibratorStateChangedListener, executor);
            if (!this.mGlobal.registerVibratorStateListener(this.mDeviceId, onVibratorStateChangedListenerDelegate)) {
                Log.w(TAG, "Failed to register vibrate state listener");
            } else {
                this.mDelegates.put(onVibratorStateChangedListener, onVibratorStateChangedListenerDelegate);
            }
        }
    }

    @Override // android.os.Vibrator
    public void removeVibratorStateListener(Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
        Preconditions.checkNotNull(onVibratorStateChangedListener);
        synchronized (this.mDelegates) {
            if (this.mDelegates.containsKey(onVibratorStateChangedListener)) {
                if (!this.mGlobal.unregisterVibratorStateListener(this.mDeviceId, this.mDelegates.get(onVibratorStateChangedListener))) {
                    Log.w(TAG, "Failed to unregister vibrate state listener");
                    return;
                }
                this.mDelegates.remove(onVibratorStateChangedListener);
            }
        }
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return this.mVibratorInfo.hasCapability(4L);
    }

    @Override // android.os.Vibrator
    public void vibrate(int i, String str, VibrationEffect vibrationEffect, String str2, VibrationAttributes vibrationAttributes) {
        this.mGlobal.vibrate(this.mDeviceId, vibrationEffect, this.mToken);
    }

    @Override // android.os.Vibrator
    public void cancel() {
        this.mGlobal.cancelVibrate(this.mDeviceId, this.mToken);
    }

    @Override // android.os.Vibrator
    public void cancel(int i) {
        cancel();
    }
}
