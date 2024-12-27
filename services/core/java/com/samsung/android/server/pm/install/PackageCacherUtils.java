package com.samsung.android.server.pm.install;

import android.util.Slog;

import java.io.File;

public abstract class PackageCacherUtils {
    public static void changeModifiedTimeOfTheCacheIfNeeded(File file, File file2) {
        if (file2 == null) {
            return;
        }
        if (file.exists() && file.lastModified() <= file2.lastModified()) {
            file.setLastModified(file2.lastModified() + 1000);
            Slog.d(
                    "PackageCacher",
                    "cacheResult lastModified(apk): "
                            + file2.lastModified()
                            + ", lastModified(cache): "
                            + file.lastModified());
        }
        file.lastModified();
    }
}
