package androidx.compose.runtime.external.kotlinx.collections.immutable.internal;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DeltaCounter {
    public int count;

    public DeltaCounter() {
        this(0, 1, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DeltaCounter) && this.count == ((DeltaCounter) obj).count;
    }

    public final int hashCode() {
        return Integer.hashCode(this.count);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("DeltaCounter(count="), this.count, ')');
    }

    public DeltaCounter(int i) {
        this.count = i;
    }

    public /* synthetic */ DeltaCounter(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }
}
