package com.android.systemui.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QsStatusEventLog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ QsStatusEventLog f$0;

    public /* synthetic */ QsStatusEventLog$$ExternalSyntheticLambda0(QsStatusEventLog qsStatusEventLog) {
        this.f$0 = qsStatusEventLog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Handler handler;
        QsStatusEventLog qsStatusEventLog = this.f$0;
        qsStatusEventLog.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        Context context = qsStatusEventLog.mContext;
        final int i = 0;
        long j = currentTimeMillis - context.getSharedPreferences("QsStatusEventLog_prefs", 0).getLong("big_data_weekly_time_stored_in_milliseconds", currentTimeMillis);
        if (j <= 0) {
            context.getSharedPreferences("QsStatusEventLog_prefs", 0).edit().putLong("big_data_weekly_time_stored_in_milliseconds", currentTimeMillis).apply();
            return;
        }
        if (j > QsStatusEventLog.SA_SEVEN_DAYS_IN_MILLISECONDS.longValue()) {
            Log.d("QsStatusEventLog", " time difference greater than seven days. Send Weekly status logs.");
            context.getSharedPreferences("QsStatusEventLog_prefs", 0).edit().putLong("big_data_weekly_time_stored_in_milliseconds", currentTimeMillis).apply();
            QSTileHost qSTileHost = qsStatusEventLog.mHost;
            Collection<QSTile> tiles = qSTileHost.getTiles();
            final String str = SystemUIAnalytics.sCurrentScreenID;
            Iterator it = qSTileHost.mQQSTileHost.mTiles.values().iterator();
            int i2 = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                handler = qsStatusEventLog.mHandler;
                if (!hasNext) {
                    break;
                }
                final QSTile qSTile = (QSTile) it.next();
                handler.postDelayed(new Runnable() { // from class: com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                QSTile qSTile2 = qSTile;
                                String str2 = str;
                                if (qSTile2 instanceof SQSTile) {
                                    SystemUIAnalytics.sendEventCDLog(str2, "QPBSE1001", "active", ((SQSTile) qSTile2).getTileMapKey());
                                    break;
                                }
                                break;
                            default:
                                QSTile qSTile3 = qSTile;
                                String str3 = str;
                                if (qSTile3 instanceof SQSTile) {
                                    SystemUIAnalytics.sendEventCDLog(str3, "QPBSE1002", "active", ((SQSTile) qSTile3).getTileMapKey());
                                    break;
                                }
                                break;
                        }
                    }
                }, i2 * 100);
                i2++;
            }
            for (final QSTile qSTile2 : tiles) {
                final int i3 = 1;
                handler.postDelayed(new Runnable() { // from class: com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                QSTile qSTile22 = qSTile2;
                                String str2 = str;
                                if (qSTile22 instanceof SQSTile) {
                                    SystemUIAnalytics.sendEventCDLog(str2, "QPBSE1001", "active", ((SQSTile) qSTile22).getTileMapKey());
                                    break;
                                }
                                break;
                            default:
                                QSTile qSTile3 = qSTile2;
                                String str3 = str;
                                if (qSTile3 instanceof SQSTile) {
                                    SystemUIAnalytics.sendEventCDLog(str3, "QPBSE1002", "active", ((SQSTile) qSTile3).getTileMapKey());
                                    break;
                                }
                                break;
                        }
                    }
                }, i2 * 100);
                i2++;
            }
        }
    }
}
