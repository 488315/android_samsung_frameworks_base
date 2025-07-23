package android.app.backup;

import android.app.backup.IBackupManagerMonitor;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class BackupManagerMonitorWrapper extends IBackupManagerMonitor.Stub {
    private final BackupManagerMonitor mMonitor;

    public BackupManagerMonitorWrapper(BackupManagerMonitor backupManagerMonitor) {
        this.mMonitor = backupManagerMonitor;
    }

    @Override // android.app.backup.IBackupManagerMonitor
    public void onEvent(Bundle bundle) throws RemoteException {
        BackupManagerMonitor backupManagerMonitor = this.mMonitor;
        if (backupManagerMonitor == null) {
            return;
        }
        backupManagerMonitor.onEvent(bundle);
    }
}
