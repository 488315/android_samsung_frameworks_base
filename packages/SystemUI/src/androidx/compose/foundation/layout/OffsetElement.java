package androidx.compose.foundation.layout;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OffsetElement extends ModifierNodeElement<OffsetNode> {
    public final boolean rtlAware;
    public final float x;
    public final float y;

    public /* synthetic */ OffsetElement(float f, float f2, boolean z, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, z, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new OffsetNode(this.x, this.y, this.rtlAware, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        OffsetElement offsetElement = obj instanceof OffsetElement ? (OffsetElement) obj : null;
        return offsetElement != null && Dp.m836equalsimpl0(this.x, offsetElement.x) && Dp.m836equalsimpl0(this.y, offsetElement.y) && this.rtlAware == offsetElement.rtlAware;
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Boolean.hashCode(this.rtlAware) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y, Float.hashCode(this.x) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("OffsetModifierElement(x=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.x, ", y=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.y, ", rtlAware=", sb);
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.rtlAware, ')');
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        OffsetNode offsetNode = (OffsetNode) node;
        float f = offsetNode.x;
        float f2 = this.x;
        boolean m836equalsimpl0 = Dp.m836equalsimpl0(f, f2);
        float f3 = this.y;
        boolean z = this.rtlAware;
        if (!m836equalsimpl0 || !Dp.m836equalsimpl0(offsetNode.y, f3) || offsetNode.rtlAware != z) {
            LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(offsetNode);
            LayoutNode.Companion companion = LayoutNode.Companion;
            requireLayoutNode.requestRelayout$ui_release(false);
        }
        offsetNode.x = f2;
        offsetNode.y = f3;
        offsetNode.rtlAware = z;
    }

    private OffsetElement(float f, float f2, boolean z, Function1 function1) {
        this.x = f;
        this.y = f2;
        this.rtlAware = z;
    }
}
