package com.android.systemui.vibrate;

import android.os.Vibrator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VibratorType {
    public static VibratorType create(int i) {
        return i != 1 ? i != 2 ? new VibratorNone() : new VibratorCoinDC() : new VibratorLinear();
    }

    public abstract void playVibration(VibrationUtil vibrationUtil, int i);

    public abstract void setVibrator(Vibrator vibrator);
}
