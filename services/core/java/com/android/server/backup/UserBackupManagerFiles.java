package com.android.server.backup;

import android.os.Environment;

import java.io.File;

public abstract class UserBackupManagerFiles {
    public static File getBaseStateDir(int i) {
        return i != 0
                ? new File(Environment.getDataSystemCeDirectory(i), "backup")
                : new File(Environment.getDataDirectory(), "backup");
    }
}
