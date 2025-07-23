package kotlin.text;

import java.nio.charset.Charset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
