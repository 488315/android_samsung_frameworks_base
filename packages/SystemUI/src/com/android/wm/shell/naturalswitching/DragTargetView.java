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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            this.mDragTarget.getHandler().post(new Runnable() { // from class: com.android.wm.shell.naturalswitching.DragTargetView$$ExternalSyntheticLambda0
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
                                imageMatrix.setTranslate(DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f4, f3, floatValue, f3), 0.0f);
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

    /* JADX WARN: Code restructure failed: missing block: B:152:0x0173, code lost:
    
        if (r8 >= r10) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0175, code lost:
    
        r1 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x017d, code lost:
    
        if (r11 >= r10) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x018a, code lost:
    
        if (r11 >= r10) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0191, code lost:
    
        if (r11 < r12) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x019c, code lost:
    
        if (r8 >= r12) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x01a1, code lost:
    
        if (r11 >= r12) goto L121;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0052 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x011d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0116 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x010d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDropSide() {
        /*
            Method dump skipped, instructions count: 635
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.DragTargetView.getDropSide():int");
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
            boolean isLandscape = this.mTaskVisibility.mDisplayLayout.isLandscape();
            Rect rect2 = this.mDragTargetBounds;
            int i = rect2.left;
            Rect rect3 = this.mStableRect;
            int i2 = rect3.left;
            if (i < i2) {
                rect.left = i2 - i;
            }
            int i3 = rect2.top;
            int i4 = rect3.top;
            if (i3 < i4 && !isLandscape) {
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
