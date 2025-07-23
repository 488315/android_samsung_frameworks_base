package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.BlurEffect;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.TileMode;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BlurKt {
    /* renamed from: blur-F8QBwvs$default, reason: not valid java name */
    public static Modifier m358blurF8QBwvs$default(Modifier modifier, final float f) {
        final boolean z;
        final int i;
        BlurredEdgeTreatment.Companion.getClass();
        final Shape shape = BlurredEdgeTreatment.m359boximpl(BlurredEdgeTreatment.Rectangle).shape;
        if (shape != null) {
            TileMode.Companion.getClass();
            i = 0;
            z = true;
        } else {
            TileMode.Companion.getClass();
            z = false;
            i = TileMode.Decal;
        }
        float f2 = 0;
        Dp.Companion companion = Dp.Companion;
        return ((Float.compare(f, f2) <= 0 || Float.compare(f, f2) <= 0) && !z) ? modifier : GraphicsLayerModifierKt.graphicsLayer(modifier, new Function1() { // from class: androidx.compose.ui.draw.BlurKt$blur$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                float density = reusableGraphicsLayerScope.graphicsDensity.getDensity() * f;
                float density2 = reusableGraphicsLayerScope.graphicsDensity.getDensity() * f;
                reusableGraphicsLayerScope.setRenderEffect((density <= 0.0f || density2 <= 0.0f) ? null : new BlurEffect(null, density, density2, i, null));
                Shape shape2 = shape;
                if (shape2 == null) {
                    shape2 = RectangleShapeKt.RectangleShape;
                }
                reusableGraphicsLayerScope.setShape(shape2);
                reusableGraphicsLayerScope.setClip(z);
                return Unit.INSTANCE;
            }
        });
    }
}
