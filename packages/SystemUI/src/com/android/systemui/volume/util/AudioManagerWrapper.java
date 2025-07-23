package com.android.systemui.volume.util;

import android.content.Context;
import android.media.AudioManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
