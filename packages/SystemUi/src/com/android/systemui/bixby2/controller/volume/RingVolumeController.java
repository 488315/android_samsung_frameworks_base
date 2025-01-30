package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;
import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RingVolumeController extends VolumeType {
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
