package androidx.compose.foundation.gestures.snapping;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FinalSnappingItem {
    public static final Companion Companion = new Companion(null);
    public static final int NextItem = 1;
    public static final int PreviousItem = 2;
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof FinalSnappingItem) {
            return this.value == ((FinalSnappingItem) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("FinalSnappingItem(value="), this.value, ')');
    }
}
