package kotlin.random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractPlatformRandom extends Random {
    public abstract java.util.Random getImpl();

    @Override // kotlin.random.Random
    public final int nextBits() {
        return (getImpl().nextInt() >>> 0) & (-1);
    }

    @Override // kotlin.random.Random
    public final int nextInt() {
        return getImpl().nextInt();
    }
}
