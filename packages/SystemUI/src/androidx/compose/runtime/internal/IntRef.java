package androidx.compose.runtime.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.CharsKt__CharJVMKt;

/* loaded from: classes.dex */
public final class IntRef {
    public int element;

    public IntRef() {
        this(0, 1, null);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("IntRef(element = ");
        sb.append(this.element);
        sb.append(")@");
        int iHashCode = hashCode();
        CharsKt__CharJVMKt.checkRadix(16);
        sb.append(Integer.toString(iHashCode, 16));
        return sb.toString();
    }

    public IntRef(int i) {
        this.element = i;
    }

    public /* synthetic */ IntRef(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }
}
