package androidx.compose.foundation;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class VerticalScrollableClipShape implements Shape {
    public static final VerticalScrollableClipShape INSTANCE = new VerticalScrollableClipShape();

    private VerticalScrollableClipShape() {
    }

    @Override // androidx.compose.ui.graphics.Shape
    /* renamed from: createOutline-Pq9zytI */
    public final Outline mo40createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        float mo51roundToPx0680j_4 = density.mo51roundToPx0680j_4(ClipScrollableContainerKt.MaxSupportedElevation);
        return new Outline.Rectangle(new Rect(-mo51roundToPx0680j_4, 0.0f, Float.intBitsToFloat((int) (j >> 32)) + mo51roundToPx0680j_4, Float.intBitsToFloat((int) (j & 4294967295L))));
    }
}
