package okio;

import java.io.Closeable;
import java.io.Flushable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface Sink extends Closeable, Flushable {
    void close();

    @Override // java.io.Flushable
    void flush();

    void write(Buffer buffer, long j);
}
