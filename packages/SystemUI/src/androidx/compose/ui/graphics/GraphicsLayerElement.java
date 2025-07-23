package androidx.compose.ui.graphics;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeCoordinator;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GraphicsLayerElement extends ModifierNodeElement<SimpleGraphicsLayerModifier> {
    public final float alpha;
    public final long ambientShadowColor;
    public final int blendMode;
    public final float cameraDistance;
    public final boolean clip;
    public final ColorFilter colorFilter;
    public final int compositingStrategy;
    public final RenderEffect renderEffect;
    public final float rotationX;
    public final float rotationY;
    public final float rotationZ;
    public final float scaleX;
    public final float scaleY;
    public final float shadowElevation;
    public final Shape shape;
    public final long spotShadowColor;
    public final long transformOrigin;
    public final float translationX;
    public final float translationY;

    public /* synthetic */ GraphicsLayerElement(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, j, shape, z, renderEffect, j2, j3, i, i2, colorFilter);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new SimpleGraphicsLayerModifier(this.scaleX, this.scaleY, this.alpha, this.translationX, this.translationY, this.shadowElevation, this.rotationX, this.rotationY, this.rotationZ, this.cameraDistance, this.transformOrigin, this.shape, this.clip, this.renderEffect, this.ambientShadowColor, this.spotShadowColor, this.compositingStrategy, this.blendMode, this.colorFilter, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphicsLayerElement)) {
            return false;
        }
        GraphicsLayerElement graphicsLayerElement = (GraphicsLayerElement) obj;
        if (Float.compare(this.scaleX, graphicsLayerElement.scaleX) != 0 || Float.compare(this.scaleY, graphicsLayerElement.scaleY) != 0 || Float.compare(this.alpha, graphicsLayerElement.alpha) != 0 || Float.compare(this.translationX, graphicsLayerElement.translationX) != 0 || Float.compare(this.translationY, graphicsLayerElement.translationY) != 0 || Float.compare(this.shadowElevation, graphicsLayerElement.shadowElevation) != 0 || Float.compare(this.rotationX, graphicsLayerElement.rotationX) != 0 || Float.compare(this.rotationY, graphicsLayerElement.rotationY) != 0 || Float.compare(this.rotationZ, graphicsLayerElement.rotationZ) != 0 || Float.compare(this.cameraDistance, graphicsLayerElement.cameraDistance) != 0 || !TransformOrigin.m502equalsimpl0(this.transformOrigin, graphicsLayerElement.transformOrigin) || !Intrinsics.areEqual(this.shape, graphicsLayerElement.shape) || this.clip != graphicsLayerElement.clip || !Intrinsics.areEqual(this.renderEffect, graphicsLayerElement.renderEffect)) {
            return false;
        }
        Color.Companion companion = Color.Companion;
        if (!ULong.m3427equalsimpl0(this.ambientShadowColor, graphicsLayerElement.ambientShadowColor) || !ULong.m3427equalsimpl0(this.spotShadowColor, graphicsLayerElement.spotShadowColor)) {
            return false;
        }
        CompositingStrategy.Companion companion2 = CompositingStrategy.Companion;
        if (this.compositingStrategy == graphicsLayerElement.compositingStrategy) {
            BlendMode.Companion companion3 = BlendMode.Companion;
            return this.blendMode == graphicsLayerElement.blendMode && Intrinsics.areEqual(this.colorFilter, graphicsLayerElement.colorFilter);
        }
        return false;
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.cameraDistance, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.rotationZ, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.rotationY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.rotationX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.shadowElevation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.translationY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.translationX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleY, Float.hashCode(this.scaleX) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31);
        TransformOrigin.Companion companion = TransformOrigin.Companion;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m((this.shape.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.transformOrigin)) * 31, 31, this.clip);
        RenderEffect renderEffect = this.renderEffect;
        int hashCode = (m2 + (renderEffect == null ? 0 : renderEffect.hashCode())) * 31;
        Color.Companion companion2 = Color.Companion;
        int i = ULong.$r8$clinit;
        int m3 = MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.ambientShadowColor), 31, this.spotShadowColor);
        CompositingStrategy.Companion companion3 = CompositingStrategy.Companion;
        int m4 = ReorderTile$$ExternalSyntheticOutline0.m(this.compositingStrategy, m3, 31);
        BlendMode.Companion companion4 = BlendMode.Companion;
        int m5 = ReorderTile$$ExternalSyntheticOutline0.m(this.blendMode, m4, 31);
        ColorFilter colorFilter = this.colorFilter;
        return m5 + (colorFilter != null ? colorFilter.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GraphicsLayerElement(scaleX=");
        sb.append(this.scaleX);
        sb.append(", scaleY=");
        sb.append(this.scaleY);
        sb.append(", alpha=");
        sb.append(this.alpha);
        sb.append(", translationX=");
        sb.append(this.translationX);
        sb.append(", translationY=");
        sb.append(this.translationY);
        sb.append(", shadowElevation=");
        sb.append(this.shadowElevation);
        sb.append(", rotationX=");
        sb.append(this.rotationX);
        sb.append(", rotationY=");
        sb.append(this.rotationY);
        sb.append(", rotationZ=");
        sb.append(this.rotationZ);
        sb.append(", cameraDistance=");
        sb.append(this.cameraDistance);
        sb.append(", transformOrigin=");
        sb.append((Object) TransformOrigin.m505toStringimpl(this.transformOrigin));
        sb.append(", shape=");
        sb.append(this.shape);
        sb.append(", clip=");
        sb.append(this.clip);
        sb.append(", renderEffect=");
        sb.append(this.renderEffect);
        sb.append(", ambientShadowColor=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.ambientShadowColor, ", spotShadowColor=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.spotShadowColor, ", compositingStrategy=", sb);
        sb.append((Object) CompositingStrategy.m471toStringimpl(this.compositingStrategy));
        sb.append(", blendMode=");
        sb.append((Object) BlendMode.m448toStringimpl(this.blendMode));
        sb.append(", colorFilter=");
        sb.append(this.colorFilter);
        sb.append(')');
        return sb.toString();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        SimpleGraphicsLayerModifier simpleGraphicsLayerModifier = (SimpleGraphicsLayerModifier) node;
        simpleGraphicsLayerModifier.scaleX = this.scaleX;
        simpleGraphicsLayerModifier.scaleY = this.scaleY;
        simpleGraphicsLayerModifier.alpha = this.alpha;
        simpleGraphicsLayerModifier.translationX = this.translationX;
        simpleGraphicsLayerModifier.translationY = this.translationY;
        simpleGraphicsLayerModifier.shadowElevation = this.shadowElevation;
        simpleGraphicsLayerModifier.rotationX = this.rotationX;
        simpleGraphicsLayerModifier.rotationY = this.rotationY;
        simpleGraphicsLayerModifier.rotationZ = this.rotationZ;
        simpleGraphicsLayerModifier.cameraDistance = this.cameraDistance;
        simpleGraphicsLayerModifier.transformOrigin = this.transformOrigin;
        simpleGraphicsLayerModifier.shape = this.shape;
        simpleGraphicsLayerModifier.clip = this.clip;
        simpleGraphicsLayerModifier.renderEffect = this.renderEffect;
        simpleGraphicsLayerModifier.ambientShadowColor = this.ambientShadowColor;
        simpleGraphicsLayerModifier.spotShadowColor = this.spotShadowColor;
        simpleGraphicsLayerModifier.compositingStrategy = this.compositingStrategy;
        simpleGraphicsLayerModifier.blendMode = this.blendMode;
        simpleGraphicsLayerModifier.colorFilter = this.colorFilter;
        NodeCoordinator nodeCoordinator = DelegatableNodeKt.m632requireCoordinator64DMado(simpleGraphicsLayerModifier, 2).wrapped;
        if (nodeCoordinator != null) {
            nodeCoordinator.updateLayerBlock(simpleGraphicsLayerModifier.layerBlock, true);
        }
    }

    private GraphicsLayerElement(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter) {
        this.scaleX = f;
        this.scaleY = f2;
        this.alpha = f3;
        this.translationX = f4;
        this.translationY = f5;
        this.shadowElevation = f6;
        this.rotationX = f7;
        this.rotationY = f8;
        this.rotationZ = f9;
        this.cameraDistance = f10;
        this.transformOrigin = j;
        this.shape = shape;
        this.clip = z;
        this.renderEffect = renderEffect;
        this.ambientShadowColor = j2;
        this.spotShadowColor = j3;
        this.compositingStrategy = i;
        this.blendMode = i2;
        this.colorFilter = colorFilter;
    }
}
