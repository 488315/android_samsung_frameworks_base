package android.text.format;

import android.app.blob.XmlTags;
import android.hardware.gnss.GnssSignalType;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;

/* loaded from: classes4.dex */
public final class DateUtilsBridge {
    public static TimeZone icuTimeZone(java.util.TimeZone timeZone) {
        TimeZone timeZone2 = TimeZone.getTimeZone(timeZone.getID());
        timeZone2.freeze();
        return timeZone2;
    }

    public static Calendar createIcuCalendar(TimeZone timeZone, ULocale uLocale, long j) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, uLocale);
        gregorianCalendar.setTimeInMillis(j);
        return gregorianCalendar;
    }

    public static String toSkeleton(Calendar calendar, int i) {
        return toSkeleton(calendar, calendar, i);
    }

    public static String toSkeleton(Calendar calendar, Calendar calendar2, int i) {
        String str;
        String str2;
        String str3;
        if ((524288 & i) != 0) {
            i |= 114688;
        }
        if ((131072 & i) != 0) {
            str = GnssSignalType.CODE_TYPE_M;
        } else if ((65536 & i) == 0) {
            str = "MMMM";
        } else {
            str = "MMM";
        }
        if ((32768 & i) == 0) {
            str2 = "EEEE";
        } else {
            str2 = "EEE";
        }
        int i2 = i & 128;
        if (i2 != 0) {
            str3 = "H";
        } else if ((i & 64) == 0) {
            str3 = "j";
        } else {
            str3 = "h";
        }
        if ((i & 16384) == 0 || i2 != 0) {
            str3 = str3.concat("m");
        } else if (!onTheHour(calendar) || !onTheHour(calendar2)) {
            str3 = str3.concat("m");
        }
        if (fallOnDifferentDates(calendar, calendar2)) {
            i |= 16;
        }
        if (fallInSameMonth(calendar, calendar2) && (i & 32) != 0) {
            i &= -4;
        }
        if ((i & 19) == 0) {
            i |= 16;
        }
        if ((i & 16) != 0 && (i & 4) == 0 && (i & 8) == 0 && (!fallInSameYear(calendar, calendar2) || !isThisYear(calendar))) {
            i |= 4;
        }
        StringBuilder sb = new StringBuilder();
        if ((i & 48) != 0) {
            if ((i & 4) != 0) {
                sb.append("y");
            }
            sb.append(str);
            if ((i & 32) == 0) {
                sb.append(XmlTags.ATTR_DESCRIPTION);
            }
        }
        if ((i & 2) != 0) {
            sb.append(str2);
        }
        if ((i & 1) != 0) {
            sb.append(str3);
        }
        return sb.toString();
    }

    public static int dayDistance(Calendar calendar, Calendar calendar2) {
        return calendar2.get(20) - calendar.get(20);
    }

    public static boolean isDisplayMidnightUsingSkeleton(Calendar calendar) {
        return calendar.get(11) == 0 && calendar.get(12) == 0;
    }

    private static boolean onTheHour(Calendar calendar) {
        return calendar.get(12) == 0 && calendar.get(13) == 0;
    }

    private static boolean fallOnDifferentDates(Calendar calendar, Calendar calendar2) {
        return (calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5)) ? false : true;
    }

    private static boolean fallInSameMonth(Calendar calendar, Calendar calendar2) {
        return calendar.get(2) == calendar2.get(2);
    }

    private static boolean fallInSameYear(Calendar calendar, Calendar calendar2) {
        return calendar.get(1) == calendar2.get(1);
    }

    private static boolean isThisYear(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        return calendar.get(1) == calendar2.get(1);
    }
}
