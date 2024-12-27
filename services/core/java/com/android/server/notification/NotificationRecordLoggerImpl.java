package com.android.server.notification;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NotificationRecordLoggerImpl implements NotificationRecordLogger {
    public UiEventLogger mUiEventLogger;

    public static void writeNotificationReportedAtom(
            NotificationRecordLogger.NotificationReported notificationReported) {
        int i = notificationReported.event_id;
        long j = notificationReported.post_duration_millis;
        FrameworkStatsLog.write(
                FrameworkStatsLog.NOTIFICATION_REPORTED,
                i,
                notificationReported.uid,
                notificationReported.package_name,
                notificationReported.instance_id,
                notificationReported.notification_id_hash,
                notificationReported.channel_id_hash,
                notificationReported.group_id_hash,
                notificationReported.group_instance_id,
                notificationReported.is_group_summary,
                notificationReported.category,
                notificationReported.style,
                notificationReported.num_people,
                notificationReported.position,
                notificationReported.importance,
                notificationReported.alerting,
                notificationReported.importance_source,
                notificationReported.importance_initial,
                notificationReported.importance_initial_source,
                notificationReported.importance_asst,
                notificationReported.assistant_hash,
                notificationReported.assistant_ranking_score,
                notificationReported.is_ongoing,
                notificationReported.is_foreground_service,
                notificationReported.timeout_millis,
                notificationReported.is_non_dismissible,
                j,
                notificationReported.fsi_state,
                notificationReported.is_locked,
                notificationReported.age_in_minutes);
    }

    public final void log(
            UiEventLogger.UiEventEnum uiEventEnum, NotificationRecord notificationRecord) {
        if (notificationRecord == null) {
            return;
        }
        this.mUiEventLogger.logWithInstanceId(
                uiEventEnum,
                notificationRecord.sbn.getUid(),
                notificationRecord.sbn.getPackageName(),
                notificationRecord.sbn.getInstanceId());
    }
}
