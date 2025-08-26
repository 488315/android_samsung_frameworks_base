package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;
import android.text.format.DateFormat;
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
    private static final SimpleDateFormat sLoggingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sDumpDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final Instant MIN_USE_DATE_OF_TIMEZONE = Instant.ofEpochMilli(1546300800000L);
    private static final Object sFormatSync = new Object();
    private static char[] sFormatStr = new char[29];
    private static char[] sTmpFormatStr = new char[29];

    public static TimeZone getTimeZone(int i, boolean z, long j, String str) {
        android.icu.util.TimeZone icuTimeZone = getIcuTimeZone(i, z, j, str);
        if (icuTimeZone != null) {
            return TimeZone.getTimeZone(icuTimeZone.getID());
        }
        return null;
    }

    private static android.icu.util.TimeZone getIcuTimeZone(int i, boolean z, long j, String str) {
        CountryTimeZones.OffsetResult offsetResultLookupByOffsetWithBias;
        if (str == null) {
            return null;
        }
        android.icu.util.TimeZone timeZone = android.icu.util.TimeZone.getDefault();
        CountryTimeZones countryTimeZonesLookupCountryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
        if (countryTimeZonesLookupCountryTimeZones == null || (offsetResultLookupByOffsetWithBias = countryTimeZonesLookupCountryTimeZones.lookupByOffsetWithBias(j, timeZone, i, z)) == null) {
            return null;
        }
        return offsetResultLookupByOffsetWithBias.getTimeZone();
    }

    public static List<String> getTimeZoneIdsForCountryCode(String str) {
        if (str == null) {
            throw new NullPointerException("countryCode == null");
        }
        CountryTimeZones countryTimeZonesLookupCountryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(str.toLowerCase());
        if (countryTimeZonesLookupCountryTimeZones == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (CountryTimeZones.TimeZoneMapping timeZoneMapping : countryTimeZonesLookupCountryTimeZones.getTimeZoneMappings()) {
            if (timeZoneMapping.isShownInPickerAt(MIN_USE_DATE_OF_TIMEZONE)) {
                arrayList.add(timeZoneMapping.getTimeZoneId());
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static String getTimeZoneDatabaseVersion() {
        return ZoneInfoDb.getInstance().getVersion();
    }

    private static int accumField(int i, int i2, boolean z, int i3) {
        int i4 = 0;
        if (i > 999) {
            while (i != 0) {
                i4++;
                i /= 10;
            }
            return i4 + i2;
        }
        if (i > 99 || (z && i3 >= 3)) {
            return i2 + 3;
        }
        if (i > 9 || (z && i3 >= 2)) {
            return i2 + 2;
        }
        if (z || i > 0) {
            return i2 + 1;
        }
        return 0;
    }

    private static int printFieldLocked(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        int i4;
        if (!z && i <= 0) {
            return i2;
        }
        if (i > 999) {
            int i5 = 0;
            while (i != 0) {
                char[] cArr2 = sTmpFormatStr;
                if (i5 >= cArr2.length) {
                    break;
                }
                cArr2[i5] = (char) ((i % 10) + 48);
                i5++;
                i /= 10;
            }
            while (true) {
                i5--;
                if (i5 < 0) {
                    break;
                }
                cArr[i2] = sTmpFormatStr[i5];
                i2++;
            }
        } else {
            if ((!z || i3 < 3) && i <= 99) {
                i4 = i2;
            } else {
                int i6 = i / 100;
                cArr[i2] = (char) (i6 + 48);
                i4 = i2 + 1;
                i -= i6 * 100;
            }
            if ((z && i3 >= 2) || i > 9 || i2 != i4) {
                int i7 = i / 10;
                cArr[i4] = (char) (i7 + 48);
                i4++;
                i -= i7 * 10;
            }
            cArr[i4] = (char) (i + 48);
            i2 = i4 + 1;
        }
        cArr[i2] = c;
        return i2 + 1;
    }

    private static int formatDurationLocked(long j, int i) {
        char c;
        int i2;
        int i3;
        int i4;
        int i5;
        long j2 = j;
        if (sFormatStr.length < i) {
            sFormatStr = new char[i];
        }
        char[] cArr = sFormatStr;
        int i6 = 0;
        if (j2 == 0) {
            int i7 = i - 1;
            while (i6 < i7) {
                cArr[i6] = ' ';
                i6++;
            }
            cArr[i6] = '0';
            return i6 + 1;
        }
        if (j2 > 0) {
            c = '+';
        } else {
            j2 = -j2;
            c = '-';
        }
        int i8 = (int) (j2 % 1000);
        int iFloor = (int) Math.floor(j2 / 1000);
        if (iFloor >= SECONDS_PER_DAY) {
            i2 = iFloor / SECONDS_PER_DAY;
            iFloor -= SECONDS_PER_DAY * i2;
        } else {
            i2 = 0;
        }
        if (iFloor >= 3600) {
            i3 = iFloor / 3600;
            iFloor -= i3 * 3600;
        } else {
            i3 = 0;
        }
        if (iFloor >= 60) {
            int i9 = iFloor / 60;
            iFloor -= i9 * 60;
            i4 = i9;
        } else {
            i4 = 0;
        }
        if (i != 0) {
            int iAccumField = accumField(i2, 1, false, 0);
            int iAccumField2 = iAccumField + accumField(i3, 1, iAccumField > 0, 2);
            int iAccumField3 = iAccumField2 + accumField(i4, 1, iAccumField2 > 0, 2);
            int iAccumField4 = iAccumField3 + accumField(iFloor, 1, iAccumField3 > 0, 2);
            i5 = 0;
            for (int iAccumField5 = iAccumField4 + accumField(i8, 2, true, iAccumField4 > 0 ? 3 : 0) + 1; iAccumField5 < i; iAccumField5++) {
                cArr[i5] = ' ';
                i5++;
            }
        } else {
            i5 = 0;
        }
        cArr[i5] = c;
        int i10 = i5 + 1;
        boolean z = i != 0;
        int iPrintFieldLocked = printFieldLocked(cArr, i2, DateFormat.DATE, i10, false, 0);
        int iPrintFieldLocked2 = printFieldLocked(cArr, i3, DateFormat.HOUR, iPrintFieldLocked, iPrintFieldLocked != i10, z ? 2 : 0);
        int iPrintFieldLocked3 = printFieldLocked(cArr, i4, DateFormat.MINUTE, iPrintFieldLocked2, iPrintFieldLocked2 != i10, z ? 2 : 0);
        int iPrintFieldLocked4 = printFieldLocked(cArr, iFloor, 's', iPrintFieldLocked3, iPrintFieldLocked3 != i10, z ? 2 : 0);
        int iPrintFieldLocked5 = printFieldLocked(cArr, i8, DateFormat.MINUTE, iPrintFieldLocked4, true, (!z || iPrintFieldLocked4 == i10) ? 0 : 3);
        cArr[iPrintFieldLocked5] = 's';
        return iPrintFieldLocked5 + 1;
    }

    public static void formatDuration(long j, StringBuilder sb) {
        synchronized (sFormatSync) {
            sb.append(sFormatStr, 0, formatDurationLocked(j, 0));
        }
    }

    public static void formatDuration(long j, StringBuilder sb, int i) {
        synchronized (sFormatSync) {
            sb.append(sFormatStr, 0, formatDurationLocked(j, i));
        }
    }

    public static void formatDuration(long j, PrintWriter printWriter, int i) {
        synchronized (sFormatSync) {
            printWriter.print(new String(sFormatStr, 0, formatDurationLocked(j, i)));
        }
    }

    public static String formatDuration(long j) {
        String str;
        synchronized (sFormatSync) {
            str = new String(sFormatStr, 0, formatDurationLocked(j, 0));
        }
        return str;
    }

    public static void formatDuration(long j, PrintWriter printWriter) {
        formatDuration(j, printWriter, 0);
    }

    public static void formatDuration(long j, long j2, StringBuilder sb) {
        if (j == 0) {
            sb.append("--");
        } else {
            formatDuration(j - j2, sb, 0);
        }
    }

    public static void formatDuration(long j, long j2, PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--");
        } else {
            formatDuration(j - j2, printWriter, 0);
        }
    }

    public static String formatUptime(long j) {
        return formatTime(j, SystemClock.uptimeMillis());
    }

    public static String formatRealtime(long j) {
        return formatTime(j, SystemClock.elapsedRealtime());
    }

    public static String formatTime(long j, long j2) {
        long j3 = j - j2;
        if (j3 > 0) {
            return j + " (in " + j3 + " ms)";
        }
        if (j3 < 0) {
            return j + " (" + (-j3) + " ms ago)";
        }
        return j + " (now)";
    }

    public static String logTimeOfDay(long j) {
        Calendar calendar = Calendar.getInstance();
        if (j >= 0) {
            calendar.setTimeInMillis(j);
            return String.format("%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar);
        }
        return Long.toString(j);
    }

    public static String formatForLogging(long j) {
        if (j <= 0) {
            return "unknown";
        }
        return sLoggingFormat.format(new Date(j));
    }

    public static void dumpTime(PrintWriter printWriter, long j) {
        printWriter.print(sDumpDateFormat.format(new Date(j)));
    }

    public static boolean isTimeBetween(LocalTime localTime, LocalTime localTime2, LocalTime localTime3) {
        if (localTime.isBefore(localTime2) && localTime.isAfter(localTime3)) {
            return false;
        }
        if (localTime.isBefore(localTime3) && localTime.isBefore(localTime2) && localTime2.isBefore(localTime3)) {
            return false;
        }
        return (localTime.isAfter(localTime3) && localTime.isAfter(localTime2) && localTime2.isBefore(localTime3)) ? false : true;
    }

    public static void dumpTimeWithDelta(PrintWriter printWriter, long j, long j2) {
        printWriter.print(sDumpDateFormat.format(new Date(j)));
        if (j == j2) {
            printWriter.print(" (now)");
            return;
        }
        printWriter.print(" (");
        formatDuration(j, j2, printWriter);
        printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
    }
}
