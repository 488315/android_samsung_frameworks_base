package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawModifierNode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BorderModifierNodeElement extends ModifierNodeElement<BorderModifierNode> {
    public final Brush brush;
    public final Shape shape;
    public final float width;

    public /* synthetic */ BorderModifierNodeElement(float f, Brush brush, Shape shape, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, brush, shape);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BorderModifierNode(this.width, this.brush, this.shape, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BorderModifierNodeElement)) {
            return false;
        }
        BorderModifierNodeElement borderModifierNodeElement = (BorderModifierNodeElement) obj;
        return Dp.m836equalsimpl0(this.width, borderModifierNodeElement.width) && Intrinsics.areEqual(this.brush, borderModifierNodeElement.brush) && Intrinsics.areEqual(this.shape, borderModifierNodeElement.shape);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return this.shape.hashCode() + ((this.brush.hashCode() + (Float.hashCode(this.width) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BorderModifierNodeElement(width=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.width, ", brush=", sb);
        sb.append(this.brush);
        sb.append(", shape=");
        sb.append(this.shape);
        sb.append(')');
        return sb.toString();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BorderModifierNode borderModifierNode = (BorderModifierNode) node;
        float f = borderModifierNode.width;
        float f2 = this.width;
        boolean m836equalsimpl0 = Dp.m836equalsimpl0(f, f2);
        CacheDrawModifierNode cacheDrawModifierNode = borderModifierNode.drawWithCacheModifierNode;
        if (!m836equalsimpl0) {
            borderModifierNode.width = f2;
            cacheDrawModifierNode.invalidateDrawCache();
        }
        Brush brush = borderModifierNode.brush;
        Brush brush2 = this.brush;
        if (!Intrinsics.areEqual(brush, brush2)) {
            borderModifierNode.brush = brush2;
            cacheDrawModifierNode.invalidateDrawCache();
        }
        Shape shape = borderModifierNode.shape;
        Shape shape2 = this.shape;
        if (Intrinsics.areEqual(shape, shape2)) {
            return;
        }
        borderModifierNode.shape = shape2;
        cacheDrawModifierNode.invalidateDrawCache();
    }

    private BorderModifierNodeElement(float f, Brush brush, Shape shape) {
        this.width = f;
        this.brush = brush;
        this.shape = shape;
    }
}
