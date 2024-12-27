package com.android.server.accessibility.magnification;

import android.content.ContentResolver;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FullScreenMagnificationVibrationHelper {
    public final ContentResolver mContentResolver;
    public final Vibrator mVibrator;
    public final VibrationEffect mVibrationEffect = VibrationEffect.get(0);
    VibrationEffectSupportedProvider mIsVibrationEffectSupportedProvider =
            new VibrationEffectSupportedProvider() { // from class:
                                                     // com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper$$ExternalSyntheticLambda0
                @Override // com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper.VibrationEffectSupportedProvider
                public final boolean isVibrationEffectSupported() {
                    Vibrator vibrator = FullScreenMagnificationVibrationHelper.this.mVibrator;
                    return vibrator != null && vibrator.areAllEffectsSupported(0) == 1;
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface VibrationEffectSupportedProvider {
        boolean isVibrationEffectSupported();
    }

    public FullScreenMagnificationVibrationHelper(Context context) {
        this.mContentResolver = context.getContentResolver();
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
    }
}
