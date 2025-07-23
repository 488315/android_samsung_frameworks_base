package android.hardware.contexthub;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public class HubEndpointSessionResult {
    private final boolean mAccepted;
    private final String mReason;

    private HubEndpointSessionResult(boolean z, String str) {
        this.mAccepted = z;
        this.mReason = str;
    }

    public boolean isAccepted() {
        return this.mAccepted;
    }

    public String getReason() {
        return this.mReason;
    }

    public static HubEndpointSessionResult accept() {
        return new HubEndpointSessionResult(true, null);
    }

    public static HubEndpointSessionResult reject(String str) {
        return new HubEndpointSessionResult(false, str);
    }
}
