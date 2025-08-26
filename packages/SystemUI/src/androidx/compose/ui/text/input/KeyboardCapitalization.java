package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class KeyboardCapitalization {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Unspecified = -1;
    public static final int Characters = 1;
    public static final int Words = 2;
    public static final int Sentences = 3;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ KeyboardCapitalization(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ KeyboardCapitalization m776boximpl(int i) {
        return new KeyboardCapitalization(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m777toStringimpl(int i) {
        return i == Unspecified ? "Unspecified" : i == 0 ? "None" : i == Characters ? "Characters" : i == Words ? "Words" : i == Sentences ? "Sentences" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof KeyboardCapitalization) {
            return this.value == ((KeyboardCapitalization) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m777toStringimpl(this.value);
    }
}
