package android.text.format;

import android.icu.util.Calendar;
import android.icu.util.ULocale;
import android.util.LruCache;
import com.android.internal.hidden_from_bootclasspath.com.android.libcore.Flags;
import java.text.FieldPosition;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public final class DateIntervalFormat {
    private static final LruCache<String, android.icu.text.DateIntervalFormat> CACHED_FORMATTERS = new LruCache<>(8);

    private DateIntervalFormat() {
    }

    public static String formatDateRange(long j, long j2, int i, String str) {
        if ((i & 8192) != 0) {
            str = Time.TIMEZONE_UTC;
        }
        return formatDateRange(ULocale.getDefault(), DateUtilsBridge.icuTimeZone(str != null ? TimeZone.getTimeZone(str) : TimeZone.getDefault()), j, j2, i);
    }

    public static String formatDateRange(ULocale uLocale, android.icu.util.TimeZone timeZone, long j, long j2, int i) {
        String string;
        Calendar calendarCreateIcuCalendar = DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j);
        Calendar calendarCreateIcuCalendar2 = j == j2 ? calendarCreateIcuCalendar : DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j2);
        if (isExactlyMidnight(calendarCreateIcuCalendar2)) {
            boolean z = (i & 1) == 1;
            boolean z2 = DateUtilsBridge.dayDistance(calendarCreateIcuCalendar, calendarCreateIcuCalendar2) == 1;
            if ((!z && j != j2) || (z2 && !DateUtilsBridge.isDisplayMidnightUsingSkeleton(calendarCreateIcuCalendar))) {
                calendarCreateIcuCalendar2.add(5, -1);
            }
        }
        String skeleton = DateUtilsBridge.toSkeleton(calendarCreateIcuCalendar, calendarCreateIcuCalendar2, i);
        synchronized (CACHED_FORMATTERS) {
            string = getFormatter(skeleton, uLocale, timeZone).format(calendarCreateIcuCalendar, calendarCreateIcuCalendar2, new StringBuffer(), new FieldPosition(0)).toString();
        }
        return string;
    }

    private static android.icu.text.DateIntervalFormat getFormatter(String str, ULocale uLocale, android.icu.util.TimeZone timeZone) {
        String str2 = str + "\t" + uLocale + "\t" + timeZone;
        LruCache<String, android.icu.text.DateIntervalFormat> lruCache = CACHED_FORMATTERS;
        android.icu.text.DateIntervalFormat dateIntervalFormat = lruCache.get(str2);
        if (dateIntervalFormat != null) {
            return dateIntervalFormat;
        }
        android.icu.text.DateIntervalFormat dateIntervalFormat2 = android.icu.text.DateIntervalFormat.getInstance(str, uLocale);
        dateIntervalFormat2.setTimeZone(timeZone);
        lruCache.put(str2, dateIntervalFormat2);
        return dateIntervalFormat2;
    }

    private static boolean isExactlyMidnight(Calendar calendar) {
        return calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0 && calendar.get(14) == 0;
    }

    public static boolean isLibcoreVFlagEnabled() {
        return Flags.vApis();
    }
}
