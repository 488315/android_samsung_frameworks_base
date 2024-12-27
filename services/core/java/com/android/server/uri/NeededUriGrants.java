package com.android.server.uri;

import android.util.ArraySet;

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
