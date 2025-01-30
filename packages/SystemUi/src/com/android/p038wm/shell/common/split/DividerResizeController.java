package com.android.p038wm.shell.common.split;

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
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.split.DividerResizeLayout;
import com.android.p038wm.shell.splitscreen.CellStage;
import com.android.p038wm.shell.splitscreen.MainStage;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DividerResizeController {
    public static boolean USE_GUIDE_VIEW_EFFECTS = false;
    public final Context mContext;
    public int mCurrentDividerPosition;
    public int mDefaultHandleMoveThreshold;
    public DividerResizeLayout mDividerResizeLayout;
    public int mDividerSize;
    public DividerView mDividerView;
    public boolean mIsHorizontalDivision;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResizeAlgorithm {
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
        public int mTouchPosition;

        /* renamed from: -$$Nest$mupdate, reason: not valid java name */
        public static void m2740$$Nest$mupdate(ResizeAlgorithm resizeAlgorithm, int i) {
            if (resizeAlgorithm.mTouchPosition != i) {
                resizeAlgorithm.mTouchPosition = i;
                if (!(i < resizeAlgorithm.mDismissStartThreshold)) {
                    r1 = (i <= resizeAlgorithm.mDismissEndThreshold ? 0 : 1) != 0 ? 2 : 0;
                }
                if (resizeAlgorithm.mSplitDismissSide != r1) {
                    resizeAlgorithm.mSplitDismissSide = r1;
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
            if (i < this.mDismissStartThreshold) {
                return Math.min(-DividerResizeController.this.mDividerSize, this.mDividerSnapAlgorithm.getDismissStartTarget().position);
            }
            if (i > this.mDismissEndThreshold) {
                return Math.max(this.mDisplaySize, this.mDividerSnapAlgorithm.getDismissEndTarget().position);
            }
            if (i < i2) {
                return i2;
            }
            int i3 = this.mLastSplitTargetPosition;
            return i > i3 ? i3 : this.mDividerSnapAlgorithm.calculateSnapTarget(i, 0.0f, false).position;
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
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.mTouchPosition, "}");
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
        }
    }

    public final void finishResizing(int i) {
        final DividerSnapAlgorithm.SnapTarget dismissEndTarget;
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
        ResizeAlgorithm.m2740$$Nest$mupdate(resizeAlgorithm, i);
        int i2 = resizeAlgorithm.mTouchPosition;
        if (i2 < resizeAlgorithm.mDismissStartThreshold) {
            dismissEndTarget = resizeAlgorithm.mDividerSnapAlgorithm.getDismissStartTarget();
        } else {
            dismissEndTarget = i2 > resizeAlgorithm.mDismissEndThreshold ? resizeAlgorithm.mDividerSnapAlgorithm.getDismissEndTarget() : i2 < resizeAlgorithm.mFirstSplitTargetPosition ? resizeAlgorithm.mDividerSnapAlgorithm.getFirstSplitTarget() : i2 > resizeAlgorithm.mLastSplitTargetPosition ? resizeAlgorithm.mDividerSnapAlgorithm.getLastSplitTarget() : resizeAlgorithm.mDividerSnapAlgorithm.calculateSnapTarget(i2, 0.0f, false);
        }
        int i3 = resizeAlgorithm.mSplitDismissSide;
        boolean z3 = i3 != 0;
        int i4 = dismissEndTarget.position;
        boolean z4 = i4 != this.mCurrentDividerPosition;
        if (this.mIsResizing) {
            DividerResizeLayout dividerResizeLayout = this.mDividerResizeLayout;
            dividerResizeLayout.setAlpha(1.0f);
            DividerResizeLayout$$ExternalSyntheticLambda2 dividerResizeLayout$$ExternalSyntheticLambda2 = new DividerResizeLayout$$ExternalSyntheticLambda2(dividerResizeLayout, i4, i3);
            if (dividerResizeLayout.mFirstLayoutCalled && dividerResizeLayout.mAttachedToWindow) {
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
                handlerExecutor.executeDelayed(3000L, dividerResizeController$$ExternalSyntheticLambda0);
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("startWaitingForSyncAppsCallback: reason=resize_split, SyncId="), this.mSyncAppsId, "DividerResizeController");
            }
            z = this.mDividerResizeLayout.shouldDeferRemove(z3);
        } else {
            z = false;
        }
        StringBuilder sb = new StringBuilder("finishResizing: snapTargetPosition=");
        sb.append(dismissEndTarget.position);
        sb.append(", positionChanged=");
        sb.append(z4);
        sb.append(", isInDismissZone=");
        int i5 = resizeAlgorithm.mTouchPosition;
        if (!(i5 < resizeAlgorithm.mDismissStartThreshold)) {
            if (!(i5 > resizeAlgorithm.mDismissEndThreshold)) {
                z2 = false;
            }
        }
        sb.append(z2);
        sb.append(", deferStopDragging=");
        sb.append(z);
        Log.d("DividerResizeController", sb.toString());
        Runnable runnable = new Runnable() { // from class: com.android.wm.shell.common.split.DividerResizeController$$ExternalSyntheticLambda1
            /* JADX WARN: Removed duplicated region for block: B:12:0x0022  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x008f  */
            /* JADX WARN: Removed duplicated region for block: B:40:0x0097  */
            /* JADX WARN: Removed duplicated region for block: B:44:0x007d  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean z5;
                int i6;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                DividerResizeController dividerResizeController = DividerResizeController.this;
                DividerSnapAlgorithm.SnapTarget snapTarget = dismissEndTarget;
                dividerResizeController.getClass();
                if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING) {
                    DividerView dividerView = dividerResizeController.mDividerView;
                    if (dividerView != null && dividerView.mIsCellDivider) {
                        z5 = true;
                        i6 = snapTarget.flag;
                        if (i6 != 1) {
                            dividerResizeController.mStageCoordinator.onSnappedToDismiss(4, false, DividerResizeController.USE_GUIDE_VIEW_EFFECTS);
                        } else if (i6 != 2) {
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                StageCoordinator stageCoordinator = dividerResizeController.mStageCoordinator;
                                MainStage mainStage = stageCoordinator.mMainStage;
                                if (mainStage.mIsActive) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = mainStage.mRootTaskInfo;
                                    if (runningTaskInfo2 != null) {
                                        windowContainerTransaction.setChangeTransitMode(runningTaskInfo2.token, 1, "GuideViewEffects");
                                    }
                                    ActivityManager.RunningTaskInfo runningTaskInfo3 = stageCoordinator.mSideStage.mRootTaskInfo;
                                    if (runningTaskInfo3 != null) {
                                        windowContainerTransaction.setChangeTransitMode(runningTaskInfo3.token, 1, "GuideViewEffects");
                                    }
                                }
                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                                    CellStage cellStage = stageCoordinator.mCellStage;
                                    if (cellStage.mIsActive && (runningTaskInfo = cellStage.mRootTaskInfo) != null) {
                                        windowContainerTransaction.setChangeTransitMode(runningTaskInfo.token, 1, "GuideViewEffects");
                                    }
                                }
                            }
                            if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING && z5) {
                                dividerResizeController.mSplitLayout.setCellDividePosition(snapTarget.position, windowContainerTransaction, true);
                            } else {
                                dividerResizeController.mSplitLayout.setDividePosition(snapTarget.position, windowContainerTransaction, true);
                            }
                        } else {
                            dividerResizeController.mStageCoordinator.onSnappedToDismiss(4, true, DividerResizeController.USE_GUIDE_VIEW_EFFECTS);
                        }
                        dividerResizeController.mStageCoordinator.onLayoutSizeChanging(0, 0, dividerResizeController.mSplitLayout);
                        if (dividerResizeController.mWaitingForSyncAppsCallback) {
                            dividerResizeController.clear();
                            return;
                        } else {
                            Log.d("DividerResizeController", "onStopDraggingFinished: WaitingForSyncAppsCallback");
                            return;
                        }
                    }
                }
                z5 = false;
                i6 = snapTarget.flag;
                if (i6 != 1) {
                }
                dividerResizeController.mStageCoordinator.onLayoutSizeChanging(0, 0, dividerResizeController.mSplitLayout);
                if (dividerResizeController.mWaitingForSyncAppsCallback) {
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
        if (!this.mWaitingForSyncAppsCallback) {
            Log.w("DividerResizeController", "stopWaitingForSyncAppsCallback: failed, there is no waiting!");
            return;
        }
        int i = 0;
        this.mWaitingForSyncAppsCallback = false;
        ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mSyncAppsCallbackTimeoutRunnable);
        StringBuilder sb = new StringBuilder("stopWaitingForSyncAppsCallback: reason=");
        sb.append(str);
        sb.append(", SyncId=");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mSyncAppsId, "DividerResizeController");
        final DividerResizeLayout dividerResizeLayout = this.mDividerResizeLayout;
        int size = dividerResizeLayout.mResizeTargets.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
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
            public C38761() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DividerResizeLayout dividerResizeLayout2 = DividerResizeLayout.this;
                dividerResizeLayout2.mWindowAlphaAnimator = null;
                DividerResizeLayout.m2741$$Nest$monAnimationFinished(dividerResizeLayout2, "alphaAnimator");
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
