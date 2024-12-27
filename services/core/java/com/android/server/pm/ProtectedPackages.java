package com.android.server.pm;

import android.R;
import android.content.Context;
import android.util.SparseArray;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ProtectedPackages {
    public String mDeviceOwnerPackage;
    public int mDeviceOwnerUserId;
    public final String mDeviceProvisioningPackage;
    public final SparseArray mOwnerProtectedPackages = new SparseArray();
    public SparseArray mProfileOwnerPackages;

    public ProtectedPackages(Context context) {
        this.mDeviceProvisioningPackage =
                context.getResources().getString(R.string.display_manager_built_in_display_name);
    }

    public final synchronized String getDeviceOwnerOrProfileOwnerPackage(int i) {
        if (this.mDeviceOwnerUserId == i) {
            return this.mDeviceOwnerPackage;
        }
        SparseArray sparseArray = this.mProfileOwnerPackages;
        if (sparseArray == null) {
            return null;
        }
        return (String) sparseArray.get(i);
    }

    public final synchronized boolean hasDeviceOwnerOrProfileOwner(int i, String str) {
        if (str == null) {
            return false;
        }
        String str2 = this.mDeviceOwnerPackage;
        if (str2 != null && this.mDeviceOwnerUserId == i && str.equals(str2)) {
            return true;
        }
        SparseArray sparseArray = this.mProfileOwnerPackages;
        if (sparseArray != null) {
            if (str.equals(sparseArray.get(i))) {
                return true;
            }
        }
        return false;
    }

    public final synchronized boolean isPackageProtectedForUser(int i, String str) {
        boolean z;
        int indexOfKey = this.mOwnerProtectedPackages.indexOfKey(i);
        if (indexOfKey >= 0) {
            z = ((Set) this.mOwnerProtectedPackages.valueAt(indexOfKey)).contains(str);
        }
        return z;
    }

    public final boolean isPackageStateProtected(int i, String str) {
        return hasDeviceOwnerOrProfileOwner(i, str) || isProtectedPackage(i, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x002a, code lost:

       if (r4 != false) goto L27;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean isProtectedPackage(int r4, java.lang.String r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            if (r5 == 0) goto L37
            java.lang.String r1 = r3.mDeviceProvisioningPackage     // Catch: java.lang.Throwable -> L34
            boolean r1 = r5.equals(r1)     // Catch: java.lang.Throwable -> L34
            r2 = 1
            if (r1 != 0) goto L32
            monitor-enter(r3)     // Catch: java.lang.Throwable -> L34
            monitor-enter(r3)     // Catch: java.lang.Throwable -> L22
            android.util.SparseArray r1 = r3.mOwnerProtectedPackages     // Catch: java.lang.Throwable -> L2d
            int r1 = r1.indexOfKey(r4)     // Catch: java.lang.Throwable -> L2d
            if (r1 < 0) goto L19
            r1 = r2
            goto L1a
        L19:
            r1 = r0
        L1a:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L22
            if (r1 == 0) goto L24
            boolean r4 = r3.isPackageProtectedForUser(r4, r5)     // Catch: java.lang.Throwable -> L22
            goto L29
        L22:
            r4 = move-exception
            goto L30
        L24:
            r4 = -1
            boolean r4 = r3.isPackageProtectedForUser(r4, r5)     // Catch: java.lang.Throwable -> L22
        L29:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L34
            if (r4 == 0) goto L37
            goto L32
        L2d:
            r4 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L22
            throw r4     // Catch: java.lang.Throwable -> L22
        L30:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L34
            throw r4     // Catch: java.lang.Throwable -> L34
        L32:
            r0 = r2
            goto L37
        L34:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L37:
            monitor-exit(r3)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ProtectedPackages.isProtectedPackage(int,"
                    + " java.lang.String):boolean");
    }
}
