package com.android.systemui.p016qs.bar.soundcraft.interfaces.settings;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftSettings {
    public String budsPluginPackageName = "";
    public final Context context;
    public boolean isAppSettingEnabled;

    public SoundCraftSettings(Context context) {
        this.context = context;
    }

    public final String toString() {
        return "[isAppSettingEnabled=" + this.isAppSettingEnabled + ", budsPluginPackageName=" + this.budsPluginPackageName + "]";
    }
}
