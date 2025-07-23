package android.util;

/* loaded from: classes4.dex */
public final class SequenceUtils {
    public static int getInitSeq() {
        return Integer.MIN_VALUE;
    }

    private SequenceUtils() {
    }

    public static boolean isIncomingSeqStale(int i, int i2) {
        if (i == getInitSeq()) {
            return false;
        }
        long j = i2 - i;
        return (j < 0 && j > -2147483648L) || j > 2147483647L;
    }

    public static int getNextSeq(int i) {
        return i == Integer.MAX_VALUE ? getInitSeq() + 1 : i + 1;
    }
}
