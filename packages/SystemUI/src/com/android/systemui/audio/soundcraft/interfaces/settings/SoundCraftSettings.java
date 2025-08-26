package com.android.systemui.audio.soundcraft.interfaces.settings;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes.dex */
public final class SoundCraftSettings {
    public final AudioManager audioManager;
    public String budsPluginPackageName = "";
    public final Context context;
    public boolean isAppSettingEnabled;
    public boolean isBudsActive;
    public final ModelProvider modelProvider;

    public SoundCraftSettings(Context context, ModelProvider modelProvider, AudioManager audioManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.audioManager = audioManager;
    }

    public final String toString() {
        boolean z = this.isAppSettingEnabled;
        boolean z2 = this.isBudsActive;
        return TransitionKt$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("[isAppSettingEnabled=", ", isBudsActive=", ", budsPluginPackageName=", z, z2), this.budsPluginPackageName, "]");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update() {
        SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
        Context context = this.context;
        soundCraftSettingConstants.getClass();
        boolean z = true;
        this.isAppSettingEnabled = Settings.System.getInt(context.getContentResolver(), "audio_soundcraft_app_setting", 1) == 1;
        if (Settings.System.getInt(this.context.getContentResolver(), SettingsHelper.INDEX_BUDS_ENABLE, 0) == 1) {
            ModelProvider modelProvider = this.modelProvider;
            if ((modelProvider.volumeModel.device != 0 || this.audioManager.semGetCurrentDeviceType() != 8) && modelProvider.volumeModel.device != 128) {
                z = false;
            }
        }
        this.isBudsActive = z;
        String string = Settings.System.getString(this.context.getContentResolver(), "buds_plugin_package_name");
        if (string == null) {
            string = "";
        }
        this.budsPluginPackageName = string;
    }
}
