package com.android.p038wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.WindowConfiguration;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Debug;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.internal.policy.DockedDividerUtils;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.common.split.DividerResizeController;
import com.android.p038wm.shell.common.split.DividerResizeLayout;
import com.android.p038wm.shell.splitscreen.SplitScreen;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.android.p038wm.shell.util.StageUtils;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DividerResizeLayout extends FrameLayout {
    public static final long WINDOW_ALPHA_ANIM_DURATION;
    public DividerResizeLayout$$ExternalSyntheticLambda2 mActionDropRunnable;
    public boolean mAttachedToWindow;
    public int mBackgroundColor;
    public int mCellHostStageType;
    public int mCornerRadius;
    public int mDividerSize;
    public DividerView mDividerView;
    public Runnable mFinishRunnable;
    public final DividerResizeLayout$$ExternalSyntheticLambda0 mFinishTimeoutRunnable;
    public boolean mFirstLayoutCalled;
    public final Rect mGuideBarBounds;
    public ImageView mGuideBarView;
    public int mGuideViewBarThickness;
    public int mHalfSplitStageType;
    public Handler mHandler;
    public final DividerResizeLayout$$ExternalSyntheticLambda0 mHeavyWorkRunnable;
    public boolean mIsMultiSplitActive;
    public WindowManager.LayoutParams mLp;
    public final MultiWindowManager mMultiWindowManager;
    public DividerResizeController.ResizeAlgorithm mResizeAlgorithm;
    public final SparseArray mResizeTargets;
    public final Rect mRestrictedBounds;
    public final Rect mRootBounds;
    public SplitLayout mSplitLayout;
    public final Rect mStableInsets;
    public StageCoordinator mStageCoordinator;
    public final Region mTransparentRegion;
    public boolean mWindowAdded;
    public ValueAnimator mWindowAlphaAnimator;
    public final WindowManager mWindowManager;
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public static final float[] BLUR_PRESET = {250.0f, 0.0f, 8.0f, 29.4f, 255.0f, 0.0f, 229.5f};
    public static final float[] DARK_BLUR_PRESET = {250.0f, 0.0f, 8.0f, 29.4f, 255.0f, 0.0f, 216.8f};
    public static final RectEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public static final Interpolator SINE_OUT_60 = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    public static final Interpolator ONE_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultDividerResizeTarget extends DividerResizeTarget {
        public DefaultDividerResizeTarget(int i, ImageView imageView, ImageView imageView2, Rect rect) {
            super(i, imageView, imageView2, rect);
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void calculateBoundsForPosition(int i, Rect rect) {
            int i2 = this.mDirection;
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            DockedDividerUtils.calculateBoundsForPosition(i, i2, rect, dividerResizeLayout.mRootBounds.width(), dividerResizeLayout.mRootBounds.height(), dividerResizeLayout.mDividerSize);
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final int getDirection() {
            return this.mDirection;
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("Default"), super.toString(), "}");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DividerResizeTarget {
        public ValueAnimator mBlurAnimator;
        public final ImageView mBlurView;
        public ValueAnimator mBoundsAnimator;
        public int mDirection = -1;
        public final Rect mEndBounds;
        public int mFadeOutEndPosition;
        public int mFadeOutStartPosition;
        public boolean mHasProtectedContent;
        public boolean mIsResizing;
        public final Rect mOriginBounds;
        public final Rect mOriginOutlineInsets;
        public final Rect mOutlineInsets;
        public ValueAnimator mOutlineInsetsAnimator;
        public final C38771 mOutlineProvider;
        public int mScaleDownEndPosition;
        public int mScaleDownStartPosition;
        public boolean mShouldPlayHaptic;
        public int mSplitDismissSide;
        public final int mStageConfigPosition;
        public final int mStageType;
        public int mTaskId;
        public final Rect mTmpBounds;
        public final ImageView mView;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v5, types: [android.view.ViewOutlineProvider, com.android.wm.shell.common.split.DividerResizeLayout$DividerResizeTarget$1] */
        public DividerResizeTarget(int i, ImageView imageView, ImageView imageView2, Rect rect) {
            Rect rect2 = new Rect();
            this.mOriginBounds = rect2;
            this.mEndBounds = new Rect();
            this.mOriginOutlineInsets = new Rect();
            this.mOutlineInsets = new Rect();
            this.mTmpBounds = new Rect();
            this.mIsResizing = false;
            this.mHasProtectedContent = false;
            this.mShouldPlayHaptic = false;
            this.mSplitDismissSide = 0;
            ?? r1 = new ViewOutlineProvider() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.1
                @Override // android.view.ViewOutlineProvider
                public final void getOutline(View view, Outline outline) {
                    Rect rect3 = DividerResizeTarget.this.mOutlineInsets;
                    outline.setRoundRect(rect3.left, rect3.top, view.getWidth() - DividerResizeTarget.this.mOutlineInsets.right, view.getHeight() - DividerResizeTarget.this.mOutlineInsets.bottom, DividerResizeLayout.this.mCornerRadius);
                }
            };
            this.mOutlineProvider = r1;
            this.mStageType = i;
            this.mStageConfigPosition = DividerResizeLayout.this.mStageCoordinator.getStageWinConfigPositionByType(i);
            this.mView = imageView;
            this.mBlurView = imageView2;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            rect2.set(rect == null ? new Rect() : rect);
            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(r1);
            imageView2.setClipToOutline(true);
            imageView2.setOutlineProvider(r1);
            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                ImageView imageView3 = (ImageView) DividerResizeLayout.this.findViewById(R.id.guide_bar_view);
                DividerResizeLayout.this.mGuideBarView = imageView3;
                imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
                DividerResizeLayout.this.mGuideBarView.setBackgroundColor(DividerResizeLayout.this.getContext().getResources().getColor(R.color.guide_view_bar_color, null));
                DividerResizeLayout.this.mGuideViewBarThickness = DividerResizeLayout.this.getContext().getResources().getDimensionPixelSize(R.dimen.split_guide_view_bar_thickness);
            }
        }

        public abstract void calculateBoundsForPosition(int i, Rect rect);

        public abstract int getDirection();

        public void initDirection() {
            this.mDirection = StageUtils.convertStagePositionToDockSide(this.mStageConfigPosition);
        }

        public void initialize() {
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            this.mTaskId = dividerResizeLayout.mStageCoordinator.getTaskIdByStageType(this.mStageType);
            initDirection();
            if (isLeftOrTopDirection()) {
                DividerResizeController.ResizeAlgorithm resizeAlgorithm = dividerResizeLayout.mResizeAlgorithm;
                this.mFadeOutStartPosition = resizeAlgorithm.mFirstFadeOutPosition;
                this.mFadeOutEndPosition = resizeAlgorithm.mDismissStartThreshold;
                this.mScaleDownStartPosition = resizeAlgorithm.mFirstSplitTargetPosition;
            } else {
                DividerResizeController.ResizeAlgorithm resizeAlgorithm2 = dividerResizeLayout.mResizeAlgorithm;
                this.mFadeOutStartPosition = resizeAlgorithm2.mLastFadeOutPosition;
                this.mFadeOutEndPosition = resizeAlgorithm2.mDismissEndThreshold;
                this.mScaleDownStartPosition = resizeAlgorithm2.mLastSplitTargetPosition;
            }
            this.mScaleDownEndPosition = this.mFadeOutStartPosition;
            Rect rect = this.mOriginBounds;
            int i = rect.left;
            Rect rect2 = dividerResizeLayout.mRestrictedBounds;
            int i2 = rect2.left;
            Rect rect3 = this.mOriginOutlineInsets;
            if (i < i2) {
                rect3.left = i2 - i;
            }
            int i3 = rect.top;
            int i4 = rect2.top;
            if (i3 < i4) {
                rect3.top = i4 - i3;
            }
            int i5 = rect.right;
            int i6 = rect2.right;
            if (i5 > i6) {
                rect3.right = i5 - i6;
            }
            int i7 = rect.bottom;
            int i8 = rect2.bottom;
            if (i7 > i8) {
                rect3.bottom = i7 - i8;
            }
            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                Drawable drawable = ((FrameLayout) dividerResizeLayout).mContext.getDrawable(R.drawable.split_guide_view);
                ImageView imageView = this.mBlurView;
                imageView.setImageDrawable(drawable);
                GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable();
                int direction = getDirection();
                GradientDrawable.Orientation orientation = direction != 1 ? direction != 2 ? direction != 3 ? direction != 4 ? null : GradientDrawable.Orientation.TOP_BOTTOM : GradientDrawable.Orientation.LEFT_RIGHT : GradientDrawable.Orientation.BOTTOM_TOP : GradientDrawable.Orientation.RIGHT_LEFT;
                if (orientation != null) {
                    gradientDrawable.setOrientation(orientation);
                }
            }
        }

        public final boolean isLeftOrTopDirection() {
            return getDirection() == 1 || getDirection() == 2;
        }

        public final void startBoundsAnimation(Rect rect, boolean z, long j) {
            ValueAnimator valueAnimator = this.mBoundsAnimator;
            if (valueAnimator != null) {
                valueAnimator.end();
            }
            final Rect rect2 = new Rect();
            if (z) {
                rect2.set(this.mOriginBounds);
            } else {
                ImageView imageView = this.mBlurView;
                rect2.set(imageView.getLeft(), imageView.getTop(), imageView.getRight(), imageView.getBottom());
            }
            this.mEndBounds.set(rect);
            if (rect2.isEmpty()) {
                Log.w("DividerResizeLayout", "startBoundsAnimation: failed, invalid start bounds");
                updateViewBounds(rect);
            } else {
                if (rect2.equals(rect)) {
                    return;
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mBoundsAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DividerResizeTarget.this.updateViewBounds(DividerResizeLayout.RECT_EVALUATOR.evaluate(((Float) valueAnimator2.getAnimatedValue()).floatValue(), rect2, DividerResizeTarget.this.mEndBounds));
                    }
                });
                this.mBoundsAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        DividerResizeTarget dividerResizeTarget = DividerResizeTarget.this;
                        dividerResizeTarget.mBoundsAnimator = null;
                        DividerResizeLayout.m2741$$Nest$monAnimationFinished(DividerResizeLayout.this, "boundsAnimator");
                    }
                });
                this.mBoundsAnimator.setInterpolator(DividerResizeLayout.ONE_EASING);
                this.mBoundsAnimator.setDuration(j);
                this.mBoundsAnimator.start();
            }
        }

        public final void startOutlineInsetsAnimation(boolean z) {
            final Rect rect = this.mOriginOutlineInsets;
            if (rect.left > 0 || rect.top > 0 || rect.right > 0 || rect.bottom > 0) {
                ValueAnimator valueAnimator = this.mOutlineInsetsAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                final Rect rect2 = new Rect(this.mOutlineInsets);
                if (!z) {
                    rect = new Rect();
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mOutlineInsetsAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DividerResizeTarget.this.mOutlineInsets.set(DividerResizeLayout.RECT_EVALUATOR.evaluate(((Float) valueAnimator2.getAnimatedValue()).floatValue(), rect2, rect));
                        DividerResizeTarget.this.mView.invalidateOutline();
                        DividerResizeTarget.this.mBlurView.invalidateOutline();
                    }
                });
                this.mOutlineInsetsAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        DividerResizeTarget dividerResizeTarget = DividerResizeTarget.this;
                        dividerResizeTarget.mOutlineInsetsAnimator = null;
                        DividerResizeLayout.m2741$$Nest$monAnimationFinished(DividerResizeLayout.this, "outlineInsetsAnimator");
                    }
                });
                this.mOutlineInsetsAnimator.setInterpolator(DividerResizeLayout.ONE_EASING);
                this.mOutlineInsetsAnimator.setDuration(280L);
                this.mOutlineInsetsAnimator.start();
            }
        }

        public String toString() {
            return "DividerResizeTarget{" + SplitScreen.stageTypeToString(this.mStageType) + ", " + WindowConfiguration.stagePositionToString(this.mStageConfigPosition) + ", mOriginBounds=" + this.mOriginBounds + ", mInsets=" + this.mOriginOutlineInsets + ", Dir=" + this.mDirection;
        }

        public final boolean updateDismissSide(int i) {
            if (this.mSplitDismissSide == i) {
                return false;
            }
            if (DividerResizeLayout.DEBUG) {
                StringBuilder sb = new StringBuilder("updateDismissSide: ");
                sb.append(i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "SPLIT_DISMISS_SIDE_END" : "SPLIT_DISMISS_SIDE_START" : "SPLIT_DISMISS_SIDE_NONE");
                sb.append(" for=");
                sb.append(this);
                Log.d("DividerResizeLayout", sb.toString());
            }
            this.mSplitDismissSide = i;
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:50:0x0113, code lost:
        
            r10 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00a7, code lost:
        
            r11 = true;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateViewBounds(Rect rect) {
            float f;
            ImageView imageView = this.mView;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            ImageView imageView2 = this.mBlurView;
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
            int width = rect.width();
            marginLayoutParams.width = width;
            marginLayoutParams2.width = width;
            int height = rect.height();
            marginLayoutParams.height = height;
            marginLayoutParams2.height = height;
            int i = rect.left;
            marginLayoutParams.leftMargin = i;
            marginLayoutParams2.leftMargin = i;
            int i2 = rect.top;
            marginLayoutParams.topMargin = i2;
            marginLayoutParams2.topMargin = i2;
            imageView.setLayoutParams(marginLayoutParams);
            imageView2.setLayoutParams(marginLayoutParams2);
            if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS && imageView2.getHeight() != 0 && imageView2.getWidth() != 0) {
                int direction = getDirection();
                int top = direction != 1 ? direction != 2 ? direction != 3 ? direction != 4 ? -1 : imageView2.getTop() : imageView2.getLeft() : imageView2.getBottom() : imageView2.getRight();
                float f2 = 1.0f;
                if (!isLeftOrTopDirection() ? top < this.mScaleDownStartPosition : top > this.mScaleDownStartPosition) {
                    int abs = Math.abs(this.mScaleDownStartPosition - this.mScaleDownEndPosition);
                    if (abs == 0) {
                        if (DividerResizeLayout.DEBUG) {
                            Log.w("DividerResizeLayout", "getScaleDownFraction: failed, scaleDownZoneWith is zero");
                        }
                        f = 1.0f;
                    } else {
                        boolean z = isLeftOrTopDirection() ? false : false;
                        f = 0.9f;
                        if (!z) {
                            f = 0.9f + ((Math.abs(this.mScaleDownEndPosition - top) / abs) * 0.100000024f);
                        }
                    }
                    imageView2.setScaleX(f);
                    imageView2.setScaleY(f);
                } else {
                    imageView2.setScaleX(1.0f);
                    imageView2.setScaleY(1.0f);
                }
                if (!isLeftOrTopDirection() ? top < this.mFadeOutStartPosition : top > this.mFadeOutStartPosition) {
                    if (this.mBlurAnimator != null) {
                        Log.d("DividerResizeLayout", "cancelBlurAnimation");
                        this.mBlurAnimator.end();
                    }
                    int abs2 = Math.abs(this.mFadeOutStartPosition - this.mFadeOutEndPosition);
                    if (abs2 != 0) {
                        boolean z2 = isLeftOrTopDirection() ? false : false;
                        f2 = z2 ? 0.0f : Math.abs(this.mFadeOutEndPosition - top) / abs2;
                    } else if (DividerResizeLayout.DEBUG) {
                        Log.w("DividerResizeLayout", "getFadeOutFraction: failed, fadeOutZoneWidth is zero");
                    }
                    imageView2.setAlpha(f2);
                } else {
                    imageView2.setAlpha(1.0f);
                }
            }
            imageView.invalidate();
            imageView2.invalidate();
            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
                if (dividerResizeLayout.mGuideBarView != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
                    Rect rect2 = dividerResizeLayout.mGuideBarBounds;
                    int i3 = marginLayoutParams3.leftMargin;
                    int i4 = marginLayoutParams3.topMargin;
                    rect2.set(i3, i4, marginLayoutParams3.width + i3, marginLayoutParams3.height + i4);
                    int direction2 = getDirection();
                    if (direction2 == 1) {
                        Rect rect3 = dividerResizeLayout.mGuideBarBounds;
                        rect3.offset(rect3.width(), 0);
                        Rect rect4 = dividerResizeLayout.mGuideBarBounds;
                        rect4.right = rect4.left + dividerResizeLayout.mGuideViewBarThickness;
                    } else if (direction2 == 2) {
                        Rect rect5 = dividerResizeLayout.mGuideBarBounds;
                        rect5.offset(0, rect5.height());
                        Rect rect6 = dividerResizeLayout.mGuideBarBounds;
                        rect6.bottom = rect6.top + dividerResizeLayout.mGuideViewBarThickness;
                    } else if (direction2 == 3) {
                        dividerResizeLayout.mGuideBarBounds.offset(-dividerResizeLayout.mGuideViewBarThickness, 0);
                        Rect rect7 = dividerResizeLayout.mGuideBarBounds;
                        rect7.right = rect7.left + dividerResizeLayout.mGuideViewBarThickness;
                    } else if (direction2 != 4) {
                        dividerResizeLayout.mGuideBarBounds.setEmpty();
                    } else {
                        dividerResizeLayout.mGuideBarBounds.offset(0, -dividerResizeLayout.mGuideViewBarThickness);
                        Rect rect8 = dividerResizeLayout.mGuideBarBounds;
                        rect8.bottom = rect8.top + dividerResizeLayout.mGuideViewBarThickness;
                    }
                    ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) dividerResizeLayout.mGuideBarView.getLayoutParams();
                    marginLayoutParams4.width = dividerResizeLayout.mGuideBarBounds.width();
                    marginLayoutParams4.height = dividerResizeLayout.mGuideBarBounds.height();
                    Rect rect9 = dividerResizeLayout.mGuideBarBounds;
                    marginLayoutParams4.leftMargin = rect9.left;
                    marginLayoutParams4.topMargin = rect9.top;
                    dividerResizeLayout.mGuideBarView.setLayoutParams(marginLayoutParams4);
                    dividerResizeLayout.mGuideBarView.invalidate();
                }
            }
            Log.d("DividerResizeLayout", "updateViewBounds: " + rect + ", this=" + this);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MultiSplitDividerResizeTarget extends DividerResizeTarget {
        public int mInnerDirection;
        public int mInnerPosition;
        public int mOuterPosition;
        public final Rect mTmpRootBounds;

        public MultiSplitDividerResizeTarget(int i, ImageView imageView, ImageView imageView2, Rect rect) {
            super(i, imageView, imageView2, rect);
            this.mInnerDirection = -1;
            this.mTmpRootBounds = new Rect();
            boolean z = false;
            if (DividerResizeLayout.this.mIsMultiSplitActive) {
                int i2 = this.mStageConfigPosition;
                if (i2 == 24 || i2 == 48 || i2 == 72 || i2 == 96) {
                    z = true;
                }
            }
            if (z) {
                initInnerDirection();
                SplitLayout splitLayout = DividerResizeLayout.this.mSplitLayout;
                this.mInnerPosition = splitLayout.mCellDividePosition;
                this.mOuterPosition = splitLayout.mDividePosition;
            }
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void calculateBoundsForPosition(int i, Rect rect) {
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            DockedDividerUtils.calculateBoundsForPosition(dividerResizeLayout.mDividerView.mIsCellDivider ? this.mOuterPosition : i, this.mDirection, rect, dividerResizeLayout.mRootBounds.width(), dividerResizeLayout.mRootBounds.height(), dividerResizeLayout.mDividerSize, dividerResizeLayout.mStableInsets);
            boolean z = false;
            if ((!DividerResizeController.USE_GUIDE_VIEW_EFFECTS || dividerResizeLayout.mDividerView.mIsCellDivider) && dividerResizeLayout.mIsMultiSplitActive) {
                int i2 = this.mStageConfigPosition;
                if (i2 == 24 || i2 == 48 || i2 == 72 || i2 == 96) {
                    z = true;
                }
            }
            if (z) {
                Rect rect2 = this.mTmpRootBounds;
                rect2.set(rect);
                if (!dividerResizeLayout.mDividerView.mIsCellDivider) {
                    i = this.mInnerPosition;
                }
                DockedDividerUtils.calculateBoundsForCellWithPosition(i, this.mInnerDirection, rect, rect2, dividerResizeLayout.mDividerSize);
            }
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final int getDirection() {
            return DividerResizeLayout.this.mDividerView.mIsCellDivider ? this.mInnerDirection : this.mDirection;
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void initDirection() {
            boolean isVerticalDivision = DividerResizeLayout.this.mSplitLayout.isVerticalDivision();
            int i = this.mStageConfigPosition;
            this.mDirection = StageUtils.convertStagePositionToDockSide(isVerticalDivision ? i & 40 : i & 80);
        }

        public final void initInnerDirection() {
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            if (dividerResizeLayout.mIsMultiSplitActive) {
                int i = this.mStageConfigPosition;
                if (i == 24 || i == 48 || i == 72 || i == 96) {
                    this.mInnerDirection = StageUtils.convertStagePositionToDockSide(dividerResizeLayout.mSplitLayout.isVerticalDivision() ? i & 80 : i & 40);
                }
            }
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void initialize() {
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            boolean z = false;
            if (dividerResizeLayout.mIsMultiSplitActive) {
                int i = this.mStageConfigPosition;
                if (i == 24 || i == 48 || i == 72 || i == 96) {
                    z = true;
                }
            }
            if (z) {
                initInnerDirection();
                SplitLayout splitLayout = dividerResizeLayout.mSplitLayout;
                this.mInnerPosition = splitLayout.mCellDividePosition;
                this.mOuterPosition = splitLayout.mDividePosition;
            }
            super.initialize();
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final String toString() {
            StringBuilder sb = new StringBuilder(ActionResults.RESULT_SUPPORT_MULTI_SPLIT);
            sb.append(super.toString());
            sb.append(", mInnerDir=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.mInnerDirection, "}");
        }
    }

    /* renamed from: -$$Nest$monAnimationFinished, reason: not valid java name */
    public static void m2741$$Nest$monAnimationFinished(DividerResizeLayout dividerResizeLayout, String str) {
        if (DEBUG) {
            dividerResizeLayout.getClass();
            Log.d("DividerResizeLayout", "onAnimationFinished: ".concat(str));
        }
        dividerResizeLayout.postFinishRunnableIfPossible("onAnimationFinished", false);
    }

    static {
        WINDOW_ALPHA_ANIM_DURATION = DividerResizeController.USE_GUIDE_VIEW_EFFECTS ? 250L : 150L;
    }

    public DividerResizeLayout(Context context) {
        this(context, null);
    }

    public final void createResizeTarget(int i) {
        ImageView imageView;
        ImageView imageView2;
        Rect rect;
        ImageView imageView3;
        ImageView imageView4;
        Rect rect2;
        if (i == 0) {
            imageView3 = (ImageView) findViewById(R.id.main_view);
            imageView4 = (ImageView) findViewById(R.id.main_blur_view);
            rect2 = this.mStageCoordinator.getMainStageBounds();
        } else if (i == 1) {
            imageView3 = (ImageView) findViewById(R.id.side_view);
            imageView4 = (ImageView) findViewById(R.id.side_blur_view);
            rect2 = this.mStageCoordinator.getSideStageBounds();
        } else {
            if (!CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING || i != 2) {
                imageView = null;
                imageView2 = null;
                rect = null;
                if (imageView != null || imageView2 == null) {
                    Log.w("DividerResizeLayout", "createResizeTarget: failed, cannot found views");
                } else {
                    this.mResizeTargets.put(i, CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING ? new MultiSplitDividerResizeTarget(i, imageView, imageView2, rect) : new DefaultDividerResizeTarget(i, imageView, imageView2, rect));
                    return;
                }
            }
            imageView3 = (ImageView) findViewById(R.id.cell_view);
            imageView4 = (ImageView) findViewById(R.id.cell_blur_view);
            SplitLayout splitLayout = this.mStageCoordinator.mSplitLayout;
            splitLayout.getClass();
            rect2 = new Rect(splitLayout.mBounds3);
        }
        imageView = imageView3;
        imageView2 = imageView4;
        rect = rect2;
        if (imageView != null) {
        }
        Log.w("DividerResizeLayout", "createResizeTarget: failed, cannot found views");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        boolean gatherTransparentRegion = super.gatherTransparentRegion(region);
        region.set(this.mTransparentRegion);
        return gatherTransparentRegion;
    }

    public final void init(DividerView dividerView, SplitLayout splitLayout, StageCoordinator stageCoordinator, DividerResizeController.ResizeAlgorithm resizeAlgorithm) {
        int i;
        setAlpha(0.0f);
        this.mDividerView = dividerView;
        this.mSplitLayout = splitLayout;
        this.mStageCoordinator = stageCoordinator;
        this.mResizeAlgorithm = resizeAlgorithm;
        this.mHandler = dividerView.getHandler();
        this.mDividerSize = this.mSplitLayout.mDividerSize;
        this.mCornerRadius = MultiWindowUtils.getRoundedCornerRadius(((FrameLayout) this).mContext);
        ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.mw_divider_handle_move_threshold_default);
        this.mRootBounds.set(splitLayout.mRootBounds);
        Rect rect = this.mRestrictedBounds;
        rect.set(splitLayout.mRootBounds);
        rect.inset(splitLayout.mInsetsState.calculateInsets(rect, WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout(), false));
        this.mStableInsets.set(splitLayout.getDisplayStableInsets(((FrameLayout) this).mContext));
        if (this.mSplitLayout.isLandscape() && !CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING) {
            this.mRestrictedBounds.top = 0;
        }
        if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING) {
            boolean isMultiSplitActive = this.mStageCoordinator.isMultiSplitActive();
            this.mIsMultiSplitActive = isMultiSplitActive;
            if (isMultiSplitActive) {
                int cellHostStageType = this.mStageCoordinator.getCellHostStageType();
                this.mCellHostStageType = cellHostStageType;
                this.mHalfSplitStageType = cellHostStageType == 0 ? 1 : 0;
            }
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2603, 792, -2);
        this.mLp = layoutParams;
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.width = this.mRootBounds.width();
        this.mLp.height = this.mRootBounds.height();
        WindowManager.LayoutParams layoutParams2 = this.mLp;
        layoutParams2.gravity = 8388659;
        layoutParams2.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams3 = this.mLp;
        layoutParams3.multiwindowFlags = 64;
        layoutParams3.setTitle("DividerResizeLayout");
        if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
            int color = getContext().getResources().getColor(17171493, null);
            this.mBackgroundColor = color;
            setBackgroundColor(color);
        }
        this.mTransparentRegion.setEmpty();
        if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING && this.mDividerView.mIsCellDivider) {
            this.mTransparentRegion.set(this.mHalfSplitStageType == 0 ? this.mStageCoordinator.getMainStageBounds() : this.mStageCoordinator.getSideStageBounds());
        }
        if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
            int focusedStageType = this.mStageCoordinator.getFocusedStageType();
            if (this.mDividerView.mIsCellDivider) {
                if (focusedStageType == 2 || focusedStageType == (i = this.mCellHostStageType)) {
                    createResizeTarget(focusedStageType);
                } else {
                    createResizeTarget(i);
                }
            } else if (this.mIsMultiSplitActive) {
                createResizeTarget(this.mHalfSplitStageType);
            } else if (focusedStageType == 0) {
                createResizeTarget(0);
            } else {
                createResizeTarget(1);
            }
        } else {
            if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING && this.mIsMultiSplitActive) {
                createResizeTarget(2);
                if (this.mDividerView.mIsCellDivider) {
                    createResizeTarget(this.mCellHostStageType);
                }
            }
            createResizeTarget(0);
            createResizeTarget(1);
        }
        for (int size = this.mResizeTargets.size() - 1; size >= 0; size--) {
            DividerResizeTarget dividerResizeTarget = (DividerResizeTarget) this.mResizeTargets.valueAt(size);
            if (dividerResizeTarget != null) {
                dividerResizeTarget.initialize();
                if (DEBUG) {
                    Log.d("DividerResizeLayout", "init: " + dividerResizeTarget);
                }
            }
        }
        this.mHandler.post(this.mHeavyWorkRunnable);
    }

    public final void loadSnapshotsForResizeTarget() {
        for (int size = this.mResizeTargets.size() - 1; size >= 0; size--) {
            DividerResizeTarget dividerResizeTarget = (DividerResizeTarget) this.mResizeTargets.valueAt(size);
            if (dividerResizeTarget != null) {
                int i = dividerResizeTarget.mTaskId;
                if (i == -1) {
                    Log.w("DividerResizeLayout", "loadSnapshot: Cannot find taskId for " + dividerResizeTarget);
                } else {
                    DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
                    SurfaceFreezerSnapshot surfaceFreezerSnapshot = dividerResizeLayout.mMultiWindowManager.getSurfaceFreezerSnapshot(i);
                    if (surfaceFreezerSnapshot == null || surfaceFreezerSnapshot.getSnapshotBitmap() == null) {
                        Log.w("DividerResizeLayout", "loadSnapshot: Failed to get snapshot for " + dividerResizeTarget);
                    } else {
                        dividerResizeTarget.mHasProtectedContent = surfaceFreezerSnapshot.hasProtectedContent();
                        boolean containsSecureLayer = surfaceFreezerSnapshot.containsSecureLayer();
                        if (DEBUG) {
                            StringBuilder sb = new StringBuilder("loadSnapshot: ");
                            sb.append(dividerResizeTarget);
                            sb.append(", hasProtectedContent=");
                            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(sb, dividerResizeTarget.mHasProtectedContent, ", containsSecureLayer=", containsSecureLayer, "DividerResizeLayout");
                        }
                        boolean z = dividerResizeTarget.mHasProtectedContent;
                        ImageView imageView = dividerResizeTarget.mView;
                        ImageView imageView2 = dividerResizeTarget.mBlurView;
                        if (z) {
                            imageView.setBackgroundColor(dividerResizeLayout.getResources().getColor(R.color.protected_content_bg_color));
                            imageView2.setBackgroundColor(dividerResizeLayout.getResources().getColor(R.color.protected_content_bg_color));
                            imageView2.setImageDrawable(dividerResizeLayout.getResources().getDrawable(R.drawable.mw_splitview_ic_previewlock_mtrl));
                            imageView2.getDrawable().setAlpha(76);
                            imageView2.setScaleType(ImageView.ScaleType.CENTER);
                        } else {
                            Bitmap createSnapshotBitmapWithWallpaper = surfaceFreezerSnapshot.hasWallpaperBitmap() ? surfaceFreezerSnapshot.createSnapshotBitmapWithWallpaper(Color.argb(Math.round(Color.alpha(dividerResizeLayout.mBackgroundColor) * 0.9f), Color.red(dividerResizeLayout.mBackgroundColor), Color.green(dividerResizeLayout.mBackgroundColor), Color.blue(dividerResizeLayout.mBackgroundColor))) : surfaceFreezerSnapshot.getSnapshotBitmap();
                            Context context = ((FrameLayout) dividerResizeLayout).mContext;
                            SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
                            float[] fArr = (context.getResources().getConfiguration().uiMode & 32) != 0 ? DARK_BLUR_PRESET : BLUR_PRESET;
                            semGfxImageFilter.setBlurRadius(fArr[0]);
                            semGfxImageFilter.setProportionalSaturation(fArr[1]);
                            semGfxImageFilter.setCurveLevel(fArr[2]);
                            semGfxImageFilter.setCurveMinX(fArr[3]);
                            semGfxImageFilter.setCurveMaxX(fArr[4]);
                            semGfxImageFilter.setCurveMinY(fArr[5]);
                            semGfxImageFilter.setCurveMaxY(fArr[6]);
                            Bitmap applyToBitmap = semGfxImageFilter.applyToBitmap(createSnapshotBitmapWithWallpaper);
                            Log.d("DividerResizeLayout", "loadSnapshot: w=" + createSnapshotBitmapWithWallpaper.getWidth() + " h=" + createSnapshotBitmapWithWallpaper.getHeight());
                            imageView.setImageBitmap(createSnapshotBitmapWithWallpaper);
                            if (applyToBitmap != null) {
                                createSnapshotBitmapWithWallpaper = applyToBitmap;
                            }
                            imageView2.setImageBitmap(createSnapshotBitmapWithWallpaper);
                            if (containsSecureLayer) {
                                WindowManager.LayoutParams layoutParams = dividerResizeLayout.mLp;
                                int i2 = layoutParams.flags;
                                if ((i2 & 8192) == 0) {
                                    layoutParams.flags = i2 | 8192;
                                    if (dividerResizeLayout.mWindowAdded) {
                                        dividerResizeLayout.mWindowManager.updateViewLayout(dividerResizeLayout, layoutParams);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (DEBUG) {
            Log.d("DividerResizeLayout", "onAttachedToWindow: mTransparentRegion=" + this.mTransparentRegion);
        }
        if (this.mAttachedToWindow) {
            return;
        }
        this.mAttachedToWindow = true;
        if (!this.mTransparentRegion.isEmpty()) {
            getParent().requestTransparentRegion(this);
        }
        invalidate();
        if (this.mFirstLayoutCalled && this.mAttachedToWindow) {
            onReadyToShow();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mTransparentRegion.isEmpty()) {
            return;
        }
        canvas.save();
        canvas.clipRect(this.mTransparentRegion.getBounds());
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.restore();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mFirstLayoutCalled) {
            return;
        }
        this.mFirstLayoutCalled = true;
        if (DEBUG) {
            Log.d("DividerResizeLayout", "onFirstLayout");
        }
        if (this.mFirstLayoutCalled && this.mAttachedToWindow) {
            onReadyToShow();
        }
    }

    public final void onReadyToShow() {
        if (DEBUG) {
            Log.d("DividerResizeLayout", "onReadyToShow: mActionDropRunnable=" + this.mActionDropRunnable);
        }
        DividerResizeLayout$$ExternalSyntheticLambda2 dividerResizeLayout$$ExternalSyntheticLambda2 = this.mActionDropRunnable;
        if (dividerResizeLayout$$ExternalSyntheticLambda2 != null) {
            dividerResizeLayout$$ExternalSyntheticLambda2.run();
            this.mActionDropRunnable = null;
        }
        postFinishRunnableIfPossible("onReadyToShow", false);
    }

    public final void postFinishRunnableIfPossible(String str, boolean z) {
        if ((z || !shouldDeferRemove(true)) && this.mFinishRunnable != null) {
            Log.d("DividerResizeLayout", "postFinishRunnableIfPossible: reason=".concat(str));
            this.mHandler.removeCallbacks(this.mFinishTimeoutRunnable);
            this.mHandler.post(this.mFinishRunnable);
            this.mFinishRunnable = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldDeferRemove(boolean z) {
        boolean z2;
        if (!(this.mFirstLayoutCalled && this.mAttachedToWindow)) {
            return true;
        }
        if (z) {
            if (this.mWindowAlphaAnimator == null) {
                for (int size = this.mResizeTargets.size() - 1; size >= 0; size--) {
                    DividerResizeTarget dividerResizeTarget = (DividerResizeTarget) this.mResizeTargets.valueAt(size);
                    if (dividerResizeTarget != null) {
                        if ((!(dividerResizeTarget.mBoundsAnimator != null) && dividerResizeTarget.mBlurAnimator == null && dividerResizeTarget.mOutlineInsetsAnimator == null) ? false : true) {
                        }
                    }
                }
                z2 = false;
                if (z2) {
                    return true;
                }
            }
            z2 = true;
            if (z2) {
            }
        }
        return false;
    }

    public DividerResizeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DividerResizeLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0] */
    public DividerResizeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        final int i3 = 0;
        this.mWindowAdded = false;
        this.mAttachedToWindow = false;
        this.mFirstLayoutCalled = false;
        this.mFinishTimeoutRunnable = new Runnable(this) { // from class: com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ DividerResizeLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        DividerResizeLayout dividerResizeLayout = this.f$0;
                        boolean z = DividerResizeLayout.DEBUG;
                        dividerResizeLayout.getClass();
                        DividerResizeLayout$$ExternalSyntheticLambda1 dividerResizeLayout$$ExternalSyntheticLambda1 = new DividerResizeLayout$$ExternalSyntheticLambda1();
                        int size = dividerResizeLayout.mResizeTargets.size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                dividerResizeLayout.postFinishRunnableIfPossible("Timeout", true);
                                return;
                            } else {
                                DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
                                if (dividerResizeTarget != null) {
                                    dividerResizeLayout$$ExternalSyntheticLambda1.accept(dividerResizeTarget);
                                }
                            }
                        }
                    default:
                        DividerResizeLayout dividerResizeLayout2 = this.f$0;
                        boolean z2 = DividerResizeLayout.DEBUG;
                        dividerResizeLayout2.getClass();
                        long currentTimeMillis = System.currentTimeMillis();
                        try {
                            if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                dividerResizeLayout2.loadSnapshotsForResizeTarget();
                            }
                            if (dividerResizeLayout2.mWindowAdded) {
                                Log.w("DividerResizeLayout", "addWindow: failed, window is already added, Callers=" + Debug.getCallers(5));
                            } else {
                                dividerResizeLayout2.mWindowAdded = true;
                                dividerResizeLayout2.mWindowManager.addView(dividerResizeLayout2, dividerResizeLayout2.mLp);
                            }
                            return;
                        } finally {
                            Log.d("DividerResizeLayout", "mHeavyWorkRunnable, dur=" + (System.currentTimeMillis() - currentTimeMillis));
                        }
                }
            }
        };
        this.mResizeTargets = new SparseArray();
        this.mGuideBarBounds = new Rect();
        this.mRestrictedBounds = new Rect();
        this.mStableInsets = new Rect();
        new Rect();
        this.mTransparentRegion = new Region();
        this.mHalfSplitStageType = -1;
        this.mCellHostStageType = -1;
        final int i4 = 1;
        this.mHeavyWorkRunnable = new Runnable(this) { // from class: com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ DividerResizeLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i4) {
                    case 0:
                        DividerResizeLayout dividerResizeLayout = this.f$0;
                        boolean z = DividerResizeLayout.DEBUG;
                        dividerResizeLayout.getClass();
                        DividerResizeLayout$$ExternalSyntheticLambda1 dividerResizeLayout$$ExternalSyntheticLambda1 = new DividerResizeLayout$$ExternalSyntheticLambda1();
                        int size = dividerResizeLayout.mResizeTargets.size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                dividerResizeLayout.postFinishRunnableIfPossible("Timeout", true);
                                return;
                            } else {
                                DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
                                if (dividerResizeTarget != null) {
                                    dividerResizeLayout$$ExternalSyntheticLambda1.accept(dividerResizeTarget);
                                }
                            }
                        }
                    default:
                        DividerResizeLayout dividerResizeLayout2 = this.f$0;
                        boolean z2 = DividerResizeLayout.DEBUG;
                        dividerResizeLayout2.getClass();
                        long currentTimeMillis = System.currentTimeMillis();
                        try {
                            if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                dividerResizeLayout2.loadSnapshotsForResizeTarget();
                            }
                            if (dividerResizeLayout2.mWindowAdded) {
                                Log.w("DividerResizeLayout", "addWindow: failed, window is already added, Callers=" + Debug.getCallers(5));
                            } else {
                                dividerResizeLayout2.mWindowAdded = true;
                                dividerResizeLayout2.mWindowManager.addView(dividerResizeLayout2, dividerResizeLayout2.mLp);
                            }
                            return;
                        } finally {
                            Log.d("DividerResizeLayout", "mHeavyWorkRunnable, dur=" + (System.currentTimeMillis() - currentTimeMillis));
                        }
                }
            }
        };
        this.mRootBounds = new Rect();
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mMultiWindowManager = new MultiWindowManager();
    }
}
