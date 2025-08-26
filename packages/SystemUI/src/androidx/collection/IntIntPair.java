package androidx.collection;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class IntIntPair {
    public final long packedValue;

    private /* synthetic */ IntIntPair(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ IntIntPair m0boximpl(long j) {
        return new IntIntPair(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m1constructorimpl(int i, int i2) {
        return (i2 & 4294967295L) | (i << 32);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof IntIntPair) {
            return this.packedValue == ((IntIntPair) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("(");
        long j = this.packedValue;
        sb.append((int) (j >> 32));
        sb.append(", ");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, (int) (j & 4294967295L), ')');
    }
}
