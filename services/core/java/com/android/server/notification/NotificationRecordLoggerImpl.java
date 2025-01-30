package com.android.server.notification;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.notification.NotificationRecordLogger;

/* loaded from: classes2.dex */
public class NotificationRecordLoggerImpl implements NotificationRecordLogger {
    public UiEventLogger mUiEventLogger = new UiEventLoggerImpl();

    @Override // com.android.server.notification.NotificationRecordLogger
    public void logNotificationPosted(NotificationRecordLogger.NotificationReported notificationReported) {
        writeNotificationReportedAtom(notificationReported);
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void logNotificationAdjusted(NotificationRecord notificationRecord, int i, int i2, InstanceId instanceId) {
        writeNotificationReportedAtom(new NotificationRecordLogger.NotificationReported(new NotificationRecordLogger.NotificationRecordPair(notificationRecord, null), NotificationRecordLogger.NotificationReportedEvent.NOTIFICATION_ADJUSTED, i, i2, instanceId));
    }

    public final void writeNotificationReportedAtom(NotificationRecordLogger.NotificationReported notificationReported) {
        FrameworkStatsLog.write(FrameworkStatsLog.NOTIFICATION_REPORTED, notificationReported.event_id, notificationReported.uid, notificationReported.package_name, notificationReported.instance_id, notificationReported.notification_id_hash, notificationReported.channel_id_hash, notificationReported.group_id_hash, notificationReported.group_instance_id, notificationReported.is_group_summary, notificationReported.category, notificationReported.style, notificationReported.num_people, notificationReported.position, notificationReported.importance, notificationReported.alerting, notificationReported.importance_source, notificationReported.importance_initial, notificationReported.importance_initial_source, notificationReported.importance_asst, notificationReported.assistant_hash, notificationReported.assistant_ranking_score, notificationReported.is_ongoing, notificationReported.is_foreground_service, notificationReported.timeout_millis, notificationReported.is_non_dismissible, notificationReported.post_duration_millis);
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum, NotificationRecord notificationRecord) {
        if (notificationRecord == null) {
            return;
        }
        this.mUiEventLogger.logWithInstanceId(uiEventEnum, notificationRecord.getUid(), notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getInstanceId());
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum) {
        this.mUiEventLogger.log(uiEventEnum);
    }
}
