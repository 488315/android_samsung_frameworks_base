package com.android.server.tv.tunerresourcemanager;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LnbResource extends TunerResourceBasic {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder extends TunerResourceBasic.Builder {}

    public final String toString() {
        StringBuilder sb = new StringBuilder("LnbResource[handle=");
        sb.append(this.mHandle);
        sb.append(", isInUse=");
        sb.append(this.mIsInUse);
        sb.append(", ownerClientId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOwnerClientId, sb, "]");
    }
}
