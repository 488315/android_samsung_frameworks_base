package com.android.systemui.vibrate;

import android.os.Vibrator;

public abstract class VibratorType {
    public static VibratorType create(int i) {
        return i != 1 ? i != 2 ? new VibratorNone() : new VibratorCoinDC() : new VibratorLinear();
    }

    public abstract void playVibration(VibrationUtil vibrationUtil, int i);

    public abstract void setVibrator(Vibrator vibrator);
}
