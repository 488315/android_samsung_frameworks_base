package com.android.systemui.vibrate;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;

/* loaded from: classes3.dex */
public class VibratorLinear extends VibratorType {
    public Vibrator mVibrator;

    @Override // com.android.systemui.vibrate.VibratorType
    public final void playVibration(VibrationUtil vibrationUtil, int i) {
        int iSemGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(i);
        vibrationUtil.getClass();
        this.mVibrator.vibrate(VibrationEffect.semCreateHaptic(iSemGetVibrationIndex, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
    }

    @Override // com.android.systemui.vibrate.VibratorType
    public final void setVibrator(Vibrator vibrator) {
        this.mVibrator = vibrator;
    }
}
