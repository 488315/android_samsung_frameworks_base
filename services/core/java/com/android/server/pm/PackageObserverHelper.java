package com.android.server.pm;

import android.util.ArraySet;

public final class PackageObserverHelper {
    public final Object mLock = new Object();
    public ArraySet mActiveSnapshot = new ArraySet();
}
