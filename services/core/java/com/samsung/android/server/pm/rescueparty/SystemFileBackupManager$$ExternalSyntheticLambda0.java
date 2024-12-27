package com.samsung.android.server.pm.rescueparty;

import android.util.Base64;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.function.BiConsumer;

public final /* synthetic */ class SystemFileBackupManager$$ExternalSyntheticLambda0
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SystemFileBackupManager$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        String str = (String) obj;
        AbstractBackupController abstractBackupController = (AbstractBackupController) obj2;
        switch (this.$r8$classId) {
            case 0:
                abstractBackupController.getClass();
                Slog.d("SystemFileBackupManager", "Saving files for pm_settings_backup");
                synchronized (abstractBackupController.mLock) {
                    try {
                        File controllerDir = abstractBackupController.getControllerDir();
                        byte[] bArr = new byte[16];
                        abstractBackupController.random.nextBytes(bArr);
                        File file =
                                new File(
                                        controllerDir,
                                        "backup_item_" + Base64.encodeToString(bArr, 10));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        Slog.d("AbstractBackupController", "Saving files on " + file);
                        try {
                        } catch (Exception e) {
                            Slog.d("AbstractBackupController", "Failed to save files: " + e);
                        }
                        if (abstractBackupController.onSaveFiles(file)) {
                            abstractBackupController.addBackupItemList(file);
                            abstractBackupController.writeBackupItems();
                            abstractBackupController.cleanUpOutdatedFiles();
                            if (abstractBackupController.mLastSelectedBackupItemIndex.get() != -1) {
                                abstractBackupController.setLastSelectedItemIndex(-1);
                                abstractBackupController.putBackupConfigInt(
                                        abstractBackupController.mLastSelectedBackupItemIndex.get(),
                                        "last_selected_item",
                                        false);
                            }
                        }
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "Notifying ", str, " of system ready", "SystemFileBackupManager");
                PackageManagerBackupController packageManagerBackupController =
                        (PackageManagerBackupController) abstractBackupController;
                packageManagerBackupController.getClass();
                Slog.d("PmBackupController", "Reset reboot counts");
                packageManagerBackupController.mRebootCntByPackages = 0;
                packageManagerBackupController.mRebootCntByPkgsState = 0;
                packageManagerBackupController.putBackupConfigInt(
                        0, "reboot_cnt_by_packages", false);
                packageManagerBackupController.putBackupConfigInt(
                        packageManagerBackupController.mRebootCntByPkgsState,
                        "reboot_cnt_by_packages_state",
                        false);
                return;
        }
    }
}
