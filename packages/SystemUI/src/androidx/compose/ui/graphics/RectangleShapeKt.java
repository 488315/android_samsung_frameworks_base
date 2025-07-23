package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RectangleShapeKt {
    public static final RectangleShapeKt$RectangleShape$1 RectangleShape = new Shape() { // from class: androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1
        @Override // androidx.compose.ui.graphics.Shape
        /* renamed from: createOutline-Pq9zytI */
        public final Outline mo40createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
            return new Outline.Rectangle(SizeKt.m421toRectuvyYCjk(j));
        }

        public final String toString() {
            return "RectangleShape";
        }
    };
}
