package com.android.server.backup;

import android.util.Slog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class UserBackupManagerFilePersistedSettings {
    public static void writeBackupEnableState(int i, boolean z) {
        Slog.d("BackupManagerService", "user:" + i + " writeBackupEnableState enable:" + z);
        File baseStateDir = UserBackupManagerFiles.getBaseStateDir(i);
        File file = new File(baseStateDir, "backup_enabled");
        File file2 = new File(baseStateDir, "backup_enabled-stage");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                fileOutputStream.write(z ? 1 : 0);
                fileOutputStream.close();
                if (!file2.renameTo(file)) {
                    Slog.e(
                            "BackupManagerService",
                            "Write enable failed as could not rename staging file to actual");
                }
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | RuntimeException e) {
            Slog.e(
                    "BackupManagerService",
                    "Unable to record backup enable state; reverting to disabled: "
                            + e.getMessage());
            file.delete();
            file2.delete();
        }
    }
}
