package com.android.p038wm.shell.naturalswitching;

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
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final C40031 mOutlineProvider;
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
                int width = rect.left + ((rect.width() - minimumDragTargetViewBounds.width()) / 2);
                rect.left = width;
                rect.right = minimumDragTargetViewBounds.width() + width;
            }
            if (rect.height() > minimumDragTargetViewBounds.height()) {
                rect.bottom = minimumDragTargetViewBounds.height() + rect.top;
            }
        }
        boolean isEmpty = this.mEndBounds.isEmpty();
        if (this.mEndBounds.equals(rect)) {
            return;
        }
        this.mEndBounds.set(rect);
        Drawable drawable = this.mDragTargetImage.getDrawable();
        final float width2 = rect.width() < ((this.mHasProtectedContent || drawable == null) ? rect.width() : drawable.getIntrinsicWidth()) ? (rect.width() - r1) / 2.0f : 0.0f;
        final long j = this.mIsDragEndCalled ? 150L : isEmpty ? 350L : 175L;
        if (this.mDragTarget.isAttachedToWindow()) {
            this.mDragTarget.getHandler().post(new Runnable() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    final DragTargetView dragTargetView = DragTargetView.this;
                    final ViewGroup.MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
                    final Rect rect2 = rect;
                    long j2 = j;
                    final float f = width2;
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
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    dragTargetView.mBoundsAnimator = ofFloat;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            DragTargetView dragTargetView2 = DragTargetView.this;
                            Rect rect4 = rect3;
                            Rect rect5 = rect2;
                            ViewGroup.MarginLayoutParams marginLayoutParams3 = marginLayoutParams2;
                            float f3 = f2;
                            float f4 = f;
                            RectEvaluator rectEvaluator2 = DragTargetView.RECT_EVALUATOR;
                            dragTargetView2.getClass();
                            float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            Rect evaluate = DragTargetView.RECT_EVALUATOR.evaluate(floatValue, rect4, rect5);
                            marginLayoutParams3.width = evaluate.width();
                            marginLayoutParams3.height = evaluate.height();
                            marginLayoutParams3.leftMargin = evaluate.left;
                            marginLayoutParams3.topMargin = evaluate.top;
                            dragTargetView2.mDragTarget.setLayoutParams(marginLayoutParams3);
                            if (f3 != f4) {
                                Matrix imageMatrix = dragTargetView2.mDragTargetImage.getImageMatrix();
                                imageMatrix.setTranslate(((f4 - f3) * floatValue) + f3, 0.0f);
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

    /* JADX WARN: Code restructure failed: missing block: B:125:0x00c4, code lost:
    
        if (r3 < r11) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x00cf, code lost:
    
        if (r3 >= r4) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x00d8, code lost:
    
        if (r10 >= r4) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x00e3, code lost:
    
        if (r10 >= r4) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x00ec, code lost:
    
        if (r10 < r11) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x00f5, code lost:
    
        if (r3 >= r11) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x00fb, code lost:
    
        if (r10 >= r11) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00bd, code lost:
    
        if (r3 >= r4) goto L78;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDropSide() {
        this.mDragTargetImage.getGlobalVisibleRect(this.mCurrentDragTargetRect);
        Rect rect = new Rect(this.mStableRect);
        int i = rect.top + 120;
        rect.top = i;
        TaskVisibility taskVisibility = this.mTaskVisibility;
        int i2 = 16;
        if (taskVisibility.mSupportOnlyTwoUpMode) {
            DisplayLayout displayLayout = taskVisibility.mDisplayLayout;
            if (displayLayout.mWidth > displayLayout.mHeight) {
                Rect rect2 = this.mCurrentDragTargetRect;
                if (rect2.left <= rect.left) {
                    return 2;
                }
                if (rect2.right >= rect.right) {
                    return 8;
                }
            } else {
                Rect rect3 = this.mCurrentDragTargetRect;
                if (rect3.top <= i) {
                    return 4;
                }
                if (rect3.bottom >= rect.bottom) {
                    return 16;
                }
            }
            return 1;
        }
        if (taskVisibility.isTwoUp() && isFloatingDragTarget()) {
            if (this.mController.isVerticalDivision()) {
                Rect rect4 = this.mCurrentDragTargetRect;
                if (rect4.top <= rect.top) {
                    return 4;
                }
                if (rect4.bottom >= rect.bottom) {
                    return 16;
                }
            } else {
                Rect rect5 = this.mCurrentDragTargetRect;
                if (rect5.left <= rect.left) {
                    return 2;
                }
                if (rect5.right >= rect.right) {
                    return 8;
                }
            }
            return 1;
        }
        Rect rect6 = new Rect();
        this.mDragTarget.getBoundsOnScreen(rect6);
        int i3 = rect.left - rect6.left;
        int i4 = rect.top - rect6.top;
        int i5 = rect6.right - rect.right;
        int width = getMinimumDragTargetViewBounds().width() / 2;
        int i6 = rect6.bottom;
        int i7 = rect.bottom;
        int i8 = i6 - i7;
        if (i8 <= width) {
            width = i8;
        }
        Rect rect7 = this.mCurrentDragTargetRect;
        int i9 = rect7.left;
        int i10 = rect.left;
        if (i9 <= i10) {
            if (rect7.top > rect.top) {
                if (rect7.bottom >= i7) {
                }
            }
            i2 = 2;
        } else {
            int i11 = rect7.top;
            int i12 = rect.top;
            if (i11 <= i12) {
                if (i9 > i10) {
                    if (rect7.right >= rect.right) {
                    }
                }
                i2 = 4;
            } else {
                int i13 = rect7.right;
                int i14 = rect.right;
                if (i13 >= i14) {
                    if (i11 > i12) {
                        if (rect7.bottom >= i7) {
                        }
                    }
                    i2 = 8;
                } else if (rect7.bottom >= i7) {
                    if (i9 > i10) {
                        if (i13 == i14) {
                        }
                    }
                    i2 = 16;
                } else {
                    i2 = 1;
                }
            }
        }
        if (this.mTaskVisibility.isMultiSplit() && i2 == 1 && !isQuarter(this.mDragTargetWindowingMode)) {
            Rect taskBounds = this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode);
            int splitCreateMode = this.mController.getSplitCreateMode();
            if (splitCreateMode != 2) {
                if (splitCreateMode != 3) {
                    if (splitCreateMode != 4) {
                        if (splitCreateMode != 5 || this.mCurrentDragTargetRect.bottom <= taskBounds.bottom) {
                            return i2;
                        }
                    } else if (this.mCurrentDragTargetRect.right <= taskBounds.right) {
                        return i2;
                    }
                } else if (this.mCurrentDragTargetRect.top >= taskBounds.top - this.mDividerSize) {
                    return i2;
                }
            } else if (this.mCurrentDragTargetRect.left >= taskBounds.left - this.mDividerSize) {
                return i2;
            }
            return 32;
        }
        if (this.mTaskVisibility.isMultiSplit()) {
            if (isQuarter(this.mDragTargetWindowingMode)) {
                int cellStageWindowConfigPosition = this.mController.getCellStageWindowConfigPosition();
                if (this.mNonDragTargetView.isNonTargetsHorizontal()) {
                    if (i2 == 4 && (cellStageWindowConfigPosition & 16) != 0) {
                        return 1;
                    }
                    if (i2 == 16 && (cellStageWindowConfigPosition & 64) != 0) {
                        return 1;
                    }
                } else {
                    if (i2 == 2 && (cellStageWindowConfigPosition & 8) != 0) {
                        return 1;
                    }
                    if (i2 == 8 && (cellStageWindowConfigPosition & 32) != 0) {
                        return 1;
                    }
                }
            }
        } else if (isFloatingDragTarget() && this.mTaskVisibility.isTwoUp()) {
            if (this.mNonDragTargetView.isNonTargetsHorizontal()) {
                if (i2 != 2 && i2 != 8) {
                    return 1;
                }
            } else if (i2 != 4 && i2 != 16) {
                return 1;
            }
        } else if (!this.mTaskVisibility.isTwoUp() && isFloatingDragTarget()) {
            this.mTaskVisibility.isTaskVisible(1);
        }
        return i2;
    }

    public final Rect getMinimumDragTargetViewBounds() {
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && isPipNaturalSwitching()) {
            return new Rect(0, 0, this.mDragTargetBounds.width(), this.mDragTargetBounds.height());
        }
        Rect rect = new Rect(this.mStableRect);
        int width = rect.width();
        int height = rect.height();
        if (!this.mTaskVisibility.mSupportOnlyTwoUpMode) {
            rect.scale(0.5f);
        } else if (width > height) {
            rect.right = rect.left + ((int) ((width * 0.5f) + 0.5f));
        } else {
            rect.bottom = rect.top + ((int) ((height * 0.5f) + 0.5f));
            rect.right = rect.left + ((int) ((width * 0.85f) + 0.5f));
        }
        int i = rect.right;
        int i2 = this.mDividerSize;
        rect.right = i - i2;
        rect.bottom -= i2;
        rect.offsetTo(0, 0);
        return rect;
    }

    public final boolean isFloatingDragTarget() {
        return this.mDragTargetWindowingMode == 5 || (CoreRune.MW_NATURAL_SWITCHING_PIP && isPipNaturalSwitching());
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
            DisplayLayout displayLayout = this.mTaskVisibility.mDisplayLayout;
            boolean z2 = true;
            boolean z3 = !(displayLayout.mWidth > displayLayout.mHeight);
            Rect rect2 = this.mDragTargetBounds;
            int i = rect2.left;
            Rect rect3 = this.mStableRect;
            int i2 = rect3.left;
            if (i < i2) {
                rect.left = i2 - i;
            }
            int i3 = rect2.top;
            int i4 = rect3.top;
            if (i3 < i4 && z3) {
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
            if (this.mTargetOutlineInsets.equals(rect)) {
                z2 = false;
            } else {
                this.mTargetOutlineInsets.set(rect);
            }
            if (z2) {
                ValueAnimator valueAnimator = this.mOutlineInsetsAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                final Rect rect4 = new Rect();
                final Rect rect5 = new Rect(this.mTargetOutlineInsets);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mOutlineInsetsAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.DragTargetView.3
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
                DragTargetView dragTargetView = DragTargetView.this;
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
