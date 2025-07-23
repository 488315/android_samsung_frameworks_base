package com.android.systemui.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCounterCoordinator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QsStatusEventLog {
    private static final String BIG_DATA_WEEKLY_TIME_STORED_IN_MILLI_SECONDS = "big_data_weekly_time_stored_in_milliseconds";
    private static final String PREFS = "QsStatusEventLog_prefs";
    private static final String TAG = "QsStatusEventLog";
    private final Context mContext;
    private final Handler mHandler = new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER));
    private final SecPanelSAStatusLogInteractor mPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
    private final QSHost mQSHost;
    private final QSHost mQuickQSHost;
    private static final Long SA_SEVEN_DAYS_IN_MILLISECONDS = Long.valueOf(NotifCounterCoordinator.MINIMUM_STATUS_UPDATE_PERIOD_MS);
    private static final Long SA_QS_STATUS_EVENT_LOG_SENDING_DELAY = 100L;

    public QsStatusEventLog(Context context, QSHost qSHost, QSHost qSHost2) {
        this.mContext = context;
        this.mQSHost = qSHost;
        this.mQuickQSHost = qSHost2;
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
        Collection tiles = this.mQSHost.getTiles();
        final String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
        ArrayList arrayList = (ArrayList) this.mQuickQSHost.getTiles();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            final QSTile qSTile = (QSTile) obj;
            final int i4 = 0;
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            QsStatusEventLog.lambda$sendStatusEventLog$0(qSTile, currentScreenID);
                            break;
                        default:
                            QsStatusEventLog.lambda$sendStatusEventLog$1(qSTile, currentScreenID);
                            break;
                    }
                }
            }, SA_QS_STATUS_EVENT_LOG_SENDING_DELAY.longValue() * i2);
            i2++;
        }
        ArrayList arrayList2 = (ArrayList) tiles;
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            final QSTile qSTile2 = (QSTile) obj2;
            final int i5 = 1;
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i5) {
                        case 0:
                            QsStatusEventLog.lambda$sendStatusEventLog$0(qSTile2, currentScreenID);
                            break;
                        default:
                            QsStatusEventLog.lambda$sendStatusEventLog$1(qSTile2, currentScreenID);
                            break;
                    }
                }
            }, SA_QS_STATUS_EVENT_LOG_SENDING_DELAY.longValue() * i2);
            i2++;
        }
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.mPanelSAStatusLogInteractor;
        if (secPanelSAStatusLogInteractor != null) {
            Handler handler = this.mHandler;
            QsStatusEventLog$$ExternalSyntheticLambda3 qsStatusEventLog$$ExternalSyntheticLambda3 = new QsStatusEventLog$$ExternalSyntheticLambda3(secPanelSAStatusLogInteractor, 0);
            Long l = SA_QS_STATUS_EVENT_LOG_SENDING_DELAY;
            handler.postDelayed(qsStatusEventLog$$ExternalSyntheticLambda3, l.longValue() * i2);
            Handler handler2 = this.mHandler;
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.mPanelSAStatusLogInteractor;
            Objects.requireNonNull(secPanelSAStatusLogInteractor2);
            handler2.postDelayed(new QsStatusEventLog$$ExternalSyntheticLambda3(secPanelSAStatusLogInteractor2, 1), l.longValue() * (i2 + 1));
            Handler handler3 = this.mHandler;
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.mPanelSAStatusLogInteractor;
            Objects.requireNonNull(secPanelSAStatusLogInteractor3);
            handler3.postDelayed(new QsStatusEventLog$$ExternalSyntheticLambda3(secPanelSAStatusLogInteractor3, 2), l.longValue() * (i2 + 2));
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
