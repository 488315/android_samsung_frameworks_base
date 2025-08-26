package com.android.wm.shell.unfold;

import android.app.ActivityManager;
import android.util.SparseArray;
import android.view.SurfaceControl;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.wm.shell.unfold.animation.UnfoldTaskAnimator;
import dagger.Lazy;
import java.util.List;
import java.util.Optional;

/* loaded from: classes3.dex */
public class UnfoldAnimationController implements ShellUnfoldProgressProvider.UnfoldListener {
    public final List mAnimators;
    public final ShellExecutor mExecutor;
    public boolean mIsInStageChange;
    public final TransactionPool mTransactionPool;
    public final ShellUnfoldProgressProvider mUnfoldProgressProvider;
    public final Lazy mUnfoldTransitionHandler;
    public final SparseArray mTaskSurfaces = new SparseArray();
    public final SparseArray mAnimatorsByTaskId = new SparseArray();

    public UnfoldAnimationController(ShellInit shellInit, TransactionPool transactionPool, ShellUnfoldProgressProvider shellUnfoldProgressProvider, List<UnfoldTaskAnimator> list, Lazy lazy, ShellExecutor shellExecutor) {
        this.mUnfoldProgressProvider = shellUnfoldProgressProvider;
        this.mUnfoldTransitionHandler = lazy;
        this.mTransactionPool = transactionPool;
        this.mExecutor = shellExecutor;
        this.mAnimators = list;
        if (shellUnfoldProgressProvider != ShellUnfoldProgressProvider.NO_PROVIDER) {
            shellInit.addInitCallback(new UnfoldAnimationController$$ExternalSyntheticLambda0(this, 0), this);
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeFinished() {
        if (((UnfoldTransitionHandler) ((Optional) this.mUnfoldTransitionHandler.get()).get()).mTransition != null) {
            return;
        }
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
        for (int i = 0; i < this.mAnimators.size(); i++) {
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimators.get(i);
            unfoldTaskAnimator.resetAllSurfaces(transactionAcquire);
            unfoldTaskAnimator.prepareFinishTransaction(transactionAcquire);
        }
        transactionAcquire.apply();
        transactionPool.release(transactionAcquire);
        this.mIsInStageChange = false;
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeProgress(float f) {
        TransactionPool transactionPool;
        if (((UnfoldTransitionHandler) ((Optional) this.mUnfoldTransitionHandler.get()).get()).mTransition != null) {
            return;
        }
        SurfaceControl.Transaction transactionAcquire = null;
        int i = 0;
        while (true) {
            int size = this.mAnimators.size();
            transactionPool = this.mTransactionPool;
            if (i >= size) {
                break;
            }
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimators.get(i);
            if (unfoldTaskAnimator.hasActiveTasks()) {
                if (transactionAcquire == null) {
                    transactionAcquire = transactionPool.acquire();
                }
                unfoldTaskAnimator.applyAnimationProgress(f, transactionAcquire);
            }
            i++;
        }
        if (transactionAcquire != null) {
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeStarted() {
        TransactionPool transactionPool;
        if (((UnfoldTransitionHandler) ((Optional) this.mUnfoldTransitionHandler.get()).get()).mTransition != null) {
            return;
        }
        this.mIsInStageChange = true;
        SurfaceControl.Transaction transactionAcquire = null;
        int i = 0;
        while (true) {
            int size = this.mAnimators.size();
            transactionPool = this.mTransactionPool;
            if (i >= size) {
                break;
            }
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimators.get(i);
            if (unfoldTaskAnimator.hasActiveTasks()) {
                if (transactionAcquire == null) {
                    transactionAcquire = transactionPool.acquire();
                }
                unfoldTaskAnimator.prepareStartTransaction(transactionAcquire);
            }
            i++;
        }
        if (transactionAcquire != null) {
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
        }
    }

    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimatorsByTaskId.get(runningTaskInfo.taskId);
        if (unfoldTaskAnimator == null) {
            for (int i = 0; i < this.mAnimators.size(); i++) {
                UnfoldTaskAnimator unfoldTaskAnimator2 = (UnfoldTaskAnimator) this.mAnimators.get(i);
                if (unfoldTaskAnimator2.isApplicableTask(runningTaskInfo)) {
                    this.mAnimatorsByTaskId.put(runningTaskInfo.taskId, unfoldTaskAnimator2);
                    unfoldTaskAnimator2.onTaskAppeared(runningTaskInfo, (SurfaceControl) this.mTaskSurfaces.get(runningTaskInfo.taskId));
                    return;
                }
            }
            return;
        }
        if (unfoldTaskAnimator.isApplicableTask(runningTaskInfo)) {
            unfoldTaskAnimator.onTaskChanged(runningTaskInfo);
            return;
        }
        if (this.mIsInStageChange) {
            TransactionPool transactionPool = this.mTransactionPool;
            SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
            unfoldTaskAnimator.resetSurface(runningTaskInfo, transactionAcquire);
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
        }
        unfoldTaskAnimator.onTaskVanished(runningTaskInfo);
        this.mAnimatorsByTaskId.remove(runningTaskInfo.taskId);
    }
}
