package androidx.compose.ui.graphics;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class GraphicsLayerModifierKt {
    public static final Modifier graphicsLayer(Modifier modifier, Function1 function1) {
        return modifier.then(new BlockGraphicsLayerElement(function1));
    }

    /* renamed from: graphicsLayer-Ap8cVGQ$default, reason: not valid java name */
    public static Modifier m476graphicsLayerAp8cVGQ$default(Modifier.Companion companion, float f, float f2, float f3, float f4, Shape shape, int i) {
        float f5 = (i & 1) != 0 ? 1.0f : f;
        float f6 = (i & 2) != 0 ? 1.0f : f2;
        float f7 = (i & 4) != 0 ? 1.0f : f3;
        float f8 = (i & 32) != 0 ? 0.0f : f4;
        TransformOrigin.Companion.getClass();
        long j = TransformOrigin.Center;
        Shape shape2 = (i & 2048) != 0 ? RectangleShapeKt.RectangleShape : shape;
        long j2 = GraphicsLayerScopeKt.DefaultShadowColor;
        CompositingStrategy.Companion.getClass();
        BlendMode.Companion.getClass();
        GraphicsLayerElement graphicsLayerElement = new GraphicsLayerElement(f5, f6, f7, 0.0f, 0.0f, f8, 0.0f, 0.0f, 0.0f, 8.0f, j, shape2, false, null, j2, j2, 0, BlendMode.SrcOver, null, null);
        companion.then(graphicsLayerElement);
        return graphicsLayerElement;
    }

    /* renamed from: graphicsLayer-_6ThJ44$default, reason: not valid java name */
    public static Modifier m477graphicsLayer_6ThJ44$default(Modifier modifier, float f, float f2, float f3, float f4, float f5, Shape shape, boolean z, int i, int i2) {
        int i3;
        float f6 = (i2 & 1) != 0 ? 1.0f : f;
        float f7 = (i2 & 2) != 0 ? 1.0f : f2;
        float f8 = (i2 & 4) != 0 ? 1.0f : f3;
        float f9 = (i2 & 32) != 0 ? 0.0f : f4;
        float f10 = (i2 & 256) != 0 ? 0.0f : f5;
        TransformOrigin.Companion.getClass();
        long j = TransformOrigin.Center;
        Shape shape2 = (i2 & 2048) != 0 ? RectangleShapeKt.RectangleShape : shape;
        boolean z2 = (i2 & 4096) != 0 ? false : z;
        long j2 = GraphicsLayerScopeKt.DefaultShadowColor;
        if ((i2 & 65536) != 0) {
            CompositingStrategy.Companion.getClass();
            i3 = 0;
        } else {
            i3 = i;
        }
        BlendMode.Companion.getClass();
        return modifier.then(new GraphicsLayerElement(f6, f7, f8, 0.0f, 0.0f, f9, 0.0f, 0.0f, f10, 8.0f, j, shape2, z2, null, j2, j2, i3, BlendMode.SrcOver, null, null));
    }
}
