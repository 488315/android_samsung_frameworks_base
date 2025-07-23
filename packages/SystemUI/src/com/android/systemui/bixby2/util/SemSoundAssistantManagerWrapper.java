package com.android.systemui.bixby2.util;

import android.content.Context;
import com.samsung.android.media.SemSoundAssistantManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
