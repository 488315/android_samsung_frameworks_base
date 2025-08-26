package com.android.settingslib.utils;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.SpannableStringBuilder;
import android.text.style.TtsSpan;
import com.android.systemui.R;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class PowerUtil {
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
            long jAbs = Math.abs(System.currentTimeMillis() + j);
            long jAbs2 = Math.abs(FIFTEEN_MINUTES_MILLIS);
            long j2 = jAbs % jAbs2;
            return context.getString(R.string.power_discharge_by_only_short, DateFormat.getInstanceForSkeleton(android.text.format.DateFormat.getTimeFormatString(context)).format(Date.from(Instant.ofEpochMilli(j2 < jAbs2 / 2 ? jAbs - j2 : (jAbs - j2) + jAbs2))));
        }
        long jAbs3 = Math.abs(j);
        long jAbs4 = Math.abs(ONE_HOUR_MILLIS);
        long j3 = jAbs3 % jAbs4;
        long j4 = j3 < jAbs4 / 2 ? jAbs3 - j3 : (jAbs3 - j3) + jAbs4;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int iFloor = ((int) Math.floor(j4 / 1000.0d)) + 30;
        if (iFloor >= 86400) {
            i = iFloor / 86400;
            iFloor -= 86400 * i;
        } else {
            i = 0;
        }
        if (iFloor >= 3600) {
            i2 = iFloor / PluginLock.VERSION;
            iFloor -= i2 * PluginLock.VERSION;
        } else {
            i2 = 0;
        }
        int i3 = iFloor >= 60 ? iFloor / 60 : 0;
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
