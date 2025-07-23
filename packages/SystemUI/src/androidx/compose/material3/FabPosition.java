package androidx.compose.material3;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FabPosition {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Center = 1;
    public static final int End = 2;
    public static final int EndOverlay = 3;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof FabPosition) {
            return this.value == ((FabPosition) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        int i = this.value;
        return i == 0 ? "FabPosition.Start" : i == Center ? "FabPosition.Center" : i == End ? "FabPosition.End" : "FabPosition.EndOverlay";
    }
}
