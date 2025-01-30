package com.android.wm.shell.onehanded;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.BaseInterpolator;
import android.window.WindowContainerToken;
import androidx.constraintlayout.motion.widget.MotionPaths$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedAnimationController {
    public final HashMap mAnimatorMap = new HashMap();
    public final OneHandedInterpolator mInterpolator = new OneHandedInterpolator(this);
    public final OneHandedSurfaceTransactionHelper mSurfaceTransactionHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OneHandedInterpolator extends BaseInterpolator {
        public OneHandedInterpolator(OneHandedAnimationController oneHandedAnimationController) {
        }

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return (float) ((Math.sin((((f - 4.0f) / 4.0f) * 6.283185307179586d) / 4.0d) * Math.pow(2.0d, (-10.0f) * f)) + 1.0d);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OneHandedTransitionAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        public float mCurrentValue;
        public float mEndValue;
        public final SurfaceControl mLeash;
        public final List mOneHandedAnimationCallbacks;
        public final float mStartValue;
        public final ViewCompat$$ExternalSyntheticLambda0 mSurfaceControlTransactionFactory;
        public OneHandedSurfaceTransactionHelper mSurfaceTransactionHelper;
        public final WindowContainerToken mToken;
        public int mTransitionDirection;

        public /* synthetic */ OneHandedTransitionAnimator(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl, float f, float f2, int i) {
            this(windowContainerToken, surfaceControl, f, f2);
        }

        public static OneHandedTransitionAnimator ofYOffset(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl, float f, float f2, Rect rect) {
            return new OneHandedTransitionAnimator(windowContainerToken, surfaceControl, f, f2, rect) { // from class: com.android.wm.shell.onehanded.OneHandedAnimationController.OneHandedTransitionAnimator.1
                public final Rect mTmpRect;

                {
                    int i = 0;
                    this.mTmpRect = new Rect(rect);
                }

                @Override // com.android.wm.shell.onehanded.OneHandedAnimationController.OneHandedTransitionAnimator
                public final void applySurfaceControlTransaction(float f3, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    float m24m = MotionPaths$$ExternalSyntheticOutline0.m24m(this.mEndValue, f3, (1.0f - f3) * this.mStartValue, 0.5f);
                    Rect rect2 = this.mTmpRect;
                    int i = rect2.left;
                    int round = Math.round(m24m) + rect2.top;
                    Rect rect3 = this.mTmpRect;
                    rect2.set(i, round, rect3.right, Math.round(m24m) + rect3.bottom);
                    this.mCurrentValue = m24m;
                    OneHandedSurfaceTransactionHelper oneHandedSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    Rect rect4 = this.mTmpRect;
                    oneHandedSurfaceTransactionHelper.getClass();
                    transaction.setWindowCrop(surfaceControl2, rect4.width(), rect4.height());
                    if (oneHandedSurfaceTransactionHelper.mEnableCornerRadius) {
                        transaction.setCornerRadius(surfaceControl2, oneHandedSurfaceTransactionHelper.mCornerRadius);
                    }
                    transaction.setPosition(surfaceControl2, 0.0f, m24m);
                    transaction.apply();
                }
            };
        }

        public abstract void applySurfaceControlTransaction(float f, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mCurrentValue = this.mEndValue;
            ((ArrayList) this.mOneHandedAnimationCallbacks).forEach(new C4023x55a9da47(this, 1));
            ((ArrayList) this.mOneHandedAnimationCallbacks).clear();
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            this.mCurrentValue = this.mEndValue;
            getClass();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            ((ArrayList) this.mOneHandedAnimationCallbacks).forEach(new C4024x55a9da48(this, transaction, 0));
            ((ArrayList) this.mOneHandedAnimationCallbacks).clear();
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            this.mCurrentValue = this.mStartValue;
            ((ArrayList) this.mOneHandedAnimationCallbacks).forEach(new C4023x55a9da47(this, 0));
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            getClass();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            ((ArrayList) this.mOneHandedAnimationCallbacks).forEach(new C4024x55a9da48(this, transaction, 1));
            applySurfaceControlTransaction(valueAnimator.getAnimatedFraction(), transaction, this.mLeash);
        }

        private OneHandedTransitionAnimator(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl, float f, float f2) {
            this.mOneHandedAnimationCallbacks = new ArrayList();
            this.mLeash = surfaceControl;
            this.mToken = windowContainerToken;
            this.mStartValue = f;
            this.mEndValue = f2;
            addListener(this);
            addUpdateListener(this);
            this.mSurfaceControlTransactionFactory = new ViewCompat$$ExternalSyntheticLambda0();
            this.mTransitionDirection = 0;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }

    public OneHandedAnimationController(Context context) {
        this.mSurfaceTransactionHelper = new OneHandedSurfaceTransactionHelper(context);
    }
}
