package kotlin;

/* loaded from: classes4.dex */
public abstract class UnsignedKt {
    public static final double ulongToDouble(long j) {
        return ((j >>> 11) * 2048) + (j & 2047);
    }
}
