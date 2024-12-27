package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import android.media.AudioManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
