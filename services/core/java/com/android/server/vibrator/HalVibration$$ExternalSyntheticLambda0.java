package com.android.server.vibrator;

import android.os.VibrationEffect;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class HalVibration$$ExternalSyntheticLambda0 implements VibrationEffect.Transformation {
    public final VibrationEffect transform(VibrationEffect vibrationEffect, Object obj) {
        return vibrationEffect.resolve(((Integer) obj).intValue());
    }
}
