package android.service.notification;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.service.notification.ZenModeConfig;
import android.util.ArraySet;
import android.util.Log;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class ScheduleCalendar {
    public static final boolean DEBUG = Log.isLoggable("ConditionProviders", 3);
    public static final String TAG = "ScheduleCalendar";
    private ZenModeConfig.ScheduleInfo mSchedule;
    private final ArraySet<Integer> mDays = new ArraySet<>();
    private final Calendar mCalendar = Calendar.getInstance();

    public String toString() {
        return "ScheduleCalendar[mDays=" + this.mDays + ", mSchedule=" + this.mSchedule + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean exitAtAlarm() {
        return this.mSchedule.exitAtAlarm;
    }

    public void setSchedule(ZenModeConfig.ScheduleInfo scheduleInfo) {
        if (Objects.equals(this.mSchedule, scheduleInfo)) {
            return;
        }
        this.mSchedule = scheduleInfo;
        updateDays();
    }

    public void maybeSetNextAlarm(long j, long j2) {
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo == null || !scheduleInfo.exitAtAlarm) {
            return;
        }
        if (j2 == 0) {
            this.mSchedule.nextAlarm = 0L;
            return;
        }
        if (j2 > j) {
            this.mSchedule.nextAlarm = j2;
            return;
        }
        if (this.mSchedule.nextAlarm < j) {
            if (DEBUG) {
                Log.d(TAG, "All alarms are in the past " + this.mSchedule.nextAlarm);
            }
            this.mSchedule.nextAlarm = 0L;
        }
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mCalendar.setTimeZone(timeZone);
    }

    public long getNextChangeTime(long j) {
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo == null) {
            return 0L;
        }
        return Math.min(getNextTime(j, scheduleInfo.startHour, this.mSchedule.startMinute, true), getNextTime(j, this.mSchedule.endHour, this.mSchedule.endMinute, false));
    }

    private long getNextTime(long j, int i, int i2, boolean z) {
        long closestActualTime = z ? getClosestActualTime(j, i, i2) : getTime(j, i, i2);
        if (closestActualTime > j) {
            return closestActualTime;
        }
        long jAddDays = addDays(closestActualTime, 1);
        return z ? getClosestActualTime(jAddDays, i, i2) : getTime(jAddDays, i, i2);
    }

    private long getTime(long j, int i, int i2) {
        this.mCalendar.setTimeInMillis(j);
        this.mCalendar.set(11, i);
        this.mCalendar.set(12, i2);
        this.mCalendar.set(13, 0);
        this.mCalendar.set(14, 0);
        return this.mCalendar.getTimeInMillis();
    }

    public boolean isInSchedule(long j) {
        if (this.mSchedule == null || this.mDays.size() == 0) {
            return false;
        }
        long closestActualTime = getClosestActualTime(j, this.mSchedule.startHour, this.mSchedule.startMinute);
        long time = getTime(j, this.mSchedule.endHour, this.mSchedule.endMinute);
        if (time <= closestActualTime) {
            time = addDays(time, 1);
        }
        long j2 = time;
        return isInSchedule(-1, j, closestActualTime, j2) || isInSchedule(0, j, closestActualTime, j2);
    }

    public boolean isAlarmInSchedule(long j, long j2) {
        if (this.mSchedule != null && this.mDays.size() != 0) {
            long closestActualTime = getClosestActualTime(j, this.mSchedule.startHour, this.mSchedule.startMinute);
            long time = getTime(j, this.mSchedule.endHour, this.mSchedule.endMinute);
            if (time <= closestActualTime) {
                time = addDays(time, 1);
            }
            if ((isInSchedule(-1, j, closestActualTime, time) && isInSchedule(-1, j2, closestActualTime, time)) || (isInSchedule(0, j, closestActualTime, time) && isInSchedule(0, j2, closestActualTime, time))) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldExitForAlarm(long j) {
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        return scheduleInfo != null && scheduleInfo.exitAtAlarm && this.mSchedule.nextAlarm != 0 && j >= this.mSchedule.nextAlarm && isAlarmInSchedule(this.mSchedule.nextAlarm, j);
    }

    private boolean isInSchedule(int i, long j, long j2, long j3) {
        return this.mDays.contains(Integer.valueOf(((((getDayOfWeek(j) - 1) + (i % 7)) + 7) % 7) + 1)) && j >= addDays(j2, i) && j < addDays(j3, i);
    }

    private int getDayOfWeek(long j) {
        this.mCalendar.setTimeInMillis(j);
        return this.mCalendar.get(7);
    }

    private void updateDays() {
        this.mDays.clear();
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo == null || scheduleInfo.days == null) {
            return;
        }
        for (int i = 0; i < this.mSchedule.days.length; i++) {
            this.mDays.add(Integer.valueOf(this.mSchedule.days[i]));
        }
    }

    private long addDays(long j, int i) {
        this.mCalendar.setTimeInMillis(j);
        this.mCalendar.add(5, i);
        return this.mCalendar.getTimeInMillis();
    }

    public long getClosestActualTime(long j, int i, int i2) {
        long time = getTime(j, i, i2);
        if (this.mCalendar.getTimeZone().observesDaylightTime()) {
            this.mCalendar.setTimeInMillis(time);
            int i3 = this.mCalendar.get(11);
            int i4 = this.mCalendar.get(12);
            if (i3 == i + 1 && i4 == i2) {
                return getTime(j, i3, 0);
            }
        }
        return time;
    }
}
