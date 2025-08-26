package android.text.format;

import android.content.res.Resources;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DecimalFormatSymbols;
import com.android.i18n.timezone.WallTime;
import com.android.i18n.timezone.ZoneInfoData;
import com.android.internal.R;
import com.android.internal.accessibility.common.ShortcutConstants;
import java.nio.CharBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
class TimeFormatter {
    private static final int DAYSPERLYEAR = 366;
    private static final int DAYSPERNYEAR = 365;
    private static final int DAYSPERWEEK = 7;
    private static final int FORCE_LOWER_CASE = -1;
    private static final int HOURSPERDAY = 24;
    private static final int MINSPERHOUR = 60;
    private static final int MONSPERYEAR = 12;
    private static final int SECSPERMIN = 60;
    private static DateFormatSymbols sDateFormatSymbols;
    private static String sDateOnlyFormat;
    private static String sDateTimeFormat;
    private static DecimalFormatSymbols sDecimalFormatSymbols;
    private static Locale sLocale;
    private static String sTimeOnlyFormat;
    private final DateFormatSymbols dateFormatSymbols;
    private final String dateOnlyFormat;
    private final String dateTimeFormat;
    private final DecimalFormatSymbols decimalFormatSymbols;
    private java.util.Formatter numberFormatter;
    private StringBuilder outputBuilder;
    private final String timeOnlyFormat;

    private static boolean brokenIsLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static boolean brokenIsUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static char brokenToLower(char c) {
        return (c < 'A' || c > 'Z') ? c : (char) (c + ' ');
    }

    private static char brokenToUpper(char c) {
        return (c < 'a' || c > 'z') ? c : (char) (c - ' ');
    }

    private static String getFormat(int i, String str, String str2, String str3, String str4) {
        return i != 45 ? i != 48 ? i != 95 ? str : str2 : str4 : str3;
    }

    public TimeFormatter() {
        synchronized (TimeFormatter.class) {
            Locale locale = Locale.getDefault();
            Locale locale2 = sLocale;
            if (locale2 == null || !locale.equals(locale2)) {
                sLocale = locale;
                sDateFormatSymbols = DateFormat.getIcuDateFormatSymbols(locale);
                sDecimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
                Resources system = Resources.getSystem();
                sTimeOnlyFormat = system.getString(R.string.time_of_day);
                sDateOnlyFormat = system.getString(R.string.month_day_year);
                sDateTimeFormat = system.getString(R.string.date_and_time);
            }
            this.dateFormatSymbols = sDateFormatSymbols;
            this.decimalFormatSymbols = sDecimalFormatSymbols;
            this.dateTimeFormat = sDateTimeFormat;
            this.timeOnlyFormat = sTimeOnlyFormat;
            this.dateOnlyFormat = sDateOnlyFormat;
        }
    }

    String formatMillisWithFixedFormat(long j) {
        LocalDateTime localDateTimeOfInstant = LocalDateTime.ofInstant(Instant.ofEpochMilli(j), ZoneId.systemDefault());
        StringBuilder sb = new StringBuilder(19);
        sb.append(localDateTimeOfInstant.getYear());
        sb.append('-');
        append2DigitNumber(sb, localDateTimeOfInstant.getMonthValue());
        sb.append('-');
        append2DigitNumber(sb, localDateTimeOfInstant.getDayOfMonth());
        sb.append(' ');
        append2DigitNumber(sb, localDateTimeOfInstant.getHour());
        sb.append(ShortcutConstants.SERVICES_SEPARATOR);
        append2DigitNumber(sb, localDateTimeOfInstant.getMinute());
        sb.append(ShortcutConstants.SERVICES_SEPARATOR);
        append2DigitNumber(sb, localDateTimeOfInstant.getSecond());
        return localizeDigits(sb.toString());
    }

