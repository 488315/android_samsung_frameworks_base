package com.android.wm.shell.common.split;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.util.TypedValue;
import android.view.Display;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.RoundedCorner;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda28;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda6;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SplitLayout implements DisplayInsetsController.OnInsetsChangedListener {
    public final boolean mAllowLeftRightSplitInPortrait;
    public final Rect mBounds3;
    public final Rect mCellDividerBounds;
    public int mCellDividerPosition;
    public boolean mCellInitialized;
    public DividerSnapAlgorithm mCellSnapAlgorithm;
    public final SplitWindowManager mCellSplitWindowManager;
    public int mCellStageWindowConfigPosition;
    public final List mContentBounds;
    public Context mContext;
    public int mDensity;
    public final DesktopState mDesktopState;
    public final boolean mDimNonImeSide;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final Rect mDividerBounds;
    public ValueAnimator mDividerFlingAnimator;
    public int mDividerInsets;
    public int mDividerPosition;
    public int mDividerSize;
    DividerSnapAlgorithm mDividerSnapAlgorithm;
    public int mDividerWindowWidth;
    public float mFontScale;
    public int mFontWeightAdjustment;
    public boolean mFreezeDividerWindow;
    public final Handler mHandler;
    public final Rect mHostAndCellArea;
    public final Rect mHostBounds;
    public final ImePositionProcessor mImePositionProcessor;
    public boolean mInitialized;
    public final InsetsState mInsetsState;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final Rect mInvisibleBounds;
    public boolean mIsLargeScreen;
    public boolean mIsLeftRightSplit;
    public Locale mLocale;
    public final Rect mNavigationBarRect;
    public final Rect mOffScreenMovingBounds1;
    public final Rect mOffScreenMovingBounds2;
    public final List mOffscreenTouchZones;
    public int mOrientation;
    public boolean mParallelMultiSplit;
    public final SplitWindowManager.ParentContainerCallbacks mParentContainerCallbacks;
    public Insets mPinnedTaskbarInsets;
    public int mPossibleSplitDivision;
    public final Rect mRootBounds;
    public int mRotation;
    public int mSplitDivision;
    public final SplitLayoutHandler mSplitLayoutHandler;
    public int mSplitScreenFeasibleMode;
    public final SplitState mSplitState;
    public final SplitWindowManager mSplitWindowManager;
    public final List mStageBounds;
    public StageCoordinator mStageCoordinator;
    public final ResizingEffectPolicy mSurfaceEffectPolicy;
    public AnimatorSet mSwapAnimator;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Rect mTempRect;
    public final Rect mTempRect2;
    public int mUiMode;
    public final Rect mWinBounds3;
    public WindowContainerToken mWinToken1;
    public WindowContainerToken mWinToken2;
    public WindowContainerToken mWinToken3;
    public static final Interpolator SHRINK_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    public static final Interpolator GROW_INTERPOLATOR = new PathInterpolator(0.45f, 0.0f, 0.5f, 1.0f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ImePositionProcessor implements DisplayImeController.ImePositionProcessor {
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
                    Rect rect = splitLayout.mTempRect;
                    rect.set(splitLayout.mDividerBounds);
                    Rect rect2 = splitLayout.mRootBounds;
                    rect.offset(-rect2.left, -rect2.top);
                    splitLayout.mTempRect.offset(0, this.mYOffsetForIme);
                    Rect rect3 = splitLayout.mTempRect;
                    transaction.setPosition(surfaceControl, rect3.left, rect3.top);
                }
                splitLayout.copyTopLeftRefBounds(splitLayout.mTempRect);
                transaction.setWindowCrop(surfaceControl2, splitLayout.mTempRect.width(), splitLayout.mTempRect.height() + this.mYOffsetForIme);
                Rect rect4 = splitLayout.mTempRect;
                rect4.set(splitLayout.getBottomRightBounds());
                Rect rect5 = splitLayout.mRootBounds;
                rect4.offset(-rect5.left, -rect5.top);
                splitLayout.mTempRect.offset(0, this.mYOffsetForIme);
                Rect rect6 = splitLayout.mTempRect;
                transaction.setPosition(surfaceControl3, rect6.left, rect6.top);
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
            return (z && splitLayout.mStageCoordinator.isMultiSplitActive()) ? splitLayout.isVerticalDivision() ? splitLayout.getCellSnapAlgorithm().mFirstSplitTarget.position : splitLayout.mDividerSnapAlgorithm.mFirstSplitTarget.position : splitLayout.mSplitState.isSplitStashed() ? splitLayout.mDividerSnapAlgorithm.getStashStartTarget().position : splitLayout.mDividerSnapAlgorithm.mFirstSplitTarget.position;
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final boolean hasSplitImeFocus() {
            return this.mHasImeFocus;
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeControlTargetChanged(int i, boolean z) {
            if (i == this.mDisplayId && !z && this.mImeShown) {
                reset();
                SplitLayout splitLayout = SplitLayout.this;
                splitLayout.setDividerInteractive("onImeControlTargetChanged", true, true);
                SplitLayoutHandler splitLayoutHandler = splitLayout.mSplitLayoutHandler;
                StageCoordinator stageCoordinator = (StageCoordinator) splitLayoutHandler;
                stageCoordinator.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    long j = 0;
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 786816716454508474L, 5, Long.valueOf(j), Long.valueOf(j));
                }
                int i2 = stageCoordinator.mSideStagePosition;
                StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
                StageTaskListener stageTaskListener2 = stageCoordinator.mSideStage;
                StageTaskListener stageTaskListener3 = i2 == 0 ? stageTaskListener2 : stageTaskListener;
                if (i2 != 0) {
                    stageTaskListener = stageTaskListener2;
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && stageCoordinator.isMultiSplitActive()) {
                    splitLayout.applyLayoutOffsetTargetForMultiSplit(windowContainerTransaction, 0, stageCoordinator.getBottomStages());
                } else {
                    ActivityManager.RunningTaskInfo runningTaskInfo = stageTaskListener3.mRootTaskInfo;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = stageTaskListener.mRootTaskInfo;
                    windowContainerTransaction.setBounds(runningTaskInfo.token, splitLayout.getTopLeftBounds());
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
                    windowContainerTransaction.setBounds(runningTaskInfo2.token, splitLayout.getBottomRightBounds());
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo2.token, 0, 0);
                }
                stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                ((StageCoordinator) splitLayoutHandler).onLayoutPositionChanging(splitLayout);
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeEndPositioning(int i, boolean z, SurfaceControl.Transaction transaction) {
            if (i == this.mDisplayId) {
                if ((this.mHasImeFocus || this.mYOffsetForIme != 0) && !z) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -9149866949618287148L, 3, Boolean.valueOf(z));
                    }
                    onProgress(1.0f);
                    SplitLayout splitLayout = SplitLayout.this;
                    ((StageCoordinator) splitLayout.mSplitLayoutHandler).onLayoutPositionChanging(splitLayout);
                    if (this.mImeShown) {
                        return;
                    }
                    ((StageCoordinator) splitLayout.mSplitLayoutHandler).setExcludeImeInsets(false);
                }
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImePositionChanged(int i, int i2, SurfaceControl.Transaction transaction) {
            if (i == this.mDisplayId) {
                if (this.mHasImeFocus || this.mYOffsetForIme != 0) {
                    onProgress((i2 - this.mStartImeTop) / (this.mEndImeTop - r2));
                    SplitLayout splitLayout = SplitLayout.this;
                    ((StageCoordinator) splitLayout.mSplitLayoutHandler).onLayoutPositionChanging(splitLayout);
                }
            }
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImeRequested(int i, boolean z) {
            if (i != this.mDisplayId) {
                return;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4191550307873537023L, 0, String.valueOf(z));
            }
            ((StageCoordinator) SplitLayout.this.mSplitLayoutHandler).setExcludeImeInsets(true);
        }

        /* JADX WARN: Removed duplicated region for block: B:104:0x0149  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0150  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x01c2  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x01c9  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x01d4 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:95:0x017f  */
        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onImeStartPositioning(int r10, int r11, boolean r12, boolean r13, int r14) {
            /*
                Method dump skipped, instructions count: 470
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.SplitLayout.ImePositionProcessor.onImeStartPositioning(int, int, boolean, boolean, int):int");
        }

        public final void onProgress(float f) {
            float f2 = (0.0f * f) + 0.0f;
            this.mDimValue1 = f2;
            this.mDimValue2 = f2;
            float f3 = this.mLastYOffset;
            this.mYOffsetForIme = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mTargetYOffset, f3, f, f3);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SplitLayoutHandler {
    }

    public SplitLayout(String str, Context context, Configuration configuration, SplitLayoutHandler splitLayoutHandler, SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks, DisplayController displayController, DisplayImeController displayImeController, ShellTaskOrganizer shellTaskOrganizer, int i, SplitState splitState, Handler handler, DesktopState desktopState) {
        this(str, context, configuration, splitLayoutHandler, parentContainerCallbacks, displayController, displayImeController, shellTaskOrganizer, i, splitState, handler, desktopState, -1);
    }

    public static boolean isLandscape(Rect rect) {
        return rect.width() > rect.height();
    }

    public final void applyLayoutOffsetTargetForMultiSplit(WindowContainerTransaction windowContainerTransaction, int i, ArrayList arrayList) {
        if (i == 0) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) arrayList.get(size);
                windowContainerTransaction.setBounds(runningTaskInfo.token, getNotAdjustedBounds(runningTaskInfo.configuration.windowConfiguration.getStageType()));
                windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
            }
            return;
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) arrayList.get(size2);
            Rect notAdjustedBounds = getNotAdjustedBounds(runningTaskInfo2.configuration.windowConfiguration.getStageType());
            Rect rect = new Rect(notAdjustedBounds);
            rect.offset(0, i);
            windowContainerTransaction.setBounds(runningTaskInfo2.token, rect);
            if (runningTaskInfo2.configuration.windowConfiguration.getBounds().equals(notAdjustedBounds)) {
                WindowContainerToken windowContainerToken = runningTaskInfo2.token;
                Configuration configuration = runningTaskInfo2.configuration;
                windowContainerTransaction.setScreenSizeDp(windowContainerToken, configuration.screenWidthDp, configuration.screenHeightDp);
            } else {
                getDisplayLayout(this.mContext).getStableBounds(this.mTempRect, false);
                this.mTempRect.intersectUnchecked(notAdjustedBounds);
                windowContainerTransaction.setScreenSizeDp(runningTaskInfo2.token, (int) ((this.mTempRect.width() / getDisplayLayout(this.mContext).density()) + 0.5f), (int) ((this.mTempRect.height() / getDisplayLayout(this.mContext).density()) + 0.5f));
            }
        }
    }

    public final boolean applyTaskChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
        boolean z;
        if (getTopLeftBounds().equals((Rect) this.mContentBounds.getFirst()) && runningTaskInfo.token.equals(this.mWinToken1)) {
            z = false;
        } else {
            Rect topLeftBounds = getTopLeftBounds();
            windowContainerTransaction.setBounds(runningTaskInfo.token, topLeftBounds);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo.token, getSmallestWidthDp(topLeftBounds));
            ((Rect) this.mContentBounds.getFirst()).set(getTopLeftBounds());
            this.mWinToken1 = runningTaskInfo.token;
            z = true;
        }
        if (getBottomRightBounds().equals((Rect) this.mContentBounds.getLast()) && runningTaskInfo2.token.equals(this.mWinToken2)) {
            return z;
        }
        Rect bottomRightBounds = getBottomRightBounds();
        windowContainerTransaction.setBounds(runningTaskInfo2.token, bottomRightBounds);
        windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo2.token, getSmallestWidthDp(bottomRightBounds));
        ((Rect) this.mContentBounds.getLast()).set(getBottomRightBounds());
        this.mWinToken2 = runningTaskInfo2.token;
        return true;
    }

    public final int calculateCurrentSnapPosition() {
        return this.mDividerSnapAlgorithm.snap(this.mDividerPosition, true).snapPosition;
    }

    public final float calculateSplitRatioForParallelMultiSplit(MultiSplitLayoutInfo multiSplitLayoutInfo) {
        boolean z = multiSplitLayoutInfo.splitDivision == 0;
        Rect rect = new Rect();
        CellUtil.getCellAndHostArea(multiSplitLayoutInfo.cellStagePosition, getTopLeftBounds(), getBottomRightBounds(), rect, z);
        return rect.equals(getTopLeftBounds()) ? 0.66f : 0.33f;
    }

    public final void copyTopLeftRefBounds(Rect rect) {
        rect.set(getTopLeftBounds());
        Rect rect2 = this.mRootBounds;
        rect.offset(-rect2.left, -rect2.top);
    }

    public final DividerSnapAlgorithm createCellSnapAlgorithm() {
        int width;
        int height;
        CellUtil.getCellAndHostArea(this.mCellStageWindowConfigPosition, getTopLeftBounds(), getBottomRightBounds(), this.mHostAndCellArea, isVerticalDivision());
        Rect rect = new Rect(this.mHostAndCellArea);
        Rect rect2 = new Rect(getDisplayStableInsets(this.mContext));
        if (isVerticalDivision()) {
            rect.top -= rect2.top;
            rect.bottom += rect2.bottom;
        } else {
            rect.left -= rect2.left;
            rect.right += rect2.right;
        }
        if (!CoreRune.MW_PARALLEL_MULTI_SPLIT || !this.mParallelMultiSplit) {
            return new DividerSnapAlgorithm(this.mContext.getResources(), rect.width(), rect.height(), this.mDividerSize, !isVerticalDivision(), rect2, this.mPinnedTaskbarInsets.toRect(), -1, false, true, false, true);
        }
        rect.set(this.mHostAndCellArea);
        if (getTopLeftBounds().equals(this.mHostAndCellArea)) {
            if (isVerticalDivision()) {
                rect.left -= rect2.left;
                rect.top -= rect2.top;
                rect2.right = 0;
                rect.bottom += rect2.bottom;
            } else {
                rect.left -= rect2.left;
                rect.top -= rect2.top;
                rect.right += rect2.right;
                rect2.bottom = 0;
            }
            width = rect.width();
            height = rect.height();
        } else {
            if (isVerticalDivision()) {
                rect2.left = rect.left;
                rect.top -= rect2.top;
                rect.right += rect2.right;
                rect.bottom += rect2.bottom;
            } else {
                rect.left -= rect2.left;
                rect2.top = rect.top;
                rect.right += rect2.right;
                rect.bottom += rect2.bottom;
            }
            width = this.mRootBounds.width();
            height = this.mRootBounds.height();
        }
        return new DividerSnapAlgorithm(this.mContext.getResources(), width, height, this.mDividerSize, isVerticalDivision(), rect2, this.mPinnedTaskbarInsets.toRect(), -1, false, true, true, true);
    }

    public void flingDividerPosition(int i, int i2, int i3, Interpolator interpolator, Runnable runnable) {
        flingDividerPosition(i, i2, i3, interpolator, runnable, false);
    }

    public final void flingDividerToCenter(final StageCoordinator$$ExternalSyntheticLambda6 stageCoordinator$$ExternalSyntheticLambda6) {
        final DividerSnapAlgorithm.SnapTarget snapTarget = this.mDividerSnapAlgorithm.mMiddleTarget;
        final int i = snapTarget.position;
        flingDividerPosition(this.mDividerPosition, i, 450, Interpolators.FAST_OUT_SLOW_IN, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                SplitLayout splitLayout = SplitLayout.this;
                int i2 = i;
                DividerSnapAlgorithm.SnapTarget snapTarget2 = snapTarget;
                StageCoordinator$$ExternalSyntheticLambda6 stageCoordinator$$ExternalSyntheticLambda62 = stageCoordinator$$ExternalSyntheticLambda6;
                Interpolator interpolator = SplitLayout.SHRINK_INTERPOLATOR;
                splitLayout.setDividePosition(i2, null, true);
                splitLayout.mSplitState.mState = snapTarget2.snapPosition;
                stageCoordinator$$ExternalSyntheticLambda62.run();
            }
        });
    }

    public final void flingDividerToDismiss(final int i, final boolean z) {
        flingDividerPosition(this.mDividerPosition, z ? this.mDividerSnapAlgorithm.mDismissEndTarget.position : this.mDividerSnapAlgorithm.mDismissStartTarget.position, 450, Interpolators.FAST_OUT_SLOW_IN, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SplitLayout splitLayout = SplitLayout.this;
                boolean z2 = z;
                ((StageCoordinator) splitLayout.mSplitLayoutHandler).onSnappedToDismiss(i, z2, false);
            }
        });
    }

    public final void flingDividerToOtherSide(int i) {
        if (this.mDividerFlingAnimator != null) {
            return;
        }
        if (i == 3) {
            snapToTarget(this.mDividerPosition, this.mDividerSnapAlgorithm.mFirstSplitTarget, 500, Interpolators.EMPHASIZED, false);
            return;
        }
        if (i == 4) {
            snapToTarget(this.mDividerPosition, this.mDividerSnapAlgorithm.mLastSplitTarget, 500, Interpolators.EMPHASIZED, false);
        } else if (i == 14) {
            snapToTarget(this.mDividerPosition, this.mDividerSnapAlgorithm.getStashEndTarget(), 500, Interpolators.EMPHASIZED, true);
        } else {
            if (i != 15) {
                return;
            }
            snapToTarget(this.mDividerPosition, this.mDividerSnapAlgorithm.getStashStartTarget(), 500, Interpolators.EMPHASIZED, true);
        }
    }

    public final Rect getBottomRightBounds() {
        return (Rect) this.mStageBounds.getLast();
    }

    public final Rect getBottomRightRefBounds() {
        Rect bottomRightBounds = getBottomRightBounds();
        Rect rect = this.mRootBounds;
        bottomRightBounds.offset(-rect.left, -rect.top);
        return bottomRightBounds;
    }

    public final Rect getBounds3() {
        return new Rect(this.mBounds3);
    }

    public final SurfaceControl getCellDividerLeash() {
        SplitWindowManager splitWindowManager = this.mCellSplitWindowManager;
        if (splitWindowManager == null) {
            return null;
        }
        return splitWindowManager.mLeash;
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
        DisplayLayout displayLayout = getDisplayLayout(context);
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
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            systemBars &= ~WindowInsets.Type.statusBars();
        }
        return displayLayout != null ? displayLayout.stableInsets(true) : ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(systemBars).toRect();
    }

    public final int getDividePositionByRatio() {
        int i;
        int height;
        int i2;
        boolean isVerticalDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? isVerticalDivision() : isLandscape(this.mRootBounds);
        this.mTempRect.set(this.mRootBounds);
        this.mTempRect.inset(getDisplayStableInsets(this.mContext));
        if (isVerticalDivision) {
            Rect rect = this.mTempRect;
            i = rect.left;
            height = rect.width();
            i2 = this.mDividerSize;
        } else {
            Rect rect2 = this.mTempRect;
            i = rect2.top;
            height = rect2.height();
            i2 = this.mDividerSize;
        }
        return this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i + ((int) (((height - i2) * 0.5f) + 0.5f))).position;
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
        if (this.mIsLeftRightSplit) {
            f = (getTopLeftBounds().right + getBottomRightBounds().left) / 2.0f;
            i = getBottomRightBounds().right;
        } else {
            f = (getTopLeftBounds().bottom + getBottomRightBounds().top) / 2.0f;
            i = getBottomRightBounds().bottom;
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
        updateBounds(this.mDividerSnapAlgorithm.mMiddleTarget.position, rect, rect2, new Rect(), false);
    }

    public final Rect getNotAdjustedBounds(int i) {
        return i == 4 ? this.mBounds3 : isVerticalDivision() ? this.mHostBounds : (this.mCellStageWindowConfigPosition & 64) != 0 ? this.mHostBounds : getBottomRightBounds();
    }

    public final Rect getRefHostBounds() {
        Rect hostBounds = getHostBounds();
        Rect rect = this.mRootBounds;
        hostBounds.offset(-rect.left, -rect.top);
        return hostBounds;
    }

    public final Rect getRootBounds() {
        return new Rect(this.mRootBounds);
    }

    public final int getSmallestWidthDp(Rect rect) {
        Insets calculateInsets;
        this.mTempRect.set(rect);
        if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            Rect rect2 = new Rect();
            DisplayLayout displayLayout = getDisplayLayout(this.mContext);
            if (displayLayout == null) {
                this.mTempRect.inset(getDisplayStableInsets(this.mContext));
            } else {
                rect2.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                int navigationBars = WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout();
                if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
                    calculateInsets = Insets.NONE;
                } else {
                    if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
                        navigationBars &= ~WindowInsets.Type.navigationBars();
                    }
                    calculateInsets = this.mInsetsState.calculateInsets(rect2, navigationBars, false);
                }
                rect2.inset(calculateInsets);
                this.mTempRect.intersect(rect2);
            }
        }
        return (int) (Math.min(this.mTempRect.width(), this.mTempRect.height()) / this.mContext.getResources().getDisplayMetrics().density);
    }

    public final Rect getTopLeftBounds() {
        return (Rect) this.mStageBounds.getFirst();
    }

    public final Rect getTopLeftRefBounds() {
        Rect topLeftBounds = getTopLeftBounds();
        Rect rect = this.mRootBounds;
        topLeftBounds.offset(-rect.left, -rect.top);
        return topLeftBounds;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initDividerPosition(android.graphics.Rect r5, boolean r6, boolean r7) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.SplitLayout.initDividerPosition(android.graphics.Rect, boolean, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x007e  */
    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void insetsChanged(android.view.InsetsState r13) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.SplitLayout.insetsChanged(android.view.InsetsState):void");
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        if (this.mInsetsState.equals(insetsState)) {
            return;
        }
        insetsChanged(insetsState);
    }

    public final boolean isSplitScreenFeasible(boolean z) {
        DividerSnapAlgorithm dividerSnapAlgorithm = new DividerSnapAlgorithm(this.mContext.getResources(), getDisplayLayout(this.mContext).mWidth, getDisplayLayout(this.mContext).mHeight, this.mDividerSize, z, getDisplayLayout(this.mContext).stableInsets(true), this.mPinnedTaskbarInsets.toRect(), z ? 2 : 1);
        Rect rect = dividerSnapAlgorithm.mInsets;
        int i = rect.top;
        boolean z2 = dividerSnapAlgorithm.mIsHorizontalDivision;
        int i2 = z2 ? rect.bottom : rect.right;
        int i3 = z2 ? dividerSnapAlgorithm.mDisplayHeight : dividerSnapAlgorithm.mDisplayWidth;
        boolean z3 = CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM;
        if (z3 && !z2) {
            i2 = 0;
        }
        return (((i3 - i2) - i) - dividerSnapAlgorithm.mDividerSize) / 2 >= (z3 ? dividerSnapAlgorithm.getMinimalSize() : dividerSnapAlgorithm.mMinimalSizeResizableTask);
    }

    public final boolean isVerticalDivision() {
        return this.mSplitDivision == 0;
    }

    public final ValueAnimator moveSurface(final SurfaceControl.Transaction transaction, StageTaskListener stageTaskListener, Rect rect, Rect rect2, final float f, final float f2, final boolean z, final boolean z2, final boolean z3) {
        boolean z4 = stageTaskListener != null;
        SurfaceControl dividerLeash = z4 ? stageTaskListener.mRootLeash : getDividerLeash();
        final ActivityManager.RunningTaskInfo runningTaskInfo = z4 ? stageTaskListener.mRootTaskInfo : null;
        final SplitDecorManager splitDecorManager = z4 ? stageTaskListener.mSplitDecorManager : null;
        final Rect rect3 = new Rect(rect);
        Rect rect4 = new Rect(rect2);
        final float f3 = rect4.left - rect3.left;
        final float f4 = rect4.top - rect3.top;
        final float width = rect4.width() - rect3.width();
        final float height = rect4.height() - rect3.height();
        float radius = this.mSplitWindowManager.mDividerView.getDisplay().getRoundedCorner(0) != null ? r0.getRadius() : 0.0f;
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        PipUtils pipUtils = PipUtils.INSTANCE;
        final float applyDimension = ((int) TypedValue.applyDimension(1, 14.0f, displayMetrics)) * 2.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        final SurfaceControl surfaceControl = dividerLeash;
        final float f5 = radius;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SplitLayout splitLayout = SplitLayout.this;
                SurfaceControl surfaceControl2 = surfaceControl;
                boolean z5 = z;
                SurfaceControl.Transaction transaction2 = transaction;
                float f6 = f5;
                Rect rect5 = rect3;
                float f7 = f3;
                float f8 = f4;
                float f9 = width;
                float f10 = height;
                boolean z6 = z2;
                float f11 = applyDimension;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                float f12 = f;
                float f13 = f2;
                boolean z7 = z3;
                SplitDecorManager splitDecorManager2 = splitDecorManager;
                Interpolator interpolator = SplitLayout.SHRINK_INTERPOLATOR;
                splitLayout.getClass();
                if (surfaceControl2 == null) {
                    return;
                }
                if (z5) {
                    transaction2.setCornerRadius(surfaceControl2, f6);
                }
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float interpolation = ((PathInterpolator) Interpolators.EMPHASIZED).getInterpolation(floatValue);
                float f14 = (f7 * interpolation) + rect5.left;
                float f15 = (f8 * interpolation) + rect5.top;
                int width2 = (int) ((f9 * interpolation) + rect5.width());
                int height2 = (int) ((interpolation * f10) + rect5.height());
                if (z6) {
                    float f16 = height2;
                    float f17 = f11 / f16;
                    float f18 = width2;
                    float f19 = f11 / f18;
                    float interpolation2 = floatValue <= 0.166f ? ((PathInterpolator) SplitLayout.SHRINK_INTERPOLATOR).getInterpolation(floatValue / 0.166f) : 1.0f - ((PathInterpolator) SplitLayout.GROW_INTERPOLATOR).getInterpolation((floatValue - 0.166f) / 0.834f);
                    float f20 = f17 * interpolation2;
                    float f21 = 1.0f - f20;
                    float f22 = f19 * interpolation2;
                    float f23 = 1.0f - f22;
                    f14 += (f20 * f18) / 2.0f;
                    f15 += (f22 * f16) / 2.0f;
                    width2 = (int) (f18 * f21);
                    height2 = (int) (f16 * f23);
                    transaction2.setScale(surfaceControl2, f21, f23);
                }
                if (runningTaskInfo2 != null) {
                    transaction2.setLayer(surfaceControl2, z6 ? -20 : 10);
                } else {
                    transaction2.setLayer(surfaceControl2, 0);
                }
                if (f12 == 0.0f && f13 == 0.0f) {
                    transaction2.setPosition(surfaceControl2, f14, f15);
                    splitLayout.mTempRect.set((int) f14, (int) f15, (int) (f14 + width2), (int) (f15 + height2));
                    transaction2.setWindowCrop(surfaceControl2, width2, height2);
                    if (z7) {
                        splitDecorManager2.drawNextVeilFrameForSwapAnimation(runningTaskInfo2, splitLayout.mTempRect, transaction2, z6, surfaceControl2, 0.0f, 0.0f);
                    }
                } else {
                    int i = (int) (interpolation * f12);
                    int i2 = (int) (interpolation * f13);
                    float f24 = f15;
                    float f25 = i;
                    float f26 = i2;
                    transaction2.setPosition(surfaceControl2, f14 + f25, f24 + f26);
                    splitLayout.mTempRect.set(0, 0, width2, height2);
                    splitLayout.mTempRect.offsetTo(-i, -i2);
                    transaction2.setCrop(surfaceControl2, splitLayout.mTempRect);
                    if (z7) {
                        splitDecorManager2.drawNextVeilFrameForSwapAnimation(runningTaskInfo2, splitLayout.mTempRect, transaction2, z6, surfaceControl2, f25, f26);
                    }
                }
                transaction2.apply();
            }
        });
        return ofFloat;
    }

    public final void playSwapAnimation(SurfaceControl.Transaction transaction, StageTaskListener stageTaskListener, StageTaskListener stageTaskListener2, final StageCoordinator$$ExternalSyntheticLambda28 stageCoordinator$$ExternalSyntheticLambda28) {
        final Rect displayStableInsets = getDisplayStableInsets(this.mContext);
        boolean z = this.mIsLeftRightSplit;
        displayStableInsets.set(z ? displayStableInsets.left : 0, z ? 0 : displayStableInsets.top, z ? displayStableInsets.right : 0, z ? 0 : displayStableInsets.bottom);
        boolean z2 = (displayStableInsets.left == 0 && displayStableInsets.top == 0 && displayStableInsets.right == 0 && displayStableInsets.bottom == 0) ? false : true;
        final int i = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(this.mIsLeftRightSplit ? getBottomRightBounds().width() : getBottomRightBounds().height()).position;
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        updateBounds(i, rect2, rect, rect3, false);
        Rect rect4 = this.mRootBounds;
        rect.offset(-rect4.left, -rect4.top);
        Rect rect5 = this.mRootBounds;
        rect2.offset(-rect5.left, -rect5.top);
        Rect rect6 = this.mRootBounds;
        rect3.offset(-rect6.left, -rect6.top);
        ValueAnimator moveSurface = moveSurface(transaction, stageTaskListener, getTopLeftRefBounds(), rect, -displayStableInsets.left, -displayStableInsets.top, true, true, z2);
        ValueAnimator moveSurface2 = moveSurface(transaction, stageTaskListener2, getBottomRightRefBounds(), rect2, displayStableInsets.left, displayStableInsets.top, true, false, z2);
        Rect rect7 = new Rect(this.mDividerBounds);
        Rect rect8 = this.mRootBounds;
        rect7.offset(-rect8.left, -rect8.top);
        ValueAnimator moveSurface3 = moveSurface(transaction, null, rect7, rect3, 0.0f, 0.0f, false, false, false);
        AnimatorSet animatorSet = new AnimatorSet();
        this.mSwapAnimator = animatorSet;
        animatorSet.playTogether(moveSurface, moveSurface2, moveSurface3);
        this.mSwapAnimator.setDuration(500L);
        this.mSwapAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.SplitLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                SplitLayout.this.mInteractionJankMonitor.cancel(82);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SplitLayout splitLayout = SplitLayout.this;
                int i2 = i;
                splitLayout.mDividerPosition = i2;
                splitLayout.updateBounds(i2);
                stageCoordinator$$ExternalSyntheticLambda28.accept(displayStableInsets);
                SplitLayout.this.mInteractionJankMonitor.end(82);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SplitLayout splitLayout = SplitLayout.this;
                InteractionJankMonitor interactionJankMonitor = splitLayout.mInteractionJankMonitor;
                SurfaceControl dividerLeash = splitLayout.getDividerLeash();
                SplitLayout splitLayout2 = SplitLayout.this;
                interactionJankMonitor.begin(dividerLeash, splitLayout2.mContext, splitLayout2.mHandler, 82);
            }
        });
        this.mSwapAnimator.start();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x004a, code lost:
    
        if (r1 != 15) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void populateTouchZones() {
        /*
            r5 = this;
            java.util.List r0 = r5.mOffscreenTouchZones
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto Ld
            r5.removeTouchZones()
        Ld:
            com.android.wm.shell.common.split.SplitState r0 = r5.mSplitState
            int r1 = r0.mState
            int r2 = r5.calculateCurrentSnapPosition()
            if (r1 == r2) goto L38
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "SplitState is "
            r2.<init>(r3)
            int r0 = r0.mState
            r2.append(r0)
            java.lang.String r0 = ", expected "
            r2.append(r0)
            int r0 = r5.calculateCurrentSnapPosition()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            java.lang.String r2 = "SplitLayout"
            android.util.Log.wtf(r2, r0)
        L38:
            r0 = 3
            if (r1 == r0) goto L61
            r0 = 4
            if (r1 == r0) goto L4d
            r0 = 6
            if (r1 == r0) goto L61
            r0 = 7
            if (r1 == r0) goto L4d
            r0 = 14
            if (r1 == r0) goto L4d
            r0 = 15
            if (r1 == r0) goto L61
            goto L74
        L4d:
            java.util.List r0 = r5.mOffscreenTouchZones
            com.android.wm.shell.common.split.OffscreenTouchZone r2 = new com.android.wm.shell.common.split.OffscreenTouchZone
            com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0 r3 = new com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0
            r4 = 0
            r3.<init>(r5)
            r1 = 1
            r2.<init>(r1, r3)
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            r0.add(r2)
            goto L74
        L61:
            java.util.List r0 = r5.mOffscreenTouchZones
            com.android.wm.shell.common.split.OffscreenTouchZone r2 = new com.android.wm.shell.common.split.OffscreenTouchZone
            com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0 r3 = new com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0
            r4 = 1
            r3.<init>(r5)
            r1 = 0
            r2.<init>(r1, r3)
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            r0.add(r2)
        L74:
            java.util.List r0 = r5.mOffscreenTouchZones
            com.android.wm.shell.common.split.SplitWindowManager$ParentContainerCallbacks r5 = r5.mParentContainerCallbacks
            java.util.Objects.requireNonNull(r5)
            com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda2 r1 = new com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda2
            r2 = 0
            r1.<init>(r5, r2)
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            r0.forEach(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.SplitLayout.populateTouchZones():void");
    }

    public final void release(SurfaceControl.Transaction transaction) {
        if (this.mInitialized) {
            this.mInitialized = false;
            this.mSplitWindowManager.release(transaction);
            removeTouchZones();
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

    public final void removeTouchZones() {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        ((ArrayList) this.mOffscreenTouchZones).forEach(new SplitLayout$$ExternalSyntheticLambda2(transaction, 1));
        transaction.apply();
        ((ArrayList) this.mOffscreenTouchZones).clear();
    }

    public final void resetDividerPosition() {
        int i = this.mDividerSnapAlgorithm.mMiddleTarget.position;
        this.mDividerPosition = i;
        updateBounds(i);
        this.mWinToken1 = null;
        this.mWinToken2 = null;
        ((Rect) this.mContentBounds.getFirst()).setEmpty();
        ((Rect) this.mContentBounds.getLast()).setEmpty();
    }

    public final void setCellDividePosition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        this.mCellDividerPosition = i;
        updateCellAndHostBounds(i);
        if (z) {
            ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanged(this, windowContainerTransaction);
        }
    }

    public final void setCellDividerRatio(float f, int i, boolean z, boolean z2) {
        int i2;
        int height;
        boolean isVerticalDivision = isVerticalDivision();
        Rect rect = new Rect();
        CellUtil.getCellAndHostArea(i, getTopLeftBounds(), getBottomRightBounds(), rect, isVerticalDivision);
        int i3 = z ? this.mDividerSize : 0;
        if (z2) {
            f = 1.0f - f;
        }
        if (!(CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mParallelMultiSplit) ? isVerticalDivision : !isVerticalDivision) {
            i2 = rect.left;
            height = rect.width();
        } else {
            i2 = rect.top;
            height = rect.height();
        }
        int i4 = i2 + ((int) (((height - i3) * f) + 0.5f));
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
        this.mDividerPosition = i;
        updateBounds(i);
        boolean z2 = CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER;
        if (z2 && (splitWindowManager = this.mCellSplitWindowManager) != null && this.mStageCoordinator.isMultiSplitActive() && z2 && (dividerView = splitWindowManager.mDividerView) != null) {
            dividerView.mSetTouchRegion = true;
        }
        if (z) {
            ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanged(this, windowContainerTransaction);
        }
    }

    public final void setDivideRatio(float f, boolean z, boolean z2) {
        int i;
        int height;
        this.mTempRect.set(this.mRootBounds);
        if (z) {
            this.mTempRect.inset(getDisplayStableInsets(this.mContext));
        }
        int i2 = z2 ? this.mDividerSize : 0;
        if (this.mIsLeftRightSplit) {
            Rect rect = this.mTempRect;
            i = rect.left;
            height = rect.width();
        } else {
            Rect rect2 = this.mTempRect;
            i = rect2.top;
            height = rect2.height();
        }
        DividerSnapAlgorithm.SnapTarget calculateNonDismissingSnapTarget = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i + ((int) (((height - i2) * f) + 0.5f)));
        SplitState splitState = this.mSplitState;
        if (calculateNonDismissingSnapTarget != null) {
            splitState.mState = this.mDividerSnapAlgorithm.snap(calculateNonDismissingSnapTarget.position, true).snapPosition;
        } else {
            splitState.mState = 10;
        }
        setDividePosition(calculateNonDismissingSnapTarget != null ? calculateNonDismissingSnapTarget.position : this.mDividerSnapAlgorithm.mMiddleTarget.position, null, false);
    }

    public final void setDividerAtBorder(boolean z) {
        setDividePosition(z ? this.mDividerSnapAlgorithm.mDismissStartTarget.position : this.mDividerSnapAlgorithm.mDismissEndTarget.position, null, false);
    }

    public final void setDividerInteractive(String str, boolean z, boolean z2) {
        DividerView dividerView = this.mSplitWindowManager.mDividerView;
        if (dividerView == null) {
            return;
        }
        dividerView.setInteractive(str, z, z2);
    }

    public final void snapToTarget(int i, final DividerSnapAlgorithm.SnapTarget snapTarget, int i2, Interpolator interpolator, boolean z) {
        int i3 = snapTarget.snapPosition;
        if (i3 == 11) {
            final int i4 = 0;
            flingDividerPosition(i, snapTarget.position, i2, interpolator, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda6
                public final /* synthetic */ SplitLayout f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i5 = i4;
                    SplitLayout splitLayout = this.f$0;
                    switch (i5) {
                        case 0:
                            ((StageCoordinator) splitLayout.mSplitLayoutHandler).onSnappedToDismiss(4, false, false);
                            break;
                        default:
                            ((StageCoordinator) splitLayout.mSplitLayoutHandler).onSnappedToDismiss(4, true, false);
                            break;
                    }
                }
            });
        } else if (i3 != 12) {
            flingDividerPosition(i, snapTarget.position, i2, interpolator, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    SplitLayout splitLayout = SplitLayout.this;
                    DividerSnapAlgorithm.SnapTarget snapTarget2 = snapTarget;
                    Interpolator interpolator2 = SplitLayout.SHRINK_INTERPOLATOR;
                    splitLayout.getClass();
                    boolean z2 = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
                    splitLayout.setDividePosition(snapTarget2.position, null, true);
                    splitLayout.mSplitState.mState = snapTarget2.snapPosition;
                }
            }, z);
        } else {
            final int i5 = 1;
            flingDividerPosition(i, snapTarget.position, i2, interpolator, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda6
                public final /* synthetic */ SplitLayout f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i52 = i5;
                    SplitLayout splitLayout = this.f$0;
                    switch (i52) {
                        case 0:
                            ((StageCoordinator) splitLayout.mSplitLayoutHandler).onSnappedToDismiss(4, false, false);
                            break;
                        default:
                            ((StageCoordinator) splitLayout.mSplitLayoutHandler).onSnappedToDismiss(4, true, false);
                            break;
                    }
                }
            });
        }
    }

    public final void update(SurfaceControl.Transaction transaction, boolean z) {
        boolean z2 = this.mInitialized;
        ImePositionProcessor imePositionProcessor = this.mImePositionProcessor;
        DesktopState desktopState = this.mDesktopState;
        SplitWindowManager splitWindowManager = this.mSplitWindowManager;
        if (!z2) {
            if (z2) {
                return;
            }
            this.mInitialized = true;
            splitWindowManager.init(this, this.mInsetsState, false, desktopState);
            populateTouchZones();
            this.mDisplayImeController.addPositionProcessor(imePositionProcessor);
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mStageCoordinator.isMultiSplitActive()) {
            splitWindowManager.release(transaction);
            releaseCellDivider(transaction);
            imePositionProcessor.reset();
            splitWindowManager.init(this, this.mInsetsState, true, desktopState);
            if (!this.mCellInitialized) {
                this.mCellInitialized = true;
                this.mCellSplitWindowManager.init(this, this.mInsetsState, false, desktopState);
                this.mCellSnapAlgorithm = createCellSnapAlgorithm();
            }
            removeTouchZones();
            return;
        }
        splitWindowManager.release(transaction);
        if (z) {
            imePositionProcessor.reset();
        }
        splitWindowManager.init(this, this.mInsetsState, true, desktopState);
        populateTouchZones();
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            StageCoordinator stageCoordinator = this.mStageCoordinator;
            if (stageCoordinator.mIsMultiSplitRotating || stageCoordinator.mUpdateCoverDisplaySplitLayout) {
                return;
            }
        }
        ((StageCoordinator) this.mSplitLayoutHandler).onLayoutPositionChanging(this);
    }

    public final void updateBounds(int i) {
        updateBounds(i, getTopLeftBounds(), getBottomRightBounds(), this.mDividerBounds, false);
    }

    public final void updateCellAndHostBounds(int i) {
        DividerView dividerView;
        CellUtil.getCellAndHostArea(this.mCellStageWindowConfigPosition, getTopLeftBounds(), getBottomRightBounds(), this.mHostAndCellArea, isVerticalDivision());
        int cellSide = CellUtil.getCellSide(this.mCellStageWindowConfigPosition, isVerticalDivision(), this.mParallelMultiSplit);
        Rect rect = this.mBounds3;
        Rect rect2 = this.mHostBounds;
        Rect rect3 = this.mHostAndCellArea;
        int i2 = this.mDividerSize;
        int i3 = -1;
        if (cellSide == -1) {
            Slog.e("CellUtil", "calcBoundsForPosition. dockSide invalid. ");
        } else {
            DockedDividerUtils.calculateBoundsForCellWithPosition(rect, rect3, i, cellSide, i2);
            if (cellSide == 1) {
                i3 = 3;
            } else if (cellSide == 2) {
                i3 = 4;
            } else if (cellSide == 3) {
                i3 = 1;
            } else if (cellSide == 4) {
                i3 = 2;
            }
            DockedDividerUtils.calculateBoundsForCellWithPosition(rect2, rect3, i, i3, i2);
        }
        boolean z = CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER;
        if (z) {
            this.mCellDividerBounds.set(this.mBounds3);
            if (cellSide == 1) {
                Rect rect4 = this.mCellDividerBounds;
                int i4 = this.mBounds3.right;
                int i5 = this.mDividerInsets;
                rect4.left = i4 - i5;
                rect4.right = i4 + this.mDividerSize + i5;
            } else if (cellSide == 2) {
                Rect rect5 = this.mCellDividerBounds;
                Rect rect6 = this.mBounds3;
                int i6 = rect6.bottom;
                int i7 = this.mDividerInsets;
                rect5.bottom = i6 + i7 + this.mDividerSize;
                rect5.top = rect6.bottom - i7;
            } else if (cellSide == 3) {
                Rect rect7 = this.mCellDividerBounds;
                Rect rect8 = this.mBounds3;
                int i8 = rect8.left;
                int i9 = this.mDividerInsets;
                rect7.left = (i8 - i9) - this.mDividerSize;
                rect7.right = rect8.left + i9;
            } else if (cellSide == 4) {
                Rect rect9 = this.mCellDividerBounds;
                int i10 = this.mBounds3.top;
                int i11 = this.mDividerInsets;
                rect9.bottom = i10 + i11;
                rect9.top = (i10 - this.mDividerSize) - i11;
            }
            int width = this.mCellDividerBounds.width();
            int height = this.mCellDividerBounds.height();
            InsetsState insetsState = this.mInsetsState;
            SplitWindowManager splitWindowManager = this.mCellSplitWindowManager;
            splitWindowManager.getClass();
            if (!z || !splitWindowManager.mIsCellDivider || (dividerView = splitWindowManager.mDividerView) == null || splitWindowManager.mViewHost == null) {
                return;
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) dividerView.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            splitWindowManager.mViewHost.relayout(layoutParams);
            splitWindowManager.mDividerView.onInsetsChanged(insetsState, false);
        }
    }

    public final void updateCellStageWindowConfigPosition(int i) {
        if (this.mCellStageWindowConfigPosition != i) {
            this.mCellStageWindowConfigPosition = i;
            DividerSnapAlgorithm createCellSnapAlgorithm = createCellSnapAlgorithm();
            this.mCellSnapAlgorithm = createCellSnapAlgorithm;
            setCellDividePosition(createCellSnapAlgorithm.mMiddleTarget.position, null, false);
        }
    }

    public final boolean updateConfiguration(Configuration configuration) {
        SplitWindowManager splitWindowManager;
        int rotation = configuration.windowConfiguration.getRotation();
        Rect bounds = configuration.windowConfiguration.getBounds();
        int i = configuration.orientation;
        int i2 = configuration.densityDpi;
        int i3 = configuration.uiMode;
        Locale locale = configuration.getLocales().get(0);
        float f = configuration.fontScale;
        int i4 = configuration.fontWeightAdjustment;
        boolean z = this.mIsLeftRightSplit;
        if (this.mOrientation == i && this.mRotation == rotation && this.mDensity == i2 && this.mUiMode == i3 && this.mRootBounds.equals(bounds) && this.mLocale.equals(locale) && this.mFontScale == f && this.mFontWeightAdjustment == i4) {
            return false;
        }
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && !bounds.equals(this.mRootBounds) && configuration.semDisplayDeviceType != 5 && this.mSplitScreenFeasibleMode == 1) {
            boolean isSplitScreenFeasible = isSplitScreenFeasible(true);
            boolean isSplitScreenFeasible2 = isSplitScreenFeasible(false);
            if (isSplitScreenFeasible) {
                this.mPossibleSplitDivision = 1;
            } else if (isSplitScreenFeasible2) {
                this.mPossibleSplitDivision = 0;
            }
            Slog.d("SplitLayout", "split feasible changed, splitDivision=" + this.mPossibleSplitDivision);
        }
        this.mContext = this.mContext.createConfigurationContext(configuration);
        this.mSplitWindowManager.setConfiguration(configuration);
        this.mOrientation = i;
        this.mTempRect.set(this.mRootBounds);
        this.mRootBounds.set(bounds);
        this.mRotation = rotation;
        this.mDensity = i2;
        this.mUiMode = i3;
        this.mIsLargeScreen = configuration.smallestScreenWidthDp >= 600;
        this.mIsLeftRightSplit = SplitScreenUtils.isLeftRightSplit(this.mAllowLeftRightSplitInPortrait, configuration, this.mSplitDivision);
        updateLayouts();
        this.mLocale = locale;
        this.mFontScale = f;
        this.mFontWeightAdjustment = i4;
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && (splitWindowManager = this.mCellSplitWindowManager) != null) {
            splitWindowManager.setConfiguration(configuration);
        }
        boolean isVerticalDivision = isVerticalDivision();
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && configuration.semDisplayDeviceType == 5) {
            this.mSplitDivision = !isLandscape(this.mRootBounds) ? 1 : 0;
            this.mIsLeftRightSplit = isVerticalDivision();
            this.mStageCoordinator.setSplitDivision(this.mSplitDivision, false, false);
        }
        updateDividerConfig(this.mContext);
        initDividerPosition(this.mTempRect, z, isVerticalDivision);
        updateInvisibleRect();
        return true;
    }

    public final void updateDividerBounds(int i, boolean z, boolean z2) {
        updateBounds(i, getTopLeftBounds(), getBottomRightBounds(), this.mDividerBounds, z2);
        Point point = this.mSurfaceEffectPolicy.mRetreatingSideParallax;
        ((StageCoordinator) this.mSplitLayoutHandler).onLayoutSizeChanging(this, point.x, point.y, z);
    }

    public final void updateDividerConfig(Context context) {
        Resources resources = context.getResources();
        Display display = context.getDisplay();
        int dimensionPixelSize = CoreRune.MW_MULTI_SPLIT_DIVIDER ? CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? resources.getDimensionPixelSize(R.dimen.secondary_rounded_corner_radius_adjustment) : resources.getDimensionPixelSize(R.dimen.secondary_rounded_corner_radius) : resources.getDimensionPixelSize(R.dimen.indeterminate_progress_alpha_23);
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
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD) {
            this.mDividerInsets = dimensionPixelSize;
            this.mDividerSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.split_divider_bar_width_fold);
        } else {
            this.mDividerInsets = Math.max(dimensionPixelSize, max);
            this.mDividerSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.split_divider_bar_width);
        }
        this.mDividerWindowWidth = (this.mDividerInsets * 2) + this.mDividerSize;
    }

    public final void updateInvisibleRect() {
        Rect rect = this.mInvisibleBounds;
        Rect rect2 = this.mRootBounds;
        int i = rect2.left;
        int i2 = rect2.top;
        boolean z = this.mIsLeftRightSplit;
        int i3 = rect2.right;
        if (z) {
            i3 /= 2;
        }
        int i4 = rect2.bottom;
        if (!z) {
            i4 /= 2;
        }
        rect.set(i, i2, i3, i4);
        Rect rect3 = this.mInvisibleBounds;
        boolean z2 = this.mIsLeftRightSplit;
        rect3.offset(z2 ? this.mRootBounds.right : 0, z2 ? 0 : this.mRootBounds.bottom);
    }

    public final void updateLayouts() {
        this.mDividerSnapAlgorithm = new DividerSnapAlgorithm(this.mContext.getResources(), this.mRootBounds.width(), this.mRootBounds.height(), this.mDividerSize, this.mIsLeftRightSplit, getDisplayStableInsets(this.mContext), this.mPinnedTaskbarInsets.toRect(), this.mIsLeftRightSplit ? 1 : 2);
    }

    public final void updateSnapAlgorithm(int i) {
        this.mTempRect.set(this.mRootBounds);
        updateLayouts();
        initDividerPosition(this.mTempRect, this.mIsLeftRightSplit, i == 0);
    }

    public SplitLayout(String str, Context context, Configuration configuration, SplitLayoutHandler splitLayoutHandler, SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks, DisplayController displayController, DisplayImeController displayImeController, ShellTaskOrganizer shellTaskOrganizer, int i, SplitState splitState, Handler handler, DesktopState desktopState, int i2) {
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        Rect rect = new Rect();
        this.mRootBounds = rect;
        this.mDividerBounds = new Rect();
        this.mStageBounds = List.of(new Rect(), new Rect());
        this.mContentBounds = List.of(new Rect(), new Rect());
        this.mInvisibleBounds = new Rect();
        this.mOffscreenTouchZones = new ArrayList();
        this.mInsetsState = new InsetsState();
        this.mPinnedTaskbarInsets = Insets.NONE;
        int i3 = 0;
        this.mInitialized = false;
        this.mFreezeDividerWindow = false;
        this.mIsLargeScreen = false;
        this.mNavigationBarRect = new Rect();
        this.mHostAndCellArea = new Rect();
        this.mBounds3 = new Rect();
        this.mWinBounds3 = new Rect();
        this.mHostBounds = new Rect();
        this.mCellStageWindowConfigPosition = 0;
        this.mCellInitialized = false;
        this.mCellDividerBounds = new Rect();
        this.mParallelMultiSplit = false;
        this.mSplitDivision = 0;
        this.mSplitScreenFeasibleMode = 2;
        this.mPossibleSplitDivision = -1;
        this.mOffScreenMovingBounds1 = new Rect();
        this.mOffScreenMovingBounds2 = new Rect();
        this.mHandler = handler;
        this.mContext = context.createConfigurationContext(configuration);
        this.mOrientation = configuration.orientation;
        this.mRotation = configuration.windowConfiguration.getRotation();
        this.mDensity = configuration.densityDpi;
        this.mIsLargeScreen = configuration.smallestScreenWidthDp >= 600;
        this.mSplitLayoutHandler = splitLayoutHandler;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mParentContainerCallbacks = parentContainerCallbacks;
        this.mSplitWindowManager = new SplitWindowManager(str, this.mContext, configuration, parentContainerCallbacks);
        this.mLocale = configuration.getLocales().get(0);
        this.mFontScale = configuration.fontScale;
        this.mFontWeightAdjustment = configuration.fontWeightAdjustment;
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
            this.mCellSplitWindowManager = new SplitWindowManager(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "Cell"), this.mContext, configuration, parentContainerCallbacks, true);
        } else {
            this.mCellSplitWindowManager = null;
        }
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mImePositionProcessor = new ImePositionProcessor(this, this.mContext.getDisplayId(), i3);
        this.mSurfaceEffectPolicy = new ResizingEffectPolicy(i, this);
        this.mSplitState = splitState;
        this.mDesktopState = desktopState;
        Resources resources = this.mContext.getResources();
        this.mDimNonImeSide = resources.getBoolean(com.android.systemui.R.bool.config_dimNonImeAttachedSide);
        boolean z = resources.getBoolean(R.bool.config_notificationReviewPermissions);
        this.mAllowLeftRightSplitInPortrait = z;
        this.mIsLeftRightSplit = SplitScreenUtils.isLeftRightSplit(z, configuration, -1);
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            this.mSplitDivision = i2;
            this.mIsLeftRightSplit = isVerticalDivision();
        }
        updateDividerConfig(this.mContext);
        rect.set(configuration.windowConfiguration.getBounds());
        updateLayouts();
        this.mInteractionJankMonitor = InteractionJankMonitor.getInstance();
        resetDividerPosition();
        updateInvisibleRect();
    }

    public final void flingDividerPosition(int i, int i2, int i3, Interpolator interpolator, final Runnable runnable, final boolean z) {
        if (runnable != null && !z) {
            runnable.run();
            return;
        }
        if (i == i2) {
            if (runnable != null) {
                runnable.run();
            }
            this.mInteractionJankMonitor.end(52);
            return;
        }
        ValueAnimator valueAnimator = this.mDividerFlingAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofInt(i, i2).setDuration(i3);
        this.mDividerFlingAnimator = duration;
        duration.setInterpolator(interpolator);
        DividerView dividerView = this.mSplitWindowManager.mDividerView;
        final boolean z2 = dividerView != null && dividerView.mMoving;
        this.mDividerFlingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SplitLayout splitLayout = SplitLayout.this;
                boolean z3 = z2;
                boolean z4 = z;
                Interpolator interpolator2 = SplitLayout.SHRINK_INTERPOLATOR;
                splitLayout.getClass();
                splitLayout.updateDividerBounds(((Integer) valueAnimator2.getAnimatedValue()).intValue(), z3, z4);
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
                SplitLayout.this.mInteractionJankMonitor.end(52);
                SplitLayout.this.mDividerFlingAnimator = null;
            }
        });
        this.mDividerFlingAnimator.start();
    }

    public final void updateBounds(int i, Rect rect, Rect rect2, Rect rect3, boolean z) {
        int i2;
        boolean z2 = CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY;
        if (z2 || CoreRune.IS_TABLET_DEVICE) {
            this.mTempRect.set(this.mRootBounds);
            this.mTempRect.inset(getDisplayStableInsets(this.mContext));
            rect3.set(this.mTempRect);
            rect.set(this.mTempRect);
            rect2.set(this.mTempRect);
        } else {
            rect3.set(this.mRootBounds);
            rect.set(this.mRootBounds);
            rect2.set(this.mRootBounds);
        }
        SplitState splitState = this.mSplitState;
        if (splitState.isSplitStashed()) {
            this.mOffScreenMovingBounds1.set(this.mRootBounds);
            this.mOffScreenMovingBounds2.set(this.mRootBounds);
            if (z2 || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
                this.mOffScreenMovingBounds1.inset(getDisplayStableInsets(this.mContext));
                this.mOffScreenMovingBounds2.inset(getDisplayStableInsets(this.mContext));
            }
            if (this.mIsLeftRightSplit) {
                this.mOffScreenMovingBounds1.right = this.mDividerSnapAlgorithm.getStashEndTarget().position;
                this.mOffScreenMovingBounds2.left = this.mDividerSnapAlgorithm.getStashStartTarget().position + this.mDividerSize;
            } else {
                this.mOffScreenMovingBounds1.bottom = this.mDividerSnapAlgorithm.getStashEndTarget().position;
                this.mOffScreenMovingBounds2.top = this.mDividerSnapAlgorithm.getStashStartTarget().position + this.mDividerSize;
            }
        }
        if (this.mIsLeftRightSplit) {
            i2 = i + this.mRootBounds.left;
            int i3 = i2 - this.mDividerInsets;
            rect3.left = i3;
            rect3.right = i3 + this.mDividerWindowWidth;
            rect.right = i2;
            rect2.left = this.mDividerSize + i2;
            if (z) {
                rect.left = i2 - this.mOffScreenMovingBounds1.width();
                rect2.right = this.mOffScreenMovingBounds2.width() + rect2.left;
            } else {
                this.mDividerSnapAlgorithm.getClass();
                if (splitState.isSplitStashed()) {
                    if (i2 < this.mDividerSnapAlgorithm.mMiddleTarget.position) {
                        rect.left = rect.right - this.mOffScreenMovingBounds1.width();
                    } else {
                        rect2.right = this.mOffScreenMovingBounds2.width() + rect2.left;
                    }
                }
            }
        } else {
            i2 = i + this.mRootBounds.top;
            int i4 = i2 - this.mDividerInsets;
            rect3.top = i4;
            rect3.bottom = i4 + this.mDividerWindowWidth;
            rect.bottom = i2;
            rect2.top = this.mDividerSize + i2;
            if (z) {
                rect.top = i2 - this.mOffScreenMovingBounds1.height();
                rect2.bottom = this.mOffScreenMovingBounds2.height() + rect2.top;
            } else {
                this.mDividerSnapAlgorithm.getClass();
                if (splitState.isSplitStashed()) {
                    if (i2 < this.mDividerSnapAlgorithm.mMiddleTarget.position) {
                        rect.top = rect.bottom - this.mOffScreenMovingBounds1.height();
                    } else {
                        rect2.bottom = this.mOffScreenMovingBounds2.height() + rect2.top;
                    }
                }
            }
        }
        DockedDividerUtils.sanitizeStackBounds(rect, true);
        DockedDividerUtils.sanitizeStackBounds(rect2, false);
        if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
            this.mCellSnapAlgorithm = createCellSnapAlgorithm();
        }
        if (z2) {
            updateCellAndHostBounds(this.mCellDividerPosition);
        }
        boolean z3 = this.mIsLeftRightSplit;
        DividerSnapAlgorithm dividerSnapAlgorithm = this.mDividerSnapAlgorithm;
        boolean isSplitStashed = splitState.isSplitStashed();
        ResizingEffectPolicy resizingEffectPolicy = this.mSurfaceEffectPolicy;
        if (!isSplitStashed) {
            resizingEffectPolicy.mDimmingSide = -1;
            resizingEffectPolicy.mDimValue = 0.0f;
            return;
        }
        ParallaxSpec parallaxSpec = resizingEffectPolicy.mParallaxSpec;
        int dimmingSide = parallaxSpec.getDimmingSide(i2, dividerSnapAlgorithm, z3);
        resizingEffectPolicy.mDimmingSide = dimmingSide;
        if (dimmingSide != -1) {
            resizingEffectPolicy.mDimValue = parallaxSpec.getDimValue(i2, dividerSnapAlgorithm);
        }
    }

    public final boolean applyTaskChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2, ActivityManager.RunningTaskInfo runningTaskInfo3) {
        boolean z;
        Rect topLeftBounds;
        Rect rect;
        if (this.mBounds3.equals(this.mWinBounds3) && runningTaskInfo3.token.equals(this.mWinToken3)) {
            z = false;
        } else {
            windowContainerTransaction.setBounds(runningTaskInfo3.token, this.mBounds3);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo3.token, getSmallestWidthDp(this.mBounds3));
            this.mWinBounds3.set(this.mBounds3);
            this.mWinToken3 = runningTaskInfo3.token;
            z = true;
        }
        if (CellUtil.isCellInLeftOrTopBounds(this.mCellStageWindowConfigPosition, isVerticalDivision())) {
            topLeftBounds = this.mHostBounds;
            rect = getBottomRightBounds();
        } else {
            topLeftBounds = getTopLeftBounds();
            rect = this.mHostBounds;
        }
        if (!topLeftBounds.equals((Rect) this.mContentBounds.getFirst()) || !runningTaskInfo.token.equals(this.mWinToken1)) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, topLeftBounds);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo.token, getSmallestWidthDp(topLeftBounds));
            ((Rect) this.mContentBounds.getFirst()).set(topLeftBounds);
            this.mWinToken1 = runningTaskInfo.token;
            z = true;
        }
        if (rect.equals((Rect) this.mContentBounds.getLast()) && runningTaskInfo2.token.equals(this.mWinToken2)) {
            return z;
        }
        windowContainerTransaction.setBounds(runningTaskInfo2.token, rect);
        windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo2.token, getSmallestWidthDp(rect));
        ((Rect) this.mContentBounds.getLast()).set(rect);
        this.mWinToken2 = runningTaskInfo2.token;
        return true;
    }
}
