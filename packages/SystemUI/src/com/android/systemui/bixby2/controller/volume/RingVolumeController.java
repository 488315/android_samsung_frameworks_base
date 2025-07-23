package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;
import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RingVolumeController extends VolumeType {
    public static final int $stable = 0;
    private final String streamTypeToString = "Ringtone";

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStatus() {
        return isVoiceCapable() ? 1 : 4;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStatusCode() {
        return isVoiceCapable() ? "success" : ActionResults.RESULT_DO_NOT_SUPPORT_CALL;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStreamType() {
        return 2;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public boolean volumeStreamAllowedByDnd(NotificationManager.Policy policy) {
        return true;
    }
}
