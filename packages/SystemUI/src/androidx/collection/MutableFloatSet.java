package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableFloatSet extends FloatSet {
    public MutableFloatSet() {
        this(0, 1, null);
    }

    public MutableFloatSet(int i) {
        super(null);
        long[] jArr;
        if (i < 0) {
            RuntimeHelpersKt.throwIllegalArgumentException("Capacity must be a positive value.");
            throw null;
        }
        int unloadedCapacity = ScatterMapKt.unloadedCapacity(i);
        int max = unloadedCapacity > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(unloadedCapacity)) : 0;
        this._capacity = max;
        if (max == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            int i2 = ((max + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i2];
            Arrays.fill(jArr2, 0, i2, -9187201950435737472L);
            jArr = jArr2;
        }
        this.metadata = jArr;
        int i3 = max >> 3;
        long j = 255 << ((max & 7) << 3);
        jArr[i3] = (jArr[i3] & (~j)) | j;
        this.elements = new float[max];
    }

    public /* synthetic */ MutableFloatSet(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }
}
