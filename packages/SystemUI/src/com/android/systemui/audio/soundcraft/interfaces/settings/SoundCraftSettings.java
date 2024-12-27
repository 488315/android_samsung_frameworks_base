package com.android.systemui.audio.soundcraft.interfaces.settings;

import android.content.Context;
import android.provider.Settings;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SoundCraftSettings {
    public String budsPluginPackageName = "";
    public final Context context;
    public boolean isAppSettingEnabled;
    public boolean isBudsActive;
    public final ModelProvider modelProvider;

    public SoundCraftSettings(Context context, ModelProvider modelProvider) {
        this.context = context;
        this.modelProvider = modelProvider;
    }

    public final String toString() {
        boolean z = this.isAppSettingEnabled;
        boolean z2 = this.isBudsActive;
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("[isAppSettingEnabled=", ", isBudsActive=", ", budsPluginPackageName=", z, z2), this.budsPluginPackageName, "]");
    }

    public final void update() {
        SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
        Context context = this.context;
        soundCraftSettingConstants.getClass();
        this.isAppSettingEnabled = Settings.System.getInt(context.getContentResolver(), "audio_soundcraft_app_setting", 1) == 1;
        this.isBudsActive = Settings.System.getInt(this.context.getContentResolver(), SettingsHelper.INDEX_BUDS_ENABLE, 0) == 1 && this.modelProvider.volumeModel.device == 128;
        String string = Settings.System.getString(this.context.getContentResolver(), "buds_plugin_package_name");
        if (string == null) {
            string = "";
        }
        this.budsPluginPackageName = string;
    }
}
