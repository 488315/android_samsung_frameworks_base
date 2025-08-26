package androidx.compose.foundation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MarqueeAnimationMode {
    public static final Companion Companion = new Companion(null);
    public static final int WhileFocused = 1;
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ MarqueeAnimationMode(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ MarqueeAnimationMode m44boximpl(int i) {
        return new MarqueeAnimationMode(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m45toStringimpl(int i) {
        if (i == 0) {
            return "Immediately";
        }
        if (i == WhileFocused) {
            return "WhileFocused";
        }
        throw new IllegalStateException(("invalid value: " + i).toString());
    }

    public final boolean equals(Object obj) {
        if (obj instanceof MarqueeAnimationMode) {
            return this.value == ((MarqueeAnimationMode) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m45toStringimpl(this.value);
    }
}
