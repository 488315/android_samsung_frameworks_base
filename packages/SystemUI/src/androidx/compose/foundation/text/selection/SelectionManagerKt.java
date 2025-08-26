package androidx.compose.foundation.text.selection;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;

/* loaded from: classes.dex */
public abstract class SelectionManagerKt {
    static {
        new Rect(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    }

    public static final Rect visibleBounds(LayoutCoordinates layoutCoordinates) {
        Rect rectBoundsInWindow = LayoutCoordinatesKt.boundsInWindow(layoutCoordinates);
        long jMo621windowToLocalMKHz9U = layoutCoordinates.mo621windowToLocalMKHz9U(rectBoundsInWindow.m411getTopLeftF1C5BW0());
        long jMo621windowToLocalMKHz9U2 = layoutCoordinates.mo621windowToLocalMKHz9U(rectBoundsInWindow.m408getBottomRightF1C5BW0());
        return new Rect(Float.intBitsToFloat((int) (jMo621windowToLocalMKHz9U >> 32)), Float.intBitsToFloat((int) (jMo621windowToLocalMKHz9U & 4294967295L)), Float.intBitsToFloat((int) (jMo621windowToLocalMKHz9U2 >> 32)), Float.intBitsToFloat((int) (jMo621windowToLocalMKHz9U2 & 4294967295L)));
    }
}
