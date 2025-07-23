package androidx.compose.foundation.shape;

import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CornerBasedShape implements Shape {
    public final CornerSize bottomEnd;
    public final CornerSize bottomStart;
    public final CornerSize topEnd;
    public final CornerSize topStart;

    public CornerBasedShape(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
        this.topStart = cornerSize;
        this.topEnd = cornerSize2;
        this.bottomEnd = cornerSize3;
        this.bottomStart = cornerSize4;
    }

    public static /* synthetic */ CornerBasedShape copy$default(CornerBasedShape cornerBasedShape, CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, int i) {
        if ((i & 1) != 0) {
            cornerSize = cornerBasedShape.topStart;
        }
        CornerSize cornerSize4 = cornerBasedShape.topEnd;
        if ((i & 4) != 0) {
            cornerSize2 = cornerBasedShape.bottomEnd;
        }
        return cornerBasedShape.copy(cornerSize, cornerSize4, cornerSize2, cornerSize3);
    }

    public abstract RoundedCornerShape copy(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4);

    /* renamed from: createOutline-LjSzlW0, reason: not valid java name */
    public abstract Outline mo183createOutlineLjSzlW0(long j, float f, float f2, float f3, float f4, LayoutDirection layoutDirection);

    @Override // androidx.compose.ui.graphics.Shape
    /* renamed from: createOutline-Pq9zytI */
    public final Outline mo40createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        float mo184toPxTmRCtEA = this.topStart.mo184toPxTmRCtEA(density, j);
        float mo184toPxTmRCtEA2 = this.topEnd.mo184toPxTmRCtEA(density, j);
        float mo184toPxTmRCtEA3 = this.bottomEnd.mo184toPxTmRCtEA(density, j);
        float mo184toPxTmRCtEA4 = this.bottomStart.mo184toPxTmRCtEA(density, j);
        float m416getMinDimensionimpl = Size.m416getMinDimensionimpl(j);
        float f = mo184toPxTmRCtEA + mo184toPxTmRCtEA4;
        if (f > m416getMinDimensionimpl) {
            float f2 = m416getMinDimensionimpl / f;
            mo184toPxTmRCtEA *= f2;
            mo184toPxTmRCtEA4 *= f2;
        }
        float f3 = mo184toPxTmRCtEA2 + mo184toPxTmRCtEA3;
        if (f3 > m416getMinDimensionimpl) {
            float f4 = m416getMinDimensionimpl / f3;
            mo184toPxTmRCtEA2 *= f4;
            mo184toPxTmRCtEA3 *= f4;
        }
        if (mo184toPxTmRCtEA < 0.0f || mo184toPxTmRCtEA2 < 0.0f || mo184toPxTmRCtEA3 < 0.0f || mo184toPxTmRCtEA4 < 0.0f) {
            StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("Corner size in Px can't be negative(topStart = ", mo184toPxTmRCtEA, ", topEnd = ", mo184toPxTmRCtEA2, ", bottomEnd = ");
            m.append(mo184toPxTmRCtEA3);
            m.append(", bottomStart = ");
            m.append(mo184toPxTmRCtEA4);
            m.append(")!");
            InlineClassHelperKt.throwIllegalArgumentException(m.toString());
        }
        return mo183createOutlineLjSzlW0(j, mo184toPxTmRCtEA, mo184toPxTmRCtEA2, mo184toPxTmRCtEA3, mo184toPxTmRCtEA4, layoutDirection);
    }
}
