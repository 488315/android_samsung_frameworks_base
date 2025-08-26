package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TextDirection {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Ltr = 1;
    public static final int Rtl = 2;
    public static final int Content = 3;
    public static final int ContentOrLtr = 4;
    public static final int ContentOrRtl = 5;
    public static final int Unspecified = Integer.MIN_VALUE;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ TextDirection(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextDirection m809boximpl(int i) {
        return new TextDirection(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m810toStringimpl(int i) {
        return i == Ltr ? "Ltr" : i == Rtl ? "Rtl" : i == Content ? "Content" : i == ContentOrLtr ? "ContentOrLtr" : i == ContentOrRtl ? "ContentOrRtl" : i == Unspecified ? "Unspecified" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TextDirection) {
            return this.value == ((TextDirection) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m810toStringimpl(this.value);
    }
}
