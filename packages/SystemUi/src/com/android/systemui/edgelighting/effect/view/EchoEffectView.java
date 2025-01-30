package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EchoEffectView extends RelativeLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float[] ALPHA;
    public final int[] ALPHA_IN_DURATION;
    public final int[] ALPHA_OUT_DELAY;
    public final float[] LINE_HEIGHT;
    public final ArrayList mAnimList;
    public AnimatorSet mAnimatorSet;
    public final C13231 mAnimtorListener;
    public final ArrayList mLeftLine;
    public final ArrayList mRightLine;
    public int mScreenHeight;
    public int mScreenWidth;

    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.edgelighting.effect.view.EchoEffectView$1] */
    public EchoEffectView(Context context) {
        super(context);
        this.mRightLine = new ArrayList();
        this.mLeftLine = new ArrayList();
        this.mAnimList = new ArrayList();
        this.LINE_HEIGHT = new float[]{0.8f, 0.67f, 0.52f};
        this.ALPHA_IN_DURATION = new int[]{400, 300, 300};
        this.ALPHA_OUT_DELAY = new int[]{800, 900, 900};
        this.ALPHA = new float[]{1.0f, 0.7f, 0.4f};
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.EchoEffectView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                int i = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                for (int i = 0; i < 3; i++) {
                    ((View) echoEffectView.mLeftLine.get(i)).setScaleY(0.0f);
                    ((View) echoEffectView.mLeftLine.get(i)).setAlpha(0.0f);
                    ((View) echoEffectView.mRightLine.get(i)).setScaleY(0.0f);
                    ((View) echoEffectView.mRightLine.get(i)).setAlpha(0.0f);
                }
                int i2 = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }
        };
    }

    public static Drawable makeGradientBg(int i) {
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.argb(30, Color.red(i), Color.green(i), Color.blue(i)), i, Color.argb(30, Color.red(i), Color.green(i), Color.blue(i))});
        gradientDrawable.setCornerRadius(90.0f);
        return gradientDrawable;
    }

    public final Animator getAnim(View view, int i) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(i * 300);
        for (int i2 = 0; i2 < 2; i2++) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, this.ALPHA[i]);
            long j = i2 * 1400;
            ofFloat.setStartDelay(j);
            ofFloat.setDuration(this.ALPHA_IN_DURATION[i]);
            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.17f, 0.17f, 0.83f, 0.83f, ofFloat);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", this.ALPHA[i], 0.0f);
            ofFloat2.setDuration(400L);
            ofFloat2.setStartDelay(r7 + this.ALPHA_OUT_DELAY[i]);
            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.17f, 0.17f, 0.83f, 0.83f, ofFloat2);
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
            ofFloat3.setStartDelay(j);
            ofFloat3.setDuration(500L);
            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.17f, 0.17f, 0.83f, 0.83f, ofFloat3);
            animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        }
        return animatorSet;
    }

    public final void startAnimation() {
        if (this.mAnimatorSet == null) {
            this.mAnimatorSet = new AnimatorSet();
        }
        this.mAnimatorSet.cancel();
        this.mAnimList.clear();
        for (int i = 0; i < 3; i++) {
            this.mAnimList.add(getAnim((View) this.mLeftLine.get(i), i));
            this.mAnimList.add(getAnim((View) this.mRightLine.get(i), i));
        }
        this.mAnimatorSet.addListener(this.mAnimtorListener);
        this.mAnimatorSet.playTogether(this.mAnimList);
        this.mAnimatorSet.start();
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.edgelighting.effect.view.EchoEffectView$1] */
    public EchoEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRightLine = new ArrayList();
        this.mLeftLine = new ArrayList();
        this.mAnimList = new ArrayList();
        this.LINE_HEIGHT = new float[]{0.8f, 0.67f, 0.52f};
        this.ALPHA_IN_DURATION = new int[]{400, 300, 300};
        this.ALPHA_OUT_DELAY = new int[]{800, 900, 900};
        this.ALPHA = new float[]{1.0f, 0.7f, 0.4f};
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.EchoEffectView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                int i = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                for (int i = 0; i < 3; i++) {
                    ((View) echoEffectView.mLeftLine.get(i)).setScaleY(0.0f);
                    ((View) echoEffectView.mLeftLine.get(i)).setAlpha(0.0f);
                    ((View) echoEffectView.mRightLine.get(i)).setScaleY(0.0f);
                    ((View) echoEffectView.mRightLine.get(i)).setAlpha(0.0f);
                }
                int i2 = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.edgelighting.effect.view.EchoEffectView$1] */
    public EchoEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRightLine = new ArrayList();
        this.mLeftLine = new ArrayList();
        this.mAnimList = new ArrayList();
        this.LINE_HEIGHT = new float[]{0.8f, 0.67f, 0.52f};
        this.ALPHA_IN_DURATION = new int[]{400, 300, 300};
        this.ALPHA_OUT_DELAY = new int[]{800, 900, 900};
        this.ALPHA = new float[]{1.0f, 0.7f, 0.4f};
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.EchoEffectView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                int i2 = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                for (int i2 = 0; i2 < 3; i2++) {
                    ((View) echoEffectView.mLeftLine.get(i2)).setScaleY(0.0f);
                    ((View) echoEffectView.mLeftLine.get(i2)).setAlpha(0.0f);
                    ((View) echoEffectView.mRightLine.get(i2)).setScaleY(0.0f);
                    ((View) echoEffectView.mRightLine.get(i2)).setAlpha(0.0f);
                }
                int i22 = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.edgelighting.effect.view.EchoEffectView$1] */
    public EchoEffectView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRightLine = new ArrayList();
        this.mLeftLine = new ArrayList();
        this.mAnimList = new ArrayList();
        this.LINE_HEIGHT = new float[]{0.8f, 0.67f, 0.52f};
        this.ALPHA_IN_DURATION = new int[]{400, 300, 300};
        this.ALPHA_OUT_DELAY = new int[]{800, 900, 900};
        this.ALPHA = new float[]{1.0f, 0.7f, 0.4f};
        this.mAnimtorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.effect.view.EchoEffectView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                int i22 = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                EchoEffectView echoEffectView = EchoEffectView.this;
                for (int i22 = 0; i22 < 3; i22++) {
                    ((View) echoEffectView.mLeftLine.get(i22)).setScaleY(0.0f);
                    ((View) echoEffectView.mLeftLine.get(i22)).setAlpha(0.0f);
                    ((View) echoEffectView.mRightLine.get(i22)).setScaleY(0.0f);
                    ((View) echoEffectView.mRightLine.get(i22)).setAlpha(0.0f);
                }
                int i222 = EchoEffectView.$r8$clinit;
                echoEffectView.getClass();
                EchoEffectView.this.getClass();
            }
        };
    }
}
