package com.android.wm.shell.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.transition.TransitionAnimationHelper;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DefaultSurfaceAnimator {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class AnimationAdapter implements ValueAnimator.AnimatorUpdateListener {
        public Choreographer mChoreographer;
        public final SurfaceControl mLeash;
        public SurfaceControl.Transaction mTransaction;

        public AnimationAdapter(SurfaceControl surfaceControl) {
            this.mLeash = surfaceControl;
        }

        public abstract void applyTransformation(long j, ValueAnimator valueAnimator);

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            applyTransformation(valueAnimator.getAnimatedFraction() >= 1.0f ? valueAnimator.getDuration() : Math.min(valueAnimator.getDuration(), valueAnimator.getCurrentPlayTime()), valueAnimator);
            if (this.mChoreographer == null) {
                this.mChoreographer = Choreographer.getInstance();
            }
            this.mTransaction.setFrameTimelineVsync(this.mChoreographer.getVsyncId());
            this.mTransaction.apply();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultAnimationAdapter extends AnimationAdapter {
        public final Animation mAnim;
        public final Rect mAnimClipRect;
        public final Rect mClipRect;
        public final float mCornerRadius;
        public final float[] mMatrix;
        public final Point mPosition;
        public final TransitionAnimationHelper.RoundedContentPerDisplay mRoundedContentBounds;
        public final Transformation mTransformation;
        public final int mWindowBottom;

        public DefaultAnimationAdapter(Animation animation, SurfaceControl surfaceControl, Point point, Rect rect, float f, TransitionAnimationHelper.RoundedContentPerDisplay roundedContentPerDisplay) {
            super(surfaceControl);
            this.mTransformation = new Transformation();
            this.mMatrix = new float[9];
            this.mAnim = animation;
            if (point == null || (point.x == 0 && point.y == 0)) {
                point = null;
            }
            this.mPosition = point;
            Rect rect2 = (rect == null || rect.isEmpty()) ? null : rect;
            this.mClipRect = rect2;
            this.mAnimClipRect = rect2 != null ? new Rect() : null;
            this.mCornerRadius = f;
            this.mWindowBottom = rect != null ? rect.bottom : 0;
            this.mRoundedContentBounds = roundedContentPerDisplay;
        }

        @Override // com.android.wm.shell.transition.DefaultSurfaceAnimator.AnimationAdapter
        public final void applyTransformation(long j, ValueAnimator valueAnimator) {
            boolean z;
            Transformation transformation = this.mTransformation;
            SurfaceControl.Transaction transaction = this.mTransaction;
            SurfaceControl surfaceControl = this.mLeash;
            transformation.clear();
            this.mAnim.getTransformation(j, transformation);
            if (this.mPosition != null) {
                Matrix matrix = transformation.getMatrix();
                Point point = this.mPosition;
                matrix.postTranslate(point.x, point.y);
            }
            transaction.setMatrix(surfaceControl, transformation.getMatrix(), this.mMatrix);
            transaction.setAlpha(surfaceControl, transformation.getAlpha());
            Rect rect = this.mClipRect;
            if (rect != null) {
                TransitionAnimationHelper.RoundedContentPerDisplay roundedContentPerDisplay = this.mRoundedContentBounds;
                if (roundedContentPerDisplay != null) {
                    rect.bottom = Math.min(roundedContentPerDisplay.mBounds.bottom, this.mWindowBottom);
                }
                this.mAnimClipRect.set(this.mClipRect);
                boolean z2 = true;
                if (transformation.hasClipRect()) {
                    this.mAnimClipRect.intersectUnchecked(transformation.getClipRect());
                    z = true;
                } else {
                    z = false;
                }
                Insets insets = transformation.getInsets();
                Insets insets2 = Insets.NONE;
                Insets min = Insets.min(insets, insets2);
                if (!min.equals(insets2)) {
                    this.mAnimClipRect.inset(min);
                    z = true;
                }
                if (this.mCornerRadius <= 0.0f || !this.mAnim.hasRoundedCorners()) {
                    z2 = z;
                } else {
                    transaction.setCornerRadius(surfaceControl, this.mCornerRadius);
                }
                if (z2) {
                    transaction.setWindowCrop(surfaceControl, this.mAnimClipRect);
                }
            }
        }
    }

    public static void buildSurfaceAnimation(ArrayList arrayList, Animation animation, SurfaceControl surfaceControl, Runnable runnable, TransactionPool transactionPool, ShellExecutor shellExecutor, Point point, float f, Rect rect, TransitionAnimationHelper.RoundedContentPerDisplay roundedContentPerDisplay) {
        buildSurfaceAnimation(arrayList, animation, runnable, transactionPool, shellExecutor, new DefaultAnimationAdapter(animation, surfaceControl, point, rect, f, roundedContentPerDisplay));
    }

    public static void buildSurfaceAnimation(final ArrayList arrayList, Animation animation, final Runnable runnable, final TransactionPool transactionPool, final ShellExecutor shellExecutor, AnimationAdapter animationAdapter) {
        final SurfaceControl.Transaction acquire = transactionPool.acquire();
        animationAdapter.mTransaction = acquire;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.overrideDurationScale(1.0f);
        ofFloat.setDuration(animation.computeDurationHint());
        Consumer consumer = new Consumer() { // from class: com.android.wm.shell.transition.DefaultSurfaceAnimator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TransactionPool transactionPool2 = TransactionPool.this;
                SurfaceControl.Transaction transaction = acquire;
                ShellExecutor shellExecutor2 = shellExecutor;
                final ArrayList arrayList2 = arrayList;
                final Runnable runnable2 = runnable;
                final ValueAnimator valueAnimator = (ValueAnimator) obj;
                transactionPool2.release(transaction);
                shellExecutor2.execute(new Runnable() { // from class: com.android.wm.shell.transition.DefaultSurfaceAnimator$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayList arrayList3 = arrayList2;
                        ValueAnimator valueAnimator2 = valueAnimator;
                        Runnable runnable3 = runnable2;
                        arrayList3.remove(valueAnimator2);
                        runnable3.run();
                    }
                });
            }
        };
        ofFloat.addUpdateListener(animationAdapter);
        ofFloat.addListener(new AnonymousClass1(ofFloat, consumer, animationAdapter));
        arrayList.add(ofFloat);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.transition.DefaultSurfaceAnimator$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public boolean mFinished;
        public final /* synthetic */ Consumer val$afterFinish;
        public final /* synthetic */ ValueAnimator val$animator;
        public final /* synthetic */ ValueAnimator.AnimatorUpdateListener val$updateListener;

        public AnonymousClass1(ValueAnimator valueAnimator, Consumer consumer, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
            this.val$animator = valueAnimator;
            this.val$afterFinish = consumer;
            this.val$updateListener = animatorUpdateListener;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            onFinish();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            onFinish();
        }

        public final void onFinish() {
            if (this.mFinished) {
                return;
            }
            this.mFinished = true;
            if (this.val$animator.getAnimatedFraction() < 1.0f) {
                this.val$animator.setCurrentFraction(1.0f);
            }
            this.val$afterFinish.accept(this.val$animator);
            this.val$animator.removeUpdateListener(this.val$updateListener);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }
    }
}
