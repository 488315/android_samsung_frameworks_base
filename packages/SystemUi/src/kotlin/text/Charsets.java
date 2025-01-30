package kotlin.text;

import java.nio.charset.Charset;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
