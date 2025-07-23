package com.android.internal.widget.remotecompose.core;

/* loaded from: classes6.dex */
public abstract class Operation {
    private static final boolean ENABLE_DIRTY_FLAG_OPTIMIZATION = true;
    private boolean mDirty = true;

    public abstract void apply(RemoteContext remoteContext);

    public abstract String deepToString(String str);

    public abstract void write(WireBuffer wireBuffer);

    public void markDirty() {
        this.mDirty = true;
    }

    public void markNotDirty() {
        this.mDirty = false;
    }

    public boolean isDirty() {
        return this.mDirty;
    }
}
