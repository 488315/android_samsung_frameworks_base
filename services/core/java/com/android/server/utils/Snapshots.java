package com.android.server.utils;

public abstract class Snapshots {
    public static Object maybeSnapshot(Object obj) {
        return obj instanceof Snappable ? ((Snappable) obj).snapshot() : obj;
    }
}
