package com.android.server.wm;

import android.util.SparseIntArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MirrorActiveUids {
    public final SparseIntArray mUidStates = new SparseIntArray();
    public final SparseIntArray mNumNonAppVisibleWindowMap = new SparseIntArray();

    public final synchronized boolean hasNonAppVisibleWindow(int i) {
        return this.mNumNonAppVisibleWindowMap.get(i) > 0;
    }
}
