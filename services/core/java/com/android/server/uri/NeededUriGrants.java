package com.android.server.uri;

import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NeededUriGrants {
    public final int flags;
    public final String targetPkg;
    public final int targetUid;
    public final ArraySet uris = new ArraySet();

    public NeededUriGrants(int i, int i2, String str) {
        this.targetPkg = str;
        this.targetUid = i;
        this.flags = i2;
    }
}
