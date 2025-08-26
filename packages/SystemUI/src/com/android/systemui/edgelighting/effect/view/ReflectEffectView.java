package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class ReflectEffectView extends DrawEdgeLayout {
    public AnimatorSet mAnimationSet;
    public ImageView mImageFrame;
    public float mLightingAlpha;

    public ReflectEffectView(Context context) {
        super(context);
        this.mLightingAlpha = 1.0f;
        init();
    }

    public final void init() {
        ImageView imageView = new ImageView(getContext());
        this.mImageFrame = imageView;
        imageView.setImageResource(R.drawable.edge_prism);
        this.mImageFrame.setScaleType(ImageView.ScaleType.FIT_XY);
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        int iRound = (int) Math.round(Math.sqrt(Math.pow(displayMetrics.heightPixels, 2.0d) + Math.pow(displayMetrics.widthPixels, 2.0d)));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iRound, iRound);
        layoutParams.setMarginStart((displayMetrics.widthPixels - iRound) / 2);
        layoutParams.topMargin = (displayMetrics.heightPixels - iRound) / 2;
        this.mImageFrame.setLayoutParams(layoutParams);
        addView(this.mImageFrame);
        setBackgroundColor(0);
    }

    public final void startAnimation() {
        AnimatorSet animatorSet = this.mAnimationSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mAnimationSet = null;
        }
        this.mImageFrame.setRotation(0.0f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mImageFrame, "rotation", 0.0f, 360.0f);
        objectAnimatorOfFloat.setDuration(6000L);
        objectAnimatorOfFloat.setRepeatCount(-1);
        objectAnimatorOfFloat.setRepeatMode(1);
        KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.0f, 0.0f, 1.0f, 1.0f, objectAnimatorOfFloat);
        this.mImageFrame.setAlpha(0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mImageFrame, "alpha", this.mLightingAlpha);
        objectAnimatorOfFloat2.setDuration(350L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mAnimationSet = animatorSet2;
        animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        this.mAnimationSet.start();
    }

    public final void stopAnimation() {
        this.mImageFrame.setAlpha(this.mLightingAlpha);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mImageFrame, "alpha", 0.0f);
        objectAnimatorOfFloat.setDuration(500L);
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.ReflectEffectView.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                AnimatorSet animatorSet = ReflectEffectView.this.mAnimationSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                ImageView imageView = ReflectEffectView.this.mImageFrame;
                if (imageView != null) {
                    imageView.setImageDrawable(null);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        objectAnimatorOfFloat.start();
    }

    public ReflectEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLightingAlpha = 1.0f;
        init();
    }

    public ReflectEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLightingAlpha = 1.0f;
        init();
    }
}
