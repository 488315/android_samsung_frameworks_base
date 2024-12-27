package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import android.media.AudioManager;
import kotlin.jvm.internal.Intrinsics;

public final class SystemServiceExtension {
    public static final SystemServiceExtension INSTANCE = new SystemServiceExtension();

    private SystemServiceExtension() {
    }

    public static AudioManager getAudioManager(Context context) {
        Object systemService = context.getSystemService((Class<Object>) AudioManager.class);
        Intrinsics.checkNotNull(systemService);
        return (AudioManager) systemService;
    }
}
