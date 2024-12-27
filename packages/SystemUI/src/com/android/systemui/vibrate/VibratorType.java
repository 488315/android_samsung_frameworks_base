package com.android.systemui.vibrate;

import android.os.Vibrator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class VibratorType {
    public static VibratorType create(int i) {
        return i != 1 ? i != 2 ? new VibratorNone() : new VibratorCoinDC() : new VibratorLinear();
    }

    public abstract void playVibration(VibrationUtil vibrationUtil, int i);

    public abstract void setVibrator(Vibrator vibrator);
}
