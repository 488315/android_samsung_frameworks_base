package androidx.compose.ui.input.pointer;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConsumedData {
    public final PointerInputChange change;
    public final boolean downChange;
    public final boolean positionChange;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ConsumedData() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.ConsumedData.<init>():void");
    }

    public ConsumedData(PointerInputChange pointerInputChange) {
        this(pointerInputChange.positionChange, pointerInputChange.downChange);
        this.change = pointerInputChange;
    }

    public ConsumedData(boolean z, boolean z2) {
        this.positionChange = z;
        this.downChange = z2;
    }

    public /* synthetic */ ConsumedData(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
    }
}
