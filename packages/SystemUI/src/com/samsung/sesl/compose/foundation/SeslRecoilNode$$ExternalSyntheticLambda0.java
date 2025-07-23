package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SeslRecoilNode$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ SeslRecoilNode f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        DrawScope drawScope = (DrawScope) obj;
        Function1 function1 = (Function1) obj2;
        float floatValue = ((Number) this.f$0.scaleAnimatable.internalState.getValue()).floatValue();
        long mo544getCenterF1C5BW0 = drawScope.mo544getCenterF1C5BW0();
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            drawContext.transform.m530scale0AR0LA0(floatValue, floatValue, mo544getCenterF1C5BW0);
            function1.mo779invoke(drawScope);
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            throw th;
        }
    }
}
