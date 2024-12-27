package com.android.server.pm.parsing;

import android.content.pm.PackageParserCacheHelper;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Parcel;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;

import com.android.internal.pm.parsing.IPackageCacher;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.server.pm.ApexManager;

import libcore.io.IoUtils;

import com.samsung.android.server.pm.install.PackageCacherUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageCacher implements IPackageCacher {
    public static final AtomicInteger sCachedPackageReadCount = new AtomicInteger();
    public final File mCacheDir;
    public final PackageParser2.Callback mCallback;

    public PackageCacher(File file, PackageParser2.Callback callback) {
        this.mCacheDir = file;
        this.mCallback = callback;
    }

    public static ParsedPackage fromCacheEntryStatic(byte[] bArr) {
        return fromCacheEntryStatic(bArr, null);
    }

    public static ParsedPackage fromCacheEntryStatic(
            byte[] bArr, ParsingPackageUtils.Callback callback) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        new PackageParserCacheHelper.ReadHelper(obtain).startAndInstall();
        PackageImpl packageImpl = new PackageImpl(obtain, callback);
        obtain.recycle();
        sCachedPackageReadCount.incrementAndGet();
        return packageImpl;
    }

    public static String getCacheKey(File file, int i) {
        return file.getName() + '-' + i + '-' + file.getAbsolutePath().hashCode();
    }

    public static boolean isCacheUpToDate(File file, File file2) {
        try {
            if (file.toPath().startsWith(Environment.getApexDirectory().toPath())) {
                File backingApexFile = ApexManager.getInstance().getBackingApexFile(file);
                if (backingApexFile == null) {
                    Slog.w(
                            "PackageCacher",
                            "Failed to find APEX file backing " + file.getAbsolutePath());
                } else {
                    file = backingApexFile;
                }
            }
            return Os.stat(file.getAbsolutePath()).st_mtime
                    < Os.stat(file2.getAbsolutePath()).st_mtime;
        } catch (ErrnoException e) {
            if (e.errno != OsConstants.ENOENT) {
                Slog.w("Error while stating package cache : ", e);
            }
            return false;
        }
    }

    public static byte[] toCacheEntryStatic(ParsedPackage parsedPackage) {
        Parcel obtain = Parcel.obtain();
        PackageParserCacheHelper.WriteHelper writeHelper =
                new PackageParserCacheHelper.WriteHelper(obtain);
        ((PackageImpl) parsedPackage).writeToParcel(obtain, 0);
        writeHelper.finishAndUninstall();
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public final void cacheResult(File file, int i, ParsedPackage parsedPackage) {
        FileOutputStream fileOutputStream;
        try {
            File file2 = new File(this.mCacheDir, getCacheKey(file, i));
            if (file2.exists() && !file2.delete()) {
                Slog.e("PackageCacher", "Unable to delete cache file: " + file2);
            }
            byte[] cacheEntry = toCacheEntry(parsedPackage);
            if (cacheEntry == null) {
                return;
            }
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (IOException e) {
                Slog.w("PackageCacher", "Error writing cache entry.", e);
                file2.delete();
            }
            try {
                fileOutputStream.write(cacheEntry);
                fileOutputStream.close();
                PackageCacherUtils.changeModifiedTimeOfTheCacheIfNeeded(file2, file);
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            Slog.w("PackageCacher", "Error saving package cache.", th3);
        }
    }

    public final void cleanCachedResult(File file) {
        final String name = file.getName();
        for (File file2 :
                FileUtils.listFilesOrEmpty(
                        this.mCacheDir,
                        new FilenameFilter() { // from class:
                                               // com.android.server.pm.parsing.PackageCacher$$ExternalSyntheticLambda0
                            @Override // java.io.FilenameFilter
                            public final boolean accept(File file3, String str) {
                                return str.startsWith(name);
                            }
                        })) {
            if (!file2.delete()) {
                Slog.e("PackageCacher", "Unable to clean cache file: " + file2);
            }
        }
    }

    public ParsedPackage fromCacheEntry(byte[] bArr) {
        return fromCacheEntryStatic(bArr, this.mCallback);
    }

    public final ParsedPackage getCachedResult(File file, int i) {
        File file2 = new File(this.mCacheDir, getCacheKey(file, i));
        try {
            if (!isCacheUpToDate(file, file2)) {
                return null;
            }
            ParsedPackage fromCacheEntry =
                    fromCacheEntry(IoUtils.readFileAsByteArray(file2.getAbsolutePath()));
            if (file.getAbsolutePath().equals(fromCacheEntry.getPath())) {
                return fromCacheEntry;
            }
            return null;
        } catch (Throwable th) {
            Slog.w("PackageCacher", "Error reading package cache: ", th);
            file2.delete();
            return null;
        }
    }

    public byte[] toCacheEntry(ParsedPackage parsedPackage) {
        return toCacheEntryStatic(parsedPackage);
    }
}
