package com.android.server.backup.fullbackup;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.Slog;
import com.android.internal.backup.IObbBackupService;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.utils.FullBackupUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public final class FullBackupObbConnection implements ServiceConnection {
    public final UserBackupManagerService backupManagerService;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public volatile IObbBackupService mService = null;

    public FullBackupObbConnection(UserBackupManagerService userBackupManagerService) {
        this.backupManagerService = userBackupManagerService;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters = userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    public final boolean backupObbs(PackageInfo packageInfo, OutputStream outputStream) {
        synchronized (this) {
            while (this.mService == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
        }
        ?? r4 = 0;
        r4 = 0;
        r4 = 0;
        r4 = 0;
        r4 = 0;
        r4 = 0;
        ParcelFileDescriptor[] parcelFileDescriptorArr = null;
        try {
            try {
                parcelFileDescriptorArr = ParcelFileDescriptor.createPipe();
                int generateRandomIntegerToken = this.backupManagerService.generateRandomIntegerToken();
                this.backupManagerService.prepareOperationTimeout(generateRandomIntegerToken, this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), null, 0);
                this.mService.backupObbs(packageInfo.packageName, parcelFileDescriptorArr[1], generateRandomIntegerToken, this.backupManagerService.mBackupManagerBinder);
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
                r4 = waitUntilOperationComplete;
            } catch (Exception e2) {
                Slog.w("BackupManagerService", "Unable to back up OBBs for " + packageInfo, e2);
            }
            return r4;
        } finally {
            try {
                outputStream.flush();
                if (parcelFileDescriptorArr != null) {
                    ParcelFileDescriptor parcelFileDescriptor3 = parcelFileDescriptorArr[r4];
                    if (parcelFileDescriptor3 != null) {
                        parcelFileDescriptor3.close();
                    }
                    ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptorArr[1];
                    if (parcelFileDescriptor4 != null) {
                        parcelFileDescriptor4.close();
                    }
                }
            } catch (IOException e3) {
                Slog.w("BackupManagerService", "I/O error closing down OBB backup", e3);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.mService = IObbBackupService.Stub.asInterface(iBinder);
            notifyAll();
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this) {
            this.mService = null;
            notifyAll();
        }
    }

    public final void tearDown() {
        this.backupManagerService.mContext.unbindService(this);
    }
}
