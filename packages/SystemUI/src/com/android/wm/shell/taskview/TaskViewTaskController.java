package com.android.wm.shell.taskview;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Debug;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.CloseGuard;
import android.util.Log;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.taskview.TaskView;
import com.samsung.android.nexus.video.BuildConfig;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskViewTaskController implements ShellTaskOrganizer.TaskListener {
    public Rect mCaptionInsets;
    public final Binder mCaptionInsetsOwner;
    public final Context mContext;
    public final boolean mCreatedByTaskViewFactory;
    public final CloseGuard mGuard;
    public final boolean mHideTaskWithSurface;
    public TaskView.Listener mListener;
    public Executor mListenerExecutor;
    public boolean mNotifiedForInitialized;
    public ActivityManager.RunningTaskInfo mPendingInfo;
    public final Executor mShellExecutor;
    public SurfaceControl mSurfaceControl;
    public boolean mSurfaceCreated;
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public SurfaceControl mTaskLeash;
    public boolean mTaskNotFound;
    public final ShellTaskOrganizer mTaskOrganizer;
    public WindowContainerToken mTaskToken;
    public TaskView mTaskViewBase;
    public final TaskViewController mTaskViewController;
    public final SurfaceControl.Transaction mTransaction;

    public TaskViewTaskController(Context context, ShellTaskOrganizer shellTaskOrganizer, TaskViewController taskViewController, SyncTransactionQueue syncTransactionQueue) {
        this(context, shellTaskOrganizer, taskViewController, syncTransactionQueue, false);
    }

    public final void applyCaptionInsetsIfNeeded() {
        if (this.mTaskToken == null) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (this.mCaptionInsets != null) {
            windowContainerTransaction.addInsetsSource(this.mTaskToken, this.mCaptionInsetsOwner, 0, WindowInsets.Type.captionBar(), this.mCaptionInsets, (Rect[]) null, 0);
        } else {
            windowContainerTransaction.removeInsetsSource(this.mTaskToken, this.mCaptionInsetsOwner, 0, WindowInsets.Type.captionBar());
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        builder.setParent(findTaskSurface$4(i));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump$2(PrintWriter printWriter, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        printWriter.println(str + this);
    }

    public final void finalize() {
        try {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
                performRelease();
            }
        } finally {
            super.finalize();
        }
    }

    public final SurfaceControl findTaskSurface$4(int i) {
        SurfaceControl surfaceControl;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (runningTaskInfo == null || (surfaceControl = this.mTaskLeash) == null || runningTaskInfo.taskId != i) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "There is no surface for taskId="));
        }
        return surfaceControl;
    }

    public ActivityManager.RunningTaskInfo getPendingInfo() {
        return this.mPendingInfo;
    }

    public final void notifyTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mListener == null) {
            return;
        }
        this.mListenerExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda4(this, runningTaskInfo.taskId, 1));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
        WindowContainerToken windowContainerToken = this.mTaskToken;
        if (windowContainerToken == null || !windowContainerToken.equals(runningTaskInfo.token) || this.mListener == null) {
            return;
        }
        this.mListenerExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda4(this, runningTaskInfo.taskId, 0));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(final ActivityManager.RunningTaskInfo runningTaskInfo, final SurfaceControl surfaceControl) {
        TaskViewController taskViewController = this.mTaskViewController;
        if (taskViewController.isUsingShellTransitions()) {
            this.mPendingInfo = runningTaskInfo;
            if (this.mTaskNotFound) {
                if (runningTaskInfo != null) {
                    notifyTaskRemovalStarted(runningTaskInfo);
                    this.mTaskViewBase.getClass();
                    taskViewController.removeTaskView(this, runningTaskInfo.token);
                }
                resetTaskInfo();
                return;
            }
            return;
        }
        this.mTaskInfo = runningTaskInfo;
        this.mTaskToken = runningTaskInfo.token;
        this.mTaskLeash = surfaceControl;
        if (this.mSurfaceCreated) {
            this.mTransaction.reparent(surfaceControl, this.mSurfaceControl).show(this.mTaskLeash).apply();
        } else {
            updateTaskVisibility();
        }
        this.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(this.mTaskToken, true);
        this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda7
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                TaskView taskView = TaskViewTaskController.this.mTaskViewBase;
                if (taskView.mTaskViewController.isUsingShellTransitions()) {
                    return;
                }
                taskView.getBoundsOnScreen(taskView.mTmpRect);
                taskView.mTaskViewController.setTaskBounds(taskView.mTaskViewTaskController, taskView.mTmpRect);
                ActivityManager.TaskDescription taskDescription = runningTaskInfo2.taskDescription;
                if (taskDescription != null) {
                    taskView.runOnViewThread(new TaskView$$ExternalSyntheticLambda1(taskView, taskDescription.getBackgroundColor(), 1));
                }
            }
        });
        if (this.mListener != null) {
            final int i = runningTaskInfo.taskId;
            final ComponentName componentName = runningTaskInfo.baseActivity;
            this.mListenerExecutor.execute(new Runnable() { // from class: com.android.wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    TaskViewTaskController taskViewTaskController = TaskViewTaskController.this;
                    taskViewTaskController.mListener.onTaskCreated(i, componentName);
                }
            });
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        TaskView taskView = this.mTaskViewBase;
        taskView.getClass();
        ActivityManager.TaskDescription taskDescription = runningTaskInfo.taskDescription;
        if (taskDescription != null) {
            taskView.runOnViewThread(new TaskView$$ExternalSyntheticLambda1(taskView, taskDescription.getBackgroundColor(), 2));
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        WindowContainerToken windowContainerToken = this.mTaskToken;
        if (windowContainerToken == null || !windowContainerToken.equals(runningTaskInfo.token)) {
            return;
        }
        SurfaceControl surfaceControl = this.mTaskLeash;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
        if (runningTaskInfo2 != null) {
            notifyTaskRemovalStarted(runningTaskInfo2);
            this.mTaskViewBase.getClass();
        }
        this.mTransaction.reparent(surfaceControl, null).apply();
        resetTaskInfo();
    }

    public final void performRelease() {
        this.mShellExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(this, 4));
        this.mGuard.close();
        if (this.mListener == null || !this.mNotifiedForInitialized) {
            return;
        }
        this.mListenerExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(this, 5));
        this.mNotifiedForInitialized = false;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, findTaskSurface$4(i));
    }

    public final void resetTaskInfo() {
        if (this.mTaskToken != null) {
            Slog.d("TaskViewTaskController", "resetTaskInfo: " + this);
        }
        this.mTaskInfo = null;
        this.mTaskToken = null;
        this.mTaskLeash = null;
        this.mPendingInfo = null;
        this.mTaskNotFound = false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TaskViewTaskController{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" id=");
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        sb.append(runningTaskInfo != null ? Integer.valueOf(runningTaskInfo.taskId) : "-1");
        sb.append(" t=");
        sb.append(this.mTaskToken);
        sb.append(" leash=");
        sb.append(this.mTaskLeash);
        sb.append(" c=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.mCreatedByTaskViewFactory ? "factory" : SystemUIAnalytics.QPNE_VID_BUBBLE, "}");
    }

    public final void updateTaskVisibility() {
        boolean z = this.mSurfaceCreated;
        if (z || this.mHideTaskWithSurface) {
            if (this.mTaskToken == null) {
                Log.w("TaskViewTaskController", "updateTaskVisibility: failed, task token is null, " + this + ", Callers=" + Debug.getCallers(5));
                return;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setHidden(this.mTaskToken, !z);
            if (!z) {
                windowContainerTransaction.reorder(this.mTaskToken, false);
            }
            SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
            syncTransactionQueue.queue(windowContainerTransaction);
            if (this.mListener == null) {
                return;
            }
            final int i = this.mTaskInfo.taskId;
            syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda9
                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                    TaskViewTaskController taskViewTaskController = TaskViewTaskController.this;
                    taskViewTaskController.mListenerExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda4(taskViewTaskController, i, 2));
                }
            });
        }
    }

    public TaskViewTaskController(Context context, ShellTaskOrganizer shellTaskOrganizer, TaskViewController taskViewController, SyncTransactionQueue syncTransactionQueue, boolean z) {
        CloseGuard closeGuard = new CloseGuard();
        this.mGuard = closeGuard;
        this.mTransaction = new SurfaceControl.Transaction();
        this.mCaptionInsetsOwner = new Binder();
        this.mHideTaskWithSurface = true;
        this.mCreatedByTaskViewFactory = z;
        this.mContext = context;
        this.mTaskOrganizer = shellTaskOrganizer;
        Executor executor = shellTaskOrganizer.getExecutor();
        this.mShellExecutor = executor;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewController = taskViewController;
        executor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(this, 0));
        closeGuard.open(BuildConfig.BUILD_TYPE);
    }
}
