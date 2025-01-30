package com.samsung.android.server.pm.rescueparty;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.SystemClock;
import android.os.UserManager;
import android.util.Log;
import android.util.Slog;
import com.android.server.pm.PackageManagerTracedLock;
import java.io.File;

/* loaded from: classes2.dex */
public class PackageManagerBackupController extends AbstractBackupController {
    public static boolean DEBUG = true;
    public final Context mContext;
    public final PackageManagerTracedLock mLock;
    public final File mPackagesBackupFile;
    public final File mPackagesFile;
    public int mRebootCntByPackages;
    public int mRebootCntByPkgsState;

    @Override // com.samsung.android.server.pm.rescueparty.AbstractBackupController, com.samsung.android.server.pm.rescueparty.BackupController
    public String getControllerName() {
        return "pm_settings_backup";
    }

    public PackageManagerBackupController(PackageManagerTracedLock packageManagerTracedLock, Context context) {
        super(context);
        this.mPackagesFile = getPackagesFile();
        this.mPackagesBackupFile = getPackagesBackupFile();
        this.mLock = packageManagerTracedLock;
        this.mContext = context;
        this.mRebootCntByPackages = getBackupConfigInt("reboot_cnt_by_packages", 0);
        this.mRebootCntByPkgsState = getBackupConfigInt("reboot_cnt_by_packages_state", 0);
    }

    public void incRebootCntByPackages() {
        int i = this.mRebootCntByPackages + 1;
        this.mRebootCntByPackages = i;
        putBackupConfigInt("reboot_cnt_by_packages", i, true);
    }

    public int getRebootCntByPackages() {
        return this.mRebootCntByPackages;
    }

    @Override // com.samsung.android.server.pm.rescueparty.BackupController
    public void onSystemReady() {
        resetRebootCounts();
    }

    @Override // com.samsung.android.server.pm.rescueparty.AbstractBackupController
    public boolean onSaveFiles(File file) {
        Slog.d("PmBackupController", "onSaveFiles: " + file);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        if (userManager == null) {
            Slog.d("PmBackupController", "No UserManager registered");
            return false;
        }
        synchronized (this.mLock) {
            if (!savePackagesFile(file)) {
                return false;
            }
            for (UserInfo userInfo : userManager.getUsers()) {
                synchronized (this.mLock) {
                    savePackagesStateForUser(file, userInfo.id);
                }
            }
            Slog.d("PmBackupController", "Total time: " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
            return true;
        }
    }

    public final boolean savePackagesFile(File file) {
        if (!this.mPackagesFile.exists() || this.mPackagesBackupFile.exists()) {
            Slog.d("PmBackupController", "There's something wrong, skip copying of the packages file");
            return false;
        }
        return copyFile(this.mPackagesFile, new File(file, "packages.xml"));
    }

    public final boolean savePackagesStateForUser(File file, int i) {
        File userPackagesStateFile = getUserPackagesStateFile(i);
        File userPackagesStateBackupFile = getUserPackagesStateBackupFile(i);
        if (!userPackagesStateFile.exists() || userPackagesStateBackupFile.exists()) {
            Slog.d("PmBackupController", "There's something wrong, skip copying of the packages state file for user " + i);
            return false;
        }
        File file2 = new File(file, "users/" + i);
        if (!file2.mkdirs()) {
            Slog.e("PmBackupController", "!@Failed to make dirs for " + file2);
            return false;
        }
        return copyFile(userPackagesStateFile, new File(file2, "package-restrictions.xml"));
    }

    public File getBackupPackagesFile() {
        File latestBackupItemDir = getLatestBackupItemDir();
        if (latestBackupItemDir != null && latestBackupItemDir.exists()) {
            File file = new File(latestBackupItemDir, "packages.xml");
            if (file.exists()) {
                return file;
            }
        }
        Log.e("PmBackupController", "!@Invalid file or not exists in " + latestBackupItemDir);
        return null;
    }

    public File getBackupPackagesStateFile(int i) {
        File latestBackupItemDir = getLatestBackupItemDir();
        if (latestBackupItemDir != null && latestBackupItemDir.exists()) {
            File file = new File(latestBackupItemDir, "users/" + i + "/package-restrictions.xml");
            if (file.exists()) {
                return file;
            }
        }
        Log.e("PmBackupController", "!@Invalid dir or not exists in " + latestBackupItemDir + " for user " + i);
        return null;
    }

    public void resetRebootCounts() {
        if (DEBUG) {
            Slog.d("PmBackupController", "Reset reboot counts");
        }
        this.mRebootCntByPackages = 0;
        this.mRebootCntByPkgsState = 0;
        putBackupConfigInt("reboot_cnt_by_packages", 0, false);
        putBackupConfigInt("reboot_cnt_by_packages_state", this.mRebootCntByPkgsState, false);
    }

    public final File getUserPackagesStateFile(int i) {
        return new File(new File(new File(injectSystemDataDir(), "users"), Integer.toString(i)), "package-restrictions.xml");
    }

    public final File getUserPackagesStateBackupFile(int i) {
        return new File(new File(new File(injectSystemDataDir(), "users"), Integer.toString(i)), "package-restrictions-backup.xml");
    }

    public final File getPackagesFile() {
        return new File(injectSystemDataDir(), "packages.xml");
    }

    public final File getPackagesBackupFile() {
        return new File(injectSystemDataDir(), "packages-backup.xml");
    }
}
