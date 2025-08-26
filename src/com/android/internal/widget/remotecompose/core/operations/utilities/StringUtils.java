package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.media.MediaMetrics;
import com.android.internal.content.NativeLibraryHelper;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class StringUtils {
    public static String floatToString(float f, int i, int i2, char c, char c2) {
        boolean z = f < 0.0f;
        if (z) {
            f = -f;
        }
        int i3 = (int) f;
        float f2 = f % 1.0f;
        String strValueOf = String.valueOf(i3);
        int length = strValueOf.length();
        if (length < i) {
            int i4 = i - length;
            if (c != 0) {
                char[] cArr = new char[i4];
                Arrays.fill(cArr, c);
                strValueOf = new String(cArr) + strValueOf;
            }
        } else if (length > i) {
            strValueOf = strValueOf.substring(length - i);
        }
        String str = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            if (!z) {
                str = "";
            }
            sb.append(str);
            sb.append(strValueOf);
            return sb.toString();
        }
        for (int i5 = 0; i5 < i2; i5++) {
            f2 *= 10.0f;
        }
        float fRound = Math.round(f2);
        for (int i6 = 0; i6 < i2; i6++) {
            fRound *= 0.1f;
        }
        String string = Float.toString(fRound);
        String strSubstring = string.substring(2, Math.min(string.length(), i2 + 2));
        int length2 = strSubstring.length();
        for (int length3 = strSubstring.length() - 1; length3 >= 0 && strSubstring.charAt(length3) == '0'; length3--) {
            length2--;
        }
        if (length2 != strSubstring.length()) {
            strSubstring = strSubstring.substring(0, length2);
        }
        int length4 = strSubstring.length();
        if (c2 != 0 && length4 < i2) {
            char[] cArr2 = new char[i2 - length4];
            Arrays.fill(cArr2, c2);
            strSubstring = strSubstring + new String(cArr2);
        }
        StringBuilder sb2 = new StringBuilder();
        if (!z) {
            str = "";
        }
        sb2.append(str);
        sb2.append(strValueOf);
        sb2.append(MediaMetrics.SEPARATOR);
        sb2.append(strSubstring);
        return sb2.toString();
    }
}
