package okio;

import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes.dex */
public interface Source extends Closeable {
    long read(Buffer buffer, long j) throws IOException;
}
