package com.android.server.backup;

import java.util.function.IntConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class UserBackupManagerService$$ExternalSyntheticLambda14
        implements IntConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserBackupManagerService f$0;

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda14(
            int i, UserBackupManagerService userBackupManagerService) {
        this.$r8$classId = i;
        this.f$0 = userBackupManagerService;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        int i2 = this.$r8$classId;
        UserBackupManagerService userBackupManagerService = this.f$0;
        switch (i2) {
            case 0:
                userBackupManagerService.getClass();
                if (i == 0 || i == 1) {
                    userBackupManagerService.mBackupHandler.removeMessages(
                            userBackupManagerService.getMessageIdForOperationType(i));
                    break;
                }
                break;
            default:
                userBackupManagerService.mBackupHandler.removeMessages(
                        userBackupManagerService.getMessageIdForOperationType(i));
                break;
        }
    }
}
