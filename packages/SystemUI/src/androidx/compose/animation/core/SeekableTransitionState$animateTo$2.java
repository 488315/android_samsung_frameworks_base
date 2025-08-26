package androidx.compose.animation.core;

import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;

/* loaded from: classes.dex */
final class SeekableTransitionState$animateTo$2 extends SuspendLambda implements Function1 {
    final /* synthetic */ FiniteAnimationSpec<Float> $animationSpec;
    final /* synthetic */ Object $targetState;
    final /* synthetic */ Transition<Object> $transition;
    int label;
    final /* synthetic */ SeekableTransitionState<Object> this$0;

    /* renamed from: androidx.compose.animation.core.SeekableTransitionState$animateTo$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FiniteAnimationSpec<Float> $animationSpec;
        final /* synthetic */ Object $targetState;
        final /* synthetic */ Transition<Object> $transition;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ SeekableTransitionState<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SeekableTransitionState<Object> seekableTransitionState, Object obj, Transition<Object> transition, FiniteAnimationSpec<Float> finiteAnimationSpec, Continuation continuation) {
            super(2, continuation);
            this.this$0 = seekableTransitionState;
            this.$targetState = obj;
            this.$transition = transition;
            this.$animationSpec = finiteAnimationSpec;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$targetState, this.$transition, this.$animationSpec, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:83:0x01e7, code lost:
        
            if (androidx.compose.animation.core.SeekableTransitionState.access$waitForComposition(r2, r20) != r1) goto L85;
         */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00db  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00ed  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            SeekableTransitionState<Object> seekableTransitionState;
            Mutex mutex;
            Object objAnimateOneFrame;
            SeekableTransitionState<Object> seekableTransitionState2;
            VectorizedFiniteAnimationSpec vectorizedFiniteAnimationSpecVectorize;
            AnimationVector1D animationVector1D;
            long jRoundToLong;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Object value = ((SnapshotMutableStateImpl) this.this$0.targetState$delegate).getValue();
                    if (!Intrinsics.areEqual(this.$targetState, value)) {
                        SeekableTransitionState.access$moveAnimationToInitialState(this.this$0);
                        this.this$0.setFraction(0.0f);
                        this.$transition.updateTarget$animation_core(this.$targetState);
                        this.$transition.setPlayTimeNanos(0L);
                        this.this$0.setCurrentState$animation_core(value);
                        ((SnapshotMutableStateImpl) this.this$0.targetState$delegate).setValue(this.$targetState);
                    }
                    seekableTransitionState = this.this$0;
                    MutexImpl mutexImpl = seekableTransitionState.compositionContinuationMutex;
                    this.L$0 = mutexImpl;
                    this.L$1 = seekableTransitionState;
                    this.label = 1;
                    if (mutexImpl.lock(this) != coroutineSingletons) {
                        mutex = mutexImpl;
                    }
                    return coroutineSingletons;
                }
                if (i == 1) {
                    seekableTransitionState = (SeekableTransitionState) this.L$1;
                    mutex = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i == 2) {
                        ResultKt.throwOnFailure(obj);
                        seekableTransitionState2 = this.this$0;
                        this.label = 3;
                        if (SeekableTransitionState.access$waitForCompositionAfterTargetStateChange(seekableTransitionState2, this) != coroutineSingletons) {
                            if (!Intrinsics.areEqual(((SnapshotMutableStateImpl) this.this$0.currentState$delegate).getValue(), this.$targetState)) {
                            }
                            return Unit.INSTANCE;
                        }
                        return coroutineSingletons;
                    }
                    if (i == 3) {
                        ResultKt.throwOnFailure(obj);
                        if (!Intrinsics.areEqual(((SnapshotMutableStateImpl) this.this$0.currentState$delegate).getValue(), this.$targetState)) {
                            if (((SnapshotMutableFloatStateImpl) this.this$0.fraction$delegate).getFloatValue() < 1.0f) {
                                SeekableTransitionState.SeekingAnimationState seekingAnimationState = this.this$0.currentAnimation;
                                FiniteAnimationSpec<Float> finiteAnimationSpec = this.$animationSpec;
                                if (finiteAnimationSpec != null) {
                                    FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                    vectorizedFiniteAnimationSpecVectorize = finiteAnimationSpec.vectorize(VectorConvertersKt.FloatToVector);
                                } else {
                                    vectorizedFiniteAnimationSpecVectorize = null;
                                }
                                if (seekingAnimationState == null || !Intrinsics.areEqual(vectorizedFiniteAnimationSpecVectorize, seekingAnimationState.animationSpec)) {
                                    VectorizedFiniteAnimationSpec vectorizedFiniteAnimationSpec = seekingAnimationState != null ? seekingAnimationState.animationSpec : null;
                                    if (vectorizedFiniteAnimationSpec != null) {
                                        long j = seekingAnimationState.progressNanos;
                                        SeekableTransitionState.Companion.getClass();
                                        AnimationVector1D animationVector1D2 = SeekableTransitionState.Target1;
                                        AnimationVector1D animationVector1D3 = seekingAnimationState.initialVelocity;
                                        if (animationVector1D3 == null) {
                                            animationVector1D3 = SeekableTransitionState.ZeroVelocity;
                                        }
                                        animationVector1D = (AnimationVector1D) vectorizedFiniteAnimationSpec.getVelocityFromNanos(j, seekingAnimationState.start, animationVector1D2, animationVector1D3);
                                    } else if (seekingAnimationState == null || seekingAnimationState.progressNanos == 0) {
                                        SeekableTransitionState.Companion.getClass();
                                        animationVector1D = SeekableTransitionState.ZeroVelocity;
                                    } else {
                                        long j2 = seekingAnimationState.durationNanos;
                                        if (j2 == Long.MIN_VALUE) {
                                            j2 = this.this$0.totalDurationNanos;
                                        }
                                        float f = j2 / 1.0E9f;
                                        if (f <= 0.0f) {
                                            SeekableTransitionState.Companion.getClass();
                                            animationVector1D = SeekableTransitionState.ZeroVelocity;
                                        } else {
                                            animationVector1D = new AnimationVector1D(1.0f / f);
                                        }
                                    }
                                    if (seekingAnimationState == null) {
                                        seekingAnimationState = new SeekableTransitionState.SeekingAnimationState();
                                    }
                                    seekingAnimationState.animationSpec = vectorizedFiniteAnimationSpecVectorize;
                                    seekingAnimationState.isComplete = false;
                                    seekingAnimationState.value = ((SnapshotMutableFloatStateImpl) this.this$0.fraction$delegate).getFloatValue();
                                    float floatValue = ((SnapshotMutableFloatStateImpl) this.this$0.fraction$delegate).getFloatValue();
                                    AnimationVector1D animationVector1D4 = seekingAnimationState.start;
                                    animationVector1D4.set$animation_core(floatValue, 0);
                                    long j3 = this.this$0.totalDurationNanos;
                                    seekingAnimationState.durationNanos = j3;
                                    seekingAnimationState.progressNanos = 0L;
                                    seekingAnimationState.initialVelocity = animationVector1D;
                                    if (vectorizedFiniteAnimationSpecVectorize != null) {
                                        SeekableTransitionState.Companion.getClass();
                                        jRoundToLong = vectorizedFiniteAnimationSpecVectorize.getDurationNanos(animationVector1D4, SeekableTransitionState.Target1, animationVector1D);
                                    } else {
                                        jRoundToLong = MathKt__MathJVMKt.roundToLong((1.0d - ((SnapshotMutableFloatStateImpl) r4.fraction$delegate).getFloatValue()) * j3);
                                    }
                                    seekingAnimationState.animationSpecDuration = jRoundToLong;
                                    this.this$0.currentAnimation = seekingAnimationState;
                                }
                            }
                            SeekableTransitionState<Object> seekableTransitionState3 = this.this$0;
                            this.L$0 = null;
                            this.L$1 = null;
                            this.label = 4;
                            if (SeekableTransitionState.access$runAnimations(seekableTransitionState3, this) != coroutineSingletons) {
                                this.this$0.setCurrentState$animation_core(this.$targetState);
                                SeekableTransitionState<Object> seekableTransitionState4 = this.this$0;
                                this.label = 5;
                            }
                            return coroutineSingletons;
                        }
                        return Unit.INSTANCE;
                    }
                    if (i != 4) {
                        if (i != 5) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        SeekableTransitionState<Object> seekableTransitionState5 = this.this$0;
                        SeekableTransitionState.Companion companion = SeekableTransitionState.Companion;
                        seekableTransitionState5.setFraction(0.0f);
                        return Unit.INSTANCE;
                    }
                    ResultKt.throwOnFailure(obj);
                    this.this$0.setCurrentState$animation_core(this.$targetState);
                    SeekableTransitionState<Object> seekableTransitionState42 = this.this$0;
                    this.label = 5;
                }
                Object obj2 = seekableTransitionState.composedTargetState;
                mutex.unlock(null);
                if (!Intrinsics.areEqual(this.$targetState, obj2)) {
                    SeekableTransitionState<Object> seekableTransitionState6 = this.this$0;
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 2;
                    if (seekableTransitionState6.lastFrameTimeNanos == Long.MIN_VALUE) {
                        objAnimateOneFrame = MonotonicFrameClockKt.getMonotonicFrameClock(getContext()).withFrameNanos(seekableTransitionState6.firstFrameLambda, this);
                        if (objAnimateOneFrame != coroutineSingletons) {
                            objAnimateOneFrame = Unit.INSTANCE;
                        }
                    } else {
                        objAnimateOneFrame = seekableTransitionState6.animateOneFrame(this);
                        if (objAnimateOneFrame != coroutineSingletons) {
                            objAnimateOneFrame = Unit.INSTANCE;
                        }
                    }
                    if (objAnimateOneFrame != coroutineSingletons) {
                        seekableTransitionState2 = this.this$0;
                        this.label = 3;
                        if (SeekableTransitionState.access$waitForCompositionAfterTargetStateChange(seekableTransitionState2, this) != coroutineSingletons) {
                        }
                    }
                }
                return coroutineSingletons;
            } catch (Throwable th) {
                mutex.unlock(null);
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekableTransitionState$animateTo$2(Transition<Object> transition, SeekableTransitionState<Object> seekableTransitionState, Object obj, FiniteAnimationSpec<Float> finiteAnimationSpec, Continuation continuation) {
        super(1, continuation);
        this.$transition = transition;
        this.this$0 = seekableTransitionState;
        this.$targetState = obj;
        this.$animationSpec = finiteAnimationSpec;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new SeekableTransitionState$animateTo$2(this.$transition, this.this$0, this.$targetState, this.$animationSpec, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((SeekableTransitionState$animateTo$2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$targetState, this.$transition, this.$animationSpec, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$transition.onTransitionEnd$animation_core();
        return Unit.INSTANCE;
    }
}
