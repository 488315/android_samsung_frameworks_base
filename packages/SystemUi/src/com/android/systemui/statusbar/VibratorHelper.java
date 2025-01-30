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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VibratorHelper {
    public final Executor mExecutor;
    public final SettingsHelper mSettingsHelper;
    public final Vibrator mVibrator;
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public static final VibrationEffect BIOMETRIC_SUCCESS_VIBRATION_EFFECT = VibrationEffect.get(0);
    public static final VibrationEffect BIOMETRIC_ERROR_VIBRATION_EFFECT = VibrationEffect.get(1);
    public static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(50);

    public VibratorHelper(Vibrator vibrator, SettingsHelper settingsHelper) {
        this(vibrator, Executors.newSingleThreadExecutor(), settingsHelper);
    }

    public final void cancel() {
        if (hasVibrator()) {
            final Vibrator vibrator = this.mVibrator;
            Objects.requireNonNull(vibrator);
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda2
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

    public final void vibrate(final int i) {
        if (hasVibrator()) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VibratorHelper vibratorHelper = VibratorHelper.this;
                    int i2 = i;
                    vibratorHelper.getClass();
                    vibratorHelper.mVibrator.vibrate(VibrationEffect.get(i2, false), VibratorHelper.TOUCH_VIBRATION_ATTRIBUTES);
                }
            });
        }
    }

    public final void vibrateButton() {
        if (this.mSettingsHelper.isHapticFeedbackEnabled()) {
            vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        }
    }

    public final void vibrateGesture() {
        SettingsHelper settingsHelper = this.mSettingsHelper;
        settingsHelper.getClass();
        if ((BasicRune.NAVBAR_GESTURE && settingsHelper.mItemLists.get("navigation_gestures_vibrate").getIntValue() == 0) ? false : true) {
            vibrate(VibrationEffect.semCreateWaveform(isSupportDCMotorHapticFeedback() ? HapticFeedbackConstants.semGetVibrationIndex(100) : HapticFeedbackConstants.semGetVibrationIndex(23), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        }
    }

    public VibratorHelper(Vibrator vibrator, Executor executor, SettingsHelper settingsHelper) {
        this.mExecutor = executor;
        this.mVibrator = vibrator;
        this.mSettingsHelper = settingsHelper;
    }

    public final void vibrate(final int i, final String str, final VibrationEffect vibrationEffect, final String str2, final VibrationAttributes vibrationAttributes) {
        if (hasVibrator()) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    VibratorHelper vibratorHelper = VibratorHelper.this;
                    vibratorHelper.mVibrator.vibrate(i, str, vibrationEffect, str2, vibrationAttributes);
                }
            });
        }
    }

    public final void vibrate(final VibrationEffect vibrationEffect, final AudioAttributes audioAttributes) {
        if (hasVibrator()) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VibratorHelper vibratorHelper = VibratorHelper.this;
                    vibratorHelper.mVibrator.vibrate(vibrationEffect, audioAttributes);
                }
            });
        }
    }

    public final void vibrate(final VibrationEffect vibrationEffect) {
        if (hasVibrator()) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    VibratorHelper vibratorHelper = VibratorHelper.this;
                    vibratorHelper.mVibrator.vibrate(vibrationEffect);
                }
            });
        }
    }
}
