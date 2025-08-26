package com.android.wm.shell.pip2.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.SurfaceControl;
import com.android.systemui.R;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;

/* loaded from: classes3.dex */
public class PipAlphaAnimator extends ValueAnimator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Runnable mAnimationEndCallback;
    public final AnonymousClass1 mAnimatorListener;
    public final AnonymousClass2 mAnimatorUpdateListener;
    public final int mCornerRadius;
    public final int mDirection;
    public final SurfaceControl.Transaction mFinishTransaction;
    public final SurfaceControl mLeash;
    public final int mShadowRadius;
    public final SurfaceControl.Transaction mStartTransaction;
    public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;

    /* renamed from: -$$Nest$monAlphaAnimationUpdate, reason: not valid java name */
    public static void m3274$$Nest$monAlphaAnimationUpdate(PipAlphaAnimator pipAlphaAnimator, float f, SurfaceControl.Transaction transaction) {
        transaction.setAlpha(pipAlphaAnimator.mLeash, f).setCornerRadius(pipAlphaAnimator.mLeash, pipAlphaAnimator.mCornerRadius).setShadowRadius(pipAlphaAnimator.mLeash, pipAlphaAnimator.mDirection == 0 ? pipAlphaAnimator.mShadowRadius : 0.0f);
        transaction.apply();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.animation.Animator$AnimatorListener, com.android.wm.shell.pip2.animation.PipAlphaAnimator$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.animation.ValueAnimator$AnimatorUpdateListener, com.android.wm.shell.pip2.animation.PipAlphaAnimator$2] */
    public PipAlphaAnimator(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, int i) throws Resources.NotFoundException {
        ?? r0 = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip2.animation.PipAlphaAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PipAlphaAnimator pipAlphaAnimator = PipAlphaAnimator.this;
                SurfaceControl.Transaction transaction3 = pipAlphaAnimator.mFinishTransaction;
                if (transaction3 != null) {
                    PipAlphaAnimator.m3274$$Nest$monAlphaAnimationUpdate(pipAlphaAnimator, pipAlphaAnimator.mDirection == 0 ? 1.0f : 0.0f, transaction3);
                    PipAlphaAnimator.this.mFinishTransaction.apply();
                }
                Runnable runnable = PipAlphaAnimator.this.mAnimationEndCallback;
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PipAlphaAnimator pipAlphaAnimator = PipAlphaAnimator.this;
                int i2 = PipAlphaAnimator.$r8$clinit;
                pipAlphaAnimator.getClass();
                PipAlphaAnimator pipAlphaAnimator2 = PipAlphaAnimator.this;
                SurfaceControl.Transaction transaction3 = pipAlphaAnimator2.mStartTransaction;
                if (transaction3 != null) {
                    PipAlphaAnimator.m3274$$Nest$monAlphaAnimationUpdate(pipAlphaAnimator2, pipAlphaAnimator2.mDirection == 0 ? 0.0f : 1.0f, transaction3);
                    PipAlphaAnimator.this.mStartTransaction.apply();
                }
            }
        };
        this.mAnimatorListener = r0;
        ?? r1 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip2.animation.PipAlphaAnimator.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PipAlphaAnimator.m3274$$Nest$monAlphaAnimationUpdate(PipAlphaAnimator.this, ((Float) valueAnimator.getAnimatedValue()).floatValue(), ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) PipAlphaAnimator.this.mSurfaceControlTransactionFactory).getTransaction());
            }
        };
        this.mAnimatorUpdateListener = r1;
        this.mLeash = surfaceControl;
        this.mStartTransaction = transaction;
        this.mFinishTransaction = transaction2;
        this.mDirection = i;
        setFloatValues(i == 0 ? 0.0f : 1.0f, i != 0 ? 0.0f : 1.0f);
        this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
        int integer = context.getResources().getInteger(R.integer.config_pipEnterAnimationDuration);
        this.mCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        this.mShadowRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
        setDuration(integer);
        addListener(r0);
        addUpdateListener(r1);
    }

    public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
        this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
    }
}