    private static void append2DigitNumber(StringBuilder sb, int i) {
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String format(String str, WallTime wallTime, ZoneInfoData zoneInfoData) {
        try {
            StringBuilder sb = new StringBuilder();
            this.outputBuilder = sb;
            this.numberFormatter = new java.util.Formatter(sb, Locale.US);
            formatInternal(str, wallTime, zoneInfoData);
            return localizeDigits(sb.toString());
        } finally {
            this.outputBuilder = null;
            this.numberFormatter = null;
        }
    }

    private String localizeDigits(String str) {
        if (this.decimalFormatSymbols.getZeroDigit() == '0') {
            return str;
        }
        int length = str.length();
        int zeroDigit = this.decimalFormatSymbols.getZeroDigit() - '0';
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt >= '0' && cCharAt <= '9') {
                cCharAt = (char) (cCharAt + zeroDigit);
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private void formatInternal(String str, WallTime wallTime, ZoneInfoData zoneInfoData) {
        CharBuffer charBufferWrap = CharBuffer.wrap(str);
        while (charBufferWrap.remaining() > 0) {
            if (charBufferWrap.get(charBufferWrap.position()) == '%' ? handleToken(charBufferWrap, wallTime, zoneInfoData) : true) {
                this.outputBuilder.append(charBufferWrap.get(charBufferWrap.position()));
            }
            charBufferWrap.position(charBufferWrap.position() + 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:156:0x02fd, code lost:
    
        if (r20.getMonth() < 0) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0303, code lost:
    
        if (r20.getMonth() < 12) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0306, code lost:
    
        r17 = r18.dateFormatSymbols.getMonths(0, 0)[r20.getMonth()];
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0312, code lost:
    
        modifyAndAppend(r17, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0317, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean handleToken(CharBuffer charBuffer, WallTime wallTime, ZoneInfoData zoneInfoData) {
        char c;
        String str;
        String str2;
        int i;
        int i2;
        char c2;
        char c3 = 0;
        while (true) {
            if (charBuffer.remaining() <= 1) {
                return true;
            }
            charBuffer.position(charBuffer.position() + 1);
            c = charBuffer.get(charBuffer.position());
            if (c != '#') {
                if (c == '+') {
                    formatInternal("%a %b %e %H:%M:%S %Z %Y", wallTime, zoneInfoData);
                    return false;
                }
                if (c != '-' && c != '0') {
                    if (c == 'M') {
                        this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMinute()));
                        return false;
                    }
                    if (c == 'p') {
                        if (wallTime.getHour() >= 12) {
                            str = this.dateFormatSymbols.getAmPmStrings()[1];
                        } else {
                            str = this.dateFormatSymbols.getAmPmStrings()[0];
                        }
                        modifyAndAppend(str, c3);
                        return false;
                    }
                    if (c == 'O') {
                        continue;
                    } else {
                        if (c == 'P') {
                            if (wallTime.getHour() >= 12) {
                                str2 = this.dateFormatSymbols.getAmPmStrings()[1];
                            } else {
                                str2 = this.dateFormatSymbols.getAmPmStrings()[0];
                            }
                            modifyAndAppend(str2, -1);
                            return false;
                        }
                        if (c != '^' && c != '_') {
                            if (c != 'g') {
                                String str3 = "?";
                                if (c != 'h') {
                                    switch (c) {
                                        case 'A':
                                            if (wallTime.getWeekDay() >= 0 && wallTime.getWeekDay() < 7) {
                                                str3 = this.dateFormatSymbols.getWeekdays(0, 1)[wallTime.getWeekDay() + 1];
                                            }
                                            modifyAndAppend(str3, c3);
                                            return false;
                                        case 'B':
                                            if (c3 == '-') {
                                                if (wallTime.getMonth() >= 0 && wallTime.getMonth() < 12) {
                                                    str3 = this.dateFormatSymbols.getMonths(1, 1)[wallTime.getMonth()];
                                                }
                                                modifyAndAppend(str3, c3);
                                            } else {
                                                if (wallTime.getMonth() >= 0 && wallTime.getMonth() < 12) {
                                                    str3 = this.dateFormatSymbols.getMonths(0, 1)[wallTime.getMonth()];
                                                }
                                                modifyAndAppend(str3, c3);
                                            }
                                            return false;
                                        case 'C':
                                            outputYear(wallTime.getYear(), true, false, c3);
                                            return false;
                                        case 'D':
                                            formatInternal("%m/%d/%y", wallTime, zoneInfoData);
                                            return false;
                                        case 'E':
                                            break;
                                        case 'F':
                                            formatInternal("%Y-%m-%d", wallTime, zoneInfoData);
                                            return false;
                                        case 'G':
                                            break;
                                        case 'H':
                                            this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getHour()));
                                            return false;
                                        case 'I':
                                            this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getHour() % 12 != 0 ? wallTime.getHour() % 12 : 12));
                                            return false;
                                        default:
                                            switch (c) {
                                                case 'R':
                                                    formatInternal(DateUtils.HOUR_MINUTE_24, wallTime, zoneInfoData);
                                                    return false;
                                                case 'S':
                                                    this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getSecond()));
                                                    return false;
                                                case 'T':
                                                    formatInternal("%H:%M:%S", wallTime, zoneInfoData);
                                                    return false;
                                                case 'U':
                                                    this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(((wallTime.getYearDay() + 7) - wallTime.getWeekDay()) / 7));
                                                    return false;
                                                case 'V':
                                                    break;
                                                case 'W':
                                                    this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(((wallTime.getYearDay() + 7) - (wallTime.getWeekDay() != 0 ? wallTime.getWeekDay() - 1 : 6)) / 7));
                                                    return false;
                                                case 'X':
                                                    formatInternal(this.timeOnlyFormat, wallTime, zoneInfoData);
                                                    return false;
                                                case 'Y':
                                                    outputYear(wallTime.getYear(), true, true, c3);
                                                    return false;
                                                case 'Z':
                                                    if (wallTime.getIsDst() < 0) {
                                                        return false;
                                                    }
                                                    modifyAndAppend(TimeZone.getTimeZone(zoneInfoData.getID()).getDisplayName(wallTime.getIsDst() != 0, 0), c3);
                                                    return false;
                                                default:
                                                    switch (c) {
                                                        case 'a':
                                                            if (wallTime.getWeekDay() >= 0 && wallTime.getWeekDay() < 7) {
                                                                str3 = this.dateFormatSymbols.getWeekdays(0, 0)[wallTime.getWeekDay() + 1];
                                                            }
                                                            modifyAndAppend(str3, c3);
                                                            return false;
                                                        case 'b':
                                                            break;
                                                        case 'c':
                                                            formatInternal(this.dateTimeFormat, wallTime, zoneInfoData);
                                                            return false;
                                                        case 'd':
                                                            this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMonthDay()));
                                                            return false;
                                                        case 'e':
                                                            this.numberFormatter.format(getFormat(c3, "%2d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMonthDay()));
                                                            return false;
                                                        default:
                                                            switch (c) {
                                                                case 'j':
                                                                    this.numberFormatter.format(getFormat(c3, "%03d", "%3d", "%d", "%03d"), Integer.valueOf(wallTime.getYearDay() + 1));
                                                                    return false;
                                                                case 'k':
                                                                    this.numberFormatter.format(getFormat(c3, "%2d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getHour()));
                                                                    return false;
                                                                case 'l':
                                                                    this.numberFormatter.format(getFormat(c3, "%2d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getHour() % 12 != 0 ? wallTime.getHour() % 12 : 12));
                                                                    return false;
                                                                case 'm':
                                                                    this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(wallTime.getMonth() + 1));
                                                                    return false;
                                                                case 'n':
                                                                    this.outputBuilder.append('\n');
                                                                    return false;
                                                                default:
                                                                    switch (c) {
                                                                        case 'r':
                                                                            formatInternal("%I:%M:%S %p", wallTime, zoneInfoData);
                                                                            return false;
                                                                        case 's':
                                                                            this.outputBuilder.append(Integer.toString(wallTime.mktime(zoneInfoData)));
                                                                            return false;
                                                                        case 't':
                                                                            this.outputBuilder.append('\t');
                                                                            return false;
                                                                        case 'u':
                                                                            this.numberFormatter.format("%d", Integer.valueOf(wallTime.getWeekDay() != 0 ? wallTime.getWeekDay() : 7));
                                                                            return false;
                                                                        case 'v':
                                                                            formatInternal("%e-%b-%Y", wallTime, zoneInfoData);
                                                                            return false;
                                                                        case 'w':
                                                                            this.numberFormatter.format("%d", Integer.valueOf(wallTime.getWeekDay()));
                                                                            return false;
                                                                        case 'x':
                                                                            formatInternal(this.dateOnlyFormat, wallTime, zoneInfoData);
                                                                            return false;
                                                                        case 'y':
                                                                            outputYear(wallTime.getYear(), false, true, c3);
                                                                            return false;
                                                                        case 'z':
                                                                            if (wallTime.getIsDst() < 0) {
                                                                                return false;
                                                                            }
                                                                            int gmtOffset = wallTime.getGmtOffset();
                                                                            if (gmtOffset < 0) {
                                                                                gmtOffset = -gmtOffset;
                                                                                c2 = '-';
                                                                            } else {
                                                                                c2 = '+';
                                                                            }
                                                                            this.outputBuilder.append(c2);
                                                                            int i3 = gmtOffset / 60;
                                                                            this.numberFormatter.format(getFormat(c3, "%04d", "%4d", "%d", "%04d"), Integer.valueOf(((i3 / 60) * 100) + (i3 % 60)));
                                                                            return false;
                                                                        default:
                                                                            return true;
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            c3 = c;
        }
        int year = wallTime.getYear();
        int yearDay = wallTime.getYearDay();
        int weekDay = wallTime.getWeekDay();
        while (true) {
            int i4 = isLeap(year) ? 366 : 365;
            int i5 = (((yearDay + 11) - weekDay) % 7) - 3;
            int i6 = i5 - (i4 % 7);
            int i7 = i;
            if (i6 < -3) {
                i6 += 7;
            }
            if (yearDay >= i6 + i4) {
                year++;
                i2 = 1;
            } else if (yearDay >= i5) {
                i2 = ((yearDay - i5) / 7) + 1;
            } else {
                year--;
                yearDay += isLeap(year) ? 366 : 365;
                i = i7;
            }
        }
        if (c == 'V') {
            this.numberFormatter.format(getFormat(c3, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(i2));
        } else if (c == 'g') {
            outputYear(year, false, true, c3);
        } else {
            outputYear(year, true, true, c3);
        }
        return false;
    }

    private void modifyAndAppend(CharSequence charSequence, int i) {
        int i2 = 0;
        if (i == -1) {
            while (i2 < charSequence.length()) {
                this.outputBuilder.append(brokenToLower(charSequence.charAt(i2)));
                i2++;
            }
            return;
        }
        if (i != 35) {
            if (i == 94) {
                while (i2 < charSequence.length()) {
                    this.outputBuilder.append(brokenToUpper(charSequence.charAt(i2)));
                    i2++;
                }
                return;
            }
            this.outputBuilder.append(charSequence);
            return;
        }
        while (i2 < charSequence.length()) {
            char cCharAt = charSequence.charAt(i2);
            if (brokenIsUpper(cCharAt)) {
                cCharAt = brokenToLower(cCharAt);
            } else if (brokenIsLower(cCharAt)) {
                cCharAt = brokenToUpper(cCharAt);
            }
            this.outputBuilder.append(cCharAt);
            i2++;
        }
    }

    private void outputYear(int i, boolean z, boolean z2, int i2) {
        int i3 = i % 100;
        int i4 = (i / 100) + (i3 / 100);
        int i5 = i3 % 100;
        if (i5 < 0 && i4 > 0) {
            i5 += 100;
            i4--;
        } else if (i4 < 0 && i5 > 0) {
            i5 -= 100;
            i4++;
        }
        if (z) {
            if (i4 != 0 || i5 >= 0) {
                this.numberFormatter.format(getFormat(i2, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(i4));
            } else {
                this.outputBuilder.append("-0");
            }
        }
        if (z2) {
            if (i5 < 0) {
                i5 = -i5;
            }
            this.numberFormatter.format(getFormat(i2, "%02d", "%2d", "%d", "%02d"), Integer.valueOf(i5));
        }
    }

    private static boolean isLeap(int i) {
        if (i % 4 == 0) {
            return i % 100 != 0 || i % 400 == 0;
        }
        return false;
    }
}
