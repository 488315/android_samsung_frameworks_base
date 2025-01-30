package com.android.server.wm;

import android.app.compat.CompatChanges;
import android.content.pm.PackageManager;
import android.provider.DeviceConfig;
import java.util.HashSet;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public abstract class ActivitySecurityModelFeatureFlags {
    public static int sAsmRestrictionsEnabled;
    public static int sAsmToastsEnabled;
    public static final HashSet sExcludedPackageNames = new HashSet();
    public static PackageManager sPm;

    public static void initialize(Executor executor, PackageManager packageManager) {
        updateFromDeviceConfig();
        DeviceConfig.addOnPropertiesChangedListener("window_manager", executor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wm.ActivitySecurityModelFeatureFlags$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                ActivitySecurityModelFeatureFlags.updateFromDeviceConfig();
            }
        });
        sPm = packageManager;
    }

    public static boolean shouldShowToast(int i) {
        return flagEnabledForUid(sAsmToastsEnabled, i);
    }

    public static boolean shouldRestrictActivitySwitch(int i) {
        return flagEnabledForUid(sAsmRestrictionsEnabled, i);
    }

    public static boolean flagEnabledForUid(int i, int i2) {
        if (!(i == 2 || (i == 1 && CompatChanges.isChangeEnabled(230590090L, i2)))) {
            return false;
        }
        String[] packagesForUid = sPm.getPackagesForUid(i2);
        if (packagesForUid == null) {
            return true;
        }
        for (String str : packagesForUid) {
            if (sExcludedPackageNames.contains(str)) {
                return false;
            }
        }
        return true;
    }

    public static void updateFromDeviceConfig() {
        sAsmToastsEnabled = DeviceConfig.getInt("window_manager", "ActivitySecurity__asm_toasts_enabled", 0);
        sAsmRestrictionsEnabled = DeviceConfig.getInt("window_manager", "ActivitySecurity__asm_restrictions_enabled", 0);
        String string = DeviceConfig.getString("window_manager", "ActivitySecurity__asm_exempted_packages", "");
        sExcludedPackageNames.clear();
        for (String str : string.split(",")) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                sExcludedPackageNames.add(trim);
            }
        }
    }
}
