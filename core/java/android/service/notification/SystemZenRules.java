package android.service.notification;

import android.app.Flags;
import android.content.Context;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.internal.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class SystemZenRules {
    public static final String PACKAGE_ANDROID = "android";
    private static final String TAG = "SystemZenRules";

    public static void maybeUpgradeRules(Context context, ZenModeConfig config) {
        for (ZenModeConfig.ZenRule rule : config.automaticRules.values()) {
            if (isSystemOwnedRule(rule)) {
                if (rule.type == -1) {
                    upgradeSystemProviderRule(context, rule);
                }
                if (Flags.modesUi()) {
                    rule.allowManualInvocation = true;
                }
            }
        }
    }

    public static boolean isSystemOwnedRule(ZenModeConfig.ZenRule rule) {
        return "android".equals(rule.pkg);
    }

    private static void upgradeSystemProviderRule(Context context, ZenModeConfig.ZenRule rule) {
        ZenModeConfig.ScheduleInfo scheduleInfo = ZenModeConfig.tryParseScheduleConditionId(rule.conditionId);
        if (scheduleInfo != null) {
            rule.type = 1;
            rule.triggerDescription = getTriggerDescriptionForScheduleTime(context, scheduleInfo);
            return;
        }
        ZenModeConfig.EventInfo eventInfo = ZenModeConfig.tryParseEventConditionId(rule.conditionId);
        if (eventInfo != null) {
            rule.type = 2;
            rule.triggerDescription = getTriggerDescriptionForScheduleEvent(context, eventInfo);
        } else {
            Log.wtf(TAG, "Couldn't determine type of system-owned ZenRule " + rule);
        }
    }

    public static boolean updateTriggerDescription(Context context, ZenModeConfig.ZenRule rule) {
        ZenModeConfig.ScheduleInfo scheduleInfo = ZenModeConfig.tryParseScheduleConditionId(rule.conditionId);
        if (scheduleInfo != null) {
            return updateTriggerDescription(rule, getTriggerDescriptionForScheduleTime(context, scheduleInfo));
        }
        ZenModeConfig.EventInfo eventInfo = ZenModeConfig.tryParseEventConditionId(rule.conditionId);
        if (eventInfo != null) {
            return updateTriggerDescription(rule, getTriggerDescriptionForScheduleEvent(context, eventInfo));
        }
        Log.wtf(TAG, "Couldn't determine type of system-owned ZenRule " + rule);
        return false;
    }

    private static boolean updateTriggerDescription(ZenModeConfig.ZenRule rule, String triggerDescription) {
        if (!Objects.equals(rule.triggerDescription, triggerDescription)) {
            rule.triggerDescription = triggerDescription;
            return true;
        }
        return false;
    }

    public static String getTriggerDescriptionForScheduleTime(Context context, ZenModeConfig.ScheduleInfo schedule) {
        StringBuilder sb = new StringBuilder();
        String daysSummary = getShortDaysSummary(context, schedule);
        if (daysSummary == null) {
            return null;
        }
        sb.append(daysSummary);
        sb.append(context.getString(R.string.zen_mode_trigger_summary_divider_text));
        sb.append(context.getString(R.string.zen_mode_trigger_summary_range_symbol_combination, timeString(context, schedule.startHour, schedule.startMinute), timeString(context, schedule.endHour, schedule.endMinute)));
        return sb.toString();
    }

    private static String getShortDaysSummary(Context context, ZenModeConfig.ScheduleInfo schedule) {
        int[] days = schedule.days;
        if (days != null && days.length > 0) {
            StringBuilder sb = new StringBuilder();
            Calendar cStart = Calendar.getInstance(getLocale(context));
            Calendar cEnd = Calendar.getInstance(getLocale(context));
            int[] daysOfWeek = getDaysOfWeekForLocale(cStart);
            int startDay = Integer.MIN_VALUE;
            int lastSeenDay = Integer.MIN_VALUE;
            int i = 0;
            while (i < daysOfWeek.length) {
                int day = daysOfWeek[i];
                boolean output = i == lastSeenDay + 1;
                int j = 0;
                while (true) {
                    if (j >= days.length) {
                        break;
                    }
                    if (day != days[j]) {
                        j++;
                    } else {
                        if (i == lastSeenDay + 1) {
                            lastSeenDay = i;
                            output = false;
                        } else {
                            startDay = i;
                            lastSeenDay = i;
                        }
                        if (i == daysOfWeek.length - 1) {
                            output = true;
                        }
                    }
                }
                if (output) {
                    if (sb.length() > 0) {
                        sb.append(context.getString(R.string.zen_mode_trigger_summary_divider_text));
                    }
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", getLocale(context));
                    if (startDay == lastSeenDay) {
                        cStart.set(7, daysOfWeek[startDay]);
                        sb.append(dayFormat.format(cStart.getTime()));
                    } else {
                        cStart.set(7, daysOfWeek[startDay]);
                        cEnd.set(7, daysOfWeek[lastSeenDay]);
                        sb.append(context.getString(R.string.zen_mode_trigger_summary_range_symbol_combination, dayFormat.format(cStart.getTime()), dayFormat.format(cEnd.getTime())));
                    }
                }
                i++;
            }
            int i2 = sb.length();
            if (i2 > 0) {
                return sb.toString();
            }
            return null;
        }
        return null;
    }

    private static String timeString(Context context, int hour, int minute) {
        Calendar c = Calendar.getInstance(getLocale(context));
        c.set(11, hour);
        c.set(12, minute);
        return DateFormat.getTimeFormat(context).format(c.getTime());
    }

    private static int[] getDaysOfWeekForLocale(Calendar c) {
        int[] daysOfWeek = new int[7];
        int currentDay = c.getFirstDayOfWeek();
        for (int i = 0; i < daysOfWeek.length; i++) {
            if (currentDay > 7) {
                currentDay = 1;
            }
            daysOfWeek[i] = currentDay;
            currentDay++;
        }
        return daysOfWeek;
    }

    private static Locale getLocale(Context context) {
        return context.getResources().getConfiguration().getLocales().get(0);
    }

    public static String getTriggerDescriptionForScheduleEvent(Context context, ZenModeConfig.EventInfo event) {
        if (event.calName != null) {
            return event.calName;
        }
        return context.getResources().getString(R.string.zen_mode_trigger_event_calendar_any);
    }

    private SystemZenRules() {
    }
}
