package com.android.systemui.volume.util;

import android.content.Context;
import android.media.AudioManager;
import kotlin.jvm.internal.Intrinsics;

public final class AudioManagerWrapper {
    public final AudioManager am;

    public AudioManagerWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        Object systemService = context.getSystemService((Class<Object>) AudioManager.class);
        Intrinsics.checkNotNull(systemService);
        this.am = (AudioManager) systemService;
    }
}
