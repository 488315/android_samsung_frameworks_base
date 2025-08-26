package androidx.compose.ui.node;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TouchBoundsExpansion {
    public static final Companion Companion = new Companion(null);
    public static final long None = Companion.pack$ui_release(true, 0, 0, 0, 0);
    public final long packedValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final int access$unpack(Companion companion, long j, int i) {
            companion.getClass();
            return ((int) (j >> (i * 15))) & 32767;
        }

        public static long pack$ui_release(boolean z, int i, int i2, int i3, int i4) {
            return ((i2 & 32767) << 15) | (i & 32767) | ((i3 & 32767) << 30) | ((i4 & 32767) << 45) | (z ? Long.MIN_VALUE : 0L);
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TouchBoundsExpansion) {
            return this.packedValue == ((TouchBoundsExpansion) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return "TouchBoundsExpansion(packedValue=" + this.packedValue + ')';
    }
}
