package com.samsung.context.sdk.samsunganalytics;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class LogBuilders$LogBuilder {
    public final Map logs = new HashMap();

    public final void set(String str, String str2) {
        ((HashMap) this.logs).put(str, str2);
    }
}
