package com.google.android.material.motion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Property;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.ViewUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialMainContainerBackHelper extends MaterialBackAnimationHelper {
    public Integer expandedCornerSize;
    public Rect initialHideFromClipBounds;
    public Rect initialHideToClipBounds;
    public float initialTouchY;
    public final float maxTranslationY;
    public final float minEdgeGap;

    public MaterialMainContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.minEdgeGap = resources.getDimension(R.dimen.m3_back_progress_main_container_min_edge_gap);
        this.maxTranslationY = resources.getDimension(R.dimen.m3_back_progress_main_container_max_translation_y);
    }

    public final AnimatorSet createResetScaleAndTranslationAnimator(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.view, (Property<View, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(this.view, (Property<View, Float>) View.SCALE_Y, 1.0f), ObjectAnimator.ofFloat(this.view, (Property<View, Float>) View.TRANSLATION_X, 0.0f), ObjectAnimator.ofFloat(this.view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f));
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.motion.MaterialMainContainerBackHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                View view2 = view;
                if (view2 != null) {
                    view2.setVisibility(0);
                }
            }
        });
        return animatorSet;
    }

    public final int getExpandedCornerSize() {
        WindowInsets rootWindowInsets;
        if (this.expandedCornerSize == null) {
            int[] iArr = new int[2];
            this.view.getLocationOnScreen(iArr);
            if (iArr[1] == 0 && (rootWindowInsets = this.view.getRootWindowInsets()) != null) {
                RoundedCorner roundedCorner = rootWindowInsets.getRoundedCorner(0);
                int radius = roundedCorner != null ? roundedCorner.getRadius() : 0;
                RoundedCorner roundedCorner2 = rootWindowInsets.getRoundedCorner(1);
                int max = Math.max(radius, roundedCorner2 != null ? roundedCorner2.getRadius() : 0);
                RoundedCorner roundedCorner3 = rootWindowInsets.getRoundedCorner(3);
                int radius2 = roundedCorner3 != null ? roundedCorner3.getRadius() : 0;
                RoundedCorner roundedCorner4 = rootWindowInsets.getRoundedCorner(2);
                r3 = Math.max(max, Math.max(radius2, roundedCorner4 != null ? roundedCorner4.getRadius() : 0));
            }
            this.expandedCornerSize = Integer.valueOf(r3);
        }
        return this.expandedCornerSize.intValue();
    }

    public void startBackProgress(float f, View view) {
        View view2 = this.view;
        this.initialHideToClipBounds = new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
        if (view != null) {
            this.initialHideFromClipBounds = ViewUtils.calculateOffsetRectFromBounds(this.view, view);
        }
        this.initialTouchY = f;
    }

    public void updateBackProgress(float f, boolean z, float f2, float f3) {
        float interpolation = this.progressInterpolator.getInterpolation(f);
        float width = this.view.getWidth();
        float height = this.view.getHeight();
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        float lerp = AnimationUtils.lerp(1.0f, 0.9f, interpolation);
        float m = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(width, 0.9f, width, 2.0f);
        float f4 = this.minEdgeGap;
        float lerp2 = AnimationUtils.lerp(0.0f, Math.max(0.0f, m - f4), interpolation) * (z ? 1 : -1);
        float min = Math.min(Math.max(0.0f, ((height - (lerp * height)) / 2.0f) - f4), this.maxTranslationY);
        float f5 = f2 - this.initialTouchY;
        float lerp3 = AnimationUtils.lerp(0.0f, min, Math.abs(f5) / height) * Math.signum(f5);
        this.view.setScaleX(lerp);
        this.view.setScaleY(lerp);
        this.view.setTranslationX(lerp2);
        this.view.setTranslationY(lerp3);
        View view = this.view;
        if (view instanceof ClippableRoundedCornerLayout) {
            ((ClippableRoundedCornerLayout) view).updateClipBoundsAndCornerRadius(r0.getLeft(), r0.getTop(), r0.getRight(), r0.getBottom(), AnimationUtils.lerp(getExpandedCornerSize(), f3, interpolation));
        }
    }
}
