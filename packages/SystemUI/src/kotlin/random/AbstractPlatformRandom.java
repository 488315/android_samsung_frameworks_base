package kotlin.random;

/* loaded from: classes4.dex */
public abstract class AbstractPlatformRandom extends Random {
    public abstract java.util.Random getImpl();

    public final int nextInt() {
        return getImpl().nextInt();
    }
}
