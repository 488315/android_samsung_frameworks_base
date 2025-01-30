package android.app.backup;

import android.app.backup.IBackupManagerMonitor;
import android.p009os.Bundle;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public class BackupManagerMonitorWrapper extends IBackupManagerMonitor.Stub {
    private final BackupManagerMonitor mMonitor;

    public BackupManagerMonitorWrapper(BackupManagerMonitor monitor) {
        this.mMonitor = monitor;
    }

    @Override // android.app.backup.IBackupManagerMonitor
    public void onEvent(Bundle event) throws RemoteException {
        BackupManagerMonitor backupManagerMonitor = this.mMonitor;
        if (backupManagerMonitor == null) {
            return;
        }
        backupManagerMonitor.onEvent(event);
    }
}
