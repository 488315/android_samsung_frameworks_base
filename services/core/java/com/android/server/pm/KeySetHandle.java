package com.android.server.pm;

import android.os.Binder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KeySetHandle extends Binder {
    public final long mId;
    public int mRefCount = 1;

    public KeySetHandle(long j) {
        this.mId = j;
    }

    public KeySetHandle(long j, int i) {
        this.mId = j;
    }
}
