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
        String[] split = str.split(ShaderAssembler.NEWLINE);
        StringBuilder sb = new StringBuilder(str.length());
        for (String str2 : split) {
            StringBuilder sb2 = new StringBuilder(str2.length());
            boolean z = false;
            for (int i = 0; i < str2.length(); i++) {
                char charAt = str2.charAt(i);
                if ((charAt < 8203 || charAt > 8205) && charAt != 65279 && charAt != 847 && ((charAt < 8288 || charAt > 8293) && ((charAt < 8298 || charAt > 8303) && (charAt < 65529 || charAt > 65531)))) {
                    if (isSpace(charAt)) {
                        if (!z) {
                            sb2.append(" ");
                        }
                        z = true;
                    } else {
                        sb2.append(charAt);
                        z = false;
                    }
                }
            }
            String trim = sb2.toString().trim();
            if (trim.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(ShaderAssembler.NEWLINE);
                }
                sb.append(trim);
            }
        }
        return sb.toString();
    }

    private static boolean isSpace(char c) {
        return c != '\n' && Character.isSpaceChar(c);
    }
}
