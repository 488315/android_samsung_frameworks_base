package com.android.server.pm;

import android.util.SparseArray;

public final class ChangedPackagesTracker {
    public int mChangedPackagesSequenceNumber;
    public final Object mLock = new Object();
    public final SparseArray mUserIdToSequenceToPackage = new SparseArray();
    public final SparseArray mChangedPackagesSequenceNumbers = new SparseArray();
}
