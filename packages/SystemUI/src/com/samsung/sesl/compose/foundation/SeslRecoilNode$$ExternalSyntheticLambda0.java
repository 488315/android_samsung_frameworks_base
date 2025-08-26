package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public final /* synthetic */ class SeslRecoilNode$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ SeslRecoilNode f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        DrawScope drawScope = (DrawScope) obj;
        Function1 function1 = (Function1) obj2;
        Animatable animatable = this.f$0.scaleAnimatable;
        float fFloatValue = animatable != null ? ((Number) animatable.internalState.getValue()).floatValue() : 1.0f;
        long jMo546getCenterF1C5BW0 = drawScope.mo546getCenterF1C5BW0();
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            drawContext.transform.m532scale0AR0LA0(fFloatValue, fFloatValue, jMo546getCenterF1C5BW0);
            function1.mo781invoke(drawScope);
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
            throw th;
        }
    }
}
