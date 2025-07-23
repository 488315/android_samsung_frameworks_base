package android.text.format;

import android.icu.text.DisplayContext;
import android.icu.text.RelativeDateTimeFormatter;
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getRelativeTimeSpanString(android.icu.util.ULocale r20, android.icu.util.TimeZone r21, long r22, long r24, long r26, int r28, android.icu.text.DisplayContext r29) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.format.RelativeDateTimeFormatter.getRelativeTimeSpanString(android.icu.util.ULocale, android.icu.util.TimeZone, long, long, long, int, android.icu.text.DisplayContext):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0047, code lost:
    
        if (r20 < 86400000) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getRelativeDateTimeString(java.util.Locale r14, java.util.TimeZone r15, long r16, long r18, long r20, long r22, int r24) {
        /*
            r2 = r16
            r4 = r18
            if (r14 == 0) goto L8e
            if (r15 == 0) goto L85
            android.icu.util.ULocale r0 = android.icu.util.ULocale.forLocale(r14)
            android.icu.util.TimeZone r1 = android.text.format.DateUtilsBridge.icuTimeZone(r15)
            long r6 = r4 - r2
            long r6 = java.lang.Math.abs(r6)
            r8 = 604800000(0x240c8400, double:2.988109026E-315)
            int r14 = (r22 > r8 ? 1 : (r22 == r8 ? 0 : -1))
            if (r14 <= 0) goto L1e
            goto L20
        L1e:
            r8 = r22
        L20:
            r14 = 786432(0xc0000, float:1.102026E-39)
            r14 = r24 & r14
            if (r14 == 0) goto L29
            android.icu.text.RelativeDateTimeFormatter$Style r14 = android.icu.text.RelativeDateTimeFormatter.Style.SHORT
            goto L2b
        L29:
            android.icu.text.RelativeDateTimeFormatter$Style r14 = android.icu.text.RelativeDateTimeFormatter.Style.LONG
        L2b:
            android.icu.util.Calendar r10 = android.text.format.DateUtilsBridge.createIcuCalendar(r1, r0, r2)
            android.icu.util.Calendar r11 = android.text.format.DateUtilsBridge.createIcuCalendar(r1, r0, r4)
            int r12 = android.text.format.DateUtilsBridge.dayDistance(r10, r11)
            int r12 = java.lang.Math.abs(r12)
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            r13 = 1
            if (r6 >= 0) goto L55
            if (r12 <= 0) goto L4a
            r6 = 86400000(0x5265c00, double:4.2687272E-316)
            int r8 = (r20 > r6 ? 1 : (r20 == r6 ? 0 : -1))
            if (r8 >= 0) goto L4a
            goto L4c
        L4a:
            r6 = r20
        L4c:
            android.icu.text.DisplayContext r9 = android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE
            r8 = r24
            java.lang.String r1 = getRelativeTimeSpanString(r0, r1, r2, r4, r6, r8, r9)
            goto L6c
        L55:
            int r1 = r10.get(r13)
            int r2 = r11.get(r13)
            if (r1 == r2) goto L63
            r1 = 131092(0x20014, float:1.83699E-40)
            goto L66
        L63:
            r1 = 65560(0x10018, float:9.1869E-41)
        L66:
            android.icu.text.DisplayContext r2 = android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE
            java.lang.String r1 = android.text.format.DateTimeFormat.format(r0, r10, r1, r2)
        L6c:
            android.icu.text.DisplayContext r2 = android.icu.text.DisplayContext.CAPITALIZATION_NONE
            java.lang.String r2 = android.text.format.DateTimeFormat.format(r0, r10, r13, r2)
            android.icu.text.DisplayContext r3 = android.icu.text.DisplayContext.CAPITALIZATION_NONE
            android.text.format.RelativeDateTimeFormatter$FormatterCache r4 = android.text.format.RelativeDateTimeFormatter.CACHED_FORMATTERS
            monitor-enter(r4)
            android.icu.text.RelativeDateTimeFormatter r14 = getFormatter(r0, r14, r3)     // Catch: java.lang.Throwable -> L81
            java.lang.String r14 = r14.combineDateAndTime(r1, r2)     // Catch: java.lang.Throwable -> L81
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L81
            return r14
        L81:
            r0 = move-exception
            r14 = r0
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L81
            throw r14
        L85:
            java.lang.NullPointerException r14 = new java.lang.NullPointerException
            java.lang.String r0 = "tz == null"
            r14.<init>(r0)
            throw r14
        L8e:
            java.lang.NullPointerException r14 = new java.lang.NullPointerException
            java.lang.String r0 = "locale == null"
            r14.<init>(r0)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.format.RelativeDateTimeFormatter.getRelativeDateTimeString(java.util.Locale, java.util.TimeZone, long, long, long, long, int):java.lang.String");
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
