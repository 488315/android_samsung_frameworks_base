package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes3.dex */
public abstract class TunerResourceBasic {
    public final int mHandle;
    public boolean mIsInUse;
    public int mOwnerClientId = -1;

    public TunerResourceBasic(Builder builder) {
        this.mHandle = builder.mHandle;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public boolean isInUse() {
        return this.mIsInUse;
    }

    public int getOwnerClientId() {
        return this.mOwnerClientId;
    }

    public void setOwner(int i) {
        this.mIsInUse = true;
        this.mOwnerClientId = i;
    }

    public void removeOwner() {
        this.mIsInUse = false;
        this.mOwnerClientId = -1;
    }

    public abstract class Builder {
        public final int mHandle;

        public Builder(int i) {
            this.mHandle = i;
        }
    }
}
