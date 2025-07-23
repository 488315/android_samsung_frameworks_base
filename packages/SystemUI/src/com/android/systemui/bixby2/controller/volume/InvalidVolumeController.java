package com.android.systemui.bixby2.controller.volume;

import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
