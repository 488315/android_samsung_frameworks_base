package com.android.compose.animation.scene.content.state;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.material3.MotionScheme$Companion$standard$1;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class TransitionState$Transition$interruptionProgress$create$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animatable;
    final /* synthetic */ SceneTransitionLayoutImpl $layoutImpl;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransitionState$Transition$interruptionProgress$create$1(Animatable<Float, AnimationVector1D> animatable, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Continuation continuation) {
        super(2, continuation);
        this.$animatable = animatable;
        this.$layoutImpl = sceneTransitionLayoutImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TransitionState$Transition$interruptionProgress$create$1(this.$animatable, this.$layoutImpl, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TransitionState$Transition$interruptionProgress$create$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable<Float, AnimationVector1D> animatable = this.$animatable;
            Float f = new Float(0.0f);
            SpringSpec springSpec = ((MotionScheme$Companion$standard$1) this.$layoutImpl.state.motionScheme).fastEffectsSpec;
            this.label = 1;
            if (Animatable.animateTo$default(animatable, f, springSpec, null, null, this, 12) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
