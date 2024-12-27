package com.android.systemui.audio.soundcraft.interfaces.settings;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.util.SettingsHelper;

public final class SoundCraftSettingConstants {
    public static final SoundCraftSettingConstants INSTANCE = new SoundCraftSettingConstants();

    private SoundCraftSettingConstants() {
    }

    public static void budsPluginPackageName(Context context, String str) {
        Settings.System.putString(context.getContentResolver(), "buds_plugin_package_name", str);
        Log.d("SoundCraft.SoundCraftSettingConstants", "budsPluginPackageName=".concat(str));
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
