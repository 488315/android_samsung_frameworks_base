package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawResult;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class LegacyDragSourceNodeWithDefaultPainter$cacheDrawScopeDragShadowCallback$1$1 extends FunctionReferenceImpl implements Function1 {
    public LegacyDragSourceNodeWithDefaultPainter$cacheDrawScopeDragShadowCallback$1$1(Object obj) {
        super(1, obj, CacheDrawScopeDragShadowCallback.class, "cachePicture", "cachePicture(Landroidx/compose/ui/draw/CacheDrawScope;)Landroidx/compose/ui/draw/DrawResult;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
    public final DrawResult mo779invoke(CacheDrawScope cacheDrawScope) {
        final CacheDrawScopeDragShadowCallback cacheDrawScopeDragShadowCallback = (CacheDrawScopeDragShadowCallback) this.receiver;
        cacheDrawScopeDragShadowCallback.getClass();
        Function0 function0 = cacheDrawScope.graphicsContextProvider;
        function0.getClass();
        GraphicsLayer createGraphicsLayer = ((GraphicsContext) function0.invoke()).createGraphicsLayer();
        CacheDrawScope.m361recordTdoYBX4$default(cacheDrawScope, createGraphicsLayer, new Function1() { // from class: androidx.compose.foundation.draganddrop.CacheDrawScopeDragShadowCallback$cachePicture$1$1$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((LayoutNodeDrawScope) ((ContentDrawScope) obj)).drawContent();
                return Unit.INSTANCE;
            }
        });
        cacheDrawScopeDragShadowCallback.graphicsLayer = createGraphicsLayer;
        return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.draganddrop.CacheDrawScopeDragShadowCallback$cachePicture$1$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                GraphicsLayer graphicsLayer = CacheDrawScopeDragShadowCallback.this.graphicsLayer;
                graphicsLayer.getClass();
                GraphicsLayerKt.drawLayer((ContentDrawScope) obj, graphicsLayer);
                return Unit.INSTANCE;
            }
        });
    }
}
