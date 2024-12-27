package com.android.server.backup.internal;

import android.content.pm.PackageInfo;
import android.util.Slog;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda0;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PerformClearTask implements Runnable {
    public final UserBackupManagerService mBackupManagerService;
    public final OnTaskFinishedListener mListener;
    public final PackageInfo mPackage;
    public final TransportConnection mTransportConnection;
    public final TransportManager mTransportManager;

    public PerformClearTask(
            UserBackupManagerService userBackupManagerService,
            TransportConnection transportConnection,
            PackageInfo packageInfo,
            UserBackupManagerService$$ExternalSyntheticLambda0
                    userBackupManagerService$$ExternalSyntheticLambda0) {
        this.mBackupManagerService = userBackupManagerService;
        this.mTransportManager = userBackupManagerService.mTransportManager;
        this.mTransportConnection = transportConnection;
        this.mPackage = packageInfo;
        this.mListener = userBackupManagerService$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UserBackupManagerService userBackupManagerService;
        BackupTransportClient backupTransportClient = null;
        try {
            try {
                new File(
                                new File(
                                        this.mBackupManagerService.mBaseStateDir,
                                        this.mTransportManager.getTransportDirName(
                                                this.mTransportConnection.mTransportComponent)),
                                this.mPackage.packageName)
                        .delete();
                backupTransportClient =
                        this.mTransportConnection.connectOrThrow("PerformClearTask.run()");
                backupTransportClient.clearBackupData(this.mPackage);
                try {
                    backupTransportClient.finishBackup();
                } catch (Exception e) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            e,
                            new StringBuilder("Unable to mark clear operation finished: "),
                            "BackupManagerService");
                }
                this.mListener.onFinished("PerformClearTask.run()");
                userBackupManagerService = this.mBackupManagerService;
            } catch (Throwable th) {
                if (backupTransportClient != null) {
                    try {
                        backupTransportClient.finishBackup();
                    } catch (Exception e2) {
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                e2,
                                new StringBuilder("Unable to mark clear operation finished: "),
                                "BackupManagerService");
                    }
                }
                this.mListener.onFinished("PerformClearTask.run()");
                this.mBackupManagerService.mWakelock.release();
                throw th;
            }
        } catch (Exception e3) {
            Slog.e(
                    "BackupManagerService",
                    "Transport threw clearing data for " + this.mPackage + ": " + e3.getMessage());
            if (backupTransportClient != null) {
                try {
                    backupTransportClient.finishBackup();
                } catch (Exception e4) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            e4,
                            new StringBuilder("Unable to mark clear operation finished: "),
                            "BackupManagerService");
                }
            }
            this.mListener.onFinished("PerformClearTask.run()");
            userBackupManagerService = this.mBackupManagerService;
        }
        userBackupManagerService.mWakelock.release();
    }
}
