package com.android.server.notification;

import android.app.NotificationChannel;
import android.util.Log;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface NotificationRecordLogger {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum NotificationCancelledEvent implements UiEventLogger.UiEventEnum {
        INVALID("INVALID"),
        /* JADX INFO: Fake field, exist only in values array */
        EF1("NOTIFICATION_CANCEL_CLICK"),
        NOTIFICATION_CANCEL_USER_OTHER("NOTIFICATION_CANCEL_USER_OTHER"),
        /* JADX INFO: Fake field, exist only in values array */
        EF3("NOTIFICATION_CANCEL_USER_CANCEL_ALL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF4("NOTIFICATION_CANCEL_ERROR"),
        /* JADX INFO: Fake field, exist only in values array */
        EF5("NOTIFICATION_CANCEL_PACKAGE_CHANGED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF6("NOTIFICATION_CANCEL_USER_STOPPED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF7("NOTIFICATION_CANCEL_PACKAGE_BANNED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF8("NOTIFICATION_CANCEL_APP_CANCEL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF9("NOTIFICATION_CANCEL_APP_CANCEL_ALL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF10("NOTIFICATION_CANCEL_LISTENER_CANCEL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF11("NOTIFICATION_CANCEL_LISTENER_CANCEL_ALL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF12("NOTIFICATION_CANCEL_GROUP_SUMMARY_CANCELED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF13("NOTIFICATION_CANCEL_GROUP_OPTIMIZATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("NOTIFICATION_CANCEL_PACKAGE_SUSPENDED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("NOTIFICATION_CANCEL_PROFILE_TURNED_OFF"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("NOTIFICATION_CANCEL_UNAUTOBUNDLED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("NOTIFICATION_CANCEL_CHANNEL_BANNED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("NOTIFICATION_CANCEL_SNOOZED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("NOTIFICATION_CANCEL_TIMEOUT"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("NOTIFICATION_CANCEL_CHANNEL_REMOVED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("NOTIFICATION_CANCEL_CLEAR_DATA"),
        NOTIFICATION_CANCEL_USER_PEEK("NOTIFICATION_CANCEL_USER_PEEK"),
        NOTIFICATION_CANCEL_USER_AOD("NOTIFICATION_CANCEL_USER_AOD"),
        NOTIFICATION_CANCEL_USER_BUBBLE("NOTIFICATION_CANCEL_USER_BUBBLE"),
        NOTIFICATION_CANCEL_USER_LOCKSCREEN("NOTIFICATION_CANCEL_USER_LOCKSCREEN"),
        NOTIFICATION_CANCEL_USER_SHADE("NOTIFICATION_CANCEL_USER_SHADE"),
        NOTIFICATION_CANCEL_ASSISTANT("NOTIFICATION_CANCEL_ASSISTANT");

        private final int mId;

        NotificationCancelledEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum NotificationEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_OPEN("NOTIFICATION_OPEN"),
        NOTIFICATION_CLOSE("NOTIFICATION_CLOSE"),
        NOTIFICATION_SNOOZED("NOTIFICATION_SNOOZED"),
        NOTIFICATION_NOT_POSTED_SNOOZED("NOTIFICATION_NOT_POSTED_SNOOZED"),
        NOTIFICATION_CLICKED("NOTIFICATION_CLICKED"),
        NOTIFICATION_ACTION_CLICKED("NOTIFICATION_ACTION_CLICKED"),
        NOTIFICATION_DETAIL_OPEN_SYSTEM("NOTIFICATION_DETAIL_OPEN_SYSTEM"),
        NOTIFICATION_DETAIL_CLOSE_SYSTEM("NOTIFICATION_DETAIL_CLOSE_SYSTEM"),
        NOTIFICATION_DETAIL_OPEN_USER("NOTIFICATION_DETAIL_OPEN_USER"),
        NOTIFICATION_DETAIL_CLOSE_USER("NOTIFICATION_DETAIL_CLOSE_USER"),
        NOTIFICATION_DIRECT_REPLIED("NOTIFICATION_DIRECT_REPLIED"),
        NOTIFICATION_SMART_REPLIED("NOTIFICATION_SMART_REPLIED"),
        NOTIFICATION_SMART_REPLY_VISIBLE("NOTIFICATION_SMART_REPLY_VISIBLE"),
        /* JADX INFO: Fake field, exist only in values array */
        EF171("NOTIFICATION_ACTION_CLICKED_0"),
        /* JADX INFO: Fake field, exist only in values array */
        EF184("NOTIFICATION_ACTION_CLICKED_1"),
        /* JADX INFO: Fake field, exist only in values array */
        EF197("NOTIFICATION_ACTION_CLICKED_2"),
        /* JADX INFO: Fake field, exist only in values array */
        EF210("NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_0"),
        /* JADX INFO: Fake field, exist only in values array */
        EF223("NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_1"),
        /* JADX INFO: Fake field, exist only in values array */
        EF236("NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_2"),
        /* JADX INFO: Fake field, exist only in values array */
        EF249("NOTIFICATION_ASSIST_ACTION_CLICKED_0"),
        /* JADX INFO: Fake field, exist only in values array */
        EF262("NOTIFICATION_ASSIST_ACTION_CLICKED_1"),
        /* JADX INFO: Fake field, exist only in values array */
        EF275("NOTIFICATION_ASSIST_ACTION_CLICKED_2");

        private final int mId;

        NotificationEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_PANEL_OPEN("NOTIFICATION_PANEL_OPEN"),
        NOTIFICATION_PANEL_CLOSE("NOTIFICATION_PANEL_CLOSE");

        private final int mId;

        NotificationPanelEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationRecordPair {
        public final NotificationRecord r;

        public NotificationRecordPair(
                NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
            this.r = notificationRecord;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationReported {
        public final int age_in_minutes;
        public final int alerting;
        public final int assistant_hash;
        public final float assistant_ranking_score;
        public final String category;
        public final int channel_id_hash;
        public final int event_id;
        public final int fsi_state;
        public final int group_id_hash;
        public final int group_instance_id;
        public final int importance;
        public final int importance_asst;
        public final int importance_initial;
        public final int importance_initial_source;
        public final int importance_source;
        public final int instance_id;
        public final boolean is_foreground_service;
        public final boolean is_group_summary;
        public final boolean is_locked;
        public final boolean is_non_dismissible;
        public final boolean is_ongoing;
        public final int notification_id_hash;
        public final int num_people;
        public final String package_name;
        public final int position;
        public long post_duration_millis;
        public final int style;
        public final long timeout_millis;
        public final int uid;

        /* JADX WARN: Removed duplicated region for block: B:52:0x017f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public NotificationReported(
                com.android.server.notification.NotificationRecordLogger.NotificationRecordPair r6,
                com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent
                        r7,
                int r8,
                int r9,
                com.android.internal.logging.InstanceId r10) {
            /*
                Method dump skipped, instructions count: 443
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.notification.NotificationRecordLogger.NotificationReported.<init>(com.android.server.notification.NotificationRecordLogger$NotificationRecordPair,"
                        + " com.android.server.notification.NotificationRecordLogger$NotificationReportedEvent,"
                        + " int, int, com.android.internal.logging.InstanceId):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum NotificationReportedEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_POSTED("NOTIFICATION_POSTED"),
        NOTIFICATION_UPDATED("NOTIFICATION_UPDATED"),
        NOTIFICATION_ADJUSTED("NOTIFICATION_ADJUSTED");

        private final int mId;

        NotificationReportedEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    static NotificationReported prepareToLogNotificationPosted(
            NotificationRecord notificationRecord,
            NotificationRecord notificationRecord2,
            int i,
            int i2,
            InstanceId instanceId) {
        int i3;
        NotificationRecordPair notificationRecordPair =
                new NotificationRecordPair(notificationRecord, notificationRecord2);
        if (notificationRecord2 != null
                && i2 <= 0
                && Objects.equals(
                        notificationRecord.sbn.getChannelIdLogTag(),
                        notificationRecord2.sbn.getChannelIdLogTag())
                && Objects.equals(
                        notificationRecord.sbn.getGroupLogTag(),
                        notificationRecord2.sbn.getGroupLogTag())
                && notificationRecord.sbn.getNotification().isGroupSummary()
                        == notificationRecord2.sbn.getNotification().isGroupSummary()
                && Objects.equals(
                        notificationRecord.sbn.getNotification().category,
                        notificationRecord2.sbn.getNotification().category)
                && (i3 = notificationRecord.mImportance) == notificationRecord2.mImportance) {
            NotificationChannel notificationChannel = notificationRecord.mChannel;
            if (notificationChannel != null) {
                i3 = NotificationChannelLogger.getLoggingImportance(notificationChannel, i3);
            }
            int i4 = notificationRecord2.mImportance;
            NotificationChannel notificationChannel2 = notificationRecord2.mChannel;
            if (notificationChannel2 != null) {
                i4 = NotificationChannelLogger.getLoggingImportance(notificationChannel2, i4);
            }
            if (i3 == i4) {
                if (Math.abs(notificationRecord.mRankingScore - notificationRecord2.mRankingScore)
                        < 1.0E-4d) {
                    return null;
                }
            }
        }
        NotificationReportedEvent notificationReportedEvent =
                NotificationReportedEvent.NOTIFICATION_POSTED;
        return new NotificationReported(
                notificationRecordPair,
                notificationRecord2 != null
                        ? NotificationReportedEvent.NOTIFICATION_UPDATED
                        : NotificationReportedEvent.NOTIFICATION_POSTED,
                i,
                i2,
                instanceId);
    }

    default void logNotificationCancelled(NotificationRecord notificationRecord, int i, int i2) {
        NotificationCancelledEvent notificationCancelledEvent = NotificationCancelledEvent.INVALID;
        if (i2 == -1) {
            Log.wtf("NotificationRecordLogger", "Unexpected surface: " + i2 + " with reason " + i);
        } else if (i == 2) {
            if (i2 == 0) {
                notificationCancelledEvent =
                        NotificationCancelledEvent.NOTIFICATION_CANCEL_USER_OTHER;
            } else if (i2 == 1) {
                notificationCancelledEvent =
                        NotificationCancelledEvent.NOTIFICATION_CANCEL_USER_PEEK;
            } else if (i2 == 2) {
                notificationCancelledEvent =
                        NotificationCancelledEvent.NOTIFICATION_CANCEL_USER_AOD;
            } else if (i2 == 3) {
                notificationCancelledEvent =
                        NotificationCancelledEvent.NOTIFICATION_CANCEL_USER_SHADE;
            } else if (i2 == 4) {
                notificationCancelledEvent =
                        NotificationCancelledEvent.NOTIFICATION_CANCEL_USER_BUBBLE;
            } else if (i2 != 5) {
                Log.wtf(
                        "NotificationRecordLogger",
                        "Unexpected surface: " + i2 + " with reason " + i);
            } else {
                notificationCancelledEvent =
                        NotificationCancelledEvent.NOTIFICATION_CANCEL_USER_LOCKSCREEN;
            }
        } else if (1 <= i && i <= 21) {
            notificationCancelledEvent = NotificationCancelledEvent.values()[i];
        } else if (i == 22) {
            notificationCancelledEvent = NotificationCancelledEvent.NOTIFICATION_CANCEL_ASSISTANT;
        } else {
            Log.wtf("NotificationRecordLogger", "Unexpected reason: " + i + " with surface " + i2);
        }
        ((NotificationRecordLoggerImpl) this).log(notificationCancelledEvent, notificationRecord);
    }
}
