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
        LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.ofEpochMilli(j), ZoneId.systemDefault());
        StringBuilder sb = new StringBuilder(19);
        sb.append(ofInstant.getYear());
        sb.append('-');
        append2DigitNumber(sb, ofInstant.getMonthValue());
        sb.append('-');
        append2DigitNumber(sb, ofInstant.getDayOfMonth());
        sb.append(' ');
        append2DigitNumber(sb, ofInstant.getHour());
        sb.append(ShortcutConstants.SERVICES_SEPARATOR);
        append2DigitNumber(sb, ofInstant.getMinute());
        sb.append(ShortcutConstants.SERVICES_SEPARATOR);
        append2DigitNumber(sb, ofInstant.getSecond());
        return localizeDigits(sb.toString());
    }

    private static void append2DigitNumber(StringBuilder sb, int i) {
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
    }

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
            char charAt = str.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                charAt = (char) (charAt + zeroDigit);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    private void formatInternal(String str, WallTime wallTime, ZoneInfoData zoneInfoData) {
        CharBuffer wrap = CharBuffer.wrap(str);
        while (wrap.remaining() > 0) {
            if (wrap.get(wrap.position()) == '%' ? handleToken(wrap, wallTime, zoneInfoData) : true) {
                this.outputBuilder.append(wrap.get(wrap.position()));
            }
            wrap.position(wrap.position() + 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0306, code lost:
    
        r17 = r18.dateFormatSymbols.getMonths(0, 0)[r20.getMonth()];
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0312, code lost:
    
        modifyAndAppend(r17, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0317, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0318, code lost:
    
        r1 = r20.getYear();
        r3 = r20.getYearDay();
        r8 = r20.getWeekDay();
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0328, code lost:
    
        if (isLeap(r1) == false) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x032a, code lost:
    
        r2 = 366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x032f, code lost:
    
        r9 = (((r3 + 11) - r8) % 7) - 3;
        r14 = r9 - (r2 % 7);
        r16 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x033e, code lost:
    
        if (r14 >= (-3)) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0340, code lost:
    
        r14 = r14 + 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0343, code lost:
    
        if (r3 < (r14 + r2)) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0349, code lost:
    
        if (r3 < r9) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x036f, code lost:
    
        r1 = r1 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0375, code lost:
    
        if (isLeap(r1) == false) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0377, code lost:
    
        r9 = 366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x037c, code lost:
    
        r3 = r3 + r9;
        r15 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x037a, code lost:
    
        r9 = 365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x034b, code lost:
    
        r3 = ((r3 - r9) / 7) + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0351, code lost:
    
        if (r6 != 'V') goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0353, code lost:
    
        r18.numberFormatter.format(getFormat(r5, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x036e, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0365, code lost:
    
        if (r6 != 'g') goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0367, code lost:
    
        outputYear(r1, false, true, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x036b, code lost:
    
        outputYear(r1, true, true, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0345, code lost:
    
        r1 = r1 + 1;
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x032d, code lost:
    
        r2 = 365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x02fd, code lost:
    
        if (r20.getMonth() < 0) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0303, code lost:
    
        if (r20.getMonth() < 12) goto L160;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean handleToken(java.nio.CharBuffer r19, com.android.i18n.timezone.WallTime r20, com.android.i18n.timezone.ZoneInfoData r21) {
        /*
            Method dump skipped, instructions count: 1078
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.format.TimeFormatter.handleToken(java.nio.CharBuffer, com.android.i18n.timezone.WallTime, com.android.i18n.timezone.ZoneInfoData):boolean");
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
            char charAt = charSequence.charAt(i2);
            if (brokenIsUpper(charAt)) {
                charAt = brokenToLower(charAt);
            } else if (brokenIsLower(charAt)) {
                charAt = brokenToUpper(charAt);
            }
            this.outputBuilder.append(charAt);
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
