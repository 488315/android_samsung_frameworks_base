package com.android.server.enterprise.auditlog.zt;

import android.os.Bundle;

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
