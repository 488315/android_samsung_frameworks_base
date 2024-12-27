package com.samsung.android.server.pm.rescueparty;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AbstractBackupController {
    static int BACKUP_ITEM_INDEX_FIRST = 2;
    public final SharedPreferences mSharedPrefForConfigs;
    public final SecureRandom random = new SecureRandom();
    public final Object mLock = new Object();
    public final ArrayList mBackupItemList = new ArrayList();
    public final AtomicInteger mLastSelectedBackupItemIndex = new AtomicInteger(-1);

    public AbstractBackupController(Context context) {
        if (TextUtils.isEmpty("pm_settings_backup")) {
            throw new IllegalStateException("Module name is empty or null");
        }
        this.mSharedPrefForConfigs = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(getControllerDir(), "pref_pm_settings_backup.xml"), 0);
        try {
            readBackupItems();
            setLastSelectedItemIndex(getBackupConfigInt(-1, "last_selected_item"));
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "Failed to read configs: ", "AbstractBackupController");
            this.mBackupItemList.clear();
            this.mLastSelectedBackupItemIndex.set(-1);
        }
        Slog.d("AbstractBackupController", "mBackupItemList: " + this.mBackupItemList);
        Slog.d("AbstractBackupController", "mLastSelectedBackupItemIndex: " + this.mLastSelectedBackupItemIndex.get());
    }

    public static boolean copyFile(File file, File file2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            try {
                Slog.d("AbstractBackupController", "Copy " + file + " to " + file2);
                FileUtils.copy(file, file2);
                Slog.d("AbstractBackupController", "Took " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
                return true;
            } catch (IOException e) {
                Slog.d("AbstractBackupController", "Failed to copy " + file + " to " + file2);
                e.printStackTrace();
                Slog.d("AbstractBackupController", "Took " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
                return false;
            }
        } catch (Throwable th) {
            Slog.d("AbstractBackupController", "Took " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
            throw th;
        }
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

    public final void addBackupItemList(File file) {
        synchronized (this.mLock) {
            try {
                this.mBackupItemList.add(file.toString().trim());
                if (this.mBackupItemList.size() > 3) {
                    for (int i = 0; i < this.mBackupItemList.size() - 3; i++) {
                        File file2 = new File(getControllerDir(), (String) this.mBackupItemList.get(i));
                        this.mBackupItemList.remove(0);
                        if (file2.exists() && file2.delete()) {
                            Slog.d("AbstractBackupController", "Failed to delete " + file2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cleanUpOutdatedFiles() {
        synchronized (this.mLock) {
            try {
                File[] listFiles = getControllerDir().listFiles();
                if (listFiles == null) {
                    return;
                }
                for (File file : listFiles) {
                    if (file.isDirectory() && !this.mBackupItemList.contains(file.toString().trim())) {
                        Slog.d("AbstractBackupController", "Removing outdated file: " + file);
                        if (deleteContents(file)) {
                            file.delete();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getBackupConfigInt(int i, String str) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mSharedPrefForConfigs.getInt(str, i);
        }
        return i2;
    }

    public List getBackupItemNameList() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mBackupItemList);
        }
        return arrayList;
    }

    public File getControllerDir() {
        File file = new File(new File(injectSystemDataDir(), "pm_backup_files"), "pm_settings_backup");
        if (!file.exists() && !file.mkdirs()) {
            Slog.e("AbstractBackupController", "Failed to make " + file + " for pm_settings_backup");
        }
        return file;
    }

    public File getLatestBackupItemDir() {
        int size;
        int i;
        File file;
        synchronized (this.mLock) {
            size = this.mBackupItemList.size();
        }
        if (size == 0) {
            i = -1;
        } else {
            i = size - 1;
            if (this.mLastSelectedBackupItemIndex.get() != -1) {
                int i2 = this.mLastSelectedBackupItemIndex.get();
                int i3 = i2 - 1;
                i = i3 < 0 ? i2 + 2 : i3;
            }
        }
        if (i == -1) {
            Slog.e("AbstractBackupController", "!@No backup files for pm_settings_backup");
            return null;
        }
        setLastSelectedItemIndex(i);
        putBackupConfigInt(this.mLastSelectedBackupItemIndex.get(), "last_selected_item", true);
        try {
            synchronized (this.mLock) {
                file = new File((String) this.mBackupItemList.get(i));
            }
            if (file.exists()) {
                return file;
            }
            Slog.d("AbstractBackupController", "!@File doesn't exist: " + file);
            return null;
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "!@Invalid file name or any exception occurred: ", "AbstractBackupController");
            return null;
        }
    }

    public File injectSystemDataDir() {
        return Environment.getDataSystemDirectory();
    }

    public abstract boolean onSaveFiles(File file);

    public final void putBackupConfigInt(int i, String str, boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mSharedPrefForConfigs.edit().putInt(str, i).commit();
                } else {
                    this.mSharedPrefForConfigs.edit().putInt(str, i).apply();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void readBackupItems() {
        String string;
        synchronized (this.mLock) {
            string = this.mSharedPrefForConfigs.getString("backup_item_list", "");
        }
        String[] split = string.split(",");
        if (split == null || split.length == 0) {
            return;
        }
        for (String str : split) {
            if (!TextUtils.isEmpty(str)) {
                this.mBackupItemList.add(str);
            }
        }
    }

    public void setLastSelectedItemIndex(int i) {
        this.mLastSelectedBackupItemIndex.set(i);
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
        String join = String.join(",", arrayList);
        synchronized (this.mLock) {
            this.mSharedPrefForConfigs.edit().putString("backup_item_list", join).apply();
        }
    }
}
