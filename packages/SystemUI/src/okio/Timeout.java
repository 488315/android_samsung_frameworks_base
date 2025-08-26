package okio;

import java.io.InterruptedIOException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public class Timeout {
    public static final Timeout$Companion$NONE$1 NONE;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [okio.Timeout$Companion$NONE$1] */
    static {
        new Companion(null);
        NONE = new Timeout() { // from class: okio.Timeout$Companion$NONE$1
            @Override // okio.Timeout
            public final void throwIfReached() {
            }
        };
    }

    public void throwIfReached() throws InterruptedIOException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException("interrupted");
        }
    }
}
