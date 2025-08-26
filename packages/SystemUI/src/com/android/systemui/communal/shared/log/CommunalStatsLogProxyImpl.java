package com.android.systemui.communal.shared.log;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.communal.shared.log.CommunalMetricsLogger;

/* loaded from: classes2.dex */
public final class CommunalStatsLogProxyImpl implements CommunalMetricsLogger.StatsLogProxy {
    public final void writeCommunalHubWidgetEventReported(int i, int i2, int i3, String str) {
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(908);
        builderNewBuilder.writeInt(i);
        builderNewBuilder.writeString(str);
        builderNewBuilder.writeInt(i2);
        builderNewBuilder.writeInt(i3);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }
}
