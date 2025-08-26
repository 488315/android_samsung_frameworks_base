package com.android.systemui.shared.clocks;

import com.android.systemui.animation.TextAnimator;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class AnimatableClockView$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ AnimatableClockView f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TextAnimator.PositionedGlyph positionedGlyph = (TextAnimator.PositionedGlyph) obj;
        ((Float) obj2).getClass();
        String str = AnimatableClockView.TAG;
        int glyphIndex = positionedGlyph.getGlyphIndex() + (positionedGlyph.lineNo * 2);
        AnimatableClockView animatableClockView = this.f$0;
        if (glyphIndex < ((ArrayList) animatableClockView.glyphOffsets).size()) {
            positionedGlyph.x = ((Number) ((ArrayList) animatableClockView.glyphOffsets).get(glyphIndex)).floatValue() + positionedGlyph.x;
        }
        return Unit.INSTANCE;
    }
}
