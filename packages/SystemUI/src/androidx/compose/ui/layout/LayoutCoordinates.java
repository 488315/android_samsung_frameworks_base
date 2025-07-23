package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LayoutCoordinates {
    LayoutCoordinates getParentLayoutCoordinates$1();

    /* renamed from: getSize-YbymL2g, reason: not valid java name */
    long mo610getSizeYbymL2g();

    boolean isAttached();

    Rect localBoundingBoxOf(LayoutCoordinates layoutCoordinates, boolean z);

    /* renamed from: localPositionOf-R5De75A, reason: not valid java name */
    long mo611localPositionOfR5De75A(LayoutCoordinates layoutCoordinates, long j);

    /* renamed from: localPositionOf-S_NoaFU, reason: not valid java name */
    long mo612localPositionOfS_NoaFU(LayoutCoordinates layoutCoordinates, long j, boolean z);

    /* renamed from: localToRoot-MK-Hz9U, reason: not valid java name */
    long mo613localToRootMKHz9U(long j);

    /* renamed from: localToScreen-MK-Hz9U, reason: not valid java name */
    long mo614localToScreenMKHz9U(long j);

    /* renamed from: localToWindow-MK-Hz9U, reason: not valid java name */
    long mo615localToWindowMKHz9U(long j);

    /* renamed from: screenToLocal-MK-Hz9U, reason: not valid java name */
    long mo616screenToLocalMKHz9U(long j);

    /* renamed from: transformFrom-EL8BTi8, reason: not valid java name */
    void mo617transformFromEL8BTi8(LayoutCoordinates layoutCoordinates, float[] fArr);

    /* renamed from: transformToScreen-58bKbWc, reason: not valid java name */
    void mo618transformToScreen58bKbWc(float[] fArr);

    /* renamed from: windowToLocal-MK-Hz9U, reason: not valid java name */
    long mo619windowToLocalMKHz9U(long j);
}
