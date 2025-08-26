package com.google.android.msdl.domain;

import android.os.VibrationEffect;
import com.google.android.msdl.data.model.HapticComposition;
import com.google.android.msdl.data.model.HapticCompositionPrimitive;

/* loaded from: classes4.dex */
public abstract class MSDLPlayerImplKt {
    public static VibrationEffect composeIntoVibrationEffect$default(HapticComposition hapticComposition, Float f, int i) {
        if ((i & 1) != 0) {
            f = null;
        }
        VibrationEffect.Composition compositionStartComposition = VibrationEffect.startComposition();
        for (HapticCompositionPrimitive hapticCompositionPrimitive : hapticComposition.primitives) {
            compositionStartComposition.addPrimitive(hapticCompositionPrimitive.primitiveId, f != null ? f.floatValue() : hapticCompositionPrimitive.scale, hapticCompositionPrimitive.delayMillis);
        }
        return compositionStartComposition.compose();
    }
}
