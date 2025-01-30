package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.media.AudioSystem;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* loaded from: classes2.dex */
public abstract class PlatformTypeUtils {
    public static String getOneUIVersion(Context context) {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        int i2 = (i - KnoxCustomManagerService.ONE_UI_VERSION_SEP_VERSION_GAP) / 10000;
        int i3 = (i % 10000) / 100;
        return (context.getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite") ? "core" : "") + i2 + "." + i3;
    }

    public static boolean hasCmcFeature(Context context) {
        try {
            String[] split = String.valueOf(context.getPackageManager().getPackageInfo("com.samsung.android.mdecservice", 0).versionName).split("\\.");
            if (split.length == 4) {
                return Integer.parseInt(split[0]) >= 2;
            }
            return false;
        } catch (Exception e) {
            Log.e("AS.PlatformTypeUtils", "hasCmcFeature error", e);
            return false;
        }
    }

    public static int getPlatformType(Context context) {
        if (hasCmcFeature(context)) {
            return 1;
        }
        return AudioSystem.getPlatformType(context);
    }

    public static boolean isDeviceProvisioned(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public static boolean ignoreVoiceKey(Context context) {
        if (isDeviceProvisioned(context)) {
            return false;
        }
        Log.w("AS.PlatformTypeUtils", "don't call VoiceInteraction during setup-wizard");
        return true;
    }
}
