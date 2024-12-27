package com.android.systemui.bixby2.controller.volume;

import android.app.NotificationManager;
import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
