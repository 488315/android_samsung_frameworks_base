package com.android.settingslib.animation;

import android.R;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.settingslib.animation.AppearAnimationUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DisappearAnimationUtils extends AppearAnimationUtils {
    public static final C08821 ROW_TRANSLATION_SCALER = new AppearAnimationUtils.RowTranslationScaler() { // from class: com.android.settingslib.animation.DisappearAnimationUtils.1
    };

    public DisappearAnimationUtils(Context context) {
        this(context, 220L, 1.0f, 1.0f, AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_linear_in));
    }

    @Override // com.android.settingslib.animation.AppearAnimationUtils
    public final long calculateDelay(int i, int i2) {
        return (long) ((((Math.pow(i, 0.4d) + 0.4d) * i2 * 10.0d) + (i * 60)) * this.mDelayScale);
    }

    public DisappearAnimationUtils(Context context, long j, float f, float f2, Interpolator interpolator) {
        this(context, j, f, f2, interpolator, ROW_TRANSLATION_SCALER);
    }

    public DisappearAnimationUtils(Context context, long j, float f, float f2, Interpolator interpolator, AppearAnimationUtils.RowTranslationScaler rowTranslationScaler) {
        super(context, j, f, f2, interpolator);
        this.mRowTranslationScaler = rowTranslationScaler;
        this.mAppearing = false;
    }
}
