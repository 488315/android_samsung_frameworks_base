package com.android.systemui.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQQSTileHost;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCounterCoordinator;
import java.util.Collection;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class QsStatusEventLog {
    private static final String BIG_DATA_WEEKLY_TIME_STORED_IN_MILLI_SECONDS = "big_data_weekly_time_stored_in_milliseconds";
    private static final String PREFS = "QsStatusEventLog_prefs";
    private static final String TAG = "QsStatusEventLog";
    private final Context mContext;
    private final QSTileHost mHost;
    private static final Long SA_SEVEN_DAYS_IN_MILLISECONDS = Long.valueOf(NotifCounterCoordinator.MINIMUM_STATUS_UPDATE_PERIOD_MS);
    private static final Long SA_QS_STATUS_EVENT_LOG_SENDING_DELAY = 100L;
    private final Handler mHandler = new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER));
    private final SecPanelSAStatusLogInteractor mPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);

    public QsStatusEventLog(Context context, QSTileHost qSTileHost) {
        this.mContext = context;
        this.mHost = qSTileHost;
        startTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkWeeklyStatus() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - this.mContext.getSharedPreferences(PREFS, 0).getLong(BIG_DATA_WEEKLY_TIME_STORED_IN_MILLI_SECONDS, currentTimeMillis);
        if (j <= 0) {
            updateTime(currentTimeMillis);
        } else if (j > SA_SEVEN_DAYS_IN_MILLISECONDS.longValue()) {
            Log.d(TAG, " time difference greater than seven days. Send Weekly status logs.");
            updateTime(currentTimeMillis);
            sendStatusEventLog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendStatusEventLog$0(QSTile qSTile, String str) {
        if (qSTile instanceof SQSTile) {
            SystemUIAnalytics.sendEventCDLog(str, SystemUIAnalytics.EID_QQS_ACTIVE_BUTTONS_RATIO, SystemUIAnalytics.QPBSE_KEY_ACTIVE, ((SQSTile) qSTile).getTileMapKey());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendStatusEventLog$1(QSTile qSTile, String str) {
        if (qSTile instanceof SQSTile) {
            SystemUIAnalytics.sendEventCDLog(str, SystemUIAnalytics.EID_QS_ACTIVE_BUTTONS_RATIO, SystemUIAnalytics.QPBSE_KEY_ACTIVE, ((SQSTile) qSTile).getTileMapKey());
        }
    }

    private void sendStatusEventLog() {
        Collection<QSTile> values = this.mHost.mTiles.values();
        final String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
        SecQQSTileHost secQQSTileHost = this.mHost.mQQSTileHost;
        int i = 0;
        if (secQQSTileHost != null) {
            for (final QSTile qSTile : secQQSTileHost.mTiles.values()) {
                final int i2 = 0;
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                QsStatusEventLog.lambda$sendStatusEventLog$0(qSTile, currentScreenID);
                                break;
                            default:
                                QsStatusEventLog.lambda$sendStatusEventLog$1(qSTile, currentScreenID);
                                break;
                        }
                    }
                }, SA_QS_STATUS_EVENT_LOG_SENDING_DELAY.longValue() * i);
                i++;
            }
        }
        for (final QSTile qSTile2 : values) {
            final int i3 = 1;
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            QsStatusEventLog.lambda$sendStatusEventLog$0(qSTile2, currentScreenID);
                            break;
                        default:
                            QsStatusEventLog.lambda$sendStatusEventLog$1(qSTile2, currentScreenID);
                            break;
                    }
                }
            }, SA_QS_STATUS_EVENT_LOG_SENDING_DELAY.longValue() * i);
            i++;
        }
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.mPanelSAStatusLogInteractor;
        if (secPanelSAStatusLogInteractor != null) {
            Handler handler = this.mHandler;
            Objects.requireNonNull(secPanelSAStatusLogInteractor);
            QsStatusEventLog$$ExternalSyntheticLambda3 qsStatusEventLog$$ExternalSyntheticLambda3 = new QsStatusEventLog$$ExternalSyntheticLambda3(secPanelSAStatusLogInteractor, 0);
            Long l = SA_QS_STATUS_EVENT_LOG_SENDING_DELAY;
            handler.postDelayed(qsStatusEventLog$$ExternalSyntheticLambda3, l.longValue() * i);
            Handler handler2 = this.mHandler;
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.mPanelSAStatusLogInteractor;
            Objects.requireNonNull(secPanelSAStatusLogInteractor2);
            handler2.postDelayed(new QsStatusEventLog$$ExternalSyntheticLambda3(secPanelSAStatusLogInteractor2, 1), l.longValue() * (i + 1));
            Handler handler3 = this.mHandler;
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.mPanelSAStatusLogInteractor;
            Objects.requireNonNull(secPanelSAStatusLogInteractor3);
            handler3.postDelayed(new QsStatusEventLog$$ExternalSyntheticLambda3(secPanelSAStatusLogInteractor3, 2), l.longValue() * (i + 2));
        }
    }

    private void updateTime(long j) {
        this.mContext.getSharedPreferences(PREFS, 0).edit().putLong(BIG_DATA_WEEKLY_TIME_STORED_IN_MILLI_SECONDS, j).apply();
    }

    public void startTimer() {
        Thread thread = new Thread(new QsStatusEventLog$$ExternalSyntheticLambda3(this, 3));
        thread.setName("WeeklySALogging");
        thread.start();
    }
}
