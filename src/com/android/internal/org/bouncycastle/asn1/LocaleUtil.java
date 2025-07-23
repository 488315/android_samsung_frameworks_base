package com.android.internal.org.bouncycastle.asn1;

import com.android.internal.org.bouncycastle.util.Longs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes5.dex */
public class LocaleUtil {
    private static final Map localeCache = new HashMap();
    public static Locale EN_Locale = forEN();

    private static Locale forEN() {
        if ("en".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            return Locale.getDefault();
        }
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (int i = 0; i != availableLocales.length; i++) {
            if ("en".equalsIgnoreCase(availableLocales[i].getLanguage())) {
                return availableLocales[i];
            }
        }
        return Locale.getDefault();
    }

    static Date epochAdjust(Date date) throws ParseException {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return date;
        }
        Map map = localeCache;
        synchronized (map) {
            Long l = (Long) map.get(locale);
            if (l == null) {
                l = longValueOf(new SimpleDateFormat("yyyyMMddHHmmssz").parse("19700101000000GMT+00:00").getTime());
                map.put(locale, l);
            }
            if (l.longValue() == 0) {
                return date;
            }
            return new Date(date.getTime() - l.longValue());
        }
    }

    private static Long longValueOf(long j) {
        return Longs.valueOf(j);
    }
}
