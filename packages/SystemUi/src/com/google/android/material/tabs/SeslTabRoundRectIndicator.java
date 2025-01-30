package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SeslTabRoundRectIndicator extends SeslAbsIndicatorView {
    public final Interpolator LINEAR_INTERPOLATOR;
    public final PathInterpolator SCALE_INTERPOLATOR;
    public AnimationSet mPressAnimationSet;

    public SeslTabRoundRectIndicator(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public final void onHide() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
        alphaAnimation.setDuration(0L);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
        setAlpha(0.0f);
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public final void onSetSelectedIndicatorColor(int i) {
        if (getBackground() instanceof NinePatchDrawable) {
            return;
        }
        getBackground().setTint(i);
        if (isSelected()) {
            return;
        }
        onHide();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public final void onShow() {
        setAlpha(1.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        alphaAnimation.setDuration(0L);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0 || isSelected()) {
            return;
        }
        onHide();
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public final void startPressEffect() {
        setAlpha(1.0f);
        AnimationSet animationSet = new AnimationSet(false);
        this.mPressAnimationSet = animationSet;
        animationSet.setFillAfter(true);
        this.mPressAnimationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.google.android.material.tabs.SeslTabRoundRectIndicator.1
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                SeslTabRoundRectIndicator.this.mPressAnimationSet = null;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(250L);
        scaleAnimation.setInterpolator(this.SCALE_INTERPOLATOR);
        scaleAnimation.setFillAfter(true);
        this.mPressAnimationSet.addAnimation(scaleAnimation);
        if (!isSelected()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(100L);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(this.LINEAR_INTERPOLATOR);
            this.mPressAnimationSet.addAnimation(alphaAnimation);
        }
        startAnimation(this.mPressAnimationSet);
    }

    @Override // com.google.android.material.tabs.SeslAbsIndicatorView
    public final void startReleaseEffect() {
        setAlpha(1.0f);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(350L);
        scaleAnimation.setInterpolator(this.SCALE_INTERPOLATOR);
        scaleAnimation.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation);
        startAnimation(animationSet);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.LINEAR_INTERPOLATOR = new LinearInterpolator();
        this.SCALE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        boolean isLightTheme = SeslMisc.isLightTheme(context);
        int i3 = isLightTheme ? R.drawable.sesl_tablayout_subtab_indicator_background_light : R.drawable.sesl_tablayout_subtab_indicator_background_dark;
        Object obj = ContextCompat.sLock;
        Drawable drawable = context.getDrawable(i3);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, drawable);
        onSetSelectedIndicatorColor(getResources().getColor(isLightTheme ? R.color.sesl_tablayout_subtab_background_stroke_color_light : R.color.sesl_tablayout_subtab_background_stroke_color_dark));
    }
}
