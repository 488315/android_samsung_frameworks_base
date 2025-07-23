package androidx.compose.material.ripple;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.interaction.Interaction;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class StateLayer {
    public final boolean bounded;
    public Interaction currentInteraction;
    public final Function0 rippleAlpha;
    public final Animatable animatedAlpha = AnimatableKt.Animatable(0.0f, 0.01f);
    public final List interactions = new ArrayList();

    public StateLayer(boolean z, Function0 function0) {
        this.bounded = z;
        this.rippleAlpha = function0;
    }
}
