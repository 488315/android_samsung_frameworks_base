package com.android.server.tv.tunerresourcemanager;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

import java.util.HashSet;
import java.util.Set;

public final class FrontendResource extends TunerResourceBasic {
    public final int mExclusiveGroupId;
    public final Set mExclusiveGroupMemberHandles;
    public final int mType;

    public final class Builder extends TunerResourceBasic.Builder {
        public int mExclusiveGroupId;
        public int mType;
    }

    public FrontendResource(Builder builder) {
        super(builder);
        this.mExclusiveGroupMemberHandles = new HashSet();
        this.mType = builder.mType;
        this.mExclusiveGroupId = builder.mExclusiveGroupId;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FrontendResource[handle=");
        sb.append(this.mHandle);
        sb.append(", type=");
        sb.append(this.mType);
        sb.append(", exclusiveGId=");
        sb.append(this.mExclusiveGroupId);
        sb.append(", exclusiveGMemeberHandles=");
        sb.append(this.mExclusiveGroupMemberHandles);
        sb.append(", isInUse=");
        sb.append(this.mIsInUse);
        sb.append(", ownerClientId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOwnerClientId, sb, "]");
    }
}
