package com.android.server.pm;

import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedUsesPermission;

import java.util.List;

public final class UpdateOwnershipHelper {
    public final ArrayMap mUpdateOwnerOptOutsToOwners = new ArrayMap(200);
    public final Object mLock = new Object();

    public static boolean hasValidOwnershipDenyList(PackageSetting packageSetting) {
        AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        if (androidPackageInternal == null) {
            return false;
        }
        if ((!packageSetting.isSystem() && !packageSetting.pkgState.updatedSystemApp)
                || !androidPackageInternal
                        .getProperties()
                        .containsKey("android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST")) {
            return false;
        }
        String[] strArr = {
            "android.permission.INSTALL_PACKAGES", "android.permission.INSTALL_PACKAGE_UPDATES"
        };
        List usesPermissions = androidPackageInternal.getUsesPermissions();
        for (int i = 0; i < usesPermissions.size(); i++) {
            for (int i2 = 0; i2 < 2; i2++) {
                if (strArr[i2].equals(((ParsedUsesPermission) usesPermissions.get(i)).getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void removeUpdateOwnerDenyList(String str) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mUpdateOwnerOptOutsToOwners.size() - 1; size >= 0; size--) {
                    ArrayMap arrayMap = this.mUpdateOwnerOptOutsToOwners;
                    ArraySet arraySet = (ArraySet) arrayMap.get(arrayMap.keyAt(size));
                    if (arraySet.remove(str) && arraySet.isEmpty()) {
                        this.mUpdateOwnerOptOutsToOwners.removeAt(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
