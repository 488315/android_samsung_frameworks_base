package com.android.wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.window.WindowContainerTransaction;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.split.DividerResizeController;
import com.android.wm.shell.common.split.DividerResizeLayout;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DividerResizeController {
    public static boolean USE_GUIDE_VIEW_EFFECTS = false;
    public final Context mContext;
    public int mCurrentDividerPosition;
    public int mDefaultHandleMoveThreshold;
    public DividerResizeLayout mDividerResizeLayout;
    public int mDividerSize;
    public DividerView mDividerView;
    public boolean mIsHorizontalDivision;
    public boolean mIsMultiSplitActive;
    public final LayoutInflater mLayoutInflater;
    public final ShellExecutor mMainExecutor;
    public SplitLayout mSplitLayout;
    public StageCoordinator mStageCoordinator;
    public boolean mUseGuideViewByMultiStar = false;
    public final ResizeAlgorithm mResizeAlgorithm = new ResizeAlgorithm();
    public boolean mResizingRequested = false;
    public boolean mIsResizing = false;
    public boolean mIsFinishing = false;
    public int mSyncAppsId = -1;
    public boolean mWaitingForSyncAppsCallback = false;
    public final DividerResizeController$$ExternalSyntheticLambda0 mSyncAppsCallbackTimeoutRunnable = new DividerResizeController$$ExternalSyntheticLambda0(this, 0);
    public int mHalfSplitStageType = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResizeAlgorithm {
        public int mDismissEndTargetPosition;
        public int mDismissEndThreshold;
        public int mDismissStartThreshold;
        public int mDisplaySize;
        public DividerSnapAlgorithm mDividerSnapAlgorithm;
        public int mFirstFadeOutPosition;
        public int mFirstSplitTargetPosition;
        public int mLastFadeOutPosition;
        public int mLastSplitTargetPosition;
        public int mMiddleTargetPosition;
        public int mSplitDismissSide = 0;
        public int mSplitStashEndPosition;
        public int mSplitStashEndThreshold;
        public int mSplitStashStartPosition;
        public int mSplitStashStartThreshold;
        public int mTouchPosition;

        /* renamed from: -$$Nest$mupdate, reason: not valid java name */
        public static void m3220$$Nest$mupdate(ResizeAlgorithm resizeAlgorithm, int i) {
            if (resizeAlgorithm.mTouchPosition != i) {
                resizeAlgorithm.mTouchPosition = i;
                int i2 = i < resizeAlgorithm.mDismissStartThreshold ? 1 : i > resizeAlgorithm.mDismissEndThreshold ? 2 : 0;
                if (resizeAlgorithm.mSplitDismissSide != i2) {
                    resizeAlgorithm.mSplitDismissSide = i2;
                }
            }
        }

        public ResizeAlgorithm() {
        }

        public final int getSnapTargetPosition() {
            int i = this.mTouchPosition;
            int i2 = this.mFirstSplitTargetPosition;
            if (i >= i2 && i <= this.mLastSplitTargetPosition) {
                return i;
            }
            boolean z = i < this.mDismissStartThreshold;
            DividerResizeController dividerResizeController = DividerResizeController.this;
            if (z) {
                return (CoreRune.MW_PARALLEL_MULTI_SPLIT && dividerResizeController.mSplitLayout.mParallelMultiSplit) ? this.mDividerSnapAlgorithm.mDismissStartTarget.position : Math.min(-dividerResizeController.mDividerSize, this.mDividerSnapAlgorithm.mDismissStartTarget.position);
            }
            if (i > this.mDismissEndThreshold) {
                return (CoreRune.MW_PARALLEL_MULTI_SPLIT && dividerResizeController.mSplitLayout.mParallelMultiSplit) ? this.mDividerSnapAlgorithm.mDismissEndTarget.position : Math.max(this.mDisplaySize, this.mDividerSnapAlgorithm.mDismissEndTarget.position);
            }
            if (i < i2) {
                return i2;
            }
            int i3 = this.mLastSplitTargetPosition;
            return i > i3 ? i3 : this.mDividerSnapAlgorithm.calculateSnapTarget(i, false).position;
        }

        public final boolean isInStartStashZone() {
            int i = this.mSplitStashStartThreshold;
            return i > 0 && this.mTouchPosition < i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ResizeAlgorithm{ds=");
            sb.append(this.mDismissStartThreshold);
            sb.append(", ff=");
            sb.append(this.mFirstFadeOutPosition);
            sb.append(", f=");
            sb.append(this.mFirstSplitTargetPosition);
            sb.append(", m=");
            sb.append(this.mMiddleTargetPosition);
            sb.append(", l=");
            sb.append(this.mLastSplitTargetPosition);
            sb.append(", lf=");
            sb.append(this.mLastFadeOutPosition);
            sb.append(", de=");
            sb.append(this.mDismissEndThreshold);
            sb.append(", touch=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mTouchPosition, "}", sb);
        }
    }

    public DividerResizeController(Context context, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public final void clear() {
        this.mResizingRequested = false;
        this.mWaitingForSyncAppsCallback = false;
        this.mIsResizing = false;
        this.mIsFinishing = false;
        this.mSplitLayout = null;
        this.mDividerView = null;
        this.mIsHorizontalDivision = false;
        DividerResizeLayout dividerResizeLayout = this.mDividerResizeLayout;
        dividerResizeLayout.getClass();
        Log.d("DividerResizeLayout", "remove");
        if (dividerResizeLayout.mHandler.hasCallbacks(dividerResizeLayout.mHeavyWorkRunnable)) {
            dividerResizeLayout.mHandler.removeCallbacks(dividerResizeLayout.mHeavyWorkRunnable);
        }
        dividerResizeLayout.mFinishRunnable = null;
        dividerResizeLayout.mHandler = null;
        dividerResizeLayout.mDividerView = null;
        dividerResizeLayout.mAttachedToWindow = false;
        dividerResizeLayout.mFirstLayoutCalled = false;
        if (dividerResizeLayout.mWindowAdded) {
            dividerResizeLayout.mWindowAdded = false;
            dividerResizeLayout.mWindowManager.removeViewImmediate(dividerResizeLayout);
        } else {
            Log.w("DividerResizeLayout", "removeWindow: failed, window isn't added, Callers=" + Debug.getCallers(5));
        }
        this.mDividerResizeLayout = null;
        ResizeAlgorithm resizeAlgorithm = this.mResizeAlgorithm;
        resizeAlgorithm.mSplitDismissSide = 0;
        resizeAlgorithm.mDividerSnapAlgorithm = null;
        if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING) {
            this.mHalfSplitStageType = -1;
            this.mIsMultiSplitActive = false;
        }
    }

    public final void finishResizing(int i) {
        final DividerSnapAlgorithm.SnapTarget calculateSnapTarget;
        boolean z;
        if (!this.mResizingRequested) {
            Log.w("DividerResizeController", "finishResizing: failed, NOT resizing state!");
            return;
        }
        if (this.mIsFinishing) {
            Log.w("DividerResizeController", "finishResizing: failed, already finishing state!");
            return;
        }
        boolean z2 = true;
        this.mIsFinishing = true;
        if (!this.mIsResizing) {
            i = this.mCurrentDividerPosition;
        }
        ResizeAlgorithm resizeAlgorithm = this.mResizeAlgorithm;
        ResizeAlgorithm.m3220$$Nest$mupdate(resizeAlgorithm, i);
        int i2 = resizeAlgorithm.mTouchPosition;
        if (i2 < resizeAlgorithm.mDismissStartThreshold) {
            calculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.mDismissStartTarget;
        } else if (i2 > resizeAlgorithm.mDismissEndThreshold) {
            calculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.mDismissEndTarget;
        } else if (resizeAlgorithm.isInStartStashZone()) {
            calculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.getStashStartTarget();
        } else {
            int i3 = resizeAlgorithm.mSplitStashEndThreshold;
            if (i3 <= 0 || resizeAlgorithm.mTouchPosition <= i3) {
                int i4 = resizeAlgorithm.mTouchPosition;
                calculateSnapTarget = i4 < resizeAlgorithm.mFirstSplitTargetPosition ? resizeAlgorithm.mDividerSnapAlgorithm.mFirstSplitTarget : i4 > resizeAlgorithm.mLastSplitTargetPosition ? resizeAlgorithm.mDividerSnapAlgorithm.mLastSplitTarget : resizeAlgorithm.mDividerSnapAlgorithm.calculateSnapTarget(i4, false);
            } else {
                calculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.getStashEndTarget();
            }
        }
        int i5 = resizeAlgorithm.mSplitDismissSide;
        boolean z3 = i5 != 0;
        int i6 = calculateSnapTarget.position;
        boolean z4 = i6 != this.mCurrentDividerPosition;
        if (this.mIsResizing) {
            DividerResizeLayout dividerResizeLayout = this.mDividerResizeLayout;
            dividerResizeLayout.setAlpha(1.0f);
            DividerResizeLayout$$ExternalSyntheticLambda2 dividerResizeLayout$$ExternalSyntheticLambda2 = new DividerResizeLayout$$ExternalSyntheticLambda2(dividerResizeLayout, i6, i5);
            if (dividerResizeLayout.isReadyToShow()) {
                dividerResizeLayout$$ExternalSyntheticLambda2.run();
            } else {
                dividerResizeLayout.mActionDropRunnable = dividerResizeLayout$$ExternalSyntheticLambda2;
                Log.d("DividerResizeLayout", "onActionDrop: defer action drop, isn't ready to show yet");
            }
            if (!z4) {
                z3 = true;
            } else if (this.mWaitingForSyncAppsCallback) {
                Log.w("DividerResizeController", "startWaitingForSyncAppsCallback: failed, already waiting!");
            } else {
                this.mSyncAppsId++;
                this.mWaitingForSyncAppsCallback = true;
                HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
                DividerResizeController$$ExternalSyntheticLambda0 dividerResizeController$$ExternalSyntheticLambda0 = this.mSyncAppsCallbackTimeoutRunnable;
                handlerExecutor.removeCallbacks(dividerResizeController$$ExternalSyntheticLambda0);
                handlerExecutor.executeDelayed(dividerResizeController$$ExternalSyntheticLambda0, 3000L);
                RecyclerView$$ExternalSyntheticOutline0.m(this.mSyncAppsId, "DividerResizeController", new StringBuilder("startWaitingForSyncAppsCallback: reason=resize_split, SyncId="));
            }
            z = this.mDividerResizeLayout.shouldDeferRemove(z3);
        } else {
            z = false;
        }
        StringBuilder sb = new StringBuilder("finishResizing: snapTargetPosition=");
        sb.append(calculateSnapTarget.position);
        sb.append(", positionChanged=");
        sb.append(z4);
        sb.append(", isInDismissZone=");
        int i7 = resizeAlgorithm.mTouchPosition;
        if (i7 >= resizeAlgorithm.mDismissStartThreshold && i7 <= resizeAlgorithm.mDismissEndThreshold) {
            z2 = false;
        }
        sb.append(z2);
        sb.append(", deferStopDragging=");
        sb.append(z);
        Log.d("DividerResizeController", sb.toString());
        Runnable runnable = new Runnable() { // from class: com.android.wm.shell.common.split.DividerResizeController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityManager.RunningTaskInfo runningTaskInfo;
                DividerView dividerView;
                DividerResizeController dividerResizeController = DividerResizeController.this;
                DividerSnapAlgorithm.SnapTarget snapTarget = calculateSnapTarget;
                dividerResizeController.getClass();
                boolean z5 = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
                boolean z6 = z5 && (dividerView = dividerResizeController.mDividerView) != null && dividerView.mIsCellDivider;
                int i8 = snapTarget.snapPosition;
                if (i8 == 11) {
                    dividerResizeController.mStageCoordinator.onSnappedToDismiss(4, false, DividerResizeController.USE_GUIDE_VIEW_EFFECTS);
                } else if (i8 != 12) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                        StageCoordinator stageCoordinator = dividerResizeController.mStageCoordinator;
                        StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
                        if (stageTaskListener.mIsActive) {
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = stageTaskListener.mRootTaskInfo;
                            if (runningTaskInfo2 != null) {
                                windowContainerTransaction.setChangeTransitMode(runningTaskInfo2.token, 1, "GuideViewEffects");
                            }
                            ActivityManager.RunningTaskInfo runningTaskInfo3 = stageCoordinator.mSideStage.mRootTaskInfo;
                            if (runningTaskInfo3 != null) {
                                windowContainerTransaction.setChangeTransitMode(runningTaskInfo3.token, 1, "GuideViewEffects");
                            }
                        }
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                            StageTaskListener stageTaskListener2 = stageCoordinator.mCellStage;
                            if (stageTaskListener2.mIsActive && (runningTaskInfo = stageTaskListener2.mRootTaskInfo) != null) {
                                windowContainerTransaction.setChangeTransitMode(runningTaskInfo.token, 1, "GuideViewEffects");
                            }
                        }
                    }
                    DividerResizeController.ResizeAlgorithm resizeAlgorithm2 = dividerResizeController.mResizeAlgorithm;
                    if (resizeAlgorithm2.isInStartStashZone()) {
                        dividerResizeController.mSplitLayout.mSplitState.mState = 14;
                    } else {
                        int i9 = resizeAlgorithm2.mSplitStashEndThreshold;
                        if (i9 <= 0 || resizeAlgorithm2.mTouchPosition <= i9) {
                            dividerResizeController.mSplitLayout.mSplitState.mState = 10;
                        } else {
                            dividerResizeController.mSplitLayout.mSplitState.mState = 15;
                        }
                    }
                    if (CoreRune.MW_SA_LOGGING && dividerResizeController.mSplitLayout.mSplitState.isSplitStashed()) {
                        boolean z7 = dividerResizeController.mContext.getResources().getConfiguration().orientation == 1;
                        boolean z8 = dividerResizeController.mSplitLayout.mIsLeftRightSplit;
                        CoreSaLogger.logForAdvanced("1006", z7 ? z8 ? "From Portrait_SbS" : "From Portrait_T&B" : z8 ? "From Landscape_T&B" : "From Landscape_SbS");
                    }
                    int i10 = snapTarget.position;
                    if (z5 && z6) {
                        dividerResizeController.mSplitLayout.setCellDividePosition(i10, windowContainerTransaction, true);
                    } else {
                        dividerResizeController.mSplitLayout.setDividePosition(i10, windowContainerTransaction, true);
                    }
                } else {
                    dividerResizeController.mStageCoordinator.onSnappedToDismiss(4, true, DividerResizeController.USE_GUIDE_VIEW_EFFECTS);
                }
                dividerResizeController.mStageCoordinator.onLayoutSizeChanging(dividerResizeController.mSplitLayout, 0, 0, false);
                if (dividerResizeController.mWaitingForSyncAppsCallback) {
                    Log.d("DividerResizeController", "onStopDraggingFinished: WaitingForSyncAppsCallback");
                } else {
                    dividerResizeController.clear();
                }
            }
        };
        if (!z) {
            runnable.run();
            return;
        }
        DividerResizeLayout dividerResizeLayout2 = this.mDividerResizeLayout;
        dividerResizeLayout2.mFinishRunnable = runnable;
        dividerResizeLayout2.mHandler.postDelayed(dividerResizeLayout2.mFinishTimeoutRunnable, 1000L);
    }

    public final void stopWaitingForSyncAppsCallback(String str) {
        int i = 0;
        if (!this.mWaitingForSyncAppsCallback) {
            Log.w("DividerResizeController", "stopWaitingForSyncAppsCallback: failed, there is no waiting!");
            return;
        }
        this.mWaitingForSyncAppsCallback = false;
        ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mSyncAppsCallbackTimeoutRunnable);
        StringBuilder sb = new StringBuilder("stopWaitingForSyncAppsCallback: reason=");
        sb.append(str);
        sb.append(", SyncId=");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mSyncAppsId, "DividerResizeController", sb);
        final DividerResizeLayout dividerResizeLayout = this.mDividerResizeLayout;
        for (int size = dividerResizeLayout.mResizeTargets.size() - 1; size >= 0; size--) {
            DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
            if (dividerResizeTarget != null && dividerResizeTarget.mSplitDismissSide == 0) {
                dividerResizeTarget.mView.setVisibility(4);
                dividerResizeTarget.startOutlineInsetsAnimation(false);
            }
        }
        ValueAnimator valueAnimator = dividerResizeLayout.mWindowAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        dividerResizeLayout.mWindowAlphaAnimator = ofFloat;
        ofFloat.addUpdateListener(new DividerResizeLayout$$ExternalSyntheticLambda3(dividerResizeLayout, i));
        dividerResizeLayout.mWindowAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.1
            public AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DividerResizeLayout dividerResizeLayout2 = DividerResizeLayout.this;
                dividerResizeLayout2.mWindowAlphaAnimator = null;
                dividerResizeLayout2.postFinishRunnableIfPossible("onAnimationFinished", false);
            }
        });
        dividerResizeLayout.mWindowAlphaAnimator.setInterpolator(new AccelerateInterpolator());
        dividerResizeLayout.mWindowAlphaAnimator.setDuration(DividerResizeLayout.WINDOW_ALPHA_ANIM_DURATION);
        dividerResizeLayout.mWindowAlphaAnimator.start();
        DividerResizeController$$ExternalSyntheticLambda0 dividerResizeController$$ExternalSyntheticLambda0 = new DividerResizeController$$ExternalSyntheticLambda0(this, 1);
        if (!this.mDividerResizeLayout.shouldDeferRemove(true)) {
            dividerResizeController$$ExternalSyntheticLambda0.run();
            return;
        }
        DividerResizeLayout dividerResizeLayout2 = this.mDividerResizeLayout;
        dividerResizeLayout2.mFinishRunnable = dividerResizeController$$ExternalSyntheticLambda0;
        dividerResizeLayout2.mHandler.postDelayed(dividerResizeLayout2.mFinishTimeoutRunnable, 1000L);
    }
}
