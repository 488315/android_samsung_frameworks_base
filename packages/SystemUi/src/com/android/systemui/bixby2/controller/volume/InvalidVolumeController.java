package com.android.systemui.bixby2.controller.volume;

import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InvalidVolumeController extends VolumeType {
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
