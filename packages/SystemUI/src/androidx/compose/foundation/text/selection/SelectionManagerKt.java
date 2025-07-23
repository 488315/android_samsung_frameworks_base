package androidx.compose.foundation.text.selection;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectionManagerKt {
    static {
        new Rect(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    }

    public static final Rect visibleBounds(LayoutCoordinates layoutCoordinates) {
        Rect boundsInWindow = LayoutCoordinatesKt.boundsInWindow(layoutCoordinates);
        long mo619windowToLocalMKHz9U = layoutCoordinates.mo619windowToLocalMKHz9U(boundsInWindow.m409getTopLeftF1C5BW0());
        long mo619windowToLocalMKHz9U2 = layoutCoordinates.mo619windowToLocalMKHz9U(boundsInWindow.m406getBottomRightF1C5BW0());
        return new Rect(Float.intBitsToFloat((int) (mo619windowToLocalMKHz9U >> 32)), Float.intBitsToFloat((int) (mo619windowToLocalMKHz9U & 4294967295L)), Float.intBitsToFloat((int) (mo619windowToLocalMKHz9U2 >> 32)), Float.intBitsToFloat((int) (mo619windowToLocalMKHz9U2 & 4294967295L)));
    }
}
