package com.android.server.tv.tunerresourcemanager;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import com.android.server.tv.tunerresourcemanager.TunerResourceBasic;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DemuxResource extends TunerResourceBasic {
    public final int mFilterTypes;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
