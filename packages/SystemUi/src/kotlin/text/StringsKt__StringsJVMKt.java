package kotlin.text;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.IntIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
    public static final boolean equals(String str, String str2, boolean z) {
        return str == null ? str2 == null : !z ? str.equals(str2) : str.equalsIgnoreCase(str2);
    }

    public static final boolean isBlank(CharSequence charSequence) {
        boolean z;
        if (charSequence.length() == 0) {
            return true;
        }
        Iterable intRange = new IntRange(0, charSequence.length() - 1);
        if (!(intRange instanceof Collection) || !((Collection) intRange).isEmpty()) {
            Iterator it = intRange.iterator();
            while (it.hasNext()) {
                if (!CharsKt__CharJVMKt.isWhitespace(charSequence.charAt(((IntIterator) it).nextInt()))) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        return z;
    }

    public static final boolean regionMatches(int i, int i2, boolean z, int i3, String str, String str2) {
        return !z ? str.regionMatches(i, str2, i2, i3) : str.regionMatches(z, i, str2, i2, i3);
    }

    public static String replace$default(String str, String str2, String str3) {
        int indexOf = StringsKt__StringsKt.indexOf(0, str, str2, false);
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
            indexOf = StringsKt__StringsKt.indexOf(indexOf + i, str, str2, false);
        } while (indexOf > 0);
        sb.append((CharSequence) str, i2, str.length());
        return sb.toString();
    }
}
