package androidx.compose.animation.core;

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
