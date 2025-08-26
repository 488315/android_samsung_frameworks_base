package androidx.compose.ui.graphics;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class SimpleGraphicsLayerModifier extends Modifier.Node implements LayoutModifierNode {
    public float alpha;
    public long ambientShadowColor;
    public int blendMode;
    public float cameraDistance;
    public boolean clip;
    public ColorFilter colorFilter;
    public int compositingStrategy;
    public final Function1 layerBlock;
    public RenderEffect renderEffect;
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float scaleX;
    public float scaleY;
    public float shadowElevation;
    public Shape shape;
    public long spotShadowColor;
    public long transformOrigin;
    public float translationX;
    public float translationY;

    public /* synthetic */ SimpleGraphicsLayerModifier(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, j, shape, z, renderEffect, j2, j3, i, i2, colorFilter);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.graphics.SimpleGraphicsLayerModifier$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope.placeWithLayer$default((Placeable.PlacementScope) obj, placeableMo610measureBRTryo0, 0, 0, this.layerBlock, 4);
                return Unit.INSTANCE;
            }
        });
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SimpleGraphicsLayerModifier(scaleX=");
        sb.append(this.scaleX);
        sb.append(", scaleY=");
        sb.append(this.scaleY);
        sb.append(", alpha = ");
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
        sb.append((Object) TransformOrigin.m507toStringimpl(this.transformOrigin));
        sb.append(", shape=");
        sb.append(this.shape);
        sb.append(", clip=");
        sb.append(this.clip);
        sb.append(", renderEffect=");
        sb.append(this.renderEffect);
        sb.append(", ambientShadowColor=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.ambientShadowColor, ", spotShadowColor=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.spotShadowColor, ", compositingStrategy=", sb);
        sb.append((Object) CompositingStrategy.m473toStringimpl(this.compositingStrategy));
        sb.append(", blendMode=");
        sb.append((Object) BlendMode.m450toStringimpl(this.blendMode));
        sb.append(", colorFilter=");
        sb.append(this.colorFilter);
        sb.append(')');
        return sb.toString();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SimpleGraphicsLayerModifier(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        int i4;
        int i5;
        if ((i3 & 65536) != 0) {
            CompositingStrategy.Companion.getClass();
            i4 = 0;
        } else {
            i4 = i;
        }
        if ((i3 & 131072) != 0) {
            BlendMode.Companion.getClass();
            i5 = BlendMode.SrcOver;
        } else {
            i5 = i2;
        }
        this(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, j, shape, z, renderEffect, j2, j3, i4, i5, (i3 & 262144) != 0 ? null : colorFilter, null);
    }

    private SimpleGraphicsLayerModifier(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter) {
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
        this.layerBlock = new Function1() { // from class: androidx.compose.ui.graphics.SimpleGraphicsLayerModifier$layerBlock$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                reusableGraphicsLayerScope.setScaleX(this.this$0.scaleX);
                reusableGraphicsLayerScope.setScaleY(this.this$0.scaleY);
                reusableGraphicsLayerScope.setAlpha(this.this$0.alpha);
                reusableGraphicsLayerScope.setTranslationX(this.this$0.translationX);
                reusableGraphicsLayerScope.setTranslationY(this.this$0.translationY);
                reusableGraphicsLayerScope.setShadowElevation(this.this$0.shadowElevation);
                SimpleGraphicsLayerModifier simpleGraphicsLayerModifier = this.this$0;
                float f11 = simpleGraphicsLayerModifier.rotationX;
                if (reusableGraphicsLayerScope.rotationX != f11) {
                    reusableGraphicsLayerScope.mutatedFields |= 256;
                    reusableGraphicsLayerScope.rotationX = f11;
                }
                float f12 = simpleGraphicsLayerModifier.rotationY;
                if (reusableGraphicsLayerScope.rotationY != f12) {
                    reusableGraphicsLayerScope.mutatedFields |= 512;
                    reusableGraphicsLayerScope.rotationY = f12;
                }
                reusableGraphicsLayerScope.setRotationZ(simpleGraphicsLayerModifier.rotationZ);
                SimpleGraphicsLayerModifier simpleGraphicsLayerModifier2 = this.this$0;
                float f13 = simpleGraphicsLayerModifier2.cameraDistance;
                if (reusableGraphicsLayerScope.cameraDistance != f13) {
                    reusableGraphicsLayerScope.mutatedFields |= 2048;
                    reusableGraphicsLayerScope.cameraDistance = f13;
                }
                reusableGraphicsLayerScope.m498setTransformOrigin__ExYCQ(simpleGraphicsLayerModifier2.transformOrigin);
                reusableGraphicsLayerScope.setShape(this.this$0.shape);
                reusableGraphicsLayerScope.setClip(this.this$0.clip);
                reusableGraphicsLayerScope.setRenderEffect(this.this$0.renderEffect);
                reusableGraphicsLayerScope.m495setAmbientShadowColor8_81llA(this.this$0.ambientShadowColor);
                reusableGraphicsLayerScope.m497setSpotShadowColor8_81llA(this.this$0.spotShadowColor);
                reusableGraphicsLayerScope.m496setCompositingStrategyaDBOjCE(this.this$0.compositingStrategy);
                SimpleGraphicsLayerModifier simpleGraphicsLayerModifier3 = this.this$0;
                int i3 = simpleGraphicsLayerModifier3.blendMode;
                int i4 = reusableGraphicsLayerScope.blendMode;
                BlendMode.Companion companion = BlendMode.Companion;
                if (i4 != i3) {
                    reusableGraphicsLayerScope.mutatedFields |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    reusableGraphicsLayerScope.blendMode = i3;
                }
                ColorFilter colorFilter2 = simpleGraphicsLayerModifier3.colorFilter;
                if (!Intrinsics.areEqual(reusableGraphicsLayerScope.colorFilter, colorFilter2)) {
                    reusableGraphicsLayerScope.mutatedFields |= 262144;
                    reusableGraphicsLayerScope.colorFilter = colorFilter2;
                }
                return Unit.INSTANCE;
            }
        };
    }
}
