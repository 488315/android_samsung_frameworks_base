package com.android.server.backup;

import android.util.Slog;

import com.android.server.backup.transport.TransportNotRegisteredException;

import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class UserBackupManagerService$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserBackupManagerService f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda7(
            UserBackupManagerService userBackupManagerService, List list, List list2, int i) {
        this.$r8$classId = i;
        this.f$0 = userBackupManagerService;
        this.f$1 = list;
        this.f$2 = list2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                UserBackupManagerService userBackupManagerService = this.f$0;
                List list = this.f$1;
                List list2 = this.f$2;
                String str = (String) obj;
                userBackupManagerService.getClass();
                try {
                    String transportDirName =
                            userBackupManagerService.mTransportManager.getTransportDirName(str);
                    list.add(str);
                    list2.add(transportDirName);
                    break;
                } catch (TransportNotRegisteredException e) {
                    Slog.e(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    userBackupManagerService.mUserId,
                                    "Unexpected unregistered transport"),
                            e);
                    return;
                }
            default:
                UserBackupManagerService userBackupManagerService2 = this.f$0;
                List list3 = this.f$1;
                List list4 = this.f$2;
                String str2 = (String) obj;
                userBackupManagerService2.getClass();
                try {
                    String transportDirName2 =
                            userBackupManagerService2.mTransportManager.getTransportDirName(str2);
                    list3.add(str2);
                    list4.add(transportDirName2);
                    break;
                } catch (TransportNotRegisteredException e2) {
                    Slog.e(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    userBackupManagerService2.mUserId,
                                    "Unexpected unregistered transport"),
                            e2);
                }
        }
    }
}
