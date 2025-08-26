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
import android.util.Log;
import android.util.Slog;
import android.util.TypedValue;
import android.view.Display;
import android.view.InsetsController;
import android.view.InsetsSource;
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
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda31;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda6;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

        /* JADX WARN: Removed duplicated region for block: B:60:0x00bf  */
        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int onImeStartPositioning(int i, int i2, boolean z, boolean z2, int i3) {
            int i4;
            DividerView dividerView;
            int i5 = this.mDisplayId;
            if (i == i5) {
                SplitLayout splitLayout = SplitLayout.this;
                if (splitLayout.mInitialized) {
                    boolean z3 = CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME;
                    ShellTaskOrganizer shellTaskOrganizer = splitLayout.mTaskOrganizer;
                    SplitLayoutHandler splitLayoutHandler = splitLayout.mSplitLayoutHandler;
                    int splitItemStagePosition = z3 ? ((StageCoordinator) splitLayoutHandler).getSplitItemStagePosition(shellTaskOrganizer.getImeTarget(i5)) : ((StageCoordinator) splitLayoutHandler).getSplitItemPosition(shellTaskOrganizer.getImeTarget(i5));
                    boolean z4 = !z3 ? splitItemStagePosition == -1 : splitItemStagePosition == 0;
                    this.mHasImeFocus = z4;
                    if (z4 || !z) {
                        this.mStartImeTop = z ? i2 : i3;
                        if (z) {
                            i2 = i3;
                        }
                        this.mEndImeTop = i2;
                        this.mImeShown = z;
                        this.mLastYOffset = this.mYOffsetForIme;
                        boolean z5 = !z3 ? splitItemStagePosition != 1 || z2 || SplitLayout.isLandscape(splitLayout.mRootBounds) || !this.mImeShown : (splitItemStagePosition & 64) == 0 || z2 || !z || (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout.mParallelMultiSplit && SplitLayout.isLandscape(splitLayout.mRootBounds));
                        if (splitLayout.mRootBounds.height() / splitLayout.mRootBounds.width() >= 2.0555556f) {
                            boolean z6 = i3 - (getMinTopStackBottom() + splitLayout.mDividerSize) >= ((int) (((((float) splitLayout.mDensity) / 160.0f) * 132.0f) + 0.5f));
                            if (z5) {
                                int iAbs = Math.abs(this.mEndImeTop - this.mStartImeTop);
                                int topStageBottom = z6 ? ((CoreRune.MW_MULTI_SPLIT_FREE_POSITION && splitLayout.mStageCoordinator.isMultiSplitActive()) ? splitLayout.mStageCoordinator.getTopStageBottom() : splitLayout.getTopLeftBounds().bottom) - getMinTopStackBottom() : z3 ? splitLayout.mStageCoordinator.getTopStageBottom() - ((int) ((r1 - splitLayout.getDisplayStableInsets(splitLayout.mContext).top) * 0.7f)) : (int) Math.max(splitLayout.getTopLeftBounds().bottom - (splitLayout.getTopLeftBounds().height() * 0.3f), 0.0f);
                                i4 = -((CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY || CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) ? Math.min(iAbs - (splitLayout.getDisplayLayout(splitLayout.mContext).stableInsets(true).bottom + MultiWindowUtils.getRoundedCornerRadius(splitLayout.mContext)), topStageBottom) : Math.min(iAbs, topStageBottom));
                            } else {
                                i4 = 0;
                            }
                            this.mTargetYOffset = i4;
                            int i6 = this.mLastYOffset;
                            if (i4 != i6) {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 506703508172978242L, 5, Long.valueOf(i6), Long.valueOf(i4));
                                }
                                int i7 = this.mTargetYOffset;
                                this.mTaskBoundsAdjusted = i7 != 0;
                                ((StageCoordinator) splitLayoutHandler).setLayoutOffsetTargetForEnsureDock(i7, splitLayout);
                            } else if (this.mTaskBoundsAdjusted && i4 == 0) {
                                Slog.d("SplitLayout", "onImeStartPositioning. y offset is 0 but task adjusted. reset task bounds.");
                                this.mTaskBoundsAdjusted = false;
                                ((StageCoordinator) splitLayoutHandler).setLayoutOffsetTargetForEnsureDock(0, splitLayout);
                            }
                            splitLayout.setDividerInteractive("onImeStartPositioning", (this.mImeShown && this.mHasImeFocus && !z2) ? false : true, true);
                            if (z3 && splitLayout.mStageCoordinator.isMultiSplitActive()) {
                                boolean z7 = (this.mImeShown && this.mHasImeFocus) ? false : true;
                                DividerView dividerView2 = splitLayout.mCellSplitWindowManager.mDividerView;
                                if (dividerView2 != null) {
                                    dividerView2.setInteractive("onImeStartPositioning", z7, true);
                                }
                            }
                            if (this.mImeShown) {
                                ((StageCoordinator) splitLayoutHandler).setExcludeImeInsets(false);
                            }
                            if (this.mTargetYOffset != this.mLastYOffset) {
                                return 1;
                            }
                        }
                    } else if (!z && (dividerView = splitLayout.mSplitWindowManager.mDividerView) != null) {
                        dividerView.setInteractive("onImeStartPositioning", true, false);
                        return 0;
                    }
                }
            }
            return 0;
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
        int iWidth;
        int iHeight;
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
            iWidth = rect.width();
            iHeight = rect.height();
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
            iWidth = this.mRootBounds.width();
            iHeight = this.mRootBounds.height();
        }
        return new DividerSnapAlgorithm(this.mContext.getResources(), iWidth, iHeight, this.mDividerSize, isVerticalDivision(), rect2, this.mPinnedTaskbarInsets.toRect(), -1, false, true, true, true);
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
                SplitLayout splitLayout = this.f$0;
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
        flingDividerPosition(this.mDividerPosition, z ? this.mDividerSnapAlgorithm.mDismissEndTarget.position : this.mDividerSnapAlgorithm.mDismissStartTarget.position, 450, Interpolators.FAST_OUT_SLOW_IN, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                SplitLayout splitLayout = this.f$0;
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
        int iSystemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
        if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            return Insets.NONE.toRect();
        }
        if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
            iSystemBars &= ~WindowInsets.Type.navigationBars();
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT) {
            iSystemBars &= ~WindowInsets.Type.displayCutout();
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            iSystemBars &= ~WindowInsets.Type.statusBars();
        }
        return displayLayout != null ? displayLayout.stableInsets(true) : ((WindowManager) context.getSystemService(WindowManager.class)).getMaximumWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(iSystemBars).toRect();
    }

    public final int getDividePositionByRatio() {
        int i;
        int iHeight;
        int i2;
        boolean zIsVerticalDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? isVerticalDivision() : isLandscape(this.mRootBounds);
        this.mTempRect.set(this.mRootBounds);
        this.mTempRect.inset(getDisplayStableInsets(this.mContext));
        if (zIsVerticalDivision) {
            Rect rect = this.mTempRect;
            i = rect.left;
            iHeight = rect.width();
            i2 = this.mDividerSize;
        } else {
            Rect rect2 = this.mTempRect;
            i = rect2.top;
            iHeight = rect2.height();
            i2 = this.mDividerSize;
        }
        return this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i + ((int) (((iHeight - i2) * 0.5f) + 0.5f))).position;
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
        if (i == 4) {
            return this.mBounds3;
        }
        if (isVerticalDivision()) {
            return this.mHostBounds;
        }
        if (!CoreRune.MW_PARALLEL_MULTI_SPLIT || !this.mParallelMultiSplit) {
            return (this.mCellStageWindowConfigPosition & 64) != 0 ? this.mHostBounds : getBottomRightBounds();
        }
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        int i2 = 1;
        if (i == 1) {
            i2 = 0;
        } else if (i != 2) {
            i2 = i != 4 ? -1 : 5;
        }
        return stageCoordinator.getStageBounds(i2);
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
        Insets insetsCalculateInsets;
        this.mTempRect.set(rect);
        if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !CoreRune.MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY) {
            Rect rect2 = new Rect();
            DisplayLayout displayLayout = getDisplayLayout(this.mContext);
            if (displayLayout == null) {
                this.mTempRect.inset(getDisplayStableInsets(this.mContext));
            } else {
                rect2.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                int iNavigationBars = WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout();
                if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
                    insetsCalculateInsets = Insets.NONE;
                } else {
                    if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
                        iNavigationBars &= ~WindowInsets.Type.navigationBars();
                    }
                    insetsCalculateInsets = this.mInsetsState.calculateInsets(rect2, iNavigationBars, false);
                }
                rect2.inset(insetsCalculateInsets);
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

    /* JADX WARN: Removed duplicated region for block: B:42:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initDividerPosition(Rect rect, boolean z, boolean z2) {
        float fWidth;
        int iWidth;
        float fWidth2;
        int i;
        float f;
        int iHeight;
        this.mTempRect2.set(this.mRootBounds);
        this.mTempRect2.inset(getDisplayStableInsets(this.mContext));
        boolean zEquals = rect.equals(this.mRootBounds);
        boolean z3 = CoreRune.MW_MULTI_SPLIT_FREE_POSITION;
        if (!z3) {
            fWidth = this.mDividerPosition / (z ? rect.width() : rect.height());
            iWidth = this.mIsLeftRightSplit ? this.mRootBounds.width() : this.mRootBounds.height();
        } else {
            if (zEquals) {
                fWidth = (this.mDividerPosition - (z2 ? this.mTempRect2.left : this.mTempRect2.top)) / ((z2 ? this.mTempRect2.width() : this.mTempRect2.height()) - this.mDividerSize);
                fWidth2 = (isVerticalDivision() ? this.mTempRect2.width() : this.mTempRect2.height()) - this.mDividerSize;
                if (z3 || !zEquals) {
                    i = (int) ((fWidth2 * fWidth) + 0.5f);
                } else {
                    i = ((int) (fWidth2 * fWidth)) + (isVerticalDivision() ? this.mTempRect2.left : this.mTempRect2.top);
                }
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
                    boolean z4 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
                    if (z4 && this.mParallelMultiSplit) {
                        f = this.mCellDividerPosition;
                        iHeight = z2 ? rect.width() : rect.height();
                    } else {
                        f = this.mCellDividerPosition;
                        iHeight = z2 ? rect.height() : rect.width();
                    }
                    setCellDividePosition((int) (((z4 && this.mParallelMultiSplit) ? isVerticalDivision() ? this.mRootBounds.width() : this.mRootBounds.height() : isVerticalDivision() ? this.mRootBounds.height() : this.mRootBounds.width()) * (f / iHeight)), null, false);
                }
                int i2 = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i).position;
                this.mDividerPosition = i2;
                updateBounds(i2);
            }
            fWidth = this.mDividerPosition / (z2 ? rect.width() : rect.height());
            iWidth = isVerticalDivision() ? this.mRootBounds.width() : this.mRootBounds.height();
        }
        fWidth2 = iWidth;
        if (z3) {
            i = (int) ((fWidth2 * fWidth) + 0.5f);
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
        }
        int i22 = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i).position;
        this.mDividerPosition = i22;
        updateBounds(i22);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ed  */
    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void insetsChanged(InsetsState insetsState) {
        boolean z;
        Insets insetsCalculateVisibleInsets;
        SplitLayout splitLayout;
        SplitWindowManager splitWindowManager;
        DividerView dividerView;
        if ((!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY || !this.mStageCoordinator.isApplyFoldingPolicy(true)) && !MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && !MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED && !this.mInsetsState.equals(insetsState)) {
            Insets insetsCalculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false);
            if (this.mNavigationBarRect.equals(insetsCalculateInsets.toRect())) {
                z = false;
            } else {
                if ((this.mNavigationBarRect.isEmpty() || insetsCalculateInsets.toRect().isEmpty()) && this.mImePositionProcessor.mYOffsetForIme != 0) {
                    this.mNavigationBarRect.set(insetsCalculateInsets.toRect());
                } else {
                    this.mNavigationBarRect.set(insetsCalculateInsets.toRect());
                    if (insetsState.getDisplayFrame().equals(this.mRootBounds)) {
                        z = true;
                    }
                }
                z = false;
            }
        }
        this.mInsetsState.set(insetsState);
        boolean z2 = this.mInitialized;
        SplitLayoutHandler splitLayoutHandler = this.mSplitLayoutHandler;
        if (!z2) {
            if (z) {
                ((StageCoordinator) splitLayoutHandler).handleLayoutSizeChange(this, false);
                return;
            }
            return;
        }
        if (this.mFreezeDividerWindow) {
            return;
        }
        if (CoreRune.MW_MULTI_FOLD_SPLIT_FOLDING_POLICY && this.mStageCoordinator.isDeviceHalfClosed()) {
            Slog.d("SplitLayout", "insetsChanged: skip updateSurface by half closed");
            return;
        }
        int iSourceSize = insetsState.sourceSize() - 1;
        while (true) {
            if (iSourceSize < 0) {
                insetsCalculateVisibleInsets = Insets.NONE;
                break;
            }
            InsetsSource insetsSourceSourceAt = insetsState.sourceAt(iSourceSize);
            if (insetsSourceSourceAt.getType() == WindowInsets.Type.navigationBars() && insetsSourceSourceAt.hasFlags(2)) {
                insetsCalculateVisibleInsets = insetsSourceSourceAt.calculateVisibleInsets(this.mRootBounds);
                break;
            }
            iSourceSize--;
        }
        if (!this.mPinnedTaskbarInsets.equals(insetsCalculateVisibleInsets)) {
            this.mPinnedTaskbarInsets = insetsCalculateVisibleInsets;
            updateLayouts();
            DividerSnapAlgorithm.SnapTarget snapTargetCalculateSnapTarget = this.mDividerSnapAlgorithm.calculateSnapTarget(this.mDividerPosition, false);
            int i = snapTargetCalculateSnapTarget.position;
            int i2 = this.mDividerPosition;
            if (i != i2) {
                splitLayout = this;
                splitLayout.snapToTarget(i2, snapTargetCalculateSnapTarget, 300, InsetsController.RESIZE_INTERPOLATOR, false);
            } else {
                splitLayout = this;
            }
        }
        DividerView dividerView2 = splitLayout.mSplitWindowManager.mDividerView;
        if (dividerView2 != null) {
            dividerView2.onInsetsChanged(insetsState, true);
        }
        if (z) {
            ((StageCoordinator) splitLayoutHandler).handleLayoutSizeChange(splitLayout, false);
        }
        if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || (splitWindowManager = splitLayout.mCellSplitWindowManager) == null || (dividerView = splitWindowManager.mDividerView) == null) {
            return;
        }
        dividerView.onInsetsChanged(insetsState, true);
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
        final float fWidth = rect4.width() - rect3.width();
        final float fHeight = rect4.height() - rect3.height();
        float radius = this.mSplitWindowManager.mDividerView.getDisplay().getRoundedCorner(0) != null ? r0.getRadius() : 0.0f;
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        PipUtils pipUtils = PipUtils.INSTANCE;
        final float fApplyDimension = ((int) TypedValue.applyDimension(1, 14.0f, displayMetrics)) * 2.0f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
        final SurfaceControl surfaceControl = dividerLeash;
        final float f5 = radius;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SplitLayout splitLayout = this.f$0;
                SurfaceControl surfaceControl2 = surfaceControl;
                boolean z5 = z;
                SurfaceControl.Transaction transaction2 = transaction;
                float f6 = f5;
                Rect rect5 = rect3;
                float f7 = f3;
                float f8 = f4;
                float f9 = fWidth;
                float f10 = fHeight;
                boolean z6 = z2;
                float f11 = fApplyDimension;
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
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float interpolation = ((PathInterpolator) Interpolators.EMPHASIZED).getInterpolation(fFloatValue);
                float f14 = (f7 * interpolation) + rect5.left;
                float f15 = (f8 * interpolation) + rect5.top;
                int iWidth = (int) ((f9 * interpolation) + rect5.width());
                int iHeight = (int) ((interpolation * f10) + rect5.height());
                if (z6) {
                    float f16 = iHeight;
                    float f17 = f11 / f16;
                    float f18 = iWidth;
                    float f19 = f11 / f18;
                    float interpolation2 = fFloatValue <= 0.166f ? ((PathInterpolator) SplitLayout.SHRINK_INTERPOLATOR).getInterpolation(fFloatValue / 0.166f) : 1.0f - ((PathInterpolator) SplitLayout.GROW_INTERPOLATOR).getInterpolation((fFloatValue - 0.166f) / 0.834f);
                    float f20 = f17 * interpolation2;
                    float f21 = 1.0f - f20;
                    float f22 = f19 * interpolation2;
                    float f23 = 1.0f - f22;
                    f14 += (f20 * f18) / 2.0f;
                    f15 += (f22 * f16) / 2.0f;
                    iWidth = (int) (f18 * f21);
                    iHeight = (int) (f16 * f23);
                    transaction2.setScale(surfaceControl2, f21, f23);
                }
                if (runningTaskInfo2 != null) {
                    transaction2.setLayer(surfaceControl2, z6 ? -20 : 10);
                } else {
                    transaction2.setLayer(surfaceControl2, 0);
                }
                if (f12 == 0.0f && f13 == 0.0f) {
                    transaction2.setPosition(surfaceControl2, f14, f15);
                    splitLayout.mTempRect.set((int) f14, (int) f15, (int) (f14 + iWidth), (int) (f15 + iHeight));
                    transaction2.setWindowCrop(surfaceControl2, iWidth, iHeight);
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
                    splitLayout.mTempRect.set(0, 0, iWidth, iHeight);
                    splitLayout.mTempRect.offsetTo(-i, -i2);
                    transaction2.setCrop(surfaceControl2, splitLayout.mTempRect);
                    if (z7) {
                        splitDecorManager2.drawNextVeilFrameForSwapAnimation(runningTaskInfo2, splitLayout.mTempRect, transaction2, z6, surfaceControl2, f25, f26);
                    }
                }
                transaction2.apply();
            }
        });
        return valueAnimatorOfFloat;
    }

    public final void playSwapAnimation(SurfaceControl.Transaction transaction, StageTaskListener stageTaskListener, StageTaskListener stageTaskListener2, final StageCoordinator$$ExternalSyntheticLambda31 stageCoordinator$$ExternalSyntheticLambda31) {
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
        ValueAnimator valueAnimatorMoveSurface = moveSurface(transaction, stageTaskListener, getTopLeftRefBounds(), rect, -displayStableInsets.left, -displayStableInsets.top, true, true, z2);
        ValueAnimator valueAnimatorMoveSurface2 = moveSurface(transaction, stageTaskListener2, getBottomRightRefBounds(), rect2, displayStableInsets.left, displayStableInsets.top, true, false, z2);
        Rect rect7 = new Rect(this.mDividerBounds);
        Rect rect8 = this.mRootBounds;
        rect7.offset(-rect8.left, -rect8.top);
        ValueAnimator valueAnimatorMoveSurface3 = moveSurface(transaction, null, rect7, rect3, 0.0f, 0.0f, false, false, false);
        AnimatorSet animatorSet = new AnimatorSet();
        this.mSwapAnimator = animatorSet;
        animatorSet.playTogether(valueAnimatorMoveSurface, valueAnimatorMoveSurface2, valueAnimatorMoveSurface3);
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
                stageCoordinator$$ExternalSyntheticLambda31.accept(displayStableInsets);
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void populateTouchZones() {
        if (!((ArrayList) this.mOffscreenTouchZones).isEmpty()) {
            removeTouchZones();
        }
        SplitState splitState = this.mSplitState;
        final int i = splitState.mState;
        if (i != calculateCurrentSnapPosition()) {
            Log.wtf("SplitLayout", "SplitState is " + splitState.mState + ", expected " + calculateCurrentSnapPosition());
        }
        if (i == 3) {
            final int i2 = 1;
            ((ArrayList) this.mOffscreenTouchZones).add(new OffscreenTouchZone(false, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0
                public final /* synthetic */ SplitLayout f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            SplitLayout splitLayout = this.f$0;
                            int i3 = i;
                            Interpolator interpolator = SplitLayout.SHRINK_INTERPOLATOR;
                            splitLayout.flingDividerToOtherSide(i3);
                            break;
                        default:
                            SplitLayout splitLayout2 = this.f$0;
                            int i4 = i;
                            Interpolator interpolator2 = SplitLayout.SHRINK_INTERPOLATOR;
                            splitLayout2.flingDividerToOtherSide(i4);
                            break;
                    }
                }
            }));
        } else if (i == 4) {
            final int i3 = 0;
            ((ArrayList) this.mOffscreenTouchZones).add(new OffscreenTouchZone(true, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda0
                public final /* synthetic */ SplitLayout f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            SplitLayout splitLayout = this.f$0;
                            int i32 = i;
                            Interpolator interpolator = SplitLayout.SHRINK_INTERPOLATOR;
                            splitLayout.flingDividerToOtherSide(i32);
                            break;
                        default:
                            SplitLayout splitLayout2 = this.f$0;
                            int i4 = i;
                            Interpolator interpolator2 = SplitLayout.SHRINK_INTERPOLATOR;
                            splitLayout2.flingDividerToOtherSide(i4);
                            break;
                    }
                }
            }));
        } else if (i != 6) {
            if (i != 7 && i != 14) {
                if (i == 15) {
                }
            }
        }
        List list = this.mOffscreenTouchZones;
        SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks = this.mParentContainerCallbacks;
        Objects.requireNonNull(parentContainerCallbacks);
        ((ArrayList) list).forEach(new SplitLayout$$ExternalSyntheticLambda2(parentContainerCallbacks, 0));
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
        int iHeight;
        boolean zIsVerticalDivision = isVerticalDivision();
        Rect rect = new Rect();
        CellUtil.getCellAndHostArea(i, getTopLeftBounds(), getBottomRightBounds(), rect, zIsVerticalDivision);
        int i3 = z ? this.mDividerSize : 0;
        if (z2) {
            f = 1.0f - f;
        }
        if (!(CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mParallelMultiSplit) ? zIsVerticalDivision : !zIsVerticalDivision) {
            i2 = rect.left;
            iHeight = rect.width();
        } else {
            i2 = rect.top;
            iHeight = rect.height();
        }
        int i4 = i2 + ((int) (((iHeight - i3) * f) + 0.5f));
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
        int iHeight;
        this.mTempRect.set(this.mRootBounds);
        if (z) {
            this.mTempRect.inset(getDisplayStableInsets(this.mContext));
        }
        int i2 = z2 ? this.mDividerSize : 0;
        if (this.mIsLeftRightSplit) {
            Rect rect = this.mTempRect;
            i = rect.left;
            iHeight = rect.width();
        } else {
            Rect rect2 = this.mTempRect;
            i = rect2.top;
            iHeight = rect2.height();
        }
        DividerSnapAlgorithm.SnapTarget snapTargetCalculateNonDismissingSnapTarget = this.mDividerSnapAlgorithm.calculateNonDismissingSnapTarget(i + ((int) (((iHeight - i2) * f) + 0.5f)));
        SplitState splitState = this.mSplitState;
        if (snapTargetCalculateNonDismissingSnapTarget != null) {
            splitState.mState = this.mDividerSnapAlgorithm.snap(snapTargetCalculateNonDismissingSnapTarget.position, true).snapPosition;
        } else {
            splitState.mState = 10;
        }
        setDividePosition(snapTargetCalculateNonDismissingSnapTarget != null ? snapTargetCalculateNonDismissingSnapTarget.position : this.mDividerSnapAlgorithm.mMiddleTarget.position, null, false);
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
            flingDividerPosition(i, snapTarget.position, i2, interpolator, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda5
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
            flingDividerPosition(i, snapTarget.position, i2, interpolator, new Runnable() { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    SplitLayout splitLayout = this.f$0;
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
            flingDividerPosition(i, snapTarget.position, i2, interpolator, new Runnable(this) { // from class: com.android.wm.shell.common.split.SplitLayout$$ExternalSyntheticLambda5
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
            int iWidth = this.mCellDividerBounds.width();
            int iHeight = this.mCellDividerBounds.height();
            InsetsState insetsState = this.mInsetsState;
            SplitWindowManager splitWindowManager = this.mCellSplitWindowManager;
            splitWindowManager.getClass();
            if (!z || !splitWindowManager.mIsCellDivider || (dividerView = splitWindowManager.mDividerView) == null || splitWindowManager.mViewHost == null) {
                return;
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) dividerView.getLayoutParams();
            layoutParams.width = iWidth;
            layoutParams.height = iHeight;
            splitWindowManager.mViewHost.relayout(layoutParams);
            splitWindowManager.mDividerView.onInsetsChanged(insetsState, false);
        }
    }

    public final void updateCellStageWindowConfigPosition(int i) {
        if (this.mCellStageWindowConfigPosition != i) {
            this.mCellStageWindowConfigPosition = i;
            DividerSnapAlgorithm dividerSnapAlgorithmCreateCellSnapAlgorithm = createCellSnapAlgorithm();
            this.mCellSnapAlgorithm = dividerSnapAlgorithmCreateCellSnapAlgorithm;
            setCellDividePosition(dividerSnapAlgorithmCreateCellSnapAlgorithm.mMiddleTarget.position, null, false);
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
            boolean zIsSplitScreenFeasible = isSplitScreenFeasible(true);
            boolean zIsSplitScreenFeasible2 = isSplitScreenFeasible(false);
            if (zIsSplitScreenFeasible) {
                this.mPossibleSplitDivision = 1;
            } else if (zIsSplitScreenFeasible2) {
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
        boolean zIsVerticalDivision = isVerticalDivision();
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && configuration.semDisplayDeviceType == 5 && (!CoreRune.MW_MULTI_FOLD_SPLIT_FOLDING_POLICY || !this.mStageCoordinator.isDeviceHalfClosed())) {
            this.mSplitDivision = !isLandscape(this.mRootBounds) ? 1 : 0;
            this.mIsLeftRightSplit = isVerticalDivision();
            this.mStageCoordinator.setSplitDivision(this.mSplitDivision, false, false);
        }
        updateDividerConfig(this.mContext);
        initDividerPosition(this.mTempRect, z, zIsVerticalDivision);
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
        int dimensionPixelSize = CoreRune.MW_MULTI_SPLIT_DIVIDER ? CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? resources.getDimensionPixelSize(R.dimen.secondary_rounded_corner_radius_bottom) : resources.getDimensionPixelSize(R.dimen.secondary_rounded_corner_radius_adjustment) : resources.getDimensionPixelSize(R.dimen.indeterminate_progress_alpha_24);
        RoundedCorner roundedCorner = display.getRoundedCorner(0);
        int iMax = roundedCorner != null ? Math.max(0, roundedCorner.getRadius()) : 0;
        RoundedCorner roundedCorner2 = display.getRoundedCorner(1);
        if (roundedCorner2 != null) {
            iMax = Math.max(iMax, roundedCorner2.getRadius());
        }
        RoundedCorner roundedCorner3 = display.getRoundedCorner(2);
        if (roundedCorner3 != null) {
            iMax = Math.max(iMax, roundedCorner3.getRadius());
        }
        RoundedCorner roundedCorner4 = display.getRoundedCorner(3);
        if (roundedCorner4 != null) {
            iMax = Math.max(iMax, roundedCorner4.getRadius());
        }
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD) {
            this.mDividerInsets = dimensionPixelSize;
            this.mDividerSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.split_divider_bar_width_fold);
        } else {
            this.mDividerInsets = Math.max(dimensionPixelSize, iMax);
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

    public final void updateSplitScreenFeasibleMode(int i) {
        if (this.mSplitScreenFeasibleMode == 2 && i == 1 && getDisplayLayout(this.mContext) != null) {
            Rect rect = new Rect();
            getDisplayLayout(this.mContext).getStableBounds(rect, true);
            if (this.mRootBounds.width() != rect.width() && this.mRootBounds.height() != rect.height()) {
                boolean zIsSplitScreenFeasible = isSplitScreenFeasible(true);
                boolean zIsSplitScreenFeasible2 = isSplitScreenFeasible(false);
                if (zIsSplitScreenFeasible) {
                    this.mPossibleSplitDivision = 1;
                } else if (zIsSplitScreenFeasible2) {
                    this.mPossibleSplitDivision = 0;
                }
                Slog.d("SplitLayout", "possibleSplitDivision=" + this.mPossibleSplitDivision);
            }
        }
        this.mSplitScreenFeasibleMode = i;
    }

    public SplitLayout(String str, Context context, Configuration configuration, SplitLayoutHandler splitLayoutHandler, SplitWindowManager.ParentContainerCallbacks parentContainerCallbacks, DisplayController displayController, DisplayImeController displayImeController, ShellTaskOrganizer shellTaskOrganizer, int i, SplitState splitState, Handler handler, DesktopState desktopState, int i2) throws Resources.NotFoundException {
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
                SplitLayout splitLayout = this.f$0;
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
        boolean zIsSplitStashed = splitState.isSplitStashed();
        ResizingEffectPolicy resizingEffectPolicy = this.mSurfaceEffectPolicy;
        if (!zIsSplitStashed) {
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
        Rect bottomRightBounds;
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
            bottomRightBounds = getBottomRightBounds();
        } else {
            topLeftBounds = getTopLeftBounds();
            bottomRightBounds = this.mHostBounds;
        }
        if (!topLeftBounds.equals((Rect) this.mContentBounds.getFirst()) || !runningTaskInfo.token.equals(this.mWinToken1)) {
            windowContainerTransaction.setBounds(runningTaskInfo.token, topLeftBounds);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo.token, getSmallestWidthDp(topLeftBounds));
            ((Rect) this.mContentBounds.getFirst()).set(topLeftBounds);
            this.mWinToken1 = runningTaskInfo.token;
            z = true;
        }
        if (bottomRightBounds.equals((Rect) this.mContentBounds.getLast()) && runningTaskInfo2.token.equals(this.mWinToken2)) {
            return z;
        }
        windowContainerTransaction.setBounds(runningTaskInfo2.token, bottomRightBounds);
        windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo2.token, getSmallestWidthDp(bottomRightBounds));
        ((Rect) this.mContentBounds.getLast()).set(bottomRightBounds);
        this.mWinToken2 = runningTaskInfo2.token;
        return true;
    }
}
