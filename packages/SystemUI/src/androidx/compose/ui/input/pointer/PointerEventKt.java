package androidx.compose.ui.input.pointer;

import androidx.compose.ui.geometry.Offset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PointerEventKt {
    public static final boolean changedToDownIgnoreConsumed(PointerInputChange pointerInputChange) {
        return !pointerInputChange.previousPressed && pointerInputChange.pressed;
    }

    public static final boolean changedToUp(PointerInputChange pointerInputChange) {
        return (pointerInputChange.isConsumed() || !pointerInputChange.previousPressed || pointerInputChange.pressed) ? false : true;
    }

    public static final boolean changedToUpIgnoreConsumed(PointerInputChange pointerInputChange) {
        return pointerInputChange.previousPressed && !pointerInputChange.pressed;
    }

    /* renamed from: isOutOfBounds-jwHxaWs, reason: not valid java name */
    public static final boolean m589isOutOfBoundsjwHxaWs(PointerInputChange pointerInputChange, long j, long j2) {
        PointerType.Companion.getClass();
        int i = pointerInputChange.type == PointerType.Touch ? 1 : 0;
        long j3 = pointerInputChange.position;
        float intBitsToFloat = Float.intBitsToFloat((int) (j3 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j3 & 4294967295L));
        float f = i;
        float intBitsToFloat3 = Float.intBitsToFloat((int) (j2 >> 32)) * f;
        float f2 = ((int) (j >> 32)) + intBitsToFloat3;
        float intBitsToFloat4 = Float.intBitsToFloat((int) (j2 & 4294967295L)) * f;
        return (intBitsToFloat > f2) | (intBitsToFloat < (-intBitsToFloat3)) | (intBitsToFloat2 < (-intBitsToFloat4)) | (intBitsToFloat2 > ((int) (j & 4294967295L)) + intBitsToFloat4);
    }

    public static final long positionChangeInternal(PointerInputChange pointerInputChange, boolean z) {
        long m400minusMKHz9U = Offset.m400minusMKHz9U(pointerInputChange.position, pointerInputChange.previousPosition);
        if (z || !pointerInputChange.isConsumed()) {
            return m400minusMKHz9U;
        }
        Offset.Companion.getClass();
        return 0L;
    }
}
