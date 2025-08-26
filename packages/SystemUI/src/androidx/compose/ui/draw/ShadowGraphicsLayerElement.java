package androidx.compose.ui.draw;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.BlockGraphicsLayerModifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.Dp;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ShadowGraphicsLayerElement extends ModifierNodeElement<BlockGraphicsLayerModifier> {
    public final long ambientColor;
    public final boolean clip;
    public final float elevation;
    public final Shape shape;
    public final long spotColor;

    public /* synthetic */ ShadowGraphicsLayerElement(float f, Shape shape, boolean z, long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, shape, z, j, j2);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BlockGraphicsLayerModifier(new ShadowGraphicsLayerElement$createBlock$1(this));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadowGraphicsLayerElement)) {
            return false;
        }
        ShadowGraphicsLayerElement shadowGraphicsLayerElement = (ShadowGraphicsLayerElement) obj;
        if (!Dp.m838equalsimpl0(this.elevation, shadowGraphicsLayerElement.elevation) || !Intrinsics.areEqual(this.shape, shadowGraphicsLayerElement.shape) || this.clip != shadowGraphicsLayerElement.clip) {
            return false;
        }
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.ambientColor, shadowGraphicsLayerElement.ambientColor) && ULong.m3447equalsimpl0(this.spotColor, shadowGraphicsLayerElement.spotColor);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        int iM = TransitionData$$ExternalSyntheticOutline0.m((this.shape.hashCode() + (Float.hashCode(this.elevation) * 31)) * 31, 31, this.clip);
        Color.Companion companion2 = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.spotColor) + MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.ambientColor);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShadowGraphicsLayerElement(elevation=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.elevation, ", shape=", sb);
        sb.append(this.shape);
        sb.append(", clip=");
        sb.append(this.clip);
        sb.append(", ambientColor=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.ambientColor, ", spotColor=", sb);
        sb.append((Object) Color.m464toStringimpl(this.spotColor));
        sb.append(')');
        return sb.toString();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BlockGraphicsLayerModifier blockGraphicsLayerModifier = (BlockGraphicsLayerModifier) node;
        blockGraphicsLayerModifier.layerBlock = new ShadowGraphicsLayerElement$createBlock$1(this);
        NodeCoordinator nodeCoordinator = DelegatableNodeKt.m634requireCoordinator64DMado(blockGraphicsLayerModifier, 2).wrapped;
        if (nodeCoordinator != null) {
            nodeCoordinator.updateLayerBlock(blockGraphicsLayerModifier.layerBlock, true);
        }
    }

    private ShadowGraphicsLayerElement(float f, Shape shape, boolean z, long j, long j2) {
        this.elevation = f;
        this.shape = shape;
        this.clip = z;
        this.ambientColor = j;
        this.spotColor = j2;
    }
}
