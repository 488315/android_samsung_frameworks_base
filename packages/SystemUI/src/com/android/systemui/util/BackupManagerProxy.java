package com.android.systemui.util;

import android.app.backup.BackupManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BackupManagerProxy {
    public static final int $stable = 0;

    public final void dataChanged(String str) {
        BackupManager.dataChanged(str);
    }

    public final void dataChangedForUser(int i, String str) {
        BackupManager.dataChangedForUser(i, str);
    }
}
