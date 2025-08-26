package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

/* loaded from: classes.dex */
public abstract class UtilsKt {
    public static final int indexSegment(int i, int i2) {
        return (i >> i2) & 31;
    }
}
