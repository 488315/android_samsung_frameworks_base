package com.android.p038wm.shell.pip;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.TaskInfo;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.util.RotationUtils;
import android.view.SurfaceControl;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.p038wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p038wm.shell.pip.PipTransition;
import com.android.p038wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipAnimationController {
    public PipTransitionAnimator mCurrentAnimator;
    public long mLastOneShotAlphaAnimationTime;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;
    public final ThreadLocal mSfAnimationHandlerThreadLocal = ThreadLocal.withInitial(new PipAnimationController$$ExternalSyntheticLambda0());
    public int mOneShotAnimationType = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PipTransactionHandler {
        public boolean handlePipTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect, float f) {
            return this instanceof PipTransition.C40441;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class PipTransitionAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        public static final float[] PIP_BACKGROUND_COLOR = {0.0f, 0.0f, 0.0f};
        public final int mAnimationType;
        public boolean mBackgroundColorApplied;
        public final Object mBaseValue;
        public PipContentOverlay mContentOverlay;
        public Object mCurrentValue;
        public final Rect mDestinationBounds;
        public Object mEndValue;
        public boolean mHasRequestedEnd;
        public final SurfaceControl mLeash;
        public PipAnimationCallback mPipAnimationCallback;
        public PipTransactionHandler mPipTransactionHandler;
        public Object mStartValue;
        public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
        public PipSurfaceTransactionHelper mSurfaceTransactionHelper;
        public final TaskInfo mTaskInfo;
        public int mTransitionDirection;

        public /* synthetic */ PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Object obj, Object obj2, Object obj3, int i2) {
            this(taskInfo, surfaceControl, i, rect, obj, obj2, obj3);
        }

        /* JADX WARN: Type inference failed for: r22v0, types: [com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator$2] */
        public static C40362 ofBounds(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, final Rect rect3, final Rect rect4, final int i, final float f, final int i2) {
            Rect rect5;
            final Rect rect6;
            Rect rect7;
            final boolean isOutPipDirection = PipAnimationController.isOutPipDirection(i);
            final boolean isInPipDirection = PipAnimationController.isInPipDirection(i);
            final Rect rect8 = isOutPipDirection ? new Rect(rect3) : new Rect(rect);
            if (i2 == 1 || i2 == 3) {
                Rect rect9 = new Rect(rect3);
                Rect rect10 = new Rect(rect3);
                RotationUtils.rotateBounds(rect10, rect8, i2);
                rect5 = rect9;
                rect6 = rect10;
                rect7 = isOutPipDirection ? rect10 : rect8;
            } else {
                rect6 = null;
                rect5 = null;
                rect7 = rect8;
            }
            final Rect rect11 = rect4 == null ? null : new Rect(rect4.left - rect7.left, rect4.top - rect7.top, rect7.right - rect4.right, rect7.bottom - rect4.bottom);
            final Rect rect12 = new Rect(0, 0, 0, 0);
            final Rect rect13 = rect7;
            final Rect rect14 = rect5;
            return new PipTransitionAnimator(taskInfo, surfaceControl, 0, rect3, new Rect(rect), new Rect(rect2), new Rect(rect3)) { // from class: com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.2
                public final RectEvaluator mRectEvaluator = new RectEvaluator(new Rect());
                public final RectEvaluator mInsetsEvaluator = new RectEvaluator(new Rect());

                {
                    int i3 = 0;
                }

                /* JADX WARN: Removed duplicated region for block: B:20:0x00f5  */
                /* JADX WARN: Removed duplicated region for block: B:23:0x0112  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0122  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x012c  */
                /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:32:0x0129  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x00f7  */
                /* JADX WARN: Removed duplicated region for block: B:79:0x0266  */
                /* JADX WARN: Removed duplicated region for block: B:81:0x0270  */
                /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:84:0x026d  */
                /* JADX WARN: Removed duplicated region for block: B:97:0x023a  */
                /* JADX WARN: Removed duplicated region for block: B:98:0x0249  */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void applySurfaceControlTransaction(float f2, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    boolean z;
                    boolean z2;
                    PipTransactionHandler pipTransactionHandler;
                    Rect evaluate;
                    float max;
                    float height;
                    int height2;
                    float height3;
                    int height4;
                    Rect evaluate2;
                    float f3;
                    float f4;
                    int i3;
                    int i4;
                    float f5;
                    float f6;
                    float f7;
                    float f8;
                    float f9;
                    PipTransactionHandler pipTransactionHandler2;
                    Rect rect15 = (Rect) this.mBaseValue;
                    Rect rect16 = (Rect) this.mStartValue;
                    Rect rect17 = (Rect) getEndValue();
                    Rect evaluate3 = this.mRectEvaluator.evaluate(f2, rect16, rect17);
                    PipContentOverlay pipContentOverlay = this.mContentOverlay;
                    if (pipContentOverlay != null) {
                        pipContentOverlay.onAnimationUpdate(transaction, evaluate3, f2);
                    }
                    if (rect6 != null) {
                        if (!rect17.equals(rect14)) {
                            rect6.set(rect3);
                            RotationUtils.rotateBounds(rect6, rect8, i2);
                            rect14.set(rect17);
                        }
                        Rect evaluate4 = this.mRectEvaluator.evaluate(f2, rect16, rect6);
                        this.mCurrentValue = evaluate4;
                        Rect rect18 = rect11;
                        if (rect18 == null) {
                            evaluate2 = rect12;
                        } else {
                            boolean z3 = isOutPipDirection;
                            Rect rect19 = z3 ? rect18 : rect12;
                            if (z3) {
                                rect18 = rect12;
                            }
                            evaluate2 = this.mInsetsEvaluator.evaluate(f2, rect19, rect18);
                        }
                        Rect rect20 = evaluate2;
                        if (!Transitions.SHELL_TRANSITIONS_ROTATION) {
                            if (i2 == 1) {
                                f3 = f2 * 90.0f;
                                int i5 = rect17.right;
                                f4 = ((i5 - r7) * f2) + rect16.left;
                                i3 = rect17.top;
                                i4 = rect16.top;
                            } else {
                                f3 = f2 * (-90.0f);
                                int i6 = rect17.left;
                                f4 = ((i6 - r7) * f2) + rect16.left;
                                i3 = rect17.bottom;
                                i4 = rect16.top;
                            }
                            f5 = f3;
                            f6 = f2 * (i3 - i4);
                            f7 = i4;
                        } else {
                            if (i2 == 1) {
                                float f10 = 1.0f - f2;
                                int i7 = rect17.left;
                                f9 = (rect16.width() * f10) + ((i7 - r8) * f2) + rect16.left;
                                int i8 = rect17.top;
                                f8 = (f2 * (i8 - r2)) + rect16.top;
                                f5 = 90.0f * f10;
                                Rect rect21 = new Rect(rect13);
                                rect21.inset(rect20);
                                this.mSurfaceTransactionHelper.rotateAndScaleWithCrop(transaction, surfaceControl2, rect13, evaluate4, rect20, f5, f9, f8, isOutPipDirection, i2 != 3);
                                if (!PipAnimationController.isOutPipDirection(this.mTransitionDirection)) {
                                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                                    pipSurfaceTransactionHelper.round(rect21, evaluate4, transaction, surfaceControl2);
                                    pipSurfaceTransactionHelper.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                                }
                                pipTransactionHandler2 = this.mPipTransactionHandler;
                                if (pipTransactionHandler2 == null ? pipTransactionHandler2.handlePipTransaction(surfaceControl2, transaction, evaluate4, 1.0f) : false) {
                                    transaction.apply();
                                    return;
                                }
                                return;
                            }
                            float f11 = 1.0f - f2;
                            f5 = (-90.0f) * f11;
                            int i9 = rect17.left;
                            f4 = ((i9 - r8) * f2) + rect16.left;
                            int i10 = rect17.top;
                            f6 = (f2 * (i10 - r8)) + rect16.top;
                            f7 = rect16.height() * f11;
                        }
                        f8 = f7 + f6;
                        f9 = f4;
                        Rect rect212 = new Rect(rect13);
                        rect212.inset(rect20);
                        this.mSurfaceTransactionHelper.rotateAndScaleWithCrop(transaction, surfaceControl2, rect13, evaluate4, rect20, f5, f9, f8, isOutPipDirection, i2 != 3);
                        if (!PipAnimationController.isOutPipDirection(this.mTransitionDirection)) {
                        }
                        pipTransactionHandler2 = this.mPipTransactionHandler;
                        if (pipTransactionHandler2 == null ? pipTransactionHandler2.handlePipTransaction(surfaceControl2, transaction, evaluate4, 1.0f) : false) {
                        }
                    } else {
                        float f12 = 1.0f - f2;
                        float f13 = f * f12;
                        this.mCurrentValue = evaluate3;
                        if (this.mAnimationType == 0) {
                            int transitionDirection = getTransitionDirection();
                            if (!PipAnimationController.isInPipDirection(transitionDirection) && !PipAnimationController.isOutPipDirection(transitionDirection)) {
                                z = true;
                                if (!z || rect4 == null) {
                                    z2 = false;
                                    if (isOutPipDirection) {
                                        PipSurfaceTransactionHelper pipSurfaceTransactionHelper2 = this.mSurfaceTransactionHelper;
                                        pipSurfaceTransactionHelper2.crop(rect15, transaction, surfaceControl2);
                                        pipSurfaceTransactionHelper2.scale(f13, rect15, evaluate3, transaction, surfaceControl2);
                                        pipSurfaceTransactionHelper2.round(rect15, evaluate3, transaction, surfaceControl2);
                                        pipSurfaceTransactionHelper2.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                                    } else {
                                        PipSurfaceTransactionHelper pipSurfaceTransactionHelper3 = this.mSurfaceTransactionHelper;
                                        pipSurfaceTransactionHelper3.crop(rect17, transaction, surfaceControl2);
                                        pipSurfaceTransactionHelper3.scale(0.0f, rect17, evaluate3, transaction, surfaceControl2);
                                    }
                                } else {
                                    Rect rect22 = rect11;
                                    if (rect22 == null) {
                                        evaluate = rect12;
                                    } else {
                                        boolean z4 = isOutPipDirection;
                                        Rect rect23 = z4 ? rect22 : rect12;
                                        if (z4) {
                                            rect22 = rect12;
                                        }
                                        evaluate = this.mInsetsEvaluator.evaluate(f2, rect23, rect22);
                                    }
                                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper4 = this.mSurfaceTransactionHelper;
                                    Rect rect24 = rect4;
                                    Rect rect25 = rect8;
                                    boolean z5 = isInPipDirection;
                                    Rect rect26 = pipSurfaceTransactionHelper4.mTmpDestinationRect;
                                    rect26.set(rect25);
                                    z2 = false;
                                    rect26.offsetTo(0, 0);
                                    rect26.inset(evaluate);
                                    if (!z5 || rect24 == null || rect24.width() >= rect25.width()) {
                                        max = Math.max(evaluate3.width() / rect25.width(), evaluate3.height() / rect25.height());
                                    } else {
                                        if (rect25.width() <= rect25.height()) {
                                            height = evaluate3.width();
                                            height2 = rect24.width();
                                        } else {
                                            height = evaluate3.height();
                                            height2 = rect24.height();
                                        }
                                        float f14 = height / height2;
                                        if (rect25.width() <= rect25.height()) {
                                            height3 = evaluate3.width();
                                            height4 = rect25.width();
                                        } else {
                                            height3 = evaluate3.height();
                                            height4 = rect25.height();
                                        }
                                        max = (f2 * f14) + ((height3 / height4) * f12);
                                    }
                                    Matrix matrix = pipSurfaceTransactionHelper4.mTmpTransform;
                                    matrix.setScale(max, max);
                                    transaction.setMatrix(surfaceControl2, matrix, pipSurfaceTransactionHelper4.mTmpFloat9).setCrop(surfaceControl2, rect26).setPosition(surfaceControl2, evaluate3.left - (evaluate.left * max), evaluate3.top - (evaluate.top * max));
                                    if (!PipAnimationController.isOutPipDirection(this.mTransitionDirection)) {
                                        Rect rect27 = new Rect(rect13);
                                        rect27.inset(evaluate);
                                        PipSurfaceTransactionHelper pipSurfaceTransactionHelper5 = this.mSurfaceTransactionHelper;
                                        pipSurfaceTransactionHelper5.round(rect27, evaluate3, transaction, surfaceControl2);
                                        pipSurfaceTransactionHelper5.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                                    }
                                }
                                pipTransactionHandler = this.mPipTransactionHandler;
                                if (pipTransactionHandler == null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, evaluate3, 1.0f) : z2) {
                                    transaction.apply();
                                    return;
                                }
                                return;
                            }
                        }
                        z = false;
                        if (z) {
                        }
                        z2 = false;
                        if (isOutPipDirection) {
                        }
                        pipTransactionHandler = this.mPipTransactionHandler;
                        if (pipTransactionHandler == null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, evaluate3, 1.0f) : z2) {
                        }
                    }
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void onEndTransaction(int i3, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    if (this.mBackgroundColorApplied) {
                        this.mBackgroundColorApplied = false;
                        transaction.unsetColor(surfaceControl2);
                        transaction.apply();
                        Log.d("PipTaskOrganizer", "PipTransitionAnimator_onEndTransaction: unsetColor, dir=" + i3);
                    }
                    Rect rect15 = this.mDestinationBounds;
                    this.mSurfaceTransactionHelper.resetScale(rect15, transaction, surfaceControl2);
                    if (PipAnimationController.isOutPipDirection(i3)) {
                        transaction.setMatrix(surfaceControl2, 1.0f, 0.0f, 0.0f, 1.0f);
                        if (CoreRune.MW_PIP_SHELL_TRANSITION && i3 == 3) {
                            transaction.setPosition(surfaceControl2, rect15.left, rect15.top);
                        } else {
                            transaction.setPosition(surfaceControl2, 0.0f, 0.0f);
                        }
                        transaction.setWindowCrop(surfaceControl2, 0, 0);
                    } else {
                        this.mSurfaceTransactionHelper.crop(rect15, transaction, surfaceControl2);
                    }
                    PipContentOverlay pipContentOverlay = this.mContentOverlay;
                    if (pipContentOverlay != null) {
                        this.mSurfaceTransactionHelper.resetScale(rect15, transaction, pipContentOverlay.mLeash);
                        this.mContentOverlay.onAnimationEnd(transaction);
                    }
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void onStartTransaction(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.getClass();
                    transaction.setAlpha(surfaceControl2, 1.0f);
                    pipSurfaceTransactionHelper.round(surfaceControl2, !PipAnimationController.isOutPipDirection(this.mTransitionDirection), transaction);
                    pipSurfaceTransactionHelper.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                    if (PipAnimationController.isInPipDirection(i)) {
                        transaction.setWindowCrop(surfaceControl2, (Rect) this.mStartValue);
                    }
                    if (PipAnimationController.isOutPipDirection(i)) {
                        this.mBackgroundColorApplied = true;
                        transaction.setColor(surfaceControl2, PipTransitionAnimator.PIP_BACKGROUND_COLOR);
                        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("PipTransitionAnimator_onStartTransaction: setColor, dir="), i, "PipTaskOrganizer");
                    }
                    transaction.show(surfaceControl2);
                    transaction.apply();
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void updateEndValue(Object obj) {
                    Object obj2;
                    this.mEndValue = (Rect) obj;
                    Object obj3 = this.mStartValue;
                    if (obj3 == null || (obj2 = this.mCurrentValue) == null) {
                        return;
                    }
                    ((Rect) obj3).set((Rect) obj2);
                }
            };
        }

        public abstract void applySurfaceControlTransaction(float f, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

        public int getAnimationType() {
            return this.mAnimationType;
        }

        public final SurfaceControl getContentOverlayLeash() {
            PipContentOverlay pipContentOverlay = this.mContentOverlay;
            if (pipContentOverlay == null) {
                return null;
            }
            return pipContentOverlay.mLeash;
        }

        public Object getEndValue() {
            return this.mEndValue;
        }

        public int getTransitionDirection() {
            return this.mTransitionDirection;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationCancel(this.mTaskInfo, this);
            }
            this.mTransitionDirection = 0;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.mHasRequestedEnd) {
                return;
            }
            this.mHasRequestedEnd = true;
            this.mCurrentValue = this.mEndValue;
            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            onEndTransaction(this.mTransitionDirection, transaction, this.mLeash);
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationEnd(this.mTaskInfo, transaction, this);
            }
            this.mTransitionDirection = 0;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            this.mCurrentValue = this.mStartValue;
            onStartTransaction(((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction(), this.mLeash);
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationStart(this);
            }
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (this.mHasRequestedEnd) {
                return;
            }
            SurfaceControl surfaceControl = this.mLeash;
            applySurfaceControlTransaction(valueAnimator.getAnimatedFraction(), ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction(), surfaceControl);
        }

        public final void reattachContentOverlay(PipContentOverlay pipContentOverlay) {
            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            PipContentOverlay pipContentOverlay2 = this.mContentOverlay;
            if (pipContentOverlay2 != null) {
                pipContentOverlay2.detach(transaction);
            }
            this.mContentOverlay = pipContentOverlay;
            pipContentOverlay.attach(transaction, this.mLeash);
        }

        public final void setDestinationBounds(Rect rect) {
            this.mDestinationBounds.set(rect);
            if (this.mAnimationType == 1) {
                onStartTransaction(((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction(), this.mLeash);
            }
        }

        public PipTransitionAnimator setPipAnimationCallback(PipAnimationCallback pipAnimationCallback) {
            this.mPipAnimationCallback = pipAnimationCallback;
            return this;
        }

        public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
            this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
        }

        public PipTransitionAnimator setTransitionDirection(int i) {
            if (i != 1) {
                this.mTransitionDirection = i;
            }
            return this;
        }

        public final boolean shouldApplyShadowRadius() {
            if (PipAnimationController.isOutPipDirection(this.mTransitionDirection)) {
                return false;
            }
            return !(this.mTransitionDirection == 5);
        }

        public void updateEndValue(Object obj) {
            this.mEndValue = obj;
        }

        private PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Object obj, Object obj2, Object obj3) {
            Rect rect2 = new Rect();
            this.mDestinationBounds = rect2;
            this.mTaskInfo = taskInfo;
            this.mLeash = surfaceControl;
            this.mAnimationType = i;
            rect2.set(rect);
            this.mBaseValue = obj;
            this.mStartValue = obj2;
            this.mEndValue = obj3;
            addListener(this);
            addUpdateListener(this);
            this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
            this.mTransitionDirection = 0;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        public void onStartTransaction(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        }

        public void onEndTransaction(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        }
    }

    public PipAnimationController(PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
    }

    public static boolean isInPipDirection(int i) {
        return i == 2;
    }

    public static boolean isOutPipDirection(int i) {
        return i == 3 || i == 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0022, code lost:
    
        if (((java.lang.Float) r0.getEndValue()).floatValue() == 1.0f) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void quietCancel(ValueAnimator valueAnimator) {
        boolean z;
        if ((valueAnimator instanceof PipTransitionAnimator) && valueAnimator.isRunning()) {
            PipTransitionAnimator pipTransitionAnimator = (PipTransitionAnimator) valueAnimator;
            z = true;
            if (pipTransitionAnimator.getAnimationType() == 1) {
            }
        }
        z = false;
        if (z) {
            Log.d("PipTaskOrganizer", "PipAnimationController_quietCancel: Call end before cancel, reason=fade_in_aniamtor");
            valueAnimator.end();
        }
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.removeAllListeners();
        valueAnimator.cancel();
    }

    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, final Rect rect, float f, float f2) {
        Log.d("PipTaskOrganizer", "[PipAnimationController] getAnimator: dest=" + rect + ", " + f + "->" + f2 + ", Caller=" + Debug.getCallers(7));
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            float[] fArr = PipTransitionAnimator.PIP_BACKGROUND_COLOR;
            PipTransitionAnimator pipTransitionAnimator2 = new PipTransitionAnimator(taskInfo, surfaceControl, 1, rect, Float.valueOf(f), Float.valueOf(f), Float.valueOf(f2)) { // from class: com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.1
                {
                    int i = 0;
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void applySurfaceControlTransaction(float f3, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    float floatValue = (((Float) getEndValue()).floatValue() * f3) + ((1.0f - f3) * ((Float) this.mStartValue).floatValue());
                    this.mCurrentValue = Float.valueOf(floatValue);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.getClass();
                    transaction.setAlpha(surfaceControl2, floatValue);
                    pipSurfaceTransactionHelper.round(surfaceControl2, !PipAnimationController.isOutPipDirection(this.mTransitionDirection), transaction);
                    pipSurfaceTransactionHelper.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                    Rect rect2 = rect;
                    PipTransactionHandler pipTransactionHandler = this.mPipTransactionHandler;
                    if (pipTransactionHandler != null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, rect2, floatValue) : false) {
                        return;
                    }
                    transaction.apply();
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void onStartTransaction(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    if (getTransitionDirection() == 5) {
                        return;
                    }
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.resetScale(this.mDestinationBounds, transaction, surfaceControl2);
                    pipSurfaceTransactionHelper.crop(this.mDestinationBounds, transaction, surfaceControl2);
                    pipSurfaceTransactionHelper.round(surfaceControl2, !PipAnimationController.isOutPipDirection(this.mTransitionDirection), transaction);
                    pipSurfaceTransactionHelper.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                    transaction.show(surfaceControl2);
                    transaction.apply();
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void updateEndValue(Object obj) {
                    this.mEndValue = (Float) obj;
                    this.mStartValue = this.mCurrentValue;
                }
            };
            setupPipTransitionAnimator(pipTransitionAnimator2);
            this.mCurrentAnimator = pipTransitionAnimator2;
        } else if (pipTransitionAnimator.getAnimationType() == 1 && Objects.equals(rect, this.mCurrentAnimator.mDestinationBounds) && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.updateEndValue(Float.valueOf(f2));
        } else {
            this.mCurrentAnimator.cancel();
            PipTransitionAnimator pipTransitionAnimator3 = new PipTransitionAnimator(taskInfo, surfaceControl, 1, rect, Float.valueOf(f), Float.valueOf(f), Float.valueOf(f2)) { // from class: com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.1
                {
                    int i = 0;
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void applySurfaceControlTransaction(float f3, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    float floatValue = (((Float) getEndValue()).floatValue() * f3) + ((1.0f - f3) * ((Float) this.mStartValue).floatValue());
                    this.mCurrentValue = Float.valueOf(floatValue);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.getClass();
                    transaction.setAlpha(surfaceControl2, floatValue);
                    pipSurfaceTransactionHelper.round(surfaceControl2, !PipAnimationController.isOutPipDirection(this.mTransitionDirection), transaction);
                    pipSurfaceTransactionHelper.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                    Rect rect2 = rect;
                    PipTransactionHandler pipTransactionHandler = this.mPipTransactionHandler;
                    if (pipTransactionHandler != null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, rect2, floatValue) : false) {
                        return;
                    }
                    transaction.apply();
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void onStartTransaction(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    if (getTransitionDirection() == 5) {
                        return;
                    }
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.resetScale(this.mDestinationBounds, transaction, surfaceControl2);
                    pipSurfaceTransactionHelper.crop(this.mDestinationBounds, transaction, surfaceControl2);
                    pipSurfaceTransactionHelper.round(surfaceControl2, !PipAnimationController.isOutPipDirection(this.mTransitionDirection), transaction);
                    pipSurfaceTransactionHelper.shadow(surfaceControl2, shouldApplyShadowRadius(), transaction);
                    transaction.show(surfaceControl2);
                    transaction.apply();
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void updateEndValue(Object obj) {
                    this.mEndValue = (Float) obj;
                    this.mStartValue = this.mCurrentValue;
                }
            };
            setupPipTransitionAnimator(pipTransitionAnimator3);
            this.mCurrentAnimator = pipTransitionAnimator3;
        }
        return this.mCurrentAnimator;
    }

    public final void setupPipTransitionAnimator(PipTransitionAnimator pipTransitionAnimator) {
        pipTransitionAnimator.mSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipTransitionAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        pipTransitionAnimator.setFloatValues(0.0f, 1.0f);
        pipTransitionAnimator.setAnimationHandler((AnimationHandler) this.mSfAnimationHandlerThreadLocal.get());
    }

    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2) {
        return getAnimator(taskInfo, surfaceControl, rect, rect2, rect3, rect4, i, f, i2, null);
    }

    public final PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2, Rect rect5) {
        Log.d("PipTaskOrganizer", "[PipAnimationController] getAnimator direction=" + i + " endBounds=" + rect3 + " caller=" + Debug.getCallers(7));
        if (rect3.isEmpty() && rect5 != null) {
            Log.w("PipTaskOrganizer", "getAnimator destination empty, setDefaultBounds");
            rect3.set(rect5);
        }
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            PipTransitionAnimator.C40362 ofBounds = PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect2, rect2, rect3, rect4, i, 0.0f, i2);
            setupPipTransitionAnimator(ofBounds);
            this.mCurrentAnimator = ofBounds;
        } else if (pipTransitionAnimator.getAnimationType() == 1 && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.setDestinationBounds(rect3);
        } else if (this.mCurrentAnimator.getAnimationType() == 0 && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.setDestinationBounds(rect3);
            this.mCurrentAnimator.updateEndValue(new Rect(rect3));
        } else {
            this.mCurrentAnimator.cancel();
            PipTransitionAnimator.C40362 ofBounds2 = PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect, rect2, rect3, rect4, i, f, i2);
            setupPipTransitionAnimator(ofBounds2);
            this.mCurrentAnimator = ofBounds2;
        }
        return this.mCurrentAnimator;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PipAnimationCallback {
        public void onPipAnimationStart(PipTransitionAnimator pipTransitionAnimator) {
        }

        public void onPipAnimationCancel(TaskInfo taskInfo, PipTransitionAnimator pipTransitionAnimator) {
        }

        public void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipTransitionAnimator pipTransitionAnimator) {
        }
    }
}
