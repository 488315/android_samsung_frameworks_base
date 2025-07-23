package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyboardType {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Text = 1;
    public static final int Ascii = 2;
    public static final int Number = 3;
    public static final int Phone = 4;
    public static final int Uri = 5;
    public static final int Email = 6;
    public static final int Password = 7;
    public static final int NumberPassword = 8;
    public static final int Decimal = 9;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ KeyboardType(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ KeyboardType m776boximpl(int i) {
        return new KeyboardType(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m777toStringimpl(int i) {
        return i == 0 ? "Unspecified" : i == Text ? "Text" : i == Ascii ? "Ascii" : i == Number ? "Number" : i == Phone ? "Phone" : i == Uri ? "Uri" : i == Email ? "Email" : i == Password ? "Password" : i == NumberPassword ? "NumberPassword" : i == Decimal ? "Decimal" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof KeyboardType) {
            return this.value == ((KeyboardType) obj).value;
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
