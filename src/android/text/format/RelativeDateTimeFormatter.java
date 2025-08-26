package android.text.format;

import android.icu.text.DisplayContext;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.util.Calendar;
import android.icu.util.ULocale;
import android.util.LruCache;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public final class RelativeDateTimeFormatter {
    private static final FormatterCache CACHED_FORMATTERS = new FormatterCache();
    public static final long DAY_IN_MILLIS = 86400000;
    private static final int DAY_IN_MS = 86400000;
    private static final int EPOCH_JULIAN_DAY = 2440588;
    public static final long HOUR_IN_MILLIS = 3600000;
    public static final long MINUTE_IN_MILLIS = 60000;
    public static final long SECOND_IN_MILLIS = 1000;
    public static final long WEEK_IN_MILLIS = 604800000;
    public static final long YEAR_IN_MILLIS = 31449600000L;

    static class FormatterCache extends LruCache<String, android.icu.text.RelativeDateTimeFormatter> {
        FormatterCache() {
            super(8);
        }
    }

    private RelativeDateTimeFormatter() {
    }

    public static String getRelativeTimeSpanString(Locale locale, TimeZone timeZone, long j, long j2, long j3, int i) {
        return getRelativeTimeSpanString(locale, timeZone, j, j2, j3, i, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
    }

    public static String getRelativeTimeSpanString(Locale locale, TimeZone timeZone, long j, long j2, long j3, int i, DisplayContext displayContext) {
        if (locale == null) {
            throw new NullPointerException("locale == null");
        }
        if (timeZone == null) {
            throw new NullPointerException("tz == null");
        }
        return getRelativeTimeSpanString(ULocale.forLocale(locale), DateUtilsBridge.icuTimeZone(timeZone), j, j2, j3, i, displayContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String getRelativeTimeSpanString(ULocale uLocale, android.icu.util.TimeZone timeZone, long j, long j2, long j3, int i, DisplayContext displayContext) {
        RelativeDateTimeFormatter.Style style;
        RelativeDateTimeFormatter.Direction direction;
        int i2;
        int iAbs;
        RelativeDateTimeFormatter.RelativeUnit relativeUnit;
        String str;
        long jAbs = Math.abs(j2 - j);
        boolean z = false;
        boolean z2 = j2 >= j;
        if ((i & 786432) != 0) {
            style = RelativeDateTimeFormatter.Style.SHORT;
        } else {
            style = RelativeDateTimeFormatter.Style.LONG;
        }
        if (z2) {
            direction = RelativeDateTimeFormatter.Direction.LAST;
        } else {
            direction = RelativeDateTimeFormatter.Direction.NEXT;
        }
        RelativeDateTimeFormatter.AbsoluteUnit absoluteUnit = null;
        if (jAbs < 60000 && j3 < 60000) {
            iAbs = (int) (jAbs / 1000);
            relativeUnit = RelativeDateTimeFormatter.RelativeUnit.SECONDS;
        } else if (jAbs < 3600000 && j3 < 3600000) {
            iAbs = (int) (jAbs / 60000);
            relativeUnit = RelativeDateTimeFormatter.RelativeUnit.MINUTES;
        } else if (jAbs < 86400000 && j3 < 86400000) {
            iAbs = (int) (jAbs / 3600000);
            relativeUnit = RelativeDateTimeFormatter.RelativeUnit.HOURS;
        } else if (jAbs < 604800000 && j3 < 604800000) {
            iAbs = Math.abs(dayDistance(timeZone, j, j2));
            relativeUnit = RelativeDateTimeFormatter.RelativeUnit.DAYS;
            if (iAbs == 2) {
                if (z2) {
                    synchronized (CACHED_FORMATTERS) {
                        str = getFormatter(uLocale, style, displayContext).format(RelativeDateTimeFormatter.Direction.LAST_2, RelativeDateTimeFormatter.AbsoluteUnit.DAY);
                    }
                } else {
                    synchronized (CACHED_FORMATTERS) {
                        str = getFormatter(uLocale, style, displayContext).format(RelativeDateTimeFormatter.Direction.NEXT_2, RelativeDateTimeFormatter.AbsoluteUnit.DAY);
                    }
                }
                if (str != null && !str.isEmpty()) {
                    return str;
                }
            } else {
                if (iAbs == 1) {
                    absoluteUnit = RelativeDateTimeFormatter.AbsoluteUnit.DAY;
                } else if (iAbs == 0) {
                    absoluteUnit = RelativeDateTimeFormatter.AbsoluteUnit.DAY;
                    direction = RelativeDateTimeFormatter.Direction.THIS;
                }
                RelativeDateTimeFormatter.AbsoluteUnit absoluteUnit2 = absoluteUnit;
                synchronized (CACHED_FORMATTERS) {
                    android.icu.text.RelativeDateTimeFormatter formatter = getFormatter(uLocale, style, displayContext);
                    if (z) {
                        return formatter.format(iAbs, direction, relativeUnit);
                    }
                    return formatter.format(direction, absoluteUnit2);
                }
            }
        } else if (j3 == 604800000) {
            iAbs = (int) (jAbs / 604800000);
            relativeUnit = RelativeDateTimeFormatter.RelativeUnit.WEEKS;
        } else {
            Calendar calendarCreateIcuCalendar = DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j);
            if ((i & 12) == 0) {
                i2 = calendarCreateIcuCalendar.get(1) != DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j2).get(1) ? i | 4 : i | 8;
            } else {
                i2 = i;
            }
            return DateTimeFormat.format(uLocale, calendarCreateIcuCalendar, i2, displayContext);
        }
        z = true;
        RelativeDateTimeFormatter.AbsoluteUnit absoluteUnit22 = absoluteUnit;
        synchronized (CACHED_FORMATTERS) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getRelativeDateTimeString(Locale locale, TimeZone timeZone, long j, long j2, long j3, long j4, int i) {
        RelativeDateTimeFormatter.Style style;
        String relativeTimeSpanString;
        String strCombineDateAndTime;
        if (locale == null) {
            throw new NullPointerException("locale == null");
        }
        if (timeZone == null) {
            throw new NullPointerException("tz == null");
        }
        ULocale uLocaleForLocale = ULocale.forLocale(locale);
        android.icu.util.TimeZone timeZoneIcuTimeZone = DateUtilsBridge.icuTimeZone(timeZone);
        long jAbs = Math.abs(j2 - j);
        long j5 = j4 <= 604800000 ? j4 : 604800000L;
        if ((i & 786432) != 0) {
            style = RelativeDateTimeFormatter.Style.SHORT;
        } else {
            style = RelativeDateTimeFormatter.Style.LONG;
        }
        Calendar calendarCreateIcuCalendar = DateUtilsBridge.createIcuCalendar(timeZoneIcuTimeZone, uLocaleForLocale, j);
        Calendar calendarCreateIcuCalendar2 = DateUtilsBridge.createIcuCalendar(timeZoneIcuTimeZone, uLocaleForLocale, j2);
        int iAbs = Math.abs(DateUtilsBridge.dayDistance(calendarCreateIcuCalendar, calendarCreateIcuCalendar2));
        if (jAbs >= j5) {
            relativeTimeSpanString = DateTimeFormat.format(uLocaleForLocale, calendarCreateIcuCalendar, calendarCreateIcuCalendar.get(1) != calendarCreateIcuCalendar2.get(1) ? 131092 : 65560, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        } else if (iAbs > 0) {
            long j6 = j3 >= 86400000 ? j3 : 86400000L;
            relativeTimeSpanString = getRelativeTimeSpanString(uLocaleForLocale, timeZoneIcuTimeZone, j, j2, j6, i, DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        }
        String str = DateTimeFormat.format(uLocaleForLocale, calendarCreateIcuCalendar, 1, DisplayContext.CAPITALIZATION_NONE);
        DisplayContext displayContext = DisplayContext.CAPITALIZATION_NONE;
        synchronized (CACHED_FORMATTERS) {
            strCombineDateAndTime = getFormatter(uLocaleForLocale, style, displayContext).combineDateAndTime(relativeTimeSpanString, str);
        }
        return strCombineDateAndTime;
    }

    private static android.icu.text.RelativeDateTimeFormatter getFormatter(ULocale uLocale, RelativeDateTimeFormatter.Style style, DisplayContext displayContext) {
        String str = uLocale + "\t" + style + "\t" + displayContext;
        FormatterCache formatterCache = CACHED_FORMATTERS;
        android.icu.text.RelativeDateTimeFormatter relativeDateTimeFormatter = formatterCache.get(str);
        if (relativeDateTimeFormatter != null) {
            return relativeDateTimeFormatter;
        }
        android.icu.text.RelativeDateTimeFormatter relativeDateTimeFormatter2 = android.icu.text.RelativeDateTimeFormatter.getInstance(uLocale, null, style, displayContext);
        formatterCache.put(str, relativeDateTimeFormatter2);
        return relativeDateTimeFormatter2;
    }

    private static int dayDistance(android.icu.util.TimeZone timeZone, long j, long j2) {
        return julianDay(timeZone, j2) - julianDay(timeZone, j);
    }

    private static int julianDay(android.icu.util.TimeZone timeZone, long j) {
        return ((int) ((j + timeZone.getOffset(j)) / 86400000)) + 2440588;
    }
}
