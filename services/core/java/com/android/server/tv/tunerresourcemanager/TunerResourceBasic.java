package com.android.server.tv.tunerresourcemanager;

public abstract class TunerResourceBasic {
    public final int mHandle;
    public boolean mIsInUse;
    public int mOwnerClientId = -1;

    public abstract class Builder {
        public final int mHandle;

        public Builder(int i) {
            this.mHandle = i;
        }
    }

    public TunerResourceBasic(Builder builder) {
        this.mHandle = builder.mHandle;
    }

    public final void setOwner(int i) {
        this.mIsInUse = true;
        this.mOwnerClientId = i;
    }
}
