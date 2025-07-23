package androidx.fragment.app;

import android.util.Log;
import java.io.Writer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LogWriter extends Writer {
    public final StringBuilder mBuilder = new StringBuilder(128);
    public final String mTag;

    public LogWriter(String str) {
        this.mTag = str;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public final void flush() {
        flushBuilder();
    }

    public final void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            Log.d(this.mTag, this.mBuilder.toString());
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
        }
    }

    @Override // java.io.Writer
    public final void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append(c);
            }
        }
    }
}
