package com.android.systemui.volume.util;

import android.content.Context;
import android.media.AudioManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AudioManagerWrapper {
    public final AudioManager am;

    public AudioManagerWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        Object systemService = context.getSystemService((Class<Object>) AudioManager.class);
        Intrinsics.checkNotNull(systemService);
        this.am = (AudioManager) systemService;
    }
}
