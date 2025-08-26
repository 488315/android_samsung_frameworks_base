package androidx.compose.material.ripple;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.interaction.Interaction;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;

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
