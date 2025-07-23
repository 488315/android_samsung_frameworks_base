package android.util;

import java.io.Writer;

/* loaded from: classes4.dex */
public class LogWriter extends Writer {
    private final int mBuffer;
    private StringBuilder mBuilder;
    private final int mPriority;
    private final String mTag;

    public LogWriter(int i, String str) {
        this.mBuilder = new StringBuilder(128);
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = 0;
    }

    public LogWriter(int i, String str, int i2) {
        this.mBuilder = new StringBuilder(128);
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = i2;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        flushBuilder();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append(c);
            }
        }
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            Log.println_native(this.mBuffer, this.mPriority, this.mTag, this.mBuilder.toString());
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
        }
    }
}
