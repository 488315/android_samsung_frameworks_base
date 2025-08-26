package com.android.wm.shell.naturalswitching;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.sec.ims.presence.ServiceTuple;

/* loaded from: classes3.dex */
public class DragTargetView extends FrameLayout {
    public static final RectEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public boolean mAnimatingExit;
    public ValueAnimator mBoundsAnimator;
    public SplitScreenController mController;
    public int mCornerRadius;
    public final Rect mCurrentDragTargetRect;
    public final Rect mCurrentOutlineInsets;
    public final Rect mDisplayBounds;
    public int mDividerSize;
    public final PointF mDownScale;
    public FrameLayout mDragTarget;
    public Rect mDragTargetBounds;
    public ImageView mDragTargetImage;
    public int mDragTargetWindowingMode;
    public final Rect mEndBounds;
    public final Point mHandlerPosition;
    public boolean mHasProtectedContent;
    public boolean mIsDragEndCalled;
    public WindowManager.LayoutParams mLp;
    public NonDragTargetView mNonDragTargetView;
    public ValueAnimator mOutlineInsetsAnimator;
    public final AnonymousClass1 mOutlineProvider;
    public SpringAnimation mScaleDownAnimX;
    public SpringAnimation mScaleDownAnimY;
    public SpringAnimation mScaleUpAnimX;
    public SpringAnimation mScaleUpAnimY;
    public final Rect mStableRect;
    public final Rect mTargetOutlineInsets;
    public TaskVisibility mTaskVisibility;
    public final float[] mTmpFloats;
    public final PointF mUpScale;
    public WindowManager mWm;

    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.wm.shell.naturalswitching.DragTargetView$1] */
    public DragTargetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayBounds = new Rect();
        this.mHasProtectedContent = false;
        this.mDragTargetBounds = new Rect();
        this.mCurrentDragTargetRect = new Rect();
        this.mStableRect = new Rect();
        this.mDownScale = new PointF(1.0f, 1.0f);
        this.mUpScale = new PointF(1.0f, 1.0f);
        this.mAnimatingExit = false;
        this.mTmpFloats = new float[9];
        this.mEndBounds = new Rect();
        this.mHandlerPosition = new Point();
        this.mTargetOutlineInsets = new Rect();
        this.mCurrentOutlineInsets = new Rect();
        this.mOutlineProvider = new ViewOutlineProvider() { // from class: com.android.wm.shell.naturalswitching.DragTargetView.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                Rect rect = DragTargetView.this.mCurrentOutlineInsets;
                outline.setRoundRect(rect.left, rect.top, view.getWidth() - DragTargetView.this.mCurrentOutlineInsets.right, view.getHeight() - DragTargetView.this.mCurrentOutlineInsets.bottom, r7.mCornerRadius);
            }
        };
    }

    public final void adjustDragTargetViewBoundsIfNeeded() {
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && isPipNaturalSwitching()) {
            return;
        }
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mDragTarget.getLayoutParams();
        final Rect rect = new Rect(this.mDragTargetBounds);
        if (!this.mIsDragEndCalled) {
            Rect minimumDragTargetViewBounds = getMinimumDragTargetViewBounds();
            if (rect.width() > minimumDragTargetViewBounds.width()) {
                int iWidth = rect.left + ((rect.width() - minimumDragTargetViewBounds.width()) / 2);
                rect.left = iWidth;
                rect.right = minimumDragTargetViewBounds.width() + iWidth;
            }
            if (rect.height() > minimumDragTargetViewBounds.height()) {
                rect.bottom = minimumDragTargetViewBounds.height() + rect.top;
            }
        }
        boolean zIsEmpty = this.mEndBounds.isEmpty();
        if (this.mEndBounds.equals(rect)) {
            return;
        }
        this.mEndBounds.set(rect);
        Drawable drawable = this.mDragTargetImage.getDrawable();
        final float fWidth = rect.width() < ((this.mHasProtectedContent || drawable == null) ? rect.width() : drawable.getIntrinsicWidth()) ? (rect.width() - r1) / 2.0f : 0.0f;
        final long j = this.mIsDragEndCalled ? 150L : zIsEmpty ? 350L : 175L;
        if (this.mDragTarget.isAttachedToWindow()) {
            this.mDragTarget.getHandler().post(new Runnable() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final DragTargetView dragTargetView = this.f$0;
                    final ViewGroup.MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
                    final Rect rect2 = rect;
                    long j2 = j;
                    final float f = fWidth;
                    RectEvaluator rectEvaluator = DragTargetView.RECT_EVALUATOR;
                    dragTargetView.getClass();
                    PathInterpolator pathInterpolator = InterpolatorUtils.SINE_OUT_60;
                    ValueAnimator valueAnimator = dragTargetView.mBoundsAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                        dragTargetView.mBoundsAnimator = null;
                    }
                    if (!dragTargetView.mAnimatingExit && dragTargetView.mIsDragEndCalled) {
                        dragTargetView.mAnimatingExit = true;
                        dragTargetView.startSpringAnimation(false);
                    }
                    dragTargetView.mDragTargetImage.getImageMatrix().getValues(dragTargetView.mTmpFloats);
                    final float f2 = dragTargetView.mTmpFloats[2];
                    final Rect rect3 = new Rect();
                    dragTargetView.mDragTargetImage.getGlobalVisibleRect(rect3);
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    dragTargetView.mBoundsAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            DragTargetView dragTargetView2 = dragTargetView;
                            Rect rect4 = rect3;
                            Rect rect5 = rect2;
                            ViewGroup.MarginLayoutParams marginLayoutParams3 = marginLayoutParams2;
                            float f3 = f2;
                            float f4 = f;
                            RectEvaluator rectEvaluator2 = DragTargetView.RECT_EVALUATOR;
                            dragTargetView2.getClass();
                            float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            Rect rectEvaluate = DragTargetView.RECT_EVALUATOR.evaluate(fFloatValue, rect4, rect5);
                            marginLayoutParams3.width = rectEvaluate.width();
                            marginLayoutParams3.height = rectEvaluate.height();
                            marginLayoutParams3.leftMargin = rectEvaluate.left;
                            marginLayoutParams3.topMargin = rectEvaluate.top;
                            dragTargetView2.mDragTarget.setLayoutParams(marginLayoutParams3);
                            if (f3 != f4) {
                                Matrix imageMatrix = dragTargetView2.mDragTargetImage.getImageMatrix();
                                imageMatrix.setTranslate(DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f4, f3, fFloatValue, f3), 0.0f);
                                dragTargetView2.mDragTargetImage.setImageMatrix(imageMatrix);
                                dragTargetView2.mDragTargetImage.invalidate();
                            }
                        }
                    });
                    dragTargetView.mBoundsAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.DragTargetView.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            DragTargetView dragTargetView2 = DragTargetView.this;
                            dragTargetView2.mBoundsAnimator = null;
                            if (dragTargetView2.mAnimatingExit) {
                                dragTargetView2.mAnimatingExit = false;
                            }
                        }
                    });
                    dragTargetView.mBoundsAnimator.setInterpolator(pathInterpolator);
                    dragTargetView.mBoundsAnimator.setDuration(j2);
                    dragTargetView.mBoundsAnimator.start();
                }
            });
        }
    }

    public final Rect getCurrentDragTargetRect() {
        this.mCurrentDragTargetRect.set(this.mDragTarget.getLeft(), this.mDragTarget.getTop(), this.mDragTarget.getRight(), this.mDragTarget.getBottom());
        this.mCurrentDragTargetRect.offsetTo((int) this.mDragTarget.getX(), (int) this.mDragTarget.getY());
        return this.mCurrentDragTargetRect;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0052 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x010d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0116 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x011d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDropSide() {
        int i;
        int i2;
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mController.isParallelMultiSplit()) {
            this.mDragTargetImage.getGlobalVisibleRect(this.mCurrentDragTargetRect);
            Rect rect = new Rect(this.mStableRect);
            rect.top += 120;
            Rect taskBounds = this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode);
            if (this.mTaskVisibility.mDisplayLayout.isLandscape()) {
                Rect rect2 = this.mCurrentDragTargetRect;
                int i3 = rect2.right;
                int i4 = rect2.left;
                int i5 = i3 - i4;
                int i6 = taskBounds.left;
                if (i6 <= rect.left) {
                    int i7 = taskBounds.right;
                    if (i5 >= i7) {
                        if (i4 >= rect.right || i5 <= i7) {
                        }
                    }
                    return 2;
                }
                int i8 = taskBounds.right;
                if (i8 < rect.right) {
                    if (i5 >= i6) {
                        if (i5 >= i8) {
                        }
                    }
                    return 2;
                }
                if (i5 <= i6) {
                    if (i5 >= i6 || i3 <= i6) {
                    }
                }
                return 8;
            }
            int i9 = taskBounds.top;
            if (i9 <= rect.top) {
                Rect rect3 = this.mCurrentDragTargetRect;
                if (rect3.top >= taskBounds.bottom) {
                    if (rect3.bottom < rect.bottom) {
                        return 32;
                    }
                }
                return 4;
            }
            int i10 = taskBounds.bottom;
            if (i10 < rect.bottom) {
                int i11 = this.mCurrentDragTargetRect.top;
                if (i11 >= i9) {
                    if (i11 >= i10) {
                    }
                }
                return 4;
            }
            Rect rect4 = this.mCurrentDragTargetRect;
            int i12 = rect4.top;
            if (i12 <= i9) {
                if (i12 >= i9 || rect4.bottom < i9) {
                    return 4;
                }
            }
            return 16;
        }
        this.mDragTargetImage.getGlobalVisibleRect(this.mCurrentDragTargetRect);
        Rect rect5 = new Rect(this.mStableRect);
        rect5.top += 120;
        TaskVisibility taskVisibility = this.mTaskVisibility;
        if (taskVisibility.mSupportOnlyTwoUpMode) {
            if (taskVisibility.mDisplayLayout.isLandscape()) {
                Rect rect6 = this.mCurrentDragTargetRect;
                if (rect6.left > rect5.left) {
                    if (rect6.right >= rect5.right) {
                        return 8;
                    }
                }
                return 2;
            }
            Rect rect7 = this.mCurrentDragTargetRect;
            if (rect7.top > rect5.top) {
                if (rect7.bottom >= rect5.bottom) {
                    return 16;
                }
            }
            return 4;
            return 1;
        }
        if (taskVisibility.isTwoUp() && isFloatingDragTarget()) {
            if (this.mController.isVerticalDivision()) {
                Rect rect8 = this.mCurrentDragTargetRect;
                if (rect8.top > rect5.top) {
                    if (rect8.bottom >= rect5.bottom) {
                    }
                }
                return 4;
            }
            Rect rect9 = this.mCurrentDragTargetRect;
            if (rect9.left > rect5.left) {
                if (rect9.right >= rect5.right) {
                }
            }
            return 2;
        }
        Rect rect10 = new Rect();
        this.mDragTarget.getBoundsOnScreen(rect10);
        int i13 = rect5.left - rect10.left;
        int i14 = rect5.top - rect10.top;
        int i15 = rect10.right - rect5.right;
        int iWidth = getMinimumDragTargetViewBounds().width() / 2;
        int i16 = rect10.bottom;
        int i17 = rect5.bottom;
        int i18 = i16 - i17;
        if (i18 <= iWidth) {
            iWidth = i18;
        }
        Rect rect11 = this.mCurrentDragTargetRect;
        int i19 = rect11.left;
        int i20 = rect5.left;
        if (i19 > i20) {
            i = 32;
            int i21 = rect11.top;
            int i22 = rect5.top;
            if (i21 <= i22) {
                if (i19 <= i20) {
                    if (i13 >= i14) {
                        i2 = 2;
                    }
                } else if (rect11.right >= rect5.right && i15 >= i14) {
                    i2 = 8;
                }
                i2 = 4;
            } else {
                int i23 = rect11.right;
                int i24 = rect5.right;
                if (i23 >= i24) {
                    if (i21 <= i22) {
                        if (i15 < i14) {
                            i2 = 4;
                        }
                    } else if (rect11.bottom >= i17 && i15 < iWidth) {
                        i2 = 16;
                    }
                    i2 = 8;
                } else if (rect11.bottom >= i17) {
                    if (i19 <= i20) {
                        if (i13 >= iWidth) {
                        }
                    } else if (i23 != i24 || i15 < iWidth) {
                    }
                    i2 = 16;
                } else {
                    i2 = 1;
                }
            }
        } else if (rect11.top <= rect5.top) {
            i2 = i13 >= i14 ? 2 : 4;
            i = 32;
        } else {
            if (rect11.bottom >= i17 && i13 < iWidth) {
                i2 = 16;
            }
            i = 32;
        }
        if (this.mTaskVisibility.isMultiSplit() && i2 == 1 && !isQuarter(this.mDragTargetWindowingMode)) {
            Rect taskBounds2 = this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode);
            int splitCreateMode = this.mController.getSplitCreateMode();
            if (splitCreateMode != 2) {
                if (splitCreateMode != 3) {
                    if (splitCreateMode != 4) {
                        if (splitCreateMode != 5) {
                            Log.d("DragTargetView", "invalid create mode");
                            return i2;
                        }
                        if (this.mCurrentDragTargetRect.bottom > taskBounds2.bottom) {
                            return i;
                        }
                    } else if (this.mCurrentDragTargetRect.right > taskBounds2.right) {
                        return i;
                    }
                } else if (this.mCurrentDragTargetRect.top < taskBounds2.top - this.mDividerSize) {
                    return i;
                }
            } else if (this.mCurrentDragTargetRect.left < taskBounds2.left - this.mDividerSize) {
                return i;
            }
        } else if (this.mTaskVisibility.isMultiSplit()) {
            if (isQuarter(this.mDragTargetWindowingMode)) {
                int cellStageWindowConfigPosition = this.mController.getCellStageWindowConfigPosition();
                if (!this.mNonDragTargetView.isNonTargetsHorizontal() ? (i2 != 2 || (cellStageWindowConfigPosition & 8) == 0) && (i2 != 8 || (cellStageWindowConfigPosition & 32) == 0) : (i2 != 4 || (cellStageWindowConfigPosition & 16) == 0) && (i2 != 16 || (cellStageWindowConfigPosition & 64) == 0)) {
                }
            }
        } else if (isFloatingDragTarget() && this.mTaskVisibility.isTwoUp()) {
            if (!this.mNonDragTargetView.isNonTargetsHorizontal() ? i2 == 4 || i2 == 16 : i2 == 2 || i2 == 8) {
            }
        } else if (!this.mTaskVisibility.isTwoUp() && isFloatingDragTarget()) {
            this.mTaskVisibility.isTaskVisible(1);
        }
        return i2;
        return 1;
    }

    public final Rect getMinimumDragTargetViewBounds() {
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && isPipNaturalSwitching()) {
            return new Rect(0, 0, this.mDragTargetBounds.width(), this.mDragTargetBounds.height());
        }
        Rect rect = new Rect(this.mStableRect);
        int iWidth = rect.width();
        int iHeight = rect.height();
        if (!this.mTaskVisibility.mSupportOnlyTwoUpMode) {
            rect.scale(0.5f);
        } else if (iWidth > iHeight) {
            rect.right = rect.left + ((int) ((iWidth * 0.5f) + 0.5f));
        } else {
            rect.bottom = rect.top + ((int) ((iHeight * 0.5f) + 0.5f));
            rect.right = rect.left + ((int) ((iWidth * 0.85f) + 0.5f));
        }
        int i = rect.right;
        int i2 = this.mDividerSize;
        rect.right = i - i2;
        rect.bottom -= i2;
        rect.offsetTo(0, 0);
        return rect;
    }

    public final boolean isFloatingDragTarget() {
        if (this.mDragTargetWindowingMode != 5) {
            return CoreRune.MW_NATURAL_SWITCHING_PIP && isPipNaturalSwitching();
        }
        return true;
    }

    public final boolean isPipNaturalSwitching() {
        return this.mDragTargetWindowingMode == 2;
    }

    public final boolean isQuarter(int i) {
        if (NaturalSwitchingLayout.isFloating(i)) {
            return false;
        }
        if (i == 12) {
            return true;
        }
        return (i == 3 && this.mController.getCellHostStageType() == 0) || (i == 4 && this.mController.getCellHostStageType() == 1);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    public final void startSpringAnimation(boolean z) {
        this.mDragTarget.setPivotY(0.0f);
        if (!z) {
            this.mScaleDownAnimX.animateToFinalPosition(1.0f);
            this.mScaleDownAnimY.animateToFinalPosition(1.0f);
            return;
        }
        if (!isFloatingDragTarget()) {
            Rect rect = new Rect();
            boolean zIsLandscape = this.mTaskVisibility.mDisplayLayout.isLandscape();
            Rect rect2 = this.mDragTargetBounds;
            int i = rect2.left;
            Rect rect3 = this.mStableRect;
            int i2 = rect3.left;
            if (i < i2) {
                rect.left = i2 - i;
            }
            int i3 = rect2.top;
            int i4 = rect3.top;
            if (i3 < i4 && !zIsLandscape) {
                rect.top = i4 - i3;
            }
            int i5 = rect2.right;
            int i6 = rect3.right;
            if (i5 > i6) {
                rect.right = i5 - i6;
            }
            int i7 = rect2.bottom;
            int i8 = rect3.bottom;
            if (i7 > i8) {
                rect.bottom = i7 - i8;
            }
            if (!this.mTargetOutlineInsets.equals(rect)) {
                this.mTargetOutlineInsets.set(rect);
                ValueAnimator valueAnimator = this.mOutlineInsetsAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                final Rect rect4 = new Rect();
                final Rect rect5 = new Rect(this.mTargetOutlineInsets);
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mOutlineInsetsAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.DragTargetView.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DragTargetView.this.mCurrentOutlineInsets.set(DragTargetView.RECT_EVALUATOR.evaluate(((Float) valueAnimator2.getAnimatedValue()).floatValue(), rect4, rect5));
                        DragTargetView.this.mDragTarget.invalidateOutline();
                    }
                });
                this.mOutlineInsetsAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.DragTargetView.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        DragTargetView.this.mOutlineInsetsAnimator = null;
                    }
                });
                this.mOutlineInsetsAnimator.setInterpolator(InterpolatorUtils.ONE_EASING);
                this.mOutlineInsetsAnimator.setDuration(350L);
                this.mOutlineInsetsAnimator.start();
            }
        }
        this.mScaleDownAnimX.animateToFinalPosition(this.mDownScale.x);
        this.mScaleDownAnimY.animateToFinalPosition(this.mDownScale.y);
        postDelayed(new Runnable() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DragTargetView dragTargetView = this.f$0;
                dragTargetView.mScaleDownAnimX.cancel();
                dragTargetView.mScaleDownAnimY.cancel();
                dragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                AudioManager audioManager = (AudioManager) dragTargetView.getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                if (audioManager == null) {
                    Log.w("DragTargetView", "performSoundEffect: Couldn't get audio manager");
                } else {
                    audioManager.playSoundEffect(106);
                }
                dragTargetView.mScaleUpAnimX.animateToFinalPosition(dragTargetView.mUpScale.x);
                dragTargetView.mScaleUpAnimY.animateToFinalPosition(dragTargetView.mUpScale.y);
                dragTargetView.mNonDragTargetView.startTransition(true);
            }
        }, 250L);
    }
}
