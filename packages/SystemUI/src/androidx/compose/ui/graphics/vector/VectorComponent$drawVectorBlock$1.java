package androidx.compose.ui.graphics.vector;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class VectorComponent$drawVectorBlock$1 extends Lambda implements Function1 {
    final /* synthetic */ VectorComponent this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VectorComponent$drawVectorBlock$1(VectorComponent vectorComponent) {
        super(1);
        this.this$0 = vectorComponent;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        DrawScope drawScope = (DrawScope) obj;
        VectorComponent vectorComponent = this.this$0;
        GroupComponent groupComponent = vectorComponent.root;
        float f = vectorComponent.rootScaleX;
        float f2 = vectorComponent.rootScaleY;
        Offset.Companion.getClass();
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            drawContext.transform.m530scale0AR0LA0(f, f2, 0L);
            groupComponent.draw(drawScope);
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            throw th;
        }
    }
}
