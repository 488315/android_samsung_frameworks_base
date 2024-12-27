package com.android.systemui.vibrate;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;

public final class VibratorCoinDC extends VibratorType {
    public Vibrator mVibrator;

    @Override // com.android.systemui.vibrate.VibratorType
    public final void playVibration(VibrationUtil vibrationUtil, int i) {
        int semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(100);
        vibrationUtil.getClass();
        this.mVibrator.vibrate(VibrationEffect.semCreateWaveform(semGetVibrationIndex, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
    }

    @Override // com.android.systemui.vibrate.VibratorType
    public final void setVibrator(Vibrator vibrator) {
        this.mVibrator = vibrator;
    }
}
