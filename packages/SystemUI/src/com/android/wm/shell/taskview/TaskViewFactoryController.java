package com.android.wm.shell.taskview;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;

/* loaded from: classes3.dex */
public class TaskViewFactoryController {
    public final TaskViewFactoryImpl mImpl;
    public final ShellExecutor mShellExecutor;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskViewController mTaskViewController;

    public class TaskViewFactoryImpl {
        public /* synthetic */ TaskViewFactoryImpl(TaskViewFactoryController taskViewFactoryController, int i) {
            this();
        }

        private TaskViewFactoryImpl() {
        }
    }

    public TaskViewFactoryController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, SyncTransactionQueue syncTransactionQueue, TaskViewController taskViewController) {
        this.mImpl = new TaskViewFactoryImpl(this, 0);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellExecutor;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewController = taskViewController;
    }

    public TaskViewFactoryController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, SyncTransactionQueue syncTransactionQueue) {
        this.mImpl = new TaskViewFactoryImpl(this, 0);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellExecutor;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewController = null;
    }
}
