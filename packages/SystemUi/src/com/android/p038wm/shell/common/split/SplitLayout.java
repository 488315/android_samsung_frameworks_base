package com.android.p038wm.shell.common.split;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Slog;
import android.view.Display;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.RoundedCorner;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.internal.policy.DockedDividerUtils;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayImeController;
import com.android.p038wm.shell.common.DisplayInsetsController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.split.SplitWindowManager;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.android.p038wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda11;
import com.android.p038wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitLayout implements DisplayInsetsController.OnInsetsChangedListener {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public final Rect mBounds1;
    public final Rect mBounds2;
    public final Rect mBounds3;
    public int mCellDividePosition;
    public final Rect mCellDividerBounds;
    public boolean mCellInitialized;
    public DividerSnapAlgorithm mCellSnapAlgorithm;
    public final SplitWindowManager mCellSplitWindowManager;
    public int mCellStageWindowConfigPosition;
    public Context mContext;
    public int mDensity;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public int mDividePosition;
    public final Rect mDividerBounds;
    public ValueAnimator mDividerFlingAnimator;
    public int mDividerInsets;
    public int mDividerSize;
    DividerSnapAlgorithm mDividerSnapAlgorithm;
    public int mDividerWindowWidth;
    public float mFontScale;
    public int mFontWeightAdjustment;
    public boolean mFreezeDividerWindow;
    public final Rect mHostAndCellArea;
    public final Rect mHostBounds;
    public final ImePositionProcessor mImePositionProcessor;
    public boolean mInitialized;
    public final InsetsState mInsetsState;
    public final Rect mInvisibleBounds;
    public Locale mLocale;
    public final Rect mNavigationBarRect;
    public int mOrientation;
    public int mPossibleSplitDivision;
    public final Rect mRootBounds;
    public int mRotation;
    public final SharedPreferences mSharedPreferences;
    public int mSplitDivision;
    public final SplitLayoutHandler mSplitLayoutHandler;
    public int mSplitScreenFeasibleMode;
    public final SplitWindowManager mSplitWindowManager;
    public StageCoordinator mStageCoordinator;
    public final ResizingEffectPolicy mSurfaceEffectPolicy;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Rect mTempRect;
    public final Rect mTempRect2;
    public int mUiMode;
    public final Rect mWinBounds1;
    public final Rect mWinBounds2;
    public final Rect mWinBounds3;
    public WindowContainerToken mWinToken1;
    public WindowContainerToken mWinToken2;
    public WindowContainerToken mWinToken3;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ImePositionProcessor implements DisplayImeController.ImePositionProcessor {
        public float mDimValue1;
        public float mDimValue2;
        public final int mDisplayId;
        public int mEndImeTop;
        public boolean mHasImeFocus;
        public boolean mImeShown;
        public int mLastYOffset;
        public int mStartImeTop;
        public int mTargetYOffset;
        public boolean mTaskBoundsAdjusted;
        public int mYOffsetForIme;

        public /* synthetic */ ImePositionProcessor(SplitLayout splitLayout, int i, int i2) {
            this(i);
        }

        public final boolean adjustSurfaceLayoutForIme(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, SurfaceControl surfaceControl3, SurfaceControl surfaceControl4, SurfaceControl surfaceControl5) {
            boolean z;
            boolean z2 = this.mDimValue1 > 0.001f || this.mDimValue2 > 0.001f;
            if (this.mYOffsetForIme != 0) {
                SplitLayout splitLayout = SplitLayout.this;
                if (surfaceControl != null) {
                    splitLayout.getRefDividerBounds(splitLayout.mTempRect);
                    splitLayout.mTempRect.offset(0, this.mYOffsetForIme);
                    Rect rect = splitLayout.mTempRect;
                    transaction.setPosition(surfaceControl, rect.left, rect.top);
                }
                splitLayout.getRefBounds1(splitLayout.mTempRect);
                transaction.setWindowCrop(surfaceControl2, splitLayout.mTempRect.width(), splitLayout.mTempRect.height() + this.mYOffsetForIme);
                splitLayout.getRefBounds2(splitLayout.mTempRect);
                splitLayout.mTempRect.offset(0, this.mYOffsetForIme);
                Rect rect2 = splitLayout.mTempRect;
                transaction.setPosition(surfaceControl3, rect2.left, rect2.top);
                z = true;
            } else {
                z = false;
            }
            if (!z2) {
                return z;
            }
            transaction.setAlpha(surfaceControl4, this.mDimValue1).setVisibility(surfaceControl4, this.mDimValue1 > 0.001f);
            transaction.setAlpha(surfaceControl5, this.mDimValue2).setVisibility(surfaceControl5, this.mDimValue2 > 0.001f);
            return true;
        }

        public final int getMinTopStackBottom() {
            boolean z = CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME;
            SplitLayout splitLayout = SplitLayout.this;
            return (z && splitLayout.mStageCoordinator.isMultiSplitActive()) ? splitLayout.isVerticalDivision() ? splitLayout.getCellSnapAlgorithm().getFirstSplitTarget().position : splitLayout.mDividerSnapAlgorithm.getFirstSplitTarget().position : splitLayout.mDividerSnapAlgorithm.getFirstSplitTarget().position;
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeControlTargetChanged(int i, boolean z) {
            if (i == this.mDisplayId && !z && this.mImeShown) {
                reset();
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.setDividerInteractive("onImeControlTargetChanged", true, true);
                SplitLayoutHandler splitLayoutHandler = splitLayout.mSplitLayoutHandler;
                StageCoordinator stageCoordinator = (StageCoordinator) splitLayoutHandler;
                int i2 = stageCoordinator.mSideStagePosition;
                StageTaskListener stageTaskListener = stageCoordinator.mSideStage;
                StageTaskListener stageTaskListener2 = stageCoordinator.mMainStage;
                StageTaskListener stageTaskListener3 = i2 == 0 ? stageTaskListener : stageTaskListener2;
                if (i2 == 0) {
                    stageTaskListener = stageTaskListener2;
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && stageCoordinator.isMultiSplitActive()) {
                    splitLayout.applyLayoutOffsetTargetForMultiSplit(windowContainerTransaction, 0, stageCoordinator.getBottomStages());
                } else {
                    ActivityManager.RunningTaskInfo runningTaskInfo = stageTaskListener3.mRootTaskInfo;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = stageTaskListener.mRootTaskInfo;
                    windowContainerTransaction.setBounds(runningTaskInfo.token, splitLayout.mBounds1);
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
                    windowContainerTransaction.setBounds(runningTaskInfo2.token, splitLayout.mBounds2);
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo2.token, 0, 0);
                }
                stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                ((StageCoordinator) splitLayoutHandler).onLayoutPositionChanging(splitLayout);
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeEndPositioning(int i, boolean z) {
            if (i == this.mDisplayId) {
                if (!this.mHasImeFocus) {
                    if (!(this.mYOffsetForIme != 0)) {
                        return;
                    }
                }
                if (z) {
                    return;
                }
                this.mDimValue1 = 0.0f;
                this.mDimValue2 = 0.0f;
                float f = this.mLastYOffset;
                this.mYOffsetForIme = (int) DependencyGraph$$ExternalSyntheticOutline0.m20m(this.mTargetYOffset, f, 1.0f, f);
                SplitLayout splitLayout = SplitLayout.this;
                ((StageCoordinator) splitLayout.mSplitLayoutHandler).onLayoutPositionChanging(splitLayout);
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImePositionChanged(int i, int i2) {
            if (i == this.mDisplayId) {
                if (!this.mHasImeFocus) {
                    if (!(this.mYOffsetForIme != 0)) {
                        return;
                    }
                }
                float f = (i2 - this.mStartImeTop) / (this.mEndImeTop - r3);
                float f2 = (f * 0.0f) + 0.0f;
                this.mDimValue1 = f2;
                this.mDimValue2 = f2;
                float f3 = this.mLastYOffset;
                this.mYOffsetForIme = (int) DependencyGraph$$ExternalSyntheticOutline0.m20m(this.mTargetYOffset, f3, f, f3);
                SplitLayout splitLayout = SplitLayout.this;
                ((StageCoordinator) splitLayout.mSplitLayoutHandler).onLayoutPositionChanging(splitLayout);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x00d4  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x014e  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x01a3  */
        /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:85:0x015b  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x0147  */
        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int onImeStartPositioning(int i, int i2, int i3, boolean z, boolean z2) {
            boolean z3;
            int i4;
            int i5 = this.mDisplayId;
            if (i != i5) {
                return 0;
            }
            SplitLayout splitLayout = SplitLayout.this;
            if (!splitLayout.mInitialized) {
                return 0;
            }
            boolean z4 = CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME;
            ShellTaskOrganizer shellTaskOrganizer = splitLayout.mTaskOrganizer;
            SplitLayoutHandler splitLayoutHandler = splitLayout.mSplitLayoutHandler;
            int splitItemStagePosition = z4 ? ((StageCoordinator) splitLayoutHandler).getSplitItemStagePosition(shellTaskOrganizer.getImeTarget(i5)) : ((StageCoordinator) splitLayoutHandler).getSplitItemPosition(shellTaskOrganizer.getImeTarget(i5));
            boolean z5 = !CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME ? splitItemStagePosition == -1 : splitItemStagePosition == 0;
            this.mHasImeFocus = z5;
            if (!z5) {
                if (!(this.mYOffsetForIme != 0)) {
                    if (!z) {
                        splitLayout.mSplitWindowManager.setInteractive("onImeStartPositioning", true, false);
                    }
                    return 0;
                }
                boolean z6 = SplitLayout.DEBUG;
                Slog.d("SplitLayout", "onImeStartPositioning: start animation, showing=" + z + ", shownTop=" + i3 + ", reason=AdjustedByIme");
            }
            this.mStartImeTop = z ? i2 : i3;
            if (z) {
                i2 = i3;
            }
            this.mEndImeTop = i2;
            this.mImeShown = z;
            this.mLastYOffset = this.mYOffsetForIme;
            boolean z7 = !CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME ? splitItemStagePosition != 1 || z2 || SplitLayout.isLandscape(splitLayout.mRootBounds) || !this.mImeShown : (splitItemStagePosition & 64) == 0 || z2 || !z;
            if (r2.height() / r2.width() >= 2.0555556f) {
                if (i3 - (getMinTopStackBottom() + splitLayout.mDividerSize) >= ((int) (((splitLayout.mDensity / 160.0f) * 132.0f) + 0.5f))) {
                    z3 = true;
                    if (z7) {
                        i4 = 0;
                    } else {
                        int abs = Math.abs(this.mEndImeTop - this.mStartImeTop);
                        int topStageBottom = z3 ? ((CoreRune.MW_MULTI_SPLIT_FREE_POSITION && splitLayout.mStageCoordinator.isMultiSplitActive()) ? splitLayout.mStageCoordinator.getTopStageBottom() : splitLayout.mBounds1.bottom) - getMinTopStackBottom() : CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME ? splitLayout.mStageCoordinator.getTopStageBottom() - ((int) ((r2 - splitLayout.getDisplayStableInsets(splitLayout.mContext).top) * 0.7f)) : (int) (r1.height() * 0.7f);
                        i4 = -((CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) ? Math.min(abs - (splitLayout.getDisplayLayout(splitLayout.mContext).stableInsets(true).bottom + MultiWindowUtils.getRoundedCornerRadius(splitLayout.mContext)), topStageBottom) : Math.min(abs, topStageBottom));
                    }
                    this.mTargetYOffset = i4;
                    if (i4 == this.mLastYOffset) {
                        this.mTaskBoundsAdjusted = i4 != 0;
                        ((StageCoordinator) splitLayoutHandler).setLayoutOffsetTargetFromIme(i4, splitLayout);
                    } else if (this.mTaskBoundsAdjusted && i4 == 0) {
                        boolean z8 = SplitLayout.DEBUG;
                        Slog.d("SplitLayout", "onImeStartPositioning. y offset is 0 but task adjusted. reset task bounds.");
                        this.mTaskBoundsAdjusted = false;
                        ((StageCoordinator) splitLayoutHandler).setLayoutOffsetTargetFromIme(0, splitLayout);
                    }
                    splitLayout.setDividerInteractive("onImeStartPositioning", this.mImeShown || !this.mHasImeFocus || z2, true);
                    if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && splitLayout.mStageCoordinator.isMultiSplitActive()) {
                        splitLayout.mCellSplitWindowManager.setInteractive("onImeStartPositioning", this.mImeShown || !this.mHasImeFocus, true);
                    }
                    return this.mTargetYOffset == this.mLastYOffset ? 1 : 0;
                }
            }
            z3 = false;
            if (z7) {
            }
            this.mTargetYOffset = i4;
            if (i4 == this.mLastYOffset) {
            }
            splitLayout.setDividerInteractive("onImeStartPositioning", this.mImeShown || !this.mHasImeFocus || z2, true);
            if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME) {
                splitLayout.mCellSplitWindowManager.setInteractive("onImeStartPositioning", this.mImeShown || !this.mHasImeFocus, true);
            }
            if (this.mTargetYOffset == this.mLastYOffset) {
            }
        }

        public final void reset() {
            this.mHasImeFocus = false;
            this.mImeShown = false;
            this.mTargetYOffset = 0;
            this.mLastYOffset = 0;
            this.mYOffsetForIme = 0;
            this.mDimValue1 = 0.0f;
            this.mDimValue2 = 0.0f;
        }

        private ImePositionProcessor(int i) {
            this.mDisplayId = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResizingEffectPolicy {
        public final int mParallaxType;
        public int mShrinkSide = -1;
        public int mDismissingSide = -1;
        public final Point mParallaxOffset = new Point();
        public float mDismissingDimValue = 0.0f;

        public ResizingEffectPolicy(int i) {
            new Rect();
            new Rect();
            this.mParallaxType = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SplitLayoutHandler {
    }

    public SplitLayout(String str, Context context, Configuration configuration, SplitLayoutHandler splitLayoutHandler, SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks, DisplayController displayController, DisplayImeController displayImeController, ShellTaskOrganizer shellTaskOrganizer, int i) {
        this(str, context, configuration, splitLayoutHandler, parentContainerCallbacks, displayController, displayImeController, shellTaskOrganizer, i, -1);
    }

    public static boolean isLandscape(Rect rect) {
        return rect.width() > rect.height();
    }

    public final void applyLayoutOffsetTargetForMultiSplit(WindowContainerTransaction windowContainerTransaction, int i, ArrayList arrayList) {
        Rect rect = this.mBounds2;
        Rect rect2 = this.mBounds3;
        Rect rect3 = this.mHostBounds;
        if (i == 0) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) arrayList.get(size);
                windowContainerTransaction.setBounds(runningTaskInfo.token, runningTaskInfo.configuration.windowConfiguration.getStageType() == 4 ? rect2 : (!isVerticalDivision() && (this.mCellStageWindowConfigPosition & 64) == 0) ? rect : rect3);
                windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
            }
        } else {
            int size2 = arrayList.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) arrayList.get(size2);
                Rect rect4 = runningTaskInfo2.configuration.windowConfiguration.getStageType() == 4 ? rect2 : (!isVerticalDivision() && (this.mCellStageWindowConfigPosition & 64) == 0) ? rect : rect3;
                Rect rect5 = new Rect(rect4);
                rect5.offset(0, i);
                windowContainerTransaction.setBounds(runningTaskInfo2.token, rect5);
                if (runningTaskInfo2.configuration.windowConfiguration.getBounds().equals(rect4)) {
                    WindowContainerToken windowContainerToken = runningTaskInfo2.token;
                    Configuration configuration = runningTaskInfo2.configuration;
                    windowContainerTransaction.setScreenSizeDp(windowContainerToken, configuration.screenWidthDp, configuration.screenHeightDp);
                } else {
                    DisplayLayout displayLayout = getDisplayLayout(this.mContext);
                    Rect rect6 = this.mTempRect;
                    displayLayout.getStableBounds(rect6, false);
                    rect6.intersectUnchecked(rect4);
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo2.token, (int) ((rect6.width() / getDisplayLayout(this.mContext).density()) + 0.5f), (int) ((rect6.height() / getDisplayLayout(this.mContext).density()) + 0.5f));
                }
            }
        }
    }

    public final boolean applyTaskChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
        boolean z;
        Rect rect = this.mBounds1;
        Rect rect2 = this.mWinBounds1;
        if (rect.equals(rect2) && runningTaskInfo.token.equals(this.mWinToken1)) {
            z = false;
        } else {
            windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo.token, getSmallestWidthDp(rect));
            rect2.set(rect);
            this.mWinToken1 = runningTaskInfo.token;
            z = true;
        }
        Rect rect3 = this.mBounds2;
        Rect rect4 = this.mWinBounds2;
        if (rect3.equals(rect4) && runningTaskInfo2.token.equals(this.mWinToken2)) {
            return z;
        }
        windowContainerTransaction.setBounds(runningTaskInfo2.token, rect3);
        windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo2.token, getSmallestWidthDp(rect3));
        rect4.set(rect3);
        this.mWinToken2 = runningTaskInfo2.token;
        return true;
    }

    public final DividerSnapAlgorithm createCellSnapAlgorithm() {
        boolean isCellInLeftOrTopBounds = CellUtil.isCellInLeftOrTopBounds(this.mCellStageWindowConfigPosition, isVerticalDivision());
        Rect rect = this.mHostAndCellArea;
        if (isCellInLeftOrTopBounds) {
            rect.set(this.mBounds1);
        } else {
            rect.set(this.mBounds2);
        }
        Rect rect2 = new Rect(rect);
        Rect displayStableInsets = getDisplayStableInsets(this.mContext);
        if (isVerticalDivision()) {
            rect2.top -= displayStableInsets.top;
            rect2.bottom += displayStableInsets.bottom;
        } else {
            rect2.left -= displayStableInsets.left;
            rect2.right += displayStableInsets.right;
        }
        return new DividerSnapAlgorithm(this.mContext.getResources(), rect2.width(), rect2.height(), this.mDividerSize, isVerticalDivision(), displayStableInsets);
    }

    public void flingDividePosition(int i, int i2, int i3, final Runnable runnable) {
        if (runnable != null) {
            runnable.run();
            return;
        }
        if (i == i2) {
            ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanged(this, null);
            if (runnable != null) {
                runnable.run();
            }
            InteractionJankMonitor.getInstance().end(52);
            return;
        }
        ValueAnimator valueAnimator = this.mDividerFlingAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofInt(i, i2).setDuration(i3);
        this.mDividerFlingAnimator = duration;
        duration.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mDividerFlingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.getClass();
                splitLayout.updateDivideBounds(((Integer) valueAnimator2.getAnimatedValue()).intValue());
            }
        });
        this.mDividerFlingAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.SplitLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                SplitLayout.this.mDividerFlingAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                InteractionJankMonitor.getInstance().end(52);
                SplitLayout.this.mDividerFlingAnimator = null;
            }
        });
        this.mDividerFlingAnimator.start();
    }

    public final void flingDividerToCenter() {
        final int i = this.mDividerSnapAlgorithm.getMiddleTarget().position;
        flingDividePosition(this.mDividePosition, i, 450, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SplitLayout.this.setDividePosition(i, null, true);
            }
        });
    }

    public final void flingDividerToDismiss(final int i, final boolean z) {
        flingDividePosition(this.mDividePosition, z ? this.mDividerSnapAlgorithm.getDismissEndTarget().position : this.mDividerSnapAlgorithm.getDismissStartTarget().position, 450, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SplitLayout splitLayout = SplitLayout.this;
                boolean z2 = z;
                ((StageCoordinator) splitLayout.mSplitLayoutHandler).onSnappedToDismiss(i, z2, false);
            }
        });
    }

    public final Rect getBounds1() {
        return new Rect(this.mBounds1);
    }

    public final Rect getBounds2() {
        return new Rect(this.mBounds2);
    }

    public final SurfaceControl getCellDividerLeash() {
        SplitWindowManager splitWindowManager = this.mCellSplitWindowManager;
        if (splitWindowManager == null) {
            return null;
        }
        return splitWindowManager.mLeash;
    }

    public final int getCellSide() {
        boolean isVerticalDivision = isVerticalDivision();
        int i = this.mCellStageWindowConfigPosition;
        if (isVerticalDivision) {
            if ((i & 16) != 0) {
                return 2;
            }
            if ((i & 64) != 0) {
                return 4;
            }
        } else {
            if ((i & 8) != 0) {
                return 1;
            }
            if ((i & 32) != 0) {
                return 3;
            }
        }
        return -1;
    }

    public final DividerSnapAlgorithm getCellSnapAlgorithm() {
        if (this.mCellSnapAlgorithm == null) {
            this.mCellSnapAlgorithm = createCellSnapAlgorithm();
        }
        return this.mCellSnapAlgorithm;
    }

    public final DisplayLayout getDisplayLayout(Context context) {
        return this.mDisplayController.getDisplayLayout(context.getDisplayId());
    }

    public final Rect getDisplayStableInsets(Context context) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(context.getDisplayId());
        int systemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
        if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            return Insets.NONE.toRect();
        }
        if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
            systemBars &= ~WindowInsets.Type.navigationBars();
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT) {
            systemBars &= ~WindowInsets.Type.displayCutout();
        }
        return displayLayout != null ? displayLayout.stableInsets(true) : ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(systemBars).toRect();
    }

    public final SurfaceControl getDividerLeash() {
        SplitWindowManager splitWindowManager = this.mSplitWindowManager;
        if (splitWindowManager == null) {
            return null;
        }
        return splitWindowManager.mLeash;
    }

    public final float getDividerPositionAsFraction() {
        float f;
        int i;
        boolean isVerticalDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? isVerticalDivision() : isLandscape();
        Rect rect = this.mBounds2;
        Rect rect2 = this.mBounds1;
        if (isVerticalDivision) {
            f = (rect2.right + rect.left) / 2.0f;
            i = rect.right;
        } else {
            f = (rect2.bottom + rect.top) / 2.0f;
            i = rect.bottom;
        }
        return Math.min(1.0f, Math.max(0.0f, f / i));
    }

    public final DividerSnapAlgorithm getDividerSnapAlgorithm() {
        return this.mDividerSnapAlgorithm;
    }

    public final Rect getHostBounds() {
        return new Rect(this.mHostBounds);
    }

    public final void getInitBounds(Rect rect, Rect rect2) {
        updateBounds(rect, rect2, this.mDividerSnapAlgorithm.getMiddleTarget().position, new Rect());
    }

    public final void getRefBounds1(Rect rect) {
        rect.set(this.mBounds1);
        Rect rect2 = this.mRootBounds;
        rect.offset(-rect2.left, -rect2.top);
    }

    public final void getRefBounds2(Rect rect) {
        rect.set(this.mBounds2);
        Rect rect2 = this.mRootBounds;
        rect.offset(-rect2.left, -rect2.top);
    }

    public final Rect getRefCellDividerBounds() {
        Rect rect = new Rect(this.mCellDividerBounds);
        Rect rect2 = this.mRootBounds;
        rect.offset(-rect2.left, -rect2.top);
        return rect;
    }

    public final void getRefDividerBounds(Rect rect) {
        rect.set(this.mDividerBounds);
        Rect rect2 = this.mRootBounds;
        rect.offset(-rect2.left, -rect2.top);
    }

    public final Rect getRefHostBounds() {
        Rect hostBounds = getHostBounds();
        Rect rect = this.mRootBounds;
        hostBounds.offset(-rect.left, -rect.top);
        return hostBounds;
    }

    public final int getSmallestWidthDp(Rect rect) {
        Insets calculateInsets;
        Rect rect2 = this.mTempRect;
        rect2.set(rect);
        if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            Rect rect3 = new Rect();
            DisplayLayout displayLayout = getDisplayLayout(this.mContext);
            if (displayLayout == null) {
                rect2.inset(getDisplayStableInsets(this.mContext));
            } else {
                rect3.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                int navigationBars = WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout();
                if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
                    calculateInsets = Insets.NONE;
                } else {
                    if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
                        navigationBars &= ~WindowInsets.Type.navigationBars();
                    }
                    calculateInsets = this.mInsetsState.calculateInsets(rect3, navigationBars, false);
                }
                rect3.inset(calculateInsets);
                rect2.intersect(rect3);
            }
        }
        return (int) (Math.min(rect2.width(), rect2.height()) / this.mContext.getResources().getDisplayMetrics().density);
    }

    public final DividerSnapAlgorithm getSnapAlgorithm(Context context, Rect rect) {
        boolean isVerticalDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? isVerticalDivision() : isLandscape(rect);
        return new DividerSnapAlgorithm(context.getResources(), rect.width(), rect.height(), this.mDividerSize, !isVerticalDivision, getDisplayStableInsets(context), isVerticalDivision ? 1 : 2);
    }

    public final void init() {
        if (this.mInitialized) {
            return;
        }
        this.mInitialized = true;
        this.mSplitWindowManager.init(this, this.mInsetsState);
        this.mDisplayImeController.addPositionProcessor(this.mImePositionProcessor);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initDividerPosition(Rect rect, boolean z) {
        float width;
        int width2;
        float f;
        int i;
        Rect rect2 = this.mTempRect2;
        Rect rect3 = this.mRootBounds;
        rect2.set(rect3);
        rect2.inset(getDisplayStableInsets(this.mContext));
        boolean equals = rect.equals(rect3);
        if (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            width = this.mDividePosition / (isLandscape(rect) ? rect.width() : rect.height());
            width2 = isLandscape() ? rect3.width() : rect3.height();
        } else {
            if (equals) {
                width = (this.mDividePosition - (z ? rect2.left : rect2.top)) / ((z ? rect2.width() : rect2.height()) - this.mDividerSize);
                f = (isVerticalDivision() ? rect2.width() : rect2.height()) - this.mDividerSize;
                if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION || !equals) {
                    i = (int) ((f * width) + 0.5f);
                } else {
                    i = ((int) (f * width)) + (isVerticalDivision() ? rect2.left : rect2.top);
                }
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
                    setCellDividePosition((int) ((isVerticalDivision() ? rect3.height() : rect3.width()) * (this.mCellDividePosition / (z ? rect.height() : rect.width()))), null, false);
                }
                int i2 = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i).position;
                this.mDividePosition = i2;
                updateBounds(i2);
            }
            width = this.mDividePosition / (z ? rect.width() : rect.height());
            width2 = isVerticalDivision() ? rect3.width() : rect3.height();
        }
        f = width2;
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
        }
        i = (int) ((f * width) + 0.5f);
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
        }
        int i22 = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i).position;
        this.mDividePosition = i22;
        updateBounds(i22);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
    
        if ((r6.mStageCoordinator.mTopStageAfterFoldDismiss != -1) != false) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0089  */
    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void insetsChanged(InsetsState insetsState) {
        boolean z;
        boolean z2;
        boolean z3;
        SplitWindowManager splitWindowManager;
        DividerView dividerView;
        boolean z4 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
        InsetsState insetsState2 = this.mInsetsState;
        if (z4) {
        }
        if (!MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && !MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED && !insetsState2.equals(insetsState) && !this.mFreezeDividerWindow) {
            Insets calculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false);
            Rect rect = calculateInsets.toRect();
            Rect rect2 = this.mNavigationBarRect;
            if (!rect2.equals(rect)) {
                rect2.set(calculateInsets.toRect());
                z = true;
                if (z || !insetsState.getDisplayFrame().equals(this.mRootBounds)) {
                    z2 = false;
                } else {
                    if (CoreRune.SAFE_DEBUG || CoreRune.IS_DEBUG_LEVEL_MID) {
                        Slog.d("SplitLayout", "insetsChanged. and update layout for newInsets=" + insetsState);
                    }
                    z2 = true;
                }
                insetsState2.set(insetsState);
                z3 = this.mInitialized;
                SplitLayoutHandler splitLayoutHandler = this.mSplitLayoutHandler;
                if (z3) {
                    if (z2) {
                        ((StageCoordinator) splitLayoutHandler).handleLayoutSizeChange(this, false);
                        return;
                    }
                    return;
                } else {
                    if (this.mFreezeDividerWindow) {
                        return;
                    }
                    DividerView dividerView2 = this.mSplitWindowManager.mDividerView;
                    if (dividerView2 != null) {
                        dividerView2.onInsetsChanged(insetsState, true);
                    }
                    if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && (splitWindowManager = this.mCellSplitWindowManager) != null && (dividerView = splitWindowManager.mDividerView) != null) {
                        dividerView.onInsetsChanged(insetsState, true);
                    }
                    if (z2) {
                        ((StageCoordinator) splitLayoutHandler).handleLayoutSizeChange(this, false);
                        return;
                    }
                    return;
                }
            }
        }
        z = false;
        if (z) {
        }
        z2 = false;
        insetsState2.set(insetsState);
        z3 = this.mInitialized;
        SplitLayoutHandler splitLayoutHandler2 = this.mSplitLayoutHandler;
        if (z3) {
        }
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        if (this.mInsetsState.equals(insetsState)) {
            return;
        }
        insetsChanged(insetsState);
    }

    public final boolean isSplitScreenFeasible(boolean z) {
        return new DividerSnapAlgorithm(this.mContext.getResources(), getDisplayLayout(this.mContext).mWidth, getDisplayLayout(this.mContext).mHeight, this.mDividerSize, z, getDisplayLayout(this.mContext).stableInsets(true), z ? 2 : 1).isSplitScreenFeasible();
    }

    public final boolean isVerticalDivision() {
        return this.mSplitDivision == 0;
    }

    public final ValueAnimator moveSurface(final SurfaceControl.Transaction transaction, final SurfaceControl surfaceControl, Rect rect, Rect rect2, final float f, final float f2) {
        final Rect rect3 = new Rect(rect);
        Rect rect4 = new Rect(rect2);
        final float f3 = rect4.left - rect3.left;
        final float f4 = rect4.top - rect3.top;
        final float width = rect4.width() - rect3.width();
        final float height = rect4.height() - rect3.height();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SplitLayout splitLayout = SplitLayout.this;
                SurfaceControl surfaceControl2 = surfaceControl;
                Rect rect5 = rect3;
                float f5 = f3;
                float f6 = f4;
                float f7 = width;
                float f8 = height;
                float f9 = f;
                float f10 = f2;
                SurfaceControl.Transaction transaction2 = transaction;
                splitLayout.getClass();
                if (surfaceControl2 == null) {
                    return;
                }
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float f11 = (f5 * floatValue) + rect5.left;
                float f12 = (f6 * floatValue) + rect5.top;
                int width2 = (int) ((f7 * floatValue) + rect5.width());
                int height2 = (int) ((f8 * floatValue) + rect5.height());
                if (f9 == 0.0f && f10 == 0.0f) {
                    transaction2.setPosition(surfaceControl2, f11, f12);
                    transaction2.setWindowCrop(surfaceControl2, width2, height2);
                } else {
                    int i = (int) (f9 * floatValue);
                    int i2 = (int) (floatValue * f10);
                    transaction2.setPosition(surfaceControl2, f11 + i, f12 + i2);
                    Rect rect6 = splitLayout.mTempRect;
                    rect6.set(0, 0, width2, height2);
                    rect6.offsetTo(-i, -i2);
                    transaction2.setCrop(surfaceControl2, rect6);
                }
                transaction2.apply();
            }
        });
        return ofFloat;
    }

    public final void release(SurfaceControl.Transaction transaction) {
        if (this.mInitialized) {
            this.mInitialized = false;
            this.mSplitWindowManager.release(transaction);
            DisplayImeController displayImeController = this.mDisplayImeController;
            ImePositionProcessor imePositionProcessor = this.mImePositionProcessor;
            synchronized (displayImeController.mPositionProcessors) {
                displayImeController.mPositionProcessors.remove(imePositionProcessor);
            }
            this.mImePositionProcessor.reset();
            ValueAnimator valueAnimator = this.mDividerFlingAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            resetDividerPosition();
        }
    }

    public final void releaseCellDivider(SurfaceControl.Transaction transaction) {
        if (this.mCellInitialized) {
            this.mCellInitialized = false;
            this.mCellSplitWindowManager.release(transaction);
        }
    }

    public final void resetDividerPosition() {
        int i = this.mDividerSnapAlgorithm.getMiddleTarget().position;
        this.mDividePosition = i;
        updateBounds(i);
        this.mWinToken1 = null;
        this.mWinToken2 = null;
        this.mWinBounds1.setEmpty();
        this.mWinBounds2.setEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
    
        if ((r8.mSplitScreenFeasibleMode == 1) != false) goto L19;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void rotateTo(int i) {
        Object[] objArr = (((i - this.mRotation) + 4) % 4) % 2 != 0;
        this.mRotation = i;
        Rect rect = this.mRootBounds;
        Rect rect2 = new Rect(rect);
        if (objArr != false) {
            rect2.set(rect.top, rect.left, rect.bottom, rect.right);
        }
        Rect rect3 = this.mTempRect;
        rect3.set(rect);
        rect.set(rect2);
        boolean isVerticalDivision = isVerticalDivision();
        if (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || !MultiWindowUtils.isInSubDisplay(this.mContext)) {
            if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            }
            this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, rect);
            initDividerPosition(rect3, isVerticalDivision);
        }
        if (objArr != false) {
            this.mSplitDivision = !isLandscape() ? 1 : 0;
        }
        this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, rect);
        initDividerPosition(rect3, isVerticalDivision);
    }

    public final void setCellDividePosition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (DEBUG && this.mCellDividePosition != i) {
            Slog.d("SplitLayout", "setCellDividePosition: " + this.mCellDividePosition + " -> " + i);
        }
        this.mCellDividePosition = i;
        updateCellAndHostBounds(i);
        if (z) {
            ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanged(this, windowContainerTransaction);
        }
    }

    public final void setCellDividerRatio(float f, int i, boolean z, boolean z2) {
        int i2;
        int width;
        boolean isVerticalDivision = isVerticalDivision();
        Rect rect = new Rect();
        Rect bounds1 = getBounds1();
        Rect bounds2 = getBounds2();
        if (CellUtil.isCellInLeftOrTopBounds(i, isVerticalDivision)) {
            rect.set(bounds1);
        } else {
            rect.set(bounds2);
        }
        int i3 = z ? this.mDividerSize : 0;
        if (z2) {
            f = 1.0f - f;
        }
        if (isVerticalDivision) {
            i2 = rect.top;
            width = rect.height();
        } else {
            i2 = rect.left;
            width = rect.width();
        }
        int i4 = i2 + ((int) (((width - i3) * f) + 0.5f));
        if (z2) {
            i4 -= this.mDividerSize;
        }
        updateCellStageWindowConfigPosition(i);
        updateCellAndHostBounds(i4);
        setCellDividePosition(createCellSnapAlgorithm().calculateNonDismissingSnapTarget(i4).position, null, false);
    }

    public final void setDividePosition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        SplitWindowManager splitWindowManager;
        DividerView dividerView;
        this.mDividePosition = i;
        updateBounds(i);
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && (splitWindowManager = this.mCellSplitWindowManager) != null && this.mStageCoordinator.isMultiSplitActive()) {
            splitWindowManager.getClass();
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && (dividerView = splitWindowManager.mDividerView) != null) {
                dividerView.mSetTouchRegion = true;
            }
        }
        if (z) {
            ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanged(this, windowContainerTransaction);
        }
    }

    public final void setDivideRatio(float f, boolean z, boolean z2) {
        int i;
        int height;
        boolean isVerticalDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? isVerticalDivision() : isLandscape();
        Rect rect = this.mRootBounds;
        Rect rect2 = this.mTempRect;
        rect2.set(rect);
        if (z) {
            rect2.inset(getDisplayStableInsets(this.mContext));
        }
        int i2 = z2 ? this.mDividerSize : 0;
        if (isVerticalDivision) {
            i = rect2.left;
            height = rect2.width();
        } else {
            i = rect2.top;
            height = rect2.height();
        }
        setDividePosition(this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i + ((int) (((height - i2) * f) + 0.5f))).position, null, false);
    }

    public final void setDividerAtBorder(boolean z) {
        setDividePosition(z ? this.mDividerSnapAlgorithm.getDismissStartTarget().position : this.mDividerSnapAlgorithm.getDismissEndTarget().position, null, false);
    }

    public final void setDividerInteractive(String str, boolean z, boolean z2) {
        this.mSplitWindowManager.setInteractive(str, z, z2);
    }

    public final void snapToTarget(int i, final DividerSnapAlgorithm.SnapTarget snapTarget, final boolean z) {
        int i2 = snapTarget.flag;
        final int i3 = 1;
        if (i2 == 1) {
            final int i4 = 0;
            flingDividePosition(i, snapTarget.position, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda2
                public final /* synthetic */ SplitLayout f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            ((StageCoordinator) this.f$0.mSplitLayoutHandler).onSnappedToDismiss(4, false, false);
                            break;
                        default:
                            ((StageCoordinator) this.f$0.mSplitLayoutHandler).onSnappedToDismiss(4, true, false);
                            break;
                    }
                }
            });
        } else if (i2 != 2) {
            flingDividePosition(i, snapTarget.position, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SplitLayout splitLayout = SplitLayout.this;
                    boolean z2 = z;
                    DividerSnapAlgorithm.SnapTarget snapTarget2 = snapTarget;
                    splitLayout.getClass();
                    if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING && z2) {
                        splitLayout.setCellDividePosition(snapTarget2.position, null, true);
                    } else {
                        splitLayout.setDividePosition(snapTarget2.position, null, true);
                    }
                }
            });
        } else {
            flingDividePosition(i, snapTarget.position, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda2
                public final /* synthetic */ SplitLayout f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            ((StageCoordinator) this.f$0.mSplitLayoutHandler).onSnappedToDismiss(4, false, false);
                            break;
                        default:
                            ((StageCoordinator) this.f$0.mSplitLayoutHandler).onSnappedToDismiss(4, true, false);
                            break;
                    }
                }
            });
        }
    }

    public final void splitSwitching(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, final StageCoordinator$$ExternalSyntheticLambda11 stageCoordinator$$ExternalSyntheticLambda11) {
        boolean isLandscape = isLandscape();
        final Rect displayStableInsets = getDisplayStableInsets(this.mContext);
        displayStableInsets.set(isLandscape ? displayStableInsets.left : 0, isLandscape ? 0 : displayStableInsets.top, isLandscape ? displayStableInsets.right : 0, isLandscape ? 0 : displayStableInsets.bottom);
        DividerSnapAlgorithm dividerSnapAlgorithm = this.mDividerSnapAlgorithm;
        Rect rect = this.mBounds2;
        final int i = dividerSnapAlgorithm.calculateNonDismissingSnapTarget(isLandscape ? rect.width() : rect.height()).position;
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        Rect rect4 = new Rect();
        updateBounds(rect3, rect2, i, rect4);
        Rect rect5 = this.mRootBounds;
        rect2.offset(-rect5.left, -rect5.top);
        rect3.offset(-rect5.left, -rect5.top);
        rect4.offset(-rect5.left, -rect5.top);
        Rect bounds1 = getBounds1();
        bounds1.offset(-rect5.left, -rect5.top);
        ValueAnimator moveSurface = moveSurface(transaction, surfaceControl, bounds1, rect2, -displayStableInsets.left, -displayStableInsets.top);
        Rect bounds2 = getBounds2();
        bounds2.offset(-rect5.left, -rect5.top);
        ValueAnimator moveSurface2 = moveSurface(transaction, surfaceControl2, bounds2, rect3, displayStableInsets.left, displayStableInsets.top);
        SurfaceControl dividerLeash = getDividerLeash();
        Rect rect6 = new Rect(this.mDividerBounds);
        rect6.offset(-rect5.left, -rect5.top);
        ValueAnimator moveSurface3 = moveSurface(transaction, dividerLeash, rect6, rect4, 0.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(moveSurface, moveSurface2, moveSurface3);
        animatorSet.setDuration(350L);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.SplitLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SplitLayout splitLayout = SplitLayout.this;
                int i2 = i;
                splitLayout.mDividePosition = i2;
                splitLayout.updateBounds(i2);
                stageCoordinator$$ExternalSyntheticLambda11.accept(displayStableInsets);
            }
        });
        animatorSet.start();
    }

    public final void update(SurfaceControl.Transaction transaction) {
        if (!this.mInitialized) {
            init();
            return;
        }
        boolean z = CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER;
        InsetsState insetsState = this.mInsetsState;
        ImePositionProcessor imePositionProcessor = this.mImePositionProcessor;
        SplitWindowManager splitWindowManager = this.mSplitWindowManager;
        if (!z || !this.mStageCoordinator.isMultiSplitActive()) {
            splitWindowManager.release(transaction);
            imePositionProcessor.reset();
            splitWindowManager.init(this, insetsState);
            return;
        }
        splitWindowManager.release(transaction);
        releaseCellDivider(transaction);
        imePositionProcessor.reset();
        splitWindowManager.init(this, insetsState);
        if (this.mCellInitialized) {
            return;
        }
        this.mCellInitialized = true;
        this.mCellSplitWindowManager.init(this, insetsState);
        this.mCellSnapAlgorithm = createCellSnapAlgorithm();
    }

    public final void updateBounds(int i) {
        updateBounds(this.mBounds1, this.mBounds2, i, this.mDividerBounds);
    }

    public final void updateCellAndHostBounds(int i) {
        DividerView dividerView;
        boolean isCellInLeftOrTopBounds = CellUtil.isCellInLeftOrTopBounds(this.mCellStageWindowConfigPosition, isVerticalDivision());
        Rect rect = this.mHostAndCellArea;
        if (isCellInLeftOrTopBounds) {
            rect.set(this.mBounds1);
        } else {
            rect.set(this.mBounds2);
        }
        int cellSide = getCellSide();
        int i2 = this.mDividerSize;
        Rect rect2 = this.mBounds3;
        Rect rect3 = this.mHostBounds;
        if (cellSide == -1) {
            Slog.e("CellUtil", "calcBoundsForPosition. dockSide invalid. ");
        } else {
            DockedDividerUtils.calculateBoundsForCellWithPosition(i, cellSide, rect2, rect, i2);
            DockedDividerUtils.calculateBoundsForCellWithPosition(i, DockedDividerUtils.invertDockSide(cellSide), rect3, rect, i2);
        }
        if (DEBUG) {
            Slog.d("SplitLayout", "updateCellAndHostBounds: mHostAndCellArea=" + rect + ", cell=" + rect2 + ", host=" + rect3 + ", cellDividePos=" + i + ", cellSide=" + cellSide + ", cellConfigPos=" + WindowConfiguration.stagePositionToString(this.mCellStageWindowConfigPosition) + ", Callers=" + Debug.getCallers(5));
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
            Rect rect4 = this.mCellDividerBounds;
            rect4.set(rect2);
            if (cellSide == 1) {
                int i3 = rect2.right;
                int i4 = this.mDividerInsets;
                rect4.left = i3 - i4;
                rect4.right = i3 + this.mDividerSize + i4;
            } else if (cellSide == 2) {
                int i5 = rect2.bottom;
                int i6 = this.mDividerInsets;
                rect4.bottom = i5 + i6 + this.mDividerSize;
                rect4.top = rect2.bottom - i6;
            } else if (cellSide == 3) {
                int i7 = rect2.left;
                int i8 = this.mDividerInsets;
                rect4.left = (i7 - i8) - this.mDividerSize;
                rect4.right = rect2.left + i8;
            } else if (cellSide == 4) {
                int i9 = rect2.top;
                int i10 = this.mDividerInsets;
                rect4.bottom = i9 + i10;
                rect4.top = (i9 - this.mDividerSize) - i10;
            }
            int width = rect4.width();
            int height = rect4.height();
            SplitWindowManager splitWindowManager = this.mCellSplitWindowManager;
            splitWindowManager.getClass();
            if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || !splitWindowManager.mIsCellDivider || (dividerView = splitWindowManager.mDividerView) == null || splitWindowManager.mViewHost == null) {
                return;
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) dividerView.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            splitWindowManager.mViewHost.relayout(layoutParams);
            splitWindowManager.mDividerView.onInsetsChanged(this.mInsetsState, false);
        }
    }

    public final void updateCellStageWindowConfigPosition(int i) {
        if (this.mCellStageWindowConfigPosition != i) {
            this.mCellStageWindowConfigPosition = i;
            DividerSnapAlgorithm createCellSnapAlgorithm = createCellSnapAlgorithm();
            this.mCellSnapAlgorithm = createCellSnapAlgorithm;
            setCellDividePosition(createCellSnapAlgorithm.getMiddleTarget().position, null, false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean updateConfiguration(Configuration configuration) {
        SplitWindowManager splitWindowManager;
        int rotation = configuration.windowConfiguration.getRotation();
        Rect bounds = configuration.windowConfiguration.getBounds();
        int i = configuration.orientation;
        int i2 = configuration.densityDpi;
        int i3 = configuration.uiMode;
        Locale locale = configuration.locale;
        float f = configuration.fontScale;
        int i4 = configuration.fontWeightAdjustment;
        int i5 = this.mOrientation;
        Rect rect = this.mRootBounds;
        if (i5 == i && this.mRotation == rotation && this.mDensity == i2 && this.mUiMode == i3 && rect.equals(bounds) && this.mLocale.equals(locale) && this.mFontScale == f && this.mFontWeightAdjustment == i4) {
            return false;
        }
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && !bounds.equals(rect) && configuration.semDisplayDeviceType != 5) {
            if ((this.mSplitScreenFeasibleMode == 1) != false) {
                boolean isSplitScreenFeasible = isSplitScreenFeasible(true);
                boolean isSplitScreenFeasible2 = isSplitScreenFeasible(false);
                if (isSplitScreenFeasible) {
                    this.mPossibleSplitDivision = 1;
                } else if (isSplitScreenFeasible2) {
                    this.mPossibleSplitDivision = 0;
                }
                Slog.d("SplitLayout", "split feasible changed, splitDivision=" + this.mPossibleSplitDivision);
            }
        }
        this.mContext = this.mContext.createConfigurationContext(configuration);
        this.mSplitWindowManager.setConfiguration(configuration);
        this.mOrientation = i;
        Rect rect2 = this.mTempRect;
        rect2.set(rect);
        rect.set(bounds);
        this.mRotation = rotation;
        this.mDensity = i2;
        this.mUiMode = i3;
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && (splitWindowManager = this.mCellSplitWindowManager) != null) {
            splitWindowManager.setConfiguration(configuration);
        }
        this.mLocale = locale;
        this.mFontScale = f;
        this.mFontWeightAdjustment = i4;
        boolean isVerticalDivision = isVerticalDivision();
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && configuration.semDisplayDeviceType == 5) {
            this.mSplitDivision = !isLandscape() ? 1 : 0;
        }
        this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, rect);
        updateDividerConfig(this.mContext);
        initDividerPosition(rect2, isVerticalDivision);
        updateInvisibleRect();
        return true;
    }

    public final void updateDivideBounds(int i) {
        updateBounds(i);
        Point point = this.mSurfaceEffectPolicy.mParallaxOffset;
        ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanging(point.x, point.y, this);
    }

    public final void updateDividerConfig(Context context) {
        StageCoordinator stageCoordinator;
        Resources resources = context.getResources();
        Display display = context.getDisplay();
        int dimensionPixelSize = CoreRune.MW_MULTI_SPLIT_DIVIDER ? CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? resources.getDimensionPixelSize(R.dimen.notification_big_picture_max_width) : resources.getDimensionPixelSize(R.dimen.notification_big_picture_max_height_low_ram) : resources.getDimensionPixelSize(R.dimen.dropdownitem_icon_width);
        RoundedCorner roundedCorner = display.getRoundedCorner(0);
        int max = roundedCorner != null ? Math.max(0, roundedCorner.getRadius()) : 0;
        RoundedCorner roundedCorner2 = display.getRoundedCorner(1);
        if (roundedCorner2 != null) {
            max = Math.max(max, roundedCorner2.getRadius());
        }
        RoundedCorner roundedCorner3 = display.getRoundedCorner(2);
        if (roundedCorner3 != null) {
            max = Math.max(max, roundedCorner3.getRadius());
        }
        RoundedCorner roundedCorner4 = display.getRoundedCorner(3);
        if (roundedCorner4 != null) {
            max = Math.max(max, roundedCorner4.getRadius());
        }
        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && (stageCoordinator = this.mStageCoordinator) != null && stageCoordinator.mIsVideoControls) {
            this.mDividerSize = 0;
            this.mDividerInsets = 0;
        } else {
            this.mDividerInsets = Math.max(dimensionPixelSize, max);
            this.mDividerSize = resources.getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? com.android.systemui.R.dimen.split_divider_bar_width_fold : com.android.systemui.R.dimen.split_divider_bar_width);
        }
        this.mDividerWindowWidth = (this.mDividerInsets * 2) + this.mDividerSize;
    }

    public final void updateInvisibleRect() {
        Rect rect = this.mRootBounds;
        int i = rect.left;
        int i2 = rect.top;
        int i3 = isLandscape() ? rect.right / 2 : rect.right;
        int i4 = isLandscape() ? rect.bottom : rect.bottom / 2;
        Rect rect2 = this.mInvisibleBounds;
        rect2.set(i, i2, i3, i4);
        rect2.offset(isLandscape() ? rect.right : 0, isLandscape() ? 0 : rect.bottom);
    }

    public final void updateSnapAlgorithm(int i) {
        Rect rect = this.mTempRect;
        Rect rect2 = this.mRootBounds;
        rect.set(rect2);
        this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, rect2);
        initDividerPosition(rect, i == 0);
    }

    public SplitLayout(String str, Context context, Configuration configuration, SplitLayoutHandler splitLayoutHandler, SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks, DisplayController displayController, DisplayImeController displayImeController, ShellTaskOrganizer shellTaskOrganizer, int i, int i2) {
        this.mTempRect = new Rect();
        Rect rect = new Rect();
        this.mRootBounds = rect;
        this.mDividerBounds = new Rect();
        this.mBounds1 = new Rect();
        this.mBounds2 = new Rect();
        this.mInvisibleBounds = new Rect();
        this.mWinBounds1 = new Rect();
        this.mWinBounds2 = new Rect();
        this.mInsetsState = new InsetsState();
        int i3 = 0;
        this.mInitialized = false;
        this.mFreezeDividerWindow = false;
        this.mTempRect2 = new Rect();
        this.mSplitDivision = 0;
        this.mHostAndCellArea = new Rect();
        this.mBounds3 = new Rect();
        this.mWinBounds3 = new Rect();
        this.mHostBounds = new Rect();
        this.mCellStageWindowConfigPosition = 0;
        this.mCellInitialized = false;
        this.mCellDividerBounds = new Rect();
        this.mNavigationBarRect = new Rect();
        this.mSplitScreenFeasibleMode = 2;
        this.mPossibleSplitDivision = -1;
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            this.mSplitDivision = i2;
        }
        this.mContext = context.createConfigurationContext(configuration);
        this.mOrientation = configuration.orientation;
        this.mRotation = configuration.windowConfiguration.getRotation();
        this.mDensity = configuration.densityDpi;
        this.mSplitLayoutHandler = splitLayoutHandler;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mSplitWindowManager = new SplitWindowManager(str, this.mContext, configuration, parentContainerCallbacks);
        this.mLocale = configuration.locale;
        this.mFontScale = configuration.fontScale;
        this.mFontWeightAdjustment = configuration.fontWeightAdjustment;
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
            this.mCellSplitWindowManager = new SplitWindowManager(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "Cell"), this.mContext, configuration, parentContainerCallbacks, true);
        } else {
            this.mCellSplitWindowManager = null;
        }
        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
            this.mSharedPreferences = this.mContext.getSharedPreferences("video_controls_pref", 0);
        }
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mImePositionProcessor = new ImePositionProcessor(this, this.mContext.getDisplayId(), i3);
        this.mSurfaceEffectPolicy = new ResizingEffectPolicy(i);
        updateDividerConfig(this.mContext);
        rect.set(configuration.windowConfiguration.getBounds());
        this.mDividerSnapAlgorithm = getSnapAlgorithm(this.mContext, rect);
        resetDividerPosition();
        this.mContext.getResources().getBoolean(com.android.systemui.R.bool.config_dimNonImeAttachedSide);
        updateInvisibleRect();
    }

    public final boolean isLandscape() {
        return isLandscape(this.mRootBounds);
    }

    public final void updateBounds(Rect rect, Rect rect2, int i, Rect rect3) {
        StageCoordinator stageCoordinator;
        StageCoordinator stageCoordinator2;
        boolean z = CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY;
        Rect rect4 = this.mRootBounds;
        Rect rect5 = this.mTempRect;
        if (z || CoreRune.IS_TABLET_DEVICE) {
            rect5.set(rect4);
            rect5.inset(getDisplayStableInsets(this.mContext));
            rect3.set(rect5);
            rect.set(rect5);
            rect2.set(rect5);
        } else {
            rect3.set(rect4);
            rect.set(rect4);
            rect2.set(rect4);
        }
        boolean isVerticalDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? isVerticalDivision() : isLandscape(rect4);
        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && this.mDividerSize == 0 && (stageCoordinator2 = this.mStageCoordinator) != null && (stageCoordinator2.mIsVideoControls || stageCoordinator2.mWillBeVideoControls)) {
            rect5.set(rect4);
            rect5.inset(new Rect());
            Rect rect6 = this.mBounds1;
            rect6.set(rect5);
            Rect rect7 = this.mBounds2;
            rect7.set(rect5);
            int i2 = this.mSharedPreferences.getInt("video_controls_mode", 0);
            int width = ((rect4.width() * 9) * 10) / (i2 != 1 ? i2 != 2 ? 160 : IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberDelay : 180);
            rect6.bottom = width;
            rect7.top = width;
            Rect rect8 = this.mDividerBounds;
            rect8.bottom = 0;
            rect8.top = 0;
        } else if (isVerticalDivision) {
            int i3 = i + rect4.left;
            int i4 = i3 - this.mDividerInsets;
            rect3.left = i4;
            rect3.right = i4 + this.mDividerWindowWidth;
            rect.right = i3;
            rect2.left = i3 + this.mDividerSize;
        } else {
            int i5 = i + rect4.top;
            int i6 = i5 - this.mDividerInsets;
            rect3.top = i6;
            rect3.bottom = i6 + this.mDividerWindowWidth;
            rect.bottom = i5;
            rect2.top = i5 + this.mDividerSize;
        }
        DockedDividerUtils.sanitizeStackBounds(rect, true);
        DockedDividerUtils.sanitizeStackBounds(rect2, false);
        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && (stageCoordinator = this.mStageCoordinator) != null) {
            stageCoordinator.mWillBeVideoControls = false;
        }
        if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
            this.mCellSnapAlgorithm = createCellSnapAlgorithm();
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            updateCellAndHostBounds(this.mCellDividePosition);
        }
    }

    public final boolean applyTaskChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2, ActivityManager.RunningTaskInfo runningTaskInfo3) {
        boolean z;
        Rect rect;
        Rect rect2 = this.mBounds3;
        Rect rect3 = this.mWinBounds3;
        if (rect2.equals(rect3) && runningTaskInfo3.token.equals(this.mWinToken3)) {
            z = false;
        } else {
            windowContainerTransaction.setBounds(runningTaskInfo3.token, rect2);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo3.token, getSmallestWidthDp(rect2));
            rect3.set(rect2);
            this.mWinToken3 = runningTaskInfo3.token;
            z = true;
        }
        boolean isCellInLeftOrTopBounds = CellUtil.isCellInLeftOrTopBounds(this.mCellStageWindowConfigPosition, isVerticalDivision());
        Rect rect4 = this.mHostBounds;
        if (isCellInLeftOrTopBounds) {
            rect = this.mBounds2;
        } else {
            rect4 = this.mBounds1;
            rect = rect4;
        }
        Rect rect5 = this.mWinBounds1;
        if (!rect4.equals(rect5) || !runningTaskInfo.token.equals(this.mWinToken1)) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, rect4);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo.token, getSmallestWidthDp(rect4));
            rect5.set(rect4);
            this.mWinToken1 = runningTaskInfo.token;
            z = true;
        }
        Rect rect6 = this.mWinBounds2;
        if (rect.equals(rect6) && runningTaskInfo2.token.equals(this.mWinToken2)) {
            return z;
        }
        windowContainerTransaction.setBounds(runningTaskInfo2.token, rect);
        windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo2.token, getSmallestWidthDp(rect));
        rect6.set(rect);
        this.mWinToken2 = runningTaskInfo2.token;
        return true;
    }
}
