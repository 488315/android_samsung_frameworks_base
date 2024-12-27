package com.android.systemui.util;

import android.app.backup.BackupManager;

public final class BackupManagerProxy {
    public static final int $stable = 0;

    public final void dataChanged(String str) {
        BackupManager.dataChanged(str);
    }

    public final void dataChangedForUser(int i, String str) {
        BackupManager.dataChangedForUser(i, str);
    }
}
