package com.android.server.pm;

import android.os.Binder;

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
