package com.android.server.backup.fullbackup;

import android.app.backup.IBackupManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.backup.IObbBackupService;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.utils.FullBackupUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/* loaded from: classes.dex */
public class FullBackupObbConnection implements ServiceConnection {
    public UserBackupManagerService backupManagerService;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public volatile IObbBackupService mService = null;

    public FullBackupObbConnection(UserBackupManagerService userBackupManagerService) {
        this.backupManagerService = userBackupManagerService;
        BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
    }

    public void establish() {
        this.backupManagerService.getContext().bindServiceAsUser(new Intent().setComponent(new ComponentName("com.android.sharedstoragebackup", "com.android.sharedstoragebackup.ObbBackupService")), this, 1, UserHandle.SYSTEM);
    }

    public void tearDown() {
        this.backupManagerService.getContext().unbindService(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public boolean backupObbs(PackageInfo packageInfo, OutputStream outputStream) {
        waitForConnection();
        ?? r3 = 0;
        r3 = 0;
        r3 = 0;
        r3 = 0;
        r3 = 0;
        r3 = 0;
        ParcelFileDescriptor[] parcelFileDescriptorArr = null;
        try {
            try {
                parcelFileDescriptorArr = ParcelFileDescriptor.createPipe();
                int generateRandomIntegerToken = this.backupManagerService.generateRandomIntegerToken();
                this.backupManagerService.prepareOperationTimeout(generateRandomIntegerToken, this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), null, 0);
                this.mService.backupObbs(packageInfo.packageName, parcelFileDescriptorArr[1], generateRandomIntegerToken, this.backupManagerService.getBackupManagerBinder());
                FullBackupUtils.routeSocketDataToOutput(parcelFileDescriptorArr[0], outputStream);
                boolean waitUntilOperationComplete = this.backupManagerService.waitUntilOperationComplete(generateRandomIntegerToken);
                try {
                    outputStream.flush();
                    ParcelFileDescriptor parcelFileDescriptor = parcelFileDescriptorArr[0];
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                    ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptorArr[1];
                    if (parcelFileDescriptor2 != null) {
                        parcelFileDescriptor2.close();
                    }
                } catch (IOException e) {
                    Slog.w("BackupManagerService", "I/O error closing down OBB backup", e);
                }
                r3 = waitUntilOperationComplete;
            } finally {
                try {
                    outputStream.flush();
                    if (parcelFileDescriptorArr != null) {
                        ParcelFileDescriptor parcelFileDescriptor3 = parcelFileDescriptorArr[r3];
                        if (parcelFileDescriptor3 != null) {
                            parcelFileDescriptor3.close();
                        }
                        ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptorArr[1];
                        if (parcelFileDescriptor4 != null) {
                            parcelFileDescriptor4.close();
                        }
                    }
                } catch (IOException e2) {
                    Slog.w("BackupManagerService", "I/O error closing down OBB backup", e2);
                }
            }
        } catch (Exception e3) {
            Slog.w("BackupManagerService", "Unable to back up OBBs for " + packageInfo, e3);
        }
        return r3;
    }

    public void restoreObbFile(String str, ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) {
        waitForConnection();
        try {
            this.mService.restoreObbFile(str, parcelFileDescriptor, j, i, str2, j2, j3, i2, iBackupManager);
        } catch (Exception e) {
            Slog.w("BackupManagerService", "Unable to restore OBBs for " + str, e);
        }
    }

    public final void waitForConnection() {
        synchronized (this) {
            while (this.mService == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.mService = IObbBackupService.Stub.asInterface(iBinder);
            notifyAll();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        synchronized (this) {
            this.mService = null;
            notifyAll();
        }
    }
}
