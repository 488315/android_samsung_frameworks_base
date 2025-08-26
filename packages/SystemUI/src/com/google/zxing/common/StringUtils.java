package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: classes4.dex */
public final class StringUtils {
    public static final Charset PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset();
    public static final Charset SHIFT_JIS_CHARSET = Charset.forName("SJIS");

    static {
        try {
            Charset.forName("GB2312");
        } catch (UnsupportedCharsetException unused) {
        }
        Charset charsetForName = Charset.forName("EUC_JP");
        Charset charset = SHIFT_JIS_CHARSET;
        Charset charset2 = PLATFORM_DEFAULT_ENCODING;
        if (charset.equals(charset2)) {
            return;
        }
        charsetForName.equals(charset2);
    }

    private StringUtils() {
    }
}
