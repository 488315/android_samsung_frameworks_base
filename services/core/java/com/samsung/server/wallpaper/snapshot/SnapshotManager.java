package com.samsung.server.wallpaper.snapshot;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.util.JournaledFile;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;

import libcore.io.IoUtils;

import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SnapshotManager {
    public final SnapshotCallback mCallback;
    public final Context mContext;
    public final SparseArray mSnapshotRepositories = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PerWhichSnapshot implements Cloneable {
        public WallpaperData wallpaper;
        public Map settings = new HashMap();
        public int connectedSnapshotForLiveWallpaper = -1;

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public final PerWhichSnapshot m1239clone() {
            PerWhichSnapshot perWhichSnapshot = new PerWhichSnapshot();
            perWhichSnapshot.wallpaper = this.wallpaper.m1039clone();
            for (Map.Entry entry : this.settings.entrySet()) {
                String str = (String) entry.getKey();
                Integer num = (Integer) entry.getValue();
                num.intValue();
                perWhichSnapshot.settings.put(str, num);
            }
            perWhichSnapshot.connectedSnapshotForLiveWallpaper =
                    this.connectedSnapshotForLiveWallpaper;
            return perWhichSnapshot;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SnapshotData {
        public boolean isFromPairedService;
        public final int key;
        public final Map perWhichSnapshots = new HashMap();
        public String source = "";
        public final int userId;

        public SnapshotData(int i, int i2) {
            this.userId = i;
            this.key = i2;
        }

        public final int getConnectedSnapshotForLiveWallpaper(int i) {
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot) ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                return perWhichSnapshot.connectedSnapshotForLiveWallpaper;
            }
            return -1;
        }

        public final WallpaperData getWallpaperData(int i) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot)
                            ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot != null) {
                return perWhichSnapshot.wallpaper;
            }
            return null;
        }

        public final ArrayList getWhiches() {
            ArrayList arrayList = new ArrayList();
            for (Integer num : ((HashMap) this.perWhichSnapshots).keySet()) {
                if (WhichChecker.isSystem(num.intValue())) {
                    arrayList.add(num);
                }
            }
            for (Integer num2 : ((HashMap) this.perWhichSnapshots).keySet()) {
                if (WhichChecker.isLock(num2.intValue())) {
                    arrayList.add(num2);
                }
            }
            return arrayList;
        }

        public final boolean hasWallpaperData() {
            return ((HashMap) this.perWhichSnapshots).size() > 0;
        }

        public final boolean hasWallpaperData(int i) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            Log.d("SnapshotManager", "hasWallpaperData: which = " + checkWhich);
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot)
                            ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot != null && perWhichSnapshot.wallpaper != null) {
                return true;
            }
            Log.d("SnapshotManager", "hasWallpaperData: [" + checkWhich + "] NOT exists.");
            return false;
        }

        public final void setConnectedSnapshotForLiveWallpaper(int i, int i2) {
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot) ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                perWhichSnapshot.connectedSnapshotForLiveWallpaper = i2;
            }
        }

        public final void setLockscreenVisibility(int i, int i2) {
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot) ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                if (WhichChecker.isSubDisplay(i)) {
                    perWhichSnapshot.settings.put("lockscreen_wallpaper_sub", Integer.valueOf(i2));
                } else {
                    perWhichSnapshot.settings.put("lockscreen_wallpaper", Integer.valueOf(i2));
                }
            }
        }

        public final void setSettingsData(int i, HashMap hashMap) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot)
                            ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot == null) {
                perWhichSnapshot = new PerWhichSnapshot();
                ((HashMap) this.perWhichSnapshots)
                        .put(Integer.valueOf(checkWhich), perWhichSnapshot);
            }
            perWhichSnapshot.settings = hashMap;
        }

        public final void setWallpaperData(int i, WallpaperData wallpaperData) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            PerWhichSnapshot perWhichSnapshot =
                    (PerWhichSnapshot)
                            ((HashMap) this.perWhichSnapshots).get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot == null) {
                perWhichSnapshot = new PerWhichSnapshot();
                ((HashMap) this.perWhichSnapshots)
                        .put(Integer.valueOf(checkWhich), perWhichSnapshot);
            }
            perWhichSnapshot.wallpaper = wallpaperData;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SnapshotHistory {
        public String desc;
        public int key;
        public Map results;
        public String time;
        public int type;

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            int i = this.type;
            if (i == 1) {
                stringBuffer.append(String.format("%-15s", "BACKUP"));
            } else if (i == 2) {
                stringBuffer.append(String.format("%-15s", "RESTORE"));
            } else if (i == 3) {
                stringBuffer.append(String.format("%-15s", "CLEAR"));
            } else if (i == 4) {
                stringBuffer.append(String.format("%-15s", "PARTIAL CLEAR"));
            } else if (i != 5) {
                stringBuffer.append(String.format("%-15s", "UNKNOWN"));
            } else {
                stringBuffer.append(String.format("%-15s", "RESTORE TB"));
            }
            stringBuffer.append(String.format("key = %2d", Integer.valueOf(this.key)));
            stringBuffer.append(String.format("%30s", this.time));
            Map map = this.results;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    Integer num = (Integer) entry.getKey();
                    num.intValue();
                    Integer num2 = (Integer) entry.getValue();
                    int intValue = num2.intValue();
                    stringBuffer.append("\t");
                    stringBuffer.append(String.format("{%-3d,", num));
                    if (intValue == -9) {
                        stringBuffer.append("Unknown error");
                    } else if (intValue == -4) {
                        stringBuffer.append("Maximum numer exceeded");
                    } else if (intValue == -3) {
                        stringBuffer.append("Already has backup wallpaper");
                    } else if (intValue != -2) {
                        switch (intValue) {
                            case 1001:
                                stringBuffer.append("Success");
                                break;
                            case 1002:
                                stringBuffer.append("Reset to default");
                                break;
                            case 1003:
                                stringBuffer.append("No data");
                                break;
                            case 1004:
                                stringBuffer.append("Migrate to prior data");
                                break;
                            default:
                                stringBuffer.append(String.format(" %-2d", num2));
                                break;
                        }
                    } else {
                        stringBuffer.append("Failed to copy file");
                    }
                    stringBuffer.append("}");
                }
            }
            String str = this.desc;
            if (!TextUtils.isEmpty(str)) {
                stringBuffer.append("\t");
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SnapshotRepository {
        public final int userId;
        public int key = 0;
        public final LinkedList mSnapshots = new LinkedList();
        public final ArrayList mSnapshotHistories = new ArrayList();

        public SnapshotRepository(int i) {
            this.userId = i;
        }

        public final void add(SnapshotData snapshotData) {
            if (snapshotData == null) {
                Log.e("SnapshotManager", "add: wallpaper is null.");
                return;
            }
            if (this.mSnapshots.size() >= 100) {
                Log.e("SnapshotManager", "add: Maximum backup data capacity is exceeded.");
                return;
            }
            int i = snapshotData.key;
            if (getByKey(i) == null) {
                this.mSnapshots.addFirst(snapshotData);
                return;
            }
            Log.e(
                    "SnapshotManager",
                    "add: Backup wallpaper for key [" + i + "] is already exist. Drop this one.");
        }

        public final void addHistory(SnapshotHistory snapshotHistory) {
            if (this.mSnapshotHistories.size() > 30) {
                this.mSnapshotHistories.remove(0);
            }
            this.mSnapshotHistories.add(snapshotHistory);
        }

        public final ArrayList getAll() {
            if (this.mSnapshots != null) {
                return new ArrayList(this.mSnapshots);
            }
            return null;
        }

        public final SnapshotData getByIndex(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList == null || linkedList.size() <= 0) {
                return null;
            }
            return (SnapshotData) this.mSnapshots.get(i);
        }

        public final SnapshotData getByKey(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null && linkedList.size() > 0) {
                Iterator it = this.mSnapshots.iterator();
                while (it.hasNext()) {
                    SnapshotData snapshotData = (SnapshotData) it.next();
                    if (snapshotData.key == i) {
                        return snapshotData;
                    }
                }
            }
            return null;
        }

        public final int getIndex(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null && linkedList.size() > 0) {
                for (int i2 = 0; i2 < this.mSnapshots.size(); i2++) {
                    if (((SnapshotData) this.mSnapshots.get(i2)).key == i) {
                        return i2;
                    }
                }
            }
            return -1;
        }

        public final void remove(int i, int i2) {
            SnapshotData byKey = getByKey(i);
            if (byKey != null) {
                if (byKey.hasWallpaperData(i2)) {
                    int checkWhich = SnapshotHelper.checkWhich(i2);
                    ((HashMap) byKey.perWhichSnapshots).remove(Integer.valueOf(checkWhich));
                }
                if (byKey.hasWallpaperData()) {
                    return;
                }
                removeByKey(i);
            }
        }

        public final SnapshotData removeByKey(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList == null || linkedList.size() <= 0) {
                return null;
            }
            int index = getIndex(i);
            if (index != -1) {
                return (SnapshotData) this.mSnapshots.remove(index);
            }
            Log.d("SnapshotManager", "remove: SnapshotData for key " + i + " is not exist.");
            return null;
        }

        public final int size() {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null) {
                return linkedList.size();
            }
            return 0;
        }
    }

    public SnapshotManager(Context context, SnapshotCallback snapshotCallback) {
        this.mContext = context;
        this.mCallback = snapshotCallback;
    }

    public final void addHistory(int i, int i2) {
        SnapshotRepository repositroy = getRepositroy(i);
        SnapshotHistory snapshotHistory = new SnapshotHistory();
        snapshotHistory.type = 2;
        snapshotHistory.key = i2;
        snapshotHistory.time =
                SimpleDateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
        snapshotHistory.desc = "No snapshot";
        repositroy.addHistory(snapshotHistory);
    }

    public final void addHistory(int i, int i2, int i3, Map map) {
        SnapshotRepository repositroy = getRepositroy(i);
        SnapshotHistory snapshotHistory = new SnapshotHistory();
        snapshotHistory.type = i2;
        snapshotHistory.key = i3;
        snapshotHistory.time =
                SimpleDateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
        snapshotHistory.results = map;
        repositroy.addHistory(snapshotHistory);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0127, code lost:

       if (r5 != 8) goto L65;
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int addSnapshot(
            android.content.Context r20,
            int r21,
            int r22,
            int r23,
            com.android.server.wallpaper.WallpaperData r24) {
        /*
            Method dump skipped, instructions count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.server.wallpaper.snapshot.SnapshotManager.addSnapshot(android.content.Context,"
                    + " int, int, int, com.android.server.wallpaper.WallpaperData):int");
    }

    public final int getPairedDlsSnapshotKey(int i, int i2) {
        SnapshotData snapshot = getSnapshot(i, i2);
        if (snapshot == null) {
            return -1;
        }
        ArrayList whiches = snapshot.getWhiches();
        if (whiches.size() != 1) {
            Log.d("SnapshotManager", "getPairedDlsSnapshotKey: Number of whiches is not 1.");
            return -1;
        }
        int i3 = 0;
        int intValue = ((Integer) whiches.get(0)).intValue();
        if (Rune.SUPPORT_PAIRED_DLS_SNAPSHOT && !WhichChecker.isSystem(intValue)) {
            i3 = WhichChecker.isSubDisplay(intValue) ? 6 : 18;
        }
        Iterator it = getRepositroy(i).getAll().iterator();
        while (it.hasNext()) {
            SnapshotData snapshotData = (SnapshotData) it.next();
            if ("com.samsung.android.dynamiclock".equals(snapshotData.source)
                    && snapshotData.hasWallpaperData(i3)
                    && snapshotData.isFromPairedService) {
                StringBuilder sb = new StringBuilder("getPairedDlsSnapshotKey: key = ");
                int i4 = snapshotData.key;
                sb.append(i4);
                Log.d("SnapshotManager", sb.toString());
                return i4;
            }
        }
        return -1;
    }

    public final SnapshotRepository getRepositroy(int i) {
        SnapshotRepository snapshotRepository =
                (SnapshotRepository) this.mSnapshotRepositories.get(i);
        if (snapshotRepository != null) {
            return snapshotRepository;
        }
        SnapshotRepository snapshotRepository2 = new SnapshotRepository(i);
        this.mSnapshotRepositories.put(i, snapshotRepository2);
        return snapshotRepository2;
    }

    public final SnapshotData getSnapshot(int i, int i2) {
        return getRepositroy(i).getByKey(i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:65:0x0082. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02ef A[Catch: IndexOutOfBoundsException -> 0x015a, IOException -> 0x0160, XmlPullParserException -> 0x0168, NullPointerException -> 0x0170, FileNotFoundException -> 0x0178, NumberFormatException -> 0x018a, TRY_LEAVE, TryCatch #59 {NumberFormatException -> 0x018a, blocks: (B:35:0x0370, B:98:0x0144, B:103:0x0184, B:107:0x0180, B:111:0x0195, B:113:0x019a, B:117:0x01a2, B:122:0x01ce, B:178:0x025e, B:191:0x02a0, B:195:0x02b9, B:197:0x02c1, B:198:0x02c7, B:200:0x02cd, B:202:0x02d9, B:217:0x02ef, B:25:0x0336, B:27:0x0340, B:29:0x0348, B:62:0x0357), top: B:34:0x0370 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x04ab  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadSettingsLockedForSnapshot(int r25) {
        /*
            Method dump skipped, instructions count: 1228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.server.wallpaper.snapshot.SnapshotManager.loadSettingsLockedForSnapshot(int):void");
    }

    public final void migrateToPriorSnapshot(int i, int i2, int i3) {
        File file;
        SnapshotRepository repositroy = getRepositroy(i);
        int index = repositroy.getIndex(i2);
        SnapshotData byIndex = repositroy.getByIndex(index);
        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT || byIndex == null) {
            file = null;
        } else {
            WallpaperData wallpaperData = byIndex.getWallpaperData(i3);
            file = wallpaperData.getWallpaperFile(wallpaperData.mSemWallpaperData.mWpType);
        }
        for (int i4 = index - 1; i4 >= 0; i4--) {
            SnapshotData byIndex2 = repositroy.getByIndex(i4);
            if (byIndex2 == null) {
                Log.e("SnapshotManager", "migrateToPriorSnapshot: Something wrong!");
            } else if (byIndex2.hasWallpaperData(i3)) {
                WallpaperData wallpaperData2 = byIndex2.getWallpaperData(i3);
                File wallpaperFile =
                        wallpaperData2.getWallpaperFile(wallpaperData2.mSemWallpaperData.mWpType);
                if (byIndex != null) {
                    ((HashMap) byIndex2.perWhichSnapshots)
                            .put(
                                    Integer.valueOf(i3),
                                    ((PerWhichSnapshot)
                                                    ((HashMap) byIndex.perWhichSnapshots)
                                                            .get(Integer.valueOf(i3)))
                                            .m1239clone());
                }
                int connectedSnapshotForLiveWallpaper =
                        byIndex2.getConnectedSnapshotForLiveWallpaper(i3);
                int i5 = byIndex2.key;
                if (connectedSnapshotForLiveWallpaper != -1) {
                    SnapshotData snapshot = getSnapshot(i, connectedSnapshotForLiveWallpaper);
                    int correspondingWhich = SnapshotHelper.getCorrespondingWhich(i3);
                    if (snapshot == null || !snapshot.hasWallpaperData(correspondingWhich)) {
                        byIndex2.setConnectedSnapshotForLiveWallpaper(i3, -1);
                    } else {
                        snapshot.setConnectedSnapshotForLiveWallpaper(correspondingWhich, i5);
                    }
                }
                Log.d(
                        "SnapshotManager",
                        "migrateToPriorSnapshot: source = " + file + ", target = " + wallpaperFile);
                if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
                    if (byIndex != null) {
                        SnapshotHelper.renameDirectory(
                                SnapshotHelper.getBackupWallpaperDir(i, byIndex.key, i3),
                                SnapshotHelper.getBackupWallpaperDir(i, i5, i3));
                    }
                    byIndex2.getWallpaperData(i3)
                            .setWallpaperFile(SnapshotHelper.getBackupWallpaperFile(i, i5, i3));
                    return;
                } else if (file == null || !file.exists()) {
                    Log.d("SnapshotManager", "migrateToPriorSnapshot: source does not exist.");
                    SnapshotHelper.deleteFile(wallpaperFile);
                    return;
                } else {
                    if (wallpaperFile == null) {
                        wallpaperFile = SnapshotHelper.getBackupWallpaperFile(i, i5, i3);
                    }
                    SnapshotHelper.saveFile(file, wallpaperFile);
                    byIndex2.getWallpaperData(i3).setWallpaperFile(wallpaperFile);
                    return;
                }
            }
        }
    }

    public final Map removeSnapshotByKey(int i, int i2) {
        SnapshotData removeByKey = getRepositroy(i).removeByKey(i2);
        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
            if (removeByKey != null) {
                SnapshotHelper.deleteDirectory(
                        new File(
                                Environment.getUserSystemDirectory(removeByKey.userId),
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        removeByKey.key, "wallpaper_backup/")));
            }
        } else if (removeByKey != null) {
            Iterator it = removeByKey.getWhiches().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                WallpaperData wallpaperData = removeByKey.getWallpaperData(intValue);
                File wallpaperFile =
                        wallpaperData.getWallpaperFile(wallpaperData.mSemWallpaperData.mWpType);
                Log.d(
                        "SnapshotManager",
                        "removeBackupFiles: which = "
                                + intValue
                                + ",wallpaperFile  = "
                                + wallpaperFile
                                + ", cropFile = "
                                + wallpaperData.getCropFile());
                SnapshotHelper.deleteFile(wallpaperFile);
            }
        }
        HashMap hashMap = new HashMap();
        if (removeByKey != null) {
            Iterator it2 = removeByKey.getWhiches().iterator();
            while (it2.hasNext()) {
                Integer num = (Integer) it2.next();
                num.getClass();
                hashMap.put(num, 1);
            }
        }
        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
            if (i2 > 0) {
                SnapshotHelper.deleteDirectory(
                        new File(
                                Environment.getUserSystemDirectory(i),
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        i2, "wallpaper_backup/")));
            }
        } else if (i2 > 0) {
            int i3 = 2;
            int[] iArr = {1, 2};
            int i4 = 4;
            int[] iArr2 = {4, 16, 8, 32};
            int i5 = 0;
            while (i5 < i3) {
                int i6 = iArr[i5];
                int i7 = 0;
                while (i7 < i4) {
                    try {
                        File[] listBackupFiles = SnapshotHelper.listBackupFiles(i, iArr2[i7] | i6);
                        if (listBackupFiles != null) {
                            for (File file : listBackupFiles) {
                                if (Integer.parseInt(file.getName().split("_")[0]) == i2
                                        && !file.delete()) {
                                    Log.w(
                                            "SnapshotHelper",
                                            "deleteFilesByKeyLegacy: failed to delete " + file);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("SnapshotHelper", "deleteFilesByKeyLegacy: " + e.getMessage());
                    }
                    i7++;
                    i4 = 4;
                }
                i5++;
                i3 = 2;
                i4 = 4;
            }
        }
        return hashMap;
    }

    public final ArrayList removeSnapshotByWhich(int i, int i2) {
        SnapshotRepository repositroy = getRepositroy(i);
        ArrayList all = repositroy.getAll();
        ArrayList arrayList = new ArrayList();
        Iterator it = all.iterator();
        while (it.hasNext()) {
            SnapshotData snapshotData = (SnapshotData) it.next();
            snapshotData.getClass();
            ((HashMap) snapshotData.perWhichSnapshots)
                    .remove(Integer.valueOf(SnapshotHelper.checkWhich(i2)));
            WallpaperData wallpaperData = snapshotData.getWallpaperData(i2);
            if (wallpaperData != null) {
                SnapshotHelper.deleteFile(
                        wallpaperData.getWallpaperFile(wallpaperData.mSemWallpaperData.mWpType));
                SnapshotHelper.deleteFile(wallpaperData.getCropFile());
            }
            if (!snapshotData.hasWallpaperData()) {
                repositroy.removeByKey(snapshotData.key);
                arrayList.add(snapshotData);
            }
        }
        if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
            if (i2 > 0) {
                try {
                    File[] listFiles =
                            new File(Environment.getUserSystemDirectory(i), "wallpaper_backup")
                                    .listFiles();
                    if (listFiles != null && listFiles.length > 0) {
                        for (int i3 = 0; i3 < listFiles.length; i3++) {
                            if (listFiles[i3].isDirectory()) {
                                File[] listFiles2 = listFiles[i3].listFiles();
                                if (listFiles2 != null && listFiles2.length > 0) {
                                    for (int i4 = 0; i4 < listFiles2.length; i4++) {
                                        if (Integer.parseInt(listFiles2[i4].getName()) == i2) {
                                            Log.d(
                                                    "SnapshotHelper",
                                                    "deleteFilesByWhich: "
                                                            + listFiles2[i4].getPath());
                                            SnapshotHelper.deleteDirectory(listFiles2[i4]);
                                        }
                                    }
                                }
                                File[] listFiles3 = listFiles[i3].listFiles();
                                if (listFiles3 == null || listFiles3.length <= 0) {
                                    SnapshotHelper.deleteDirectory(listFiles[i3]);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d("SnapshotHelper", "deleteFilesByWhich: " + e.getMessage());
                }
            }
        } else if (i2 > 0) {
            try {
                File[] listBackupFiles = SnapshotHelper.listBackupFiles(i, i2);
                if (listBackupFiles != null) {
                    for (File file : listBackupFiles) {
                        if (Integer.parseInt(file.getName().split("_")[1]) == i2
                                && !file.delete()) {
                            Log.w(
                                    "SnapshotHelper",
                                    "deleteFilesByWhichLegacy: failed to delete " + file);
                        }
                    }
                }
            } catch (Exception e2) {
                Log.e("SnapshotHelper", "deleteFilesByWhichLegacy: " + e2, e2);
            }
        }
        return arrayList;
    }

    public final void saveSettingsLockedForSnapshot(int i) {
        SnapshotRepository repositroy = getRepositroy(i);
        SnapshotCallback snapshotCallback = this.mCallback;
        String absolutePath =
                new File(Environment.getUserSystemDirectory(i), "wallpaper_backup_info.xml")
                        .getAbsolutePath();
        JournaledFile journaledFile =
                new JournaledFile(
                        new File(absolutePath),
                        new File(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                        absolutePath, ".tmp")));
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 =
                    new FileOutputStream(journaledFile.chooseForWrite(), false);
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream2);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                if (repositroy.size() > 0) {
                    Log.d(
                            "SnapshotHelper",
                            "saveSettingsLockedForSnapshot: Backup data size = "
                                    + repositroy.size());
                    for (int size = repositroy.size() + (-1); size >= 0; size--) {
                        SnapshotData byIndex = repositroy.getByIndex(size);
                        if (byIndex != null) {
                            resolveSerializer.startTag((String) null, "Backup");
                            resolveSerializer.attribute(
                                    (String) null, "key", Integer.toString(byIndex.key));
                            resolveSerializer.attribute((String) null, "source", byIndex.source);
                            Iterator it = byIndex.getWhiches().iterator();
                            while (it.hasNext()) {
                                int intValue = ((Integer) it.next()).intValue();
                                WallpaperData wallpaperData = byIndex.getWallpaperData(intValue);
                                if (wallpaperData != null) {
                                    String str = WhichChecker.isSystem(intValue) ? "wp" : "kwp";
                                    if (WhichChecker.isSystem(intValue)
                                            || WhichChecker.isSupportLock(intValue)) {
                                        ((WallpaperManagerService) snapshotCallback)
                                                .requestWriteWallpaperAttributes(
                                                        resolveSerializer, str, wallpaperData);
                                        PerWhichSnapshot perWhichSnapshot =
                                                (PerWhichSnapshot)
                                                        ((HashMap) byIndex.perWhichSnapshots)
                                                                .get(
                                                                        Integer.valueOf(
                                                                                SnapshotHelper
                                                                                        .checkWhich(
                                                                                                intValue)));
                                        SnapshotHelper.writeSnapshotSettingsData(
                                                resolveSerializer,
                                                str + "Settings",
                                                (HashMap)
                                                        (perWhichSnapshot != null
                                                                ? perWhichSnapshot.settings
                                                                : null),
                                                intValue);
                                    }
                                }
                            }
                            resolveSerializer.endTag((String) null, "Backup");
                        }
                    }
                }
                ArrayList arrayList = repositroy.mSnapshotHistories;
                if ((arrayList != null ? arrayList.size() : 0) > 0) {
                    resolveSerializer.startTag((String) null, "History");
                    SnapshotHelper.writeSnapshotHistory(resolveSerializer, repositroy);
                    resolveSerializer.endTag((String) null, "History");
                }
                resolveSerializer.endDocument();
                fileOutputStream2.flush();
                FileUtils.sync(fileOutputStream2);
                fileOutputStream2.close();
                journaledFile.commit();
            } catch (IOException
                    | IllegalArgumentException
                    | IllegalStateException
                    | IndexOutOfBoundsException unused) {
                fileOutputStream = fileOutputStream2;
                IoUtils.closeQuietly(fileOutputStream);
                journaledFile.rollback();
            }
        } catch (IOException
                | IllegalArgumentException
                | IllegalStateException
                | IndexOutOfBoundsException unused2) {
        }
    }
}
