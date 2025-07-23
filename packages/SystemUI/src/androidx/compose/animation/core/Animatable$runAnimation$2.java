package androidx.compose.animation.core;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class Animatable$runAnimation$2 extends SuspendLambda implements Function1 {
    final /* synthetic */ Animation<Object, AnimationVector> $animation;
    final /* synthetic */ Function1 $block;
    final /* synthetic */ Object $initialVelocity;
    final /* synthetic */ long $startTime;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ Animatable<Object, AnimationVector> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Animatable$runAnimation$2(Animatable<Object, AnimationVector> animatable, Object obj, Animation<Object, AnimationVector> animation, long j, Function1 function1, Continuation continuation) {
        super(1, continuation);
        this.this$0 = animatable;
        this.$initialVelocity = obj;
        this.$animation = animation;
        this.$startTime = j;
        this.$block = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new Animatable$runAnimation$2(this.this$0, this.$initialVelocity, this.$animation, this.$startTime, this.$block, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((Animatable$runAnimation$2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AnimationState animationState;
        Ref$BooleanRef ref$BooleanRef;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable<Object, AnimationVector> animatable = this.this$0;
                animatable.internalState.velocityVector = (AnimationVector) ((TwoWayConverterImpl) animatable.typeConverter).convertToVector.mo779invoke(this.$initialVelocity);
                ((SnapshotMutableStateImpl) this.this$0.targetValue$delegate).setValue(this.$animation.getTargetValue());
                ((SnapshotMutableStateImpl) this.this$0.isRunning$delegate).setValue(Boolean.TRUE);
                AnimationState animationState2 = this.this$0.internalState;
                final AnimationState animationState3 = new AnimationState(animationState2.typeConverter, ((SnapshotMutableStateImpl) animationState2.value$delegate).getValue(), AnimationVectorsKt.copy(animationState2.velocityVector), animationState2.lastFrameTimeNanos, Long.MIN_VALUE, animationState2.isRunning);
                final Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                Animation<Object, AnimationVector> animation = this.$animation;
                long j = this.$startTime;
                final Animatable<Object, AnimationVector> animatable2 = this.this$0;
                final Function1 function1 = this.$block;
                Function1 function12 = new Function1() { // from class: androidx.compose.animation.core.Animatable$runAnimation$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        AnimationScope animationScope = (AnimationScope) obj2;
                        SuspendAnimationKt.updateState(animationScope, animatable2.internalState);
                        Animatable<Object, AnimationVector> animatable3 = animatable2;
                        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) animationScope.value$delegate;
                        Object clampToBounds = animatable3.clampToBounds(snapshotMutableStateImpl.getValue());
                        if (Intrinsics.areEqual(clampToBounds, snapshotMutableStateImpl.getValue())) {
                            Function1 function13 = function1;
                            if (function13 != null) {
                                function13.mo779invoke(animatable2);
                            }
                        } else {
                            ((SnapshotMutableStateImpl) animatable2.internalState.value$delegate).setValue(clampToBounds);
                            ((SnapshotMutableStateImpl) animationState3.value$delegate).setValue(clampToBounds);
                            Function1 function14 = function1;
                            if (function14 != null) {
                                function14.mo779invoke(animatable2);
                            }
                            animationScope.cancelAnimation();
                            ref$BooleanRef2.element = true;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = animationState3;
                this.L$1 = ref$BooleanRef2;
                this.label = 1;
                if (SuspendAnimationKt.animate(animationState3, animation, j, function12, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                animationState = animationState3;
                ref$BooleanRef = ref$BooleanRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ref$BooleanRef = (Ref$BooleanRef) this.L$1;
                animationState = (AnimationState) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            AnimationEndReason animationEndReason = ref$BooleanRef.element ? AnimationEndReason.BoundReached : AnimationEndReason.Finished;
            Animatable.access$endAnimation(this.this$0);
            return new AnimationResult(animationState, animationEndReason);
        } catch (CancellationException e) {
            Animatable.access$endAnimation(this.this$0);
            throw e;
        }
    }
}
