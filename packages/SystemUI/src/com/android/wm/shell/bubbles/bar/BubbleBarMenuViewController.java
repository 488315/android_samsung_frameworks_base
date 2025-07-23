package com.android.wm.shell.bubbles.bar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleBarMenuViewController {
    public Bubble mBubble;
    public final Context mContext;
    public final BubbleBarHandleView mHandleView;
    public BubbleBarExpandedView.AnonymousClass4 mListener;
    public ValueAnimator mMenuAnimator;
    public BubbleBarMenuView mMenuView;
    public final ViewGroup mRootView;
    public View mScrimView;

    public BubbleBarMenuViewController(Context context, BubbleBarHandleView bubbleBarHandleView, ViewGroup viewGroup) {
        this.mContext = context;
        this.mRootView = viewGroup;
        this.mHandleView = bubbleBarHandleView;
    }

    public final void animateTransition(final Runnable runnable, boolean z) {
        if (this.mMenuView == null) {
            return;
        }
        float f = z ? 0.0f : 1.0f;
        ValueAnimator valueAnimator = this.mMenuAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            f = ((Float) this.mMenuAnimator.getAnimatedValue()).floatValue();
            this.mMenuAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, z ? 1.0f : 0.0f);
        ofFloat.setDuration(600L);
        ofFloat.setInterpolator(Interpolators.EMPHASIZED);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleBarMenuViewController.this.mMenuAnimator = null;
                runnable.run();
            }
        });
        this.mMenuAnimator = ofFloat;
        int width = this.mMenuView.getWidth();
        BubbleBarHandleView bubbleBarHandleView = this.mHandleView;
        int i = (int) bubbleBarHandleView.mHandleWidth;
        int i2 = (int) bubbleBarHandleView.mHandleHeight;
        float f2 = ((width - i) * 0.4f) + i;
        final int color = this.mContext.getColor(R.color.side_fps_toast_background);
        final float width2 = f2 / this.mMenuView.getWidth();
        final float f3 = f2 - ((int) bubbleBarHandleView.mHandleWidth);
        final float height = ((this.mMenuView.mBubbleSectionView.getHeight() * f2) / this.mMenuView.getWidth()) - i2;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                BubbleBarMenuViewController bubbleBarMenuViewController = BubbleBarMenuViewController.this;
                float f4 = f3;
                float f5 = height;
                int i3 = color;
                float f6 = width2;
                bubbleBarMenuViewController.getClass();
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                boolean z2 = floatValue <= 0.4f;
                int i4 = z2 ? 0 : 8;
                BubbleBarHandleView bubbleBarHandleView2 = bubbleBarMenuViewController.mHandleView;
                bubbleBarHandleView2.setVisibility(i4);
                bubbleBarMenuViewController.mMenuView.setVisibility(z2 ? 8 : 0);
                if (z2) {
                    float f7 = floatValue / 0.4f;
                    float f8 = (f4 * f7) + bubbleBarHandleView2.mHandleWidth;
                    float f9 = f5 * f7;
                    float f10 = bubbleBarHandleView2.mHandleHeight + f9;
                    int intValue = ((Integer) bubbleBarHandleView2.mArgbEvaluator.evaluate(f7, Integer.valueOf(bubbleBarHandleView2.mRegionSamplerColor), Integer.valueOf(i3))).intValue();
                    bubbleBarHandleView2.mCurrentHandleHeight = f10;
                    bubbleBarHandleView2.mCurrentHandleWidth = f8;
                    bubbleBarHandleView2.mHandlePaint.setColor(intValue);
                    bubbleBarHandleView2.invalidate();
                    bubbleBarHandleView2.setTranslationY(f9 / 2.0f);
                    return;
                }
                bubbleBarMenuViewController.mMenuView.setTranslationY((bubbleBarHandleView2.getHeight() - bubbleBarHandleView2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bubble_bar_expanded_view_handle_height)) / 2);
                bubbleBarMenuViewController.mMenuView.setPivotY(0.0f);
                bubbleBarMenuViewController.mMenuView.setPivotX(r1.getWidth() / 2.0f);
                float f11 = (floatValue - 0.4f) / 0.6f;
                float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f6, f11, f6);
                BubbleBarMenuView bubbleBarMenuView = bubbleBarMenuViewController.mMenuView;
                int dimensionPixelSize = bubbleBarMenuView.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bubble_manage_menu_elevation);
                bubbleBarMenuView.setScaleX(m$1);
                bubbleBarMenuView.setScaleY(m$1);
                bubbleBarMenuView.mBubbleIconView.setAlpha(f11);
                bubbleBarMenuView.mBubbleTitleView.setAlpha(f11);
                bubbleBarMenuView.mBubbleDismissIconView.setAlpha(f11);
                float f12 = dimensionPixelSize;
                bubbleBarMenuView.mBubbleSectionView.setElevation(f12 * f11);
                float max = Math.max(0.0f, (f11 - 0.33333334f) * 2.0f);
                float max2 = Math.max(0.0f, (f11 - 0.6666667f) * 3.0f);
                bubbleBarMenuView.mActionsSectionView.setAlpha(max);
                bubbleBarMenuView.mActionsSectionView.setElevation(f12 * max);
                for (int childCount = bubbleBarMenuView.mActionsSectionView.getChildCount() - 1; childCount >= 0; childCount--) {
                    bubbleBarMenuView.mActionsSectionView.getChildAt(childCount).setAlpha(max2);
                }
            }
        });
        ofFloat.start();
    }

    public final void hideMenu(final boolean z) {
        if (this.mMenuView == null || this.mScrimView == null) {
            return;
        }
        runOnMenuIsMeasured(new Runnable() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BubbleBarMenuViewController bubbleBarMenuViewController = BubbleBarMenuViewController.this;
                boolean z2 = z;
                bubbleBarMenuViewController.getClass();
                BubbleBarMenuViewController$$ExternalSyntheticLambda1 bubbleBarMenuViewController$$ExternalSyntheticLambda1 = new BubbleBarMenuViewController$$ExternalSyntheticLambda1(bubbleBarMenuViewController, 0);
                if (z2) {
                    bubbleBarMenuViewController.animateTransition(bubbleBarMenuViewController$$ExternalSyntheticLambda1, false);
                } else {
                    bubbleBarMenuViewController$$ExternalSyntheticLambda1.run();
                }
            }
        });
    }

    public final void runOnMenuIsMeasured(final Runnable runnable) {
        if (this.mMenuView.getWidth() == 0 || this.mMenuView.getHeight() == 0) {
            this.mMenuView.post(new Runnable() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BubbleBarMenuViewController.this.runOnMenuIsMeasured(runnable);
                }
            });
        } else {
            runnable.run();
        }
    }
}
