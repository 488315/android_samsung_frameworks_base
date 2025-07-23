package com.android.wm.shell.activityembedding;

import android.R;
import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.window.TransitionInfo;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.shared.TransitionUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ActivityEmbeddingAnimationSpec {
    public final Interpolator mFastOutExtraSlowInInterpolator;
    public final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    public final TransitionAnimation mTransitionAnimation;
    public float mTransitionAnimationScaleSetting;

    public ActivityEmbeddingAnimationSpec(Context context) {
        this.mTransitionAnimation = new TransitionAnimation(context, false, "ActivityEmbeddingAnimSpec");
        this.mFastOutExtraSlowInInterpolator = AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_extra_slow_in);
    }

    public final Animation loadCustomAnimation(TransitionInfo.AnimationOptions animationOptions, int i) {
        int i2;
        if (animationOptions == null || animationOptions.getType() != 1) {
            return null;
        }
        if (TransitionUtil.isOpeningType(i)) {
            i2 = animationOptions.getEnterResId();
        } else if (TransitionUtil.isClosingType(i)) {
            i2 = animationOptions.getExitResId();
        } else if (i == 6) {
            i2 = animationOptions.getChangeResId();
        } else {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Unknown transit type:", "ActivityEmbeddingAnimSpec");
            i2 = -1;
        }
        if (i2 == -1) {
            return null;
        }
        Animation loadDefaultAnimationRes = this.mTransitionAnimation.loadDefaultAnimationRes(i2);
        return loadDefaultAnimationRes != null ? loadDefaultAnimationRes : new AlphaAnimation(1.0f, 1.0f);
    }
}
