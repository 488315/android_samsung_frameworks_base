package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CompositingStrategy {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Offscreen = 1;
    public static final int ModulateAlpha = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m471toStringimpl(int i) {
        return "CompositingStrategy(value=" + i + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof CompositingStrategy) {
            return this.value == ((CompositingStrategy) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m471toStringimpl(this.value);
    }
}
