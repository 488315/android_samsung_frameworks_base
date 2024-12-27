package com.samsung.server.wallpaper.snapshot;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;
import com.android.server.wallpaper.WallpaperUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.snapshot.SnapshotManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SnapshotHelper {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class SettingsData {
        public static final HashMap defaultSettings;
        public static final Map settingsData;

        static {
            String[] strArr = {"lockscreen_wallpaper", "android.wallpaper.settings_systemui_transparency"};
            String[] strArr2 = {"lockscreen_wallpaper", "lockscreen_wallpaper_transparent"};
            HashMap hashMap = new HashMap();
            settingsData = hashMap;
            hashMap.put(1, strArr);
            hashMap.put(5, strArr);
            hashMap.put(2, strArr2);
            hashMap.put(6, strArr2);
            hashMap.put(17, new String[]{"lockscreen_wallpaper_sub", "sub_display_system_wallpaper_transparency"});
            hashMap.put(18, new String[]{"lockscreen_wallpaper_sub", "sub_display_lockscreen_wallpaper_transparency"});
            hashMap.put(33, new String[]{""});
            defaultSettings = new HashMap() { // from class: com.samsung.server.wallpaper.snapshot.SnapshotHelper.SettingsData.1
                {
                    put("android.wallpaper.settings_systemui_transparency", 1);
                    put("lockscreen_wallpaper", 1);
                    put("lockscreen_wallpaper_transparent", 1);
                    put("sub_display_system_wallpaper_transparency", 1);
                    put("lockscreen_wallpaper_sub", 1);
                    put("sub_display_lockscreen_wallpaper_transparency", 1);
                }
            };
        }

        public static int getDefaultValue(String str) {
            HashMap hashMap = defaultSettings;
            if (hashMap.containsKey(str)) {
                return ((Integer) hashMap.get(str)).intValue();
            }
            return -1;
        }
    }

    public static int checkWhich(int i) {
        return WhichChecker.getMode(i) == 0 ? i | 4 : i;
    }

    public static void copyDirectory(File file, File file2) {
        Log.d("SnapshotHelper", "copyDirectory: sourceLocation = " + file + ", targetLocation = " + file2);
        if (!file.isDirectory()) {
            FileUtils.copyFile(file, file2);
            return;
        }
        if (!file2.exists() && !file2.mkdirs()) {
            Log.e("SnapshotHelper", "copyDirectory: failed to create directory");
            return;
        }
        String[] list = file.list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                copyDirectory(new File(file, list[i]), new File(file2, list[i]));
            }
        }
    }

    public static boolean deleteDirectory(File file) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return false;
            }
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    deleteDirectory(file2);
                } else if (!file2.delete()) {
                    Log.w("SnapshotHelper", "deleteDirectory: failed to delete " + file2);
                }
            }
        }
        return file.delete();
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Log.d("SnapshotHelper", "deleteFile: " + file.getPath());
        if (file.delete()) {
            return;
        }
        Log.w("SnapshotHelper", "deleteFile: failed to delete " + file);
    }

    public static File getBackupWallpaperAssetsDir(int i, int i2, int i3) {
        return new File(Environment.getUserSystemDirectory(i), DualAppManagerService$$ExternalSyntheticOutline0.m(i2, i3, "wallpaper_backup/", "/", "/wallpaper_assets"));
    }

    public static File getBackupWallpaperDir(int i, int i2, int i3) {
        return new File(Environment.getUserSystemDirectory(i), ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, "wallpaper_backup/", "/"));
    }

    public static String getBackupWallpaperDirLegacy(int i) {
        return WhichChecker.isDex(i) ? "dex_wallpaper_backup" : WhichChecker.isSubDisplay(i) ? "sub_wallpaper_backup" : "wallpaper_backup";
    }

    public static File getBackupWallpaperFile(int i, int i2, int i3) {
        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
            return new File(getBackupWallpaperDir(i, i2, i3), "wallpaper");
        }
        return new File(WhichChecker.isLock(i3) ? WallpaperUtils.getWallpaperLockDir(i) : Environment.getUserSystemDirectory(i), (getBackupWallpaperDirLegacy(i3).concat("/") + i2 + "_") + i3);
    }

    public static int getCorrespondingWhich(int i) {
        if (!WhichChecker.isSystem(i)) {
            return WhichChecker.isSubDisplay(i) ? 17 : 5;
        }
        if (WhichChecker.isVirtualDisplay(i) || WhichChecker.isWatchFaceDisplay(i)) {
            return 0;
        }
        return WhichChecker.isSubDisplay(i) ? 18 : 6;
    }

    public static ArrayList getWhiches(int i) {
        ArrayList arrayList = new ArrayList();
        if (WhichChecker.getType(i) != 3) {
            if (WhichChecker.getMode(i) == 0) {
                i |= 4;
            }
            arrayList.add(Integer.valueOf(i));
        } else if (WhichChecker.getMode(i) == 0) {
            arrayList.addAll(getWhichesForEachMode(1));
            arrayList.addAll(getWhichesForEachMode(2));
        } else {
            arrayList.add(Integer.valueOf(WhichChecker.getMode(i) | 1));
            arrayList.add(Integer.valueOf(WhichChecker.getMode(i) | 2));
        }
        return arrayList;
    }

    public static ArrayList getWhichesForEachMode(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(i | 4));
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                arrayList.add(Integer.valueOf(i | 16));
            } else if ((i & 2) == 0) {
                arrayList.add(Integer.valueOf(i | 16));
            }
        }
        return arrayList;
    }

    public static File[] listBackupFiles(int i, int i2) {
        try {
            File file = new File(WhichChecker.isLock(i2) ? WallpaperUtils.getWallpaperLockDir(i) : Environment.getUserSystemDirectory(i), getBackupWallpaperDirLegacy(i2));
            Log.d("SnapshotHelper", "listBackupFiles: directory = " + file);
            return file.listFiles();
        } catch (Exception e) {
            Log.d("SnapshotHelper", "listBackupFiles: " + e.getMessage());
            return null;
        }
    }

    public static void migrateFromOld(int i) {
        int[] iArr = {5, 17, 9, 6, 18, 10};
        for (int i2 = 0; i2 < 6; i2++) {
            try {
                int i3 = iArr[i2];
                File file = new File(WhichChecker.isLock(i3) ? WallpaperUtils.getWallpaperLockDir(i) : Environment.getUserSystemDirectory(i), getBackupWallpaperDirLegacy(i3));
                if (file.exists()) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null && listFiles.length > 0) {
                        for (int i4 = 0; i4 < listFiles.length; i4++) {
                            if (!listFiles[i4].isDirectory()) {
                                String[] split = listFiles[i4].getName().split("_");
                                File backupWallpaperFile = getBackupWallpaperFile(i, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                                Slog.d("SnapshotHelper", "migrateFromOld: from " + listFiles[i4].getPath() + ", to " + backupWallpaperFile.getPath());
                                saveFile(listFiles[i4], backupWallpaperFile);
                                deleteFile(listFiles[i4]);
                            }
                        }
                    }
                    if (!file.getPath().equals(new File(Environment.getUserSystemDirectory(i), "wallpaper_backup").getPath())) {
                        Slog.d("SnapshotHelper", "migrateFromOld: delete dir " + file);
                        deleteDirectory(file);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void parseSnapshotHistory(TypedXmlPullParser typedXmlPullParser, SnapshotManager.SnapshotRepository snapshotRepository) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "hisotory_count", 0);
        for (int i = 0; i < attributeInt; i++) {
            if (attributeInt > 0) {
                int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "type" + i, 0);
                int attributeInt3 = typedXmlPullParser.getAttributeInt((String) null, "key" + i, 0);
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "time" + i);
                String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "desc" + i);
                if (TextUtils.isEmpty(attributeValue2)) {
                    int attributeInt4 = typedXmlPullParser.getAttributeInt((String) null, "resultCount" + i, 0);
                    if (attributeInt4 > 0) {
                        HashMap hashMap = new HashMap();
                        for (int i2 = 0; i2 < attributeInt4; i2++) {
                            hashMap.put(Integer.valueOf(typedXmlPullParser.getAttributeInt((String) null, "which" + String.valueOf(i) + String.valueOf(i2), 0)), Integer.valueOf(typedXmlPullParser.getAttributeInt((String) null, KnoxCustomManagerService.SPCM_KEY_RESULT + String.valueOf(i) + String.valueOf(i2), 0)));
                        }
                        SnapshotManager.SnapshotHistory snapshotHistory = new SnapshotManager.SnapshotHistory();
                        snapshotHistory.type = attributeInt2;
                        snapshotHistory.key = attributeInt3;
                        snapshotHistory.time = attributeValue;
                        snapshotHistory.results = hashMap;
                        snapshotRepository.addHistory(snapshotHistory);
                    }
                } else {
                    SnapshotManager.SnapshotHistory snapshotHistory2 = new SnapshotManager.SnapshotHistory();
                    snapshotHistory2.type = attributeInt2;
                    snapshotHistory2.key = attributeInt3;
                    snapshotHistory2.time = attributeValue;
                    snapshotHistory2.desc = attributeValue2;
                    snapshotRepository.addHistory(snapshotHistory2);
                }
            }
        }
    }

    public static void renameDirectory(File file, File file2) {
        if (!deleteDirectory(file2)) {
            Log.w("SnapshotHelper", "delete target Location failed : " + file2);
        }
        if (file.renameTo(file2)) {
            return;
        }
        Log.w("SnapshotHelper", "renameDirectory: Failed to rename from " + file.getAbsolutePath() + " to " + file2.getAbsolutePath() + ", try copy and delete");
        copyDirectory(file, file2);
        deleteDirectory(file);
    }

    public static boolean saveFile(File file, File file2) {
        if (file == null) {
            Log.e("SnapshotHelper", "saveFile: source = " + file + ", target = " + file2);
            return false;
        }
        if (file.exists()) {
            if (!file2.exists()) {
                Log.d("SnapshotHelper", "saveFile: target file doesn't exist, mkdir success? = " + file2.mkdirs());
            }
            if (FileUtils.copyFile(file, file2)) {
                Log.d("SnapshotHelper", "saveFile: success copy file. \n\tsource = " + file + "\n\ttarget = " + file2);
                return true;
            }
            Log.d("SnapshotHelper", "saveFile: Failed to copy file.");
        } else {
            Log.d("SnapshotHelper", "saveFile: Source file does not exist.");
        }
        deleteFile(file2);
        return false;
    }

    public static void updateSettings(Context context, int i, Map map, WallpaperManagerService.SemCallback semCallback) {
        int intForUser;
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                int intValue = ((Integer) entry.getValue()).intValue();
                Log.d("SnapshotHelper", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(intValue, "updateSettings: ", str, "[", "]"));
                try {
                    intForUser = Settings.System.getIntForUser(context.getContentResolver(), str, -1, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!str.equals("lockscreen_wallpaper") && !str.equals("lockscreen_wallpaper_sub")) {
                    if (intValue != intForUser) {
                        if (intValue != -1) {
                            Settings.System.putIntForUser(context.getContentResolver(), str, intValue, i);
                        } else {
                            Log.d("SnapshotHelper", "updateSettings: value is -1. Write default setting value. Need to monitor behaviours.");
                            Settings.System.putIntForUser(context.getContentResolver(), str, SettingsData.getDefaultValue(str), i);
                        }
                    }
                }
                if (intValue == 0) {
                    Slog.d("SnapshotHelper", "updateSettings: Set restored wallpaper to be system and lock");
                    int i2 = str.equals("lockscreen_wallpaper") ? 4 : 16;
                    int i3 = i2 | 1;
                    WallpaperData onWallpaperDataRequested = semCallback.onWallpaperDataRequested(i, i3);
                    int i4 = i2 | 2;
                    WallpaperData onWallpaperDataRequested2 = semCallback.onWallpaperDataRequested(i, i4);
                    semCallback.onWallpaperFlagUpdated(i, i4);
                    semCallback.onBindWallpaperRequested(i, onWallpaperDataRequested.mWhich);
                    semCallback.onDetachWallpaper(onWallpaperDataRequested2);
                    semCallback.onLockWallpaperChanged(i, i4, onWallpaperDataRequested2.mSemWallpaperData.mExternalParams);
                    semCallback.notifySemWallpaperColors(i3);
                    semCallback.notifySemWallpaperColors(i4);
                }
            }
        }
    }

    public static void writeDefaultSettings(Context context, int i, int i2) {
        Map map = SettingsData.settingsData;
        String[] strArr = (String[]) ((HashMap) SettingsData.settingsData).get(Integer.valueOf(checkWhich(i2)));
        if (strArr != null) {
            for (String str : strArr) {
                int defaultValue = SettingsData.getDefaultValue(str);
                if (defaultValue != -1) {
                    Log.d("SnapshotHelper", "writeDefultSettings: Reset to default settings. name = " + str + ", value = " + defaultValue);
                    if (str.equals("lockscreen_wallpaper") || str.equals("lockscreen_wallpaper_sub")) {
                        Log.d("SnapshotHelper", "writeDefultSettings: skip [" + str + "]");
                    } else {
                        Settings.System.putIntForUser(context.getContentResolver(), str, defaultValue, i);
                    }
                }
            }
        }
    }

    public static void writeSnapshotHistory(TypedXmlSerializer typedXmlSerializer, SnapshotManager.SnapshotRepository snapshotRepository) {
        ArrayList arrayList = snapshotRepository.mSnapshotHistories;
        int size = arrayList != null ? arrayList.size() : 0;
        ArrayList arrayList2 = snapshotRepository.mSnapshotHistories;
        typedXmlSerializer.attributeInt((String) null, "hisotory_count", arrayList2 != null ? arrayList2.size() : 0);
        if (size > 0) {
            ArrayList arrayList3 = snapshotRepository.mSnapshotHistories;
            for (int i = 0; i < arrayList3.size(); i++) {
                SnapshotManager.SnapshotHistory snapshotHistory = (SnapshotManager.SnapshotHistory) arrayList3.get(i);
                if (snapshotHistory != null) {
                    typedXmlSerializer.attributeInt((String) null, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "type"), snapshotHistory.type);
                    typedXmlSerializer.attributeInt((String) null, "key" + i, snapshotHistory.key);
                    typedXmlSerializer.attribute((String) null, "time" + i, snapshotHistory.time);
                    String str = snapshotHistory.desc;
                    if (!TextUtils.isEmpty(str)) {
                        typedXmlSerializer.attribute((String) null, "desc" + i, str);
                    }
                    Map map = snapshotHistory.results;
                    if (map == null) {
                        typedXmlSerializer.attributeInt((String) null, "resultCount" + i, 0);
                    } else {
                        int size2 = map.size();
                        typedXmlSerializer.attributeInt((String) null, "resultCount" + i, size2);
                        if (size2 > 0) {
                            int i2 = 0;
                            for (Map.Entry entry : snapshotHistory.results.entrySet()) {
                                typedXmlSerializer.attributeInt((String) null, "which" + String.valueOf(i) + String.valueOf(i2), ((Integer) entry.getKey()).intValue());
                                typedXmlSerializer.attributeInt((String) null, KnoxCustomManagerService.SPCM_KEY_RESULT + String.valueOf(i) + String.valueOf(i2), ((Integer) entry.getValue()).intValue());
                                i2++;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void writeSnapshotSettingsData(XmlSerializer xmlSerializer, String str, HashMap hashMap, int i) {
        if (hashMap == null) {
            return;
        }
        xmlSerializer.startTag(null, str);
        xmlSerializer.attribute(null, "which", Integer.toString(i));
        for (Map.Entry entry : hashMap.entrySet()) {
            xmlSerializer.attribute(null, (String) entry.getKey(), Integer.toString(((Integer) entry.getValue()).intValue()));
        }
        xmlSerializer.endTag(null, str);
    }
}
