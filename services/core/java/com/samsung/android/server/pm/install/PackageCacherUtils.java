package com.samsung.android.server.pm.install;

import android.util.Slog;
import java.io.File;

/* loaded from: classes2.dex */
public abstract class PackageCacherUtils {
    public static long changeModifiedTimeOfTheCacheIfNeeded(File file, File file2) {
        if (file == null || file2 == null) {
            return -1L;
        }
        if (file.exists() && file.lastModified() <= file2.lastModified()) {
            file.setLastModified(file2.lastModified() + 1000);
            Slog.d("PackageCacher", "cacheResult lastModified(apk): " + file2.lastModified() + ", lastModified(cache): " + file.lastModified());
        }
        return file.lastModified();
    }
}
