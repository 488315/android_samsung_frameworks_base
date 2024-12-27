package com.android.server.pm.permission;

import android.content.pm.PermissionInfo;
import android.os.UserHandle;

import libcore.util.EmptyArray;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Permission {
    public boolean mDefinitionChanged;
    public int[] mGids = EmptyArray.INT;
    public boolean mGidsPerUser;
    public PermissionInfo mPermissionInfo;
    public boolean mReconciled;
    public final int mType;
    public int mUid;

    public Permission(int i, String str, String str2) {
        PermissionInfo permissionInfo = new PermissionInfo();
        this.mPermissionInfo = permissionInfo;
        permissionInfo.name = str;
        permissionInfo.packageName = str2;
        permissionInfo.protectionLevel = 2;
        this.mType = i;
    }

    public Permission(PermissionInfo permissionInfo, int i) {
        this.mPermissionInfo = permissionInfo;
        this.mType = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.pm.permission.Permission createOrUpdate(
            com.android.server.pm.permission.Permission r8,
            android.content.pm.PermissionInfo r9,
            com.android.server.pm.pkg.PackageState r10,
            java.util.Collection r11,
            boolean r12) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.permission.Permission.createOrUpdate(com.android.server.pm.permission.Permission,"
                    + " android.content.pm.PermissionInfo, com.android.server.pm.pkg.PackageState,"
                    + " java.util.Collection,"
                    + " boolean):com.android.server.pm.permission.Permission");
    }

    public static Permission findPermissionTree(String str, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Permission permission = (Permission) it.next();
            String str2 = permission.mPermissionInfo.name;
            if (str.startsWith(str2)
                    && str.length() > str2.length()
                    && str.charAt(str2.length()) == '.') {
                return permission;
            }
        }
        return null;
    }

    public final boolean addToTree(int i, PermissionInfo permissionInfo, Permission permission) {
        boolean z;
        PermissionInfo permissionInfo2 = this.mPermissionInfo;
        if (permissionInfo2.protectionLevel == i
                && this.mReconciled
                && this.mUid == permission.mUid
                && Objects.equals(
                        permissionInfo2.packageName, permission.mPermissionInfo.packageName)) {
            PermissionInfo permissionInfo3 = this.mPermissionInfo;
            if (permissionInfo3.icon == permissionInfo.icon
                    && permissionInfo3.logo == permissionInfo.logo
                    && permissionInfo3.protectionLevel == permissionInfo.protectionLevel
                    && Objects.equals(permissionInfo3.name, permissionInfo.name)
                    && Objects.equals(
                            permissionInfo3.nonLocalizedLabel, permissionInfo.nonLocalizedLabel)
                    && Objects.equals(permissionInfo3.packageName, permissionInfo.packageName)) {
                z = false;
                PermissionInfo permissionInfo4 = new PermissionInfo(permissionInfo);
                this.mPermissionInfo = permissionInfo4;
                permissionInfo4.packageName = permission.mPermissionInfo.packageName;
                permissionInfo4.protectionLevel = i;
                this.mReconciled = true;
                this.mUid = permission.mUid;
                return z;
            }
        }
        z = true;
        PermissionInfo permissionInfo42 = new PermissionInfo(permissionInfo);
        this.mPermissionInfo = permissionInfo42;
        permissionInfo42.packageName = permission.mPermissionInfo.packageName;
        permissionInfo42.protectionLevel = i;
        this.mReconciled = true;
        this.mUid = permission.mUid;
        return z;
    }

    public final int[] computeGids(int i) {
        if (!this.mGidsPerUser) {
            int[] iArr = this.mGids;
            return iArr.length != 0 ? (int[]) iArr.clone() : iArr;
        }
        int[] iArr2 = new int[this.mGids.length];
        int i2 = 0;
        while (true) {
            int[] iArr3 = this.mGids;
            if (i2 >= iArr3.length) {
                return iArr2;
            }
            iArr2[i2] = UserHandle.getUid(i, iArr3[i2]);
            i2++;
        }
    }

    public final PermissionInfo generatePermissionInfo(int i, int i2) {
        PermissionInfo permissionInfo;
        if (this.mPermissionInfo != null) {
            permissionInfo = new PermissionInfo(this.mPermissionInfo);
            if ((i & 128) != 128) {
                permissionInfo.metaData = null;
            }
        } else {
            permissionInfo = new PermissionInfo();
            PermissionInfo permissionInfo2 = this.mPermissionInfo;
            permissionInfo.name = permissionInfo2.name;
            permissionInfo.packageName = permissionInfo2.packageName;
            permissionInfo.nonLocalizedLabel = permissionInfo2.name;
        }
        permissionInfo.flags |= 1073741824;
        if (i2 >= 26) {
            permissionInfo.protectionLevel = this.mPermissionInfo.protectionLevel;
        } else {
            int i3 = this.mPermissionInfo.protectionLevel;
            int i4 = i3 & 15;
            if (i4 == 2) {
                permissionInfo.protectionLevel = i3;
            } else {
                permissionInfo.protectionLevel = i4;
            }
        }
        return permissionInfo;
    }

    public final boolean isDevelopment() {
        return isSignature() && (this.mPermissionInfo.protectionLevel & 32) != 0;
    }

    public final boolean isInstant() {
        return (this.mPermissionInfo.protectionLevel & 4096) != 0;
    }

    public final boolean isInternal() {
        return (this.mPermissionInfo.protectionLevel & 15) == 4;
    }

    public final boolean isPrivileged() {
        return (this.mPermissionInfo.protectionLevel & 16) != 0;
    }

    public final boolean isRole() {
        return (this.mPermissionInfo.protectionLevel & 67108864) != 0;
    }

    public final boolean isRuntime() {
        return (this.mPermissionInfo.protectionLevel & 15) == 1;
    }

    public final boolean isSignature() {
        return (this.mPermissionInfo.protectionLevel & 15) == 2;
    }
}
