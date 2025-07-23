package kotlin.time;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MonotonicTimeSource {
    public static final MonotonicTimeSource INSTANCE = new MonotonicTimeSource();
    public static final long zero = System.nanoTime();

    private MonotonicTimeSource() {
    }

    public static long read() {
        return System.nanoTime() - zero;
    }

    public final String toString() {
        return "TimeSource(System.nanoTime())";
    }
}
