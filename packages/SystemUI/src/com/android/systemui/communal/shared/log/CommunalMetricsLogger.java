package com.android.systemui.communal.shared.log;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalMetricsLogger {
    public final List loggablePrefixes;
    public final StatsLogProxy statsLogProxy;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface StatsLogProxy {
    }

    public CommunalMetricsLogger(List<String> list, StatsLogProxy statsLogProxy) {
        this.loggablePrefixes = list;
        this.statsLogProxy = statsLogProxy;
    }

    public final boolean isLoggable(String str) {
        List list = this.loggablePrefixes;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return true;
            }
        }
        return false;
    }
}
