package com.samsung.server.wallpaper;

import java.io.File;

/* loaded from: classes2.dex */
public class LockWallpaper {
    public LockWallpaper() {
        Log.m129d("LockWallpaper", "LockWallpaper");
    }

    public File getWallpaperFile(File file, SemWallpaperData semWallpaperData) {
        if (semWallpaperData.getWpType() == 8) {
            Log.m129d("LockWallpaper", "generateResizedBitmap: get first frame for video wallpaper.");
            file = semWallpaperData.getVideoFirstFrameFile();
        }
        if (semWallpaperData.getWpType() == 4) {
            Log.m129d("LockWallpaper", "generateResizedBitmap: get background for animated wallpaper.");
            file = semWallpaperData.getAnimatedBackground();
        }
        if (semWallpaperData.getWpType() != 1) {
            return file;
        }
        Log.m129d("LockWallpaper", "generateResizedBitmap: get background for animated wallpaper.");
        return semWallpaperData.getMotionBackground();
    }
}
