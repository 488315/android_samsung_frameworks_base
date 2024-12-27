package com.android.server.utils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Snapshots {
    public static Object maybeSnapshot(Object obj) {
        return obj instanceof Snappable ? ((Snappable) obj).snapshot() : obj;
    }
}
