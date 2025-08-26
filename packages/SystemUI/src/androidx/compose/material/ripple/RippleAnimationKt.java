package androidx.compose.material.ripple;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class RippleAnimationKt {
    public static final float BoundedRippleExtraRadius;

    static {
        Dp.Companion companion = Dp.Companion;
        BoundedRippleExtraRadius = 10;
    }

    /* renamed from: getRippleEndRadius-cSwnlzA, reason: not valid java name */
    public static final float m244getRippleEndRadiuscSwnlzA(ContentDrawScope contentDrawScope, boolean z, long j) {
        float fM399getDistanceimpl = Offset.m399getDistanceimpl(OffsetKt.Offset(Size.m419getWidthimpl(j), Size.m417getHeightimpl(j))) / 2.0f;
        if (!z) {
            return fM399getDistanceimpl;
        }
        return ((LayoutNodeDrawScope) contentDrawScope).mo58toPx0680j_4(BoundedRippleExtraRadius) + fM399getDistanceimpl;
    }
}
