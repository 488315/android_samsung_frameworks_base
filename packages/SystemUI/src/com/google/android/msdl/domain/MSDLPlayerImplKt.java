package com.google.android.msdl.domain;

import android.os.VibrationEffect;
import com.google.android.msdl.data.model.HapticComposition;
import com.google.android.msdl.data.model.HapticCompositionPrimitive;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class MSDLPlayerImplKt {
    public static VibrationEffect composeIntoVibrationEffect$default(HapticComposition hapticComposition, Float f, int i) {
        if ((i & 1) != 0) {
            f = null;
        }
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        for (HapticCompositionPrimitive hapticCompositionPrimitive : hapticComposition.primitives) {
            startComposition.addPrimitive(hapticCompositionPrimitive.primitiveId, f != null ? f.floatValue() : hapticCompositionPrimitive.scale, hapticCompositionPrimitive.delayMillis);
        }
        return startComposition.compose();
    }
}
