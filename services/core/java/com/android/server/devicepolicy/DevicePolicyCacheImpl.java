package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyCache;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.SparseIntArray;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DevicePolicyCacheImpl extends DevicePolicyCache {
    public final Object mLock = new Object();
    public final Set mScreenCaptureDisallowedUsers = new HashSet();
    public final SparseIntArray mPasswordQuality = new SparseIntArray();
    public final SparseIntArray mPermissionPolicy = new SparseIntArray();
    public ArrayMap mLauncherShortcutOverrides = new ArrayMap();
    public volatile boolean mCanGrantSensorsPermissions = false;
    public final SparseIntArray mContentProtectionPolicy = new SparseIntArray();

    public final boolean canAdminGrantSensorsPermissions() {
        return this.mCanGrantSensorsPermissions;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Device policy cache:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Screen capture disallowed users: " + this.mScreenCaptureDisallowedUsers);
            indentingPrintWriter.println("Password quality: " + this.mPasswordQuality);
            indentingPrintWriter.println("Permission policy: " + this.mPermissionPolicy);
            indentingPrintWriter.println("Content protection policy: " + this.mContentProtectionPolicy);
            indentingPrintWriter.println("Admin can grant sensors permission: " + this.mCanGrantSensorsPermissions);
            indentingPrintWriter.print("Shortcuts overrides: ");
            indentingPrintWriter.println(this.mLauncherShortcutOverrides);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final int getContentProtectionPolicy(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mContentProtectionPolicy.get(i, 1);
        }
        return i2;
    }

    public final Map getLauncherShortcutOverrides() {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap(this.mLauncherShortcutOverrides);
        }
        return arrayMap;
    }

    public final int getPasswordQuality(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mPasswordQuality.get(i, 0);
        }
        return i2;
    }

    public final int getPermissionPolicy(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mPermissionPolicy.get(i, 0);
        }
        return i2;
    }

    public final boolean isScreenCaptureAllowed(int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (((HashSet) this.mScreenCaptureDisallowedUsers).contains(Integer.valueOf(i)) || ((HashSet) this.mScreenCaptureDisallowedUsers).contains(-1)) ? false : true;
            } finally {
            }
        }
        return z;
    }

    public final void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mPasswordQuality.delete(i);
            this.mPermissionPolicy.delete(i);
            this.mContentProtectionPolicy.delete(i);
        }
    }

    public final void setContentProtectionPolicy(int i, Integer num) {
        synchronized (this.mLock) {
            try {
                if (num == null) {
                    this.mContentProtectionPolicy.delete(i);
                } else {
                    this.mContentProtectionPolicy.put(i, num.intValue());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
