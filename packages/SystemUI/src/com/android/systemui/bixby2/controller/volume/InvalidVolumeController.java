package com.android.systemui.bixby2.controller.volume;

import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
