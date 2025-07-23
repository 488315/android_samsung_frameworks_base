package com.android.systemui.util;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EventLogImpl implements EventLog {
    public static final int $stable = 0;

    @Override // com.android.systemui.util.EventLog
    public int writeEvent(int i, int i2) {
        return android.util.EventLog.writeEvent(i, i2);
    }

    @Override // com.android.systemui.util.EventLog
    public int writeEvent(int i, long j) {
        return android.util.EventLog.writeEvent(i, j);
    }

    @Override // com.android.systemui.util.EventLog
    public int writeEvent(int i, float f) {
        return android.util.EventLog.writeEvent(i, f);
    }

    @Override // com.android.systemui.util.EventLog
    public int writeEvent(int i, String str) {
        return android.util.EventLog.writeEvent(i, str);
    }

    @Override // com.android.systemui.util.EventLog
    public int writeEvent(int i, Object... objArr) {
        return android.util.EventLog.writeEvent(i, Arrays.copyOf(objArr, objArr.length));
    }
}
