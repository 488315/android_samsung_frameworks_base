package androidx.core.util;

import android.util.Log;
import java.io.Writer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
