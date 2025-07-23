package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static final /* synthetic */ TextDirection m807boximpl(int i) {
        return new TextDirection(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m808toStringimpl(int i) {
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
        return m808toStringimpl(this.value);
    }
}
