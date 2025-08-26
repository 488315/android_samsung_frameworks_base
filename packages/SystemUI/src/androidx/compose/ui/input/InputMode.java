package androidx.compose.ui.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class InputMode {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Touch = 1;
    public static final int Keyboard = 2;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ InputMode(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ InputMode m574boximpl(int i) {
        return new InputMode(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof InputMode) {
            return this.value == ((InputMode) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = Touch;
        int i2 = this.value;
        return i2 == i ? "Touch" : i2 == Keyboard ? "Keyboard" : "Error";
    }
}
