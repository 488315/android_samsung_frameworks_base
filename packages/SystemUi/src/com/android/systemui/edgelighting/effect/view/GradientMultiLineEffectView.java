package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class GradientMultiLineEffectView extends AbsEdgeLightingMaskView {
    public final String TAG;
    public int mColorType;
    public ImageView mMidLayer;
    public FrameLayout mMultiLineEffectContainer;

    public GradientMultiLineEffectView(Context context) {
        super(context);
        this.TAG = "GradientMultiLineEffectView";
        this.mColorType = 1;
    }

    public final void hide() {
        AnimatorSet animatorSet = this.mAnimationSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AbsEdgeLightingMaskView.changeRingImageAlpha(this.mMultiLineEffectContainer, 0.0f, 400L);
        new Handler().postDelayed(new Runnable() { // from class: com.android.systemui.edgelighting.effect.view.GradientMultiLineEffectView.1
            @Override // java.lang.Runnable
            public final void run() {
                GradientMultiLineEffectView.this.resetImageDrawable();
            }
        }, 400L);
    }

    @Override // com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView
    public final void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.two_gradient_effect, this);
        this.mMultiLineEffectContainer = (FrameLayout) findViewById(R.id.gradient_effect_container);
        this.mTopLayer = (ImageView) findViewById(R.id.top_layer);
        this.mBottomLayer = (ImageView) findViewById(R.id.bottom_layer);
        this.mMainLayer = findViewById(R.id.main_layer);
        this.mMidLayer = (ImageView) findViewById(R.id.mid_layer);
        this.mMultiLineEffectContainer.setAlpha(0.0f);
    }

    public final void setImageDrawable() {
        if (this.mTopLayer.getDrawable() == null) {
            if (this.mColorType == 2) {
                this.mTopLayer.setImageResource(R.drawable.color_gradation_small);
            } else {
                this.mTopLayer.setImageResource(R.drawable.color_gradation_3);
            }
        }
        if (this.mBottomLayer.getDrawable() == null) {
            this.mBottomLayer.setImageResource(R.drawable.color_gradation_3);
        }
        if (this.mMidLayer.getDrawable() == null) {
            this.mMidLayer.setImageResource(R.drawable.color_gradation_3);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView
    public final void startRotation(long j) {
        AnimatorSet animatorSet = this.mAnimationSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mAnimationSet = null;
        }
        this.mMainLayer.setAlpha(0.0f);
        this.mTopLayer.setRotation(0.0f);
        this.mMidLayer.setRotation(0.0f);
        this.mBottomLayer.setRotation(0.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mMainLayer, "alpha", 1.0f);
        ofFloat.setDuration(300L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mTopLayer, "rotation", 0.0f, 360.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mMidLayer, "rotation", 0.0f, 360.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mBottomLayer, "rotation", 0.0f, 360.0f);
        ofFloat2.setDuration(3000L);
        ofFloat3.setDuration(3000L);
        ofFloat4.setDuration(3000L);
        if (this.mColorType == 2) {
            ofFloat3.setStartDelay(300L);
            ofFloat4.setStartDelay(500L);
        } else {
            ofFloat2.setStartDelay(350L);
        }
        ofFloat2.setRepeatMode(1);
        ofFloat2.setRepeatCount(-1);
        ofFloat3.setRepeatMode(1);
        ofFloat3.setRepeatCount(-1);
        ofFloat4.setRepeatMode(1);
        ofFloat4.setRepeatCount(-1);
        ofFloat2.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
        ofFloat3.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
        ofFloat4.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mAnimationSet = animatorSet2;
        if (this.mColorType == 2) {
            animatorSet2.playTogether(ofFloat2, ofFloat3, ofFloat4, ofFloat);
        } else {
            animatorSet2.playTogether(ofFloat2, ofFloat4, ofFloat);
        }
        ofFloat4.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.GradientMultiLineEffectView.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                AbsEdgeLightingMaskView.changeRingImageAlpha(GradientMultiLineEffectView.this.mMultiLineEffectContainer, 0.0f, 300L);
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
        this.mAnimationSet.start();
    }

    public final void updateEffectAlpha() {
        this.mMultiLineEffectContainer.setAlpha(this.mStrokeAlpha);
    }

    public GradientMultiLineEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "GradientMultiLineEffectView";
        this.mColorType = 1;
    }
}
