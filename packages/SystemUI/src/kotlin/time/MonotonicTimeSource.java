package kotlin.time;

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
