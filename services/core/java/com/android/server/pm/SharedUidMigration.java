package com.android.server.pm;

import android.os.Build;
import android.os.SystemProperties;

/* loaded from: classes3.dex */
public abstract class SharedUidMigration {
    public static boolean isDisabled() {
        return false;
    }

    public static int getCurrentStrategy() {
        int i;
        if (Build.IS_USERDEBUG && (i = SystemProperties.getInt("persist.debug.pm.shared_uid_migration_strategy", 1)) <= 2 && i >= 1) {
            return i;
        }
        return 1;
    }

    public static boolean applyStrategy(int i) {
        return !isDisabled() && getCurrentStrategy() >= i;
    }
}
