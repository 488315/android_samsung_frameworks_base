package androidx.compose.animation.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StartOffset {
    public final long value;

    /* renamed from: constructor-impl$default, reason: not valid java name */
    public static long m12constructorimpl$default(int i) {
        StartOffsetType.Companion.getClass();
        return i * StartOffsetType.Delay;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof StartOffset) {
            return this.value == ((StartOffset) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return "StartOffset(value=" + this.value + ')';
    }
}
