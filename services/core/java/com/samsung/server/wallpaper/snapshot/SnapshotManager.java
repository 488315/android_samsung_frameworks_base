package com.samsung.server.wallpaper.snapshot;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.server.wallpaper.WallpaperData;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.PreloadedLiveWallpaperHelper;
import com.samsung.server.wallpaper.snapshot.SnapshotHelper;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SnapshotManager {
    public final SnapshotCallback mCallback;
    public final Context mContext;
    public SparseArray mSnapshotRepositories = new SparseArray();

    public SnapshotManager(Context context, SnapshotCallback snapshotCallback) {
        this.mContext = context;
        this.mCallback = snapshotCallback;
    }

    public int getSnapshotCount(int i) {
        return getRepositroy(i).size();
    }

    public int getSnapshotCount(int i, int i2) {
        SnapshotRepository repositroy = getRepositroy(i);
        int i3 = 0;
        if (repositroy.size() > 0) {
            Iterator it = repositroy.getAll().iterator();
            while (it.hasNext()) {
                if (((SnapshotData) it.next()).hasWallpaperData(i2)) {
                    i3++;
                }
            }
        }
        return i3;
    }

    public SnapshotData getNearestSnapshot(int i, int i2) {
        Iterator it = getRepositroy(i).getAll().iterator();
        while (it.hasNext()) {
            SnapshotData snapshotData = (SnapshotData) it.next();
            if (snapshotData.hasWallpaperData(i2)) {
                return snapshotData;
            }
        }
        return null;
    }

    public int getPairedDlsSnapshotKey(int i, int i2) {
        SnapshotData snapshot = getSnapshot(i, i2);
        if (snapshot == null) {
            return -1;
        }
        ArrayList whiches = snapshot.getWhiches();
        if (whiches.size() != 1) {
            Log.m129d("SnapshotManager", "getPairedDlsSnapshotKey: Number of whiches is not 1.");
            return -1;
        }
        int correspondingWhichForDls = SnapshotHelper.getCorrespondingWhichForDls(((Integer) whiches.get(0)).intValue());
        Iterator it = getRepositroy(i).getAll().iterator();
        while (it.hasNext()) {
            SnapshotData snapshotData = (SnapshotData) it.next();
            if ("com.samsung.android.dynamiclock".equals(snapshotData.getSource()) && snapshotData.hasWallpaperData(correspondingWhichForDls) && snapshotData.isFromPairedService()) {
                Log.m129d("SnapshotManager", "getPairedDlsSnapshotKey: key = " + snapshotData.getKey());
                return snapshotData.getKey();
            }
        }
        return -1;
    }

    public SnapshotRepository getRepositroy(int i) {
        SnapshotRepository snapshotRepository = (SnapshotRepository) this.mSnapshotRepositories.get(i);
        if (snapshotRepository != null) {
            return snapshotRepository;
        }
        SnapshotRepository snapshotRepository2 = new SnapshotRepository(i);
        this.mSnapshotRepositories.put(i, snapshotRepository2);
        return snapshotRepository2;
    }

    public ArrayList getAllSnapshots(int i) {
        return getRepositroy(i).getAll();
    }

    public SnapshotData getSnapshot(int i, int i2) {
        return getRepositroy(i).getByKey(i2);
    }

    public SnapshotData getLastSnapshot(int i) {
        return getRepositroy(i).getLastSnapshot();
    }

    public SnapshotData getLastSnapshotByWhich(int i, int i2) {
        Iterator it = getRepositroy(i).getAll().iterator();
        while (it.hasNext()) {
            SnapshotData snapshotData = (SnapshotData) it.next();
            if (snapshotData.hasWallpaperData(i2)) {
                return snapshotData;
            }
        }
        Log.m129d("SnapshotManager", "getLastSnapshotByWhich: userId = " + i + ", which = " + i2 + "No snapshot for the which.");
        return null;
    }

    public int makeSnapshotKey(int i) {
        return getRepositroy(i).makeKey();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002b, code lost:
    
        if (r6 != 8) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int addSnapshot(Context context, int i, int i2, int i3, WallpaperData wallpaperData) {
        SnapshotRepository repositroy = getRepositroy(i);
        int add = repositroy.add(context, i3, i2, wallpaperData);
        if (add < 0) {
            if (add != -3) {
                repositroy.remove(i3, i2);
            }
            return add;
        }
        int wpType = wallpaperData.getSemWallpaperData().getWpType();
        if (wpType != 0 && wpType != 1 && wpType != 4) {
            if (wpType == 7) {
                if (Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
                    PreloadedLiveWallpaperHelper.recoverComponentNameIfMissed(wallpaperData);
                    if (!SnapshotHelper.saveBackupFileForLiveWallpaper(i, i2, i3, wallpaperData)) {
                        Log.m130e("SnapshotManager", "addSnapshot: Failed to copy backup file.");
                        repositroy.remove(i3, i2);
                        return -2;
                    }
                }
            }
            this.mSnapshotRepositories.put(i, repositroy);
            return i3;
        }
        if (!SnapshotHelper.saveBackupFile(i, i2, i3, wallpaperData)) {
            Log.m130e("SnapshotManager", "addSnapshot: Failed to copy backup file.");
            repositroy.remove(i3, i2);
            return -2;
        }
        this.mSnapshotRepositories.put(i, repositroy);
        return i3;
    }

    public Map removeSnapshotByKey(int i, int i2) {
        SnapshotData removeByKey = getRepositroy(i).removeByKey(i2);
        removeBackupFiles(removeByKey);
        HashMap hashMap = new HashMap();
        if (removeByKey != null) {
            Iterator it = removeByKey.getWhiches().iterator();
            while (it.hasNext()) {
                hashMap.put(Integer.valueOf(((Integer) it.next()).intValue()), 1);
            }
        }
        SnapshotHelper.deleteFilesByKey(i, i2);
        return hashMap;
    }

    public void removeSnapshotByWhich(int i, int i2) {
        SnapshotRepository repositroy = getRepositroy(i);
        Iterator it = repositroy.getAll().iterator();
        while (it.hasNext()) {
            SnapshotData snapshotData = (SnapshotData) it.next();
            snapshotData.remove(i2);
            SnapshotHelper.deleteFiles(snapshotData.getWallpaperData(i2));
            if (!snapshotData.hasWallpaperData()) {
                repositroy.removeByKey(snapshotData.getKey());
            }
        }
        SnapshotHelper.deleteFilesByWhich(i, i2);
    }

    public void migrateToPriorSnapshot(int i, int i2, int i3) {
        SnapshotRepository repositroy = getRepositroy(i);
        if (repositroy == null) {
            return;
        }
        int index = repositroy.getIndex(i2);
        SnapshotData byIndex = repositroy.getByIndex(index);
        File wallpaperFile = !Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT ? byIndex.getWallpaperData(i3).getWallpaperFile() : null;
        for (int i4 = index - 1; i4 >= 0; i4--) {
            SnapshotData byIndex2 = repositroy.getByIndex(i4);
            if (byIndex2 == null) {
                Log.m130e("SnapshotManager", "migrateToPriorSnapshot: Something wrong!");
            } else if (byIndex2.hasWallpaperData(i3)) {
                File wallpaperFile2 = byIndex2.getWallpaperData(i3).getWallpaperFile();
                byIndex2.setPerWhichSnapshot(i3, byIndex.getPerWhichSnapshot(i3).m14854clone());
                int connectedSnapshotForLiveWallpaper = byIndex2.getConnectedSnapshotForLiveWallpaper(i3);
                if (connectedSnapshotForLiveWallpaper != -1) {
                    SnapshotData snapshot = getSnapshot(i, connectedSnapshotForLiveWallpaper);
                    int correspondingWhich = SnapshotHelper.getCorrespondingWhich(i3);
                    if (snapshot != null && snapshot.hasWallpaperData(correspondingWhich)) {
                        snapshot.setConnectedSnapshotForLiveWallpaper(correspondingWhich, byIndex2.getKey());
                    } else {
                        byIndex2.setConnectedSnapshotForLiveWallpaper(i3, -1);
                    }
                }
                Log.m129d("SnapshotManager", "migrateToPriorSnapshot: source = " + wallpaperFile + ", target = " + wallpaperFile2);
                if (!Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
                    if (wallpaperFile == null || !wallpaperFile.exists()) {
                        Log.m129d("SnapshotManager", "migrateToPriorSnapshot: source does not exist.");
                        SnapshotHelper.deleteFile(wallpaperFile2);
                        return;
                    } else {
                        if (wallpaperFile2 == null) {
                            wallpaperFile2 = SnapshotHelper.getBackupWallpaperFile(i, byIndex2.getKey(), i3);
                        }
                        SnapshotHelper.saveFile(wallpaperFile, wallpaperFile2);
                        byIndex2.getWallpaperData(i3).setWallpaperFile(wallpaperFile2);
                        return;
                    }
                }
                SnapshotHelper.renameDirectory(SnapshotHelper.getBackupWallpaperDir(i, byIndex.getKey(), i3), SnapshotHelper.getBackupWallpaperDir(i, byIndex2.getKey(), i3));
                byIndex2.getWallpaperData(i3).setWallpaperFile(SnapshotHelper.getBackupWallpaperFile(i, byIndex2.getKey(), i3));
                return;
            }
        }
    }

    public boolean shouldRestoreSnapshot(int i, int i2, int i3) {
        SnapshotRepository repositroy = getRepositroy(i);
        if (repositroy == null) {
            return false;
        }
        int index = repositroy.getIndex(i2);
        repositroy.getByIndex(index);
        for (int i4 = index - 1; i4 >= 0; i4--) {
            SnapshotData byIndex = repositroy.getByIndex(i4);
            if (byIndex == null) {
                Log.m130e("SnapshotManager", "shouldRestoreSnapshot: Something wrong!");
            } else if (byIndex.hasWallpaperData(i3)) {
                return false;
            }
        }
        return true;
    }

    public final void removeBackupFilesLegacy(SnapshotData snapshotData) {
        if (snapshotData == null) {
            return;
        }
        Iterator it = snapshotData.getWhiches().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            WallpaperData wallpaperData = snapshotData.getWallpaperData(intValue);
            Log.m129d("SnapshotManager", "removeBackupFiles: which = " + intValue + ",wallpaperFile  = " + wallpaperData.getWallpaperFile() + ", cropFile = " + wallpaperData.getWallpaperCropFile());
            SnapshotHelper.deleteFile(wallpaperData.getWallpaperFile());
        }
    }

    public final void removeBackupFiles(SnapshotData snapshotData) {
        if (!Rune.SUPPORT_LAYERED_WALLPAPER_SNAPSHOT) {
            removeBackupFilesLegacy(snapshotData);
        } else {
            if (snapshotData == null) {
                return;
            }
            SnapshotHelper.deleteDirectory(SnapshotHelper.getBackupWallpaperDir(snapshotData.getUserId(), snapshotData.getKey()));
        }
    }

    public void loadSettingsLockedForSnapshot(int i) {
        SnapshotRepository repositroy = getRepositroy(i);
        repositroy.clear();
        repositroy.setKey(SnapshotHelper.loadSettingsLockedForSnapshot(this.mContext, i, repositroy, this.mCallback));
    }

    public void saveSettingsLockedForSnapshot(int i) {
        SnapshotRepository repositroy = getRepositroy(i);
        if (repositroy == null) {
            Log.m129d("SnapshotManager", "saveSettingsLockedForSnapshot: Nothing to save for user " + i + ".");
            return;
        }
        SnapshotHelper.saveSettingsLockedForSnapshot(this.mContext, i, repositroy, this.mCallback);
    }

    public void addHistory(int i, int i2, int i3, Map map) {
        getRepositroy(i).addHistory(new SnapshotHistory(i2, i3, map));
    }

    public void addHistory(int i, int i2, int i3, String str) {
        getRepositroy(i).addHistory(new SnapshotHistory(i2, i3, str));
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mSnapshotRepositories.size() > 0) {
            for (int i = 0; i < this.mSnapshotRepositories.size(); i++) {
                SnapshotRepository snapshotRepository = (SnapshotRepository) this.mSnapshotRepositories.get(this.mSnapshotRepositories.keyAt(i));
                if (snapshotRepository.getUserId() >= 0) {
                    snapshotRepository.dump(fileDescriptor, printWriter, strArr);
                }
            }
        }
    }

    public class SnapshotRepository {
        public final int userId;
        public int key = 0;
        public final LinkedList mSnapshots = new LinkedList();
        public final ArrayList mSnapshotHistories = new ArrayList();

        public SnapshotRepository(int i) {
            this.userId = i;
        }

        public int getUserId() {
            return this.userId;
        }

        public int makeKey() {
            int i;
            do {
                i = this.key + 1;
                this.key = i;
            } while (i <= 0);
            return i;
        }

        public void setKey(int i) {
            this.key = i;
        }

        public int size() {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null) {
                return linkedList.size();
            }
            return 0;
        }

        public ArrayList getAll() {
            if (this.mSnapshots != null) {
                return new ArrayList(this.mSnapshots);
            }
            return null;
        }

        public SnapshotData getByIndex(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList == null || linkedList.size() <= 0) {
                return null;
            }
            return (SnapshotData) this.mSnapshots.get(i);
        }

        public SnapshotData getByKey(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null && linkedList.size() > 0) {
                Iterator it = this.mSnapshots.iterator();
                while (it.hasNext()) {
                    SnapshotData snapshotData = (SnapshotData) it.next();
                    if (snapshotData.getKey() == i) {
                        return snapshotData;
                    }
                }
            }
            return null;
        }

        public int getIndex(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null && linkedList.size() > 0) {
                for (int i2 = 0; i2 < this.mSnapshots.size(); i2++) {
                    if (((SnapshotData) this.mSnapshots.get(i2)).getKey() == i) {
                        return i2;
                    }
                }
            }
            return -1;
        }

        public SnapshotData getLastSnapshot() {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList == null || linkedList.size() <= 0) {
                return null;
            }
            return (SnapshotData) this.mSnapshots.getFirst();
        }

        public int add(Context context, int i, int i2, WallpaperData wallpaperData) {
            SnapshotData byKey = getByKey(i);
            if (byKey == null) {
                if (this.mSnapshots.size() >= 100) {
                    Log.m130e("SnapshotManager", "add: Maximum number of snapshot is reached.");
                    return -4;
                }
                byKey = new SnapshotData(this.userId, i);
                this.mSnapshots.addFirst(byKey);
            }
            if (byKey.hasWallpaperData(i2)) {
                Log.m130e("SnapshotManager", "add: Already has WallpaperData for which [" + i2 + "].");
                return -3;
            }
            byKey.setWallpaperData(i2, wallpaperData);
            byKey.setSettingsData(context, i2);
            return i;
        }

        public boolean add(SnapshotData snapshotData) {
            if (snapshotData == null) {
                Log.m130e("SnapshotManager", "add: wallpaper is null.");
                return false;
            }
            if (this.mSnapshots.size() >= 100) {
                Log.m130e("SnapshotManager", "add: Maximum backup data capacity is exceeded.");
                return false;
            }
            if (getByKey(snapshotData.getKey()) != null) {
                Log.m130e("SnapshotManager", "add: Backup wallpaper for key [" + snapshotData.getKey() + "] is already exist. Drop this one.");
                return false;
            }
            this.mSnapshots.addFirst(snapshotData);
            return true;
        }

        public void remove(int i, int i2) {
            SnapshotData byKey = getByKey(i);
            if (byKey != null) {
                if (byKey.hasWallpaperData(i2)) {
                    byKey.remove(i2);
                }
                if (byKey.hasWallpaperData()) {
                    return;
                }
                removeByKey(i);
            }
        }

        public SnapshotData removeByKey(int i) {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList == null || linkedList.size() <= 0) {
                return null;
            }
            int index = getIndex(i);
            if (index == -1) {
                Log.m129d("SnapshotManager", "remove: SnapshotData for key " + i + " is not exist.");
                return null;
            }
            return (SnapshotData) this.mSnapshots.remove(index);
        }

        public void clear() {
            LinkedList linkedList = this.mSnapshots;
            if (linkedList != null) {
                linkedList.clear();
            }
        }

        public void addHistory(SnapshotHistory snapshotHistory) {
            if (this.mSnapshotHistories.size() > 30) {
                this.mSnapshotHistories.remove(0);
            }
            this.mSnapshotHistories.add(snapshotHistory);
        }

        public int getHistoryLength() {
            ArrayList arrayList = this.mSnapshotHistories;
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }

        public List getHistory() {
            return this.mSnapshotHistories;
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.println("userId = " + this.userId);
            printWriter.println("lastKey = " + this.key);
            if (this.mSnapshotHistories.size() > 0) {
                Iterator it = this.mSnapshotHistories.iterator();
                while (it.hasNext()) {
                    printWriter.println((SnapshotHistory) it.next());
                }
            }
            Iterator it2 = getAll().iterator();
            while (it2.hasNext()) {
                ((SnapshotData) it2.next()).dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public class PerWhichSnapshot implements Cloneable {
        public WallpaperData wallpaper;
        public Map settings = new HashMap();
        public int connectedSnapshotForLiveWallpaper = -1;

        public void setLockscreenVisibility(boolean z, int i) {
            if (z) {
                this.settings.put("lockscreen_wallpaper_sub", Integer.valueOf(i));
            } else {
                this.settings.put("lockscreen_wallpaper", Integer.valueOf(i));
            }
        }

        public int getLockscreenVisibility(boolean z) {
            String str = z ? "lockscreen_wallpaper_sub" : "lockscreen_wallpaper";
            return ((Integer) this.settings.getOrDefault(str, Integer.valueOf(SnapshotHelper.SettingsData.getDefaultValue(str)))).intValue();
        }

        public void setConnectedSnapshotForLiveWallpaper(int i) {
            this.connectedSnapshotForLiveWallpaper = i;
        }

        public int getConnectedSnapshotForLiveWallpaper() {
            return this.connectedSnapshotForLiveWallpaper;
        }

        public void setSettings(Map map) {
            this.settings = map;
        }

        public Map getSettings() {
            return this.settings;
        }

        public void setWallpaperData(WallpaperData wallpaperData) {
            this.wallpaper = wallpaperData;
        }

        public WallpaperData getWallpaperData() {
            return this.wallpaper;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public PerWhichSnapshot m14854clone() {
            PerWhichSnapshot perWhichSnapshot = new PerWhichSnapshot();
            perWhichSnapshot.wallpaper = this.wallpaper.m12812clone();
            for (Map.Entry entry : this.settings.entrySet()) {
                perWhichSnapshot.settings.put((String) entry.getKey(), Integer.valueOf(((Integer) entry.getValue()).intValue()));
            }
            perWhichSnapshot.connectedSnapshotForLiveWallpaper = this.connectedSnapshotForLiveWallpaper;
            return perWhichSnapshot;
        }
    }

    public class SnapshotData {
        public boolean isFromPairedService;
        public int key;
        public Map perWhichSnapshots = new HashMap();
        public String source = "";
        public int userId;

        public SnapshotData(int i, int i2) {
            this.userId = i;
            this.key = i2;
        }

        public int getUserId() {
            return this.userId;
        }

        public int getKey() {
            return this.key;
        }

        public void setConnectedSnapshotForLiveWallpaper(int i, int i2) {
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                perWhichSnapshot.setConnectedSnapshotForLiveWallpaper(i2);
            }
        }

        public int getConnectedSnapshotForLiveWallpaper(int i) {
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                return perWhichSnapshot.getConnectedSnapshotForLiveWallpaper();
            }
            return -1;
        }

        public void setLockscreenVisibility(int i, int i2) {
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                perWhichSnapshot.setLockscreenVisibility(WhichChecker.isSubDisplay(i), i2);
            }
        }

        public int getLockscreenVisibility(int i) {
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(i));
            if (perWhichSnapshot != null) {
                return perWhichSnapshot.getLockscreenVisibility(WhichChecker.isSubDisplay(i));
            }
            return 1;
        }

        public void setSource(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.source = str;
        }

        public String getSource() {
            return this.source;
        }

        public ArrayList getWhiches() {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.perWhichSnapshots.keySet().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (WhichChecker.isSystem(intValue)) {
                    arrayList.add(Integer.valueOf(intValue));
                }
            }
            Iterator it2 = this.perWhichSnapshots.keySet().iterator();
            while (it2.hasNext()) {
                int intValue2 = ((Integer) it2.next()).intValue();
                if (WhichChecker.isLock(intValue2)) {
                    arrayList.add(Integer.valueOf(intValue2));
                }
            }
            return arrayList;
        }

        public void setPerWhichSnapshot(int i, PerWhichSnapshot perWhichSnapshot) {
            this.perWhichSnapshots.put(Integer.valueOf(i), perWhichSnapshot);
        }

        public PerWhichSnapshot getPerWhichSnapshot(int i) {
            return (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(i));
        }

        public WallpaperData getWallpaperData(int i) {
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(SnapshotHelper.checkWhich(i)));
            if (perWhichSnapshot != null) {
                return perWhichSnapshot.getWallpaperData();
            }
            return null;
        }

        public void setWallpaperData(int i, WallpaperData wallpaperData) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot == null) {
                perWhichSnapshot = new PerWhichSnapshot();
                this.perWhichSnapshots.put(Integer.valueOf(checkWhich), perWhichSnapshot);
            }
            perWhichSnapshot.setWallpaperData(wallpaperData);
        }

        public Map getSettingsData(int i) {
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(SnapshotHelper.checkWhich(i)));
            if (perWhichSnapshot != null) {
                return perWhichSnapshot.getSettings();
            }
            return null;
        }

        public void setSettingsData(int i, HashMap hashMap) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot == null) {
                perWhichSnapshot = new PerWhichSnapshot();
                this.perWhichSnapshots.put(Integer.valueOf(checkWhich), perWhichSnapshot);
            }
            perWhichSnapshot.setSettings(hashMap);
        }

        public void setSettingsData(Context context, int i) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot == null) {
                perWhichSnapshot = new PerWhichSnapshot();
                this.perWhichSnapshots.put(Integer.valueOf(checkWhich), perWhichSnapshot);
            }
            HashMap hashMap = new HashMap();
            for (String str : SnapshotHelper.SettingsData.getSettingNames(checkWhich)) {
                Log.m129d("SnapshotManager", "setSettingsData: name = " + str + ", value = " + Settings.System.getIntForUser(context.getContentResolver(), str, -1, this.userId));
                hashMap.put(str, Integer.valueOf(Settings.System.getIntForUser(context.getContentResolver(), str, -1, this.userId)));
            }
            perWhichSnapshot.setSettings(hashMap);
        }

        public void remove(int i) {
            this.perWhichSnapshots.remove(Integer.valueOf(SnapshotHelper.checkWhich(i)));
        }

        public boolean hasWallpaperData() {
            return this.perWhichSnapshots.size() > 0;
        }

        public boolean hasWallpaperData(int i) {
            int checkWhich = SnapshotHelper.checkWhich(i);
            Log.m129d("SnapshotManager", "hasWallpaperData: which = " + checkWhich);
            PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(checkWhich));
            if (perWhichSnapshot != null && perWhichSnapshot.getWallpaperData() != null) {
                return true;
            }
            Log.m129d("SnapshotManager", "hasWallpaperData: [" + checkWhich + "] NOT exists.");
            return false;
        }

        public boolean isFromPairedService() {
            return this.isFromPairedService;
        }

        public void setFromPairedService(boolean z) {
            this.isFromPairedService = z;
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.println("\nSnapshotData:");
            printWriter.println("\tkey = " + this.key);
            printWriter.println("\tsource = " + this.source);
            printWriter.println("\tisFromPairedService = " + this.isFromPairedService);
            Iterator it = getWhiches().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                printWriter.println("\twhich = " + intValue);
                PerWhichSnapshot perWhichSnapshot = (PerWhichSnapshot) this.perWhichSnapshots.get(Integer.valueOf(intValue));
                printWriter.println("\tconnectedSnapshotForLiveWallpaper = " + perWhichSnapshot.getConnectedSnapshotForLiveWallpaper());
                WallpaperData wallpaperData = perWhichSnapshot.getWallpaperData();
                if (wallpaperData != null) {
                    printWriter.println("\tWallpaperData = ");
                    wallpaperData.dump(fileDescriptor, printWriter, strArr);
                }
                printWriter.println("\tSettingsData = ");
                for (Map.Entry entry : perWhichSnapshot.getSettings().entrySet()) {
                    printWriter.println("\t\t" + ((String) entry.getKey()) + " [" + ((Integer) entry.getValue()).intValue() + "]");
                }
            }
        }
    }

    public class SnapshotHistory {
        public String desc;
        public int key;
        public Map results;
        public String time;
        public int type;

        public SnapshotHistory(int i, int i2, Map map) {
            this.type = i;
            this.key = i2;
            this.time = SnapshotHelper.getCurrentTime();
            this.results = map;
        }

        public SnapshotHistory(int i, int i2, String str, Map map) {
            this.type = i;
            this.key = i2;
            this.time = str;
            this.results = map;
        }

        public SnapshotHistory(int i, int i2, String str) {
            this.type = i;
            this.key = i2;
            this.time = SnapshotHelper.getCurrentTime();
            this.desc = str;
        }

        public SnapshotHistory(int i, int i2, String str, String str2) {
            this.type = i;
            this.key = i2;
            this.time = str;
            this.desc = str2;
        }

        public String toString() {
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
            } else if (i == 5) {
                stringBuffer.append(String.format("%-15s", "RESTORE TB"));
            } else {
                stringBuffer.append(String.format("%-15s", "UNKNOWN"));
            }
            stringBuffer.append(String.format("key = %2d", Integer.valueOf(this.key)));
            stringBuffer.append(String.format("%30s", this.time));
            Map map = this.results;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    int intValue = ((Integer) entry.getKey()).intValue();
                    int intValue2 = ((Integer) entry.getValue()).intValue();
                    stringBuffer.append("\t");
                    stringBuffer.append(String.format("{%-3d,", Integer.valueOf(intValue)));
                    if (intValue2 != -9) {
                        if (intValue2 != -4) {
                            if (intValue2 != -3) {
                                if (intValue2 == -2) {
                                    stringBuffer.append("Failed to copy file");
                                } else {
                                    switch (intValue2) {
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
                                            stringBuffer.append(String.format(" %-2d", Integer.valueOf(intValue2)));
                                            break;
                                    }
                                }
                            } else {
                                stringBuffer.append("Already has backup wallpaper");
                            }
                        } else {
                            stringBuffer.append("Maximum numer exceeded");
                        }
                    } else {
                        stringBuffer.append("Unknown error");
                    }
                    stringBuffer.append("}");
                }
            }
            if (!TextUtils.isEmpty(this.desc)) {
                stringBuffer.append("\t");
                stringBuffer.append(this.desc);
            }
            return stringBuffer.toString();
        }
    }
}
