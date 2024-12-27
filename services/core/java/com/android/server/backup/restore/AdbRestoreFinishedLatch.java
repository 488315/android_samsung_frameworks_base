package com.android.server.backup.restore;

import android.util.Slog;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.LifecycleOperationStorage;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdbRestoreFinishedLatch implements BackupRestoreTask {
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final int mCurrentOpToken;
    public final CountDownLatch mLatch = new CountDownLatch(1);
    public final OperationStorage mOperationStorage;

    public AdbRestoreFinishedLatch(UserBackupManagerService userBackupManagerService, OperationStorage operationStorage, int i) {
        this.mOperationStorage = operationStorage;
        this.mCurrentOpToken = i;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters = userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void execute() {
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void handleCancel(boolean z) {
        Slog.w("AdbRestoreFinishedLatch", "adb onRestoreFinished() timed out");
        this.mLatch.countDown();
        ((LifecycleOperationStorage) this.mOperationStorage).removeOperation(this.mCurrentOpToken);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void operationComplete(long j) {
        this.mLatch.countDown();
        ((LifecycleOperationStorage) this.mOperationStorage).removeOperation(this.mCurrentOpToken);
    }
}
