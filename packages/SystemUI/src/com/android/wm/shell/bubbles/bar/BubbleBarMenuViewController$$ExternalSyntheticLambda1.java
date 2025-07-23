package com.android.wm.shell.bubbles.bar;

import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleBarMenuViewController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleBarMenuViewController f$0;

    public /* synthetic */ BubbleBarMenuViewController$$ExternalSyntheticLambda1(BubbleBarMenuViewController bubbleBarMenuViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleBarMenuViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BubbleBarMenuViewController bubbleBarMenuViewController = this.f$0;
        switch (i) {
            case 0:
                BubbleBarHandleView bubbleBarHandleView = bubbleBarMenuViewController.mHandleView;
                float f = bubbleBarHandleView.mHandleWidth;
                float f2 = bubbleBarHandleView.mHandleHeight;
                int i2 = bubbleBarHandleView.mRegionSamplerColor;
                bubbleBarHandleView.mCurrentHandleHeight = f2;
                bubbleBarHandleView.mCurrentHandleWidth = f;
                bubbleBarHandleView.mHandlePaint.setColor(i2);
                bubbleBarHandleView.invalidate();
                bubbleBarHandleView.setTranslationY(0.0f);
                bubbleBarMenuViewController.mMenuView.setVisibility(8);
                bubbleBarMenuViewController.mScrimView.setVisibility(8);
                bubbleBarHandleView.setVisibility(0);
                BubbleBarExpandedView.AnonymousClass4 anonymousClass4 = bubbleBarMenuViewController.mListener;
                if (anonymousClass4 != null) {
                    anonymousClass4.onMenuVisibilityChanged(false);
                    break;
                }
                break;
            case 1:
                bubbleBarMenuViewController.mMenuView.setVisibility(0);
                bubbleBarMenuViewController.mScrimView.setVisibility(0);
                bubbleBarMenuViewController.animateTransition(new BubbleBarMenuViewController$$ExternalSyntheticLambda1(bubbleBarMenuViewController, 2), true);
                break;
            case 2:
                bubbleBarMenuViewController.mMenuView.getChildAt(0).requestAccessibilityFocus();
                BubbleBarExpandedView.AnonymousClass4 anonymousClass42 = bubbleBarMenuViewController.mListener;
                if (anonymousClass42 != null) {
                    anonymousClass42.onMenuVisibilityChanged(true);
                    break;
                }
                break;
            default:
                bubbleBarMenuViewController.hideMenu(true);
                break;
        }
    }
}
