package android.text.format;

import android.icu.text.DateTimePatternGenerator;
import android.icu.text.DisplayContext;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.ULocale;
import android.util.LruCache;

class DateTimeFormat {
    private static final FormatterCache CACHED_FORMATTERS = new FormatterCache();

    static class FormatterCache extends LruCache<String, android.icu.text.DateFormat> {
        FormatterCache() {
            super(8);
        }
    }

    private DateTimeFormat() {}

    public static String format(
            ULocale icuLocale, Calendar time, int flags, DisplayContext displayContext) {
        String format;
        String skeleton = DateUtilsBridge.toSkeleton(time, flags);
        String key = skeleton + "\t" + icuLocale + "\t" + time.getTimeZone();
        synchronized (CACHED_FORMATTERS) {
            android.icu.text.DateFormat formatter = CACHED_FORMATTERS.get(key);
            if (formatter == null) {
                DateTimePatternGenerator generator =
                        DateTimePatternGenerator.getInstance(icuLocale);
                formatter = new SimpleDateFormat(generator.getBestPattern(skeleton), icuLocale);
                CACHED_FORMATTERS.put(key, formatter);
            }
            formatter.setContext(displayContext);
            format = formatter.format(time);
        }
        return format;
    }
}
