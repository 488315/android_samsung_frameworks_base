package okio;

import java.io.InterruptedIOException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Timeout {
    public static final Timeout$Companion$NONE$1 NONE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public void throwIfReached() {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException("interrupted");
        }
    }
}
