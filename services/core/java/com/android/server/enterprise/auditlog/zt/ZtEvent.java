package com.android.server.enterprise.auditlog.zt;

import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ZtEvent {
    public final Bundle bundle;
    public final int source;
    public final int tag;

    public ZtEvent(int i, int i2, Bundle bundle) {
        this.source = i;
        this.tag = i2;
        this.bundle = bundle;
    }
}
