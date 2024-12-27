package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.container.NotificationSpotlightEffect;

public final class SpotLightEffectView extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public NotificationSpotlightEffect.AnonymousClass1 mAnimListener;
    public AnimatorSet mAnimatorSet;
    public final AnonymousClass2 mAnimtorListener;
    public int mCurrWidth;
    public final Drawable mSpotlightDrawable;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.edgelighting.effect.view.SpotLightEffectView$2] */
    public SpotLightEffectView(Context context) {
        super(context);
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.SpotLightEffectView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                NotificationSpotlightEffect.AnonymousClass1 anonymousClass1 = SpotLightEffectView.this.mAnimListener;
                if (anonymousClass1 != null) {
                    NotificationSpotlightEffect notificationSpotlightEffect = NotificationSpotlightEffect.this;
                    if (notificationSpotlightEffect.mSpotlightEffect != null) {
                        notificationSpotlightEffect.resetScreenSize();
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) notificationSpotlightEffect.mSpotlightEffect.getLayoutParams();
                        layoutParams.setMarginStart((notificationSpotlightEffect.mScreenWidth - 2699) / 2);
                        notificationSpotlightEffect.mSpotlightEffect.setLayoutParams(layoutParams);
                    }
                }
                SpotLightEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.setScaleX(0.63f);
                spotLightEffectView.setScaleY(0.63f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.getClass();
            }
        };
        Drawable drawable = getContext().getDrawable(R.drawable.edge_spotlight_bg);
        this.mSpotlightDrawable = drawable;
        setBackground(drawable);
    }

    public final void startAnimation() {
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mAnimatorSet = new AnimatorSet();
        setPivotX(this.mCurrWidth / 2.0f);
        setPivotY(0.0f);
        this.mAnimatorSet.addListener(this.mAnimtorListener);
        AnimatorSet animatorSet2 = this.mAnimatorSet;
        AnimatorSet animatorSet3 = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.SpotLightEffectView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SpotLightEffectView.this.mSpotlightDrawable.setAlpha((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f));
            }
        });
        animatorSet3.setDuration(100L);
        animatorSet3.setInterpolator(new PathInterpolator(0.33f, 0.36f, 0.67f, 1.0f));
        animatorSet3.play(ofFloat);
        AnimatorSet animatorSet4 = new AnimatorSet();
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleX", 0.63f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this, "scaleY", 0.63f, 1.0f);
        animatorSet4.setDuration(1167L);
        animatorSet4.setInterpolator(new PathInterpolator(0.33f, 0.36f, 0.67f, 1.0f));
        animatorSet4.playTogether(ofFloat2, ofFloat3);
        AnimatorSet animatorSet5 = new AnimatorSet();
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.93f);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.93f);
        animatorSet5.setStartDelay(1167L);
        animatorSet5.setDuration(572L);
        animatorSet5.setInterpolator(new PathInterpolator(0.26f, 0.0f, 0.65f, 1.0f));
        animatorSet5.playTogether(ofFloat4, ofFloat5);
        AnimatorSet animatorSet6 = new AnimatorSet();
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this, "scaleX", 0.93f, 0.98f);
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(this, "scaleY", 0.93f, 0.98f);
        animatorSet6.setStartDelay(1733L);
        animatorSet6.setDuration(583L);
        animatorSet6.setInterpolator(new PathInterpolator(0.32f, 0.0f, 0.66f, 0.79f));
        animatorSet6.playTogether(ofFloat6, ofFloat7);
        AnimatorSet animatorSet7 = new AnimatorSet();
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(this, "scaleX", 0.98f, 0.93f);
        ObjectAnimator ofFloat9 = ObjectAnimator.ofFloat(this, "scaleY", 0.98f, 0.93f);
        animatorSet7.setStartDelay(2333L);
        animatorSet7.setDuration(572L);
        animatorSet7.setInterpolator(new PathInterpolator(0.33f, -0.2f, 0.67f, 1.09f));
        animatorSet7.playTogether(ofFloat8, ofFloat9);
        AnimatorSet animatorSet8 = new AnimatorSet();
        ObjectAnimator ofFloat10 = ObjectAnimator.ofFloat(this, "scaleX", 0.93f, 0.54f);
        ObjectAnimator ofFloat11 = ObjectAnimator.ofFloat(this, "scaleY", 0.93f, 0.54f);
        animatorSet8.setStartDelay(2900L);
        animatorSet8.setDuration(607L);
        animatorSet8.setInterpolator(new PathInterpolator(0.35f, -0.01f, 0.67f, 1.0f));
        animatorSet8.playTogether(ofFloat10, ofFloat11);
        animatorSet2.playTogether(animatorSet3, animatorSet4, animatorSet5, animatorSet6, animatorSet7, animatorSet8);
        this.mAnimatorSet.start();
    }

    public final void stopAnimation() {
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mAnimatorSet = null;
        }
        invalidate();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.edgelighting.effect.view.SpotLightEffectView$2] */
    public SpotLightEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.SpotLightEffectView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                NotificationSpotlightEffect.AnonymousClass1 anonymousClass1 = SpotLightEffectView.this.mAnimListener;
                if (anonymousClass1 != null) {
                    NotificationSpotlightEffect notificationSpotlightEffect = NotificationSpotlightEffect.this;
                    if (notificationSpotlightEffect.mSpotlightEffect != null) {
                        notificationSpotlightEffect.resetScreenSize();
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) notificationSpotlightEffect.mSpotlightEffect.getLayoutParams();
                        layoutParams.setMarginStart((notificationSpotlightEffect.mScreenWidth - 2699) / 2);
                        notificationSpotlightEffect.mSpotlightEffect.setLayoutParams(layoutParams);
                    }
                }
                SpotLightEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.setScaleX(0.63f);
                spotLightEffectView.setScaleY(0.63f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.getClass();
            }
        };
        Drawable drawable = getContext().getDrawable(R.drawable.edge_spotlight_bg);
        this.mSpotlightDrawable = drawable;
        setBackground(drawable);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.edgelighting.effect.view.SpotLightEffectView$2] */
    public SpotLightEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.SpotLightEffectView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                NotificationSpotlightEffect.AnonymousClass1 anonymousClass1 = SpotLightEffectView.this.mAnimListener;
                if (anonymousClass1 != null) {
                    NotificationSpotlightEffect notificationSpotlightEffect = NotificationSpotlightEffect.this;
                    if (notificationSpotlightEffect.mSpotlightEffect != null) {
                        notificationSpotlightEffect.resetScreenSize();
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) notificationSpotlightEffect.mSpotlightEffect.getLayoutParams();
                        layoutParams.setMarginStart((notificationSpotlightEffect.mScreenWidth - 2699) / 2);
                        notificationSpotlightEffect.mSpotlightEffect.setLayoutParams(layoutParams);
                    }
                }
                SpotLightEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i2 = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.setScaleX(0.63f);
                spotLightEffectView.setScaleY(0.63f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i2 = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.getClass();
            }
        };
        Drawable drawable = getContext().getDrawable(R.drawable.edge_spotlight_bg);
        this.mSpotlightDrawable = drawable;
        setBackground(drawable);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.edgelighting.effect.view.SpotLightEffectView$2] */
    public SpotLightEffectView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.SpotLightEffectView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                NotificationSpotlightEffect.AnonymousClass1 anonymousClass1 = SpotLightEffectView.this.mAnimListener;
                if (anonymousClass1 != null) {
                    NotificationSpotlightEffect notificationSpotlightEffect = NotificationSpotlightEffect.this;
                    if (notificationSpotlightEffect.mSpotlightEffect != null) {
                        notificationSpotlightEffect.resetScreenSize();
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) notificationSpotlightEffect.mSpotlightEffect.getLayoutParams();
                        layoutParams.setMarginStart((notificationSpotlightEffect.mScreenWidth - 2699) / 2);
                        notificationSpotlightEffect.mSpotlightEffect.setLayoutParams(layoutParams);
                    }
                }
                SpotLightEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i22 = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.setScaleX(0.63f);
                spotLightEffectView.setScaleY(0.63f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SpotLightEffectView spotLightEffectView = SpotLightEffectView.this;
                int i22 = SpotLightEffectView.$r8$clinit;
                spotLightEffectView.getClass();
            }
        };
    }
}
