package com.android.internal.util;

import android.os.Trace;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class NotificationBigTextNormalizer {
    private static final Pattern MULTIPLE_NEWLINES = Pattern.compile("\\v(\\s*\\v)?");
    private static final Pattern HORIZONTAL_WHITESPACES = Pattern.compile("\\h+");

    private NotificationBigTextNormalizer() {
    }

    public static String normalizeBigText(String str) {
        try {
            Trace.beginSection("NotifBigTextNormalizer#normalizeBigText");
            return normalizeLines(HORIZONTAL_WHITESPACES.matcher(MULTIPLE_NEWLINES.matcher(str).replaceAll(ShaderAssembler.NEWLINE)).replaceAll(" "));
        } finally {
            Trace.endSection();
        }
    }

    private static String normalizeLines(String str) {
        String[] strArrSplit = str.split(ShaderAssembler.NEWLINE);
        StringBuilder sb = new StringBuilder(str.length());
        for (String str2 : strArrSplit) {
            StringBuilder sb2 = new StringBuilder(str2.length());
            boolean z = false;
            for (int i = 0; i < str2.length(); i++) {
                char cCharAt = str2.charAt(i);
                if ((cCharAt < 8203 || cCharAt > 8205) && cCharAt != 65279 && cCharAt != 847 && ((cCharAt < 8288 || cCharAt > 8293) && ((cCharAt < 8298 || cCharAt > 8303) && (cCharAt < 65529 || cCharAt > 65531)))) {
                    if (isSpace(cCharAt)) {
                        if (!z) {
                            sb2.append(" ");
                        }
                        z = true;
                    } else {
                        sb2.append(cCharAt);
                        z = false;
                    }
                }
            }
            String strTrim = sb2.toString().trim();
            if (strTrim.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(ShaderAssembler.NEWLINE);
                }
                sb.append(strTrim);
            }
        }
        return sb.toString();
    }

    private static boolean isSpace(char c) {
        return c != '\n' && Character.isSpaceChar(c);
    }
}
