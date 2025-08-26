package androidx.compose.foundation;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* loaded from: classes.dex */
final class VerticalScrollableClipShape implements Shape {
    public static final VerticalScrollableClipShape INSTANCE = new VerticalScrollableClipShape();

    private VerticalScrollableClipShape() {
    }

    @Override // androidx.compose.ui.graphics.Shape
    /* renamed from: createOutline-Pq9zytI */
    public final Outline mo41createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        float fMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(ClipScrollableContainerKt.MaxSupportedElevation);
        return new Outline.Rectangle(new Rect(-fMo52roundToPx0680j_4, 0.0f, Float.intBitsToFloat((int) (j >> 32)) + fMo52roundToPx0680j_4, Float.intBitsToFloat((int) (j & 4294967295L))));
    }
}
