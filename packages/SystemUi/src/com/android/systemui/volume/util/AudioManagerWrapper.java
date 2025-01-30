package com.android.systemui.volume.util;

import android.content.Context;
import android.media.AudioManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioManagerWrapper {

    /* renamed from: am */
    public final AudioManager f398am;

    public AudioManagerWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        this.f398am = (AudioManager) context.getSystemService(AudioManager.class);
    }
}
