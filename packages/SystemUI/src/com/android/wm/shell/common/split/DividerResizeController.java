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
        public static void m3236$$Nest$mupdate(ResizeAlgorithm resizeAlgorithm, int i) {
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

        public final boolean isInEndStashZone() {
            int i = this.mSplitStashEndThreshold;
            return i > 0 && this.mTouchPosition > i;
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
        final DividerSnapAlgorithm.SnapTarget snapTargetCalculateSnapTarget;
        boolean zShouldDeferRemove;
        if (!this.mResizingRequested) {
            Log.w("DividerResizeController", "finishResizing: failed, NOT resizing state!");
            return;
        }
        if (this.mIsFinishing) {
            Log.w("DividerResizeController", "finishResizing: failed, already finishing state!");
            return;
        }
        boolean z = true;
        this.mIsFinishing = true;
        if (!this.mIsResizing) {
            i = this.mCurrentDividerPosition;
        }
        ResizeAlgorithm resizeAlgorithm = this.mResizeAlgorithm;
        ResizeAlgorithm.m3236$$Nest$mupdate(resizeAlgorithm, i);
        int i2 = resizeAlgorithm.mTouchPosition;
        if (i2 < resizeAlgorithm.mDismissStartThreshold) {
            snapTargetCalculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.mDismissStartTarget;
        } else if (i2 > resizeAlgorithm.mDismissEndThreshold) {
            snapTargetCalculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.mDismissEndTarget;
        } else if (resizeAlgorithm.isInStartStashZone()) {
            snapTargetCalculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.getStashStartTarget();
        } else if (resizeAlgorithm.isInEndStashZone()) {
            snapTargetCalculateSnapTarget = resizeAlgorithm.mDividerSnapAlgorithm.getStashEndTarget();
        } else {
            int i3 = resizeAlgorithm.mTouchPosition;
            snapTargetCalculateSnapTarget = i3 < resizeAlgorithm.mFirstSplitTargetPosition ? resizeAlgorithm.mDividerSnapAlgorithm.mFirstSplitTarget : i3 > resizeAlgorithm.mLastSplitTargetPosition ? resizeAlgorithm.mDividerSnapAlgorithm.mLastSplitTarget : resizeAlgorithm.mDividerSnapAlgorithm.calculateSnapTarget(i3, false);
        }
        int i4 = resizeAlgorithm.mSplitDismissSide;
        boolean z2 = i4 != 0;
        int i5 = snapTargetCalculateSnapTarget.position;
        boolean z3 = i5 != this.mCurrentDividerPosition;
        if (this.mIsResizing) {
            DividerResizeLayout dividerResizeLayout = this.mDividerResizeLayout;
            dividerResizeLayout.setAlpha(1.0f);
            DividerResizeLayout$$ExternalSyntheticLambda2 dividerResizeLayout$$ExternalSyntheticLambda2 = new DividerResizeLayout$$ExternalSyntheticLambda2(dividerResizeLayout, i5, i4);
            if (dividerResizeLayout.isReadyToShow()) {
                dividerResizeLayout$$ExternalSyntheticLambda2.run();
            } else {
                dividerResizeLayout.mActionDropRunnable = dividerResizeLayout$$ExternalSyntheticLambda2;
                Log.d("DividerResizeLayout", "onActionDrop: defer action drop, isn't ready to show yet");
            }
            if (!z3) {
                z2 = true;
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
            zShouldDeferRemove = this.mDividerResizeLayout.shouldDeferRemove(z2);
        } else {
            zShouldDeferRemove = false;
        }
        StringBuilder sb = new StringBuilder("finishResizing: snapTargetPosition=");
        sb.append(snapTargetCalculateSnapTarget.position);
        sb.append(", positionChanged=");
        sb.append(z3);
        sb.append(", isInDismissZone=");
        int i6 = resizeAlgorithm.mTouchPosition;
        if (i6 >= resizeAlgorithm.mDismissStartThreshold && i6 <= resizeAlgorithm.mDismissEndThreshold) {
            z = false;
        }
        sb.append(z);
        sb.append(", deferStopDragging=");
        sb.append(zShouldDeferRemove);
        Log.d("DividerResizeController", sb.toString());
        Runnable runnable = new Runnable() { // from class: com.android.wm.shell.common.split.DividerResizeController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ActivityManager.RunningTaskInfo runningTaskInfo;
                DividerView dividerView;
                DividerResizeController dividerResizeController = this.f$0;
                DividerSnapAlgorithm.SnapTarget snapTarget = snapTargetCalculateSnapTarget;
                dividerResizeController.getClass();
                boolean z4 = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
                boolean z5 = z4 && (dividerView = dividerResizeController.mDividerView) != null && dividerView.mIsCellDivider;
                int i7 = snapTarget.snapPosition;
                if (i7 == 11) {
                    dividerResizeController.mStageCoordinator.onSnappedToDismiss(4, false, DividerResizeController.USE_GUIDE_VIEW_EFFECTS);
                } else if (i7 != 12) {
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
                    } else if (resizeAlgorithm2.isInEndStashZone()) {
                        dividerResizeController.mSplitLayout.mSplitState.mState = 15;
                    } else {
                        dividerResizeController.mSplitLayout.mSplitState.mState = 10;
                    }
                    if (CoreRune.MW_SA_LOGGING && dividerResizeController.mSplitLayout.mSplitState.isSplitStashed()) {
                        boolean z6 = dividerResizeController.mContext.getResources().getConfiguration().orientation == 1;
                        boolean z7 = dividerResizeController.mSplitLayout.mIsLeftRightSplit;
                        CoreSaLogger.logForAdvanced("1006", z6 ? z7 ? "From Portrait_SbS" : "From Portrait_T&B" : z7 ? "From Landscape_T&B" : "From Landscape_SbS");
                    }
                    int i8 = snapTarget.position;
                    if (z4 && z5) {
                        dividerResizeController.mSplitLayout.setCellDividePosition(i8, windowContainerTransaction, true);
                    } else {
                        dividerResizeController.mSplitLayout.setDividePosition(i8, windowContainerTransaction, true);
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
        if (!zShouldDeferRemove) {
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        dividerResizeLayout.mWindowAlphaAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new DividerResizeLayout$$ExternalSyntheticLambda3(dividerResizeLayout, i));
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
