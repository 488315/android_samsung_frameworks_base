package androidx.compose.ui.focus;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Focusability {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Always = 1;
    public static final int Never = 2;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Focusability) {
            return this.value == ((Focusability) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = Always;
        int i2 = this.value;
        if (i2 == i) {
            return "Always";
        }
        if (i2 == 0) {
            return "SystemDefined";
        }
        if (i2 == Never) {
            return "Never";
        }
        throw new IllegalStateException("Unknown Focusability");
    }
}
