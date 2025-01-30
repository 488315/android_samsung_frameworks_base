package com.google.gson.internal.bind.util;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ISO8601Utils {
    public static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    public static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00e1 A[Catch: IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01ce, TRY_LEAVE, TryCatch #0 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01ce, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:18:0x0050, B:20:0x0060, B:21:0x0062, B:23:0x006e, B:24:0x0070, B:26:0x0076, B:30:0x0080, B:35:0x0090, B:37:0x0098, B:38:0x009c, B:40:0x00a2, B:44:0x00af, B:47:0x00b6, B:52:0x00db, B:54:0x00e1, B:59:0x0196, B:59:0x0196, B:59:0x0196, B:64:0x00f6, B:64:0x00f6, B:64:0x00f6, B:65:0x0111, B:65:0x0111, B:65:0x0111, B:66:0x0112, B:66:0x0112, B:66:0x0112, B:69:0x012e, B:69:0x012e, B:69:0x012e, B:71:0x013b, B:71:0x013b, B:71:0x013b, B:74:0x0144, B:74:0x0144, B:74:0x0144, B:76:0x0163, B:76:0x0163, B:76:0x0163, B:79:0x0173, B:79:0x0173, B:79:0x0173, B:80:0x0195, B:80:0x0195, B:80:0x0195, B:81:0x011d, B:81:0x011d, B:81:0x011d, B:82:0x01c6, B:82:0x01c6, B:82:0x01c6, B:83:0x01cd, B:83:0x01cd, B:83:0x01cd, B:84:0x00c6, B:85:0x00c9, B:88:0x00b2), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01c6 A[Catch: IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01ce, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01ce, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01ce, TryCatch #0 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01ce, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:18:0x0050, B:20:0x0060, B:21:0x0062, B:23:0x006e, B:24:0x0070, B:26:0x0076, B:30:0x0080, B:35:0x0090, B:37:0x0098, B:38:0x009c, B:40:0x00a2, B:44:0x00af, B:47:0x00b6, B:52:0x00db, B:54:0x00e1, B:59:0x0196, B:59:0x0196, B:59:0x0196, B:64:0x00f6, B:64:0x00f6, B:64:0x00f6, B:65:0x0111, B:65:0x0111, B:65:0x0111, B:66:0x0112, B:66:0x0112, B:66:0x0112, B:69:0x012e, B:69:0x012e, B:69:0x012e, B:71:0x013b, B:71:0x013b, B:71:0x013b, B:74:0x0144, B:74:0x0144, B:74:0x0144, B:76:0x0163, B:76:0x0163, B:76:0x0163, B:79:0x0173, B:79:0x0173, B:79:0x0173, B:80:0x0195, B:80:0x0195, B:80:0x0195, B:81:0x011d, B:81:0x011d, B:81:0x011d, B:82:0x01c6, B:82:0x01c6, B:82:0x01c6, B:83:0x01cd, B:83:0x01cd, B:83:0x01cd, B:84:0x00c6, B:85:0x00c9, B:88:0x00b2), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Date parse(String str, ParsePosition parsePosition) {
        String str2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int length;
        char charAt;
        try {
            int index = parsePosition.getIndex();
            int i7 = index + 4;
            int parseInt = parseInt(index, i7, str);
            if (checkOffset(str, i7, '-')) {
                i7++;
            }
            int i8 = i7 + 2;
            int parseInt2 = parseInt(i7, i8, str);
            if (checkOffset(str, i8, '-')) {
                i8++;
            }
            int i9 = i8 + 2;
            int parseInt3 = parseInt(i8, i9, str);
            boolean checkOffset = checkOffset(str, i9, 'T');
            if (!checkOffset && str.length() <= i9) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(parseInt, parseInt2 - 1, parseInt3);
                parsePosition.setIndex(i9);
                return gregorianCalendar.getTime();
            }
            if (checkOffset) {
                int i10 = i9 + 1;
                int i11 = i10 + 2;
                i5 = parseInt(i10, i11, str);
                if (checkOffset(str, i11, ':')) {
                    i11++;
                }
                int i12 = i11 + 2;
                i2 = parseInt(i11, i12, str);
                if (checkOffset(str, i12, ':')) {
                    i12++;
                }
                if (str.length() > i12 && (charAt = str.charAt(i12)) != 'Z' && charAt != '+' && charAt != '-') {
                    i6 = i12 + 2;
                    i4 = parseInt(i12, i6, str);
                    if (i4 > 59 && i4 < 63) {
                        i4 = 59;
                    }
                    if (checkOffset(str, i6, '.')) {
                        int i13 = i6 + 1;
                        int i14 = i13 + 1;
                        while (true) {
                            if (i14 >= str.length()) {
                                i14 = str.length();
                                break;
                            }
                            char charAt2 = str.charAt(i14);
                            if (charAt2 < '0' || charAt2 > '9') {
                                break;
                            }
                            i14++;
                        }
                        int min = Math.min(i14, i13 + 3);
                        i3 = parseInt(i13, min, str);
                        int i15 = min - i13;
                        if (i15 == 1) {
                            i3 *= 100;
                        } else if (i15 == 2) {
                            i3 *= 10;
                        }
                        i6 = i14;
                    } else {
                        i3 = 0;
                    }
                    if (str.length() > i6) {
                        throw new IllegalArgumentException("No time zone indicator");
                    }
                    char charAt3 = str.charAt(i6);
                    TimeZone timeZone = TIMEZONE_UTC;
                    if (charAt3 == 'Z') {
                        length = i6 + 1;
                    } else {
                        if (charAt3 != '+' && charAt3 != '-') {
                            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + charAt3 + "'");
                        }
                        String substring = str.substring(i6);
                        if (substring.length() < 5) {
                            substring = substring + "00";
                        }
                        length = i6 + substring.length();
                        if (!"+0000".equals(substring) && !"+00:00".equals(substring)) {
                            String str3 = "GMT" + substring;
                            timeZone = TimeZone.getTimeZone(str3);
                            String id = timeZone.getID();
                            if (!id.equals(str3) && !id.replace(":", "").equals(str3)) {
                                throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str3 + " given, resolves to " + timeZone.getID());
                            }
                        }
                    }
                    GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
                    gregorianCalendar2.setLenient(false);
                    gregorianCalendar2.set(1, parseInt);
                    gregorianCalendar2.set(2, parseInt2 - 1);
                    gregorianCalendar2.set(5, parseInt3);
                    gregorianCalendar2.set(11, i5);
                    gregorianCalendar2.set(12, i2);
                    gregorianCalendar2.set(13, i4);
                    gregorianCalendar2.set(14, i3);
                    parsePosition.setIndex(length);
                    return gregorianCalendar2.getTime();
                }
                i = i5;
                i9 = i12;
            } else {
                i = 0;
                i2 = 0;
            }
            i3 = 0;
            i4 = 0;
            int i16 = i9;
            i5 = i;
            i6 = i16;
            if (str.length() > i6) {
            }
        } catch (IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException e) {
            if (str == null) {
                str2 = null;
            } else {
                str2 = "\"" + str + '\"';
            }
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + e.getClass().getName() + ")";
            }
            ParseException parseException = new ParseException(FontProvider$$ExternalSyntheticOutline0.m32m("Failed to parse date [", str2, "]: ", message), parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    public static int parseInt(int i, int i2, String str) {
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
}
