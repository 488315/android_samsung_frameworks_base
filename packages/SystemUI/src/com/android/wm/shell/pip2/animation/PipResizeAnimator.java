package com.android.wm.shell.pip2.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.systemui.R;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public class PipResizeAnimator extends ValueAnimator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mAnimatedRect;
    public Runnable mAnimationEndCallback;
    public final AnonymousClass1 mAnimatorListener;
    public final AnonymousClass2 mAnimatorUpdateListener;
    public final Rect mBaseBounds;
    public final int mCornerRadius;
    public final float mDelta;
    public final Rect mEndBounds;
    public final SurfaceControl.Transaction mFinishTx;
    public final SurfaceControl mLeash;
    public final int mShadowRadius;
    public final Rect mStartBounds;
    public final SurfaceControl.Transaction mStartTx;
    public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;

    /* renamed from: -$$Nest$smsetBoundsAndRotation, reason: not valid java name */
    public static void m3276$$Nest$smsetBoundsAndRotation(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2, float f, int i, int i2) {
        Matrix matrix = new Matrix();
        float fWidth = rect2.width() / rect.width();
        matrix.setScale(fWidth, rect2.height() / rect.height());
        matrix.postTranslate(rect2.left, rect2.top);
        matrix.postRotate(f, rect2.centerX(), rect2.centerY());
        transaction.setMatrix(surfaceControl, matrix, new float[9]).setCornerRadius(surfaceControl, i / fWidth).setShadowRadius(surfaceControl, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.animation.Animator$AnimatorListener, com.android.wm.shell.pip2.animation.PipResizeAnimator$1] */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.animation.ValueAnimator$AnimatorUpdateListener, com.android.wm.shell.pip2.animation.PipResizeAnimator$2] */
    public PipResizeAnimator(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, Rect rect2, Rect rect3, int i, float f) {
        Rect rect4 = new Rect();
        this.mBaseBounds = rect4;
        Rect rect5 = new Rect();
        this.mStartBounds = rect5;
        Rect rect6 = new Rect();
        this.mEndBounds = rect6;
        Rect rect7 = new Rect();
        this.mAnimatedRect = rect7;
        ?? r4 = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip2.animation.PipResizeAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PipResizeAnimator pipResizeAnimator = PipResizeAnimator.this;
                SurfaceControl.Transaction transaction3 = pipResizeAnimator.mFinishTx;
                if (transaction3 != null) {
                    PipResizeAnimator.m3276$$Nest$smsetBoundsAndRotation(transaction3, pipResizeAnimator.mLeash, pipResizeAnimator.mBaseBounds, pipResizeAnimator.mEndBounds, 0.0f, pipResizeAnimator.mCornerRadius, pipResizeAnimator.mShadowRadius);
                }
                Runnable runnable = PipResizeAnimator.this.mAnimationEndCallback;
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PipResizeAnimator pipResizeAnimator = PipResizeAnimator.this;
                int i2 = PipResizeAnimator.$r8$clinit;
                pipResizeAnimator.getClass();
                PipResizeAnimator pipResizeAnimator2 = PipResizeAnimator.this;
                SurfaceControl.Transaction transaction3 = pipResizeAnimator2.mStartTx;
                if (transaction3 != null) {
                    PipResizeAnimator.m3276$$Nest$smsetBoundsAndRotation(transaction3, pipResizeAnimator2.mLeash, pipResizeAnimator2.mBaseBounds, pipResizeAnimator2.mStartBounds, pipResizeAnimator2.mDelta, pipResizeAnimator2.mCornerRadius, pipResizeAnimator2.mShadowRadius);
                    PipResizeAnimator.this.mStartTx.apply();
                }
            }
        };
        this.mAnimatorListener = r4;
        ?? r5 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip2.animation.PipResizeAnimator.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SurfaceControl.Transaction transaction3 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) PipResizeAnimator.this.mSurfaceControlTransactionFactory).getTransaction();
                float animatedFraction = 1.0f - PipResizeAnimator.this.getAnimatedFraction();
                PipResizeAnimator pipResizeAnimator = PipResizeAnimator.this;
                PipResizeAnimator.m3276$$Nest$smsetBoundsAndRotation(transaction3, pipResizeAnimator.mLeash, pipResizeAnimator.mBaseBounds, pipResizeAnimator.mAnimatedRect, animatedFraction * pipResizeAnimator.mDelta, pipResizeAnimator.mCornerRadius, pipResizeAnimator.mShadowRadius);
                transaction3.apply();
            }
        };
        this.mAnimatorUpdateListener = r5;
        this.mLeash = surfaceControl;
        this.mStartTx = transaction;
        this.mFinishTx = transaction2;
        this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
        rect4.set(rect);
        rect5.set(rect2);
        rect7.set(rect2);
        rect6.set(rect3);
        this.mDelta = f;
        RectEvaluator rectEvaluator = new RectEvaluator(rect7);
        this.mCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        this.mShadowRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
        setObjectValues(rect2, rect3);
        setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        addListener(r4);
        addUpdateListener(r5);
        setEvaluator(rectEvaluator);
        setDuration(i);
    }

    public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
        this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
    }
}
