package kotlin.text;

import kotlin.collections.AbstractList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
    public static String concatToString$default(char[] cArr, int i, int i2, int i3) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = cArr.length;
        }
        AbstractList.Companion companion = AbstractList.Companion;
        int length = cArr.length;
        companion.getClass();
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i, i2, length);
        return new String(cArr, i, i2 - i);
    }

    public static boolean equals(String str, String str2, boolean z) {
        return str == null ? str2 == null : !z ? str.equals(str2) : str.equalsIgnoreCase(str2);
    }

    public static String repeat(int i, CharSequence charSequence) {
        if (i < 0) {
            throw new IllegalArgumentException(("Count 'n' must be non-negative, but was " + i + '.').toString());
        }
        if (i == 0) {
            return "";
        }
        int i2 = 1;
        if (i == 1) {
            return charSequence.toString();
        }
        String str = (String) charSequence;
        int length = str.length();
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            char charAt = str.charAt(0);
            char[] cArr = new char[i];
            for (int i3 = 0; i3 < i; i3++) {
                cArr[i3] = charAt;
            }
            return new String(cArr);
        }
        StringBuilder sb = new StringBuilder(str.length() * i);
        if (1 <= i) {
            while (true) {
                sb.append((CharSequence) str);
                if (i2 == i) {
                    break;
                }
                i2++;
            }
        }
        String sb2 = sb.toString();
        sb2.getClass();
        return sb2;
    }

    public static String replace$default(String str, String str2, String str3) {
        int indexOf = StringsKt__StringsKt.indexOf(str, str2, 0, false);
        if (indexOf < 0) {
            return str;
        }
        int length = str2.length();
        int i = length >= 1 ? length : 1;
        int length2 = str3.length() + (str.length() - length);
        if (length2 < 0) {
            throw new OutOfMemoryError();
        }
        StringBuilder sb = new StringBuilder(length2);
        int i2 = 0;
        do {
            sb.append((CharSequence) str, i2, indexOf);
            sb.append(str3);
            i2 = indexOf + length;
            if (indexOf >= str.length()) {
                break;
            }
            indexOf = StringsKt__StringsKt.indexOf(str, str2, indexOf + i, false);
        } while (indexOf > 0);
        sb.append((CharSequence) str, i2, str.length());
        return sb.toString();
    }
}
