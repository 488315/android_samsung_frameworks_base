package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class IpPayloadEvent extends FilterEvent {
    private final int mDataLength;

    private IpPayloadEvent(int i) {
        this.mDataLength = i;
    }

    public int getDataLength() {
        return this.mDataLength;
    }
}
