package androidx.compose.ui.text.font;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontLoadingStrategy {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int OptionalLocal = 1;
    public static final int Async = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m763toStringimpl(int i) {
        if (i == 0) {
            return "Blocking";
        }
        if (i == OptionalLocal) {
            return "Optional";
        }
        if (i == Async) {
            return "Async";
        }
        return "Invalid(value=" + i + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof FontLoadingStrategy) {
            return this.value == ((FontLoadingStrategy) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m763toStringimpl(this.value);
    }
}
