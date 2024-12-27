package com.android.server.policy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.storage.StorageManagerInternal;
import android.provider.DeviceConfig;

import com.android.server.LocalServices;
import com.android.server.pm.pkg.AndroidPackage;

import java.util.Arrays;
import java.util.HashSet;

public abstract class SoftRestrictedPermissionPolicy {
    public static final AnonymousClass1 DUMMY_POLICY = new AnonymousClass1();
    public static final HashSet sForcedScopedStorageAppWhitelist;

    /* renamed from: com.android.server.policy.SoftRestrictedPermissionPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends SoftRestrictedPermissionPolicy {
        @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
        public final boolean mayGrantPermission() {
            return true;
        }
    }

    static {
        String string =
                DeviceConfig.getString(
                        "storage_native_boot", "forced_scoped_storage_whitelist", "");
        sForcedScopedStorageAppWhitelist =
                new HashSet(
                        Arrays.asList(
                                (string == null || string.equals(""))
                                        ? new String[0]
                                        : string.split(",")));
    }

    public static SoftRestrictedPermissionPolicy forPermission(
            Context context,
            ApplicationInfo applicationInfo,
            AndroidPackage androidPackage,
            UserHandle userHandle,
            String str) {
        final boolean z;
        final int i;
        final boolean z2;
        final boolean z3;
        final boolean z4;
        final boolean z5;
        final boolean z6;
        final boolean z7;
        boolean z8;
        final int i2;
        str.getClass();
        final boolean z9 = false;
        if (!str.equals("android.permission.READ_EXTERNAL_STORAGE")) {
            if (!str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                return DUMMY_POLICY;
            }
            if (applicationInfo != null) {
                boolean z10 =
                        (context.getPackageManager()
                                                .getPermissionFlags(
                                                        str,
                                                        applicationInfo.packageName,
                                                        userHandle)
                                        & 14336)
                                != 0;
                i2 = getMinimumTargetSDK(context, applicationInfo, userHandle);
                z9 = z10;
            } else {
                i2 = 0;
            }
            return new SoftRestrictedPermissionPolicy() { // from class:
                                                          // com.android.server.policy.SoftRestrictedPermissionPolicy.3
                @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
                public final boolean mayGrantPermission() {
                    return z9 || i2 >= 29;
                }
            };
        }
        if (applicationInfo != null) {
            PackageManager packageManager = context.getPackageManager();
            StorageManagerInternal storageManagerInternal =
                    (StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class);
            boolean z11 =
                    (packageManager.getPermissionFlags(str, applicationInfo.packageName, userHandle)
                                    & 14336)
                            != 0;
            boolean hasLegacyExternalStorage =
                    storageManagerInternal.hasLegacyExternalStorage(applicationInfo.uid);
            int i3 = applicationInfo.uid;
            PackageManager packageManager2 = context.getPackageManager();
            String[] packagesForUid = packageManager2.getPackagesForUid(i3);
            if (packagesForUid != null) {
                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i3);
                for (String str2 : packagesForUid) {
                    if (packageManager2
                            .getApplicationInfoAsUser(str2, 0, userHandleForUid)
                            .hasRequestedLegacyExternalStorage()) {
                        z8 = true;
                        break;
                    }
                }
            }
            z8 = false;
            int i4 = applicationInfo.uid;
            PackageManager packageManager3 = context.getPackageManager();
            String[] packagesForUid2 = packageManager3.getPackagesForUid(i4);
            if (packagesForUid2 != null) {
                int length = packagesForUid2.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        break;
                    }
                    if (packageManager3.checkPermission(
                                    "android.permission.WRITE_MEDIA_STORAGE", packagesForUid2[i5])
                            == 0) {
                        z9 = true;
                        break;
                    }
                    i5++;
                }
            }
            boolean hasPreserveLegacyExternalStorage =
                    androidPackage.hasPreserveLegacyExternalStorage();
            i = getMinimumTargetSDK(context, applicationInfo, userHandle);
            z3 = sForcedScopedStorageAppWhitelist.contains(applicationInfo.packageName);
            z2 = !z11;
            z = z11;
            z4 = z9;
            z5 = hasLegacyExternalStorage;
            z6 = z8;
            z7 = hasPreserveLegacyExternalStorage;
        } else {
            z = false;
            i = 0;
            z2 = false;
            z3 = false;
            z4 = false;
            z5 = false;
            z6 = false;
            z7 = false;
        }
        return new SoftRestrictedPermissionPolicy() { // from class:
                                                      // com.android.server.policy.SoftRestrictedPermissionPolicy.2
            @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
            public final int getExtraAppOpCode() {
                return 87;
            }

            @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
            public final boolean mayAllowExtraAppOp() {
                if (z2 || z3 || i >= 30) {
                    return false;
                }
                return z4 || z5 || z6;
            }

            @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
            public final boolean mayDenyExtraAppOpIfGranted() {
                return i < 30 ? !mayAllowExtraAppOp() : z2 || z3 || !z7;
            }

            @Override // com.android.server.policy.SoftRestrictedPermissionPolicy
            public final boolean mayGrantPermission() {
                return z || i >= 29;
            }
        };
    }

    public static int getMinimumTargetSDK(
            Context context, ApplicationInfo applicationInfo, UserHandle userHandle) {
        PackageManager packageManager = context.getPackageManager();
        int i = applicationInfo.targetSdkVersion;
        String[] packagesForUid = packageManager.getPackagesForUid(applicationInfo.uid);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                if (!str.equals(applicationInfo.packageName)) {
                    try {
                        i =
                                Integer.min(
                                        i,
                                        packageManager.getApplicationInfoAsUser(str, 0, userHandle)
                                                .targetSdkVersion);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
        }
        return i;
    }

    public int getExtraAppOpCode() {
        return -1;
    }

    public boolean mayAllowExtraAppOp() {
        return false;
    }

    public boolean mayDenyExtraAppOpIfGranted() {
        return false;
    }

    public abstract boolean mayGrantPermission();
}
