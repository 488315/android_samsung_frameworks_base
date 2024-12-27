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

    public void setSchedule(ZenModeConfig.ScheduleInfo schedule) {
        if (Objects.equals(this.mSchedule, schedule)) {
            return;
        }
        this.mSchedule = schedule;
        updateDays();
    }

    public void maybeSetNextAlarm(long now, long nextAlarm) {
        if (this.mSchedule == null || !this.mSchedule.exitAtAlarm) {
            return;
        }
        if (nextAlarm == 0) {
            this.mSchedule.nextAlarm = 0L;
            return;
        }
        if (nextAlarm > now) {
            this.mSchedule.nextAlarm = nextAlarm;
        } else if (this.mSchedule.nextAlarm < now) {
            if (DEBUG) {
                Log.d(TAG, "All alarms are in the past " + this.mSchedule.nextAlarm);
            }
            this.mSchedule.nextAlarm = 0L;
        }
    }

    public void setTimeZone(TimeZone tz) {
        this.mCalendar.setTimeZone(tz);
    }

    public long getNextChangeTime(long now) {
        if (this.mSchedule == null) {
            return 0L;
        }
        long nextStart = getNextTime(now, this.mSchedule.startHour, this.mSchedule.startMinute, true);
        long nextEnd = getNextTime(now, this.mSchedule.endHour, this.mSchedule.endMinute, false);
        long nextScheduleTime = Math.min(nextStart, nextEnd);
        return nextScheduleTime;
    }

    private long getNextTime(long now, int hr, int min, boolean adjust) {
        long time = adjust ? getClosestActualTime(now, hr, min) : getTime(now, hr, min);
        if (time <= now) {
            long tomorrow = addDays(time, 1);
            return adjust ? getClosestActualTime(tomorrow, hr, min) : getTime(tomorrow, hr, min);
        }
        return time;
    }

    private long getTime(long millis, int hour, int min) {
        this.mCalendar.setTimeInMillis(millis);
        this.mCalendar.set(11, hour);
        this.mCalendar.set(12, min);
        this.mCalendar.set(13, 0);
        this.mCalendar.set(14, 0);
        return this.mCalendar.getTimeInMillis();
    }

    public boolean isInSchedule(long time) {
        long end;
        if (this.mSchedule == null || this.mDays.size() == 0) {
            return false;
        }
        long start = getClosestActualTime(time, this.mSchedule.startHour, this.mSchedule.startMinute);
        long end2 = getTime(time, this.mSchedule.endHour, this.mSchedule.endMinute);
        if (end2 > start) {
            end = end2;
        } else {
            end = addDays(end2, 1);
        }
        return isInSchedule(-1, time, start, end) || isInSchedule(0, time, start, end);
    }

    public boolean isAlarmInSchedule(long alarm, long now) {
        long end;
        if (this.mSchedule == null || this.mDays.size() == 0) {
            return false;
        }
        long start = getClosestActualTime(alarm, this.mSchedule.startHour, this.mSchedule.startMinute);
        long end2 = getTime(alarm, this.mSchedule.endHour, this.mSchedule.endMinute);
        if (end2 > start) {
            end = end2;
        } else {
            end = addDays(end2, 1);
        }
        return (isInSchedule(-1, alarm, start, end) && isInSchedule(-1, now, start, end)) || (isInSchedule(0, alarm, start, end) && isInSchedule(0, now, start, end));
    }

    public boolean shouldExitForAlarm(long time) {
        return this.mSchedule != null && this.mSchedule.exitAtAlarm && this.mSchedule.nextAlarm != 0 && time >= this.mSchedule.nextAlarm && isAlarmInSchedule(this.mSchedule.nextAlarm, time);
    }

    private boolean isInSchedule(int daysOffset, long time, long start, long end) {
        int day = ((((getDayOfWeek(time) - 1) + (daysOffset % 7)) + 7) % 7) + 1;
        return this.mDays.contains(Integer.valueOf(day)) && time >= addDays(start, daysOffset) && time < addDays(end, daysOffset);
    }

    private int getDayOfWeek(long time) {
        this.mCalendar.setTimeInMillis(time);
        return this.mCalendar.get(7);
    }

    private void updateDays() {
        this.mDays.clear();
        if (this.mSchedule != null && this.mSchedule.days != null) {
            for (int i = 0; i < this.mSchedule.days.length; i++) {
                this.mDays.add(Integer.valueOf(this.mSchedule.days[i]));
            }
        }
    }

    private long addDays(long time, int days) {
        this.mCalendar.setTimeInMillis(time);
        this.mCalendar.add(5, days);
        return this.mCalendar.getTimeInMillis();
    }

    public long getClosestActualTime(long refTime, int hour, int min) {
        long resTime = getTime(refTime, hour, min);
        if (!this.mCalendar.getTimeZone().observesDaylightTime()) {
            return resTime;
        }
        this.mCalendar.setTimeInMillis(resTime);
        int resHr = this.mCalendar.get(11);
        int resMin = this.mCalendar.get(12);
        if (resHr == hour + 1 && resMin == min) {
            return getTime(refTime, resHr, 0);
        }
        return resTime;
    }
}
