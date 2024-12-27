package com.android.server.backup;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.FileUtils;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerService;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BackupManagerService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BackupManagerService$$ExternalSyntheticLambda0(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BackupManagerService backupManagerService = (BackupManagerService) this.f$0;
                int i = this.f$1;
                if (!backupManagerService.mGlobalDisable) {
                    Slog.i("BackupManagerService", "Stopping service for user: " + i);
                    backupManagerService.stopServiceForUser(i);
                    break;
                }
                break;
            default:
                BackupManagerService.AnonymousClass1 anonymousClass1 = (BackupManagerService.AnonymousClass1) this.f$0;
                int i2 = this.f$1;
                anonymousClass1.this$0.getClass();
                Slog.i("BackupManagerService", "Removing state for non system user " + i2);
                if (!FileUtils.deleteContentsAndDir(new File(UserBackupManagerFiles.getBaseStateDir(0), VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "")))) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "Failed to delete state dir for removed user: ", "BackupManagerService");
                    break;
                }
                break;
        }
    }
}
