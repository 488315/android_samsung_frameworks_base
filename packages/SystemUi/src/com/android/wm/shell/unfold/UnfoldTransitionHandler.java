package com.android.wm.shell.unfold;

import android.app.ActivityManager;
import android.os.IBinder;
import android.util.FloatProperty;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.wm.shell.unfold.animation.FullscreenUnfoldTaskAnimator;
import com.android.wm.shell.unfold.animation.SplitTaskUnfoldAnimator;
import com.android.wm.shell.unfold.animation.UnfoldTaskAnimator;
import com.android.wm.shell.util.TransitionUtil;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionHandler implements Transitions.TransitionHandler, ShellUnfoldProgressProvider.UnfoldListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List mAnimators;
    public final Executor mExecutor;
    public Transitions.TransitionFinishCallback mFinishCallback;
    public final TransactionPool mTransactionPool;
    public IBinder mTransition;
    public final Transitions mTransitions;
    public final ShellUnfoldProgressProvider mUnfoldProgressProvider;

    static {
        new FloatProperty("progress") { // from class: com.android.wm.shell.unfold.UnfoldTransitionHandler.1
            public float mProgress;

            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(this.mProgress);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                this.mProgress = f;
                ((UnfoldTransitionHandler) obj).onStateChangeProgress(f);
            }
        };
    }

    public UnfoldTransitionHandler(ShellInit shellInit, ShellUnfoldProgressProvider shellUnfoldProgressProvider, FullscreenUnfoldTaskAnimator fullscreenUnfoldTaskAnimator, SplitTaskUnfoldAnimator splitTaskUnfoldAnimator, TransactionPool transactionPool, Executor executor, Transitions transitions) {
        ArrayList arrayList = new ArrayList();
        this.mAnimators = arrayList;
        this.mUnfoldProgressProvider = shellUnfoldProgressProvider;
        this.mTransactionPool = transactionPool;
        this.mExecutor = executor;
        this.mTransitions = transitions;
        arrayList.add(splitTaskUnfoldAnimator);
        arrayList.add(fullscreenUnfoldTaskAnimator);
        if (shellUnfoldProgressProvider == ShellUnfoldProgressProvider.NO_PROVIDER || !Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.unfold.UnfoldTransitionHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UnfoldTransitionHandler unfoldTransitionHandler = UnfoldTransitionHandler.this;
                int i = 0;
                while (true) {
                    ArrayList arrayList2 = (ArrayList) unfoldTransitionHandler.mAnimators;
                    if (i >= arrayList2.size()) {
                        unfoldTransitionHandler.mTransitions.addHandler(unfoldTransitionHandler);
                        unfoldTransitionHandler.mUnfoldProgressProvider.addListener(unfoldTransitionHandler.mExecutor, unfoldTransitionHandler);
                        return;
                    } else {
                        ((UnfoldTaskAnimator) arrayList2.get(i)).init();
                        i++;
                    }
                }
            }
        }, this);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if (!(transitionRequestInfo.getType() == 6 && transitionRequestInfo.getDisplayChange() != null && transitionRequestInfo.getDisplayChange().isPhysicalDisplayChanged())) {
            return null;
        }
        this.mTransition = iBinder;
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (transitionInfo.getType() == 6) {
            boolean z = false;
            for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) transitionInfo.getChanges().get(i)).getTaskInfo();
                if (taskInfo != null && taskInfo.configuration.windowConfiguration.isAlwaysOnTop()) {
                    return;
                }
                if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && taskInfo != null && taskInfo.isSplitScreen()) {
                    z = true;
                }
            }
            if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && z) {
                Iterator it = ((ArrayList) this.mAnimators).iterator();
                while (it.hasNext()) {
                    ((UnfoldTaskAnimator) it.next()).onSplitScreenTransitionMerged(transaction);
                }
            }
            transaction.apply();
            transitionFinishCallback.onTransitionFinished(null, null);
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeFinished() {
        if (this.mFinishCallback == null) {
            return;
        }
        int i = 0;
        while (true) {
            List list = this.mAnimators;
            if (i >= ((ArrayList) list).size()) {
                this.mFinishCallback.onTransitionFinished(null, null);
                this.mFinishCallback = null;
                this.mTransition = null;
                return;
            } else {
                UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) ((ArrayList) list).get(i);
                unfoldTaskAnimator.clearTasks();
                unfoldTaskAnimator.stop();
                i++;
            }
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeProgress(float f) {
        TransactionPool transactionPool;
        if (this.mTransition == null) {
            return;
        }
        SurfaceControl.Transaction transaction = null;
        int i = 0;
        while (true) {
            List list = this.mAnimators;
            int size = ((ArrayList) list).size();
            transactionPool = this.mTransactionPool;
            if (i >= size) {
                break;
            }
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) ((ArrayList) list).get(i);
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

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int i = 0;
        if (iBinder != this.mTransition) {
            return false;
        }
        while (true) {
            List list = this.mAnimators;
            if (i >= ((ArrayList) list).size()) {
                transaction.apply();
                this.mFinishCallback = transitionFinishCallback;
                return true;
            }
            final UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) ((ArrayList) list).get(i);
            unfoldTaskAnimator.clearTasks();
            transitionInfo.getChanges().forEach(new Consumer() { // from class: com.android.wm.shell.unfold.UnfoldTransitionHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    UnfoldTaskAnimator unfoldTaskAnimator2 = UnfoldTaskAnimator.this;
                    TransitionInfo.Change change = (TransitionInfo.Change) obj;
                    if (change.getTaskInfo() != null && ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1949097678, 0, "startAnimation, check taskInfo: %s, mode: %s, isApplicableTask: %s", String.valueOf(change.getTaskInfo()), String.valueOf(TransitionInfo.modeToString(change.getMode())), String.valueOf(unfoldTaskAnimator2.isApplicableTask(change.getTaskInfo())));
                    }
                    if (change.getTaskInfo() != null) {
                        if ((change.getMode() == 6 || TransitionUtil.isOpeningType(change.getMode())) && unfoldTaskAnimator2.isApplicableTask(change.getTaskInfo())) {
                            unfoldTaskAnimator2.onTaskAppeared(change.getTaskInfo(), change.getLeash());
                        }
                    }
                }
            });
            if (unfoldTaskAnimator.hasActiveTasks()) {
                unfoldTaskAnimator.prepareStartTransaction(transaction);
                unfoldTaskAnimator.prepareFinishTransaction(transaction2);
                unfoldTaskAnimator.start();
            }
            i++;
        }
    }
}
