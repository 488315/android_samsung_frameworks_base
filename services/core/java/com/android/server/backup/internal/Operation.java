package com.android.server.backup.internal;

import com.android.server.backup.BackupRestoreTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Operation {
    public final BackupRestoreTask callback;
    public int state = 0;
    public final int type;

    public Operation(BackupRestoreTask backupRestoreTask, int i) {
        this.callback = backupRestoreTask;
        this.type = i;
    }
}
