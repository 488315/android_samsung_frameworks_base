package androidx.datastore.core;

import java.io.FileOutputStream;
import java.io.OutputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UncloseableOutputStream extends OutputStream {
    public final FileOutputStream fileOutputStream;

    public UncloseableOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public final void flush() {
        this.fileOutputStream.flush();
    }

    @Override // java.io.OutputStream
    public final void write(int i) {
        this.fileOutputStream.write(i);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) {
        this.fileOutputStream.write(bArr);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i, int i2) {
        this.fileOutputStream.write(bArr, i, i2);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }
}
