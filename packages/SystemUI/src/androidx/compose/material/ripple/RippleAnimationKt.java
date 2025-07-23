package androidx.compose.material.ripple;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RippleAnimationKt {
    public static final float BoundedRippleExtraRadius;

    static {
        Dp.Companion companion = Dp.Companion;
        BoundedRippleExtraRadius = 10;
    }

    /* renamed from: getRippleEndRadius-cSwnlzA, reason: not valid java name */
    public static final float m243getRippleEndRadiuscSwnlzA(ContentDrawScope contentDrawScope, boolean z, long j) {
        float m397getDistanceimpl = Offset.m397getDistanceimpl(OffsetKt.Offset(Size.m417getWidthimpl(j), Size.m415getHeightimpl(j))) / 2.0f;
        if (!z) {
            return m397getDistanceimpl;
        }
        return ((LayoutNodeDrawScope) contentDrawScope).mo57toPx0680j_4(BoundedRippleExtraRadius) + m397getDistanceimpl;
    }
}
