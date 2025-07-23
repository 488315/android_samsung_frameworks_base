package com.android.systemui.audio.soundcraft.interfaces.settings;

import android.content.Context;
import android.media.AudioManager;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.model.ModelProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0040, code lost:
    
        if (r0.volumeModel.device == 128) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0038, code lost:
    
        if (r5.audioManager.semGetCurrentDeviceType() == 8) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update() {
        /*
            r5 = this;
            com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants r0 = com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants.INSTANCE
            android.content.Context r1 = r5.context
            r0.getClass()
            android.content.ContentResolver r0 = r1.getContentResolver()
            java.lang.String r1 = "audio_soundcraft_app_setting"
            r2 = 1
            int r0 = android.provider.Settings.System.getInt(r0, r1, r2)
            r1 = 0
            if (r0 != r2) goto L17
            r0 = r2
            goto L18
        L17:
            r0 = r1
        L18:
            r5.isAppSettingEnabled = r0
            android.content.Context r0 = r5.context
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r3 = "buds_enable"
            int r0 = android.provider.Settings.System.getInt(r0, r3, r1)
            if (r0 != r2) goto L43
            com.android.systemui.audio.soundcraft.model.ModelProvider r0 = r5.modelProvider
            com.android.systemui.audio.soundcraft.model.common.VolumeModel r3 = r0.volumeModel
            int r3 = r3.device
            if (r3 != 0) goto L3a
            android.media.AudioManager r3 = r5.audioManager
            int r3 = r3.semGetCurrentDeviceType()
            r4 = 8
            if (r3 == r4) goto L44
        L3a:
            com.android.systemui.audio.soundcraft.model.common.VolumeModel r0 = r0.volumeModel
            int r0 = r0.device
            r3 = 128(0x80, float:1.8E-43)
            if (r0 != r3) goto L43
            goto L44
        L43:
            r2 = r1
        L44:
            r5.isBudsActive = r2
            android.content.Context r0 = r5.context
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "buds_plugin_package_name"
            java.lang.String r0 = android.provider.Settings.System.getString(r0, r1)
            if (r0 != 0) goto L56
            java.lang.String r0 = ""
        L56:
            r5.budsPluginPackageName = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings.update():void");
    }
}
