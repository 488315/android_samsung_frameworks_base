package kotlin.text;

import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public final class Charsets {
    public static final Charset UTF_8;

    static {
        new Charsets();
        UTF_8 = Charset.forName("UTF-8");
        Charset.forName("UTF-16");
        Charset.forName("UTF-16BE");
        Charset.forName("UTF-16LE");
        Charset.forName("US-ASCII");
        Charset.forName("ISO-8859-1");
    }

    private Charsets() {
    }
}
