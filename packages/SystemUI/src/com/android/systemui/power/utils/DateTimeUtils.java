package com.android.systemui.power.utils;

import android.content.Context;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static String getFormattedTime(Context context, long j) {
        int i;
        int i2;
        int i3 = 0;
        if (j >= 3600) {
            i = (int) (j / 3600);
            j -= i * 3600;
        } else {
            i = 0;
        }
        if (j >= 60) {
            i2 = (int) (j / 60);
            j -= i2 * 60;
        } else {
            i2 = 0;
        }
        int i4 = (int) j;
        if (i != 0 || i2 < 2 || i4 < 30 || (i2 = i2 + 1) != 60) {
            i3 = i2;
        } else {
            i = 1;
        }
        return (i <= 0 || i3 <= 0) ? i > 0 ? context.getString(R.string.battery_notification_charging_text_h, Integer.valueOf(i)) : i3 > 0 ? context.getResources().getConfiguration().locale.getLanguage().equals("el") ? i3 == 1 ? context.getString(R.string.battery_notification_charging_text_m, Integer.valueOf(i3)) : context.getString(R.string.durationMinutes, Integer.valueOf(i3)) : context.getString(R.string.battery_notification_charging_text_m, Integer.valueOf(i3)) : "" : context.getString(R.string.battery_notification_charging_text_h_m, Integer.valueOf(i), Integer.valueOf(i3));
    }
}
