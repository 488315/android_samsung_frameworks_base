package com.android.server.backup.internal;

import com.android.server.backup.BackupRestoreTask;

public final class Operation {
    public final BackupRestoreTask callback;
    public int state = 0;
    public final int type;

    public Operation(BackupRestoreTask backupRestoreTask, int i) {
        this.callback = backupRestoreTask;
        this.type = i;
    }
}
