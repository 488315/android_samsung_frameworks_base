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

    public static void maybeUpgradeRules(Context context, ZenModeConfig zenModeConfig) {
        for (ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
            if (isSystemOwnedRule(zenRule)) {
                if (zenRule.type == -1) {
                    upgradeSystemProviderRule(context, zenRule);
                }
                if (Flags.modesUi()) {
                    zenRule.allowManualInvocation = true;
                }
            }
        }
    }

    public static boolean isSystemOwnedRule(ZenModeConfig.ZenRule zenRule) {
        return "android".equals(zenRule.pkg);
    }

    private static void upgradeSystemProviderRule(Context context, ZenModeConfig.ZenRule zenRule) {
        ZenModeConfig.ScheduleInfo tryParseScheduleConditionId = ZenModeConfig.tryParseScheduleConditionId(zenRule.conditionId);
        if (tryParseScheduleConditionId != null) {
            zenRule.type = 1;
            zenRule.triggerDescription = getTriggerDescriptionForScheduleTime(context, tryParseScheduleConditionId);
            return;
        }
        ZenModeConfig.EventInfo tryParseEventConditionId = ZenModeConfig.tryParseEventConditionId(zenRule.conditionId);
        if (tryParseEventConditionId != null) {
            zenRule.type = 2;
            zenRule.triggerDescription = getTriggerDescriptionForScheduleEvent(context, tryParseEventConditionId);
        } else {
            Log.wtf(TAG, "Couldn't determine type of system-owned ZenRule " + zenRule);
        }
    }

    public static boolean updateTriggerDescription(Context context, ZenModeConfig.ZenRule zenRule) {
        ZenModeConfig.ScheduleInfo tryParseScheduleConditionId = ZenModeConfig.tryParseScheduleConditionId(zenRule.conditionId);
        if (tryParseScheduleConditionId != null) {
            return updateTriggerDescription(zenRule, getTriggerDescriptionForScheduleTime(context, tryParseScheduleConditionId));
        }
        ZenModeConfig.EventInfo tryParseEventConditionId = ZenModeConfig.tryParseEventConditionId(zenRule.conditionId);
        if (tryParseEventConditionId != null) {
            return updateTriggerDescription(zenRule, getTriggerDescriptionForScheduleEvent(context, tryParseEventConditionId));
        }
        Log.wtf(TAG, "Couldn't determine type of system-owned ZenRule " + zenRule);
        return false;
    }

    private static boolean updateTriggerDescription(ZenModeConfig.ZenRule zenRule, String str) {
        if (Objects.equals(zenRule.triggerDescription, str)) {
            return false;
        }
        zenRule.triggerDescription = str;
        return true;
    }

    public static String getTriggerDescriptionForScheduleTime(Context context, ZenModeConfig.ScheduleInfo scheduleInfo) {
        String daysOfWeekShort = getDaysOfWeekShort(context, scheduleInfo);
        if (daysOfWeekShort == null) {
            return null;
        }
        return context.getString(R.string.zen_mode_trigger_summary_combined, daysOfWeekShort, getTimeSummary(context, scheduleInfo));
    }

    public static String getDaysOfWeekShort(Context context, ZenModeConfig.ScheduleInfo scheduleInfo) {
        return getDaysSummary(context, R.string.zen_mode_trigger_summary_range_symbol_combination, new SimpleDateFormat("EEE", getLocale(context)), scheduleInfo);
    }

    public static String getDaysOfWeekFull(Context context, ZenModeConfig.ScheduleInfo scheduleInfo) {
        return getDaysSummary(context, R.string.zen_mode_trigger_summary_range_words, new SimpleDateFormat("EEEE", getLocale(context)), scheduleInfo);
    }

    private static String getDaysSummary(Context context, int i, SimpleDateFormat simpleDateFormat, ZenModeConfig.ScheduleInfo scheduleInfo) {
        int[] iArr = scheduleInfo.days;
        if (iArr == null || iArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance(getLocale(context));
        Calendar calendar2 = Calendar.getInstance(getLocale(context));
        int[] daysOfWeekForLocale = getDaysOfWeekForLocale(calendar);
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MIN_VALUE;
        int i4 = 0;
        while (i4 < daysOfWeekForLocale.length) {
            int i5 = daysOfWeekForLocale[i4];
            int i6 = i2 + 1;
            boolean z = true;
            boolean z2 = i4 == i6;
            int i7 = 0;
            while (true) {
                if (i7 >= iArr.length) {
                    break;
                }
                if (i5 == iArr[i7]) {
                    if (i4 == i6) {
                        z2 = false;
                    } else {
                        i3 = i4;
                    }
                    if (i4 == daysOfWeekForLocale.length - 1) {
                        i2 = i4;
                    } else {
                        i2 = i4;
                    }
                } else {
                    i7++;
                }
            }
            z = z2;
            if (z) {
                if (sb.length() > 0) {
                    sb.append(context.getString(R.string.zen_mode_trigger_summary_divider_text));
                }
                if (i3 == i2) {
                    calendar.set(7, daysOfWeekForLocale[i3]);
                    sb.append(simpleDateFormat.format(calendar.getTime()));
                } else {
                    calendar.set(7, daysOfWeekForLocale[i3]);
                    calendar2.set(7, daysOfWeekForLocale[i2]);
                    sb.append(context.getString(i, simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(calendar2.getTime())));
                    i4++;
                }
            }
            i4++;
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    public static String getTimeSummary(Context context, ZenModeConfig.ScheduleInfo scheduleInfo) {
        return context.getString(R.string.zen_mode_trigger_summary_range_symbol_combination, timeString(context, scheduleInfo.startHour, scheduleInfo.startMinute), timeString(context, scheduleInfo.endHour, scheduleInfo.endMinute));
    }

    private static String timeString(Context context, int i, int i2) {
        Calendar calendar = Calendar.getInstance(getLocale(context));
        calendar.set(11, i);
        calendar.set(12, i2);
        return DateFormat.getTimeFormat(context).format(calendar.getTime());
    }

    private static int[] getDaysOfWeekForLocale(Calendar calendar) {
        int[] iArr = new int[7];
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        for (int i = 0; i < 7; i++) {
            if (firstDayOfWeek > 7) {
                firstDayOfWeek = 1;
            }
            iArr[i] = firstDayOfWeek;
            firstDayOfWeek++;
        }
        return iArr;
    }

    private static Locale getLocale(Context context) {
        return context.getResources().getConfiguration().getLocales().get(0);
    }

    public static String getTriggerDescriptionForScheduleEvent(Context context, ZenModeConfig.EventInfo eventInfo) {
        if (eventInfo.calName != null) {
            return eventInfo.calName;
        }
        return context.getResources().getString(R.string.zen_mode_trigger_event_calendar_any);
    }

    private SystemZenRules() {
    }
}
