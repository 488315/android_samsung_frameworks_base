package com.android.wm.shell.taskview;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskViewFactoryController {
    public final TaskViewFactoryImpl mImpl;
    public final ShellExecutor mShellExecutor;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskViewController mTaskViewController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
