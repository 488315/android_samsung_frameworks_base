package com.android.wm.shell.bubbles.bar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;

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
        float fFloatValue = z ? 0.0f : 1.0f;
        ValueAnimator valueAnimator = this.mMenuAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            fFloatValue = ((Float) this.mMenuAnimator.getAnimatedValue()).floatValue();
            this.mMenuAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fFloatValue, z ? 1.0f : 0.0f);
        valueAnimatorOfFloat.setDuration(600L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.EMPHASIZED);
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleBarMenuViewController.this.mMenuAnimator = null;
                runnable.run();
            }
        });
        this.mMenuAnimator = valueAnimatorOfFloat;
        int width = this.mMenuView.getWidth();
        BubbleBarHandleView bubbleBarHandleView = this.mHandleView;
        int i = (int) bubbleBarHandleView.mHandleWidth;
        int i2 = (int) bubbleBarHandleView.mHandleHeight;
        float f = ((width - i) * 0.4f) + i;
        final int color = this.mContext.getColor(R.color.side_fps_toast_background);
        final float width2 = f / this.mMenuView.getWidth();
        final float f2 = f - ((int) bubbleBarHandleView.mHandleWidth);
        final float height = ((this.mMenuView.mBubbleSectionView.getHeight() * f) / this.mMenuView.getWidth()) - i2;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) throws Resources.NotFoundException {
                BubbleBarMenuViewController bubbleBarMenuViewController = this.f$0;
                float f3 = f2;
                float f4 = height;
                int i3 = color;
                float f5 = width2;
                bubbleBarMenuViewController.getClass();
                float fFloatValue2 = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                boolean z2 = fFloatValue2 <= 0.4f;
                int i4 = z2 ? 0 : 8;
                BubbleBarHandleView bubbleBarHandleView2 = bubbleBarMenuViewController.mHandleView;
                bubbleBarHandleView2.setVisibility(i4);
                bubbleBarMenuViewController.mMenuView.setVisibility(z2 ? 8 : 0);
                if (z2) {
                    float f6 = fFloatValue2 / 0.4f;
                    float f7 = (f3 * f6) + bubbleBarHandleView2.mHandleWidth;
                    float f8 = f4 * f6;
                    float f9 = bubbleBarHandleView2.mHandleHeight + f8;
                    int iIntValue = ((Integer) bubbleBarHandleView2.mArgbEvaluator.evaluate(f6, Integer.valueOf(bubbleBarHandleView2.mRegionSamplerColor), Integer.valueOf(i3))).intValue();
                    bubbleBarHandleView2.mCurrentHandleHeight = f9;
                    bubbleBarHandleView2.mCurrentHandleWidth = f7;
                    bubbleBarHandleView2.mHandlePaint.setColor(iIntValue);
                    bubbleBarHandleView2.invalidate();
                    bubbleBarHandleView2.setTranslationY(f8 / 2.0f);
                    return;
                }
                bubbleBarMenuViewController.mMenuView.setTranslationY((bubbleBarHandleView2.getHeight() - bubbleBarHandleView2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bubble_bar_expanded_view_handle_height)) / 2);
                bubbleBarMenuViewController.mMenuView.setPivotY(0.0f);
                bubbleBarMenuViewController.mMenuView.setPivotX(r1.getWidth() / 2.0f);
                float f10 = (fFloatValue2 - 0.4f) / 0.6f;
                float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f5, f10, f5);
                BubbleBarMenuView bubbleBarMenuView = bubbleBarMenuViewController.mMenuView;
                int dimensionPixelSize = bubbleBarMenuView.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.bubble_manage_menu_elevation);
                bubbleBarMenuView.setScaleX(fM$1);
                bubbleBarMenuView.setScaleY(fM$1);
                bubbleBarMenuView.mBubbleIconView.setAlpha(f10);
                bubbleBarMenuView.mBubbleTitleView.setAlpha(f10);
                bubbleBarMenuView.mBubbleDismissIconView.setAlpha(f10);
                float f11 = dimensionPixelSize;
                bubbleBarMenuView.mBubbleSectionView.setElevation(f11 * f10);
                float fMax = Math.max(0.0f, (f10 - 0.33333334f) * 2.0f);
                float fMax2 = Math.max(0.0f, (f10 - 0.6666667f) * 3.0f);
                bubbleBarMenuView.mActionsSectionView.setAlpha(fMax);
                bubbleBarMenuView.mActionsSectionView.setElevation(f11 * fMax);
                for (int childCount = bubbleBarMenuView.mActionsSectionView.getChildCount() - 1; childCount >= 0; childCount--) {
                    bubbleBarMenuView.mActionsSectionView.getChildAt(childCount).setAlpha(fMax2);
                }
            }
        });
        valueAnimatorOfFloat.start();
    }

    public final void hideMenu(final boolean z) {
        if (this.mMenuView == null || this.mScrimView == null) {
            return;
        }
        runOnMenuIsMeasured(new Runnable() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarMenuViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BubbleBarMenuViewController bubbleBarMenuViewController = this.f$0;
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
                    this.f$0.runOnMenuIsMeasured(runnable);
                }
            });
        } else {
            runnable.run();
        }
    }
}
