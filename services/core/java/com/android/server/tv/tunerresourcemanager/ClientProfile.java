package com.android.server.tv.tunerresourcemanager;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

import java.util.HashSet;
import java.util.Set;

public final class ClientProfile {
    public final int mId;
    public int mNiceValue;
    public int mPriority;
    public final int mProcessId;
    public final String mTvInputSessionId;
    public final int mUseCase;
    public int mPrimaryUsingFrontendHandle = -1;
    public final Set mUsingFrontendHandles = new HashSet();
    public final Set mShareFeClientIds = new HashSet();
    public final Set mUsingDemuxHandles = new HashSet();
    public Integer mShareeFeClientId = -1;
    public final Set mUsingLnbHandles = new HashSet();
    public int mUsingCasSystemId = -1;
    public int mUsingCiCamId = -1;
    public boolean mIsPriorityOverwritten = false;

    public final class Builder {
        public final int mId;
        public int mProcessId;
        public String mTvInputSessionId;
        public int mUseCase;

        public Builder(int i) {
            this.mId = i;
        }
    }

    public ClientProfile(Builder builder) {
        this.mId = builder.mId;
        this.mTvInputSessionId = builder.mTvInputSessionId;
        this.mUseCase = builder.mUseCase;
        this.mProcessId = builder.mProcessId;
    }

    public final int getPriority() {
        return this.mPriority - this.mNiceValue;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ClientProfile[id=");
        sb.append(this.mId);
        sb.append(", tvInputSessionId=");
        sb.append(this.mTvInputSessionId);
        sb.append(", useCase=");
        sb.append(this.mUseCase);
        sb.append(", processId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mProcessId, sb, "]");
    }
}
