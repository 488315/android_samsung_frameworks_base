package androidx.compose.ui.text.font;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontStyle {
    public static final Companion Companion = new Companion(null);
    public static final int Italic = 1;
    public final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ FontStyle(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FontStyle m764boximpl(int i) {
        return new FontStyle(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m765toStringimpl(int i) {
        return i == 0 ? "Normal" : i == Italic ? "Italic" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof FontStyle) {
            return this.value == ((FontStyle) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m765toStringimpl(this.value);
    }
}
