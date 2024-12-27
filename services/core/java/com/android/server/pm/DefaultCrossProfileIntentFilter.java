package com.android.server.pm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
