package android.hardware.input;

import android.app.ActivityThread;
import android.content.Context;
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

final class InputDeviceVibrator extends Vibrator {
    private static final String TAG = "InputDeviceVibrator";
    private final int mDeviceId;
    private final VibratorInfo mVibratorInfo;
    private final ArrayMap<
                    Vibrator.OnVibratorStateChangedListener, OnVibratorStateChangedListenerDelegate>
            mDelegates = new ArrayMap<>();
    private final InputManagerGlobal mGlobal = InputManagerGlobal.getInstance();
    private final Binder mToken = new Binder();

    InputDeviceVibrator(int deviceId, int vibratorId) {
        this.mDeviceId = deviceId;
        this.mVibratorInfo =
                new VibratorInfo.Builder(vibratorId)
                        .setCapabilities(4L)
                        .setSupportedEffects(new int[0])
                        .setSupportedBraking(new int[0])
                        .build();
    }

    class OnVibratorStateChangedListenerDelegate extends IVibratorStateListener.Stub {
        private final Executor mExecutor;
        private final Vibrator.OnVibratorStateChangedListener mListener;

        OnVibratorStateChangedListenerDelegate(
                Vibrator.OnVibratorStateChangedListener listener, Executor executor) {
            this.mExecutor = executor;
            this.mListener = listener;
        }

        public /* synthetic */ void lambda$onVibrating$0(boolean isVibrating) {
            this.mListener.onVibratorStateChanged(isVibrating);
        }

        @Override // android.os.IVibratorStateListener
        public void onVibrating(final boolean isVibrating) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.input.InputDeviceVibrator$OnVibratorStateChangedListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InputDeviceVibrator.OnVibratorStateChangedListenerDelegate.this
                                    .lambda$onVibrating$0(isVibrating);
                        }
                    });
        }
    }

    @Override // android.os.Vibrator
    public VibratorInfo getInfo() {
        return this.mVibratorInfo;
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        return true;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        return this.mGlobal.isVibrating(this.mDeviceId);
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(Vibrator.OnVibratorStateChangedListener listener) {
        Preconditions.checkNotNull(listener);
        Context context = ActivityThread.currentApplication();
        addVibratorStateListener(context.getMainExecutor(), listener);
    }

    @Override // android.os.Vibrator
    public void addVibratorStateListener(
            Executor executor, Vibrator.OnVibratorStateChangedListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        synchronized (this.mDelegates) {
            if (this.mDelegates.containsKey(listener)) {
                Log.w(TAG, "Listener already registered.");
                return;
            }
            OnVibratorStateChangedListenerDelegate delegate =
                    new OnVibratorStateChangedListenerDelegate(listener, executor);
            if (!this.mGlobal.registerVibratorStateListener(this.mDeviceId, delegate)) {
                Log.w(TAG, "Failed to register vibrate state listener");
            } else {
                this.mDelegates.put(listener, delegate);
            }
        }
    }

    @Override // android.os.Vibrator
    public void removeVibratorStateListener(Vibrator.OnVibratorStateChangedListener listener) {
        Preconditions.checkNotNull(listener);
        synchronized (this.mDelegates) {
            if (this.mDelegates.containsKey(listener)) {
                OnVibratorStateChangedListenerDelegate delegate = this.mDelegates.get(listener);
                if (!this.mGlobal.unregisterVibratorStateListener(this.mDeviceId, delegate)) {
                    Log.w(TAG, "Failed to unregister vibrate state listener");
                    return;
                }
                this.mDelegates.remove(listener);
            }
        }
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return this.mVibratorInfo.hasCapability(4L);
    }

    @Override // android.os.Vibrator
    public void vibrate(
            int uid,
            String opPkg,
            VibrationEffect effect,
            String reason,
            VibrationAttributes attributes) {
        this.mGlobal.vibrate(this.mDeviceId, effect, this.mToken);
    }

    @Override // android.os.Vibrator
    public void cancel() {
        this.mGlobal.cancelVibrate(this.mDeviceId, this.mToken);
    }

    @Override // android.os.Vibrator
    public void cancel(int usageFilter) {
        cancel();
    }
}
