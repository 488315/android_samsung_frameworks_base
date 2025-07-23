package androidx.compose.ui.graphics.layer;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GraphicsLayer$clipDrawBlock$1 extends Lambda implements Function1 {
    final /* synthetic */ GraphicsLayer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GraphicsLayer$clipDrawBlock$1(GraphicsLayer graphicsLayer) {
        super(1);
        this.this$0 = graphicsLayer;
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r6v4, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        DrawScope drawScope = (DrawScope) obj;
        GraphicsLayer graphicsLayer = this.this$0;
        Path path = graphicsLayer.outlinePath;
        if (graphicsLayer.usePathForClip && graphicsLayer.clip && path != null) {
            ?? r6 = graphicsLayer.drawBlock;
            ClipOp.Companion.getClass();
            int i = ClipOp.Intersect;
            CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
            long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
            drawContext.getCanvas().save();
            try {
                ((CanvasDrawScope$drawContext$1) drawContext.transform.$this_asDrawTransform).getCanvas().mo423clipPathmtrdDE(path, i);
                r6.mo779invoke(drawScope);
            } finally {
                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            }
        } else {
            graphicsLayer.drawBlock.mo779invoke(drawScope);
        }
        return Unit.INSTANCE;
    }
}
