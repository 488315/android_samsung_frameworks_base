package android.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/* loaded from: classes4.dex */
public class TeeWriter extends Writer {
    private final Writer[] mWriters;

    public TeeWriter(Writer... writerArr) {
        for (Writer writer : writerArr) {
            Objects.requireNonNull(writer);
        }
        this.mWriters = writerArr;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        for (Writer writer : this.mWriters) {
            writer.write(cArr, i, i2);
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        for (Writer writer : this.mWriters) {
            writer.flush();
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        for (Writer writer : this.mWriters) {
            writer.close();
        }
    }
}
