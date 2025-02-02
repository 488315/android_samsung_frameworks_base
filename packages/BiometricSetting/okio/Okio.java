package okio;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public final class Okio {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Logger.getLogger(Okio.class.getName());
    }

    private Okio() {
    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    public static Source source(final InputStream inputStream) {
        final Timeout timeout = new Timeout();
        if (inputStream != null) {
            return new Source() { // from class: okio.Okio.2
                @Override // java.io.Closeable, java.lang.AutoCloseable
                public final void close() throws IOException {
                    inputStream.close();
                }

                @Override // okio.Source
                public final long read(Buffer buffer, long j) throws IOException {
                    try {
                        timeout.throwIfReached();
                        Segment writableSegment = buffer.writableSegment(1);
                        int read = inputStream.read(writableSegment.data, writableSegment.limit, (int) Math.min(8192L, 8192 - writableSegment.limit));
                        if (read == -1) {
                            return -1L;
                        }
                        writableSegment.limit += read;
                        long j2 = read;
                        buffer.size += j2;
                        return j2;
                    } catch (AssertionError e) {
                        if ((e.getCause() == null || e.getMessage() == null || !e.getMessage().contains("getsockname failed")) ? false : true) {
                            throw new IOException(e);
                        }
                        throw e;
                    }
                }

                public final String toString() {
                    return "source(" + inputStream + ")";
                }
            };
        }
        throw new IllegalArgumentException("in == null");
    }
}
