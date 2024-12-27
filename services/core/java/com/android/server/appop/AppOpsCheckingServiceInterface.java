package com.android.server.appop;

import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public interface AppOpsCheckingServiceInterface {
    boolean addAppOpsModeChangedListener(AppOpsService.AnonymousClass2 anonymousClass2);

    void clearAllModes();

    SparseBooleanArray getForegroundOps(int i);

    SparseBooleanArray getForegroundOps(int i, String str);

    SparseIntArray getNonDefaultPackageModes(int i, String str);

    SparseIntArray getNonDefaultUidModes(int i);

    int getPackageMode(int i, int i2, String str);

    int getUidMode(int i, int i2, String str);

    void readState();

    boolean removePackage(int i, String str);

    void removeUid(int i);

    void setPackageMode(int i, int i2, int i3, String str);

    boolean setUidMode(int i, int i2, int i3);

    void shutdown();

    void systemReady();

    void writeState();
}
