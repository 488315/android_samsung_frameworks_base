package com.android.wm.shell.common.split;

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
import android.graphics.drawable.GradientDrawable;
import android.os.Debug;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.common.split.DividerResizeController;
import com.android.wm.shell.common.split.DividerResizeLayout;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.util.StageUtils;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
    public Rect mRootBounds;
    public SplitLayout mSplitLayout;
    public final Rect mStableInsets;
    public StageCoordinator mStageCoordinator;
    public final Region mTransparentRegion;
    public boolean mWindowAdded;
    public ValueAnimator mWindowAlphaAnimator;
    public final WindowManager mWindowManager;
    public static final float[] BLUR_PRESET = {250.0f, 0.0f, 8.0f, 29.4f, 255.0f, 0.0f, 229.5f};
    public static final float[] DARK_BLUR_PRESET = {250.0f, 0.0f, 8.0f, 29.4f, 255.0f, 0.0f, 216.8f};
    public static final RectEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public static final Interpolator SINE_OUT_60 = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    public static final Interpolator ONE_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultDividerResizeTarget extends DividerResizeTarget {
        public DefaultDividerResizeTarget(int i, ImageView imageView, ImageView imageView2, Rect rect) {
            super(i, imageView, imageView2, rect);
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void calculateBoundsForPosition(int i, Rect rect) {
            int i2 = this.mDirection;
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            DockedDividerUtils.calculateBoundsForPosition(i, i2, rect, dividerResizeLayout.mRootBounds.width(), dividerResizeLayout.mRootBounds.height(), dividerResizeLayout.mDividerSize, null);
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final int getDirection() {
            return this.mDirection;
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT), super.toString(), "}");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final AnonymousClass1 mOutlineProvider;
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
            ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
            imageView.setScaleType(scaleType);
            imageView2.setScaleType(scaleType);
            rect2.set(rect == null ? new Rect() : rect);
            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(r1);
            imageView2.setClipToOutline(true);
            imageView2.setOutlineProvider(r1);
            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                ImageView imageView3 = (ImageView) DividerResizeLayout.this.findViewById(R.id.guide_bar_view);
                DividerResizeLayout.this.mGuideBarView = imageView3;
                imageView3.setScaleType(scaleType);
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
                if (dividerResizeLayout.mIsMultiSplitActive) {
                    DividerResizeController.ResizeAlgorithm resizeAlgorithm = dividerResizeLayout.mResizeAlgorithm;
                    this.mFadeOutStartPosition = resizeAlgorithm.mFirstFadeOutPosition;
                    this.mFadeOutEndPosition = resizeAlgorithm.mDismissStartThreshold;
                } else {
                    DividerResizeController.ResizeAlgorithm resizeAlgorithm2 = dividerResizeLayout.mResizeAlgorithm;
                    this.mFadeOutStartPosition = resizeAlgorithm2.mSplitStashStartThreshold;
                    this.mFadeOutEndPosition = resizeAlgorithm2.mSplitStashStartPosition;
                }
                this.mScaleDownStartPosition = dividerResizeLayout.mResizeAlgorithm.mFirstSplitTargetPosition;
            } else {
                if (dividerResizeLayout.mIsMultiSplitActive) {
                    DividerResizeController.ResizeAlgorithm resizeAlgorithm3 = dividerResizeLayout.mResizeAlgorithm;
                    this.mFadeOutStartPosition = resizeAlgorithm3.mLastFadeOutPosition;
                    this.mFadeOutEndPosition = resizeAlgorithm3.mDismissEndThreshold;
                } else {
                    DividerResizeController.ResizeAlgorithm resizeAlgorithm4 = dividerResizeLayout.mResizeAlgorithm;
                    this.mFadeOutStartPosition = resizeAlgorithm4.mSplitStashEndThreshold;
                    this.mFadeOutEndPosition = resizeAlgorithm4.mSplitStashEndPosition;
                }
                this.mScaleDownStartPosition = dividerResizeLayout.mResizeAlgorithm.mLastSplitTargetPosition;
            }
            this.mScaleDownEndPosition = this.mFadeOutStartPosition;
            if (dividerResizeLayout.mIsMultiSplitActive) {
                Rect rect = this.mOriginBounds;
                int i = rect.left;
                Rect rect2 = dividerResizeLayout.mRestrictedBounds;
                int i2 = rect2.left;
                if (i < i2) {
                    this.mOriginOutlineInsets.left = i2 - i;
                }
                int i3 = rect.top;
                int i4 = rect2.top;
                if (i3 < i4) {
                    this.mOriginOutlineInsets.top = i4 - i3;
                }
                int i5 = rect.right;
                int i6 = rect2.right;
                if (i5 > i6) {
                    this.mOriginOutlineInsets.right = i5 - i6;
                }
                int i7 = rect.bottom;
                int i8 = rect2.bottom;
                if (i7 > i8) {
                    this.mOriginOutlineInsets.bottom = i7 - i8;
                }
            }
            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                this.mBlurView.setImageDrawable(((FrameLayout) dividerResizeLayout).mContext.getDrawable(R.drawable.split_guide_view));
                GradientDrawable gradientDrawable = (GradientDrawable) this.mBlurView.getDrawable();
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
                rect2.set(this.mBlurView.getLeft(), this.mBlurView.getTop(), this.mBlurView.getRight(), this.mBlurView.getBottom());
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
                        float[] fArr = DividerResizeLayout.BLUR_PRESET;
                        DividerResizeLayout.this.postFinishRunnableIfPossible("onAnimationFinished", false);
                    }
                });
                this.mBoundsAnimator.setInterpolator(DividerResizeLayout.ONE_EASING);
                this.mBoundsAnimator.setDuration(j);
                this.mBoundsAnimator.start();
            }
        }

        public final void startOutlineInsetsAnimation(boolean z) {
            Rect rect = this.mOriginOutlineInsets;
            if (rect.left > 0 || rect.top > 0 || rect.right > 0 || rect.bottom > 0) {
                ValueAnimator valueAnimator = this.mOutlineInsetsAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                final Rect rect2 = new Rect(this.mOutlineInsets);
                final Rect rect3 = z ? this.mOriginOutlineInsets : new Rect();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mOutlineInsetsAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DividerResizeTarget.this.mOutlineInsets.set(DividerResizeLayout.RECT_EVALUATOR.evaluate(((Float) valueAnimator2.getAnimatedValue()).floatValue(), rect2, rect3));
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
                        float[] fArr = DividerResizeLayout.BLUR_PRESET;
                        DividerResizeLayout.this.postFinishRunnableIfPossible("onAnimationFinished", false);
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

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0093, code lost:
        
            r7 = java.lang.Math.abs(r11.mScaleDownStartPosition - r11.mScaleDownEndPosition);
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x009c, code lost:
        
            if (r7 != 0) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x009e, code lost:
        
            r10 = 1.0f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x00c2, code lost:
        
            r11.mBlurView.setScaleX(r10);
            r11.mBlurView.setScaleY(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00a0, code lost:
        
            r10 = 0.9f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x00a7, code lost:
        
            if (isLeftOrTopDirection() == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x00ab, code lost:
        
            if (r0 > r11.mScaleDownEndPosition) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00b3, code lost:
        
            r10 = 0.9f + ((java.lang.Math.abs(r11.mScaleDownEndPosition - r0) / r7) * 0.100000024f);
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00b0, code lost:
        
            if (r0 < r11.mScaleDownEndPosition) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x0113, code lost:
        
            r8 = 0.0f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x0116, code lost:
        
            if (r2.mIsMultiSplitActive != false) goto L79;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x011c, code lost:
        
            if (isLeftOrTopDirection() == false) goto L71;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0122, code lost:
        
            if (r0 > r2.mResizeAlgorithm.mDismissStartThreshold) goto L74;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x012c, code lost:
        
            r8 = 0.32f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0129, code lost:
        
            if (r0 < r2.mResizeAlgorithm.mDismissEndThreshold) goto L74;
         */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0139  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x0141  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateViewBounds(android.graphics.Rect r12) {
            /*
                Method dump skipped, instructions count: 542
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.updateViewBounds(android.graphics.Rect):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MultiSplitDividerResizeTarget extends DividerResizeTarget {
        public int mInnerDirection;
        public int mInnerPosition;
        public int mOuterPosition;
        public final Rect mTmpRootBounds;

        public MultiSplitDividerResizeTarget(int i, ImageView imageView, ImageView imageView2, Rect rect) {
            super(i, imageView, imageView2, rect);
            this.mInnerDirection = -1;
            this.mTmpRootBounds = new Rect();
            if (DividerResizeLayout.this.mIsMultiSplitActive) {
                int i2 = this.mStageConfigPosition;
                if (i2 == 24 || i2 == 48 || i2 == 72 || i2 == 96) {
                    initInnerDirection();
                    SplitLayout splitLayout = DividerResizeLayout.this.mSplitLayout;
                    this.mInnerPosition = splitLayout.mCellDividerPosition;
                    this.mOuterPosition = splitLayout.mDividerPosition;
                }
            }
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void calculateBoundsForPosition(int i, Rect rect) {
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            DockedDividerUtils.calculateBoundsForPosition(dividerResizeLayout.mDividerView.mIsCellDivider ? this.mOuterPosition : i, this.mDirection, rect, dividerResizeLayout.mRootBounds.width(), dividerResizeLayout.mRootBounds.height(), dividerResizeLayout.mDividerSize, dividerResizeLayout.mStableInsets);
            if ((!DividerResizeController.USE_GUIDE_VIEW_EFFECTS || dividerResizeLayout.mDividerView.mIsCellDivider) && dividerResizeLayout.mIsMultiSplitActive) {
                int i2 = this.mStageConfigPosition;
                if (i2 == 24 || i2 == 48 || i2 == 72 || i2 == 96) {
                    this.mTmpRootBounds.set(rect);
                    if (!dividerResizeLayout.mDividerView.mIsCellDivider) {
                        i = this.mInnerPosition;
                    }
                    DockedDividerUtils.calculateBoundsForCellWithPosition(rect, this.mTmpRootBounds, i, this.mInnerDirection, dividerResizeLayout.mDividerSize);
                }
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
            int i;
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            if (dividerResizeLayout.mIsMultiSplitActive) {
                int i2 = this.mStageConfigPosition;
                if (i2 == 24 || i2 == 48 || i2 == 72 || i2 == 96) {
                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                        SplitLayout splitLayout = dividerResizeLayout.mSplitLayout;
                        if (splitLayout.mParallelMultiSplit) {
                            boolean isVerticalDivision = splitLayout.isVerticalDivision();
                            i = 8;
                            if (!isVerticalDivision) {
                                i = (i2 & 8) != 0 ? 16 : 64;
                            } else if ((i2 & 16) == 0) {
                                i = 32;
                            }
                            this.mInnerDirection = StageUtils.convertStagePositionToDockSide(i);
                        }
                    }
                    i = dividerResizeLayout.mSplitLayout.isVerticalDivision() ? i2 & 80 : i2 & 40;
                    this.mInnerDirection = StageUtils.convertStagePositionToDockSide(i);
                }
            }
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final void initialize() {
            int i;
            DividerResizeLayout dividerResizeLayout = DividerResizeLayout.this;
            if (dividerResizeLayout.mIsMultiSplitActive && ((i = this.mStageConfigPosition) == 24 || i == 48 || i == 72 || i == 96)) {
                initInnerDirection();
                SplitLayout splitLayout = dividerResizeLayout.mSplitLayout;
                this.mInnerPosition = splitLayout.mCellDividerPosition;
                this.mOuterPosition = splitLayout.mDividerPosition;
            }
            super.initialize();
        }

        @Override // com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget
        public final String toString() {
            StringBuilder sb = new StringBuilder(ActionResults.RESULT_SUPPORT_MULTI_SPLIT);
            sb.append(super.toString());
            sb.append(", mInnerDir=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mInnerDirection, "}", sb);
        }
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
        Rect bounds3;
        DividerResizeLayout dividerResizeLayout;
        int i2;
        DividerResizeTarget defaultDividerResizeTarget;
        int i3;
        if (i == 0) {
            imageView3 = (ImageView) findViewById(R.id.main_view);
            imageView4 = (ImageView) findViewById(R.id.main_blur_view);
            bounds3 = this.mStageCoordinator.getMainStageBounds();
        } else if (i == 1) {
            imageView3 = (ImageView) findViewById(R.id.side_view);
            imageView4 = (ImageView) findViewById(R.id.side_blur_view);
            bounds3 = this.mStageCoordinator.getSideStageBounds();
        } else {
            if (!CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING || i != 5) {
                imageView = null;
                imageView2 = null;
                rect = null;
                if (imageView != null || imageView2 == null) {
                    Log.w("DividerResizeLayout", "createResizeTarget: failed, cannot found views");
                }
                DividerResizeController.ResizeAlgorithm resizeAlgorithm = this.mResizeAlgorithm;
                if ((resizeAlgorithm.isInStartStashZone() || ((i3 = resizeAlgorithm.mSplitStashEndThreshold) > 0 && resizeAlgorithm.mTouchPosition > i3)) && !this.mIsMultiSplitActive) {
                    if (this.mResizeAlgorithm.isInStartStashZone()) {
                        rect.left = Math.max(rect.left, 0);
                        rect.top = Math.max(rect.top, 0);
                    } else {
                        rect.right = Math.min(rect.right, this.mRootBounds.width());
                        rect.bottom = Math.min(rect.bottom, this.mRootBounds.height());
                    }
                }
                if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING) {
                    dividerResizeLayout = this;
                    i2 = i;
                    defaultDividerResizeTarget = dividerResizeLayout.new MultiSplitDividerResizeTarget(i2, imageView, imageView2, rect);
                } else {
                    dividerResizeLayout = this;
                    i2 = i;
                    defaultDividerResizeTarget = dividerResizeLayout.new DefaultDividerResizeTarget(i2, imageView, imageView2, rect);
                }
                dividerResizeLayout.mResizeTargets.put(i2, defaultDividerResizeTarget);
                return;
            }
            imageView3 = (ImageView) findViewById(R.id.cell_view);
            imageView4 = (ImageView) findViewById(R.id.cell_blur_view);
            bounds3 = this.mStageCoordinator.mSplitLayout.getBounds3();
        }
        imageView = imageView3;
        imageView2 = imageView4;
        rect = bounds3;
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
        this.mRootBounds = splitLayout.getRootBounds();
        Rect rect = this.mRestrictedBounds;
        rect.set(splitLayout.mRootBounds);
        rect.inset(splitLayout.mInsetsState.calculateInsets(rect, WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout(), false));
        this.mStableInsets.set(splitLayout.getDisplayStableInsets(((FrameLayout) this).mContext));
        this.mRestrictedBounds.top = 0;
        boolean z = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
        if (z) {
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
        layoutParams3.multiWindowFlags = 64;
        layoutParams3.setTitle("DividerResizeLayout");
        if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
            int color = getContext().getResources().getColor(17171594, null);
            this.mBackgroundColor = color;
            setBackgroundColor(color);
        }
        this.mTransparentRegion.setEmpty();
        if (z && this.mDividerView.mIsCellDivider) {
            this.mTransparentRegion.set(this.mHalfSplitStageType == 0 ? this.mStageCoordinator.getMainStageBounds() : this.mStageCoordinator.getSideStageBounds());
        } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT && !this.mDividerView.mIsCellDivider && this.mSplitLayout.mParallelMultiSplit) {
            this.mTransparentRegion.set(this.mStageCoordinator.mSplitLayout.getBounds3());
        }
        if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
            int focusedStageType = this.mStageCoordinator.getFocusedStageType();
            if (this.mDividerView.mIsCellDivider) {
                if (focusedStageType == 5 || focusedStageType == (i = this.mCellHostStageType)) {
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
            if (z && this.mIsMultiSplitActive) {
                createResizeTarget(5);
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
            }
        }
        this.mHandler.post(this.mHeavyWorkRunnable);
    }

    public final boolean isReadyToShow() {
        return this.mFirstLayoutCalled && this.mAttachedToWindow;
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
                    if (surfaceFreezerSnapshot == null) {
                        Log.w("DividerResizeLayout", "loadSnapshot: Failed to get snapshot for " + dividerResizeTarget);
                    } else {
                        dividerResizeTarget.mHasProtectedContent = surfaceFreezerSnapshot.hasProtectedContent();
                        boolean containsSecureLayer = surfaceFreezerSnapshot.containsSecureLayer();
                        if (dividerResizeTarget.mHasProtectedContent) {
                            dividerResizeTarget.mView.setBackgroundColor(dividerResizeLayout.getResources().getColor(R.color.protected_content_bg_color));
                            dividerResizeTarget.mBlurView.setBackgroundColor(dividerResizeLayout.getResources().getColor(R.color.protected_content_bg_color));
                            dividerResizeTarget.mBlurView.setImageDrawable(dividerResizeLayout.getResources().getDrawable(R.drawable.mw_splitview_ic_previewlock_mtrl));
                            dividerResizeTarget.mBlurView.getDrawable().setAlpha(76);
                            dividerResizeTarget.mBlurView.setScaleType(ImageView.ScaleType.CENTER);
                        } else if (surfaceFreezerSnapshot.getSnapshotBitmap() == null) {
                            Log.w("DividerResizeLayout", "loadSnapshot: Failed to get snapshot bitmap for " + dividerResizeTarget);
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
                            dividerResizeTarget.mView.setImageBitmap(createSnapshotBitmapWithWallpaper);
                            ImageView imageView = dividerResizeTarget.mBlurView;
                            if (applyToBitmap != null) {
                                createSnapshotBitmapWithWallpaper = applyToBitmap;
                            }
                            imageView.setImageBitmap(createSnapshotBitmapWithWallpaper);
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
        if (this.mAttachedToWindow) {
            return;
        }
        this.mAttachedToWindow = true;
        if (!this.mTransparentRegion.isEmpty()) {
            getParent().requestTransparentRegion(this);
        }
        invalidate();
        if (isReadyToShow()) {
            DividerResizeLayout$$ExternalSyntheticLambda2 dividerResizeLayout$$ExternalSyntheticLambda2 = this.mActionDropRunnable;
            if (dividerResizeLayout$$ExternalSyntheticLambda2 != null) {
                dividerResizeLayout$$ExternalSyntheticLambda2.run();
                this.mActionDropRunnable = null;
            }
            postFinishRunnableIfPossible("onReadyToShow", false);
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
        if (isReadyToShow()) {
            DividerResizeLayout$$ExternalSyntheticLambda2 dividerResizeLayout$$ExternalSyntheticLambda2 = this.mActionDropRunnable;
            if (dividerResizeLayout$$ExternalSyntheticLambda2 != null) {
                dividerResizeLayout$$ExternalSyntheticLambda2.run();
                this.mActionDropRunnable = null;
            }
            postFinishRunnableIfPossible("onReadyToShow", false);
        }
    }

    public final void postFinishRunnableIfPossible(String str, boolean z) {
        if ((z || !shouldDeferRemove(true)) && this.mFinishRunnable != null) {
            Log.d("DividerResizeLayout", "postFinishRunnableIfPossible: reason=".concat(str));
            this.mHandler.removeCallbacks(this.mFinishTimeoutRunnable);
            this.mHandler.post(this.mFinishRunnable);
            this.mFinishRunnable = null;
        }
    }

    public final boolean shouldDeferRemove(boolean z) {
        if (isReadyToShow()) {
            if (!z) {
                return false;
            }
            if (this.mWindowAlphaAnimator == null) {
                for (int size = this.mResizeTargets.size() - 1; size >= 0; size--) {
                    DividerResizeTarget dividerResizeTarget = (DividerResizeTarget) this.mResizeTargets.valueAt(size);
                    if (dividerResizeTarget == null || (dividerResizeTarget.mBoundsAnimator == null && dividerResizeTarget.mBlurAnimator == null && dividerResizeTarget.mOutlineInsetsAnimator == null)) {
                    }
                }
                return false;
            }
        }
        return true;
    }

    public DividerResizeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DividerResizeLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0] */
    public DividerResizeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWindowAdded = false;
        this.mAttachedToWindow = false;
        this.mFirstLayoutCalled = false;
        final int i3 = 0;
        this.mFinishTimeoutRunnable = new Runnable(this) { // from class: com.android.wm.shell.common.split.DividerResizeLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ DividerResizeLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i3;
                DividerResizeLayout dividerResizeLayout = this.f$0;
                switch (i4) {
                    case 0:
                        for (int size = dividerResizeLayout.mResizeTargets.size() - 1; size >= 0; size--) {
                            DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
                            if (dividerResizeTarget != null) {
                                ValueAnimator valueAnimator = dividerResizeTarget.mBlurAnimator;
                                if (valueAnimator != null) {
                                    valueAnimator.end();
                                    dividerResizeTarget.mBlurAnimator = null;
                                }
                                ValueAnimator valueAnimator2 = dividerResizeTarget.mBoundsAnimator;
                                if (valueAnimator2 != null) {
                                    valueAnimator2.end();
                                    dividerResizeTarget.mBoundsAnimator = null;
                                }
                                ValueAnimator valueAnimator3 = dividerResizeTarget.mOutlineInsetsAnimator;
                                if (valueAnimator3 != null) {
                                    valueAnimator3.end();
                                    dividerResizeTarget.mOutlineInsetsAnimator = null;
                                }
                            }
                        }
                        dividerResizeLayout.postFinishRunnableIfPossible(PluginLockStar.TIMEOUT_TYPE, true);
                        return;
                    default:
                        float[] fArr = DividerResizeLayout.BLUR_PRESET;
                        long currentTimeMillis = System.currentTimeMillis();
                        try {
                            if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                dividerResizeLayout.loadSnapshotsForResizeTarget();
                            }
                            if (dividerResizeLayout.mWindowAdded) {
                                Log.w("DividerResizeLayout", "addWindow: failed, window is already added, Callers=" + Debug.getCallers(5));
                            } else {
                                dividerResizeLayout.mWindowAdded = true;
                                dividerResizeLayout.mWindowManager.addView(dividerResizeLayout, dividerResizeLayout.mLp);
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
                int i42 = i4;
                DividerResizeLayout dividerResizeLayout = this.f$0;
                switch (i42) {
                    case 0:
                        for (int size = dividerResizeLayout.mResizeTargets.size() - 1; size >= 0; size--) {
                            DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
                            if (dividerResizeTarget != null) {
                                ValueAnimator valueAnimator = dividerResizeTarget.mBlurAnimator;
                                if (valueAnimator != null) {
                                    valueAnimator.end();
                                    dividerResizeTarget.mBlurAnimator = null;
                                }
                                ValueAnimator valueAnimator2 = dividerResizeTarget.mBoundsAnimator;
                                if (valueAnimator2 != null) {
                                    valueAnimator2.end();
                                    dividerResizeTarget.mBoundsAnimator = null;
                                }
                                ValueAnimator valueAnimator3 = dividerResizeTarget.mOutlineInsetsAnimator;
                                if (valueAnimator3 != null) {
                                    valueAnimator3.end();
                                    dividerResizeTarget.mOutlineInsetsAnimator = null;
                                }
                            }
                        }
                        dividerResizeLayout.postFinishRunnableIfPossible(PluginLockStar.TIMEOUT_TYPE, true);
                        return;
                    default:
                        float[] fArr = DividerResizeLayout.BLUR_PRESET;
                        long currentTimeMillis = System.currentTimeMillis();
                        try {
                            if (!DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                dividerResizeLayout.loadSnapshotsForResizeTarget();
                            }
                            if (dividerResizeLayout.mWindowAdded) {
                                Log.w("DividerResizeLayout", "addWindow: failed, window is already added, Callers=" + Debug.getCallers(5));
                            } else {
                                dividerResizeLayout.mWindowAdded = true;
                                dividerResizeLayout.mWindowManager.addView(dividerResizeLayout, dividerResizeLayout.mLp);
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
