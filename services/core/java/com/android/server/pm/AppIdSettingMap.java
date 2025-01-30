package com.android.server.pm;

import com.android.server.utils.SnapshotCache;
import com.android.server.utils.WatchedArrayList;
import com.android.server.utils.WatchedSparseArray;
import com.android.server.utils.Watcher;

/* loaded from: classes3.dex */
public final class AppIdSettingMap {
    public int mFirstAvailableAppId;
    public final WatchedArrayList mNonSystemSettings;
    public final SnapshotCache mNonSystemSettingsSnapshot;
    public final WatchedSparseArray mSystemSettings;
    public final SnapshotCache mSystemSettingsSnapshot;

    public AppIdSettingMap() {
        this.mFirstAvailableAppId = 10000;
        WatchedArrayList watchedArrayList = new WatchedArrayList();
        this.mNonSystemSettings = watchedArrayList;
        this.mNonSystemSettingsSnapshot = new SnapshotCache.Auto(watchedArrayList, watchedArrayList, "AppIdSettingMap.mNonSystemSettings");
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mSystemSettings = watchedSparseArray;
        this.mSystemSettingsSnapshot = new SnapshotCache.Auto(watchedSparseArray, watchedSparseArray, "AppIdSettingMap.mSystemSettings");
    }

    public AppIdSettingMap(AppIdSettingMap appIdSettingMap) {
        this.mFirstAvailableAppId = 10000;
        this.mNonSystemSettings = (WatchedArrayList) appIdSettingMap.mNonSystemSettingsSnapshot.snapshot();
        this.mNonSystemSettingsSnapshot = new SnapshotCache.Sealed();
        this.mSystemSettings = (WatchedSparseArray) appIdSettingMap.mSystemSettingsSnapshot.snapshot();
        this.mSystemSettingsSnapshot = new SnapshotCache.Sealed();
    }

    public boolean registerExistingAppId(int i, SettingBase settingBase, Object obj) {
        if (i >= 10000) {
            int i2 = i - 10000;
            for (int size = this.mNonSystemSettings.size(); i2 >= size; size++) {
                this.mNonSystemSettings.add(null);
            }
            if (this.mNonSystemSettings.get(i2) != null) {
                PackageManagerService.reportSettingsProblem(5, "Adding duplicate app id: " + i + " name=" + obj);
                return false;
            }
            this.mNonSystemSettings.set(i2, settingBase);
            return true;
        }
        if (this.mSystemSettings.get(i) != null) {
            PackageManagerService.reportSettingsProblem(5, "Adding duplicate shared id: " + i + " name=" + obj);
            return false;
        }
        this.mSystemSettings.put(i, settingBase);
        return true;
    }

    public SettingBase getSetting(int i) {
        if (i >= 10000) {
            int i2 = i - 10000;
            if (i2 < this.mNonSystemSettings.size()) {
                return (SettingBase) this.mNonSystemSettings.get(i2);
            }
            return null;
        }
        return (SettingBase) this.mSystemSettings.get(i);
    }

    public void removeSetting(int i) {
        if (i >= 10000) {
            int i2 = i - 10000;
            if (i2 < this.mNonSystemSettings.size()) {
                this.mNonSystemSettings.set(i2, null);
            }
        } else {
            this.mSystemSettings.remove(i);
        }
        setFirstAvailableAppId(i + 1);
    }

    public final void setFirstAvailableAppId(int i) {
        if (i > this.mFirstAvailableAppId) {
            this.mFirstAvailableAppId = i;
        }
    }

    public void replaceSetting(int i, SettingBase settingBase) {
        if (i >= 10000) {
            int i2 = i - 10000;
            if (i2 < this.mNonSystemSettings.size()) {
                this.mNonSystemSettings.set(i2, settingBase);
                return;
            }
            PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: calling replaceAppIdLpw to replace SettingBase at appId=" + i + " but nothing is replaced.");
            return;
        }
        this.mSystemSettings.put(i, settingBase);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001b, code lost:
    
        return r1 + 10000;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int acquireAndRegisterNewAppId(SettingBase settingBase) {
        int size = this.mNonSystemSettings.size();
        int i = this.mFirstAvailableAppId - 10000;
        while (true) {
            if (i >= size) {
                if (size <= 9999) {
                    this.mNonSystemSettings.add(settingBase);
                    return size + 10000;
                }
                i = 0;
                while (i < size) {
                    if (this.mNonSystemSettings.get(i) == null) {
                        this.mNonSystemSettings.set(i, settingBase);
                    } else {
                        i++;
                    }
                }
                return -1;
            }
            if (this.mNonSystemSettings.get(i) == null) {
                this.mNonSystemSettings.set(i, settingBase);
                break;
            }
            i++;
        }
    }

    public AppIdSettingMap snapshot() {
        return new AppIdSettingMap(this);
    }

    public void registerObserver(Watcher watcher) {
        this.mNonSystemSettings.registerObserver(watcher);
        this.mSystemSettings.registerObserver(watcher);
    }
}
