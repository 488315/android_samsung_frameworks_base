package com.android.server.pm;

public final class DefaultCrossProfileIntentFilter {
    public final int direction;
    public final WatchedIntentFilter filter;
    public final int flags;
    public final boolean letsPersonalDataIntoProfile;

    public DefaultCrossProfileIntentFilter(
            WatchedIntentFilter watchedIntentFilter, int i, int i2, boolean z) {
        this.filter = watchedIntentFilter;
        this.flags = i;
        this.direction = i2;
        this.letsPersonalDataIntoProfile = z;
    }
}
