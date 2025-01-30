package com.android.systemui.vibrate;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
