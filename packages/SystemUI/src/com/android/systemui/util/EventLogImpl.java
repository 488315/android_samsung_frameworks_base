package com.android.systemui.util;

import java.util.Arrays;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
