package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.foundation.gestures.ScrollScope;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class TargetApproachAnimation implements ApproachAnimation<Float, AnimationVector1D> {
    public final AnimationSpec animationSpec;

    public TargetApproachAnimation(AnimationSpec<Float> animationSpec) {
        this.animationSpec = animationSpec;
    }

    @Override // androidx.compose.foundation.gestures.snapping.ApproachAnimation
    public final Object approachAnimation(ScrollScope scrollScope, Object obj, Object obj2, Function1 function1, Continuation continuation) {
        float fFloatValue = ((Number) obj).floatValue();
        float fFloatValue2 = ((Number) obj2).floatValue();
        Object objAccess$animateWithTarget = SnapFlingBehaviorKt.access$animateWithTarget(scrollScope, Math.signum(fFloatValue2) * Math.abs(fFloatValue), fFloatValue, AnimationStateKt.AnimationState$default(0.0f, fFloatValue2, 28), this.animationSpec, function1, (ContinuationImpl) continuation);
        return objAccess$animateWithTarget == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$animateWithTarget : (AnimationResult) objAccess$animateWithTarget;
    }
}
