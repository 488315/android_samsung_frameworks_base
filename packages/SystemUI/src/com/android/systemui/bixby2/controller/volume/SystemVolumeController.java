package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SystemVolumeController extends VolumeType {
    public static final int $stable = 0;
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
