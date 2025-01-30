package com.android.systemui.p016qs.bar.soundcraft.model;

import android.content.Context;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.settings.SoundCraftSettings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ModelProvider {
    public BudsInfo budsInfo = new BudsInfo(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null);
    public String playingAudioPackageNameForAppSetting;
    public final SoundCraftSettings settings;

    public ModelProvider(Context context, SoundCraftSettings soundCraftSettings) {
        this.settings = soundCraftSettings;
    }
}
