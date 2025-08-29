package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ReusableGraphicsLayerScope implements GraphicsLayerScope {
    public long ambientShadowColor;
    public int blendMode;
    public float cameraDistance;
    public boolean clip;
    public ColorFilter colorFilter;
    public int compositingStrategy;
    public Density graphicsDensity;
    public LayoutDirection layoutDirection;
    public int mutatedFields;
    public Outline outline;
    public RenderEffect renderEffect;
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float shadowElevation;
    public Shape shape;
    public long size;
    public long spotShadowColor;
    public long transformOrigin;
    public float translationX;
    public float translationY;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float alpha = 1.0f;

    public ReusableGraphicsLayerScope() {
        long j = GraphicsLayerScopeKt.DefaultShadowColor;
        this.ambientShadowColor = j;
        this.spotShadowColor = j;
        this.cameraDistance = 8.0f;
        TransformOrigin.Companion.getClass();
        this.transformOrigin = TransformOrigin.Center;
        this.shape = RectangleShapeKt.RectangleShape;
        CompositingStrategy.Companion.getClass();
        this.compositingStrategy = 0;
        Size.Companion.getClass();
        this.size = Size.Unspecified;
        this.graphicsDensity = DensityKt.Density$default(1.0f);
        this.layoutDirection = LayoutDirection.Ltr;
        BlendMode.Companion.getClass();
        this.blendMode = BlendMode.SrcOver;
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.graphicsDensity.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.graphicsDensity.getFontScale();
    }

    public final void setAlpha(float f) {
        if (this.alpha == f) {
            return;
        }
        this.mutatedFields |= 4;
        this.alpha = f;
    }

    /* renamed from: setAmbientShadowColor-8_81llA, reason: not valid java name */
    public final void m495setAmbientShadowColor8_81llA(long j) {
        long j2 = this.ambientShadowColor;
        Color.Companion companion = Color.Companion;
        if (ULong.m3446equalsimpl0(j2, j)) {
            return;
        }
        this.mutatedFields |= 64;
        this.ambientShadowColor = j;
    }

    public final void setClip(boolean z) {
        if (this.clip != z) {
            this.mutatedFields |= NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            this.clip = z;
        }
    }

    /* renamed from: setCompositingStrategy-aDBOjCE, reason: not valid java name */
    public final void m496setCompositingStrategyaDBOjCE(int i) {
        int i2 = this.compositingStrategy;
        CompositingStrategy.Companion companion = CompositingStrategy.Companion;
        if (i2 == i) {
            return;
        }
        this.mutatedFields |= NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        this.compositingStrategy = i;
    }

    public final void setRenderEffect(RenderEffect renderEffect) {
        if (Intrinsics.areEqual(this.renderEffect, renderEffect)) {
            return;
        }
        this.mutatedFields |= 131072;
        this.renderEffect = renderEffect;
    }

    public final void setRotationZ(float f) {
        if (this.rotationZ == f) {
            return;
        }
        this.mutatedFields |= 1024;
        this.rotationZ = f;
    }

    public final void setScaleX(float f) {
        if (this.scaleX == f) {
            return;
        }
        this.mutatedFields |= 1;
        this.scaleX = f;
    }

    public final void setScaleY(float f) {
        if (this.scaleY == f) {
            return;
        }
        this.mutatedFields |= 2;
        this.scaleY = f;
    }

    public final void setShadowElevation(float f) {
        if (this.shadowElevation == f) {
            return;
        }
        this.mutatedFields |= 32;
        this.shadowElevation = f;
    }

    public final void setShape(Shape shape) {
        if (Intrinsics.areEqual(this.shape, shape)) {
            return;
        }
        this.mutatedFields |= 8192;
        this.shape = shape;
    }

    /* renamed from: setSpotShadowColor-8_81llA, reason: not valid java name */
    public final void m497setSpotShadowColor8_81llA(long j) {
        long j2 = this.spotShadowColor;
        Color.Companion companion = Color.Companion;
        if (ULong.m3446equalsimpl0(j2, j)) {
            return;
        }
        this.mutatedFields |= 128;
        this.spotShadowColor = j;
    }

    /* renamed from: setTransformOrigin-__ExYCQ, reason: not valid java name */
    public final void m498setTransformOrigin__ExYCQ(long j) {
        if (TransformOrigin.m504equalsimpl0(this.transformOrigin, j)) {
            return;
        }
        this.mutatedFields |= 4096;
        this.transformOrigin = j;
    }

    public final void setTranslationX(float f) {
        if (this.translationX == f) {
            return;
        }
        this.mutatedFields |= 8;
        this.translationX = f;
    }

    public final void setTranslationY(float f) {
        if (this.translationY == f) {
            return;
        }
        this.mutatedFields |= 16;
        this.translationY = f;
    }
}
