package com.android.server.backup;

import android.os.Environment;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UserBackupManagerFiles {
    public static File getBaseStateDir(int i) {
        return i != 0
                ? new File(Environment.getDataSystemCeDirectory(i), "backup")
                : new File(Environment.getDataDirectory(), "backup");
    }
}
