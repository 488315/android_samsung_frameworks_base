package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public final class IpCidChangeEvent extends FilterEvent {
    private final int mCid;

    private IpCidChangeEvent(int i) {
        this.mCid = i;
    }

    public int getIpCid() {
        return this.mCid;
    }
}
