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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        for (int i = 0; i < this.mAnimators.size(); i++) {
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimators.get(i);
            unfoldTaskAnimator.resetAllSurfaces(acquire);
            unfoldTaskAnimator.prepareFinishTransaction(acquire);
        }
        acquire.apply();
        transactionPool.release(acquire);
        this.mIsInStageChange = false;
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeProgress(float f) {
        TransactionPool transactionPool;
        if (((UnfoldTransitionHandler) ((Optional) this.mUnfoldTransitionHandler.get()).get()).mTransition != null) {
            return;
        }
        SurfaceControl.Transaction transaction = null;
        int i = 0;
        while (true) {
            int size = this.mAnimators.size();
            transactionPool = this.mTransactionPool;
            if (i >= size) {
                break;
            }
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimators.get(i);
            if (unfoldTaskAnimator.hasActiveTasks()) {
                if (transaction == null) {
                    transaction = transactionPool.acquire();
                }
                unfoldTaskAnimator.applyAnimationProgress(f, transaction);
            }
            i++;
        }
        if (transaction != null) {
            transaction.apply();
            transactionPool.release(transaction);
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeStarted() {
        TransactionPool transactionPool;
        if (((UnfoldTransitionHandler) ((Optional) this.mUnfoldTransitionHandler.get()).get()).mTransition != null) {
            return;
        }
        this.mIsInStageChange = true;
        SurfaceControl.Transaction transaction = null;
        int i = 0;
        while (true) {
            int size = this.mAnimators.size();
            transactionPool = this.mTransactionPool;
            if (i >= size) {
                break;
            }
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) this.mAnimators.get(i);
            if (unfoldTaskAnimator.hasActiveTasks()) {
                if (transaction == null) {
                    transaction = transactionPool.acquire();
                }
                unfoldTaskAnimator.prepareStartTransaction(transaction);
            }
            i++;
        }
        if (transaction != null) {
            transaction.apply();
            transactionPool.release(transaction);
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
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            unfoldTaskAnimator.resetSurface(runningTaskInfo, acquire);
            acquire.apply();
            transactionPool.release(acquire);
        }
        unfoldTaskAnimator.onTaskVanished(runningTaskInfo);
        this.mAnimatorsByTaskId.remove(runningTaskInfo.taskId);
    }
}
