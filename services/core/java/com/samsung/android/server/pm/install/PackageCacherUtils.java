package com.samsung.android.server.pm.install;

import android.util.Slog;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
