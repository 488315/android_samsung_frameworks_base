package com.android.server.autofill.ui;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.util.DebugUtils;
import android.view.autofill.IAutoFillManagerClient;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PendingUi {
    public final IAutoFillManagerClient client;
    public int mState = 1;
    public final IBinder mToken;
    public final int sessionId;

    public PendingUi(IBinder iBinder, int i, IAutoFillManagerClient iAutoFillManagerClient) {
        this.mToken = iBinder;
        this.sessionId = i;
        this.client = iAutoFillManagerClient;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PendingUi: [token=");
        sb.append(this.mToken);
        sb.append(", sessionId=");
        sb.append(this.sessionId);
        sb.append(", state=");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                sb, DebugUtils.flagsToString(PendingUi.class, "STATE_", this.mState), "]");
    }
}
