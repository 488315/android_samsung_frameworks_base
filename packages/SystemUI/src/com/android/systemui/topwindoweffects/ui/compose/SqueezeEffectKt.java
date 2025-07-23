package com.android.systemui.topwindoweffects.ui.compose;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt$asDrawTransform$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.vector.VectorPainter;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SqueezeEffectKt {
    public static final long SqueezeColor;
    public static final float SqueezeEffectMaxThickness;

    static {
        Dp.Companion companion = Dp.Companion;
        SqueezeEffectMaxThickness = 12;
        Color.Companion.getClass();
        SqueezeColor = Color.Black;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0067, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00dd, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0120, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SqueezeEffect(final com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel.Factory r16, final kotlin.jvm.functions.Function0 r17, androidx.compose.ui.Modifier.Companion r18, androidx.compose.runtime.Composer r19, final int r20) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.topwindoweffects.ui.compose.SqueezeEffectKt.SqueezeEffect(com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel$Factory, kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier$Companion, androidx.compose.runtime.Composer, int):void");
    }

    public static final void drawTransform(DrawScope drawScope, float f, float f2, float f3, VectorPainter vectorPainter) {
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = drawContext.transform;
            float[] m481constructorimpl$default = Matrix.m481constructorimpl$default();
            Matrix.m488translateimpl(f, f2, m481constructorimpl$default);
            if (f3 != 0.0f) {
                Matrix.m485rotateZimpl(f3, m481constructorimpl$default);
            }
            ((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).getCanvas().mo425concat58bKbWc(m481constructorimpl$default);
            vectorPainter.m562drawx_KDEd0(drawScope, vectorPainter.mo561getIntrinsicSizeNHjbRc(), 1.0f, null);
        } finally {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
        }
    }
}
