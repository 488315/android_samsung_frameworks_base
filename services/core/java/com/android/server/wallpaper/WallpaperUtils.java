package com.android.server.wallpaper;

import android.os.Environment;
import com.samsung.android.wallpaper.utils.WhichChecker;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class WallpaperUtils {
    public static final String[] sPerUserFiles = {"wallpaper_orig", "wallpaper", "wallpaper_info.xml", "wallpaper_sub_display_orig", "wallpaper_sub_display", "wallpaper_subdisplay_info.xml", "wallpaper_desktop_orig", "wallpaper_desktop", "wallpaper_desktop_info.xml", "wallpaper_virtual_display_orig", "wallpaper_virtual_display", "wallpaper_virtualdisplay_info.xml"};
    public static final String[] sPerUserLockFiles = {"wallpaper_lock_orig", "wallpaper_lock", "wallpaper_sub_display_lock_orig", "wallpaper_sub_display_lock", "wallpaper_desktop_lock_orig", "wallpaper_desktop_lock"};
    public static int sWallpaperId;

    public static String getCropFileName(int i) {
        int mode = WhichChecker.getMode(i);
        int type = WhichChecker.getType(i);
        return mode != 8 ? mode != 16 ? mode != 32 ? type == 2 ? "wallpaper_lock" : "wallpaper" : type == 2 ? "" : "wallpaper_virtual_display" : type == 2 ? "wallpaper_sub_display_lock" : "wallpaper_sub_display" : type == 2 ? "wallpaper_desktop_lock" : "wallpaper_desktop";
    }

    public static String getFileName(int i) {
        int mode = WhichChecker.getMode(i);
        int type = WhichChecker.getType(i);
        return mode != 8 ? mode != 16 ? mode != 32 ? type == 2 ? "wallpaper_lock_orig" : "wallpaper_orig" : type == 2 ? "" : "wallpaper_virtual_display_orig" : type == 2 ? "wallpaper_sub_display_lock_orig" : "wallpaper_sub_display_orig" : type == 2 ? "wallpaper_desktop_lock_orig" : "wallpaper_desktop_orig";
    }

    public static String getInfoFileName(int i) {
        int mode = WhichChecker.getMode(i);
        return mode != 8 ? mode != 16 ? mode != 32 ? "wallpaper_info.xml" : "wallpaper_virtualdisplay_info.xml" : "wallpaper_subdisplay_info.xml" : "wallpaper_desktop_info.xml";
    }

    public static List getWallpaperFiles(int i) {
        File userSystemDirectory = Environment.getUserSystemDirectory(i);
        File wallpaperLockDir = getWallpaperLockDir(i);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 12; i2++) {
            arrayList.add(new File(userSystemDirectory, sPerUserFiles[i2]));
        }
        for (int i3 = 0; i3 < 6; i3++) {
            arrayList.add(new File(wallpaperLockDir, sPerUserLockFiles[i3]));
        }
        return arrayList;
    }

    public static File getWallpaperLockDir(int i) {
        return new File(Environment.getUserSystemDirectory(i), "wallpaper_lock_images");
    }

    public static int makeWallpaperIdLocked() {
        int i;
        do {
            i = sWallpaperId + 1;
            sWallpaperId = i;
        } while (i == 0);
        return i;
    }
}
