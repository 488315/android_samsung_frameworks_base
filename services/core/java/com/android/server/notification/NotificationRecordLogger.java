package com.android.server.notification;

import android.app.NotificationChannel;
import android.app.Person;
import android.os.Bundle;
import android.os.IInstalld;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface NotificationRecordLogger {
  void log(UiEventLogger.UiEventEnum uiEventEnum);

  void log(UiEventLogger.UiEventEnum uiEventEnum, NotificationRecord notificationRecord);

  void logNotificationAdjusted(
      NotificationRecord notificationRecord, int i, int i2, InstanceId instanceId);

  void logNotificationPosted(NotificationReported notificationReported);

  default NotificationReported prepareToLogNotificationPosted(
      NotificationRecord notificationRecord,
      NotificationRecord notificationRecord2,
      int i,
      int i2,
      InstanceId instanceId) {
    NotificationRecordPair notificationRecordPair =
        new NotificationRecordPair(notificationRecord, notificationRecord2);
    if (notificationRecordPair.shouldLogReported(i2)) {
      return new NotificationReported(
          notificationRecordPair,
          NotificationReportedEvent.fromRecordPair(notificationRecordPair),
          i,
          i2,
          instanceId);
    }
    return null;
  }

  default void logNotificationCancelled(NotificationRecord notificationRecord, int i, int i2) {
    log(NotificationCancelledEvent.fromCancelReason(i, i2), notificationRecord);
  }

  default void logNotificationVisibility(NotificationRecord notificationRecord, boolean z) {
    log(NotificationEvent.fromVisibility(z), notificationRecord);
  }

  public enum NotificationReportedEvent implements UiEventLogger.UiEventEnum {
    NOTIFICATION_POSTED(162),
    NOTIFICATION_UPDATED(FrameworkStatsLog.f640x2165d62a),
    NOTIFICATION_ADJUSTED(908);

    private final int mId;

    NotificationReportedEvent(int i) {
      this.mId = i;
    }

    public int getId() {
      return this.mId;
    }

    public static NotificationReportedEvent fromRecordPair(
        NotificationRecordPair notificationRecordPair) {
      return notificationRecordPair.old != null ? NOTIFICATION_UPDATED : NOTIFICATION_POSTED;
    }
  }

  public enum NotificationCancelledEvent implements UiEventLogger.UiEventEnum {
    INVALID(0),
    NOTIFICATION_CANCEL_CLICK(FrameworkStatsLog.f636xd07885aa),
    NOTIFICATION_CANCEL_USER_OTHER(FrameworkStatsLog.f643xde3a78eb),
    NOTIFICATION_CANCEL_USER_CANCEL_ALL(FrameworkStatsLog.f642x8c80549a),
    NOTIFICATION_CANCEL_ERROR(FrameworkStatsLog.f641xaad26533),
    NOTIFICATION_CANCEL_PACKAGE_CHANGED(168),
    NOTIFICATION_CANCEL_USER_STOPPED(169),
    NOTIFICATION_CANCEL_PACKAGE_BANNED(170),
    NOTIFICATION_CANCEL_APP_CANCEL(FrameworkStatsLog.f646xd1d8c3fe),
    NOTIFICATION_CANCEL_APP_CANCEL_ALL(FrameworkStatsLog.f644xd8079e8d),
    NOTIFICATION_CANCEL_LISTENER_CANCEL(173),
    NOTIFICATION_CANCEL_LISTENER_CANCEL_ALL(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_EMPTY_STATE_QUIET_MODE),
    NOTIFICATION_CANCEL_GROUP_SUMMARY_CANCELED(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP),
    NOTIFICATION_CANCEL_GROUP_OPTIMIZATION(
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT),
    NOTIFICATION_CANCEL_PACKAGE_SUSPENDED(177),
    NOTIFICATION_CANCEL_PROFILE_TURNED_OFF(178),
    NOTIFICATION_CANCEL_UNAUTOBUNDLED(FrameworkStatsLog.f633xa546c0ca),
    NOTIFICATION_CANCEL_CHANNEL_BANNED(180),
    NOTIFICATION_CANCEL_SNOOZED(181),
    NOTIFICATION_CANCEL_TIMEOUT(182),
    NOTIFICATION_CANCEL_CHANNEL_REMOVED(1261),
    NOTIFICATION_CANCEL_CLEAR_DATA(1262),
    NOTIFICATION_CANCEL_USER_PEEK(190),
    NOTIFICATION_CANCEL_USER_AOD(191),
    NOTIFICATION_CANCEL_USER_BUBBLE(1228),
    NOTIFICATION_CANCEL_USER_LOCKSCREEN(193),
    NOTIFICATION_CANCEL_USER_SHADE(192),
    NOTIFICATION_CANCEL_ASSISTANT(906);

    private final int mId;

    NotificationCancelledEvent(int i) {
      this.mId = i;
    }

    public int getId() {
      return this.mId;
    }

    public static NotificationCancelledEvent fromCancelReason(int i, int i2) {
      if (i2 == -1) {
        Log.wtf("NotificationRecordLogger", "Unexpected surface: " + i2 + " with reason " + i);
        return INVALID;
      }
      if (i != 2) {
        if (1 <= i && i <= 21) {
          return values()[i];
        }
        if (i == 22) {
          return NOTIFICATION_CANCEL_ASSISTANT;
        }
        Log.wtf("NotificationRecordLogger", "Unexpected reason: " + i + " with surface " + i2);
        return INVALID;
      }
      if (i2 == 0) {
        return NOTIFICATION_CANCEL_USER_OTHER;
      }
      if (i2 == 1) {
        return NOTIFICATION_CANCEL_USER_PEEK;
      }
      if (i2 == 2) {
        return NOTIFICATION_CANCEL_USER_AOD;
      }
      if (i2 == 3) {
        return NOTIFICATION_CANCEL_USER_SHADE;
      }
      if (i2 == 4) {
        return NOTIFICATION_CANCEL_USER_BUBBLE;
      }
      if (i2 == 5) {
        return NOTIFICATION_CANCEL_USER_LOCKSCREEN;
      }
      Log.wtf("NotificationRecordLogger", "Unexpected surface: " + i2 + " with reason " + i);
      return INVALID;
    }
  }

  public enum NotificationEvent implements UiEventLogger.UiEventEnum {
    NOTIFICATION_OPEN(197),
    NOTIFICATION_CLOSE(198),
    NOTIFICATION_SNOOZED(FrameworkStatsLog.f90x54490b7),
    NOTIFICATION_NOT_POSTED_SNOOZED(FrameworkStatsLog.f109x9a09c896),
    NOTIFICATION_CLICKED(320),
    NOTIFICATION_ACTION_CLICKED(321),
    NOTIFICATION_DETAIL_OPEN_SYSTEM(FrameworkStatsLog.TIF_TUNE_CHANGED),
    NOTIFICATION_DETAIL_CLOSE_SYSTEM(FrameworkStatsLog.AUTO_ROTATE_REPORTED),
    NOTIFICATION_DETAIL_OPEN_USER(329),
    NOTIFICATION_DETAIL_CLOSE_USER(330),
    NOTIFICATION_DIRECT_REPLIED(331),
    NOTIFICATION_SMART_REPLIED(332),
    NOTIFICATION_SMART_REPLY_VISIBLE(FrameworkStatsLog.DEVICE_ROTATED),
    NOTIFICATION_ACTION_CLICKED_0(450),
    NOTIFICATION_ACTION_CLICKED_1(FrameworkStatsLog.CDM_ASSOCIATION_ACTION),
    NOTIFICATION_ACTION_CLICKED_2(
        FrameworkStatsLog.MAGNIFICATION_TRIPLE_TAP_AND_HOLD_ACTIVATED_SESSION_REPORTED),
    NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_0(
        FrameworkStatsLog.MAGNIFICATION_FOLLOW_TYPING_FOCUS_ACTIVATED_SESSION_REPORTED),
    NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_1(454),
    NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_2(455),
    NOTIFICATION_ASSIST_ACTION_CLICKED_0(456),
    NOTIFICATION_ASSIST_ACTION_CLICKED_1(457),
    NOTIFICATION_ASSIST_ACTION_CLICKED_2(458);

    private final int mId;

    NotificationEvent(int i) {
      this.mId = i;
    }

    public int getId() {
      return this.mId;
    }

    public static NotificationEvent fromVisibility(boolean z) {
      return z ? NOTIFICATION_OPEN : NOTIFICATION_CLOSE;
    }

    public static NotificationEvent fromExpanded(boolean z, boolean z2) {
      return z2
          ? z ? NOTIFICATION_DETAIL_OPEN_USER : NOTIFICATION_DETAIL_CLOSE_USER
          : z ? NOTIFICATION_DETAIL_OPEN_SYSTEM : NOTIFICATION_DETAIL_CLOSE_SYSTEM;
    }

    public static NotificationEvent fromAction(int i, boolean z, boolean z2) {
      if (i < 0 || i > 2) {
        return NOTIFICATION_ACTION_CLICKED;
      }
      if (z) {
        return values()[NOTIFICATION_ASSIST_ACTION_CLICKED_0.ordinal() + i];
      }
      if (z2) {
        return values()[NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_0.ordinal() + i];
      }
      return values()[NOTIFICATION_ACTION_CLICKED_0.ordinal() + i];
    }
  }

  public enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
    NOTIFICATION_PANEL_OPEN(325),
    NOTIFICATION_PANEL_CLOSE(326);

    private final int mId;

    NotificationPanelEvent(int i) {
      this.mId = i;
    }

    public int getId() {
      return this.mId;
    }
  }

  public class NotificationRecordPair {
    public final NotificationRecord old;

    /* renamed from: r */
    public final NotificationRecord f1717r;

    public NotificationRecordPair(
        NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
      this.f1717r = notificationRecord;
      this.old = notificationRecord2;
    }

    public boolean shouldLogReported(int i) {
      NotificationRecord notificationRecord = this.f1717r;
      if (notificationRecord == null) {
        return false;
      }
      if (this.old == null || i > 0) {
        return true;
      }
      return (Objects.equals(
                  notificationRecord.getSbn().getChannelIdLogTag(),
                  this.old.getSbn().getChannelIdLogTag())
              && Objects.equals(
                  this.f1717r.getSbn().getGroupLogTag(), this.old.getSbn().getGroupLogTag())
              && this.f1717r.getSbn().getNotification().isGroupSummary()
                  == this.old.getSbn().getNotification().isGroupSummary()
              && Objects.equals(
                  this.f1717r.getSbn().getNotification().category,
                  this.old.getSbn().getNotification().category)
              && this.f1717r.getImportance() == this.old.getImportance()
              && NotificationRecordLogger.getLoggingImportance(this.f1717r)
                  == NotificationRecordLogger.getLoggingImportance(this.old)
              && this.f1717r.rankingScoreMatches(this.old.getRankingScore()))
          ? false
          : true;
    }

    public int getStyle() {
      return getStyle(this.f1717r.getSbn().getNotification().extras);
    }

    public final int getStyle(Bundle bundle) {
      String string;
      if (bundle == null
          || (string = bundle.getString("android.template")) == null
          || string.isEmpty()) {
        return 0;
      }
      return string.hashCode();
    }

    public int getNumPeople() {
      return getNumPeople(this.f1717r.getSbn().getNotification().extras);
    }

    public final int getNumPeople(Bundle bundle) {
      ArrayList parcelableArrayList;
      if (bundle == null
          || (parcelableArrayList =
                  bundle.getParcelableArrayList("android.people.list", Person.class))
              == null
          || parcelableArrayList.isEmpty()) {
        return 0;
      }
      return parcelableArrayList.size();
    }

    public int getAssistantHash() {
      String adjustmentIssuer = this.f1717r.getAdjustmentIssuer();
      if (adjustmentIssuer == null) {
        return 0;
      }
      return adjustmentIssuer.hashCode();
    }

    public int getInstanceId() {
      if (this.f1717r.getSbn().getInstanceId() == null) {
        return 0;
      }
      return this.f1717r.getSbn().getInstanceId().getId();
    }

    public int getNotificationIdHash() {
      return SmallHash.hash(
          this.f1717r.getSbn().getId() ^ Objects.hashCode(this.f1717r.getSbn().getTag()));
    }

    public int getChannelIdHash() {
      return SmallHash.hash(this.f1717r.getSbn().getNotification().getChannelId());
    }

    public int getGroupIdHash() {
      return SmallHash.hash(this.f1717r.getSbn().getGroup());
    }
  }

  public class NotificationReported {
    public final int alerting;
    public final int assistant_hash;
    public final float assistant_ranking_score;
    public final String category;
    public final int channel_id_hash;
    public final int event_id;
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

    public NotificationReported(
        NotificationRecordPair notificationRecordPair,
        NotificationReportedEvent notificationReportedEvent,
        int i,
        int i2,
        InstanceId instanceId) {
      this.event_id = notificationReportedEvent.getId();
      this.uid = notificationRecordPair.f1717r.getUid();
      this.package_name = notificationRecordPair.f1717r.getSbn().getPackageName();
      this.instance_id = notificationRecordPair.getInstanceId();
      this.notification_id_hash = notificationRecordPair.getNotificationIdHash();
      this.channel_id_hash = notificationRecordPair.getChannelIdHash();
      this.group_id_hash = notificationRecordPair.getGroupIdHash();
      this.group_instance_id = instanceId == null ? 0 : instanceId.getId();
      this.is_group_summary =
          notificationRecordPair.f1717r.getSbn().getNotification().isGroupSummary();
      this.category = notificationRecordPair.f1717r.getSbn().getNotification().category;
      this.style = notificationRecordPair.getStyle();
      this.num_people = notificationRecordPair.getNumPeople();
      this.position = i;
      this.importance =
          NotificationRecordLogger.getLoggingImportance(notificationRecordPair.f1717r);
      this.alerting = i2;
      this.importance_source = notificationRecordPair.f1717r.getImportanceExplanationCode();
      this.importance_initial = notificationRecordPair.f1717r.getInitialImportance();
      this.importance_initial_source =
          notificationRecordPair.f1717r.getInitialImportanceExplanationCode();
      this.importance_asst = notificationRecordPair.f1717r.getAssistantImportance();
      this.assistant_hash = notificationRecordPair.getAssistantHash();
      this.assistant_ranking_score = notificationRecordPair.f1717r.getRankingScore();
      this.is_ongoing = notificationRecordPair.f1717r.getSbn().isOngoing();
      this.is_foreground_service =
          NotificationRecordLogger.isForegroundService(notificationRecordPair.f1717r);
      this.timeout_millis =
          notificationRecordPair.f1717r.getSbn().getNotification().getTimeoutAfter();
      this.is_non_dismissible =
          NotificationRecordLogger.isNonDismissible(notificationRecordPair.f1717r);
    }
  }

  static int getLoggingImportance(NotificationRecord notificationRecord) {
    int importance = notificationRecord.getImportance();
    NotificationChannel channel = notificationRecord.getChannel();
    return channel == null
        ? importance
        : NotificationChannelLogger.getLoggingImportance(channel, importance);
  }

  static boolean isForegroundService(NotificationRecord notificationRecord) {
    return (notificationRecord.getSbn() == null
            || notificationRecord.getSbn().getNotification() == null
            || (notificationRecord.getSbn().getNotification().flags & 64) == 0)
        ? false
        : true;
  }

  static boolean isNonDismissible(NotificationRecord notificationRecord) {
    return (notificationRecord.getSbn() == null
            || notificationRecord.getSbn().getNotification() == null
            || (notificationRecord.getNotification().flags & IInstalld.FLAG_FORCE) == 0)
        ? false
        : true;
  }
}
