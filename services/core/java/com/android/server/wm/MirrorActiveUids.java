package com.android.server.wm;

import android.util.SparseIntArray;

public final class MirrorActiveUids {
    public final SparseIntArray mUidStates = new SparseIntArray();
    public final SparseIntArray mNumNonAppVisibleWindowMap = new SparseIntArray();

    public final synchronized boolean hasNonAppVisibleWindow(int i) {
        return this.mNumNonAppVisibleWindowMap.get(i) > 0;
    }
}
