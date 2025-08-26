package androidx.compose.ui.graphics.layer;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class CompositingStrategy {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Offscreen = 1;
    public static final int ModulateAlpha = 2;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof CompositingStrategy) {
            return this.value == ((CompositingStrategy) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("CompositingStrategy(value="), this.value, ')');
    }
}
