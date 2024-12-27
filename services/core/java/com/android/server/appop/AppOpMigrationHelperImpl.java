package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.server.SystemServiceManager;

import java.io.File;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpMigrationHelperImpl {
    public int mVersionAtBoot;
    public SparseArray mAppIdAppOpModes = null;
    public SparseArray mPackageAppOpModes = null;
    public final Object mLock = new Object();

    public static Map getAppOpModesForOpName(SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        ArrayMap arrayMap = new ArrayMap(size);
        for (int i = 0; i < size; i++) {
            arrayMap.put(
                    AppOpsManager.opToPublicName(sparseIntArray.keyAt(i)),
                    Integer.valueOf(sparseIntArray.valueAt(i)));
        }
        return arrayMap;
    }

    public final int getLegacyAppOpVersion() {
        synchronized (this.mLock) {
            try {
                if (this.mAppIdAppOpModes != null) {
                    if (this.mPackageAppOpModes == null) {}
                }
                readLegacyAppOpState();
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mVersionAtBoot;
    }

    public final void readLegacyAppOpState() {
        AtomicFile atomicFile =
                new AtomicFile(new File(SystemServiceManager.ensureSystemDir(), "appops.xml"));
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        int readState = LegacyAppOpStateParser.readState(atomicFile, sparseArray, sparseArray2);
        if (readState == -2) {
            this.mVersionAtBoot = -1;
        } else if (readState != -1) {
            this.mVersionAtBoot = readState;
        } else {
            this.mVersionAtBoot = 0;
        }
        SparseArray sparseArray3 = new SparseArray();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            int userId = UserHandle.getUserId(keyAt);
            Map map = (Map) sparseArray3.get(userId);
            if (map == null) {
                map = new ArrayMap();
                sparseArray3.put(userId, map);
            }
            map.put(
                    Integer.valueOf(UserHandle.getAppId(keyAt)),
                    getAppOpModesForOpName((SparseIntArray) sparseArray.valueAt(i)));
        }
        this.mAppIdAppOpModes = sparseArray3;
        SparseArray sparseArray4 = new SparseArray();
        int size2 = sparseArray2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            int keyAt2 = sparseArray2.keyAt(i2);
            Map map2 = (Map) sparseArray4.get(keyAt2);
            if (map2 == null) {
                map2 = new ArrayMap();
                sparseArray4.put(keyAt2, map2);
            }
            ArrayMap arrayMap = (ArrayMap) sparseArray2.valueAt(i2);
            int size3 = arrayMap.size();
            for (int i3 = 0; i3 < size3; i3++) {
                map2.put(
                        (String) arrayMap.keyAt(i3),
                        getAppOpModesForOpName((SparseIntArray) arrayMap.valueAt(i3)));
            }
        }
        this.mPackageAppOpModes = sparseArray4;
    }
}
