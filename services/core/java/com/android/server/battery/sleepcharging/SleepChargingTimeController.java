package com.android.server.battery.sleepcharging;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.battery.BatteryLogger;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class SleepChargingTimeController {
  public static final String TAG = "[SS]" + SleepChargingTimeController.class.getSimpleName();
  public static final LocalTime mRuglarUpdateCheckTime = LocalTime.of(16, 0);
  public Context mContext;
  public Handler mDeliveredHandler;
  public BroadcastReceiver mDeviceTimeChangedReceiver;
  public Handler mHandler;
  public boolean mIsTodaySleepChargingFinished;
  public boolean mIsUserDismissSleepCharging;
  public BroadcastReceiver mNextAlarmChangedReceiver;
  public LocalDateTime mNextAlarmDateTimeWithinSleepTime;
  public PersonalPatternManager mPersonalPatternManager;
  public int mSavedMinutesToFullCharge;
  public LocalDateTime mScheduledBedTime;
  public LocalDateTime mScheduledSleepChargingEndTime;
  public LocalDateTime mScheduledUpdateTime;
  public LocalDateTime mScheduledWakeupTime;
  public boolean mSkipUpdateSleepPatternForTest;
  public LocalDateTime mSleepChargingEndDateTime;
  public SleepChargingStatus mSleepChargingStatus;
  public BroadcastReceiver mTimeReachedReceiver;
  public Map mSleepPatterns = new HashMap();
  public final int MSG_UPDATE_SLEEP_CHARGING_STATUS = 21;
  public final int MSG_NEXT_ALARM_CHANGED = 22;
  public final int MSG_NOW_BED_TIME = 23;
  public final int MSG_NOW_BETWEEN_SLEEP_TIME = 24;
  public final int MSG_NOW_WAKEUP_TIME = 25;
  public final int MSG_NOW_SLEEP_CHARGING_END_TIME = 26;
  public final String ACTION_EXACT_NOTI_NOW_UPDATE_TIME = "ACTION_EXACT_NOTI_NOW_UPDATE_TIME";
  public final String ACTION_EXACT_NOTI_NOW_BED_TIME = "ACTION_EXACT_NOTI_NOW_BED_TIME";
  public final String ACTION_EXACT_NOTI_NOW_WAKEUP_TIME = "ACTION_EXACT_NOTI_NOW_WAKEUP_TIME";
  public final String ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME =
      "ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME";
  public boolean mIsEndCalled = false;

  public SleepChargingTimeController(
      Context context, HandlerThread handlerThread, Handler handler) {
    String str = TAG;
    Slog.d(str, "SleepChargingTimeController Created");
    Slog.d(str, "mRuglarUpdateCheckTime:" + mRuglarUpdateCheckTime);
    this.mContext = context;
    this.mDeliveredHandler = handler;
    this.mPersonalPatternManager = new PersonalPatternManager(context);
    makeHandler(handlerThread);
    activateTimeReachedReceiver(true);
    this.mHandler.sendEmptyMessageDelayed(21, 5000L);
  }

  public void end() {
    Slog.i(TAG, "[end]");
    this.mIsEndCalled = true;
    activateTimeReachedReceiver(false);
    reset(true);
  }

  public final void reset(boolean z) {
    Slog.d(TAG, "[reset]isEnd:" + z);
    activateDeviceTimeChangedReceiver(false);
    activateNextAlarmChangedReceiver(false);
    unsetTimeNoti("ACTION_EXACT_NOTI_NOW_UPDATE_TIME");
    unsetTimeNoti("ACTION_EXACT_NOTI_NOW_BED_TIME");
    unsetTimeNoti("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME");
    unsetTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
    if (!z) {
      this.mSleepChargingEndDateTime = null;
      this.mIsTodaySleepChargingFinished = false;
      this.mIsUserDismissSleepCharging = false;
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        Slog.e(TAG, "[reset]sleep exception");
        e.printStackTrace();
      }
    }
    Handler handler = this.mHandler;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
    }
  }

  public void updateDismissSleepCharging() {
    Slog.d(TAG, "[updateDismissSleepCharging]");
    this.mIsUserDismissSleepCharging = true;
    activateNextAlarmChangedReceiver(false);
  }

  public void updateSleepChargingEndTime(boolean z, int i) {
    String str = TAG;
    Slog.d(str, "[updateSleepChargingEndTime]isSleepCharging:" + z + " ,minutesToFullCharge:" + i);
    if (z && i != -1) {
      this.mSavedMinutesToFullCharge = i;
      this.mSleepChargingEndDateTime =
          this.mSleepChargingStatus.expectedFullChargeDateTime.minusMinutes(i);
      setTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
    } else if (z) {
      this.mSleepChargingEndDateTime =
          this.mSleepChargingStatus.expectedFullChargeDateTime.minusMinutes(
              this.mSavedMinutesToFullCharge);
      setTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
    } else {
      this.mSleepChargingEndDateTime = null;
      unsetTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
    }
    Slog.d(
        str,
        "[updateSleepChargingEndTime]mSleepChargingEndDateTime:" + this.mSleepChargingEndDateTime);
  }

  public boolean isActivated() {
    if (this.mIsTodaySleepChargingFinished) {
      Slog.v(TAG, "[isActivated]mIsTodaySleepChargingFinished true => false");
      return false;
    }
    SleepChargingStatus sleepChargingStatus = this.mSleepChargingStatus;
    if (sleepChargingStatus == null) {
      Slog.v(TAG, "[isActivated]mSleepChargingStatus null => false");
      return false;
    }
    if (!sleepChargingStatus.isConfident) {
      Slog.v(TAG, "[isActivated]mSleepChargingStatus Confident False => false");
      return false;
    }
    if (!this.mSleepChargingStatus.isNowSleepTime) {
      Slog.v(TAG, "[isActivated]mSleepChargingStatus isNowSleepTime False => false");
      return false;
    }
    Slog.v(TAG, "[isActivated]true");
    return true;
  }

  public boolean isNowSleepChargingTime(int i) {
    LocalDateTime truncatedTo = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    LocalDateTime localDateTime = this.mSleepChargingEndDateTime;
    if (localDateTime == null) {
      localDateTime = this.mSleepChargingStatus.expectedFullChargeDateTime.minusMinutes(i);
    }
    boolean isDateTimeBetween =
        Util.isDateTimeBetween(truncatedTo, this.mSleepChargingStatus.bedDateTime, localDateTime);
    if (i == -1) {
      i = this.mSavedMinutesToFullCharge;
    }
    Slog.d(
        TAG,
        "[isNowSleepChargingTime]alarm:"
            + this.mNextAlarmDateTimeWithinSleepTime
            + " ,minsToFullCharge:"
            + i
            + " ,sTime:"
            + this.mSleepChargingStatus.bedDateTime
            + " ~ "
            + this.mSleepChargingStatus.wakeupDateTime
            + " ,sleepChargingEnd:"
            + localDateTime
            + " ,result: "
            + isDateTimeBetween);
    return isDateTimeBetween;
  }

  public boolean isTodaySleepChargingFinished() {
    return this.mIsTodaySleepChargingFinished;
  }

  public LocalDateTime getSleepChargingEndTime() {
    return this.mSleepChargingEndDateTime;
  }

  public final void makeHandler(HandlerThread handlerThread) {
    Slog.d(TAG, "[makeHandler]");
    this.mHandler =
        new Handler(
            handlerThread
                .getLooper()) { // from class:
                                // com.android.server.battery.sleepcharging.SleepChargingTimeController.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            Slog.i(SleepChargingTimeController.TAG, "[sct_handleMessage]msg:" + message.what);
            switch (message.what) {
              case 21:
                SleepChargingTimeController.this.reset(false);
                if (SleepChargingTimeController.this.mIsEndCalled) {
                  Slog.i(
                      SleepChargingTimeController.TAG,
                      "[sct_handleMessage]isEndCalled:"
                          + SleepChargingTimeController.this.mIsEndCalled);
                  break;
                } else {
                  if (!SleepChargingTimeController.this.mSkipUpdateSleepPatternForTest) {
                    SleepChargingTimeController sleepChargingTimeController =
                        SleepChargingTimeController.this;
                    sleepChargingTimeController.mSleepPatterns =
                        sleepChargingTimeController.getSleepPatterns();
                  }
                  if (SleepChargingTimeController.this.mSleepPatterns.isEmpty()) {
                    SleepChargingTimeController.this.mSleepChargingStatus = null;
                  } else {
                    SleepChargingTimeController.this.activateDeviceTimeChangedReceiver(true);
                    SleepChargingTimeController sleepChargingTimeController2 =
                        SleepChargingTimeController.this;
                    sleepChargingTimeController2.mSleepChargingStatus =
                        sleepChargingTimeController2.getSleepChargingStatus();
                  }
                  if (SleepChargingTimeController.this.mSleepChargingStatus != null) {
                    if (SleepChargingTimeController.this.mSleepChargingStatus.isConfident) {
                      if (SleepChargingTimeController.this.mSleepChargingStatus.isNowSleepTime) {
                        SleepChargingTimeController sleepChargingTimeController3 =
                            SleepChargingTimeController.this;
                        sleepChargingTimeController3.mNextAlarmDateTimeWithinSleepTime =
                            sleepChargingTimeController3.getNextAlarmDateTimeWithinSleepTime();
                        SleepChargingTimeController.this.mSleepChargingStatus
                            .updateExpectedFullChargeDateTime();
                        SleepChargingTimeController.this.activateNextAlarmChangedReceiver(true);
                      } else {
                        SleepChargingTimeController.this.setTimeNoti(
                            "ACTION_EXACT_NOTI_NOW_BED_TIME");
                      }
                    } else {
                      Slog.i(
                          SleepChargingTimeController.TAG,
                          "[sct_handleMessage]not confident => only wait for wakeup time");
                    }
                    SleepChargingTimeController.this.setTimeNoti(
                        "ACTION_EXACT_NOTI_NOW_WAKEUP_TIME");
                  } else {
                    SleepChargingTimeController.this.setTimeNoti(
                        "ACTION_EXACT_NOTI_NOW_UPDATE_TIME");
                  }
                  SleepChargingTimeController.this.mDeliveredHandler.sendEmptyMessage(27);
                  break;
                }
              case 22:
                if (SleepChargingTimeController.this.mNextAlarmChangedReceiver == null) {
                  Slog.e(SleepChargingTimeController.TAG, "alarm receiver already disabled");
                  break;
                } else {
                  LocalDateTime nextAlarmDateTimeWithinSleepTime =
                      SleepChargingTimeController.this.getNextAlarmDateTimeWithinSleepTime();
                  if (Util.isEqualDateTime(
                      SleepChargingTimeController.this.mNextAlarmDateTimeWithinSleepTime,
                      nextAlarmDateTimeWithinSleepTime)) {
                    Slog.i(SleepChargingTimeController.TAG, "The Next Alarm Not Changed");
                    break;
                  } else {
                    Slog.i(SleepChargingTimeController.TAG, "The Next Alarm Changed");
                    SleepChargingTimeController.this.mNextAlarmDateTimeWithinSleepTime =
                        nextAlarmDateTimeWithinSleepTime;
                    SleepChargingTimeController.this.mSleepChargingStatus
                        .updateExpectedFullChargeDateTime();
                    SleepChargingTimeController.this.mDeliveredHandler.sendEmptyMessage(29);
                    break;
                  }
                }
              case 23:
              case 24:
                SleepChargingTimeController.this.mSleepChargingStatus.isNowSleepTime = true;
                SleepChargingTimeController sleepChargingTimeController4 =
                    SleepChargingTimeController.this;
                sleepChargingTimeController4.mNextAlarmDateTimeWithinSleepTime =
                    sleepChargingTimeController4.getNextAlarmDateTimeWithinSleepTime();
                SleepChargingTimeController.this.mSleepChargingStatus
                    .updateExpectedFullChargeDateTime();
                SleepChargingTimeController.this.activateNextAlarmChangedReceiver(true);
                SleepChargingTimeController.this.mDeliveredHandler.sendEmptyMessage(28);
                break;
              case 25:
                SleepChargingTimeController.this.mSleepChargingStatus.isNowSleepTime = false;
                SleepChargingTimeController.this.activateNextAlarmChangedReceiver(false);
                SleepChargingTimeController.this.mNextAlarmDateTimeWithinSleepTime = null;
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(21);
                break;
              case 26:
                SleepChargingTimeController.this.mIsTodaySleepChargingFinished = true;
                SleepChargingTimeController.this.activateNextAlarmChangedReceiver(false);
                SleepChargingTimeController.this.mDeliveredHandler.sendEmptyMessage(28);
                break;
            }
          }
        };
  }

  public final Map getSleepPatterns() {
    long currentTimeMillis = System.currentTimeMillis();
    Map sleepPatterns = this.mPersonalPatternManager.getSleepPatterns();
    long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
    String str = TAG;
    Slog.d(str, "[getSleepPatterns]elapseMillis:" + currentTimeMillis2);
    if (sleepPatterns.isEmpty()) {
      Slog.w(str, "[getSleepPatterns]Fail");
      BatteryLogger.writeToFile(
          "/data/log/battery_service/sleep_charging_history",
          "RunestoneStatus",
          this.mPersonalPatternManager.queryRunestoneStatus());
    } else {
      Slog.i(str, "[getSleepPatterns]Success");
    }
    return sleepPatterns;
  }

  public final SleepChargingStatus getSleepChargingStatus() {
    LocalDateTime now = LocalDateTime.now();
    PersonalPatternManager.SleepPattern sleepPattern =
        (PersonalPatternManager.SleepPattern)
            this.mSleepPatterns.get(now.getDayOfWeek().toString());
    if (sleepPattern == null) {
      Slog.w(TAG, "Fail to get todaySleepPattern => Fail to getSleepChargingStatus");
      return null;
    }
    LocalTime convertMillisToLocalTime =
        Util.convertMillisToLocalTime(sleepPattern.wakeupTimeMillis, true);
    LocalDate localDate = now.toLocalDate();
    LocalDateTime truncatedTo =
        LocalDateTime.of(localDate, convertMillisToLocalTime).truncatedTo(ChronoUnit.MINUTES);
    String str = TAG;
    Slog.d(str, "[getSleepChargingStatus]todayWakeupDateTime:" + truncatedTo);
    SleepChargingStatus sleepChargingStatus = this.mSleepChargingStatus;
    if (((sleepChargingStatus == null
                || !sleepChargingStatus.matchedDate.isEqual(localDate)
                || now.isBefore(this.mSleepChargingStatus.wakeupDateTime))
            ? false
            : true)
        || !now.isBefore(truncatedTo)) {
      LocalDate localDate2 = now.plusDays(1L).toLocalDate();
      PersonalPatternManager.SleepPattern sleepPattern2 =
          (PersonalPatternManager.SleepPattern)
              this.mSleepPatterns.get(localDate2.getDayOfWeek().toString());
      if (sleepPattern2 == null) {
        Slog.w(str, "Fail to get tomorrowSleepPattern => Fail to getSleepChargingStatus");
        return null;
      }
      Slog.i(str, "[getSleepChargingStatus]tomorrow SleepChargingStatus created");
      return new SleepChargingStatus(sleepPattern2, localDate2);
    }
    Slog.i(str, "[getSleepChargingStatus]today SleepChargingStatus created");
    return new SleepChargingStatus(sleepPattern, localDate);
  }

  public final void activateTimeReachedReceiver(boolean z) {
    Slog.d(TAG, "[activateTimeReachedReceiver]activate:" + z);
    if (z) {
      this.mTimeReachedReceiver =
          new BroadcastReceiver() { // from class:
                                    // com.android.server.battery.sleepcharging.SleepChargingTimeController.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Slog.i(
                  SleepChargingTimeController.TAG,
                  "[TimeReachedReceiver_onReceive]action:" + action);
              if ("ACTION_EXACT_NOTI_NOW_UPDATE_TIME".equals(action)) {
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(21);
                return;
              }
              if ("ACTION_EXACT_NOTI_NOW_BED_TIME".equals(action)) {
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(23);
              } else if ("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME".equals(action)) {
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(25);
              } else if ("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME".equals(action)) {
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(26);
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("ACTION_EXACT_NOTI_NOW_UPDATE_TIME");
      intentFilter.addAction("ACTION_EXACT_NOTI_NOW_BED_TIME");
      intentFilter.addAction("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME");
      intentFilter.addAction("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
      this.mContext.registerReceiver(this.mTimeReachedReceiver, intentFilter);
      return;
    }
    BroadcastReceiver broadcastReceiver = this.mTimeReachedReceiver;
    if (broadcastReceiver != null) {
      this.mContext.unregisterReceiver(broadcastReceiver);
      this.mTimeReachedReceiver = null;
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Type inference failed for: r2v4, types: [java.time.ZonedDateTime] */
  public final void setTimeNoti(String str) {
    char c;
    LocalDateTime of;
    AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
    if (alarmManager == null) {
      Slog.e(TAG, "Fail - AlarmManager null");
      this.mHandler.sendEmptyMessage(21);
      return;
    }
    switch (str.hashCode()) {
      case -1624530300:
        if (str.equals("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case -1566916460:
        if (str.equals("ACTION_EXACT_NOTI_NOW_BED_TIME")) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case -794781894:
        if (str.equals("ACTION_EXACT_NOTI_NOW_UPDATE_TIME")) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      case -295366002:
        if (str.equals("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME")) {
          c = 3;
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    if (c == 0) {
      LocalDateTime now = LocalDateTime.now();
      LocalTime localTime = now.toLocalTime();
      LocalTime localTime2 = mRuglarUpdateCheckTime;
      if (localTime.isBefore(localTime2)) {
        of = LocalDateTime.of(now.toLocalDate(), localTime2);
      } else {
        of = LocalDateTime.of(now.toLocalDate().plusDays(1L), localTime2);
      }
      this.mScheduledUpdateTime = of;
    } else if (c == 1) {
      of = this.mSleepChargingStatus.bedDateTime;
      this.mScheduledBedTime = of;
    } else if (c == 2) {
      of = this.mSleepChargingStatus.wakeupDateTime;
      this.mScheduledWakeupTime = of;
    } else {
      of = this.mSleepChargingEndDateTime;
      this.mScheduledSleepChargingEndTime = of;
    }
    alarmManager.setExactAndAllowWhileIdle(
        0,
        of.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        PendingIntent.getBroadcast(this.mContext, 0, new Intent(str), 67108864));
    Slog.i(TAG, "[setTimeNoti]action:" + str + " ,scheduledTime:" + of);
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public final void unsetTimeNoti(String str) {
    char c;
    String str2 = TAG;
    Slog.d(str2, "[unsetTimeNoti]action:" + str);
    AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
    if (alarmManager == null) {
      Slog.e(str2, "Fail - AlarmManager null");
      return;
    }
    switch (str.hashCode()) {
      case -1624530300:
        if (str.equals("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case -1566916460:
        if (str.equals("ACTION_EXACT_NOTI_NOW_BED_TIME")) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case -794781894:
        if (str.equals("ACTION_EXACT_NOTI_NOW_UPDATE_TIME")) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      case -295366002:
        if (str.equals("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME")) {
          c = 3;
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    if (c == 0) {
      this.mScheduledUpdateTime = null;
    } else if (c == 1) {
      this.mScheduledBedTime = null;
    } else if (c == 2) {
      this.mScheduledWakeupTime = null;
    } else {
      this.mScheduledSleepChargingEndTime = null;
    }
    alarmManager.cancel(PendingIntent.getBroadcast(this.mContext, 0, new Intent(str), 67108864));
  }

  public final void activateNextAlarmChangedReceiver(boolean z) {
    Slog.d(TAG, "[activateNextAlarmChangedReceiver]activate:" + z);
    if (z) {
      this.mNextAlarmChangedReceiver =
          new BroadcastReceiver() { // from class:
                                    // com.android.server.battery.sleepcharging.SleepChargingTimeController.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Slog.i(
                  SleepChargingTimeController.TAG,
                  "[NextAlarmChangedReceiver_onReceive]action:" + action);
              if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(action)) {
                SleepChargingTimeController.this.mHandler.removeMessages(22);
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(22);
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
      this.mContext.registerReceiver(this.mNextAlarmChangedReceiver, intentFilter);
      return;
    }
    BroadcastReceiver broadcastReceiver = this.mNextAlarmChangedReceiver;
    if (broadcastReceiver != null) {
      this.mContext.unregisterReceiver(broadcastReceiver);
      this.mNextAlarmChangedReceiver = null;
    }
  }

  public final LocalDateTime getNextAlarmDateTimeWithinSleepTime() {
    String str = TAG;
    Slog.d(str, "[getNextAlarmDateTimeWithinSleepTime]");
    AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
    if (alarmManager == null) {
      Slog.e(str, "Fail - AlarmManager null");
      return null;
    }
    AlarmManager.AlarmClockInfo nextAlarmClock = alarmManager.getNextAlarmClock();
    if (nextAlarmClock == null) {
      Slog.i(str, "There is no next Alarm");
      return null;
    }
    LocalDateTime convertMillisToLocalDateTime =
        Util.convertMillisToLocalDateTime(nextAlarmClock.getTriggerTime(), false);
    Slog.i(
        str, "nextAlarmDateTime:" + convertMillisToLocalDateTime.truncatedTo(ChronoUnit.MINUTES));
    if (!Util.isDateTimeBetween(
        convertMillisToLocalDateTime,
        this.mSleepChargingStatus.bedDateTime,
        this.mSleepChargingStatus.wakeupDateTime)) {
      Slog.i(str, "Next Alarm is not within SleepTime");
      return null;
    }
    return convertMillisToLocalDateTime.truncatedTo(ChronoUnit.MINUTES);
  }

  public final void activateDeviceTimeChangedReceiver(boolean z) {
    Slog.d(TAG, "[activateDeviceTimeChangedReceiver]activate:" + z);
    if (z) {
      this.mDeviceTimeChangedReceiver =
          new BroadcastReceiver() { // from class:
                                    // com.android.server.battery.sleepcharging.SleepChargingTimeController.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Slog.w(
                  SleepChargingTimeController.TAG,
                  "[DeviceTimeChangedReceiver_onReceive]action:" + action);
              SleepChargingStatus sleepChargingStatus =
                  SleepChargingTimeController.this.getSleepChargingStatus();
              boolean z2 =
                  SleepChargingTimeController.this.mSleepChargingStatus == null
                      || sleepChargingStatus == null
                      || !sleepChargingStatus.matchedDate.isEqual(
                          SleepChargingTimeController.this.mSleepChargingStatus.matchedDate)
                      || sleepChargingStatus.isNowSleepTime
                          != SleepChargingTimeController.this.mSleepChargingStatus.isNowSleepTime;
              String str =
                  "TimeZone:"
                      + (TimeZone.getDefault() != null ? TimeZone.getDefault().getID() : "")
                      + " ,elapsedRealtime:"
                      + SystemClock.elapsedRealtime()
                      + " ,shouldReset:"
                      + z2
                      + " ,isTodaySleepChargingFinished:"
                      + SleepChargingTimeController.this.mIsTodaySleepChargingFinished
                      + " ,isUserDismissSleepCharging:"
                      + SleepChargingTimeController.this.mIsUserDismissSleepCharging;
              Slog.i(
                  SleepChargingTimeController.TAG, "[DeviceTimeChangedReceiver_onReceive]" + str);
              BatteryLogger.writeToFile(
                  "/data/log/battery_service/sleep_charging_history",
                  "DeviceTimeChanged:" + action,
                  str);
              if (z2) {
                SleepChargingTimeController.this.mHandler.sendEmptyMessage(21);
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.TIME_SET");
      intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
      this.mContext.registerReceiver(this.mDeviceTimeChangedReceiver, intentFilter);
      return;
    }
    BroadcastReceiver broadcastReceiver = this.mDeviceTimeChangedReceiver;
    if (broadcastReceiver != null) {
      this.mContext.unregisterReceiver(broadcastReceiver);
      this.mDeviceTimeChangedReceiver = null;
    }
  }

  public class SleepChargingStatus {
    public final LocalDateTime bedDateTime;
    public final float confidence;
    public LocalDateTime expectedFullChargeDateTime;
    public final boolean isConfident;
    public boolean isNowSleepTime;
    public final LocalDate matchedDate;
    public final LocalDateTime wakeupDateTime;

    public SleepChargingStatus(
        PersonalPatternManager.SleepPattern sleepPattern, LocalDate localDate) {
      this.matchedDate = localDate;
      Slog.i(
          SleepChargingTimeController.TAG,
          "[SleepChargingStatus]matchedDate:"
              + localDate
              + "("
              + localDate.getDayOfWeek().toString()
              + ")");
      LocalTime convertMillisToLocalTime =
          Util.convertMillisToLocalTime(sleepPattern.wakeupTimeMillis, true);
      LocalDateTime truncatedTo =
          LocalDateTime.of(localDate, convertMillisToLocalTime).truncatedTo(ChronoUnit.MINUTES);
      this.wakeupDateTime = truncatedTo;
      LocalTime convertMillisToLocalTime2 =
          Util.convertMillisToLocalTime(sleepPattern.bedTimeMillis, true);
      LocalDateTime truncatedTo2 =
          LocalDateTime.of(
                  convertMillisToLocalTime2.isBefore(convertMillisToLocalTime)
                      ? localDate
                      : localDate.minusDays(1L),
                  convertMillisToLocalTime2)
              .truncatedTo(ChronoUnit.MINUTES);
      this.bedDateTime = truncatedTo2;
      this.isNowSleepTime = Util.isDateTimeBetween(LocalDateTime.now(), truncatedTo2, truncatedTo);
      Slog.i(
          SleepChargingTimeController.TAG,
          "time:" + truncatedTo2 + " ~ " + truncatedTo + " ,isNowTime:" + this.isNowSleepTime);
      float f = sleepPattern.confidence;
      this.confidence = f;
      boolean z = sleepPattern.isConfident;
      this.isConfident = z;
      Slog.i(SleepChargingTimeController.TAG, "confidence:" + f + " ,isConfident:" + z);
      this.expectedFullChargeDateTime = truncatedTo.minusMinutes(60L);
      Slog.i(
          SleepChargingTimeController.TAG,
          "marginMinutes:60 ,expectedFullChargeDateTime:" + this.expectedFullChargeDateTime);
    }

    public final void updateExpectedFullChargeDateTime() {
      if (SleepChargingTimeController.this.mNextAlarmDateTimeWithinSleepTime != null) {
        this.expectedFullChargeDateTime =
            SleepChargingTimeController.this.mNextAlarmDateTimeWithinSleepTime.minusMinutes(60L);
      } else {
        this.expectedFullChargeDateTime = this.wakeupDateTime.minusMinutes(60L);
      }
      Slog.d(
          SleepChargingTimeController.TAG,
          "[updateExpectedFullChargeDateTime]expectedFullChargeDateTime:"
              + this.expectedFullChargeDateTime);
    }

    public final String getInfo() {
      Slog.d(SleepChargingTimeController.TAG, "[getInfo]");
      StringBuilder sb = new StringBuilder();
      sb.append(
          "[SleepChargingStatus]"
              + this.matchedDate
              + "("
              + this.matchedDate.getDayOfWeek().toString()
              + ")\n");
      sb.append(
          "time:"
              + this.bedDateTime
              + " ~ "
              + this.wakeupDateTime
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      sb.append(
          "confidence:"
              + this.confidence
              + ",isConfident:"
              + this.isConfident
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      StringBuilder sb2 = new StringBuilder();
      sb2.append("marginMinutes:60 ,expectedFullChargeDateTime:");
      sb2.append(this.expectedFullChargeDateTime);
      sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      sb.append(sb2.toString());
      sb.append("isNowTime:" + this.isNowSleepTime + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      return sb.toString();
    }
  }

  public void modifySleepPatternsForTest(String str, String str2, String str3, String str4) {
    String str5 = TAG;
    Slog.e(str5, "[modifySleepPatternsForTest]");
    if (this.mPersonalPatternManager == null) {
      Slog.e(str5, "[modifySleepPatternsForTest]mPersonalPatternManager null");
      return;
    }
    try {
      long millisFromTimeString = Util.getMillisFromTimeString(str3);
      long millisFromTimeString2 = Util.getMillisFromTimeString(str4);
      if (millisFromTimeString != -1 && millisFromTimeString2 != -1) {
        float parseFloat = Float.parseFloat(str2);
        PersonalPatternManager.SleepPattern sleepPattern =
            this.mPersonalPatternManager.getSleepPattern(
                str, millisFromTimeString, millisFromTimeString2, parseFloat, parseFloat >= 0.5f);
        if (sleepPattern == null) {
          return;
        }
        this.mSleepPatterns.put(str, sleepPattern);
        this.mSkipUpdateSleepPatternForTest = true;
        BatteryLogger.writeToFile(
            "/data/log/battery_service/sleep_charging_history", "modifySleepPatternsForTest", str);
        this.mHandler.sendEmptyMessage(21);
        return;
      }
      Slog.e(str5, "[modifySleepPatternsForTest]Parsing Fail");
    } catch (Exception e) {
      Slog.e(TAG, "[modifySleepPatternsForTest]Exception");
      e.printStackTrace();
    }
  }

  public String getInfo(boolean z) {
    Slog.d(TAG, "[getInfo]includeSleepPatterns:" + z);
    StringBuilder sb = new StringBuilder();
    sb.append(
        "[SleepChargingTimeController]"
            + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mSleepPatterns.size():"
            + this.mSleepPatterns.size()
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    if (z) {
      Iterator it = this.mSleepPatterns.entrySet().iterator();
      while (it.hasNext()) {
        sb.append(
            ((PersonalPatternManager.SleepPattern) ((Map.Entry) it.next()).getValue()).getInfo());
      }
    }
    SleepChargingStatus sleepChargingStatus = this.mSleepChargingStatus;
    if (sleepChargingStatus == null) {
      sb.append("SleepChargingStatus null\n");
    } else {
      sb.append(sleepChargingStatus.getInfo());
    }
    sb.append(
        "mIsTodaySleepChargingFinished:"
            + this.mIsTodaySleepChargingFinished
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mNextAlarmDateTimeWithinSleepTime:"
            + this.mNextAlarmDateTimeWithinSleepTime
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mSavedMinutesToFullCharge:"
            + this.mSavedMinutesToFullCharge
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mSleepChargingEndDateTime:"
            + this.mSleepChargingEndDateTime
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mScheduledUpdateTime:"
            + this.mScheduledUpdateTime
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mScheduledBedTime:" + this.mScheduledBedTime + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mScheduledSleepChargingEndTime:"
            + this.mScheduledSleepChargingEndTime
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(
        "mScheduledWakeupTime:"
            + this.mScheduledWakeupTime
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    return sb.toString();
  }

  public abstract class Util {
    public static String convertLocalDateTimeToHHmmString(LocalDateTime localDateTime) {
      return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [java.time.LocalDateTime] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.time.LocalDateTime] */
    public static LocalDateTime convertMillisToLocalDateTime(long j, boolean z) {
      Instant ofEpochMilli = Instant.ofEpochMilli(j);
      if (z) {
        return ofEpochMilli.atZone(ZoneId.of("UTC")).toLocalDateTime();
      }
      return ofEpochMilli.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime convertMillisToLocalTime(long j, boolean z) {
      return convertMillisToLocalDateTime(j, z).toLocalTime();
    }

    public static boolean isDateTimeBetween(
        LocalDateTime localDateTime, LocalDateTime localDateTime2, LocalDateTime localDateTime3) {
      return !localDateTime.isBefore(localDateTime2) && localDateTime.isBefore(localDateTime3);
    }

    public static boolean isEqualDateTime(
        LocalDateTime localDateTime, LocalDateTime localDateTime2) {
      if (localDateTime == null && localDateTime2 == null) {
        return true;
      }
      return (localDateTime == null
              || localDateTime2 == null
              || !localDateTime.isEqual(localDateTime2))
          ? false
          : true;
    }

    public static long getMillisFromTimeString(String str) {
      Slog.e(SleepChargingTimeController.TAG, "[getMillisFromTimeString]timeString:" + str);
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
      simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      try {
        Date parse = simpleDateFormat.parse(str);
        if (parse == null) {
          return -1L;
        }
        long time = parse.getTime();
        Slog.e(SleepChargingTimeController.TAG, "[getMillisFromTimeString]millis:" + time);
        return time;
      } catch (ParseException e) {
        Slog.e(SleepChargingTimeController.TAG, "time string parse fail");
        e.printStackTrace();
        return -1L;
      }
    }
  }
}
