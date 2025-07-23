package androidx.compose.foundation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MarqueeAnimationMode {
    public static final Companion Companion = new Companion(null);
    public static final int WhileFocused = 1;
    public final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static final /* synthetic */ MarqueeAnimationMode m43boximpl(int i) {
        return new MarqueeAnimationMode(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m44toStringimpl(int i) {
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
        return m44toStringimpl(this.value);
    }
}
