package com.android.server.pm;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ChangedPackagesTracker {
    public int mChangedPackagesSequenceNumber;
    public final Object mLock = new Object();
    public final SparseArray mUserIdToSequenceToPackage = new SparseArray();
    public final SparseArray mChangedPackagesSequenceNumbers = new SparseArray();
}
