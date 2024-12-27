package com.android.server.tv.tunerresourcemanager;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

public final class DemuxResource extends TunerResourceBasic {
    public final int mFilterTypes;

    public final class Builder extends TunerResourceBasic.Builder {
        public int mFilterTypes;
    }

    public DemuxResource(Builder builder) {
        super(builder);
        this.mFilterTypes = builder.mFilterTypes;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DemuxResource[handle=");
        sb.append(this.mHandle);
        sb.append(", filterTypes=");
        sb.append(this.mFilterTypes);
        sb.append(", isInUse=");
        sb.append(this.mIsInUse);
        sb.append(", ownerClientId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOwnerClientId, sb, "]");
    }
}
