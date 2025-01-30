package com.android.systemui.vibrate;

import android.os.Vibrator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class VibratorType {
    public static VibratorType create(int i) {
        return i != 1 ? i != 2 ? new VibratorNone() : new VibratorCoinDC() : new VibratorLinear();
    }

    public abstract void playVibration(VibrationUtil vibrationUtil, int i);

    public abstract void setVibrator(Vibrator vibrator);
}
