package com.android.systemui.bixby2.util;

import android.content.Context;
import com.samsung.android.media.SemSoundAssistantManager;

/* loaded from: classes.dex */
public final class SemSoundAssistantManagerWrapper {
    public static final int $stable = 8;
    private final boolean isAdjustMediaVolumeOnly;
    private SemSoundAssistantManager semSoundAssistantManager;

    public SemSoundAssistantManagerWrapper(Context context) {
        SemSoundAssistantManager semSoundAssistantManager = new SemSoundAssistantManager(context);
        this.semSoundAssistantManager = semSoundAssistantManager;
        this.isAdjustMediaVolumeOnly = semSoundAssistantManager.getVolumeMode(1);
    }

    public final SemSoundAssistantManager getSemSoundAssistantManager() {
        return this.semSoundAssistantManager;
    }

    public final boolean isAdjustMediaVolumeOnly() {
        return this.isAdjustMediaVolumeOnly;
    }

    public final void setSemSoundAssistantManager(SemSoundAssistantManager semSoundAssistantManager) {
        this.semSoundAssistantManager = semSoundAssistantManager;
    }
}
