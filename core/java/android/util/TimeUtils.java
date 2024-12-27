package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.TimeZoneFinder;
import com.android.i18n.timezone.ZoneInfoDb;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    public static final long NANOS_PER_MS = 1000000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final SimpleDateFormat sLoggingFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sDumpDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final Instant MIN_USE_DATE_OF_TIMEZONE = Instant.ofEpochMilli(1546300800000L);
    private static final Object sFormatSync = new Object();
    private static char[] sFormatStr = new char[29];
    private static char[] sTmpFormatStr = new char[29];

    public static TimeZone getTimeZone(int offset, boolean dst, long when, String country) {
        android.icu.util.TimeZone icuTimeZone = getIcuTimeZone(offset, dst, when, country);
        if (icuTimeZone != null) {
            return TimeZone.getTimeZone(icuTimeZone.getID());
        }
        return null;
    }

    private static android.icu.util.TimeZone getIcuTimeZone(
            int offsetMillis, boolean isDst, long whenMillis, String countryIso) {
        CountryTimeZones.OffsetResult offsetResult;
        if (countryIso == null) {
            return null;
        }
        android.icu.util.TimeZone bias = android.icu.util.TimeZone.getDefault();
        CountryTimeZones countryTimeZones =
                TimeZoneFinder.getInstance().lookupCountryTimeZones(countryIso);
        if (countryTimeZones == null
                || (offsetResult =
                                countryTimeZones.lookupByOffsetWithBias(
                                        whenMillis, bias, offsetMillis, isDst))
                        == null) {
            return null;
        }
        return offsetResult.getTimeZone();
    }

    public static List<String> getTimeZoneIdsForCountryCode(String countryCode) {
        if (countryCode == null) {
            throw new NullPointerException("countryCode == null");
        }
        TimeZoneFinder timeZoneFinder = TimeZoneFinder.getInstance();
        CountryTimeZones countryTimeZones =
                timeZoneFinder.lookupCountryTimeZones(countryCode.toLowerCase());
        if (countryTimeZones == null) {
            return null;
        }
        List<String> timeZoneIds = new ArrayList<>();
        for (CountryTimeZones.TimeZoneMapping timeZoneMapping :
                countryTimeZones.getTimeZoneMappings()) {
            if (timeZoneMapping.isShownInPickerAt(MIN_USE_DATE_OF_TIMEZONE)) {
                timeZoneIds.add(timeZoneMapping.getTimeZoneId());
            }
        }
        return Collections.unmodifiableList(timeZoneIds);
    }

    public static String getTimeZoneDatabaseVersion() {
        return ZoneInfoDb.getInstance().getVersion();
    }

    private static int accumField(int amt, int suffix, boolean always, int zeropad) {
        if (amt > 999) {
            int num = 0;
            while (amt != 0) {
                num++;
                amt /= 10;
            }
            return num + suffix;
        }
        if (amt > 99 || (always && zeropad >= 3)) {
            return suffix + 3;
        }
        if (amt > 9 || (always && zeropad >= 2)) {
            return suffix + 2;
        }
        if (always || amt > 0) {
            return suffix + 1;
        }
        return 0;
    }

    private static int printFieldLocked(
            char[] formatStr, int amt, char suffix, int pos, boolean always, int zeropad) {
        if (always || amt > 0) {
            if (amt > 999) {
                int tmp = 0;
                while (amt != 0 && tmp < sTmpFormatStr.length) {
                    sTmpFormatStr[tmp] = (char) ((amt % 10) + 48);
                    tmp++;
                    amt /= 10;
                }
                for (int tmp2 = tmp - 1; tmp2 >= 0; tmp2--) {
                    formatStr[pos] = sTmpFormatStr[tmp2];
                    pos++;
                }
            } else {
                if ((always && zeropad >= 3) || amt > 99) {
                    int dig = amt / 100;
                    formatStr[pos] = (char) (dig + 48);
                    pos++;
                    amt -= dig * 100;
                }
                if ((always && zeropad >= 2) || amt > 9 || pos != pos) {
                    int dig2 = amt / 10;
                    formatStr[pos] = (char) (dig2 + 48);
                    pos++;
                    amt -= dig2 * 10;
                }
                formatStr[pos] = (char) (amt + 48);
                pos++;
            }
            formatStr[pos] = suffix;
            return pos + 1;
        }
        return pos;
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x0133, code lost:

       if (r9 != r8) goto L80;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int formatDurationLocked(long r27, int r29) {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.util.TimeUtils.formatDurationLocked(long,"
                    + " int):int");
    }

    public static void formatDuration(long duration, StringBuilder builder) {
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, 0);
            builder.append(sFormatStr, 0, len);
        }
    }

    public static void formatDuration(long duration, StringBuilder builder, int fieldLen) {
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, fieldLen);
            builder.append(sFormatStr, 0, len);
        }
    }

    public static void formatDuration(long duration, PrintWriter pw, int fieldLen) {
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, fieldLen);
            pw.print(new String(sFormatStr, 0, len));
        }
    }

    public static String formatDuration(long duration) {
        String str;
        synchronized (sFormatSync) {
            int len = formatDurationLocked(duration, 0);
            str = new String(sFormatStr, 0, len);
        }
        return str;
    }

    public static void formatDuration(long duration, PrintWriter pw) {
        formatDuration(duration, pw, 0);
    }

    public static void formatDuration(long time, long now, StringBuilder sb) {
        if (time == 0) {
            sb.append("--");
        } else {
            formatDuration(time - now, sb, 0);
        }
    }

    public static void formatDuration(long time, long now, PrintWriter pw) {
        if (time == 0) {
            pw.print("--");
        } else {
            formatDuration(time - now, pw, 0);
        }
    }

    public static String formatUptime(long time) {
        return formatTime(time, SystemClock.uptimeMillis());
    }

    public static String formatRealtime(long time) {
        return formatTime(time, SystemClock.elapsedRealtime());
    }

    public static String formatTime(long time, long referenceTime) {
        long diff = time - referenceTime;
        if (diff > 0) {
            return time + " (in " + diff + " ms)";
        }
        if (diff < 0) {
            return time + " (" + (-diff) + " ms ago)";
        }
        return time + " (now)";
    }

    public static String logTimeOfDay(long millis) {
        Calendar c = Calendar.getInstance();
        if (millis >= 0) {
            c.setTimeInMillis(millis);
            return String.format("%tm-%td %tH:%tM:%tS.%tL", c, c, c, c, c, c);
        }
        return Long.toString(millis);
    }

    public static String formatForLogging(long millis) {
        if (millis <= 0) {
            return "unknown";
        }
        return sLoggingFormat.format(new Date(millis));
    }

    public static void dumpTime(PrintWriter pw, long time) {
        pw.print(sDumpDateFormat.format(new Date(time)));
    }

    public static boolean isTimeBetween(LocalTime reference, LocalTime start, LocalTime end) {
        if (!reference.isBefore(start) || !reference.isAfter(end)) {
            if (!reference.isBefore(end) || !reference.isBefore(start) || !start.isBefore(end)) {
                if (reference.isAfter(end) && reference.isAfter(start) && start.isBefore(end)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public static void dumpTimeWithDelta(PrintWriter pw, long time, long now) {
        pw.print(sDumpDateFormat.format(new Date(time)));
        if (time == now) {
            pw.print(" (now)");
            return;
        }
        pw.print(" (");
        formatDuration(time, now, pw);
        pw.print(NavigationBarInflaterView.KEY_CODE_END);
    }
}
