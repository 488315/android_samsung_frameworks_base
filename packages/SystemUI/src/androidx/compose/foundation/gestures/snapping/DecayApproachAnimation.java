package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.gestures.ScrollScope;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DecayApproachAnimation implements ApproachAnimation<Float, AnimationVector1D> {
    public final DecayAnimationSpec decayAnimationSpec;

    public DecayApproachAnimation(DecayAnimationSpec<Float> decayAnimationSpec) {
        this.decayAnimationSpec = decayAnimationSpec;
    }

    @Override // androidx.compose.foundation.gestures.snapping.ApproachAnimation
    public final Object approachAnimation(ScrollScope scrollScope, Object obj, Object obj2, Function1 function1, Continuation continuation) {
        Object access$animateDecay = SnapFlingBehaviorKt.access$animateDecay(scrollScope, ((Number) obj).floatValue(), AnimationStateKt.AnimationState$default(0.0f, ((Number) obj2).floatValue(), 28), this.decayAnimationSpec, function1, (ContinuationImpl) continuation);
        return access$animateDecay == CoroutineSingletons.COROUTINE_SUSPENDED ? access$animateDecay : (AnimationResult) access$animateDecay;
    }
}
