package okio;

import java.io.InputStream;

/* loaded from: classes4.dex */
public abstract class Okio {
    public static final InputStreamSource source(InputStream inputStream) {
        int i = Okio__JvmOkioKt.$r8$clinit;
        return new InputStreamSource(inputStream, new Timeout());
    }
}
