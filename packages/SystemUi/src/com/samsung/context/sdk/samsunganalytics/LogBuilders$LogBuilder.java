package com.samsung.context.sdk.samsunganalytics;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class LogBuilders$LogBuilder {
    public final Map logs = new HashMap();

    public abstract LogBuilders$LogBuilder getThis();

    public final void set(String str, String str2) {
        ((HashMap) this.logs).put(str, str2);
        getThis();
    }
}
