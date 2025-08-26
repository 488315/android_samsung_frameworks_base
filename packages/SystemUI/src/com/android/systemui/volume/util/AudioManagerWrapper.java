package com.android.systemui.volume.util;

import android.content.Context;
import android.media.AudioManager;

/* loaded from: classes3.dex */
public final class AudioManagerWrapper {
    public final AudioManager am;

    public AudioManagerWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        Object systemService = context.getSystemService((Class<Object>) AudioManager.class);
        systemService.getClass();
        this.am = (AudioManager) systemService;
    }
}
