package com.android.systemui.edgelighting.effect.container;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Slog;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.view.EdgeLightAppEffectView;
import com.samsung.android.util.SemLog;

public final class ApplicationEffect extends AbsEdgeLightingView {
    public ValueAnimator mContainerAnimator;
    public EdgeLightAppEffectView mLightEffectView;

    public ApplicationEffect(Context context) {
        super(context);
        init();
    }

    public final void containerAlphaAnimation(float f, final float f2) {
        Slog.i("ApplicationEffect", " containerAlphaAnimation from : " + f + " to : " + f2);
        ValueAnimator valueAnimator = this.mContainerAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            Slog.i("ApplicationEffect", " containerAlphaAnimation  cancel");
            this.mContainerAnimator.removeAllListeners();
            this.mContainerAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        this.mContainerAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.container.ApplicationEffect.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ApplicationEffect.this.setAlpha(((Float) valueAnimator2.getAnimatedValue()).floatValue());
            }
        });
        this.mContainerAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.container.ApplicationEffect.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (f2 == 0.0f) {
                    ApplicationEffect.this.setVisibility(4);
                    EdgeLightAppEffectView edgeLightAppEffectView = ApplicationEffect.this.mLightEffectView;
                    if (edgeLightAppEffectView != null) {
                        SemLog.secI(edgeLightAppEffectView.TAG, " hide : " + edgeLightAppEffectView.mIsAnimating);
                        AnimatorSet animatorSet = edgeLightAppEffectView.mAnimationSet;
                        if (animatorSet != null) {
                            animatorSet.cancel();
                        }
                        edgeLightAppEffectView.mIsAnimating = false;
                        ImageView imageView = edgeLightAppEffectView.mTopLayer;
                        if (imageView != null) {
                            imageView.setImageDrawable(null);
                        }
                        ImageView imageView2 = edgeLightAppEffectView.mBottomLayer;
                        if (imageView2 != null) {
                            imageView2.setImageDrawable(null);
                        }
                        ValueAnimator valueAnimator2 = edgeLightAppEffectView.repeatColorAnimation;
                        if (valueAnimator2 != null) {
                            valueAnimator2.cancel();
                        }
                    }
                    EdgeLightingDialog.AnonymousClass4 anonymousClass4 = ApplicationEffect.this.mEdgeListener;
                    if (anonymousClass4 != null) {
                        anonymousClass4.onFinishAnimation();
                    }
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
        this.mContainerAnimator.setDuration(500L);
        this.mContainerAnimator.start();
    }

    public final void init() {
        resetScreenSize();
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.edge_application_container, this);
        EdgeLightAppEffectView edgeLightAppEffectView = (EdgeLightAppEffectView) findViewById(R.id.edge_lighting_effect);
        this.mLightEffectView = edgeLightAppEffectView;
        int i = this.mScreenWidth;
        int i2 = this.mScreenHeight;
        edgeLightAppEffectView.mWidth = i;
        edgeLightAppEffectView.mHeight = i2;
        if (edgeLightAppEffectView.mTopLayer.getDrawable() == null) {
            edgeLightAppEffectView.mTopLayer.setImageResource(R.drawable.music_gradient);
        }
        if (edgeLightAppEffectView.mBottomLayer.getDrawable() == null) {
            edgeLightAppEffectView.mBottomLayer.setImageResource(R.drawable.music_gradient);
        }
        edgeLightAppEffectView.expandViewSize(edgeLightAppEffectView.mTopLayer);
        edgeLightAppEffectView.expandViewSize(edgeLightAppEffectView.mBottomLayer);
    }

    public ApplicationEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ApplicationEffect(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }
}
