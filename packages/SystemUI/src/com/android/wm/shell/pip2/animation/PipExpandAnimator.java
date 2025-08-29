package com.android.wm.shell.pip2.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.systemui.R;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public class PipExpandAnimator extends ValueAnimator {
    public final Rect mAnimatedRect;
    public Runnable mAnimationEndCallback;
    public Runnable mAnimationStartCallback;
    public final AnonymousClass1 mAnimatorListener;
    public final AnonymousClass2 mAnimatorUpdateListener;
    public final Rect mBaseBounds;
    public final Rect mEndBounds;
    public final SurfaceControl.Transaction mFinishTransaction;
    public final RectEvaluator mInsetEvaluator;
    public final SurfaceControl mLeash;
    public final PipSurfaceTransactionHelper mPipSurfaceTransactionHelper;
    public final int mRotation;
    public final Rect mSourceRectHintInsets;
    public final Rect mStartBounds;
    public final SurfaceControl.Transaction mStartTransaction;
    public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
    public final Rect mZeroInsets;

    /* renamed from: -$$Nest$monExpandAnimationUpdate, reason: not valid java name */
    public static void m3274$$Nest$monExpandAnimationUpdate(PipExpandAnimator pipExpandAnimator, SurfaceControl.Transaction transaction, float f) {
        Rect rectEvaluate = pipExpandAnimator.mInsetEvaluator.evaluate(f, pipExpandAnimator.mSourceRectHintInsets, pipExpandAnimator.mZeroInsets);
        int i = pipExpandAnimator.mRotation;
        if (i == 0) {
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper = pipExpandAnimator.mPipSurfaceTransactionHelper;
            SurfaceControl surfaceControl = pipExpandAnimator.mLeash;
            Rect rect = pipExpandAnimator.mBaseBounds;
            Rect rect2 = pipExpandAnimator.mAnimatedRect;
            pipSurfaceTransactionHelper.mTmpDestinationRect.set(rect);
            pipSurfaceTransactionHelper.mTmpDestinationRect.offsetTo(0, 0);
            pipSurfaceTransactionHelper.mTmpDestinationRect.inset(rectEvaluate);
            float fMax = Math.max(rect2.width() / rect.width(), rect2.height() / rect.height());
            pipSurfaceTransactionHelper.mTmpTransform.setScale(fMax, fMax);
            transaction.setMatrix(surfaceControl, pipSurfaceTransactionHelper.mTmpTransform, pipSurfaceTransactionHelper.mTmpFloat9).setCrop(surfaceControl, pipSurfaceTransactionHelper.mTmpDestinationRect).setPosition(surfaceControl, rect2.left - (rectEvaluate.left * fMax), rect2.top - (rectEvaluate.top * fMax));
        } else {
            Rect rect3 = pipExpandAnimator.mStartBounds;
            Rect rect4 = pipExpandAnimator.mEndBounds;
            int i2 = rect4.left;
            float f2 = ((i2 - r6) * f) + rect3.left;
            int i3 = rect4.top;
            float f3 = ((i3 - r3) * f) + rect3.top;
            float f4 = f * (i == 1 ? 90.0f : -90.0f);
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper2 = pipExpandAnimator.mPipSurfaceTransactionHelper;
            SurfaceControl surfaceControl2 = pipExpandAnimator.mLeash;
            Rect rect5 = pipExpandAnimator.mBaseBounds;
            Rect rect6 = pipExpandAnimator.mAnimatedRect;
            pipSurfaceTransactionHelper2.mTmpDestinationRect.set(rect5);
            pipSurfaceTransactionHelper2.mTmpDestinationRect.inset(rectEvaluate);
            int iWidth = pipSurfaceTransactionHelper2.mTmpDestinationRect.width();
            int iHeight = pipSurfaceTransactionHelper2.mTmpDestinationRect.height();
            int iWidth2 = rect6.width();
            int iHeight2 = rect6.height();
            float f5 = iWidth <= iHeight ? iWidth2 / iWidth : iHeight2 / iHeight;
            Rect rect7 = pipSurfaceTransactionHelper2.mTmpDestinationRect;
            rect7.set(0, 0, iWidth2, iHeight2);
            rect7.scale(1.0f / f5);
            rect7.offset(rectEvaluate.left, rectEvaluate.top);
            pipSurfaceTransactionHelper2.mTmpTransform.setScale(f5, f5);
            pipSurfaceTransactionHelper2.mTmpTransform.postTranslate(f2 - (rectEvaluate.left * f5), f3 - (rectEvaluate.top * f5));
            pipSurfaceTransactionHelper2.mTmpTransform.postRotate(f4);
            transaction.setMatrix(surfaceControl2, pipSurfaceTransactionHelper2.mTmpTransform, pipSurfaceTransactionHelper2.mTmpFloat9).setCrop(surfaceControl2, rect7);
        }
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper3 = pipExpandAnimator.mPipSurfaceTransactionHelper;
        SurfaceControl surfaceControl3 = pipExpandAnimator.mLeash;
        pipSurfaceTransactionHelper3.getClass();
        transaction.setCornerRadius(surfaceControl3, 0.0f);
        transaction.setShadowRadius(pipExpandAnimator.mLeash, 0.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1, types: [android.animation.Animator$AnimatorListener, com.android.wm.shell.pip2.animation.PipExpandAnimator$1] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.animation.ValueAnimator$AnimatorUpdateListener, com.android.wm.shell.pip2.animation.PipExpandAnimator$2] */
    public PipExpandAnimator(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i) {
        Rect rect5 = new Rect();
        this.mBaseBounds = rect5;
        Rect rect6 = new Rect();
        this.mStartBounds = rect6;
        Rect rect7 = new Rect();
        this.mEndBounds = rect7;
        Rect rect8 = new Rect();
        this.mSourceRectHintInsets = rect8;
        this.mZeroInsets = new Rect(0, 0, 0, 0);
        Rect rect9 = new Rect();
        this.mAnimatedRect = rect9;
        ?? r7 = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip2.animation.PipExpandAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PipExpandAnimator pipExpandAnimator = PipExpandAnimator.this;
                SurfaceControl.Transaction transaction3 = pipExpandAnimator.mFinishTransaction;
                if (transaction3 != null) {
                    PipExpandAnimator.m3274$$Nest$monExpandAnimationUpdate(pipExpandAnimator, transaction3, 1.0f);
                }
                Runnable runnable = PipExpandAnimator.this.mAnimationEndCallback;
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                Runnable runnable = PipExpandAnimator.this.mAnimationStartCallback;
                if (runnable != null) {
                    runnable.run();
                }
                PipExpandAnimator pipExpandAnimator = PipExpandAnimator.this;
                SurfaceControl.Transaction transaction3 = pipExpandAnimator.mStartTransaction;
                if (transaction3 != null) {
                    PipExpandAnimator.m3274$$Nest$monExpandAnimationUpdate(pipExpandAnimator, transaction3, 0.0f);
                    PipExpandAnimator.this.mStartTransaction.apply();
                }
            }
        };
        this.mAnimatorListener = r7;
        ?? r8 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip2.animation.PipExpandAnimator.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SurfaceControl.Transaction transaction3 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) PipExpandAnimator.this.mSurfaceControlTransactionFactory).getTransaction();
                PipExpandAnimator.m3274$$Nest$monExpandAnimationUpdate(PipExpandAnimator.this, transaction3, PipExpandAnimator.this.getAnimatedFraction());
                transaction3.apply();
            }
        };
        this.mAnimatorUpdateListener = r8;
        this.mLeash = surfaceControl;
        this.mStartTransaction = transaction;
        this.mFinishTransaction = transaction2;
        rect5.set(rect);
        rect6.set(rect2);
        rect9.set(rect2);
        rect7.set(rect3);
        RectEvaluator rectEvaluator = new RectEvaluator(rect9);
        this.mInsetEvaluator = new RectEvaluator(new Rect());
        this.mPipSurfaceTransactionHelper = new PipSurfaceTransactionHelper(context);
        this.mRotation = i;
        Rect rect10 = rect4 != null ? new Rect(rect4) : null;
        if (rect10 != null) {
            rect8.set(rect10.left - rect5.left, rect10.top - rect5.top, rect5.right - rect10.right, rect5.bottom - rect10.bottom);
        }
        this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
        setDuration(context.getResources().getInteger(R.integer.config_pipEnterAnimationDuration));
        setObjectValues(rect2, rect3);
        setEvaluator(rectEvaluator);
        setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        addListener(r7);
        addUpdateListener(r8);
    }

    public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
        this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
    }
}
