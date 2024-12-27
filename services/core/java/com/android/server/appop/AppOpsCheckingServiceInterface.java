package com.android.server.appop;

import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.server.appop.AppOpsService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
