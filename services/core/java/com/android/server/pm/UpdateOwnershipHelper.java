package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.component.ParsedUsesPermission;
import java.util.List;

/* loaded from: classes3.dex */
public class UpdateOwnershipHelper {
    public final ArrayMap mUpdateOwnerOptOutsToOwners = new ArrayMap(200);
    public final Object mLock = new Object();

    public static boolean hasValidOwnershipDenyList(PackageSetting packageSetting) {
        AndroidPackageInternal pkg = packageSetting.getPkg();
        return pkg != null && (packageSetting.isSystem() || packageSetting.isUpdatedSystemApp()) && pkg.getProperties().containsKey("android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST") && usesAnyPermission(pkg, "android.permission.INSTALL_PACKAGES", "android.permission.INSTALL_PACKAGE_UPDATES");
    }

    public static boolean usesAnyPermission(AndroidPackage androidPackage, String... strArr) {
        List usesPermissions = androidPackage.getUsesPermissions();
        for (int i = 0; i < usesPermissions.size(); i++) {
            for (String str : strArr) {
                if (str.equals(((ParsedUsesPermission) usesPermissions.get(i)).getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
    
        android.util.Slog.w("PackageManager", "Deny list defined by " + r0.getPackageName() + " was trucated to maximum size of 1000");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArraySet readUpdateOwnerDenyList(PackageSetting packageSetting) {
        AndroidPackageInternal pkg;
        if (!hasValidOwnershipDenyList(packageSetting) || (pkg = packageSetting.getPkg()) == null) {
            return null;
        }
        ArraySet arraySet = new ArraySet(1000);
        try {
            int resourceId = ((PackageManager.Property) pkg.getProperties().get("android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST")).getResourceId();
            ApplicationInfo generateAppInfoWithoutState = AndroidPackageUtils.generateAppInfoWithoutState(pkg);
            XmlResourceParser xml = ResourcesManager.getInstance().getResources((IBinder) null, generateAppInfoWithoutState.sourceDir, generateAppInfoWithoutState.splitSourceDirs, generateAppInfoWithoutState.resourceDirs, generateAppInfoWithoutState.overlayPaths, generateAppInfoWithoutState.sharedLibraryFiles, (Integer) null, Configuration.EMPTY, (CompatibilityInfo) null, (ClassLoader) null, (List) null).getXml(resourceId);
            while (true) {
                try {
                    if (xml.getEventType() == 1) {
                        break;
                    }
                    if (xml.next() == 2 && "deny-ownership".equals(xml.getName())) {
                        xml.next();
                        String text = xml.getText();
                        if (text != null && !text.isBlank()) {
                            arraySet.add(text);
                            if (arraySet.size() > 1000) {
                                break;
                            }
                        }
                    }
                } finally {
                }
            }
            xml.close();
            return arraySet;
        } catch (Exception e) {
            Slog.e("PackageManager", "Failed to parse update owner list for " + packageSetting.getPackageName(), e);
            return null;
        }
    }

    public void addToUpdateOwnerDenyList(String str, ArraySet arraySet) {
        synchronized (this.mLock) {
            for (int i = 0; i < arraySet.size(); i++) {
                ArraySet arraySet2 = (ArraySet) this.mUpdateOwnerOptOutsToOwners.putIfAbsent((String) arraySet.valueAt(i), new ArraySet(new String[]{str}));
                if (arraySet2 != null) {
                    arraySet2.add(str);
                }
            }
        }
    }

    public void removeUpdateOwnerDenyList(String str) {
        synchronized (this.mLock) {
            for (int size = this.mUpdateOwnerOptOutsToOwners.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = this.mUpdateOwnerOptOutsToOwners;
                ArraySet arraySet = (ArraySet) arrayMap.get(arrayMap.keyAt(size));
                if (arraySet.remove(str) && arraySet.isEmpty()) {
                    this.mUpdateOwnerOptOutsToOwners.removeAt(size);
                }
            }
        }
    }

    public boolean isUpdateOwnershipDenylisted(String str) {
        return this.mUpdateOwnerOptOutsToOwners.containsKey(str);
    }

    public boolean isSamsungApp(String str) {
        if (str != null) {
            return str.startsWith("com.samsung") || str.startsWith("com.sec");
        }
        Slog.w("PackageManager", "Package name is null while checking update-ownership");
        return false;
    }

    public boolean isUpdateOwnershipDenyListProvider(String str) {
        if (str == null) {
            return false;
        }
        synchronized (this.mLock) {
            for (int size = this.mUpdateOwnerOptOutsToOwners.size() - 1; size >= 0; size--) {
                if (((ArraySet) this.mUpdateOwnerOptOutsToOwners.valueAt(size)).contains(str)) {
                    return true;
                }
            }
            return false;
        }
    }
}
