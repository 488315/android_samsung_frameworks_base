package com.android.systemui.vibrate;

import android.os.Vibrator;

public final class VibratorNone extends VibratorType {
    @Override // com.android.systemui.vibrate.VibratorType
    public final void setVibrator(Vibrator vibrator) {
    }

    @Override // com.android.systemui.vibrate.VibratorType
    public final void playVibration(VibrationUtil vibrationUtil, int i) {
    }
}
