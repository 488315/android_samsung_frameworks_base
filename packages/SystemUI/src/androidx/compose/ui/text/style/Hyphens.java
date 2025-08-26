package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Hyphens {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int None = 1;
    public static final int Auto = 2;
    public static final int Unspecified = Integer.MIN_VALUE;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ Hyphens(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Hyphens m795boximpl(int i) {
        return new Hyphens(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m796toStringimpl(int i) {
        return i == None ? "Hyphens.None" : i == Auto ? "Hyphens.Auto" : i == Unspecified ? "Hyphens.Unspecified" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Hyphens) {
            return this.value == ((Hyphens) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m796toStringimpl(this.value);
    }
}
