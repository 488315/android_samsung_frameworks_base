package android.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class TeeWriter extends Writer {
    private final Writer[] mWriters;

    public TeeWriter(Writer... writers) {
        for (Writer writer : writers) {
            Objects.requireNonNull(writer);
        }
        this.mWriters = writers;
    }

    @Override // java.io.Writer
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (Writer writer : this.mWriters) {
            writer.write(cbuf, off, len);
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
