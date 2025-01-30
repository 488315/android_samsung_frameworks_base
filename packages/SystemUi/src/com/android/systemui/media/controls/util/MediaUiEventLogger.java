package com.android.systemui.media.controls.util;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaUiEventLogger {
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
    public final UiEventLogger logger;

    public MediaUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final InstanceId getNewInstanceId() {
        return this.instanceIdSequence.newInstanceId();
    }

    public final void logMediaRemoved(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_REMOVED, i, str, instanceId);
    }
}
