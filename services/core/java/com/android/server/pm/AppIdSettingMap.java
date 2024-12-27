package com.android.server.pm;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.WatchedArrayList;
import com.android.server.utils.WatchedSparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppIdSettingMap {
    public int mFirstAvailableAppId;
    public final WatchedArrayList mNonSystemSettings;
    public final SnapshotCache mNonSystemSettingsSnapshot;
    public final WatchedSparseArray mSystemSettings;
    public final SnapshotCache mSystemSettingsSnapshot;

    public AppIdSettingMap() {
        this.mFirstAvailableAppId = 10000;
        WatchedArrayList watchedArrayList = new WatchedArrayList(0);
        this.mNonSystemSettings = watchedArrayList;
        this.mNonSystemSettingsSnapshot =
                new SnapshotCache.Auto(
                        watchedArrayList,
                        watchedArrayList,
                        "AppIdSettingMap.mNonSystemSettings",
                        0);
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mSystemSettings = watchedSparseArray;
        this.mSystemSettingsSnapshot =
                new SnapshotCache.Auto(
                        watchedSparseArray,
                        watchedSparseArray,
                        "AppIdSettingMap.mSystemSettings",
                        0);
    }

    public AppIdSettingMap(AppIdSettingMap appIdSettingMap) {
        this.mFirstAvailableAppId = 10000;
        this.mNonSystemSettings =
                (WatchedArrayList) appIdSettingMap.mNonSystemSettingsSnapshot.snapshot();
        this.mNonSystemSettingsSnapshot = new SnapshotCache.Auto();
        this.mSystemSettings =
                (WatchedSparseArray) appIdSettingMap.mSystemSettingsSnapshot.snapshot();
        this.mSystemSettingsSnapshot = new SnapshotCache.Auto();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001b, code lost:

       return r3 + 10000;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int acquireAndRegisterNewAppId(com.android.server.pm.SettingBase r4) {
        /*
            r3 = this;
            com.android.server.utils.WatchedArrayList r0 = r3.mNonSystemSettings
            java.util.ArrayList r1 = r0.mStorage
            int r1 = r1.size()
            int r3 = r3.mFirstAvailableAppId
            int r3 = r3 + (-10000)
        Lc:
            if (r3 >= r1) goto L1f
            java.util.ArrayList r2 = r0.mStorage
            java.lang.Object r2 = r2.get(r3)
            if (r2 != 0) goto L1c
            r0.set(r3, r4)
        L19:
            int r3 = r3 + 10000
            return r3
        L1c:
            int r3 = r3 + 1
            goto Lc
        L1f:
            r3 = 9999(0x270f, float:1.4012E-41)
            if (r1 <= r3) goto L37
            r3 = 0
        L24:
            if (r3 >= r1) goto L35
            java.util.ArrayList r2 = r0.mStorage
            java.lang.Object r2 = r2.get(r3)
            if (r2 != 0) goto L32
            r0.set(r3, r4)
            goto L19
        L32:
            int r3 = r3 + 1
            goto L24
        L35:
            r3 = -1
            return r3
        L37:
            java.util.ArrayList r3 = r0.mStorage
            r3.add(r4)
            r0.registerChild(r4)
            r0.dispatchChange(r0)
            int r1 = r1 + 10000
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.AppIdSettingMap.acquireAndRegisterNewAppId(com.android.server.pm.SettingBase):int");
    }

    public final boolean registerExistingAppId(int i, SettingBase settingBase, Object obj) {
        if (i >= 10000) {
            WatchedArrayList watchedArrayList = this.mNonSystemSettings;
            int i2 = i - 10000;
            for (int size = watchedArrayList.mStorage.size(); i2 >= size; size++) {
                watchedArrayList.mStorage.add(null);
                watchedArrayList.registerChild(null);
                watchedArrayList.dispatchChange(watchedArrayList);
            }
            if (watchedArrayList.mStorage.get(i2) != null) {
                String str = "Adding duplicate app id: " + i + " name=" + obj;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(5, str);
                return false;
            }
            watchedArrayList.set(i2, settingBase);
        } else {
            WatchedSparseArray watchedSparseArray = this.mSystemSettings;
            if (watchedSparseArray.mStorage.get(i) != null) {
                String str2 = "Adding duplicate shared id: " + i + " name=" + obj;
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(5, str2);
                return false;
            }
            watchedSparseArray.put(i, settingBase);
        }
        return true;
    }

    public final void replaceSetting(int i, SettingBase settingBase) {
        if (i < 10000) {
            this.mSystemSettings.put(i, settingBase);
            return;
        }
        WatchedArrayList watchedArrayList = this.mNonSystemSettings;
        int i2 = i - 10000;
        if (i2 < watchedArrayList.mStorage.size()) {
            watchedArrayList.set(i2, settingBase);
            return;
        }
        String m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i,
                        "Error in package manager settings: calling replaceAppIdLpw to replace"
                            + " SettingBase at appId=",
                        " but nothing is replaced.");
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        PackageManagerServiceUtils.logCriticalInfo(5, m);
    }
}
