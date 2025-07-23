package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.foundation.gestures.ScrollScope;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TargetApproachAnimation implements ApproachAnimation<Float, AnimationVector1D> {
    public final AnimationSpec animationSpec;

    public TargetApproachAnimation(AnimationSpec<Float> animationSpec) {
        this.animationSpec = animationSpec;
    }

    @Override // androidx.compose.foundation.gestures.snapping.ApproachAnimation
    public final Object approachAnimation(ScrollScope scrollScope, Object obj, Object obj2, Function1 function1, Continuation continuation) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj2).floatValue();
        Object access$animateWithTarget = SnapFlingBehaviorKt.access$animateWithTarget(scrollScope, Math.signum(floatValue2) * Math.abs(floatValue), floatValue, AnimationStateKt.AnimationState$default(0.0f, floatValue2, 28), this.animationSpec, function1, (ContinuationImpl) continuation);
        return access$animateWithTarget == CoroutineSingletons.COROUTINE_SUSPENDED ? access$animateWithTarget : (AnimationResult) access$animateWithTarget;
    }
}
