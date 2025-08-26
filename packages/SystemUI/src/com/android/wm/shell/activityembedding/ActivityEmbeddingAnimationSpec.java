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
        int changeResId;
        if (animationOptions == null || animationOptions.getType() != 1) {
            return null;
        }
        if (TransitionUtil.isOpeningType(i)) {
            changeResId = animationOptions.getEnterResId();
        } else if (TransitionUtil.isClosingType(i)) {
            changeResId = animationOptions.getExitResId();
        } else if (i == 6) {
            changeResId = animationOptions.getChangeResId();
        } else {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Unknown transit type:", "ActivityEmbeddingAnimSpec");
            changeResId = -1;
        }
        if (changeResId == -1) {
            return null;
        }
        Animation animationLoadDefaultAnimationRes = this.mTransitionAnimation.loadDefaultAnimationRes(changeResId);
        return animationLoadDefaultAnimationRes != null ? animationLoadDefaultAnimationRes : new AlphaAnimation(1.0f, 1.0f);
    }
}
