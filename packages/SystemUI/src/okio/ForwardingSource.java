package okio;

import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class ForwardingSource implements Source {
    public final Source delegate;

    public ForwardingSource(Source source) {
        this.delegate = source;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.delegate.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) {
        return this.delegate.read(buffer, j);
    }

    public final String toString() {
        return getClass().getSimpleName() + "(" + this.delegate + ")";
    }
}
