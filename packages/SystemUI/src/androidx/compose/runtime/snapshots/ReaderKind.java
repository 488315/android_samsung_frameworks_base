package androidx.compose.runtime.snapshots;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ReaderKind {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int mask;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ReaderKind) {
            return this.mask == ((ReaderKind) obj).mask;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.mask);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("ReaderKind(mask="), this.mask, ')');
    }
}
