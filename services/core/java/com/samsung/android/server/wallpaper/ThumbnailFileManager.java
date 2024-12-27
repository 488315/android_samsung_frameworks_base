package com.samsung.android.server.wallpaper;

import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SELinux;

import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ThumbnailFileManager {
    public static ThumbnailFileManager sInstance;
    public static final int[] sRotationTable = {0, 1, 2, 3};

    public static boolean copyFile(ParcelFileDescriptor parcelFileDescriptor, File file) {
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    FileUtils.copy(fileInputStream, fileOutputStream);
                    fileOutputStream.close();
                    fileInputStream.close();
                    return true;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e("ThumbnailFileManager", "copyFile : e=" + e, e);
            return false;
        }
    }

    public static synchronized ThumbnailFileManager getInstance() {
        ThumbnailFileManager thumbnailFileManager;
        synchronized (ThumbnailFileManager.class) {
            try {
                if (sInstance == null) {
                    ThumbnailFileManager thumbnailFileManager2 = new ThumbnailFileManager();
                    if (LegacyThumbnailFileRemover.getThumbnailFile(5, 1).exists()) {
                        Log.i(
                                "LegacyThumbnailFileRemover",
                                "remove: legacy wallpaper thumbnail detected.");
                        LegacyThumbnailFileRemover.removeThumbnailFiles(5);
                        LegacyThumbnailFileRemover.removeThumbnailFiles(6);
                        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                            LegacyThumbnailFileRemover.removeThumbnailFiles(17);
                            LegacyThumbnailFileRemover.removeThumbnailFiles(18);
                        }
                        LegacyThumbnailFileRemover.removeThumbnailFiles(9);
                        LegacyThumbnailFileRemover.removeThumbnailFiles(10);
                    }
                    sInstance = thumbnailFileManager2;
                }
                thumbnailFileManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return thumbnailFileManager;
    }

    public static File getThumbnailFile(int i, int i2, int i3) {
        File file =
                new File(
                        new File(Environment.getUserSystemDirectory(i2), "wallpaper_thumbs"),
                        String.valueOf(i));
        String str = "_0";
        if (i3 != 0) {
            if (i3 == 1) {
                str = "_90";
            } else if (i3 == 2) {
                str = "_180";
            } else if (i3 != 3) {
                Log.e("ThumbnailFileManager", "getFileNamePostfix: incorrect rotation(" + i3 + ")");
            } else {
                str = "_270";
            }
        }
        return new File(file, "thumbnail".concat(str));
    }

    public static boolean moveFile(File file, File file2) {
        if (file.renameTo(file2)) {
            if (SELinux.restorecon(file2)) {
                return true;
            }
            Log.w(
                    "ThumbnailFileManager",
                    "moveFile : restorecon failed - " + file2.getAbsolutePath());
            return false;
        }
        Log.w(
                "ThumbnailFileManager",
                "moveFile : failed to move file from "
                        + file.getAbsolutePath()
                        + " to "
                        + file2.getAbsolutePath());
        return false;
    }

    public final synchronized boolean deleteThumbnailFiles(int i, int i2) {
        if (WhichChecker.isModeAbsent(i)) {
            Log.e("ThumbnailFileManager", "deleteThumbnailFile: mode is not present. which=" + i);
            return false;
        }
        int mode = WhichChecker.getMode(i);
        boolean z = true;
        if (WhichChecker.isSystemAndLock(i)) {
            deleteThumbnailFiles(mode | 1, i2);
            deleteThumbnailFiles(mode | 2, i2);
            return true;
        }
        File file =
                new File(
                        new File(Environment.getUserSystemDirectory(i2), "wallpaper_thumbs"),
                        String.valueOf(i));
        if (!file.exists()) {
            return false;
        }
        int[] iArr = sRotationTable;
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = iArr[i3];
            File thumbnailFile = getThumbnailFile(i, i2, i4);
            if (thumbnailFile.exists()) {
                boolean delete = thumbnailFile.delete();
                Log.d(
                        "ThumbnailFileManager",
                        "deleteThumbnailFile: which="
                                + i
                                + ", rotation="
                                + i4
                                + ", success="
                                + delete);
                if (!delete) {
                    z = false;
                }
            }
        }
        file.delete();
        return z;
    }

    public final synchronized boolean writeThumbnailFile(
            int i, int i2, int i3, ParcelFileDescriptor parcelFileDescriptor) {
        File thumbnailFile = getThumbnailFile(i, i2, i3);
        Log.i(
                "ThumbnailFileManager",
                "writeThumbnailFile: which=" + i + ", userId=" + i2 + ", rotation=" + i3);
        if (thumbnailFile.exists() && thumbnailFile.length() > 0) {
            Log.i(
                    "ThumbnailFileManager",
                    "writeThumbnailFile: thumbnail already exist. skip writing");
            return true;
        }
        File file =
                new File(
                        new File(Environment.getUserSystemDirectory(i2), "wallpaper_thumbs"),
                        String.valueOf(i));
        if (!file.exists() && !file.mkdirs()) {
            Log.e(
                    "ThumbnailFileManager",
                    "writeThumbnailFile: failed to create thumbnail dir - "
                            + file.getAbsolutePath());
            return false;
        }
        File file2 = new File(thumbnailFile.getAbsolutePath() + ".tmp");
        if (copyFile(parcelFileDescriptor, file2)) {
            if (thumbnailFile.exists()) {
                thumbnailFile.delete();
            }
            if (moveFile(file2, thumbnailFile)) {
                return true;
            }
            file2.delete();
            return false;
        }
        Log.e("ThumbnailFileManager", "writeThumbnailFile : failed to copy remote thumbnail file");
        if (!file2.delete()) {
            Log.e(
                    "ThumbnailFileManager",
                    "writeThumbnailFile : failed to delete temp thumbnail file - "
                            + file2.getAbsolutePath());
        }
        return false;
    }
}
