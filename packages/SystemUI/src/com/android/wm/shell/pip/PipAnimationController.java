package com.android.wm.shell.pip;

import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.AppCompatTaskInfo;
import android.app.TaskInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.util.RotationUtils;
import android.view.SurfaceControl;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTransition;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.pip.PipContentOverlay;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* loaded from: classes3.dex */
public class PipAnimationController {
    public PipTransitionAnimator mCurrentAnimator;
    public long mLastOneShotAlphaAnimationTime;
    public int mOneShotAnimationType = 0;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;

    public class PipTransactionHandler {
        public boolean handlePipTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect, float f) {
            return this instanceof PipTransition.AnonymousClass1;
        }
    }

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
        public final Point mLeashOffset;
        public PipAnimationCallback mPipAnimationCallback;
        public PipTransactionHandler mPipTransactionHandler;
        public Object mStartValue;
        public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
        public PipSurfaceTransactionHelper mSurfaceTransactionHelper;
        public final TaskInfo mTaskInfo;
        public int mTransitionDirection;

        public /* synthetic */ PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Point point, Object obj, Object obj2, Object obj3, int i2) {
            this(taskInfo, surfaceControl, i, rect, point, obj, obj2, obj3);
        }

        /* JADX WARN: Type inference failed for: r0v5, types: [com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator$2] */
        public static AnonymousClass2 ofBounds(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, final Rect rect3, Rect rect4, final int i, final float f, final int i2, boolean z) {
            Rect rect5;
            final Rect rect6;
            final Rect rect7;
            Rect rect8;
            Rect rect9;
            Rect rect10;
            final boolean zIsOutPipDirection = PipAnimationController.isOutPipDirection(i);
            final boolean zIsInPipDirection = PipAnimationController.isInPipDirection(i);
            Rect rect11 = taskInfo.topActivityMainWindowFrame;
            AppCompatTaskInfo appCompatTaskInfo = taskInfo.appCompatTaskInfo;
            boolean z2 = true;
            boolean z3 = (rect11 == null || z || (appCompatTaskInfo.isTopActivityInSizeCompat() || appCompatTaskInfo.isTopActivityLetterboxed())) ? false : true;
            if (i2 != 1 && i2 != 3) {
                z2 = false;
            }
            Rect rect12 = new Rect(rect);
            Rect rect13 = new Rect(rect2);
            Rect rect14 = new Rect(rect3);
            if (zIsOutPipDirection) {
                if (z3 && !z2) {
                    rect14.set(rect11);
                }
                rect5 = new Rect(rect14);
            } else {
                if (zIsInPipDirection && z3) {
                    rect12.set(rect11);
                    if (rect2.equals(rect)) {
                        rect13.set(rect11);
                    }
                }
                rect5 = new Rect(rect12);
            }
            final Point point = (!zIsInPipDirection && zIsOutPipDirection) ? new Point(rect3.left, rect3.top) : new Point(rect.left, rect.top);
            if (z2) {
                Rect rect15 = new Rect(rect14);
                rect6 = new Rect(rect14);
                if (z3 && zIsOutPipDirection) {
                    rect6.set(rect11);
                } else {
                    RotationUtils.rotateBounds(rect6, rect5, i2);
                }
                rect8 = zIsOutPipDirection ? rect6 : rect5;
                rect7 = rect15;
            } else {
                rect6 = null;
                rect7 = null;
                rect8 = rect5;
            }
            final Rect rect16 = new Rect();
            if (rect4 != null && !rect4.isEmpty()) {
                rect16.set(rect4);
                if (PipAnimationController.isInPipDirection(i) && i2 == 0 && (rect10 = taskInfo.displayCutoutInsets) != null) {
                    rect16.offset(rect10.left, rect10.top);
                }
            } else if (PipAnimationController.isInPipDirection(i)) {
                rect16.set(PipUtils.getEnterPipWithOverlaySrcRectHint(rect13, rect14.width() / rect14.height()));
            }
            final Rect rect17 = new Rect();
            if (rect16.isEmpty()) {
                rect9 = rect5;
            } else {
                rect9 = rect5;
                rect17.set(rect16.left - rect8.left, rect16.top - rect8.top, rect8.right - rect16.right, rect8.bottom - rect16.bottom);
            }
            final Rect rect18 = new Rect(0, 0, 0, 0);
            final Rect rect19 = rect8;
            Rect rect20 = new Rect(rect12);
            Rect rect21 = new Rect(rect13);
            Rect rect22 = new Rect(rect14);
            final Rect rect23 = rect9;
            return new PipTransitionAnimator(taskInfo, surfaceControl, 0, rect14, point, rect20, rect21, rect22) { // from class: com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.2
                public final RectEvaluator mRectEvaluator = new RectEvaluator(new Rect());
                public final RectEvaluator mInsetsEvaluator = new RectEvaluator(new Rect());

                {
                    int i3 = 0;
                }

                /* JADX WARN: Removed duplicated region for block: B:100:0x0286  */
                /* JADX WARN: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:31:0x00df  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x00e3  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x00e7  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x0101  */
                /* JADX WARN: Removed duplicated region for block: B:40:0x0105  */
                /* JADX WARN: Removed duplicated region for block: B:43:0x0118  */
                /* JADX WARN: Removed duplicated region for block: B:44:0x011d  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x0120  */
                /* JADX WARN: Removed duplicated region for block: B:56:0x0144  */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void applySurfaceControlTransaction(SurfaceControl surfaceControl2, SurfaceControl.Transaction transaction, float f2) {
                    boolean z4;
                    float f3;
                    boolean z5;
                    float fMax;
                    float fHeight;
                    int iHeight;
                    float fHeight2;
                    int iHeight2;
                    float f4;
                    float fWidth;
                    int i3;
                    int i4;
                    float fHeight3;
                    Rect rect24;
                    PipTransactionHandler pipTransactionHandler;
                    Rect rect25 = (Rect) this.mBaseValue;
                    Rect rect26 = (Rect) this.mStartValue;
                    Rect rect27 = (Rect) getEndValue();
                    Rect rectEvaluate = this.mRectEvaluator.evaluate(f2, rect26, rect27);
                    PipContentOverlay pipContentOverlay = this.mContentOverlay;
                    if (pipContentOverlay != null) {
                        pipContentOverlay.onAnimationUpdate(transaction, rectEvaluate, f2);
                    }
                    if (rect6 == null) {
                        float f5 = 1.0f - f2;
                        float f6 = f * f5;
                        this.mCurrentValue = rectEvaluate;
                        if (this.mAnimationType != 0) {
                            z4 = false;
                        } else {
                            int transitionDirection = getTransitionDirection();
                            if (!PipAnimationController.isInPipDirection(transitionDirection) && !PipAnimationController.isOutPipDirection(transitionDirection)) {
                                z4 = true;
                            }
                        }
                        if (!z4 && !rect16.isEmpty()) {
                            boolean z6 = zIsOutPipDirection;
                            Rect rectEvaluate2 = this.mInsetsEvaluator.evaluate(f2, z6 ? rect17 : rect18, z6 ? rect18 : rect17);
                            PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                            Rect rect28 = rect16;
                            Rect rect29 = rect23;
                            boolean z7 = zIsInPipDirection;
                            Point point2 = point;
                            pipSurfaceTransactionHelper.mTmpDestinationRect.set(rect29);
                            pipSurfaceTransactionHelper.mTmpDestinationRect.offset(-point2.x, -point2.y);
                            pipSurfaceTransactionHelper.mTmpDestinationRect.inset(rectEvaluate2);
                            if (!z7 || rect28 == null || rect28.width() >= rect29.width()) {
                                fMax = Math.max(rectEvaluate.width() / rect29.width(), rectEvaluate.height() / rect29.height());
                            } else {
                                if (rect29.width() <= rect29.height()) {
                                    fHeight = rectEvaluate.width();
                                    iHeight = rect28.width();
                                } else {
                                    fHeight = rectEvaluate.height();
                                    iHeight = rect28.height();
                                }
                                float f7 = fHeight / iHeight;
                                if (rect29.width() <= rect29.height()) {
                                    fHeight2 = rectEvaluate.width();
                                    iHeight2 = rect29.width();
                                } else {
                                    fHeight2 = rectEvaluate.height();
                                    iHeight2 = rect29.height();
                                }
                                fMax = (f2 * f7) + (f5 * (fHeight2 / iHeight2));
                            }
                            float f8 = rectEvaluate.left;
                            Rect rect30 = pipSurfaceTransactionHelper.mTmpDestinationRect;
                            float f9 = f8 - (rect30.left * fMax);
                            float f10 = rectEvaluate.top - (rect30.top * fMax);
                            if (fMax == 1.0f) {
                                f9 = point2.x;
                                f10 = point2.y;
                            }
                            pipSurfaceTransactionHelper.mTmpTransform.setScale(fMax, fMax);
                            transaction.setMatrix(surfaceControl2, pipSurfaceTransactionHelper.mTmpTransform, pipSurfaceTransactionHelper.mTmpFloat9).setCrop(surfaceControl2, pipSurfaceTransactionHelper.mTmpDestinationRect).setPosition(surfaceControl2, f9, f10);
                            Rect rect31 = new Rect(rect19);
                            rect31.inset(rectEvaluate2);
                            PipSurfaceTransactionHelper pipSurfaceTransactionHelper2 = this.mSurfaceTransactionHelper;
                            pipSurfaceTransactionHelper2.round(transaction, surfaceControl2, rect31, rectEvaluate);
                            pipSurfaceTransactionHelper2.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                        } else if (zIsOutPipDirection) {
                            Rect rect32 = new Rect(rect27);
                            Point point3 = new Point(this.mLeashOffset);
                            rect32.offset(-point3.x, -point3.y);
                            PipSurfaceTransactionHelper pipSurfaceTransactionHelper3 = this.mSurfaceTransactionHelper;
                            pipSurfaceTransactionHelper3.getClass();
                            transaction.setCrop(surfaceControl2, rect32);
                            pipSurfaceTransactionHelper3.mTmpDestinationRectF.set(rectEvaluate);
                            pipSurfaceTransactionHelper3.scale(transaction, surfaceControl2, rect32, pipSurfaceTransactionHelper3.mTmpDestinationRectF, 0.0f, false);
                        } else {
                            PipSurfaceTransactionHelper pipSurfaceTransactionHelper4 = this.mSurfaceTransactionHelper;
                            pipSurfaceTransactionHelper4.cropAndPosition(rect25, transaction, surfaceControl2);
                            if (this.mAnimationType != 0) {
                                f3 = f6;
                                z5 = false;
                                pipSurfaceTransactionHelper4.mTmpDestinationRectF.set(rectEvaluate);
                                pipSurfaceTransactionHelper4.scale(transaction, surfaceControl2, rect25, pipSurfaceTransactionHelper4.mTmpDestinationRectF, f3, z5);
                                pipSurfaceTransactionHelper4.round(transaction, surfaceControl2, rect25, rectEvaluate);
                                pipSurfaceTransactionHelper4.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                            } else {
                                int transitionDirection2 = getTransitionDirection();
                                if (!PipAnimationController.isInPipDirection(transitionDirection2) && !PipAnimationController.isOutPipDirection(transitionDirection2)) {
                                    f3 = f6;
                                    z5 = true;
                                }
                                pipSurfaceTransactionHelper4.mTmpDestinationRectF.set(rectEvaluate);
                                pipSurfaceTransactionHelper4.scale(transaction, surfaceControl2, rect25, pipSurfaceTransactionHelper4.mTmpDestinationRectF, f3, z5);
                                pipSurfaceTransactionHelper4.round(transaction, surfaceControl2, rect25, rectEvaluate);
                                pipSurfaceTransactionHelper4.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                            }
                        }
                        PipTransactionHandler pipTransactionHandler2 = this.mPipTransactionHandler;
                        if (pipTransactionHandler2 != null ? pipTransactionHandler2.handlePipTransaction(surfaceControl2, transaction, rectEvaluate, 1.0f) : false) {
                            return;
                        }
                        transaction.apply();
                        return;
                    }
                    if (!rect27.equals(rect7)) {
                        rect6.set(rect3);
                        RotationUtils.rotateBounds(rect6, rect23, i2);
                        rect7.set(rect27);
                    }
                    Rect rectEvaluate3 = this.mRectEvaluator.evaluate(f2, rect26, rect6);
                    this.mCurrentValue = rectEvaluate3;
                    boolean z8 = zIsOutPipDirection;
                    Rect rectEvaluate4 = this.mInsetsEvaluator.evaluate(f2, z8 ? rect17 : rect18, z8 ? rect18 : rect17);
                    if (Transitions.SHELL_TRANSITIONS_ROTATION) {
                        if (i2 != 1) {
                            float f11 = 1.0f - f2;
                            f4 = f11 * (-90.0f);
                            int i5 = rect27.left;
                            fWidth = rect26.left + ((i5 - r11) * f2);
                            int i6 = rect27.top;
                            fHeight3 = (f2 * (i6 - r9)) + rect26.top + (rect26.height() * f11);
                            float f12 = fHeight3;
                            float f13 = fWidth;
                            Rect rect33 = new Rect(rect19);
                            rect24 = !zIsOutPipDirection ? rect6 : null;
                            if (rect24 != null) {
                                Point point4 = point;
                                rect24.offset(point4.x, point4.y);
                            }
                            rect33.inset(rectEvaluate4);
                            PipSurfaceTransactionHelper pipSurfaceTransactionHelper5 = this.mSurfaceTransactionHelper;
                            pipSurfaceTransactionHelper5.rotateAndScaleWithCrop(transaction, surfaceControl2, rect19, rectEvaluate3, rectEvaluate4, f4, f13, f12, zIsOutPipDirection, i2 != 3, rect24);
                            pipSurfaceTransactionHelper5.round(transaction, surfaceControl2, rect33, rectEvaluate3);
                            pipSurfaceTransactionHelper5.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                            pipTransactionHandler = this.mPipTransactionHandler;
                            if (pipTransactionHandler == null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, rectEvaluate3, 1.0f) : false) {
                                transaction.apply();
                                return;
                            }
                            return;
                        }
                        float f14 = 1.0f - f2;
                        f4 = 90.0f * f14;
                        int i7 = rect27.left;
                        fWidth = (rect26.width() * f14) + ((i7 - r11) * f2) + rect26.left;
                        i3 = rect27.top;
                        i4 = rect26.top;
                    } else if (i2 == 1) {
                        f4 = 90.0f * f2;
                        int i8 = rect27.right;
                        fWidth = ((i8 - r9) * f2) + rect26.left;
                        i3 = rect27.top;
                        i4 = rect26.top;
                    } else {
                        f4 = f2 * (-90.0f);
                        int i9 = rect27.left;
                        fWidth = ((i9 - r9) * f2) + rect26.left;
                        i3 = rect27.bottom;
                        i4 = rect26.top;
                    }
                    fHeight3 = (f2 * (i3 - i4)) + i4;
                    float f122 = fHeight3;
                    float f132 = fWidth;
                    Rect rect332 = new Rect(rect19);
                    rect24 = !zIsOutPipDirection ? rect6 : null;
                    if (rect24 != null) {
                    }
                    rect332.inset(rectEvaluate4);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper52 = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper52.rotateAndScaleWithCrop(transaction, surfaceControl2, rect19, rectEvaluate3, rectEvaluate4, f4, f132, f122, zIsOutPipDirection, i2 != 3, rect24);
                    pipSurfaceTransactionHelper52.round(transaction, surfaceControl2, rect332, rectEvaluate3);
                    pipSurfaceTransactionHelper52.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                    pipTransactionHandler = this.mPipTransactionHandler;
                    if (pipTransactionHandler == null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, rectEvaluate3, 1.0f) : false) {
                    }
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void onEndTransaction(int i3, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    if (this.mBackgroundColorApplied) {
                        this.mBackgroundColorApplied = false;
                        transaction.unsetColor(surfaceControl2);
                        transaction.apply();
                        int i4 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i3, "PipTransitionAnimator_onEndTransaction: unsetColor, dir=", "PipTaskOrganizer");
                    }
                    Rect rect24 = this.mDestinationBounds;
                    this.mSurfaceTransactionHelper.resetScale(rect24, transaction, surfaceControl2);
                    if (PipAnimationController.isOutPipDirection(i3)) {
                        transaction.setMatrix(surfaceControl2, 1.0f, 0.0f, 0.0f, 1.0f);
                        if (CoreRune.MW_PIP_SHELL_TRANSITION && i3 == 3) {
                            transaction.setPosition(surfaceControl2, rect24.left, rect24.top);
                        } else {
                            transaction.setPosition(surfaceControl2, 0.0f, 0.0f);
                        }
                        transaction.setWindowCrop(surfaceControl2, 0, 0);
                    } else {
                        this.mSurfaceTransactionHelper.cropAndPosition(rect24, transaction, surfaceControl2);
                    }
                    if (this.mContentOverlay != null) {
                        this.mContentOverlay = null;
                    }
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void onStartTransaction(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl2) {
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.getClass();
                    transaction.setAlpha(surfaceControl2, 1.0f);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl2, true);
                    pipSurfaceTransactionHelper.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                    if (PipAnimationController.isOutPipDirection(i)) {
                        this.mBackgroundColorApplied = true;
                        transaction.setColor(surfaceControl2, PipTransitionAnimator.PIP_BACKGROUND_COLOR);
                        int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                        RecyclerView$$ExternalSyntheticOutline0.m(i, "PipTaskOrganizer", new StringBuilder("PipTransitionAnimator_onStartTransaction: setColor, dir="));
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

        public abstract void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f);

        public int getAnimationType() {
            return this.mAnimationType;
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
            applySurfaceControlTransaction(this.mLeash, ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction(), valueAnimator.getAnimatedFraction());
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
            return !(this.mTransitionDirection == 5);
        }

        public void updateEndValue(Object obj) {
            this.mEndValue = obj;
        }

        public /* synthetic */ PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Object obj, Object obj2, Object obj3, int i2) {
            this(taskInfo, surfaceControl, i, rect, obj, obj2, obj3);
        }

        private PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Object obj, Object obj2, Object obj3) {
            this(taskInfo, surfaceControl, i, rect, new Point(), obj, obj2, obj3);
        }

        private PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, Point point, Object obj, Object obj2, Object obj3) {
            Rect rect2 = new Rect();
            this.mDestinationBounds = rect2;
            Point point2 = new Point();
            this.mLeashOffset = point2;
            this.mTaskInfo = taskInfo;
            this.mLeash = surfaceControl;
            this.mAnimationType = i;
            rect2.set(rect);
            point2.set(point);
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

    public static void quietCancel(ValueAnimator valueAnimator) {
        if ((valueAnimator instanceof PipTransitionAnimator) && valueAnimator.isRunning()) {
            PipTransitionAnimator pipTransitionAnimator = (PipTransitionAnimator) valueAnimator;
            if (pipTransitionAnimator.getAnimationType() == 1 && ((Float) pipTransitionAnimator.getEndValue()).floatValue() == 1.0f) {
                int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                Log.d("PipTaskOrganizer", "PipAnimationController_quietCancel: Call end before cancel, reason=fade_in_aniamtor");
                valueAnimator.end();
            }
        }
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.removeAllListeners();
        valueAnimator.cancel();
    }

    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, final Rect rect, float f, float f2) {
        int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipAnimationController] getAnimator: dest=" + rect + ", " + f + "->" + f2 + ", Caller=" + Debug.getCallers(7));
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            float[] fArr = PipTransitionAnimator.PIP_BACKGROUND_COLOR;
            PipTransitionAnimator pipTransitionAnimator2 = new PipTransitionAnimator(taskInfo, surfaceControl, 1, rect, Float.valueOf(f), Float.valueOf(f), Float.valueOf(f2)) { // from class: com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.1
                {
                    int i2 = 0;
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void applySurfaceControlTransaction(SurfaceControl surfaceControl2, SurfaceControl.Transaction transaction, float f3) {
                    float fFloatValue = (((Float) getEndValue()).floatValue() * f3) + ((1.0f - f3) * ((Float) this.mStartValue).floatValue());
                    this.mCurrentValue = Float.valueOf(fFloatValue);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.getClass();
                    transaction.setAlpha(surfaceControl2, fFloatValue);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl2, true);
                    pipSurfaceTransactionHelper.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                    Rect rect2 = rect;
                    PipTransactionHandler pipTransactionHandler = this.mPipTransactionHandler;
                    if (pipTransactionHandler != null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, rect2, fFloatValue) : false) {
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
                    pipSurfaceTransactionHelper.cropAndPosition(this.mDestinationBounds, transaction, surfaceControl2);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl2, true);
                    pipSurfaceTransactionHelper.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
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
                    int i2 = 0;
                }

                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public final void applySurfaceControlTransaction(SurfaceControl surfaceControl2, SurfaceControl.Transaction transaction, float f3) {
                    float fFloatValue = (((Float) getEndValue()).floatValue() * f3) + ((1.0f - f3) * ((Float) this.mStartValue).floatValue());
                    this.mCurrentValue = Float.valueOf(fFloatValue);
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    pipSurfaceTransactionHelper.getClass();
                    transaction.setAlpha(surfaceControl2, fFloatValue);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl2, true);
                    pipSurfaceTransactionHelper.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
                    Rect rect2 = rect;
                    PipTransactionHandler pipTransactionHandler = this.mPipTransactionHandler;
                    if (pipTransactionHandler != null ? pipTransactionHandler.handlePipTransaction(surfaceControl2, transaction, rect2, fFloatValue) : false) {
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
                    pipSurfaceTransactionHelper.cropAndPosition(this.mDestinationBounds, transaction, surfaceControl2);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl2, true);
                    pipSurfaceTransactionHelper.shadow(transaction, surfaceControl2, shouldApplyShadowRadius());
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
    }

    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2, boolean z) {
        return getAnimator(taskInfo, surfaceControl, rect, rect2, rect3, rect4, i, f, i2, z, null);
    }

    public final PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2, boolean z, Rect rect5) {
        int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipAnimationController] getAnimator direction=" + i + " endBounds=" + rect3 + " caller=" + Debug.getCallers(7));
        if (rect3.isEmpty() && rect5 != null) {
            Log.w("PipTaskOrganizer", "getAnimator destination empty, setDefaultBounds");
            rect3.set(rect5);
        }
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            PipTransitionAnimator.AnonymousClass2 anonymousClass2OfBounds = PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect2, rect2, rect3, rect4, i, 0.0f, i2, z);
            setupPipTransitionAnimator(anonymousClass2OfBounds);
            this.mCurrentAnimator = anonymousClass2OfBounds;
        } else if (pipTransitionAnimator.getAnimationType() == 1 && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.setDestinationBounds(rect3);
        } else if (this.mCurrentAnimator.getAnimationType() == 0 && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.setDestinationBounds(rect3);
            this.mCurrentAnimator.updateEndValue(new Rect(rect3));
        } else {
            this.mCurrentAnimator.cancel();
            PipTransitionAnimator.AnonymousClass2 anonymousClass2OfBounds2 = PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect, rect2, rect3, rect4, i, f, i2, z);
            setupPipTransitionAnimator(anonymousClass2OfBounds2);
            this.mCurrentAnimator = anonymousClass2OfBounds2;
        }
        return this.mCurrentAnimator;
    }

    public class PipAnimationCallback {
        public void onPipAnimationStart(PipTransitionAnimator pipTransitionAnimator) {
        }

        public void onPipAnimationCancel(TaskInfo taskInfo, PipTransitionAnimator pipTransitionAnimator) {
        }

        public void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipTransitionAnimator pipTransitionAnimator) {
        }
    }
}
