package com.android.systemui.statusbar;

import android.media.AudioAttributes;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import com.android.systemui.BasicRune;
import com.android.systemui.util.SettingsHelper;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class VibratorHelper {
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public final Executor mExecutor;
    private SettingsHelper mSettingsHelper;
    public final Vibrator mVibrator;

    static {
        VibrationEffect.get(0);
        VibrationEffect.get(1);
        VibrationAttributes.createForUsage(50);
    }

    public VibratorHelper(Vibrator vibrator, SettingsHelper settingsHelper) {
        this(vibrator, Executors.newSingleThreadExecutor(), settingsHelper);
    }

    public final void cancel() {
        if (hasVibrator()) {
            Executor executor = this.mExecutor;
            final Vibrator vibrator = this.mVibrator;
            Objects.requireNonNull(vibrator);
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    vibrator.cancel();
                }
            });
        }
    }

    public final boolean hasVibrator() {
        Vibrator vibrator = this.mVibrator;
        return vibrator != null && vibrator.hasVibrator();
    }

    public final boolean isSupportDCMotorHapticFeedback() {
        return BasicRune.NAVBAR_DC_MOTOR_HAPTIC_FEEDBACK && this.mVibrator.semGetSupportedVibrationType() == 1;
    }

    public final void vibrate(VibrationEffect vibrationEffect, AudioAttributes audioAttributes) {
        if (hasVibrator()) {
            this.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda1(this, vibrationEffect, audioAttributes));
        }
    }

    public final void vibrateButton() {
        if (this.mSettingsHelper.isHapticFeedbackEnabled()) {
            vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        }
    }

    public final void vibrateGesture() {
        if (this.mSettingsHelper.isGestureVibrationEnabled()) {
            vibrate(VibrationEffect.semCreateWaveform(isSupportDCMotorHapticFeedback() ? HapticFeedbackConstants.semGetVibrationIndex(100) : HapticFeedbackConstants.semGetVibrationIndex(23), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        }
    }

    public VibratorHelper(Vibrator vibrator, Executor executor, SettingsHelper settingsHelper) {
        this.mExecutor = executor;
        this.mVibrator = vibrator;
        this.mSettingsHelper = settingsHelper;
    }

    public final void vibrate(final VibrationEffect vibrationEffect) {
        if (hasVibrator()) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VibratorHelper vibratorHelper = VibratorHelper.this;
                    vibratorHelper.mVibrator.vibrate(vibrationEffect);
                }
            });
        }
    }
}
