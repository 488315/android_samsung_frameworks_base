package okio;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ForwardingSource implements Source {
    public final Source delegate;

    public ForwardingSource(Source source) {
        this.delegate = source;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
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
