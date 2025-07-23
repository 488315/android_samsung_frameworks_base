package androidx.compose.ui.input.nestedscroll;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NestedScrollSource {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int UserInput = 1;
    public static final int SideEffect = 2;
    public static final int Relocate = 3;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof NestedScrollSource) {
            return this.value == ((NestedScrollSource) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = UserInput;
        int i2 = this.value;
        return i2 == i ? "UserInput" : i2 == SideEffect ? "SideEffect" : i2 == Relocate ? "Relocate" : "Invalid";
    }
}
