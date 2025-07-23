package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StringUtils {
    public static final Charset PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset();
    public static final Charset SHIFT_JIS_CHARSET = Charset.forName("SJIS");

    static {
        try {
            Charset.forName("GB2312");
        } catch (UnsupportedCharsetException unused) {
        }
        Charset forName = Charset.forName("EUC_JP");
        Charset charset = SHIFT_JIS_CHARSET;
        Charset charset2 = PLATFORM_DEFAULT_ENCODING;
        if (charset.equals(charset2)) {
            return;
        }
        forName.equals(charset2);
    }

    private StringUtils() {
    }
}
