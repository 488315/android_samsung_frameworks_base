package com.android.wm.shell.taskview;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.bubbles.BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda4;
import com.android.wm.shell.transition.DefaultMixedTransition;
import com.android.wm.shell.transition.Transitions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class TaskViewTransitions implements Transitions.TransitionHandler, TaskViewController {
    public SurfaceControl.Transaction mFinishTransaction;
    public final LinkedList mLogHistory;
    public DefaultMixedHandler mMixedHandler;
    public final ArrayList mPending = new ArrayList();
    public final boolean[] mRegistered = {false};
    public final Executor mShellExecutor;
    public final SimpleDateFormat mSimpleDateFormat;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Map mTaskViews;
    public final TaskViewTransitionObserver mTransitionObserver;
    public final Transitions mTransitions;

    /* JADX INFO: Access modifiers changed from: package-private */
    public class PendingTransition {
        public IBinder mClaimed;
        public BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0 mExternalTransition;
        public final IBinder mLaunchCookie;
        public boolean mRemoveTaskViewRequested;
        public final TaskViewTaskController mTaskView;
        public final int mType;
        public final WindowContainerTransaction mWct;

        public PendingTransition(int i, WindowContainerTransaction windowContainerTransaction, TaskViewTaskController taskViewTaskController, IBinder iBinder) {
            this.mType = i;
            this.mWct = windowContainerTransaction;
            this.mTaskView = taskViewTaskController;
            this.mLaunchCookie = iBinder;
        }
    }

    public final class TaskViewTransitionObserver implements Transitions.TransitionObserver {
        public /* synthetic */ TaskViewTransitionObserver(TaskViewTransitions taskViewTransitions, int i) {
            this();
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionFinished(IBinder iBinder, boolean z) {
            TaskViewTransitions.this.setFinishTransaction(null);
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2) {
            transitionInfo.getChanges().forEach(new Consumer() { // from class: com.android.wm.shell.taskview.TaskViewTransitions$TaskViewTransitionObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskViewTaskController taskViewTaskControllerFindTaskView;
                    TaskViewTransitions.TaskViewTransitionObserver taskViewTransitionObserver = this.f$0;
                    SurfaceControl.Transaction transaction3 = transaction;
                    SurfaceControl.Transaction transaction4 = transaction2;
                    TransitionInfo.Change change = (TransitionInfo.Change) obj;
                    taskViewTransitionObserver.getClass();
                    ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                    TaskViewTransitions taskViewTransitions = TaskViewTransitions.this;
                    TaskViewTaskController taskViewTaskController = null;
                    if (taskInfo != null && (taskViewTaskControllerFindTaskView = taskViewTransitions.findTaskView(change.getTaskInfo())) != null && taskViewTaskControllerFindTaskView.mSurfaceControl != null && ((WeakHashMap) taskViewTransitions.mTaskViews).get(taskViewTaskControllerFindTaskView) != null) {
                        taskViewTaskController = taskViewTaskControllerFindTaskView;
                    }
                    if (taskViewTaskController != null) {
                        SurfaceControl surfaceControl = taskViewTaskController.mSurfaceControl;
                        Rect rect = ((TaskViewRepository.TaskViewState) ((WeakHashMap) taskViewTransitions.mTaskViews).get(taskViewTaskController)).mBounds;
                        SurfaceControl leash = change.getLeash();
                        Rect rect2 = new Rect(0, 0, rect.width(), rect.height());
                        Slog.d("TaskViewTransitions", "handleTaskViewTaskChanges: " + leash);
                        change.setSkipSetupAnimHierarchy(true);
                        transaction3.reparent(leash, surfaceControl).setPosition(leash, 0.0f, 0.0f).setCrop(leash, rect2);
                        transaction4.reparent(leash, surfaceControl).setPosition(leash, 0.0f, 0.0f).setCrop(leash, rect2);
                        taskViewTransitions.setFinishTransaction(transaction4);
                    }
                }
            });
        }

        private TaskViewTransitionObserver() {
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionStarting(IBinder iBinder) {
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
        }
    }

    public TaskViewTransitions(Transitions transitions, TaskViewRepository taskViewRepository, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue) {
        new SurfaceControl.Transaction();
        this.mLogHistory = new LinkedList();
        this.mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mFinishTransaction = null;
        TaskViewTransitionObserver taskViewTransitionObserver = new TaskViewTransitionObserver(this, 0);
        this.mTransitionObserver = taskViewTransitionObserver;
        this.mTransitions = transitions;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellTaskOrganizer.getExecutor();
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViews = new WeakHashMap();
        transitions.registerObserver(taskViewTransitionObserver);
    }

    public final void enqueueExternal(TaskViewTaskController taskViewTaskController, BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0 bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0) {
        PendingTransition pendingTransition = new PendingTransition(0, null, taskViewTaskController, null);
        pendingTransition.mExternalTransition = bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0;
        this.mPending.add(pendingTransition);
        startNextTransition();
    }

    public final PendingTransition findPending(TaskViewTaskController taskViewTaskController, int i) {
        for (int size = this.mPending.size() - 1; size >= 0; size--) {
            if (((PendingTransition) this.mPending.get(size)).mTaskView == taskViewTaskController && ((PendingTransition) this.mPending.get(size)).mExternalTransition == null && ((PendingTransition) this.mPending.get(size)).mType == i) {
                return (PendingTransition) this.mPending.get(size);
            }
        }
        return null;
    }

    public PendingTransition findPendingOpeningTransition(TaskViewTaskController taskViewTaskController) {
        for (int size = this.mPending.size() - 1; size >= 0; size--) {
            if (((PendingTransition) this.mPending.get(size)).mTaskView == taskViewTaskController && ((PendingTransition) this.mPending.get(size)).mExternalTransition == null && TransitionUtil.isOpeningType(((PendingTransition) this.mPending.get(size)).mType)) {
                return (PendingTransition) this.mPending.get(size);
            }
        }
        return null;
    }

    public final TaskViewTaskController findTaskView(ActivityManager.RunningTaskInfo runningTaskInfo) {
        for (TaskViewTaskController taskViewTaskController : ((WeakHashMap) this.mTaskViews).keySet()) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = taskViewTaskController.mTaskInfo;
            if (runningTaskInfo2 != null && runningTaskInfo.token.equals(runningTaskInfo2.token)) {
                return taskViewTaskController;
            }
        }
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        TaskViewTaskController taskViewTaskControllerFindTaskView;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        if (triggerTask == null || (taskViewTaskControllerFindTaskView = findTaskView(triggerTask)) == null || !TransitionUtil.isClosingType(transitionRequestInfo.getType())) {
            return null;
        }
        PendingTransition pendingTransition = new PendingTransition(transitionRequestInfo.getType(), null, taskViewTaskControllerFindTaskView, null);
        pendingTransition.mClaimed = iBinder;
        this.mPending.add(pendingTransition);
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final boolean isUsingShellTransitions() {
        return this.mTransitions.mIsRegistered;
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void moveTaskViewToFullscreen(final TaskViewTaskController taskViewTaskController) {
        final WindowContainerToken windowContainerToken = taskViewTaskController.mTaskToken;
        if (windowContainerToken == null) {
            return;
        }
        final WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setWindowingMode(windowContainerToken, 0);
        windowContainerTransaction.setAlwaysOnTop(windowContainerToken, false);
        this.mShellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.taskview.TaskViewTransitions$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                TaskViewTransitions taskViewTransitions = this.f$0;
                WindowContainerToken windowContainerToken2 = windowContainerToken;
                WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                TaskViewTaskController taskViewTaskController2 = taskViewTaskController;
                taskViewTransitions.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(windowContainerToken2, false);
                taskViewTransitions.mPending.add(new TaskViewTransitions.PendingTransition(6, windowContainerTransaction2, taskViewTaskController2, null));
                taskViewTransitions.startNextTransition();
                taskViewTaskController2.notifyTaskRemovalStarted(taskViewTaskController2.mTaskInfo);
            }
        });
    }

    public final void onExternalDone(IBinder iBinder) {
        PendingTransition pendingTransitionFindPending = findPending(iBinder);
        if (pendingTransitionFindPending == null) {
            return;
        }
        this.mPending.remove(pendingTransitionFindPending);
        startNextTransition();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        PendingTransition pendingTransitionFindPending;
        if (z && (pendingTransitionFindPending = findPending(iBinder)) != null) {
            this.mPending.remove(pendingTransitionFindPending);
            startNextTransition();
        }
    }

    public final void prepareActivityOptions(ActivityOptions activityOptions, Rect rect, TaskViewTaskController taskViewTaskController) {
        Binder binder = new Binder();
        this.mShellExecutor.execute(new TaskViewTransitions$$ExternalSyntheticLambda1(this, binder, taskViewTaskController, 2));
        activityOptions.setLaunchBounds(rect);
        activityOptions.setLaunchCookie(binder);
        activityOptions.setLaunchWindowingMode(6);
        activityOptions.setRemoveWithTaskOrganizer(true);
    }

    public void prepareOpenAnimation(final TaskViewTaskController taskViewTaskController, final boolean z, final SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, WindowContainerTransaction windowContainerTransaction) {
        WindowContainerTransaction windowContainerTransaction2;
        Rect rect = null;
        taskViewTaskController.mPendingInfo = null;
        taskViewTaskController.mTaskInfo = runningTaskInfo;
        taskViewTaskController.mTaskToken = runningTaskInfo.token;
        taskViewTaskController.mTaskLeash = surfaceControl;
        if (taskViewTaskController.mSurfaceCreated) {
            TaskView taskView = taskViewTaskController.mTaskViewBase;
            taskView.getBoundsOnScreen(taskView.mTmpRect);
            rect = taskView.mTmpRect;
        }
        Rect rect2 = rect;
        if (rect2 != null) {
            windowContainerTransaction2 = windowContainerTransaction;
            updateBounds(taskViewTaskController, rect2, transaction, transaction2, runningTaskInfo, surfaceControl, windowContainerTransaction2);
        } else {
            windowContainerTransaction2 = windowContainerTransaction;
            windowContainerTransaction2.setHidden(runningTaskInfo.token, true);
            updateVisibilityState(taskViewTaskController, false);
            Slog.d("TaskViewTransitions", "prepareOpenAnimation: force hidden, tid=" + runningTaskInfo.taskId + ", tv=" + taskViewTaskController + ", leash=" + surfaceControl);
        }
        if (z) {
            this.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(runningTaskInfo.token, true);
            WindowContainerToken windowContainerToken = taskViewTaskController.mTaskToken;
            if (windowContainerToken != null) {
                Context context = taskViewTaskController.mContext;
                if (context instanceof Activity) {
                    windowContainerTransaction2.setTaskViewTaskOrganizerTaskId(windowContainerToken, ((Activity) context).getTaskId());
                }
            }
        }
        ActivityManager.TaskDescription taskDescription = runningTaskInfo.taskDescription;
        if (taskDescription != null) {
            final int backgroundColor = taskDescription.getBackgroundColor();
            final TaskView taskView2 = taskViewTaskController.mTaskViewBase;
            if (taskView2.mHandler.getLooper().isCurrentThread()) {
                taskView2.runOnViewThread(new Runnable() { // from class: com.android.wm.shell.taskview.TaskView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TaskView taskView3 = taskView2;
                        SurfaceControl.Transaction transaction3 = transaction;
                        int i = backgroundColor;
                        int i2 = TaskView.$r8$clinit;
                        taskView3.setResizeBackgroundColor(transaction3, i);
                    }
                });
            } else {
                taskView2.runOnViewThread(new TaskView$$ExternalSyntheticLambda1(taskView2, backgroundColor, 0));
            }
        }
        windowContainerTransaction2.setTaskTrimmableFromRecents(runningTaskInfo.token, false);
        TaskView taskView3 = taskViewTaskController.mTaskViewBase;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = taskViewTaskController.mTaskInfo;
        if (!taskView3.mTaskViewController.isUsingShellTransitions()) {
            taskView3.getBoundsOnScreen(taskView3.mTmpRect);
            taskView3.mTaskViewController.setTaskBounds(taskView3.mTaskViewTaskController, taskView3.mTmpRect);
            ActivityManager.TaskDescription taskDescription2 = runningTaskInfo2.taskDescription;
            if (taskDescription2 != null) {
                taskView3.runOnViewThread(new TaskView$$ExternalSyntheticLambda1(taskView3, taskDescription2.getBackgroundColor(), 1));
            }
        }
        if (taskViewTaskController.mListener != null) {
            ActivityManager.RunningTaskInfo runningTaskInfo3 = taskViewTaskController.mTaskInfo;
            final int i = runningTaskInfo3.taskId;
            final ComponentName componentName = runningTaskInfo3.baseActivity;
            taskViewTaskController.mListenerExecutor.execute(new Runnable() { // from class: com.android.wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    TaskViewTaskController taskViewTaskController2 = taskViewTaskController;
                    boolean z2 = z;
                    int i2 = i;
                    ComponentName componentName2 = componentName;
                    if (z2) {
                        taskViewTaskController2.mListener.onTaskCreated(i2, componentName2);
                    }
                    if (z2 && taskViewTaskController2.mSurfaceCreated) {
                        return;
                    }
                    taskViewTaskController2.mListener.onTaskVisibilityChanged(i2, taskViewTaskController2.mSurfaceCreated);
                }
            });
        }
    }

    public final void recordLogHistory$1(String str) {
        if (this.mLogHistory.size() == 20) {
            this.mLogHistory.removeFirst();
        }
        this.mLogHistory.add("(" + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ") " + str);
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void registerTaskView(TaskViewTaskController taskViewTaskController) {
        synchronized (this.mRegistered) {
            try {
                boolean[] zArr = this.mRegistered;
                if (!zArr[0]) {
                    zArr[0] = true;
                    this.mTransitions.addHandler(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ((WeakHashMap) this.mTaskViews).put(taskViewTaskController, new TaskViewRepository.TaskViewState(null));
        Slog.d("TaskViewTransitions", "registerTaskView: " + taskViewTaskController + ", Callers=" + Debug.getCallers(5));
        StringBuilder sb = new StringBuilder("[Add] ");
        sb.append(taskViewTaskController);
        recordLogHistory$1(sb.toString());
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void removeTaskView(TaskViewTaskController taskViewTaskController, WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            windowContainerToken = taskViewTaskController.mTaskToken;
        }
        if (windowContainerToken == null) {
            unregisterTaskView(taskViewTaskController);
            return;
        }
        Slog.d("TaskViewTransitions", "removeTaskView: " + taskViewTaskController + ", Callers=" + Debug.getCallers(3));
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.removeTask(windowContainerToken);
        updateVisibilityState(taskViewTaskController, false);
        this.mShellExecutor.execute(new TaskViewTransitions$$ExternalSyntheticLambda1(this, windowContainerTransaction, taskViewTaskController, 0));
    }

    public final void setFinishTransaction(SurfaceControl.Transaction transaction) {
        if (this.mFinishTransaction != transaction) {
            this.mFinishTransaction = transaction;
            Slog.d("TaskViewTransitions", "setFinishTransaction: " + transaction);
        }
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void setTaskBounds(TaskViewTaskController taskViewTaskController, Rect rect) {
        if (taskViewTaskController.mTaskToken == null) {
            return;
        }
        if (this.mTransitions.mIsRegistered) {
            this.mShellExecutor.execute(new TaskViewTransitions$$ExternalSyntheticLambda1(this, taskViewTaskController, rect));
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setBounds(taskViewTaskController.mTaskToken, rect);
        this.mSyncQueue.queue(windowContainerTransaction);
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void setTaskViewVisible(TaskViewTaskController taskViewTaskController, boolean z) {
        TaskViewRepository.TaskViewState taskViewState = (TaskViewRepository.TaskViewState) ((WeakHashMap) this.mTaskViews).get(taskViewTaskController);
        if (taskViewState == null || taskViewState.mVisible == z || taskViewTaskController.mTaskInfo == null) {
            return;
        }
        Slog.d("TaskViewTransitions", "setTaskViewVisible: " + z + ", " + taskViewTaskController + ", Callers=" + Debug.getCallers(3));
        taskViewState.mVisible = z;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setHidden(taskViewTaskController.mTaskInfo.token, z ^ true);
        windowContainerTransaction.setBounds(taskViewTaskController.mTaskInfo.token, taskViewState.mBounds);
        this.mPending.add(new PendingTransition(z ? 3 : 4, windowContainerTransaction, taskViewTaskController, null));
        startNextTransition();
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void startActivity(TaskViewTaskController taskViewTaskController, PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, Rect rect) {
        prepareActivityOptions(activityOptions, rect, taskViewTaskController);
        if (this.mTransitions.mIsRegistered) {
            this.mShellExecutor.execute(new TaskViewTransitions$$ExternalSyntheticLambda3(this, pendingIntent, intent, activityOptions, taskViewTaskController));
            return;
        }
        try {
            pendingIntent.send(taskViewTaskController.mContext, 0, intent, null, null, null, activityOptions.toBundle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo transitionInfo2;
        SurfaceControl.Transaction transaction3;
        SurfaceControl.Transaction transaction4;
        int i;
        int i2;
        Rect rect;
        int i3;
        TaskViewTaskController taskViewTaskController;
        boolean z;
        final PendingTransition pendingTransitionFindPending = findPending(iBinder);
        if (pendingTransitionFindPending == null) {
            transitionInfo2 = transitionInfo;
            transaction3 = transaction;
            transaction4 = transaction2;
        } else {
            if ((transitionInfo.getFlags() & 8256) == 8256 && !((WeakHashMap) this.mTaskViews).isEmpty() && this.mMixedHandler != null) {
                Slog.d("TaskViewTransitions", "startAnimation: Keyguard unoccluding with taskView, " + this.mTaskViews);
                DefaultMixedHandler defaultMixedHandler = this.mMixedHandler;
                DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition = defaultMixedHandler.createDefaultMixedTransition(iBinder, 101);
                defaultMixedTransitionCreateDefaultMixedTransition.mTaskViewTransitions = defaultMixedHandler.mTaskViewTransitions;
                defaultMixedHandler.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition);
                defaultMixedTransitionCreateDefaultMixedTransition.startAnimation(iBinder, transitionInfo, transaction, transaction2, new DefaultMixedHandler$$ExternalSyntheticLambda4(defaultMixedHandler, defaultMixedTransitionCreateDefaultMixedTransition, transitionFinishCallback, 4));
                return true;
            }
            transitionInfo2 = transitionInfo;
            transaction3 = transaction;
            transaction4 = transaction2;
            this.mPending.remove(pendingTransitionFindPending);
        }
        if (pendingTransitionFindPending != null && pendingTransitionFindPending.mRemoveTaskViewRequested) {
            StringBuilder sb = new StringBuilder("startAnimation: TaskView is already removed, pending=");
            sb.append(pendingTransitionFindPending);
            sb.append(", tv=");
            TaskViewTaskController taskViewTaskController2 = pendingTransitionFindPending.mTaskView;
            sb.append(taskViewTaskController2);
            sb.append(", list=");
            sb.append(this.mTaskViews);
            Slog.w("TaskViewTransitions", sb.toString());
            if (pendingTransitionFindPending.mRemoveTaskViewRequested) {
                final int i4 = 0;
                TransitionInfo.Change changeFindChange = transitionInfo2.findChange(new Predicate() { // from class: com.android.wm.shell.taskview.TaskViewTransitions$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i5 = i4;
                        Object obj2 = pendingTransitionFindPending;
                        switch (i5) {
                            case 0:
                                TransitionInfo.Change change = (TransitionInfo.Change) obj;
                                return change.getMode() == 1 && change.getTaskInfo() != null && change.getTaskInfo().containsLaunchCookie(((TaskViewTransitions.PendingTransition) obj2).mLaunchCookie);
                            default:
                                TransitionInfo.Change change2 = (TransitionInfo.Change) obj;
                                return change2.getEndDisplayId() == ((TransitionInfo.Change) obj2).getEndDisplayId() && change2.getStartRotation() != change2.getEndRotation() && change2.hasFlags(32) && change2.getSnapshot() != null;
                        }
                    }
                });
                if (changeFindChange != null) {
                    Slog.e("TaskViewTransitions", "cleanUpTaskViewTaskIfNeeded: failed to handle open transition, reason=task_view_removed, clean up " + changeFindChange + ", pending=" + pendingTransitionFindPending + ", tv=" + taskViewTaskController2);
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.removeTask(changeFindChange.getTaskInfo().token);
                    this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                    return false;
                }
            }
        }
        if (((WeakHashMap) this.mTaskViews).isEmpty()) {
            if (pendingTransitionFindPending == null) {
                return false;
            }
            Slog.e("TaskViewTransitions", "Pending taskview transition but no task-views");
            return false;
        }
        boolean z2 = (pendingTransitionFindPending == null || pendingTransitionFindPending.mLaunchCookie == null) ? false : true;
        int i5 = 0;
        int i6 = 0;
        WindowContainerTransaction windowContainerTransaction2 = null;
        while (i5 < transitionInfo2.getChanges().size()) {
            final TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo2.getChanges().get(i5);
            int i7 = i6;
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null) {
                if (TransitionUtil.isClosingType(change.getMode())) {
                    boolean z3 = change.getMode() == 4;
                    TaskViewTaskController taskViewTaskControllerFindTaskView = findTaskView(taskInfo);
                    if (taskViewTaskControllerFindTaskView == null && !z3) {
                        if (pendingTransitionFindPending == null) {
                            Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. And non-pending transition. Is it real taskView? " + change.getTaskInfo().taskId);
                            i = i5;
                            i2 = i7;
                        }
                        i6 = i7 + 1;
                        i = i5;
                    } else if (taskViewTaskControllerFindTaskView == null) {
                        if (pendingTransitionFindPending != null) {
                            Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. This shouldn't happen, so there may be a visual artifact: " + taskInfo.taskId);
                        }
                        i = i5;
                        i2 = i7;
                    } else {
                        if (z3) {
                            if (pendingTransitionFindPending != null && pendingTransitionFindPending.mType == 4) {
                                transaction3.hide(change.getLeash());
                            }
                            if (taskViewTaskControllerFindTaskView.mTaskToken != null) {
                                transaction4.reparent(taskViewTaskControllerFindTaskView.mTaskLeash, null);
                                TaskView.Listener listener = taskViewTaskControllerFindTaskView.mListener;
                                if (listener != null) {
                                    listener.onTaskVisibilityChanged(taskViewTaskControllerFindTaskView.mTaskInfo.taskId, taskViewTaskControllerFindTaskView.mSurfaceCreated);
                                }
                            }
                        } else {
                            ActivityManager.RunningTaskInfo runningTaskInfo = taskViewTaskControllerFindTaskView.mTaskInfo;
                            if (runningTaskInfo != null) {
                                taskViewTaskControllerFindTaskView.notifyTaskRemovalStarted(runningTaskInfo);
                                taskViewTaskControllerFindTaskView.mTaskViewBase.getClass();
                            }
                            taskViewTaskControllerFindTaskView.resetTaskInfo();
                        }
                        i6 = i7 + 1;
                        i = i5;
                    }
                } else if (TransitionUtil.isOpeningType(change.getMode())) {
                    if (change.getMode() != 1) {
                        TaskViewTaskController taskViewTaskControllerFindTaskView2 = findTaskView(taskInfo);
                        if (taskViewTaskControllerFindTaskView2 == null && pendingTransitionFindPending != null) {
                            Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. This shouldn't happen, so there may be a visual artifact: " + taskInfo.taskId);
                        }
                        if (taskViewTaskControllerFindTaskView2 == null) {
                            i = i5;
                            i6 = i7;
                        } else {
                            i3 = i5;
                            taskViewTaskController = taskViewTaskControllerFindTaskView2;
                            z = false;
                        }
                    } else if (pendingTransitionFindPending == null || !taskInfo.containsLaunchCookie(pendingTransitionFindPending.mLaunchCookie)) {
                        Slog.e("TaskViewTransitions", "Found a launching TaskView in the wrong transition. All TaskView launches should be initiated by shell and in their own transition: " + taskInfo.taskId);
                        i = i5;
                        i2 = i7;
                    } else {
                        i3 = i5;
                        taskViewTaskController = pendingTransitionFindPending.mTaskView;
                        z = true;
                        z2 = false;
                    }
                    if (windowContainerTransaction2 == null) {
                        windowContainerTransaction2 = new WindowContainerTransaction();
                    }
                    WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction2;
                    i = i3;
                    prepareOpenAnimation(taskViewTaskController, z, transaction3, transaction4, taskInfo, change.getLeash(), windowContainerTransaction3);
                    i6 = i7 + 1;
                    transaction3 = transaction;
                    transaction4 = transaction2;
                    windowContainerTransaction2 = windowContainerTransaction3;
                } else {
                    i = i5;
                    i2 = i7;
                    if (change.getMode() == 6) {
                        TaskViewTaskController taskViewTaskControllerFindTaskView3 = findTaskView(taskInfo);
                        if (taskViewTaskControllerFindTaskView3 != null) {
                            ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                            SurfaceControl leash = change.getLeash();
                            taskViewTaskControllerFindTaskView3.mPendingInfo = null;
                            taskViewTaskControllerFindTaskView3.mTaskInfo = taskInfo2;
                            taskViewTaskControllerFindTaskView3.mTaskToken = taskInfo2.token;
                            taskViewTaskControllerFindTaskView3.mTaskLeash = leash;
                            if (taskViewTaskControllerFindTaskView3.mSurfaceCreated) {
                                TaskView taskView = taskViewTaskControllerFindTaskView3.mTaskViewBase;
                                taskView.getBoundsOnScreen(taskView.mTmpRect);
                                rect = taskView.mTmpRect;
                            } else {
                                rect = null;
                            }
                            if (rect != null) {
                                final int i8 = 1;
                                if (transitionInfo2.findChange(new Predicate() { // from class: com.android.wm.shell.taskview.TaskViewTransitions$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        int i52 = i8;
                                        Object obj2 = change;
                                        switch (i52) {
                                            case 0:
                                                TransitionInfo.Change change2 = (TransitionInfo.Change) obj;
                                                return change2.getMode() == 1 && change2.getTaskInfo() != null && change2.getTaskInfo().containsLaunchCookie(((TaskViewTransitions.PendingTransition) obj2).mLaunchCookie);
                                            default:
                                                TransitionInfo.Change change22 = (TransitionInfo.Change) obj;
                                                return change22.getEndDisplayId() == ((TransitionInfo.Change) obj2).getEndDisplayId() && change22.getStartRotation() != change22.getEndRotation() && change22.hasFlags(32) && change22.getSnapshot() != null;
                                        }
                                    }
                                }) != null) {
                                    Slog.d("TaskViewTransitions", "startAnimation: clear boundsOnScreen=" + rect + ", tv=" + taskViewTaskControllerFindTaskView3);
                                    rect = null;
                                }
                            }
                            if (rect != null) {
                                if (windowContainerTransaction2 == null) {
                                    windowContainerTransaction2 = new WindowContainerTransaction();
                                }
                                WindowContainerTransaction windowContainerTransaction4 = windowContainerTransaction2;
                                transaction4 = transaction2;
                                Rect rect2 = rect;
                                transaction3 = transaction;
                                updateBounds(taskViewTaskControllerFindTaskView3, rect2, transaction3, transaction4, change.getTaskInfo(), change.getLeash(), windowContainerTransaction4);
                                windowContainerTransaction2 = windowContainerTransaction4;
                            } else {
                                transaction3 = transaction;
                                transaction4 = transaction2;
                                transaction3.reparent(change.getLeash(), taskViewTaskControllerFindTaskView3.mSurfaceControl);
                                if (((WeakHashMap) this.mTaskViews).get(taskViewTaskControllerFindTaskView3) != null) {
                                    Rect rect3 = new Rect(((TaskViewRepository.TaskViewState) ((WeakHashMap) this.mTaskViews).get(taskViewTaskControllerFindTaskView3)).mBounds);
                                    rect3.offsetTo(0, 0);
                                    transaction3.setPosition(change.getLeash(), 0.0f, 0.0f).setCrop(change.getLeash(), rect3);
                                    transaction4.setPosition(change.getLeash(), 0.0f, 0.0f).setCrop(change.getLeash(), rect3);
                                    setFinishTransaction(transaction4);
                                    change.setSkipDefaultTransition(true);
                                }
                                transaction4.reparent(change.getLeash(), taskViewTaskControllerFindTaskView3.mSurfaceControl).setPosition(change.getLeash(), 0.0f, 0.0f);
                            }
                            i6 = i2 + 1;
                        } else if (pendingTransitionFindPending != null) {
                            Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. This shouldn't happen, so there may be a visual artifact: " + taskInfo.taskId);
                        }
                    }
                    transaction3 = transaction;
                    transaction4 = transaction2;
                }
                i6 = i2;
            } else {
                i = i5;
                i2 = i7;
                i6 = i2;
            }
            i5 = i + 1;
        }
        int i9 = i6;
        if (z2) {
            Slog.w("TaskViewTransitions", "Expected a TaskView launch in this transition but didn't get one, cleaning up the task view");
            TaskViewTaskController taskViewTaskController3 = pendingTransitionFindPending.mTaskView;
            taskViewTaskController3.mTaskNotFound = true;
            ActivityManager.RunningTaskInfo runningTaskInfo2 = taskViewTaskController3.mPendingInfo;
            if (runningTaskInfo2 != null) {
                taskViewTaskController3.notifyTaskRemovalStarted(runningTaskInfo2);
                taskViewTaskController3.mTaskViewBase.getClass();
                taskViewTaskController3.mTaskViewController.removeTaskView(taskViewTaskController3, runningTaskInfo2.token);
                taskViewTaskController3.resetTaskInfo();
            }
        } else {
            if (windowContainerTransaction2 == null && pendingTransitionFindPending == null && i9 != transitionInfo2.getChanges().size()) {
                return false;
            }
            if (windowContainerTransaction2 == null && pendingTransitionFindPending == null && i9 == 0) {
                Slog.e("TaskViewTransitions", "startAnimation: failed, taskViews=" + this.mTaskViews);
                return false;
            }
        }
        transaction3.apply();
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction2);
        startNextTransition();
        return true;
    }

    public final void startNextTransition() {
        if (this.mPending.isEmpty()) {
            return;
        }
        PendingTransition pendingTransition = (PendingTransition) this.mPending.get(0);
        if (pendingTransition.mClaimed != null) {
            return;
        }
        BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0 bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0 = pendingTransition.mExternalTransition;
        if (bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0 != null) {
            pendingTransition.mClaimed = bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0.start();
        } else {
            pendingTransition.mClaimed = this.mTransitions.startTransition(pendingTransition.mType, pendingTransition.mWct, this);
        }
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void startShortcutActivity(TaskViewTaskController taskViewTaskController, ShortcutInfo shortcutInfo, ActivityOptions activityOptions, Rect rect) {
        prepareActivityOptions(activityOptions, rect, taskViewTaskController);
        Context context = taskViewTaskController.mContext;
        if (this.mTransitions.mIsRegistered) {
            this.mShellExecutor.execute(new TaskViewTransitions$$ExternalSyntheticLambda3(this, context, shortcutInfo, activityOptions, taskViewTaskController));
            return;
        }
        try {
            ((LauncherApps) context.getSystemService(LauncherApps.class)).startShortcut(shortcutInfo, null, activityOptions.toBundle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startTaskView(WindowContainerTransaction windowContainerTransaction, TaskViewTaskController taskViewTaskController, IBinder iBinder) {
        Slog.d("TaskViewTransitions", "startTaskView: " + taskViewTaskController + ", Callers=" + Debug.getCallers(3));
        updateVisibilityState(taskViewTaskController, true);
        this.mPending.add(new PendingTransition(1, windowContainerTransaction, taskViewTaskController, iBinder));
        startNextTransition();
    }

    @Override // com.android.wm.shell.taskview.TaskViewController
    public final void unregisterTaskView(TaskViewTaskController taskViewTaskController) {
        ((WeakHashMap) this.mTaskViews).remove(taskViewTaskController);
        PendingTransition pendingTransitionFindPending = findPending(taskViewTaskController, 1);
        if (pendingTransitionFindPending != null) {
            pendingTransitionFindPending.mRemoveTaskViewRequested = true;
        }
        Slog.d("TaskViewTransitions", "unregisterTaskView: " + taskViewTaskController + ", pendingTransit=" + pendingTransitionFindPending + ", Callers=" + Debug.getCallers(5));
        StringBuilder sb = new StringBuilder("[Remove] ");
        sb.append(taskViewTaskController);
        recordLogHistory$1(sb.toString());
    }

    public final void updateBounds(TaskViewTaskController taskViewTaskController, Rect rect, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, WindowContainerTransaction windowContainerTransaction) {
        SurfaceControl surfaceControl2 = taskViewTaskController.mSurfaceControl;
        transaction.reparent(surfaceControl, surfaceControl2).show(surfaceControl);
        if (transaction2 != null) {
            transaction2.reparent(surfaceControl, surfaceControl2).setPosition(surfaceControl, 0.0f, 0.0f).setWindowCrop(surfaceControl, rect.width(), rect.height());
        }
        TaskViewRepository.TaskViewState taskViewState = (TaskViewRepository.TaskViewState) ((WeakHashMap) this.mTaskViews).get(taskViewTaskController);
        if (taskViewState != null) {
            taskViewState.mBounds.set(rect);
        }
        updateVisibilityState(taskViewTaskController, true);
        windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
        taskViewTaskController.applyCaptionInsetsIfNeeded();
    }

    public final void updateVisibilityState(TaskViewTaskController taskViewTaskController, boolean z) {
        TaskViewRepository.TaskViewState taskViewState = (TaskViewRepository.TaskViewState) ((WeakHashMap) this.mTaskViews).get(taskViewTaskController);
        if (taskViewState == null) {
            return;
        }
        taskViewState.mVisible = z;
    }

    public final PendingTransition findPending(IBinder iBinder) {
        for (int i = 0; i < this.mPending.size(); i++) {
            if (((PendingTransition) this.mPending.get(i)).mClaimed == iBinder) {
                return (PendingTransition) this.mPending.get(i);
            }
        }
        return null;
    }
}
