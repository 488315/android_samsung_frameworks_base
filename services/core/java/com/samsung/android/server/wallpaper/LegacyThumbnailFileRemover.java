package com.samsung.android.server.wallpaper;

import android.os.Environment;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.wallpaper.WallpaperUtils;

import com.samsung.android.wallpaper.utils.WhichChecker;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class LegacyThumbnailFileRemover {
    public static File getThumbnailFile(int i, int i2) {
        return new File(
                WhichChecker.isLock(i)
                        ? WallpaperUtils.getWallpaperLockDir(0)
                        : Environment.getUserSystemDirectory(0),
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i, "wallpaper_thumb_", i2 == 2 ? "_land" : ""));
    }

    public static void removeThumbnailFiles(int i) {
        File thumbnailFile = getThumbnailFile(i, 1);
        if (thumbnailFile.exists()) {
            thumbnailFile.delete();
        }
        File thumbnailFile2 = getThumbnailFile(i, 2);
        if (thumbnailFile2.exists()) {
            thumbnailFile2.delete();
        }
    }
}
