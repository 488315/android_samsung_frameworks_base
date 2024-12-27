package com.android.server.tv.tunerresourcemanager;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

public final class LnbResource extends TunerResourceBasic {

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
