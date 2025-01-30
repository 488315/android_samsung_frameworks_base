package com.android.settingslib.utils;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.SpannableStringBuilder;
import android.text.style.TtsSpan;
import com.android.systemui.R;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PowerUtil {
    public static final long FIFTEEN_MINUTES_MILLIS;
    public static final long ONE_DAY_MILLIS;
    public static final long ONE_HOUR_MILLIS;

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        timeUnit.toMillis(7L);
        FIFTEEN_MINUTES_MILLIS = timeUnit.toMillis(15L);
        TimeUnit timeUnit2 = TimeUnit.DAYS;
        ONE_DAY_MILLIS = timeUnit2.toMillis(1L);
        timeUnit2.toMillis(2L);
        ONE_HOUR_MILLIS = TimeUnit.HOURS.toMillis(1L);
        timeUnit.toMillis(1L);
    }

    public static String getBatteryRemainingShortStringFormatted(Context context, long j) {
        int i;
        int i2;
        if (j <= 0) {
            return null;
        }
        if (j <= ONE_DAY_MILLIS) {
            long abs = Math.abs(System.currentTimeMillis() + j);
            long abs2 = Math.abs(FIFTEEN_MINUTES_MILLIS);
            long j2 = abs % abs2;
            long j3 = abs - j2;
            if (j2 >= abs2 / 2) {
                j3 += abs2;
            }
            return context.getString(R.string.power_discharge_by_only_short, DateFormat.getInstanceForSkeleton(android.text.format.DateFormat.getTimeFormatString(context)).format(Date.from(Instant.ofEpochMilli(j3))));
        }
        long abs3 = Math.abs(j);
        long abs4 = Math.abs(ONE_HOUR_MILLIS);
        long j4 = abs3 % abs4;
        long j5 = abs3 - j4;
        if (j4 >= abs4 / 2) {
            j5 += abs4;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int floor = ((int) Math.floor(j5 / 1000.0d)) + 30;
        if (floor >= 86400) {
            i = floor / 86400;
            floor -= 86400 * i;
        } else {
            i = 0;
        }
        if (floor >= 3600) {
            i2 = floor / 3600;
            floor -= i2 * 3600;
        } else {
            i2 = 0;
        }
        int i3 = floor >= 60 ? floor / 60 : 0;
        ArrayList arrayList = new ArrayList(4);
        if (i > 0) {
            arrayList.add(new Measure(Integer.valueOf(i), MeasureUnit.DAY));
        }
        if (i2 > 0) {
            arrayList.add(new Measure(Integer.valueOf(i2), MeasureUnit.HOUR));
        }
        if (i3 > 0) {
            arrayList.add(new Measure(Integer.valueOf(i3), MeasureUnit.MINUTE));
        }
        if (arrayList.size() == 0) {
            arrayList.add(new Measure(0, MeasureUnit.MINUTE));
        }
        Measure[] measureArr = (Measure[]) arrayList.toArray(new Measure[arrayList.size()]);
        spannableStringBuilder.append((CharSequence) MeasureFormat.getInstance(context.getResources().getConfiguration().locale, MeasureFormat.FormatWidth.SHORT).formatMeasures(measureArr));
        if (measureArr.length == 1 && MeasureUnit.MINUTE.equals(measureArr[0].getUnit())) {
            spannableStringBuilder.setSpan(new TtsSpan.MeasureBuilder().setNumber(i3).setUnit("minute").build(), 0, spannableStringBuilder.length(), 33);
        }
        return context.getString(R.string.power_remaining_duration_only_short, spannableStringBuilder);
    }
}
