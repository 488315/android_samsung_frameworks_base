package com.android.wm.shell.pip2.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip2.phone.PipAppIconOverlay;
import com.android.wm.shell.pip2.phone.PipTransition$$ExternalSyntheticLambda4;
import com.android.wm.shell.shared.animation.Interpolators;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipEnterAnimator extends ValueAnimator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mAnimatedRect;
    public PipTransition$$ExternalSyntheticLambda4 mAnimationEndCallback;
    public PipTransition$$ExternalSyntheticLambda4 mAnimationStartCallback;
    public final AnonymousClass1 mAnimatorListener;
    public final AnonymousClass2 mAnimatorUpdateListener;
    public PipAppIconOverlay mContentOverlay;
    public final int mCornerRadius;
    public final Rect mEndBounds;
    public final SurfaceControl.Transaction mFinishTransaction;
    public final Rect mInitCrop;
    public final PointF mInitPos;
    public final PointF mInitScale;
    public final SurfaceControl mLeash;
    public final float[] mMatrixTmp;
    public PipAppIconOverlaySupplier mPipAppIconOverlaySupplier;
    public final RectEvaluator mRectEvaluator;
    public final int mRotation;
    public final int mShadowRadius;
    public final SurfaceControl.Transaction mStartTransaction;
    public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
    public final Matrix mTransformTensor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    interface PipAppIconOverlaySupplier {
        PipAppIconOverlay get(Context context, Rect rect, Rect rect2, ActivityInfo activityInfo, int i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.animation.Animator$AnimatorListener, com.android.wm.shell.pip2.animation.PipEnterAnimator$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.animation.ValueAnimator$AnimatorUpdateListener, com.android.wm.shell.pip2.animation.PipEnterAnimator$2] */
    public PipEnterAnimator(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, int i) {
        Rect rect2 = new Rect();
        this.mAnimatedRect = rect2;
        Rect rect3 = new Rect();
        this.mEndBounds = rect3;
        this.mTransformTensor = new Matrix();
        this.mMatrixTmp = new float[9];
        this.mInitScale = new PointF();
        this.mInitPos = new PointF();
        this.mInitCrop = new Rect();
        ?? r2 = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip2.animation.PipEnterAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PipEnterAnimator pipEnterAnimator = PipEnterAnimator.this;
                SurfaceControl.Transaction transaction3 = pipEnterAnimator.mFinishTransaction;
                if (transaction3 != null) {
                    pipEnterAnimator.onEnterAnimationUpdate(1.0f, transaction3);
                }
                PipTransition$$ExternalSyntheticLambda4 pipTransition$$ExternalSyntheticLambda4 = PipEnterAnimator.this.mAnimationEndCallback;
                if (pipTransition$$ExternalSyntheticLambda4 != null) {
                    pipTransition$$ExternalSyntheticLambda4.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PipTransition$$ExternalSyntheticLambda4 pipTransition$$ExternalSyntheticLambda4 = PipEnterAnimator.this.mAnimationStartCallback;
                if (pipTransition$$ExternalSyntheticLambda4 != null) {
                    pipTransition$$ExternalSyntheticLambda4.run();
                }
                PipEnterAnimator pipEnterAnimator = PipEnterAnimator.this;
                SurfaceControl.Transaction transaction3 = pipEnterAnimator.mStartTransaction;
                if (transaction3 != null) {
                    pipEnterAnimator.onEnterAnimationUpdate(0.0f, transaction3);
                    PipEnterAnimator.this.mStartTransaction.apply();
                }
            }
        };
        this.mAnimatorListener = r2;
        ?? r3 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip2.animation.PipEnterAnimator.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SurfaceControl.Transaction transaction3 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) PipEnterAnimator.this.mSurfaceControlTransactionFactory).getTransaction();
                PipEnterAnimator.this.onEnterAnimationUpdate(PipEnterAnimator.this.getAnimatedFraction(), transaction3);
                transaction3.apply();
            }
        };
        this.mAnimatorUpdateListener = r3;
        this.mLeash = surfaceControl;
        this.mStartTransaction = transaction;
        this.mFinishTransaction = transaction2;
        this.mRectEvaluator = new RectEvaluator(rect2);
        rect3.set(rect);
        this.mRotation = i;
        this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
        this.mPipAppIconOverlaySupplier = new PipAppIconOverlaySupplier() { // from class: com.android.wm.shell.pip2.animation.PipEnterAnimator$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.pip2.animation.PipEnterAnimator.PipAppIconOverlaySupplier
            public final PipAppIconOverlay get(Context context2, Rect rect4, Rect rect5, ActivityInfo activityInfo, int i2) {
                int i3 = PipEnterAnimator.$r8$clinit;
                PipEnterAnimator.this.getClass();
                return new PipAppIconOverlay(context2, rect4, rect5, new IconProvider(context2).getIcon(activityInfo), i2);
            }
        };
        int integer = context.getResources().getInteger(R.integer.config_pipEnterAnimationDuration);
        this.mCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        this.mShadowRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
        setDuration(integer);
        setFloatValues(0.0f, 1.0f);
        setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        addListener(r2);
        addUpdateListener(r3);
    }

    public final void onEnterAnimationUpdate(float f, SurfaceControl.Transaction transaction) {
        PointF pointF = this.mInitScale;
        PointF pointF2 = this.mInitPos;
        Rect rect = this.mInitCrop;
        float f2 = 1.0f - f;
        float f3 = ((pointF.x - 1.0f) * f2) + 1.0f;
        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF.y, 1.0f, f2, 1.0f);
        float f4 = pointF2.x;
        Rect rect2 = this.mEndBounds;
        float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect2.left, f4, f, f4);
        float f5 = pointF2.y;
        float m$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rect2.top, f5, f, f5);
        int i = this.mRotation;
        if (i == 3) {
            i = -1;
        }
        Rect rect3 = new Rect(this.mEndBounds);
        rect3.offsetTo(0, 0);
        this.mRectEvaluator.evaluate(f, rect, rect3);
        transaction.setCrop(this.mLeash, this.mAnimatedRect);
        this.mTransformTensor.reset();
        this.mTransformTensor.setScale(f3, m$1);
        this.mTransformTensor.postTranslate(m$12, m$13);
        this.mTransformTensor.postRotate((-i) * 90.0f * f);
        transaction.setMatrix(this.mLeash, this.mTransformTensor, this.mMatrixTmp);
        transaction.setCornerRadius(this.mLeash, this.mCornerRadius).setShadowRadius(this.mLeash, this.mShadowRadius);
        PipAppIconOverlay pipAppIconOverlay = this.mContentOverlay;
        if (pipAppIconOverlay != null) {
            float f6 = 1.0f / f3;
            Rect rect4 = this.mEndBounds;
            pipAppIconOverlay.mTmpTransform.reset();
            pipAppIconOverlay.mTmpTransform.setScale(f6, f6);
            float f7 = pipAppIconOverlay.mOverlayHalfSize * f6;
            pipAppIconOverlay.mTmpTransform.postTranslate((rect4.width() / 2.0f) - f7, (rect4.height() / 2.0f) - f7);
            transaction.setMatrix(pipAppIconOverlay.mLeash, pipAppIconOverlay.mTmpTransform, pipAppIconOverlay.mTmpFloat9).setAlpha(pipAppIconOverlay.mLeash, f < 0.5f ? 0.0f : (f - 0.5f) * 2.0f);
        }
    }

    public final void setAppIconContentOverlay(Context context, Rect rect, Rect rect2, ActivityInfo activityInfo, int i) {
        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        PipAppIconOverlay pipAppIconOverlay = this.mContentOverlay;
        if (pipAppIconOverlay != null) {
            pipAppIconOverlay.detach(transaction);
        }
        PipAppIconOverlay pipAppIconOverlay2 = this.mPipAppIconOverlaySupplier.get(context, rect, rect2, activityInfo, i);
        this.mContentOverlay = pipAppIconOverlay2;
        pipAppIconOverlay2.attach(transaction, this.mLeash);
    }

    public final void setEnterStartState(TransitionInfo.Change change) {
        PictureInPictureParams pictureInPictureParams;
        PointF pointF = this.mInitScale;
        PointF pointF2 = this.mInitPos;
        Rect rect = this.mInitCrop;
        PipUtils pipUtils = PipUtils.INSTANCE;
        Rect startAbsBounds = change.getStartAbsBounds();
        Rect endAbsBounds = change.getEndAbsBounds();
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        Rect sourceRectHint = (taskInfo == null || (pictureInPictureParams = taskInfo.pictureInPictureParams) == null) ? null : pictureInPictureParams.getSourceRectHint();
        if (sourceRectHint == null) {
            sourceRectHint = new Rect(startAbsBounds);
            sourceRectHint.offsetTo(0, 0);
        }
        float width = endAbsBounds.width() / sourceRectHint.width();
        float height = endAbsBounds.height() / sourceRectHint.height();
        float f = 1.0f / width;
        float f2 = 1.0f / height;
        float f3 = 0;
        pointF.set(f, f2);
        pointF2.set((f3 * f) + startAbsBounds.left + sourceRectHint.left, (f3 * f2) + startAbsBounds.top + sourceRectHint.top);
        PipUtils.INSTANCE.getClass();
        rect.left = PipUtils.roundOut(f3 - (sourceRectHint.left * width));
        rect.top = PipUtils.roundOut(f3 - (sourceRectHint.top * height));
        rect.right = PipUtils.roundOut((startAbsBounds.width() * width) + rect.left);
        rect.bottom = PipUtils.roundOut((startAbsBounds.height() * height) + rect.top);
    }

    public void setPipAppIconOverlaySupplier(PipAppIconOverlaySupplier pipAppIconOverlaySupplier) {
        this.mPipAppIconOverlaySupplier = pipAppIconOverlaySupplier;
    }

    public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
        this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
    }
}
