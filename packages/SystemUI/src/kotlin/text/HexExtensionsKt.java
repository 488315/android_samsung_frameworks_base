package kotlin.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.sec.ims.scab.CABContract;
import kotlin.collections.AbstractList;
import kotlin.text.HexFormat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class HexExtensionsKt {
    public static final int[] BYTE_TO_LOWER_CASE_HEX_DIGITS;
    public static final int[] HEX_DIGITS_TO_DECIMAL;

    static {
        int[] iArr = new int[256];
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            iArr[i2] = "0123456789abcdef".charAt(i2 & 15) | ("0123456789abcdef".charAt(i2 >> 4) << '\b');
        }
        BYTE_TO_LOWER_CASE_HEX_DIGITS = iArr;
        int[] iArr2 = new int[256];
        for (int i3 = 0; i3 < 256; i3++) {
            iArr2[i3] = "0123456789ABCDEF".charAt(i3 & 15) | ("0123456789ABCDEF".charAt(i3 >> 4) << '\b');
        }
        int[] iArr3 = new int[256];
        for (int i4 = 0; i4 < 256; i4++) {
            iArr3[i4] = -1;
        }
        int i5 = 0;
        int i6 = 0;
        while (i5 < "0123456789abcdef".length()) {
            iArr3["0123456789abcdef".charAt(i5)] = i6;
            i5++;
            i6++;
        }
        int i7 = 0;
        int i8 = 0;
        while (i7 < "0123456789ABCDEF".length()) {
            iArr3["0123456789ABCDEF".charAt(i7)] = i8;
            i7++;
            i8++;
        }
        HEX_DIGITS_TO_DECIMAL = iArr3;
        long[] jArr = new long[256];
        for (int i9 = 0; i9 < 256; i9++) {
            jArr[i9] = -1;
        }
        int i10 = 0;
        int i11 = 0;
        while (i10 < "0123456789abcdef".length()) {
            jArr["0123456789abcdef".charAt(i10)] = i11;
            i10++;
            i11++;
        }
        int i12 = 0;
        while (i < "0123456789ABCDEF".length()) {
            jArr["0123456789ABCDEF".charAt(i)] = i12;
            i++;
            i12++;
        }
    }

    public static final void checkNumberOfDigits(int i, int i2, String str) {
        int i3 = i2 - i;
        if (i3 < 1) {
            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "Expected at least 1 hexadecimal digits at index ", ", but was \"", str.substring(i, i2), "\" of length ");
            m.append(i3);
            throw new NumberFormatException(m.toString());
        }
        if (i3 > 8) {
            int i4 = (i3 + i) - 8;
            while (i < i4) {
                if (str.charAt(i) != '0') {
                    StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected the hexadecimal digit '0' at index ", ", but was '");
                    m2.append(str.charAt(i));
                    m2.append("'.\nThe result won't fit the type being parsed.");
                    throw new NumberFormatException(m2.toString());
                }
                i++;
            }
        }
    }

    public static int hexToInt$default(String str) {
        int length;
        HexFormat.Companion.getClass();
        HexFormat hexFormat = HexFormat.Default;
        int length2 = str.length();
        AbstractList.Companion companion = AbstractList.Companion;
        int length3 = str.length();
        companion.getClass();
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(0, length2, length3);
        HexFormat.NumberHexFormat numberHexFormat = hexFormat.number;
        if (numberHexFormat.isDigitsOnly) {
            checkNumberOfDigits(0, length2, str);
            return parseInt(str, 0, length2);
        }
        String str2 = numberHexFormat.prefix;
        int length4 = length2 - str2.length();
        String str3 = numberHexFormat.suffix;
        if (length4 <= str3.length()) {
            String substring = str.substring(0, length2);
            StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Expected a hexadecimal number with prefix \"", str2, "\" and suffix \"", str3, "\", but was ");
            m.append(substring);
            throw new NumberFormatException(m.toString());
        }
        int length5 = str2.length();
        boolean z = numberHexFormat.ignoreCase;
        if (length5 == 0) {
            length = 0;
        } else {
            int length6 = str2.length();
            for (int i = 0; i < length6; i++) {
                if (!CharsKt__CharKt.equals(str2.charAt(i), str.charAt(i), z)) {
                    throwNotContainedAt(0, length2, str, str2, CABContract.CABBusinessContact.PREFIX);
                    throw null;
                }
            }
            length = str2.length();
        }
        int length7 = length2 - str3.length();
        if (str3.length() != 0) {
            int length8 = str3.length();
            for (int i2 = 0; i2 < length8; i2++) {
                if (!CharsKt__CharKt.equals(str3.charAt(i2), str.charAt(length7 + i2), z)) {
                    throwNotContainedAt(length7, length2, str, str3, CABContract.CABBusinessContact.SUFFIX);
                    throw null;
                }
            }
        }
        checkNumberOfDigits(length, length7, str);
        return parseInt(str, str2.length(), length2 - str3.length());
    }

    public static final int parseInt(String str, int i, int i2) {
        int i3;
        int i4 = 0;
        while (i < i2) {
            int i5 = i4 << 4;
            char charAt = str.charAt(i);
            if ((charAt >>> '\b') != 0 || (i3 = HEX_DIGITS_TO_DECIMAL[charAt]) < 0) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected a hexadecimal digit at index ", ", but was ");
                m.append(str.charAt(i));
                throw new NumberFormatException(m.toString());
            }
            i4 = i5 | i3;
            i++;
        }
        return i4;
    }

    public static final void throwNotContainedAt(int i, int i2, String str, String str2, String str3) {
        int length = str2.length() + i;
        if (length <= i2) {
            i2 = length;
        }
        String substring = str.substring(i, i2);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Expected ", str3, " \"", str2, "\" at index ");
        m.append(i);
        m.append(", but was ");
        m.append(substring);
        throw new NumberFormatException(m.toString());
    }
}
