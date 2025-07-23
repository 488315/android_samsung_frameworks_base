package androidx.compose.runtime.snapshots;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ReaderKind {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int mask;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
