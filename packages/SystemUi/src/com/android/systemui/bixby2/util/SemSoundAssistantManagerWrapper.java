package com.android.systemui.bixby2.util;

import android.content.Context;
import com.samsung.android.media.SemSoundAssistantManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SemSoundAssistantManagerWrapper {
    private final boolean isAdjustMediaVolumeOnly;
    private SemSoundAssistantManager semSoundAssistantManager;

    public SemSoundAssistantManagerWrapper(Context context) {
        SemSoundAssistantManager semSoundAssistantManager = new SemSoundAssistantManager(context);
        this.semSoundAssistantManager = semSoundAssistantManager;
        this.isAdjustMediaVolumeOnly = semSoundAssistantManager.getVolumeKeyMode() == 1;
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
