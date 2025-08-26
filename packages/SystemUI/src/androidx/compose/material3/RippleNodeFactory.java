package androidx.compose.material3;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Dp;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RippleNodeFactory implements IndicationNodeFactory {
    public final boolean bounded;
    public final long color;
    public final ColorProducer colorProducer;
    public final float radius;

    public /* synthetic */ RippleNodeFactory(boolean z, float f, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, f, j);
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final DelegatableNode create(InteractionSource interactionSource) {
        ColorProducer colorProducer = this.colorProducer;
        if (colorProducer == null) {
            colorProducer = new ColorProducer() { // from class: androidx.compose.material3.RippleNodeFactory$create$colorProducer$1
                @Override // androidx.compose.ui.graphics.ColorProducer
                /* renamed from: invoke-0d7_KjU */
                public final long mo262invoke0d7_KjU() {
                    return this.this$0.color;
                }
            };
        }
        float f = this.radius;
        return new DelegatingThemeAwareRippleNode(interactionSource, this.bounded, f, colorProducer, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleNodeFactory)) {
            return false;
        }
        RippleNodeFactory rippleNodeFactory = (RippleNodeFactory) obj;
        if (this.bounded != rippleNodeFactory.bounded || !Dp.m838equalsimpl0(this.radius, rippleNodeFactory.radius) || !Intrinsics.areEqual(this.colorProducer, rippleNodeFactory.colorProducer)) {
            return false;
        }
        long j = rippleNodeFactory.color;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.color, j);
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final int hashCode() {
        int iHashCode = Boolean.hashCode(this.bounded) * 31;
        Dp.Companion companion = Dp.Companion;
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radius, iHashCode, 31);
        ColorProducer colorProducer = this.colorProducer;
        int iHashCode2 = colorProducer != null ? colorProducer.hashCode() : 0;
        Color.Companion companion2 = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.color) + ((iM + iHashCode2) * 31);
    }

    public /* synthetic */ RippleNodeFactory(boolean z, float f, ColorProducer colorProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, f, colorProducer);
    }

    private RippleNodeFactory(boolean z, float f, ColorProducer colorProducer, long j) {
        this.bounded = z;
        this.radius = f;
        this.colorProducer = colorProducer;
        this.color = j;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private RippleNodeFactory(boolean z, float f, ColorProducer colorProducer) {
        this(z, f, colorProducer, Color.Unspecified);
        Color.Companion.getClass();
    }

    private RippleNodeFactory(boolean z, float f, long j) {
        this(z, f, (ColorProducer) null, j);
    }
}
