package com.android.p038wm.shell.taskview;

import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskViewFactoryController {
    public final TaskViewFactoryImpl mImpl;
    public final ShellExecutor mShellExecutor;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskViewTransitions mTaskViewTransitions;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TaskViewFactoryImpl {
        public /* synthetic */ TaskViewFactoryImpl(TaskViewFactoryController taskViewFactoryController, int i) {
            this();
        }

        private TaskViewFactoryImpl() {
        }
    }

    public TaskViewFactoryController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, SyncTransactionQueue syncTransactionQueue, TaskViewTransitions taskViewTransitions) {
        this.mImpl = new TaskViewFactoryImpl(this, 0);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellExecutor;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewTransitions = taskViewTransitions;
    }

    public TaskViewFactoryController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, SyncTransactionQueue syncTransactionQueue) {
        this.mImpl = new TaskViewFactoryImpl(this, 0);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellExecutor;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewTransitions = null;
    }
}
