package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationVolumeController extends VolumeType {
    private final String streamTypeToString = PluginLockStar.NOTIFICATION_TYPE;

    private final boolean isAllowedNotification(NotificationManager.Policy policy) {
        int i = policy.priorityCategories;
        return ((i & 2) == 0 && (i & 4) == 0 && (i & 1) == 0 && (i & 128) == 0 && (i & 8) == 0 && (i & 16) == 0 && (i & 256) == 0) ? false : true;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStreamType() {
        return 5;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public boolean isStreamDisabled() {
        return isVoiceCapable() && getRingerMode() != 2;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public boolean volumeStreamAllowedByDnd(NotificationManager.Policy policy) {
        return !isVoiceCapable() || isAllowedNotification(policy);
    }
}
