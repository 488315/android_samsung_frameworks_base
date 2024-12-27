package com.android.server.vibrator;

import android.os.VibrationEffect;

public final /* synthetic */ class HalVibration$$ExternalSyntheticLambda0
        implements VibrationEffect.Transformation {
    public final VibrationEffect transform(VibrationEffect vibrationEffect, Object obj) {
        return vibrationEffect.resolve(((Integer) obj).intValue());
    }
}
