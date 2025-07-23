package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DvbsCodeRate {
    private final int mBitsPer1000Symbol;
    private final long mInnerFec;
    private final boolean mIsLinear;
    private final boolean mIsShortFrames;

    private DvbsCodeRate(long j, boolean z, boolean z2, int i) {
        this.mInnerFec = j;
        this.mIsLinear = z;
        this.mIsShortFrames = z2;
        this.mBitsPer1000Symbol = i;
    }

    public long getInnerFec() {
        return this.mInnerFec;
    }

    public boolean isLinear() {
        return this.mIsLinear;
    }

    public boolean isShortFrameEnabled() {
        return this.mIsShortFrames;
    }

    public int getBitsPer1000Symbol() {
        return this.mBitsPer1000Symbol;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int mBitsPer1000Symbol;
        private long mFec;
        private boolean mIsLinear;
        private boolean mIsShortFrames;

        private Builder() {
        }

        public Builder setInnerFec(long j) {
            this.mFec = j;
            return this;
        }

        public Builder setLinear(boolean z) {
            this.mIsLinear = z;
            return this;
        }

        public Builder setShortFrameEnabled(boolean z) {
            this.mIsShortFrames = z;
            return this;
        }

        public Builder setBitsPer1000Symbol(int i) {
            this.mBitsPer1000Symbol = i;
            return this;
        }

        public DvbsCodeRate build() {
            return new DvbsCodeRate(this.mFec, this.mIsLinear, this.mIsShortFrames, this.mBitsPer1000Symbol);
        }
    }
}
