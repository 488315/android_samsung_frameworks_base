package com.android.server.desktopmode;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.os.Bundle;
import android.os.UserHandle;

import com.android.server.LocalServices;
import com.android.server.pm.pkg.AndroidPackage;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

public abstract class DesktopModeLogger {
    public static final boolean SURVEY_LOG =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");

    public abstract class LazyHolder {
        public static final boolean SUPPORT_DMA;

        static {
            AndroidPackage androidPackage;
            PackageManagerInternal packageManagerInternal =
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            boolean z = false;
            if (packageManagerInternal != null
                    && (androidPackage =
                                    packageManagerInternal.getPackage(
                                            "com.sec.android.diagmonagent"))
                            != null) {
                long longVersionCode = androidPackage.getLongVersionCode();
                if (DesktopModeFeature.DEBUG) {
                    boolean z2 = DesktopModeLogger.SURVEY_LOG;
                    Log.d(
                            "[DMS]DesktopModeLogger",
                            "versionCode of DMA package: " + longVersionCode);
                }
                if (longVersionCode >= 540000000) {
                    z = true;
                }
            }
            SUPPORT_DMA = z;
        }
    }

    public static void log(Context context, String str, String str2) {
        if (SURVEY_LOG) {
            if (!LazyHolder.SUPPORT_DMA) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", "com.samsung.android.desktopmode");
                contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
                if (str2 != null) {
                    contentValues.put("extra", str2);
                }
                context.sendBroadcastAsUser(
                        new Intent(
                                        "com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY")
                                .putExtra("data", contentValues)
                                .setPackage("com.samsung.android.providers.context"),
                        UserHandle.CURRENT_OR_SELF);
                if (DesktopModeFeature.DEBUG) {
                    Log.d(
                            "[DMS]DesktopModeLogger",
                            "Logging to GSIM feature=" + str + ", extra=" + str2);
                    return;
                }
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("tracking_id", "403-399-565756");
            bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
            bundle.putString("pkg_name", "com.samsung.android.desktopmode");
            if (str2 != null) {
                bundle.putString("extra", str2);
            }
            context.sendBroadcastAsUser(
                    new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY")
                            .putExtras(bundle)
                            .setPackage("com.sec.android.diagmonagent"),
                    UserHandle.CURRENT_OR_SELF);
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]DesktopModeLogger",
                        "Logging to DMA feature=" + str + ", extra=" + str2);
            }
        }
    }
}
