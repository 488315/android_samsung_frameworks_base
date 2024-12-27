package com.samsung.android.server.pm.rescueparty;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerTracedLock;

import java.io.File;

public final class PackageManagerBackupController extends AbstractBackupController {
    public final Context mContext;
    public final PackageManagerTracedLock mLock;
    public final File mPackagesBackupFile;
    public final File mPackagesFile;
    public int mRebootCntByPackages;
    public int mRebootCntByPkgsState;

    public PackageManagerBackupController(
            PackageManagerTracedLock packageManagerTracedLock, Context context) {
        super(context);
        this.mPackagesFile = new File(injectSystemDataDir(), "packages.xml");
        this.mPackagesBackupFile = new File(injectSystemDataDir(), "packages-backup.xml");
        this.mLock = packageManagerTracedLock;
        this.mContext = context;
        this.mRebootCntByPackages = getBackupConfigInt(0, "reboot_cnt_by_packages");
        this.mRebootCntByPkgsState = getBackupConfigInt(0, "reboot_cnt_by_packages_state");
    }

    @Override // com.samsung.android.server.pm.rescueparty.AbstractBackupController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onSaveFiles(java.io.File r8) {
        /*
            r7 = this;
            java.lang.String r0 = "PmBackupController"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "onSaveFiles: "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            android.util.Slog.d(r0, r1)
            long r0 = android.os.SystemClock.elapsedRealtime()
            android.content.Context r2 = r7.mContext
            java.lang.String r3 = "user"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.os.UserManager r2 = (android.os.UserManager) r2
            r3 = 0
            if (r2 != 0) goto L2e
            java.lang.String r7 = "PmBackupController"
            java.lang.String r8 = "No UserManager registered"
            android.util.Slog.d(r7, r8)
            return r3
        L2e:
            com.android.server.pm.PackageManagerTracedLock r4 = r7.mLock
            boolean r5 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            monitor-enter(r4)
            java.io.File r5 = r7.mPackagesFile     // Catch: java.lang.Throwable -> L5f
            boolean r5 = r5.exists()     // Catch: java.lang.Throwable -> L5f
            if (r5 == 0) goto L53
            java.io.File r5 = r7.mPackagesBackupFile     // Catch: java.lang.Throwable -> L5f
            boolean r5 = r5.exists()     // Catch: java.lang.Throwable -> L5f
            if (r5 == 0) goto L44
            goto L53
        L44:
            java.io.File r5 = new java.io.File     // Catch: java.lang.Throwable -> L5f
            java.lang.String r6 = "packages.xml"
            r5.<init>(r8, r6)     // Catch: java.lang.Throwable -> L5f
            java.io.File r6 = r7.mPackagesFile     // Catch: java.lang.Throwable -> L5f
            boolean r5 = com.samsung.android.server.pm.rescueparty.AbstractBackupController.copyFile(r6, r5)     // Catch: java.lang.Throwable -> L5f
            goto L5b
        L53:
            java.lang.String r5 = "PmBackupController"
            java.lang.String r6 = "There's something wrong, skip copying of the packages file"
            android.util.Slog.d(r5, r6)     // Catch: java.lang.Throwable -> L5f
            r5 = r3
        L5b:
            if (r5 != 0) goto L61
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5f
            return r3
        L5f:
            r7 = move-exception
            goto La6
        L61:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5f
            java.util.List r2 = r2.getUsers()
            java.util.Iterator r2 = r2.iterator()
        L6a:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L87
            java.lang.Object r3 = r2.next()
            android.content.pm.UserInfo r3 = (android.content.pm.UserInfo) r3
            com.android.server.pm.PackageManagerTracedLock r4 = r7.mLock
            boolean r5 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            monitor-enter(r4)
            int r3 = r3.id     // Catch: java.lang.Throwable -> L82
            r7.savePackagesStateForUser(r8, r3)     // Catch: java.lang.Throwable -> L82
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L82
            goto L6a
        L82:
            r7 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L82
            boolean r8 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            throw r7
        L87:
            java.lang.String r7 = "PmBackupController"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r2 = "Total time: "
            r8.<init>(r2)
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r2 = r2 - r0
            r8.append(r2)
            java.lang.String r0 = " ms"
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            android.util.Slog.d(r7, r8)
            r7 = 1
            return r7
        La6:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5f
            boolean r8 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.pm.rescueparty.PackageManagerBackupController.onSaveFiles(java.io.File):boolean");
    }

    public final void savePackagesStateForUser(File file, int i) {
        File file2 =
                new File(
                        new File(new File(injectSystemDataDir(), "users"), Integer.toString(i)),
                        "package-restrictions.xml");
        File file3 =
                new File(
                        new File(new File(injectSystemDataDir(), "users"), Integer.toString(i)),
                        "package-restrictions-backup.xml");
        if (!file2.exists() || file3.exists()) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i,
                    "There's something wrong, skip copying of the packages state file for user ",
                    "PmBackupController");
            return;
        }
        File file4 = new File(file, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "users/"));
        if (file4.mkdirs()) {
            AbstractBackupController.copyFile(file2, new File(file4, "package-restrictions.xml"));
            return;
        }
        Slog.e("PmBackupController", "!@Failed to make dirs for " + file4);
    }
}
