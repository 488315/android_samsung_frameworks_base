package androidx.compose.ui.draw;

import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CacheDrawScope implements Density {
    public BuildDrawCacheParams cacheParams = EmptyBuildDrawCacheParams.INSTANCE;
    public LayoutNodeDrawScope contentDrawScope;
    public DrawResult drawResult;
    public Function0 graphicsContextProvider;

    /* renamed from: record-TdoYBX4$default, reason: not valid java name */
    public static void m361recordTdoYBX4$default(final CacheDrawScope cacheDrawScope, GraphicsLayer graphicsLayer, final Function1 function1) {
        final LayoutDirection layoutDirection = cacheDrawScope.cacheParams.getLayoutDirection();
        long m863toIntSizeuvyYCjk = IntSizeKt.m863toIntSizeuvyYCjk(cacheDrawScope.cacheParams.mo360getSizeNHjbRc());
        final LayoutNodeDrawScope layoutNodeDrawScope = cacheDrawScope.contentDrawScope;
        layoutNodeDrawScope.getClass();
        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
        final Density density = canvasDrawScope.drawContext.getDensity();
        final LayoutDirection layoutDirection2 = canvasDrawScope.drawContext.getLayoutDirection();
        layoutNodeDrawScope.m646recordJVtK1S4(m863toIntSizeuvyYCjk, graphicsLayer, new Function1() { // from class: androidx.compose.ui.draw.CacheDrawScope$record$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                DrawScope drawScope = (DrawScope) obj;
                CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
                Density density2 = cacheDrawScope;
                LayoutDirection layoutDirection3 = layoutDirection;
                drawContext.setDensity(density2);
                drawContext.setLayoutDirection(layoutDirection3);
                try {
                    Function1.this.mo779invoke(layoutNodeDrawScope);
                    CanvasDrawScope$drawContext$1 drawContext2 = drawScope.getDrawContext();
                    Density density3 = density;
                    LayoutDirection layoutDirection4 = layoutDirection2;
                    drawContext2.setDensity(density3);
                    drawContext2.setLayoutDirection(layoutDirection4);
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    CanvasDrawScope$drawContext$1 drawContext3 = drawScope.getDrawContext();
                    Density density4 = density;
                    LayoutDirection layoutDirection5 = layoutDirection2;
                    drawContext3.setDensity(density4);
                    drawContext3.setLayoutDirection(layoutDirection5);
                    throw th;
                }
            }
        });
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.cacheParams.getDensity().getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.cacheParams.getDensity().getFontScale();
    }

    public final DrawResult onDrawBehind(final Function1 function1) {
        return onDrawWithContent(new Function1() { // from class: androidx.compose.ui.draw.CacheDrawScope$onDrawBehind$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ContentDrawScope contentDrawScope = (ContentDrawScope) obj;
                Function1.this.mo779invoke(contentDrawScope);
                ((LayoutNodeDrawScope) contentDrawScope).drawContent();
                return Unit.INSTANCE;
            }
        });
    }

    public final DrawResult onDrawWithContent(Function1 function1) {
        DrawResult drawResult = new DrawResult(function1);
        this.drawResult = drawResult;
        return drawResult;
    }
}
