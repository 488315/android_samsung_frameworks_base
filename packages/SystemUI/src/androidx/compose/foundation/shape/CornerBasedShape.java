package androidx.compose.foundation.shape;

import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

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
    public abstract Outline mo184createOutlineLjSzlW0(long j, float f, float f2, float f3, float f4, LayoutDirection layoutDirection);

    @Override // androidx.compose.ui.graphics.Shape
    /* renamed from: createOutline-Pq9zytI */
    public final Outline mo41createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        float fMo185toPxTmRCtEA = this.topStart.mo185toPxTmRCtEA(density, j);
        float fMo185toPxTmRCtEA2 = this.topEnd.mo185toPxTmRCtEA(density, j);
        float fMo185toPxTmRCtEA3 = this.bottomEnd.mo185toPxTmRCtEA(density, j);
        float fMo185toPxTmRCtEA4 = this.bottomStart.mo185toPxTmRCtEA(density, j);
        float fM418getMinDimensionimpl = Size.m418getMinDimensionimpl(j);
        float f = fMo185toPxTmRCtEA + fMo185toPxTmRCtEA4;
        if (f > fM418getMinDimensionimpl) {
            float f2 = fM418getMinDimensionimpl / f;
            fMo185toPxTmRCtEA *= f2;
            fMo185toPxTmRCtEA4 *= f2;
        }
        float f3 = fMo185toPxTmRCtEA2 + fMo185toPxTmRCtEA3;
        if (f3 > fM418getMinDimensionimpl) {
            float f4 = fM418getMinDimensionimpl / f3;
            fMo185toPxTmRCtEA2 *= f4;
            fMo185toPxTmRCtEA3 *= f4;
        }
        if (fMo185toPxTmRCtEA < 0.0f || fMo185toPxTmRCtEA2 < 0.0f || fMo185toPxTmRCtEA3 < 0.0f || fMo185toPxTmRCtEA4 < 0.0f) {
            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("Corner size in Px can't be negative(topStart = ", fMo185toPxTmRCtEA, ", topEnd = ", fMo185toPxTmRCtEA2, ", bottomEnd = ");
            sbM.append(fMo185toPxTmRCtEA3);
            sbM.append(", bottomStart = ");
            sbM.append(fMo185toPxTmRCtEA4);
            sbM.append(")!");
            InlineClassHelperKt.throwIllegalArgumentException(sbM.toString());
        }
        return mo184createOutlineLjSzlW0(j, fMo185toPxTmRCtEA, fMo185toPxTmRCtEA2, fMo185toPxTmRCtEA3, fMo185toPxTmRCtEA4, layoutDirection);
    }
}
