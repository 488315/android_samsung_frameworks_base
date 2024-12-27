package com.android.systemui.vibrate;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
