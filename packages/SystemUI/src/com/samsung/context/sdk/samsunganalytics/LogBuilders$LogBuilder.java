package com.samsung.context.sdk.samsunganalytics;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class LogBuilders$LogBuilder {
    public final Map logs = new HashMap();

    public final void set(String str, String str2) {
        ((HashMap) this.logs).put(str, str2);
    }
}
