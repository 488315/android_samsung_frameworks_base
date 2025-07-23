package com.android.systemui.audio.soundcraft.interfaces.settings;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftSettingConstants {
    public static final SoundCraftSettingConstants INSTANCE = new SoundCraftSettingConstants();

    private SoundCraftSettingConstants() {
    }

    public static void budsPluginPackageName(Context context, String str) {
        Settings.System.putString(context.getContentResolver(), "buds_plugin_package_name", str);
        Log.d("SoundCraft.SoundCraftSettingConstants", "budsPluginPackageName=" + str);
    }

    public static void isBudsConnected(Context context, boolean z) {
        Settings.System.putInt(context.getContentResolver(), SettingsHelper.INDEX_BUDS_ENABLE, z ? 1 : 0);
        Log.d("SoundCraft.SoundCraftSettingConstants", "isBudsConnected=" + z);
    }

    public static void isBudsPluginConnected(Context context, boolean z) {
        Settings.System.putInt(context.getContentResolver(), "buds_plugin_connection_state", z ? 1 : 0);
        Log.d("SoundCraft.SoundCraftSettingConstants", "isBudsPluginConnected=" + z);
    }
}
