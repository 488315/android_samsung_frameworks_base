package okio;

import java.io.IOException;
import java.io.InterruptedIOException;

/* loaded from: classes.dex */
public class Timeout {
    public void throwIfReached() throws IOException {
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
        }
    }
}
