package com.android.systemui.bixby2.controller.volume;

import com.android.systemui.bixby2.actionresult.ActionResults;

public final class InvalidVolumeController extends VolumeType {
    public static final int $stable = 0;
    private final int status;
    private final String statusCode = ActionResults.RESULT_FAIL;

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStatus() {
        return this.status;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStatusCode() {
        return this.statusCode;
    }
}
