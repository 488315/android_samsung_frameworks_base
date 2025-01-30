package com.samsung.android.server.pm.rescueparty;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Slog;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public abstract class AbstractBackupController implements BackupController {
    static int BACKUP_ITEM_INDEX_FIRST = 2;
    public static boolean DEBUG = true;
    public static String TAG = "AbstractBackupController";
    public final Context mContext;
    public SharedPreferences mSharedPrefForConfigs;
    public final Object mLock = new Object();
    public final ArrayList mBackupItemList = new ArrayList();
    public final AtomicInteger mLastSelectedBackupItemIndex = new AtomicInteger(-1);
    public final String mBackupFilesPrefName = "pref_" + getControllerName() + ".xml";

    @Override // com.samsung.android.server.pm.rescueparty.BackupController
    public abstract String getControllerName();

    public abstract boolean onSaveFiles(File file);

    public AbstractBackupController(Context context) {
        if (TextUtils.isEmpty(getControllerName())) {
            throw new IllegalStateException("Module name is empty or null");
        }
        this.mContext = context;
        this.mSharedPrefForConfigs = getSharedPreferencesForConfigs();
        initController();
    }

    public final void initController() {
        try {
            readBackupItems();
            readLastSelectedItem();
        } catch (Exception e) {
            Slog.d(TAG, "Failed to read configs: " + e);
            this.mBackupItemList.clear();
            this.mLastSelectedBackupItemIndex.set(-1);
        }
        if (DEBUG) {
            Slog.d(TAG, "mBackupItemList: " + this.mBackupItemList);
            Slog.d(TAG, "mLastSelectedBackupItemIndex: " + this.mLastSelectedBackupItemIndex.get());
        }
    }

    public final void readBackupItems() {
        String[] split = getBackupConfigStr("backup_item_list", "").split(",");
        if (split == null || split.length == 0) {
            return;
        }
        for (String str : split) {
            if (!TextUtils.isEmpty(str)) {
                this.mBackupItemList.add(str);
            }
        }
    }

    public final void readLastSelectedItem() {
        setLastSelectedItemIndex(getBackupConfigInt("last_selected_item", -1));
    }

    public final void writeBackupItems() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mBackupItemList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!TextUtils.isEmpty(str) && str.startsWith("/data/system")) {
                arrayList.add(str);
            }
        }
        putBackupConfigStr("backup_item_list", String.join(",", arrayList));
    }

    public final void writeLastSelectedItem(boolean z) {
        putBackupConfigInt("last_selected_item", getLastSelectedItemIndex(), z);
    }

    @Override // com.samsung.android.server.pm.rescueparty.BackupController
    public void saveFiles() {
        synchronized (this.mLock) {
            File file = new File(getControllerDir(), getNextBackupItemName());
            if (!file.exists()) {
                file.mkdirs();
            }
            if (DEBUG) {
                Slog.d(TAG, "Saving files on " + file);
            }
            boolean z = false;
            try {
                if (onSaveFiles(file)) {
                    addBackupItemList(file);
                    writeBackupItems();
                    cleanUpOutdatedFiles();
                    if (this.mLastSelectedBackupItemIndex.get() != -1) {
                        setLastSelectedItemIndex(-1);
                        writeLastSelectedItem(false);
                    }
                    z = true;
                }
            } catch (Exception e) {
                Slog.d(TAG, "Failed to save files: " + e);
            }
            if (!z && file.exists()) {
                file.delete();
            }
        }
    }

    public final String getNextBackupItemName() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return "backup_item_" + Base64.encodeToString(bArr, 10);
    }

    public File getLatestBackupItemDir() {
        File file;
        int latestBackupItemSinceLastSelected = getLatestBackupItemSinceLastSelected();
        if (latestBackupItemSinceLastSelected == -1) {
            Slog.e(TAG, "!@No backup files for " + getControllerName());
            return null;
        }
        setLastSelectedItemIndex(latestBackupItemSinceLastSelected);
        writeLastSelectedItem(true);
        try {
            synchronized (this.mLock) {
                file = new File((String) this.mBackupItemList.get(latestBackupItemSinceLastSelected));
            }
            if (file.exists()) {
                return file;
            }
            Slog.d(TAG, "!@File doesn't exist: " + file);
            return null;
        } catch (Exception e) {
            Slog.d(TAG, "!@Invalid file name or any exception occurred: " + e);
            return null;
        }
    }

    public void setLastSelectedItemIndex(int i) {
        this.mLastSelectedBackupItemIndex.set(i);
    }

    public final int getLastSelectedItemIndex() {
        return this.mLastSelectedBackupItemIndex.get();
    }

    public final int getLatestBackupItemSinceLastSelected() {
        int size;
        synchronized (this.mLock) {
            size = this.mBackupItemList.size();
        }
        if (size == 0) {
            return -1;
        }
        int i = size - 1;
        if (getLastSelectedItemIndex() == -1) {
            return i;
        }
        int lastSelectedItemIndex = getLastSelectedItemIndex() - 1;
        return lastSelectedItemIndex < 0 ? lastSelectedItemIndex + 3 : lastSelectedItemIndex;
    }

    public void addBackupItemList(File file) {
        synchronized (this.mLock) {
            this.mBackupItemList.add(file.toString().trim());
            if (this.mBackupItemList.size() > 3) {
                for (int i = 0; i < this.mBackupItemList.size() - 3; i++) {
                    File file2 = new File(getControllerDir(), (String) this.mBackupItemList.get(i));
                    this.mBackupItemList.remove(0);
                    if (file2.exists() && file2.delete()) {
                        Slog.d(TAG, "Failed to delete " + file2);
                    }
                }
            }
        }
    }

    public final void cleanUpOutdatedFiles() {
        synchronized (this.mLock) {
            File[] listFiles = getControllerDir().listFiles();
            if (listFiles == null) {
                return;
            }
            for (File file : listFiles) {
                if (file.isDirectory() && !this.mBackupItemList.contains(file.toString().trim())) {
                    Slog.d(TAG, "Removing outdated file: " + file);
                    deleteContentsAndDir(file);
                }
            }
        }
    }

    public File getControllerDir() {
        File file = new File(getBackupRootDir(), getControllerName());
        if (!file.exists() && !file.mkdirs()) {
            Slog.e(TAG, "Failed to make " + file + " for " + getControllerName());
        }
        return file;
    }

    public final File getBackupRootDir() {
        return new File(injectSystemDataDir(), "pm_backup_files");
    }

    public File injectSystemDataDir() {
        return Environment.getDataSystemDirectory();
    }

    public List getBackupItemNameList() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mBackupItemList);
        }
        return arrayList;
    }

    public boolean copyFile(File file, File file2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            try {
                if (DEBUG) {
                    Slog.d(TAG, "Copy " + file + " to " + file2);
                }
                FileUtils.copy(file, file2);
                if (!DEBUG) {
                    return true;
                }
                Slog.d(TAG, "Took " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
                return true;
            } catch (IOException e) {
                Slog.d(TAG, "Failed to copy " + file + " to " + file2);
                e.printStackTrace();
                if (!DEBUG) {
                    return false;
                }
                Slog.d(TAG, "Took " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
                return false;
            }
        } catch (Throwable th) {
            if (DEBUG) {
                Slog.d(TAG, "Took " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
            }
            throw th;
        }
    }

    public static boolean deleteContentsAndDir(File file) {
        if (deleteContents(file)) {
            return file.delete();
        }
        return false;
    }

    public static boolean deleteContents(File file) {
        File[] listFiles = file.listFiles();
        boolean z = true;
        if (listFiles != null) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    z &= deleteContents(file2);
                }
                if (!file2.delete()) {
                    Log.w("FileUtils", "Failed to delete " + file2);
                    z = false;
                }
            }
        }
        return z;
    }

    public final SharedPreferences getSharedPreferencesForConfigs() {
        return this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(new File(getControllerDir(), this.mBackupFilesPrefName), 0);
    }

    public void putBackupConfigInt(String str, int i, boolean z) {
        synchronized (this.mLock) {
            if (z) {
                this.mSharedPrefForConfigs.edit().putInt(str, i).commit();
            } else {
                this.mSharedPrefForConfigs.edit().putInt(str, i).apply();
            }
        }
    }

    public void putBackupConfigStr(String str, String str2) {
        synchronized (this.mLock) {
            this.mSharedPrefForConfigs.edit().putString(str, str2).apply();
        }
    }

    public int getBackupConfigInt(String str, int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mSharedPrefForConfigs.getInt(str, i);
        }
        return i2;
    }

    public String getBackupConfigStr(String str, String str2) {
        String string;
        synchronized (this.mLock) {
            string = this.mSharedPrefForConfigs.getString(str, str2);
        }
        return string;
    }
}
