package android.app.backup;

import android.os.Bundle;
import android.os.RemoteException;

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
