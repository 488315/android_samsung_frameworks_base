package okio;

import java.io.Closeable;
import java.io.Flushable;

/* loaded from: classes4.dex */
public interface Sink extends Closeable, Flushable {
    void close();

    @Override // java.io.Flushable
    void flush();

    void write(Buffer buffer, long j);
}
