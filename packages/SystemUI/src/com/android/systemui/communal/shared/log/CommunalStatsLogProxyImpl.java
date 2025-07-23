package com.android.systemui.communal.shared.log;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.communal.shared.log.CommunalMetricsLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalStatsLogProxyImpl implements CommunalMetricsLogger.StatsLogProxy {
    public final void writeCommunalHubWidgetEventReported(int i, int i2, int i3, String str) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(908);
        newBuilder.writeInt(i);
        newBuilder.writeString(str);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
