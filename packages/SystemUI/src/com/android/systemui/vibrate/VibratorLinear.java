package com.android.systemui.vibrate;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;

public final class VibratorLinear extends VibratorType {
    public Vibrator mVibrator;

    @Override // com.android.systemui.vibrate.VibratorType
    public final void playVibration(VibrationUtil vibrationUtil, int i) {
        int semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(i);
        vibrationUtil.getClass();
        this.mVibrator.vibrate(VibrationEffect.semCreateHaptic(semGetVibrationIndex, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
    }

    @Override // com.android.systemui.vibrate.VibratorType
    public final void setVibrator(Vibrator vibrator) {
        this.mVibrator = vibrator;
    }
}
