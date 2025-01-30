package com.android.p038wm.shell.activityembedding;

import android.R;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.android.internal.policy.TransitionAnimation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivityEmbeddingAnimationSpec {
    public final Interpolator mFastOutExtraSlowInInterpolator;
    public final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    public final TransitionAnimation mTransitionAnimation;
    public float mTransitionAnimationScaleSetting;

    public ActivityEmbeddingAnimationSpec(Context context) {
        this.mTransitionAnimation = new TransitionAnimation(context, false, "ActivityEmbeddingAnimSpec");
        this.mFastOutExtraSlowInInterpolator = AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_extra_slow_in);
    }
}
