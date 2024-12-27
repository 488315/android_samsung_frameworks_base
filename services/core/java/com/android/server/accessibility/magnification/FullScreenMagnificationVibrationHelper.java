package com.android.server.accessibility.magnification;

import android.content.ContentResolver;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

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

    interface VibrationEffectSupportedProvider {
        boolean isVibrationEffectSupported();
    }

    public FullScreenMagnificationVibrationHelper(Context context) {
        this.mContentResolver = context.getContentResolver();
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
    }
}
