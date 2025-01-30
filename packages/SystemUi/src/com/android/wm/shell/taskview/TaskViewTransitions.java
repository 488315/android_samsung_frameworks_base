package com.android.wm.shell.taskview;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.TransitionUtil;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskViewTransitions implements Transitions.TransitionHandler {
    public final Transitions mTransitions;
    public final ArrayMap mTaskViews = new ArrayMap();
    public final ArrayList mPending = new ArrayList();
    public final boolean[] mRegistered = {false};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class PendingTransition {
        public IBinder mClaimed;
        public final IBinder mLaunchCookie;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TaskViewRequestedState {
        public final Rect mBounds;
        public boolean mVisible;

        public /* synthetic */ TaskViewRequestedState(int i) {
            this();
        }

        private TaskViewRequestedState() {
            this.mBounds = new Rect();
        }
    }

    public TaskViewTransitions(Transitions transitions) {
        this.mTransitions = transitions;
    }

    public final PendingTransition findPending(IBinder iBinder) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mPending;
            if (i >= arrayList.size()) {
                return null;
            }
            if (((PendingTransition) arrayList.get(i)).mClaimed == iBinder) {
                return (PendingTransition) arrayList.get(i);
            }
            i++;
        }
    }

    public PendingTransition findPendingOpeningTransition(TaskViewTaskController taskViewTaskController) {
        ArrayList arrayList = this.mPending;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((PendingTransition) arrayList.get(size)).mTaskView == taskViewTaskController && TransitionUtil.isOpeningType(((PendingTransition) arrayList.get(size)).mType)) {
                return (PendingTransition) arrayList.get(size);
            }
        }
        return null;
    }

    public final TaskViewTaskController findTaskView(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = 0;
        while (true) {
            ArrayMap arrayMap = this.mTaskViews;
            if (i >= arrayMap.size()) {
                return null;
            }
            if (((TaskViewTaskController) arrayMap.keyAt(i)).mTaskInfo != null && runningTaskInfo.token.equals(((TaskViewTaskController) arrayMap.keyAt(i)).mTaskInfo.token)) {
                return (TaskViewTaskController) arrayMap.keyAt(i);
            }
            i++;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        TaskViewTaskController findTaskView;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        if (triggerTask == null || (findTaskView = findTaskView(triggerTask)) == null || !TransitionUtil.isClosingType(transitionRequestInfo.getType())) {
            return null;
        }
        PendingTransition pendingTransition = new PendingTransition(transitionRequestInfo.getType(), null, findTaskView, null);
        pendingTransition.mClaimed = iBinder;
        this.mPending.add(pendingTransition);
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        PendingTransition findPending;
        if (z && (findPending = findPending(iBinder)) != null) {
            this.mPending.remove(findPending);
            startNextTransition();
        }
    }

    public final void setTaskViewVisible(TaskViewTaskController taskViewTaskController, boolean z) {
        ArrayMap arrayMap = this.mTaskViews;
        if (arrayMap.get(taskViewTaskController) == null || ((TaskViewRequestedState) arrayMap.get(taskViewTaskController)).mVisible == z || taskViewTaskController.mTaskInfo == null) {
            return;
        }
        ((TaskViewRequestedState) arrayMap.get(taskViewTaskController)).mVisible = z;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setHidden(taskViewTaskController.mTaskInfo.token, !z);
        windowContainerTransaction.setBounds(taskViewTaskController.mTaskInfo.token, ((TaskViewRequestedState) arrayMap.get(taskViewTaskController)).mBounds);
        this.mPending.add(new PendingTransition(z ? 3 : 4, windowContainerTransaction, taskViewTaskController, null));
        startNextTransition();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01b5  */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.app.ActivityManager$RunningTaskInfo, android.view.SurfaceControl] */
    /* JADX WARN: Type inference failed for: r9v2 */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int i;
        float f;
        boolean z;
        final TaskViewTaskController taskViewTaskController;
        boolean z2;
        boolean z3;
        boolean z4;
        ActivityManager.TaskDescription taskDescription;
        PendingTransition findPending = findPending(iBinder);
        if (findPending != null) {
            this.mPending.remove(findPending);
        }
        ArrayMap arrayMap = this.mTaskViews;
        boolean z5 = false;
        if (arrayMap.isEmpty()) {
            if (findPending != null) {
                Slog.e("TaskViewTransitions", "Pending taskview transition but no task-views");
            }
            return false;
        }
        boolean z6 = (findPending == null || findPending.mLaunchCookie == null) ? false : true;
        ?? r9 = 0;
        int i2 = 0;
        int i3 = 0;
        WindowContainerTransaction windowContainerTransaction = null;
        while (i2 < transitionInfo.getChanges().size()) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i2);
            if (change.getTaskInfo() != null) {
                if (TransitionUtil.isClosingType(change.getMode())) {
                    boolean z7 = change.getMode() == 4 ? true : z5;
                    TaskViewTaskController findTaskView = findTaskView(change.getTaskInfo());
                    if (findTaskView != null || z7) {
                        if (findTaskView == null) {
                            if (findPending != null) {
                                Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. This shouldn't happen, so there may be a visual artifact: " + change.getTaskInfo().taskId);
                            }
                        } else if (z7) {
                            if (findPending != null && findPending.mType == 4) {
                                transaction.hide(change.getLeash());
                            }
                            if (findTaskView.mTaskToken != null) {
                                transaction2.reparent(findTaskView.mTaskLeash, r9);
                                TaskView.Listener listener = findTaskView.mListener;
                                if (listener != null) {
                                    int i4 = findTaskView.mTaskInfo.taskId;
                                    listener.onTaskVisibilityChanged(findTaskView.mSurfaceCreated);
                                }
                            }
                        } else {
                            if (findTaskView.mTaskToken != null) {
                                if (findTaskView.mListener != null) {
                                    findTaskView.mListenerExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda1(findTaskView, findTaskView.mTaskInfo.taskId, 4));
                                }
                                findTaskView.mTaskViewBase.getClass();
                                findTaskView.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(findTaskView.mTaskToken, false);
                            }
                            findTaskView.resetTaskInfo();
                        }
                    }
                    i3++;
                } else {
                    if (TransitionUtil.isOpeningType(change.getMode())) {
                        final boolean z8 = change.getMode() == 1;
                        if (!z8) {
                            TaskViewTaskController findTaskView2 = findTaskView(change.getTaskInfo());
                            if (findTaskView2 != null) {
                                z = z6;
                                taskViewTaskController = findTaskView2;
                                if (windowContainerTransaction == null) {
                                }
                                ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                                SurfaceControl leash = change.getLeash();
                                taskViewTaskController.mPendingInfo = r9;
                                taskViewTaskController.mTaskInfo = taskInfo;
                                WindowContainerToken windowContainerToken = taskInfo.token;
                                taskViewTaskController.mTaskToken = windowContainerToken;
                                taskViewTaskController.mTaskLeash = leash;
                                z2 = taskViewTaskController.mSurfaceCreated;
                                TaskViewTransitions taskViewTransitions = taskViewTaskController.mTaskViewTransitions;
                                if (z2) {
                                }
                                if (z8) {
                                }
                                taskDescription = taskViewTaskController.mTaskInfo.taskDescription;
                                if (taskDescription != null) {
                                }
                                if (taskViewTaskController.mListener != null) {
                                }
                                i3++;
                                z6 = z3;
                            } else if (findPending != null) {
                                Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. This shouldn't happen, so there may be a visual artifact: " + change.getTaskInfo().taskId);
                            }
                        } else if (findPending == null || !change.getTaskInfo().containsLaunchCookie(findPending.mLaunchCookie)) {
                            Slog.e("TaskViewTransitions", "Found a launching TaskView in the wrong transition. All TaskView launches should be initiated by shell and in their own transition: " + change.getTaskInfo().taskId);
                        } else {
                            taskViewTaskController = findPending.mTaskView;
                            z = false;
                            if (windowContainerTransaction == null) {
                                windowContainerTransaction = new WindowContainerTransaction();
                            }
                            ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                            SurfaceControl leash2 = change.getLeash();
                            taskViewTaskController.mPendingInfo = r9;
                            taskViewTaskController.mTaskInfo = taskInfo2;
                            WindowContainerToken windowContainerToken2 = taskInfo2.token;
                            taskViewTaskController.mTaskToken = windowContainerToken2;
                            taskViewTaskController.mTaskLeash = leash2;
                            z2 = taskViewTaskController.mSurfaceCreated;
                            TaskViewTransitions taskViewTransitions2 = taskViewTaskController.mTaskViewTransitions;
                            if (z2) {
                                i = i2;
                                z3 = z;
                                z4 = true;
                                windowContainerTransaction.setHidden(windowContainerToken2, true);
                                taskViewTransitions2.updateVisibilityState(taskViewTaskController, false);
                            } else {
                                transaction.reparent(leash2, taskViewTaskController.mSurfaceControl).show(taskViewTaskController.mTaskLeash);
                                TaskView taskView = (TaskView) taskViewTaskController.mTaskViewBase;
                                taskView.getBoundsOnScreen(taskView.mTmpRect);
                                Rect rect = taskView.mTmpRect;
                                z3 = z;
                                i = i2;
                                transaction2.reparent(taskViewTaskController.mTaskLeash, taskViewTaskController.mSurfaceControl).setPosition(taskViewTaskController.mTaskLeash, 0.0f, 0.0f).setWindowCrop(taskViewTaskController.mTaskLeash, rect.width(), rect.height());
                                TaskViewRequestedState taskViewRequestedState = (TaskViewRequestedState) taskViewTransitions2.mTaskViews.get(taskViewTaskController);
                                if (taskViewRequestedState != null) {
                                    taskViewRequestedState.mBounds.set(rect);
                                }
                                z4 = true;
                                taskViewTransitions2.updateVisibilityState(taskViewTaskController, true);
                                windowContainerTransaction.setBounds(taskViewTaskController.mTaskToken, rect);
                            }
                            if (z8) {
                                taskViewTaskController.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(taskViewTaskController.mTaskToken, z4);
                            }
                            taskDescription = taskViewTaskController.mTaskInfo.taskDescription;
                            if (taskDescription != null) {
                                ((TaskView) taskViewTaskController.mTaskViewBase).setResizeBackgroundColor(transaction, taskDescription.getBackgroundColor());
                            }
                            if (taskViewTaskController.mListener != null) {
                                ActivityManager.RunningTaskInfo runningTaskInfo = taskViewTaskController.mTaskInfo;
                                final int i5 = runningTaskInfo.taskId;
                                final ComponentName componentName = runningTaskInfo.baseActivity;
                                taskViewTaskController.mListenerExecutor.execute(new Runnable() { // from class: com.android.wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda6
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TaskViewTaskController taskViewTaskController2 = TaskViewTaskController.this;
                                        boolean z9 = z8;
                                        int i6 = i5;
                                        if (z9) {
                                            taskViewTaskController2.mListener.onTaskCreated(i6);
                                        }
                                        if (z9 && taskViewTaskController2.mSurfaceCreated) {
                                            return;
                                        }
                                        taskViewTaskController2.mListener.onTaskVisibilityChanged(taskViewTaskController2.mSurfaceCreated);
                                    }
                                });
                            }
                            i3++;
                            z6 = z3;
                        }
                    } else {
                        i = i2;
                        if (change.getMode() == 6) {
                            TaskViewTaskController findTaskView3 = findTaskView(change.getTaskInfo());
                            if (findTaskView3 != null) {
                                transaction.reparent(change.getLeash(), findTaskView3.mSurfaceControl);
                                if (arrayMap.get(findTaskView3) != null) {
                                    Rect rect2 = new Rect(((TaskViewRequestedState) arrayMap.get(findTaskView3)).mBounds);
                                    rect2.offsetTo(0, 0);
                                    f = 0.0f;
                                    transaction.setPosition(change.getLeash(), 0.0f, 0.0f);
                                    transaction.setCrop(change.getLeash(), rect2);
                                    change.setSkipDefaultTransition(true);
                                } else {
                                    f = 0.0f;
                                }
                                transaction2.reparent(change.getLeash(), findTaskView3.mSurfaceControl).setPosition(change.getLeash(), f, f);
                                i3++;
                            } else if (findPending != null) {
                                Slog.w("TaskViewTransitions", "Found a non-TaskView task in a TaskView Transition. This shouldn't happen, so there may be a visual artifact: " + change.getTaskInfo().taskId);
                            }
                        }
                    }
                    i2 = i + 1;
                    z5 = false;
                    r9 = 0;
                }
            }
            i = i2;
            i2 = i + 1;
            z5 = false;
            r9 = 0;
        }
        if (z6) {
            Slog.w("TaskViewTransitions", "Expected a TaskView launch in this transition but didn't get one, cleaning up the task view");
            TaskViewTaskController taskViewTaskController2 = findPending.mTaskView;
            taskViewTaskController2.mTaskNotFound = true;
            if (taskViewTaskController2.mPendingInfo != null) {
                taskViewTaskController2.cleanUpPendingTask();
            }
        } else if (windowContainerTransaction == null && findPending == null && i3 != transitionInfo.getChanges().size()) {
            return false;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && KeyguardTransitionHandler.handles(transitionInfo)) {
            Slog.d("TaskViewTransitions", "Don't consume to handle keyguard transition");
            return false;
        }
        transaction.apply();
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction, null);
        startNextTransition();
        return true;
    }

    public final void startNextTransition() {
        ArrayList arrayList = this.mPending;
        if (arrayList.isEmpty()) {
            return;
        }
        PendingTransition pendingTransition = (PendingTransition) arrayList.get(0);
        if (pendingTransition.mClaimed != null) {
            return;
        }
        pendingTransition.mClaimed = this.mTransitions.startTransition(pendingTransition.mType, pendingTransition.mWct, this);
    }

    public final void updateVisibilityState(TaskViewTaskController taskViewTaskController, boolean z) {
        TaskViewRequestedState taskViewRequestedState = (TaskViewRequestedState) this.mTaskViews.get(taskViewTaskController);
        if (taskViewRequestedState == null) {
            return;
        }
        taskViewRequestedState.mVisible = z;
    }
}
