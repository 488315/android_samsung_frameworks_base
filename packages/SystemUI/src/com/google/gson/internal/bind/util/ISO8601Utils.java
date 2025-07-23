package com.google.gson.internal.bind.util;

import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String num = Integer.toString(i);
        for (int length = i2 - num.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(num);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00e1 A[Catch: IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x0052, TryCatch #0 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x0052, blocks: (B:3:0x000c, B:5:0x001f, B:6:0x0021, B:8:0x002d, B:9:0x002f, B:11:0x003e, B:13:0x0044, B:18:0x005b, B:20:0x006b, B:21:0x006d, B:23:0x0079, B:24:0x007c, B:26:0x0082, B:30:0x008c, B:35:0x009c, B:37:0x00a4, B:43:0x00db, B:45:0x00e1, B:47:0x00e7, B:48:0x0178, B:55:0x00f8, B:56:0x010e, B:57:0x010f, B:60:0x011f, B:62:0x012c, B:65:0x0135, B:67:0x0147, B:70:0x0156, B:71:0x0173, B:73:0x0176, B:74:0x011b, B:75:0x01aa, B:76:0x01b1, B:77:0x00be, B:78:0x00c1), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01aa A[Catch: IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x0052, TryCatch #0 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x0052, blocks: (B:3:0x000c, B:5:0x001f, B:6:0x0021, B:8:0x002d, B:9:0x002f, B:11:0x003e, B:13:0x0044, B:18:0x005b, B:20:0x006b, B:21:0x006d, B:23:0x0079, B:24:0x007c, B:26:0x0082, B:30:0x008c, B:35:0x009c, B:37:0x00a4, B:43:0x00db, B:45:0x00e1, B:47:0x00e7, B:48:0x0178, B:55:0x00f8, B:56:0x010e, B:57:0x010f, B:60:0x011f, B:62:0x012c, B:65:0x0135, B:67:0x0147, B:70:0x0156, B:71:0x0173, B:73:0x0176, B:74:0x011b, B:75:0x01aa, B:76:0x01b1, B:77:0x00be, B:78:0x00c1), top: B:2:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Date parse(java.lang.String r19, java.text.ParsePosition r20) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 517
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -digit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int digit2 = Character.digit(str.charAt(i4), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - digit2;
            i4 = i5;
        }
        return -i3;
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS;
            int abs = Math.abs(i / 60);
            int abs2 = Math.abs(i % 60);
            sb.append(offset >= 0 ? '+' : '-');
            padInt(sb, abs, 2);
            sb.append(':');
            padInt(sb, abs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }
}
