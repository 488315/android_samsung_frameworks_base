package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class UtilsKt {
    public static final int indexSegment(int i, int i2) {
        return (i >> i2) & 31;
    }
}
