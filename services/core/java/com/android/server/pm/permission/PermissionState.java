package com.android.server.pm.permission;

import android.content.pm.PackageManager;

public final class PermissionState {
    public int mFlags;
    public boolean mGranted;
    public final Object mLock = new Object();
    public final Permission mPermission;

    public PermissionState(Permission permission) {
        this.mPermission = permission;
    }

    public final int getFlags() {
        int i;
        synchronized (this.mLock) {
            i = this.mFlags;
        }
        return i;
    }

    public final String getName() {
        return this.mPermission.mPermissionInfo.name;
    }

    public final boolean grant() {
        synchronized (this.mLock) {
            try {
                if (this.mGranted) {
                    return false;
                }
                this.mGranted = true;
                PackageManager.invalidatePackageInfoCache();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isDefault() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = !this.mGranted && this.mFlags == 0;
            } finally {
            }
        }
        return z;
    }

    public final boolean isGranted() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mGranted;
        }
        return z;
    }

    public final boolean updateFlags(int i, int i2) {
        boolean z;
        synchronized (this.mLock) {
            int i3 = i2 & i;
            PackageManager.invalidatePackageInfoCache();
            int i4 = this.mFlags;
            int i5 = ((~i) & i4) | i3;
            this.mFlags = i5;
            z = i5 != i4;
        }
        return z;
    }
}
