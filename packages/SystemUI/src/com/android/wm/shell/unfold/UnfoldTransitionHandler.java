package com.android.wm.shell.unfold;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.util.FloatProperty;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.bubbles.BubbleViewProvider;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTaskController;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.wm.shell.unfold.animation.FullscreenUnfoldTaskAnimator;
import com.android.wm.shell.unfold.animation.SplitTaskUnfoldAnimator;
import com.android.wm.shell.unfold.animation.UnfoldTaskAnimator;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class UnfoldTransitionHandler implements Transitions.TransitionHandler, ShellUnfoldProgressProvider.UnfoldListener {
    static final int FINISH_ANIMATION_TIMEOUT_MILLIS = 5000;
    public final UnfoldTransitionHandler$$ExternalSyntheticLambda0 mAnimationPlayingTimeoutRunnable;
    public final List mAnimators;
    public final Optional mBubbleTaskUnfoldTransitionMerger;
    public final Executor mExecutor;
    public Transitions.TransitionFinishCallback mFinishCallback;
    public final Handler mHandler;
    public final TransactionPool mTransactionPool;
    public IBinder mTransition;
    public final Transitions mTransitions;
    public final ShellUnfoldProgressProvider mUnfoldProgressProvider;
    public boolean mAnimationFinished = false;
    public float mLastAnimationProgress = 0.0f;

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

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.unfold.UnfoldTransitionHandler$$ExternalSyntheticLambda0] */
    public UnfoldTransitionHandler(ShellInit shellInit, ShellUnfoldProgressProvider shellUnfoldProgressProvider, FullscreenUnfoldTaskAnimator fullscreenUnfoldTaskAnimator, SplitTaskUnfoldAnimator splitTaskUnfoldAnimator, TransactionPool transactionPool, Executor executor, Handler handler, Transitions transitions, Optional<BubbleController> optional) {
        ArrayList arrayList = new ArrayList();
        this.mAnimators = arrayList;
        final int i = 0;
        this.mAnimationPlayingTimeoutRunnable = new Runnable(this) { // from class: com.android.wm.shell.unfold.UnfoldTransitionHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ UnfoldTransitionHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                UnfoldTransitionHandler unfoldTransitionHandler = this.f$0;
                switch (i2) {
                    case 0:
                        int i3 = UnfoldTransitionHandler.FINISH_ANIMATION_TIMEOUT_MILLIS;
                        unfoldTransitionHandler.getClass();
                        Slog.wtf("UnfoldTransitionHandler", "Timeout occurred when playing the unfold animation, force finishing the transition");
                        unfoldTransitionHandler.finishTransitionIfNeeded();
                        break;
                    default:
                        for (int i4 = 0; i4 < ((ArrayList) unfoldTransitionHandler.mAnimators).size(); i4++) {
                            ((UnfoldTaskAnimator) ((ArrayList) unfoldTransitionHandler.mAnimators).get(i4)).init();
                        }
                        unfoldTransitionHandler.mTransitions.addHandler(unfoldTransitionHandler);
                        unfoldTransitionHandler.mUnfoldProgressProvider.addListener(unfoldTransitionHandler.mExecutor, unfoldTransitionHandler);
                        break;
                }
            }
        };
        this.mUnfoldProgressProvider = shellUnfoldProgressProvider;
        this.mTransitions = transitions;
        this.mTransactionPool = transactionPool;
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mBubbleTaskUnfoldTransitionMerger = optional;
        arrayList.add(splitTaskUnfoldAnimator);
        arrayList.add(fullscreenUnfoldTaskAnimator);
        if (shellUnfoldProgressProvider == ShellUnfoldProgressProvider.NO_PROVIDER || !Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        final int i2 = 1;
        shellInit.addInitCallback(new Runnable(this) { // from class: com.android.wm.shell.unfold.UnfoldTransitionHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ UnfoldTransitionHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                UnfoldTransitionHandler unfoldTransitionHandler = this.f$0;
                switch (i22) {
                    case 0:
                        int i3 = UnfoldTransitionHandler.FINISH_ANIMATION_TIMEOUT_MILLIS;
                        unfoldTransitionHandler.getClass();
                        Slog.wtf("UnfoldTransitionHandler", "Timeout occurred when playing the unfold animation, force finishing the transition");
                        unfoldTransitionHandler.finishTransitionIfNeeded();
                        break;
                    default:
                        for (int i4 = 0; i4 < ((ArrayList) unfoldTransitionHandler.mAnimators).size(); i4++) {
                            ((UnfoldTaskAnimator) ((ArrayList) unfoldTransitionHandler.mAnimators).get(i4)).init();
                        }
                        unfoldTransitionHandler.mTransitions.addHandler(unfoldTransitionHandler);
                        unfoldTransitionHandler.mUnfoldProgressProvider.addListener(unfoldTransitionHandler.mExecutor, unfoldTransitionHandler);
                        break;
                }
            }
        }, this);
    }

    public static boolean shouldPlayUnfoldAnimation(TransitionRequestInfo transitionRequestInfo) {
        TransitionRequestInfo.DisplayChange displayChange;
        if (ValueAnimator.areAnimatorsEnabled() && transitionRequestInfo.getType() == 6 && (displayChange = transitionRequestInfo.getDisplayChange()) != null && displayChange.getDisplayId() == 0 && displayChange.isPhysicalDisplayChanged() && displayChange.getStartAbsBounds() != null && displayChange.getEndAbsBounds() != null) {
            return displayChange.getEndAbsBounds().height() * displayChange.getEndAbsBounds().width() > displayChange.getStartAbsBounds().height() * displayChange.getStartAbsBounds().width();
        }
        return false;
    }

    public final void finishTransitionIfNeeded() {
        if (this.mFinishCallback == null) {
            return;
        }
        for (int i = 0; i < ((ArrayList) this.mAnimators).size(); i++) {
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) ((ArrayList) this.mAnimators).get(i);
            unfoldTaskAnimator.clearTasks();
            unfoldTaskAnimator.stop();
        }
        this.mHandler.removeCallbacks(this.mAnimationPlayingTimeoutRunnable);
        this.mFinishCallback.onTransitionFinished(null);
        this.mFinishCallback = null;
        this.mTransition = null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if (!shouldPlayUnfoldAnimation(transitionRequestInfo)) {
            return null;
        }
        this.mTransition = iBinder;
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        char c;
        boolean z;
        TaskViewTransitions.PendingTransition pendingTransitionFindPending;
        BubbleViewProvider bubbleViewProvider;
        if (transitionInfo.getType() == 6 && (transitionInfo.getFlags() & 47360) == 0) {
            boolean z2 = false;
            for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                if (taskInfo != null && taskInfo.configuration.windowConfiguration.isAlwaysOnTop()) {
                    if (!this.mBubbleTaskUnfoldTransitionMerger.isPresent()) {
                        return;
                    }
                    BubbleController bubbleController = (BubbleController) this.mBubbleTaskUnfoldTransitionMerger.get();
                    BubbleTransitions bubbleTransitions = bubbleController.mBubbleTransitions;
                    if (bubbleTransitions.mTaskViewTransitions.findTaskView(taskInfo) != null) {
                        Rect endAbsBounds = change.getEndAbsBounds();
                        ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                        SurfaceControl leash = change.getLeash();
                        TaskViewTransitions taskViewTransitions = bubbleTransitions.mTaskViewTransitions;
                        TaskViewTaskController taskViewTaskControllerFindTaskView = taskViewTransitions.findTaskView(taskInfo2);
                        if (taskViewTaskControllerFindTaskView == null || (pendingTransitionFindPending = taskViewTransitions.findPending(taskViewTaskControllerFindTaskView, 6)) == null) {
                            z = false;
                        } else {
                            taskViewTransitions.mPending.remove(pendingTransitionFindPending);
                            transaction.reparent(leash, taskViewTaskControllerFindTaskView.mSurfaceControl).setPosition(leash, 0.0f, 0.0f).setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height()).show(leash);
                            transaction2.reparent(leash, taskViewTaskControllerFindTaskView.mSurfaceControl).setPosition(leash, 0.0f, 0.0f).setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height());
                            TaskViewRepository.TaskViewState taskViewState = (TaskViewRepository.TaskViewState) ((WeakHashMap) taskViewTransitions.mTaskViews).get(taskViewTaskControllerFindTaskView);
                            if (taskViewState != null) {
                                taskViewState.mBounds.set(endAbsBounds);
                            }
                            z = true;
                        }
                        if (z && (bubbleViewProvider = bubbleController.mBubbleData.mSelectedBubble) != null && bubbleViewProvider.getExpandedView() != null) {
                            bubbleViewProvider.getExpandedView().onContainerClipUpdate();
                        }
                    } else {
                        z = false;
                    }
                    if (!z) {
                        return;
                    }
                }
                if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && taskInfo != null && taskInfo.isSplitScreen()) {
                    z2 = true;
                }
            }
            if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && z2) {
                ArrayList arrayList = (ArrayList) this.mAnimators;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((UnfoldTaskAnimator) obj).onSplitScreenTransitionMerged(transaction);
                }
            }
            transaction.apply();
            transitionFinishCallback.onTransitionFinished(null);
            int i3 = 0;
            while (true) {
                if (i3 >= transitionInfo.getChanges().size()) {
                    c = 0;
                    break;
                }
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i3);
                if ((change2.getFlags() & 32) == 0 || change2.getEndAbsBounds() == null || change2.getStartAbsBounds() == null) {
                    i3++;
                } else {
                    c = change2.getEndAbsBounds().height() * change2.getEndAbsBounds().width() > change2.getStartAbsBounds().height() * change2.getStartAbsBounds().width() ? (char) 1 : (char) 2;
                }
            }
            if (c == 2) {
                finishTransitionIfNeeded();
            }
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onFoldStateChanged(boolean z) {
        if (z) {
            finishTransitionIfNeeded();
        }
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeFinished() {
        finishTransitionIfNeeded();
        this.mAnimationFinished = !(this.mLastAnimationProgress == 0.0f);
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider.UnfoldListener
    public final void onStateChangeProgress(float f) {
        TransactionPool transactionPool;
        this.mLastAnimationProgress = f;
        if (this.mTransition == null) {
            return;
        }
        SurfaceControl.Transaction transactionAcquire = null;
        int i = 0;
        while (true) {
            int size = ((ArrayList) this.mAnimators).size();
            transactionPool = this.mTransactionPool;
            if (i >= size) {
                break;
            }
            UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) ((ArrayList) this.mAnimators).get(i);
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a6  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int iM;
        if (iBinder == this.mTransition) {
            if (CoreRune.MW_EMBED_ACTIVITY) {
                for (int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM2 >= 0; iM2--) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM2);
                    if (change.getMode() == 6 && change.hasFlags(512)) {
                        break;
                    }
                }
                for (iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
                    if (change2.getMode() != 6 || !change2.hasFlags(64) || (!change2.hasFlags(67108864) && !change2.getConfiguration().windowConfiguration.isPopOver())) {
                    }
                }
                for (int i = 0; i < ((ArrayList) this.mAnimators).size(); i++) {
                    final UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) ((ArrayList) this.mAnimators).get(i);
                    unfoldTaskAnimator.clearTasks();
                    transitionInfo.getChanges().forEach(new Consumer() { // from class: com.android.wm.shell.unfold.UnfoldTransitionHandler$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            UnfoldTaskAnimator unfoldTaskAnimator2 = unfoldTaskAnimator;
                            TransitionInfo.Change change3 = (TransitionInfo.Change) obj;
                            int i2 = UnfoldTransitionHandler.FINISH_ANIMATION_TIMEOUT_MILLIS;
                            if (change3.getTaskInfo() != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7334717703168078630L, 0, String.valueOf(change3.getTaskInfo()), String.valueOf(TransitionInfo.modeToString(change3.getMode())), String.valueOf(unfoldTaskAnimator2.isApplicableTask(change3.getTaskInfo())));
                            }
                            if (change3.getTaskInfo() != null) {
                                if ((change3.getMode() == 6 || TransitionUtil.isOpeningType(change3.getMode())) && unfoldTaskAnimator2.isApplicableTask(change3.getTaskInfo())) {
                                    unfoldTaskAnimator2.onTaskAppeared(change3.getTaskInfo(), change3.getLeash());
                                }
                            }
                        }
                    });
                    if (unfoldTaskAnimator.hasActiveTasks()) {
                        unfoldTaskAnimator.prepareStartTransaction(transaction);
                        unfoldTaskAnimator.prepareFinishTransaction(transaction2);
                        unfoldTaskAnimator.start();
                    }
                }
                transaction.apply();
                this.mFinishCallback = transitionFinishCallback;
                if (!this.mAnimationFinished) {
                    finishTransitionIfNeeded();
                    return true;
                }
                Handler handler = this.mHandler;
                UnfoldTransitionHandler$$ExternalSyntheticLambda0 unfoldTransitionHandler$$ExternalSyntheticLambda0 = this.mAnimationPlayingTimeoutRunnable;
                handler.removeCallbacks(unfoldTransitionHandler$$ExternalSyntheticLambda0);
                handler.postDelayed(unfoldTransitionHandler$$ExternalSyntheticLambda0, 5000L);
                return true;
            }
            while (iM >= 0) {
            }
            while (i < ((ArrayList) this.mAnimators).size()) {
            }
            transaction.apply();
            this.mFinishCallback = transitionFinishCallback;
            if (!this.mAnimationFinished) {
            }
        }
        return false;
    }
}
