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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.graphics.SimpleGraphicsLayerModifier$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope.placeWithLayer$default((Placeable.PlacementScope) obj, Placeable.this, 0, 0, this.layerBlock, 4);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SimpleGraphicsLayerModifier(float r26, float r27, float r28, float r29, float r30, float r31, float r32, float r33, float r34, float r35, long r36, androidx.compose.ui.graphics.Shape r38, boolean r39, androidx.compose.ui.graphics.RenderEffect r40, long r41, long r43, int r45, int r46, androidx.compose.ui.graphics.ColorFilter r47, int r48, kotlin.jvm.internal.DefaultConstructorMarker r49) {
        /*
            r25 = this;
            r0 = 65536(0x10000, float:9.1835E-41)
            r0 = r48 & r0
            if (r0 == 0) goto Lf
            androidx.compose.ui.graphics.CompositingStrategy$Companion r0 = androidx.compose.ui.graphics.CompositingStrategy.Companion
            r0.getClass()
            r0 = 0
            r21 = r0
            goto L11
        Lf:
            r21 = r45
        L11:
            r0 = 131072(0x20000, float:1.83671E-40)
            r0 = r48 & r0
            if (r0 == 0) goto L21
            androidx.compose.ui.graphics.BlendMode$Companion r0 = androidx.compose.ui.graphics.BlendMode.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.graphics.BlendMode.SrcOver
            r22 = r0
            goto L23
        L21:
            r22 = r46
        L23:
            r0 = 262144(0x40000, float:3.67342E-40)
            r0 = r48 & r0
            if (r0 == 0) goto L2d
            r0 = 0
            r23 = r0
            goto L2f
        L2d:
            r23 = r47
        L2f:
            r24 = 0
            r1 = r25
            r2 = r26
            r3 = r27
            r4 = r28
            r5 = r29
            r6 = r30
            r7 = r31
            r8 = r32
            r9 = r33
            r10 = r34
            r11 = r35
            r12 = r36
            r14 = r38
            r15 = r39
            r16 = r40
            r17 = r41
            r19 = r43
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r14, r15, r16, r17, r19, r21, r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.SimpleGraphicsLayerModifier.<init>(float, float, float, float, float, float, float, float, float, float, long, androidx.compose.ui.graphics.Shape, boolean, androidx.compose.ui.graphics.RenderEffect, long, long, int, int, androidx.compose.ui.graphics.ColorFilter, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
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
            public final Object mo779invoke(Object obj) {
                ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                reusableGraphicsLayerScope.setScaleX(SimpleGraphicsLayerModifier.this.scaleX);
                reusableGraphicsLayerScope.setScaleY(SimpleGraphicsLayerModifier.this.scaleY);
                reusableGraphicsLayerScope.setAlpha(SimpleGraphicsLayerModifier.this.alpha);
                reusableGraphicsLayerScope.setTranslationX(SimpleGraphicsLayerModifier.this.translationX);
                reusableGraphicsLayerScope.setTranslationY(SimpleGraphicsLayerModifier.this.translationY);
                reusableGraphicsLayerScope.setShadowElevation(SimpleGraphicsLayerModifier.this.shadowElevation);
                SimpleGraphicsLayerModifier simpleGraphicsLayerModifier = SimpleGraphicsLayerModifier.this;
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
                SimpleGraphicsLayerModifier simpleGraphicsLayerModifier2 = SimpleGraphicsLayerModifier.this;
                float f13 = simpleGraphicsLayerModifier2.cameraDistance;
                if (reusableGraphicsLayerScope.cameraDistance != f13) {
                    reusableGraphicsLayerScope.mutatedFields |= 2048;
                    reusableGraphicsLayerScope.cameraDistance = f13;
                }
                reusableGraphicsLayerScope.m496setTransformOrigin__ExYCQ(simpleGraphicsLayerModifier2.transformOrigin);
                reusableGraphicsLayerScope.setShape(SimpleGraphicsLayerModifier.this.shape);
                reusableGraphicsLayerScope.setClip(SimpleGraphicsLayerModifier.this.clip);
                reusableGraphicsLayerScope.setRenderEffect(SimpleGraphicsLayerModifier.this.renderEffect);
                reusableGraphicsLayerScope.m493setAmbientShadowColor8_81llA(SimpleGraphicsLayerModifier.this.ambientShadowColor);
                reusableGraphicsLayerScope.m495setSpotShadowColor8_81llA(SimpleGraphicsLayerModifier.this.spotShadowColor);
                reusableGraphicsLayerScope.m494setCompositingStrategyaDBOjCE(SimpleGraphicsLayerModifier.this.compositingStrategy);
                SimpleGraphicsLayerModifier simpleGraphicsLayerModifier3 = SimpleGraphicsLayerModifier.this;
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
