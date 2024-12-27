package com.android.server.battery.sleepcharging;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.battery.BattLogBuffer;
import com.android.server.battery.sleepcharging.PersonalPatternManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TimeZone;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SleepChargingTimeController {
    public static final LocalTime mRuglarUpdateCheckTime = LocalTime.of(16, 0);
    public Context mContext;
    public Handler mDeliveredHandler;
    public AnonymousClass2 mDeviceTimeChangedReceiver;
    public AnonymousClass1 mHandler;
    public boolean mIsEndCalled;
    public boolean mIsTodaySleepChargingFinished;
    public boolean mIsUserDismissSleepCharging;
    public AnonymousClass2 mNextAlarmChangedReceiver;
    public LocalDateTime mNextAlarmDateTimeWithinSleepTime;
    public PersonalPatternManager mPersonalPatternManager;
    public int mSavedMinutesToFullCharge;
    public LocalDateTime mScheduledSleepChargingEndTime;
    public LocalDateTime mScheduledSleepChargingStartTime;
    public LocalDateTime mScheduledUpdateTime;
    public LocalDateTime mScheduledWakeupTime;
    public LocalDateTime mSleepChargingEndDateTime;
    public SleepChargingStatus mSleepChargingStatus;
    public Map mSleepPatterns;
    public AnonymousClass2 mTimeReachedReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.battery.sleepcharging.SleepChargingTimeController$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SleepChargingTimeController this$0;

        public /* synthetic */ AnonymousClass2(SleepChargingTimeController sleepChargingTimeController, int i) {
            this.$r8$classId = i;
            this.this$0 = sleepChargingTimeController;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    LocalTime localTime = SleepChargingTimeController.mRuglarUpdateCheckTime;
                    Slog.i("[SS]SleepChargingTimeController", "[TimeReachedReceiver_onReceive]action:" + action);
                    if (!"ACTION_EXACT_NOTI_NOW_UPDATE_TIME".equals(action)) {
                        if (!"ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_START_TIME".equals(action)) {
                            if (!"ACTION_EXACT_NOTI_NOW_WAKEUP_TIME".equals(action)) {
                                if ("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME".equals(action)) {
                                    sendEmptyMessage(25);
                                    break;
                                }
                            } else {
                                sendEmptyMessage(24);
                                break;
                            }
                        } else {
                            sendEmptyMessage(23);
                            break;
                        }
                    } else {
                        sendEmptyMessage(21);
                        break;
                    }
                    break;
                case 1:
                    String action2 = intent.getAction();
                    LocalTime localTime2 = SleepChargingTimeController.mRuglarUpdateCheckTime;
                    Slog.i("[SS]SleepChargingTimeController", "[NextAlarmChangedReceiver_onReceive]action:" + action2);
                    if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(action2)) {
                        removeMessages(22);
                        sendEmptyMessage(22);
                        break;
                    }
                    break;
                default:
                    String action3 = intent.getAction();
                    LocalTime localTime3 = SleepChargingTimeController.mRuglarUpdateCheckTime;
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("[DeviceTimeChangedReceiver_onReceive]action:", action3, "[SS]SleepChargingTimeController");
                    SleepChargingStatus m307$$Nest$mgetSleepChargingStatus = SleepChargingTimeController.m307$$Nest$mgetSleepChargingStatus(this.this$0);
                    SleepChargingStatus sleepChargingStatus = this.this$0.mSleepChargingStatus;
                    boolean z = sleepChargingStatus == null || m307$$Nest$mgetSleepChargingStatus == null || !m307$$Nest$mgetSleepChargingStatus.matchedDate.isEqual(sleepChargingStatus.matchedDate) || m307$$Nest$mgetSleepChargingStatus.isNowSessionTime != this.this$0.mSleepChargingStatus.isNowSessionTime;
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("TimeZone:", TimeZone.getDefault() != null ? TimeZone.getDefault().getID() : "", " ,elapsedRealtime:");
                    m.append(SystemClock.elapsedRealtime());
                    m.append(" ,shouldReset:");
                    m.append(z);
                    m.append(" ,isTodaySleepChargingFinished:");
                    m.append(this.this$0.mIsTodaySleepChargingFinished);
                    m.append(" ,isUserDismissSleepCharging:");
                    m.append(this.this$0.mIsUserDismissSleepCharging);
                    String sb = m.toString();
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m("[DeviceTimeChangedReceiver_onReceive]", sb, "[SS]SleepChargingTimeController");
                    if (z) {
                        BattLogBuffer.addLog(2, "[DeviceTimeChanged]" + sb);
                        sendEmptyMessage(21);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SleepChargingStatus {
        public final LocalDateTime bedDateTime;
        public final float confidence;
        public LocalDateTime expectedFullChargeDateTime;
        public final boolean isConfident;
        public boolean isNowSessionTime;
        public final LocalDate matchedDate;
        public final LocalDateTime sleepChargingStartDateTime;
        public final LocalDateTime wakeupDateTime;

        /* renamed from: -$$Nest$mupdateExpectedFullChargeDateTime, reason: not valid java name */
        public static void m308$$Nest$mupdateExpectedFullChargeDateTime(SleepChargingStatus sleepChargingStatus) {
            LocalDateTime localDateTime = SleepChargingTimeController.this.mNextAlarmDateTimeWithinSleepTime;
            if (localDateTime != null) {
                sleepChargingStatus.expectedFullChargeDateTime = localDateTime.minusMinutes(60L);
            } else {
                sleepChargingStatus.expectedFullChargeDateTime = sleepChargingStatus.wakeupDateTime.minusMinutes(60L);
            }
            LocalTime localTime = SleepChargingTimeController.mRuglarUpdateCheckTime;
            Slog.d("[SS]SleepChargingTimeController", "[updateExpectedFullChargeDateTime]expectedFullChargeDateTime:" + sleepChargingStatus.expectedFullChargeDateTime);
        }

        /* JADX WARN: Type inference failed for: r1v4, types: [java.time.LocalDateTime] */
        /* JADX WARN: Type inference failed for: r7v6, types: [java.time.LocalDateTime] */
        public SleepChargingStatus(PersonalPatternManager.SleepPattern sleepPattern, LocalDate localDate) {
            this.matchedDate = localDate;
            LocalTime localTime = SleepChargingTimeController.mRuglarUpdateCheckTime;
            Slog.i("[SS]SleepChargingTimeController", "[SleepChargingStatus]matchedDate:" + localDate + "(" + localDate.getDayOfWeek().toString() + ")");
            LocalTime localTime2 = Instant.ofEpochMilli(sleepPattern.wakeupTimeMillis).atZone(ZoneId.of("UTC")).toLocalDateTime().toLocalTime();
            LocalDateTime of = LocalDateTime.of(localDate, localTime2);
            ChronoUnit chronoUnit = ChronoUnit.MINUTES;
            LocalDateTime truncatedTo = of.truncatedTo(chronoUnit);
            this.wakeupDateTime = truncatedTo;
            LocalTime localTime3 = Instant.ofEpochMilli(sleepPattern.bedTimeMillis).atZone(ZoneId.of("UTC")).toLocalDateTime().toLocalTime();
            LocalDateTime truncatedTo2 = LocalDateTime.of(localTime3.isBefore(localTime2) ? localDate : localDate.minusDays(1L), localTime3).truncatedTo(chronoUnit);
            this.bedDateTime = truncatedTo2;
            Slog.i("[SS]SleepChargingTimeController", "bedDateTime:" + truncatedTo2);
            LocalDateTime minusMinutes = truncatedTo2.minusMinutes(30L);
            this.sleepChargingStartDateTime = minusMinutes;
            LocalDateTime now = LocalDateTime.now();
            this.isNowSessionTime = !now.isBefore(minusMinutes) && now.isBefore(truncatedTo);
            StringBuilder sb = new StringBuilder("sessionTime:");
            sb.append(minusMinutes);
            sb.append(" ~ ");
            sb.append(truncatedTo);
            sb.append(" ,isNowSessionTime:");
            HeimdAllFsService$$ExternalSyntheticOutline0.m("[SS]SleepChargingTimeController", sb, this.isNowSessionTime);
            float f = sleepPattern.confidence;
            this.confidence = f;
            boolean z = sleepPattern.isConfident;
            this.isConfident = z;
            Slog.i("[SS]SleepChargingTimeController", "confidence:" + f + " ,isConfident:" + z);
            this.expectedFullChargeDateTime = truncatedTo.minusMinutes(60L);
            StringBuilder sb2 = new StringBuilder("MARGIN_MINUTES_FOR_END:60 ,expectedFullChargeDateTime:");
            sb2.append(this.expectedFullChargeDateTime);
            Slog.i("[SS]SleepChargingTimeController", sb2.toString());
        }
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [java.time.LocalDateTime] */
    /* renamed from: -$$Nest$mgetNextAlarmDateTimeWithinSleepTime, reason: not valid java name */
    public static LocalDateTime m306$$Nest$mgetNextAlarmDateTimeWithinSleepTime(SleepChargingTimeController sleepChargingTimeController) {
        Slog.d("[SS]SleepChargingTimeController", "[getNextAlarmDateTimeWithinSleepTime]");
        AlarmManager alarmManager = (AlarmManager) sleepChargingTimeController.mContext.getSystemService("alarm");
        if (alarmManager == null) {
            Slog.e("[SS]SleepChargingTimeController", "Fail - AlarmManager null");
            return null;
        }
        AlarmManager.AlarmClockInfo nextAlarmClock = alarmManager.getNextAlarmClock();
        if (nextAlarmClock == null) {
            Slog.i("[SS]SleepChargingTimeController", "There is no next Alarm");
            return null;
        }
        PendingIntent showIntent = nextAlarmClock.getShowIntent();
        if (showIntent == null || showIntent.toString() == null) {
            Slog.e("[SS]SleepChargingTimeController", "wrong alarm pending intent:" + showIntent);
            return null;
        }
        if (!showIntent.toString().contains("com.sec.android.app.clockpackage startActivity")) {
            Slog.i("[SS]SleepChargingTimeController", "no sec clockpackage alaram:" + showIntent.toString());
            return null;
        }
        ?? localDateTime = Instant.ofEpochMilli(nextAlarmClock.getTriggerTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        StringBuilder sb = new StringBuilder("nextAlarmDateTime:");
        ChronoUnit chronoUnit = ChronoUnit.MINUTES;
        sb.append(localDateTime.truncatedTo(chronoUnit));
        Slog.i("[SS]SleepChargingTimeController", sb.toString());
        SleepChargingStatus sleepChargingStatus = sleepChargingTimeController.mSleepChargingStatus;
        if (!localDateTime.isBefore(sleepChargingStatus.sleepChargingStartDateTime) && localDateTime.isBefore(sleepChargingStatus.wakeupDateTime)) {
            return localDateTime.truncatedTo(chronoUnit);
        }
        Slog.i("[SS]SleepChargingTimeController", "Next Alarm is not within SleepTime");
        return null;
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [java.time.LocalDateTime] */
    /* renamed from: -$$Nest$mgetSleepChargingStatus, reason: not valid java name */
    public static SleepChargingStatus m307$$Nest$mgetSleepChargingStatus(SleepChargingTimeController sleepChargingTimeController) {
        sleepChargingTimeController.getClass();
        LocalDateTime now = LocalDateTime.now();
        PersonalPatternManager.SleepPattern sleepPattern = (PersonalPatternManager.SleepPattern) sleepChargingTimeController.mSleepPatterns.get(now.getDayOfWeek().toString());
        if (sleepPattern == null) {
            Slog.w("[SS]SleepChargingTimeController", "Fail to get todaySleepPattern => Fail to getSleepChargingStatus");
            return null;
        }
        LocalTime localTime = Instant.ofEpochMilli(sleepPattern.wakeupTimeMillis).atZone(ZoneId.of("UTC")).toLocalDateTime().toLocalTime();
        LocalDate localDate = now.toLocalDate();
        LocalDateTime truncatedTo = LocalDateTime.of(localDate, localTime).truncatedTo(ChronoUnit.MINUTES);
        Slog.d("[SS]SleepChargingTimeController", "[getSleepChargingStatus]todayWakeupDateTime:" + truncatedTo);
        SleepChargingStatus sleepChargingStatus = sleepChargingTimeController.mSleepChargingStatus;
        if ((sleepChargingStatus == null || !sleepChargingStatus.matchedDate.isEqual(localDate) || now.isBefore(sleepChargingTimeController.mSleepChargingStatus.wakeupDateTime)) && now.isBefore(truncatedTo)) {
            Slog.i("[SS]SleepChargingTimeController", "[getSleepChargingStatus]today SleepChargingStatus created");
            return sleepChargingTimeController.new SleepChargingStatus(sleepPattern, localDate);
        }
        LocalDate localDate2 = now.plusDays(1L).toLocalDate();
        PersonalPatternManager.SleepPattern sleepPattern2 = (PersonalPatternManager.SleepPattern) sleepChargingTimeController.mSleepPatterns.get(localDate2.getDayOfWeek().toString());
        if (sleepPattern2 == null) {
            Slog.w("[SS]SleepChargingTimeController", "Fail to get tomorrowSleepPattern => Fail to getSleepChargingStatus");
            return null;
        }
        Slog.i("[SS]SleepChargingTimeController", "[getSleepChargingStatus]tomorrow SleepChargingStatus created");
        return sleepChargingTimeController.new SleepChargingStatus(sleepPattern2, localDate2);
    }

    public final void activateDeviceTimeChangedReceiver(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m("[activateDeviceTimeChangedReceiver]activate:", "[SS]SleepChargingTimeController", z);
        if (z) {
            this.mDeviceTimeChangedReceiver = new AnonymousClass2(this, 2);
            this.mContext.registerReceiver(this.mDeviceTimeChangedReceiver, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.TIME_SET", "android.intent.action.TIMEZONE_CHANGED"), 2);
        } else {
            AnonymousClass2 anonymousClass2 = this.mDeviceTimeChangedReceiver;
            if (anonymousClass2 != null) {
                this.mContext.unregisterReceiver(anonymousClass2);
                this.mDeviceTimeChangedReceiver = null;
            }
        }
    }

    public final void activateNextAlarmChangedReceiver(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m("[activateNextAlarmChangedReceiver]activate:", "[SS]SleepChargingTimeController", z);
        if (z) {
            this.mNextAlarmChangedReceiver = new AnonymousClass2(this, 1);
            this.mContext.registerReceiver(this.mNextAlarmChangedReceiver, BatteryService$$ExternalSyntheticOutline0.m("android.app.action.NEXT_ALARM_CLOCK_CHANGED"), 2);
        } else {
            AnonymousClass2 anonymousClass2 = this.mNextAlarmChangedReceiver;
            if (anonymousClass2 != null) {
                this.mContext.unregisterReceiver(anonymousClass2);
                this.mNextAlarmChangedReceiver = null;
            }
        }
    }

    public final void activateTimeReachedReceiver(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m("[activateTimeReachedReceiver]activate:", "[SS]SleepChargingTimeController", z);
        if (!z) {
            AnonymousClass2 anonymousClass2 = this.mTimeReachedReceiver;
            if (anonymousClass2 != null) {
                this.mContext.unregisterReceiver(anonymousClass2);
                this.mTimeReachedReceiver = null;
                return;
            }
            return;
        }
        this.mTimeReachedReceiver = new AnonymousClass2(this, 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_EXACT_NOTI_NOW_UPDATE_TIME");
        intentFilter.addAction("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_START_TIME");
        intentFilter.addAction("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME");
        intentFilter.addAction("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
        this.mContext.registerReceiver(this.mTimeReachedReceiver, intentFilter, 2);
    }

    public final String getInfoForOn() {
        StringBuilder sb = new StringBuilder();
        if (this.mSleepChargingStatus != null) {
            sb.append("wakeupDateTime:" + this.mSleepChargingStatus.wakeupDateTime + ",  ");
        }
        sb.append("mNextAlarmDateTimeWithinSleepTime:" + this.mNextAlarmDateTimeWithinSleepTime + ",  ");
        sb.append("mSavedMinutesToFullCharge:" + this.mSavedMinutesToFullCharge + ",  ");
        StringBuilder sb2 = new StringBuilder("mSleepChargingEndDateTime:");
        sb2.append(this.mSleepChargingEndDateTime);
        sb.append(sb2.toString());
        return sb.toString();
    }

    public final boolean isActivated() {
        if (this.mIsTodaySleepChargingFinished) {
            Slog.v("[SS]SleepChargingTimeController", "[isActivated]mIsTodaySleepChargingFinished true => false");
            return false;
        }
        SleepChargingStatus sleepChargingStatus = this.mSleepChargingStatus;
        if (sleepChargingStatus == null) {
            Slog.v("[SS]SleepChargingTimeController", "[isActivated]mSleepChargingStatus null => false");
            return false;
        }
        if (!sleepChargingStatus.isConfident) {
            Slog.v("[SS]SleepChargingTimeController", "[isActivated]mSleepChargingStatus Confident False => false");
            return false;
        }
        if (sleepChargingStatus.isNowSessionTime) {
            Slog.v("[SS]SleepChargingTimeController", "[isActivated]true");
            return true;
        }
        Slog.v("[SS]SleepChargingTimeController", "[isActivated]mSleepChargingStatus isNowSessionTime False => false");
        return false;
    }

    public final boolean isNowSleepChargingTime(int i) {
        LocalDateTime truncatedTo = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime localDateTime = this.mSleepChargingEndDateTime;
        if (localDateTime == null) {
            localDateTime = this.mSleepChargingStatus.expectedFullChargeDateTime.minusMinutes(i);
        }
        boolean z = !truncatedTo.isBefore(this.mSleepChargingStatus.sleepChargingStartDateTime) && truncatedTo.isBefore(localDateTime);
        if (i == -1) {
            i = this.mSavedMinutesToFullCharge;
        }
        StringBuilder sb = new StringBuilder("[isNowSleepChargingTime]alarm:");
        sb.append(this.mNextAlarmDateTimeWithinSleepTime);
        sb.append(" ,minsToFullCharge:");
        sb.append(i);
        sb.append(" ,sleepTime:");
        sb.append(this.mSleepChargingStatus.bedDateTime);
        sb.append(" ~ ");
        sb.append(this.mSleepChargingStatus.wakeupDateTime);
        sb.append(" ,sleepChargingTime:");
        sb.append(this.mSleepChargingStatus.sleepChargingStartDateTime);
        sb.append(" ~ ");
        sb.append(localDateTime);
        sb.append(" ,result: ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("[SS]SleepChargingTimeController", sb, z);
        return z;
    }

    public final void reset(boolean z) {
        Slog.d("[SS]SleepChargingTimeController", "[reset]isEnd:" + z);
        activateDeviceTimeChangedReceiver(false);
        activateNextAlarmChangedReceiver(false);
        unsetTimeNoti("ACTION_EXACT_NOTI_NOW_UPDATE_TIME");
        unsetTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_START_TIME");
        unsetTimeNoti("ACTION_EXACT_NOTI_NOW_WAKEUP_TIME");
        unsetTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
        if (!z) {
            this.mSleepChargingEndDateTime = null;
            this.mIsTodaySleepChargingFinished = false;
            this.mIsUserDismissSleepCharging = false;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Slog.e("[SS]SleepChargingTimeController", "[reset]sleep exception");
                e.printStackTrace();
            }
        }
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1 != null) {
            anonymousClass1.removeCallbacksAndMessages(null);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.time.ZonedDateTime] */
    public final void setTimeNoti(String str) {
        char c;
        LocalDateTime of;
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        if (alarmManager == null) {
            Slog.e("[SS]SleepChargingTimeController", "Fail - AlarmManager null");
            sendEmptyMessage(21);
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
            case -794781894:
                if (str.equals("ACTION_EXACT_NOTI_NOW_UPDATE_TIME")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -655477721:
                if (str.equals("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_START_TIME")) {
                    c = 1;
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
            of = localTime.isBefore(localTime2) ? LocalDateTime.of(now.toLocalDate(), localTime2) : LocalDateTime.of(now.toLocalDate().plusDays(1L), localTime2);
            this.mScheduledUpdateTime = of;
        } else if (c == 1) {
            of = this.mSleepChargingStatus.sleepChargingStartDateTime;
            this.mScheduledSleepChargingStartTime = of;
        } else if (c != 2) {
            of = this.mSleepChargingEndDateTime;
            this.mScheduledSleepChargingEndTime = of;
        } else {
            of = this.mSleepChargingStatus.wakeupDateTime;
            this.mScheduledWakeupTime = of;
        }
        alarmManager.setExactAndAllowWhileIdle(0, of.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), PendingIntent.getBroadcast(this.mContext, 0, new Intent(str), 67108864));
        Slog.i("[SS]SleepChargingTimeController", "[setTimeNoti]action:" + str + " ,scheduledTime:" + of);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void unsetTimeNoti(String str) {
        char c;
        Slog.d("[SS]SleepChargingTimeController", "[unsetTimeNoti]action:".concat(str));
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        if (alarmManager == null) {
            Slog.e("[SS]SleepChargingTimeController", "Fail - AlarmManager null");
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
            case -794781894:
                if (str.equals("ACTION_EXACT_NOTI_NOW_UPDATE_TIME")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -655477721:
                if (str.equals("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_START_TIME")) {
                    c = 1;
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
            this.mScheduledSleepChargingStartTime = null;
        } else if (c != 2) {
            this.mScheduledSleepChargingEndTime = null;
        } else {
            this.mScheduledWakeupTime = null;
        }
        alarmManager.cancel(PendingIntent.getBroadcast(this.mContext, 0, new Intent(str), 67108864));
    }

    public final void updateSleepChargingEndTime(int i, boolean z) {
        Slog.d("[SS]SleepChargingTimeController", "[updateSleepChargingEndTime]isSleepCharging:" + z + " ,minutesToFullCharge:" + i);
        if (z && i != -1) {
            this.mSavedMinutesToFullCharge = i;
            this.mSleepChargingEndDateTime = this.mSleepChargingStatus.expectedFullChargeDateTime.minusMinutes(i);
            setTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
        } else if (z) {
            this.mSleepChargingEndDateTime = this.mSleepChargingStatus.expectedFullChargeDateTime.minusMinutes(this.mSavedMinutesToFullCharge);
            setTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
        } else {
            this.mSleepChargingEndDateTime = null;
            unsetTimeNoti("ACTION_EXACT_NOTI_NOW_SLEEP_CHARGING_END_TIME");
        }
        Slog.d("[SS]SleepChargingTimeController", "[updateSleepChargingEndTime]mSleepChargingEndDateTime:" + this.mSleepChargingEndDateTime);
    }
}
