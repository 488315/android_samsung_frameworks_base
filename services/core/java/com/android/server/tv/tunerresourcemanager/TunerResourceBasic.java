package com.android.server.tv.tunerresourcemanager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class TunerResourceBasic {
    public final int mHandle;
    public boolean mIsInUse;
    public int mOwnerClientId = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
