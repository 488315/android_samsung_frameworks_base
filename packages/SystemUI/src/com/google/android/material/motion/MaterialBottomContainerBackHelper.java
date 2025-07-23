package com.google.android.material.motion;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialBottomContainerBackHelper extends MaterialBackAnimationHelper {
    public final float maxScaleXDistance;
    public final float maxScaleYDistance;

    public MaterialBottomContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistance = resources.getDimension(R.dimen.m3_back_progress_bottom_container_max_scale_x_distance);
        this.maxScaleYDistance = resources.getDimension(R.dimen.m3_back_progress_bottom_container_max_scale_y_distance);
    }

    public final Animator createResetScaleAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.view, (Property<View, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(this.view, (Property<View, Float>) View.SCALE_Y, 1.0f));
        View view = this.view;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                animatorSet.playTogether(ObjectAnimator.ofFloat(viewGroup.getChildAt(i), (Property<View, Float>) View.SCALE_Y, 1.0f));
            }
        }
        animatorSet.setInterpolator(new FastOutSlowInInterpolator());
        return animatorSet;
    }

    public void updateBackProgress(float f) {
        float interpolation = this.progressInterpolator.getInterpolation(f);
        float width = this.view.getWidth();
        float height = this.view.getHeight();
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        float f2 = this.maxScaleXDistance / width;
        float f3 = this.maxScaleYDistance / height;
        float lerp = 1.0f - AnimationUtils.lerp(0.0f, f2, interpolation);
        float lerp2 = 1.0f - AnimationUtils.lerp(0.0f, f3, interpolation);
        this.view.setScaleX(lerp);
        this.view.setPivotY(height);
        this.view.setScaleY(lerp2);
        View view = this.view;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                childAt.setPivotY(-childAt.getTop());
                childAt.setScaleY(lerp2 != 0.0f ? lerp / lerp2 : 1.0f);
            }
        }
    }
}
