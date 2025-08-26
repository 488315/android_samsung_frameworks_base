package okio;

import java.io.Closeable;

/* loaded from: classes4.dex */
public interface Source extends Closeable {
    long read(Buffer buffer, long j);
}
