package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemVolumeController extends VolumeType {
    private final String streamTypeToString = "System";

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStreamType() {
        return 1;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public boolean isStreamDisabled() {
        return getRingerMode() != 2;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public boolean volumeStreamAllowedByDnd(NotificationManager.Policy policy) {
        return (policy.priorityCategories & 128) != 0;
    }
}
