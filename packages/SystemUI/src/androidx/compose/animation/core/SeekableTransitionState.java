package androidx.compose.animation.core;

import androidx.collection.MutableObjectList;
import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import androidx.navigation.NavBackStackEntry;
import java.util.Arrays;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public final class SeekableTransitionState<S> extends TransitionState<S> {
    public final Function1 animateOneFrameLambda;
    public Object composedTargetState;
    public CancellableContinuationImpl compositionContinuation;
    public final MutexImpl compositionContinuationMutex;
    public SeekingAnimationState currentAnimation;
    public final MutableState currentState$delegate;
    public float durationScale;
    public final Function1 firstFrameLambda;
    public final MutableFloatState fraction$delegate;
    public final MutableObjectList initialValueAnimations;
    public long lastFrameTimeNanos;
    public final MutatorMutex mutatorMutex;
    public final Function0 recalculateTotalDurationNanos;
    public final MutableState targetState$delegate;
    public long totalDurationNanos;
    public Transition transition;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    public static final AnimationVector1D ZeroVelocity = new AnimationVector1D(0.0f);
    public static final AnimationVector1D Target1 = new AnimationVector1D(1.0f);

    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class SeekingAnimationState {
        public VectorizedFiniteAnimationSpec animationSpec;
        public long animationSpecDuration;
        public long durationNanos;
        public AnimationVector1D initialVelocity;
        public boolean isComplete;
        public long progressNanos;
        public final AnimationVector1D start = new AnimationVector1D(0.0f);
        public float value;

        public final String toString() {
            return "progress nanos: " + this.progressNanos + ", animationSpec: " + this.animationSpec + ", isComplete: " + this.isComplete + ", value: " + this.value + ", start: " + this.start + ", initialVelocity: " + this.initialVelocity + ", durationNanos: " + this.durationNanos + ", animationSpecDuration: " + this.animationSpecDuration;
        }
    }

    /* renamed from: androidx.compose.animation.core.SeekableTransitionState$seekTo$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function1 {
        final /* synthetic */ float $fraction;
        final /* synthetic */ Object $oldTargetState;
        final /* synthetic */ Object $targetState;
        final /* synthetic */ Transition<Object> $transition;
        int label;
        final /* synthetic */ SeekableTransitionState<Object> this$0;

        /* renamed from: androidx.compose.animation.core.SeekableTransitionState$seekTo$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ float $fraction;
            final /* synthetic */ Object $oldTargetState;
            final /* synthetic */ Object $targetState;
            final /* synthetic */ Transition<Object> $transition;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* renamed from: androidx.compose.animation.core.SeekableTransitionState$seekTo$3$1$1, reason: invalid class name and collision with other inner class name */
            final class C00001 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SeekableTransitionState<Object> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00001(SeekableTransitionState<Object> seekableTransitionState, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = seekableTransitionState;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00001(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00001) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        SeekableTransitionState<Object> seekableTransitionState = this.this$0;
                        this.label = 1;
                        if (SeekableTransitionState.access$runAnimations(seekableTransitionState, this) == coroutineSingletons) {
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Object obj, Object obj2, SeekableTransitionState<Object> seekableTransitionState, Transition<Object> transition, float f, Continuation continuation) {
                super(2, continuation);
                this.$targetState = obj;
                this.$oldTargetState = obj2;
                this.this$0 = seekableTransitionState;
                this.$transition = transition;
                this.$fraction = f;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$targetState, this.$oldTargetState, this.this$0, this.$transition, this.$fraction, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    if (Intrinsics.areEqual(this.$targetState, this.$oldTargetState)) {
                        SeekableTransitionState<Object> seekableTransitionState = this.this$0;
                        seekableTransitionState.currentAnimation = null;
                        if (Intrinsics.areEqual(((SnapshotMutableStateImpl) seekableTransitionState.currentState$delegate).getValue(), this.$targetState)) {
                            return Unit.INSTANCE;
                        }
                    } else {
                        SeekableTransitionState.access$moveAnimationToInitialState(this.this$0);
                    }
                    if (!Intrinsics.areEqual(this.$targetState, this.$oldTargetState)) {
                        this.$transition.updateTarget$animation_core(this.$targetState);
                        this.$transition.setPlayTimeNanos(0L);
                        SeekableTransitionState<Object> seekableTransitionState2 = this.this$0;
                        ((SnapshotMutableStateImpl) seekableTransitionState2.targetState$delegate).setValue(this.$targetState);
                        this.$transition.resetAnimationFraction$animation_core(this.$fraction);
                    }
                    SeekableTransitionState<Object> seekableTransitionState3 = this.this$0;
                    float f = this.$fraction;
                    Companion companion = SeekableTransitionState.Companion;
                    seekableTransitionState3.setFraction(f);
                    if (this.this$0.initialValueAnimations.isNotEmpty()) {
                        BuildersKt.launch$default(coroutineScope, null, null, new C00001(this.this$0, null), 3);
                    } else {
                        this.this$0.lastFrameTimeNanos = Long.MIN_VALUE;
                    }
                    SeekableTransitionState<Object> seekableTransitionState4 = this.this$0;
                    this.label = 1;
                    if (SeekableTransitionState.access$waitForCompositionAfterTargetStateChange(seekableTransitionState4, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                SeekableTransitionState<Object> seekableTransitionState5 = this.this$0;
                Companion companion2 = SeekableTransitionState.Companion;
                seekableTransitionState5.seekToFraction();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Object obj, Object obj2, SeekableTransitionState<Object> seekableTransitionState, Transition<Object> transition, float f, Continuation continuation) {
            super(1, continuation);
            this.$targetState = obj;
            this.$oldTargetState = obj2;
            this.this$0 = seekableTransitionState;
            this.$transition = transition;
            this.$fraction = f;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass3(this.$targetState, this.$oldTargetState, this.this$0, this.$transition, this.$fraction, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$targetState, this.$oldTargetState, this.this$0, this.$transition, this.$fraction, null);
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
            return Unit.INSTANCE;
        }
    }

    public SeekableTransitionState(S s) {
        super(null);
        this.targetState$delegate = SnapshotStateKt.mutableStateOf$default(s);
        this.currentState$delegate = SnapshotStateKt.mutableStateOf$default(s);
        this.composedTargetState = s;
        this.recalculateTotalDurationNanos = new Function0(this) { // from class: androidx.compose.animation.core.SeekableTransitionState$recalculateTotalDurationNanos$1
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SeekableTransitionState<Object> seekableTransitionState = this.this$0;
                Transition transition = seekableTransitionState.transition;
                seekableTransitionState.totalDurationNanos = transition != null ? ((Number) transition.totalDurationNanos$delegate.getValue()).longValue() : 0L;
                return Unit.INSTANCE;
            }
        };
        this.fraction$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
        this.compositionContinuationMutex = MutexKt.Mutex$default();
        this.mutatorMutex = new MutatorMutex();
        this.lastFrameTimeNanos = Long.MIN_VALUE;
        this.initialValueAnimations = new MutableObjectList(0, 1, null);
        this.firstFrameLambda = new Function1(this) { // from class: androidx.compose.animation.core.SeekableTransitionState$firstFrameLambda$1
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                this.this$0.lastFrameTimeNanos = ((Number) obj).longValue();
                return Unit.INSTANCE;
            }
        };
        this.animateOneFrameLambda = new Function1(this) { // from class: androidx.compose.animation.core.SeekableTransitionState$animateOneFrameLambda$1
            final /* synthetic */ SeekableTransitionState<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long jLongValue = ((Number) obj).longValue();
                SeekableTransitionState<Object> seekableTransitionState = this.this$0;
                long j = jLongValue - seekableTransitionState.lastFrameTimeNanos;
                seekableTransitionState.lastFrameTimeNanos = jLongValue;
                long jRoundToLong = MathKt__MathJVMKt.roundToLong(j / seekableTransitionState.durationScale);
                if (this.this$0.initialValueAnimations.isNotEmpty()) {
                    SeekableTransitionState<Object> seekableTransitionState2 = this.this$0;
                    MutableObjectList mutableObjectList = seekableTransitionState2.initialValueAnimations;
                    Object[] objArr = mutableObjectList.content;
                    int i = mutableObjectList._size;
                    int i2 = 0;
                    for (int i3 = 0; i3 < i; i3++) {
                        SeekableTransitionState.SeekingAnimationState seekingAnimationState = (SeekableTransitionState.SeekingAnimationState) objArr[i3];
                        SeekableTransitionState.access$recalculateAnimationValue(seekableTransitionState2, seekingAnimationState, jRoundToLong);
                        seekingAnimationState.isComplete = true;
                    }
                    Transition transition = this.this$0.transition;
                    if (transition != null) {
                        transition.updateInitialValues$animation_core();
                    }
                    MutableObjectList mutableObjectList2 = this.this$0.initialValueAnimations;
                    int i4 = mutableObjectList2._size;
                    Object[] objArr2 = mutableObjectList2.content;
                    IntRange intRangeUntil = RangesKt___RangesKt.until(0, i4);
                    int i5 = intRangeUntil.first;
                    int i6 = intRangeUntil.last;
                    if (i5 <= i6) {
                        while (true) {
                            objArr2[i5 - i2] = objArr2[i5];
                            if (((SeekableTransitionState.SeekingAnimationState) objArr2[i5]).isComplete) {
                                i2++;
                            }
                            if (i5 == i6) {
                                break;
                            }
                            i5++;
                        }
                    }
                    Arrays.fill(objArr2, i4 - i2, i4, (Object) null);
                    mutableObjectList2._size -= i2;
                }
                SeekableTransitionState<Object> seekableTransitionState3 = this.this$0;
                SeekableTransitionState.SeekingAnimationState seekingAnimationState2 = seekableTransitionState3.currentAnimation;
                if (seekingAnimationState2 != null) {
                    seekingAnimationState2.durationNanos = seekableTransitionState3.totalDurationNanos;
                    SeekableTransitionState.access$recalculateAnimationValue(seekableTransitionState3, seekingAnimationState2, jRoundToLong);
                    this.this$0.setFraction(seekingAnimationState2.value);
                    if (seekingAnimationState2.value == 1.0f) {
                        this.this$0.currentAnimation = null;
                    }
                    this.this$0.seekToFraction();
                }
                return Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$moveAnimationToInitialState(SeekableTransitionState seekableTransitionState) {
        Transition transition = seekableTransitionState.transition;
        if (transition == null) {
            return;
        }
        SeekingAnimationState seekingAnimationState = seekableTransitionState.currentAnimation;
        if (seekingAnimationState == null) {
            if (seekableTransitionState.totalDurationNanos > 0) {
                SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) seekableTransitionState.fraction$delegate;
                if (snapshotMutableFloatStateImpl.getFloatValue() == 1.0f || Intrinsics.areEqual(((SnapshotMutableStateImpl) seekableTransitionState.currentState$delegate).getValue(), ((SnapshotMutableStateImpl) seekableTransitionState.targetState$delegate).getValue())) {
                    seekingAnimationState = null;
                } else {
                    SeekingAnimationState seekingAnimationState2 = new SeekingAnimationState();
                    seekingAnimationState2.value = snapshotMutableFloatStateImpl.getFloatValue();
                    long j = seekableTransitionState.totalDurationNanos;
                    seekingAnimationState2.durationNanos = j;
                    seekingAnimationState2.animationSpecDuration = MathKt__MathJVMKt.roundToLong((1.0d - snapshotMutableFloatStateImpl.getFloatValue()) * j);
                    seekingAnimationState2.start.set$animation_core(snapshotMutableFloatStateImpl.getFloatValue(), 0);
                    seekingAnimationState = seekingAnimationState2;
                }
            }
        }
        if (seekingAnimationState != null) {
            seekingAnimationState.durationNanos = seekableTransitionState.totalDurationNanos;
            seekableTransitionState.initialValueAnimations.add(seekingAnimationState);
            transition.setInitialAnimations$animation_core(seekingAnimationState);
        }
        seekableTransitionState.currentAnimation = null;
    }

    public static final void access$recalculateAnimationValue(SeekableTransitionState seekableTransitionState, SeekingAnimationState seekingAnimationState, long j) {
        seekableTransitionState.getClass();
        long j2 = seekingAnimationState.progressNanos + j;
        seekingAnimationState.progressNanos = j2;
        long j3 = seekingAnimationState.animationSpecDuration;
        if (j2 >= j3) {
            seekingAnimationState.value = 1.0f;
            return;
        }
        VectorizedFiniteAnimationSpec vectorizedFiniteAnimationSpec = seekingAnimationState.animationSpec;
        if (vectorizedFiniteAnimationSpec == null) {
            float f = j2 / j3;
            seekingAnimationState.value = (f * 1.0f) + ((1 - f) * seekingAnimationState.start.get$animation_core(0));
            return;
        }
        AnimationVector1D animationVector1D = seekingAnimationState.initialVelocity;
        if (animationVector1D == null) {
            animationVector1D = ZeroVelocity;
        }
        seekingAnimationState.value = RangesKt___RangesKt.coerceIn(((AnimationVector1D) vectorizedFiniteAnimationSpec.getValueFromNanos(j2, seekingAnimationState.start, Target1, animationVector1D)).get$animation_core(0), 0.0f, 1.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$runAnimations(SeekableTransitionState seekableTransitionState, ContinuationImpl continuationImpl) {
        SeekableTransitionState$runAnimations$1 seekableTransitionState$runAnimations$1;
        seekableTransitionState.getClass();
        if (continuationImpl instanceof SeekableTransitionState$runAnimations$1) {
            seekableTransitionState$runAnimations$1 = (SeekableTransitionState$runAnimations$1) continuationImpl;
            int i = seekableTransitionState$runAnimations$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                seekableTransitionState$runAnimations$1.label = i - Integer.MIN_VALUE;
            } else {
                seekableTransitionState$runAnimations$1 = new SeekableTransitionState$runAnimations$1(seekableTransitionState, continuationImpl);
            }
        }
        Object obj = seekableTransitionState$runAnimations$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = seekableTransitionState$runAnimations$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MutableObjectList mutableObjectList = seekableTransitionState.initialValueAnimations;
            if (mutableObjectList.isEmpty() && seekableTransitionState.currentAnimation == null) {
                return Unit.INSTANCE;
            }
            if (SuspendAnimationKt.getDurationScale(seekableTransitionState$runAnimations$1.getContext()) != 0.0f) {
                if (seekableTransitionState.lastFrameTimeNanos == Long.MIN_VALUE) {
                    Function1 function1 = seekableTransitionState.firstFrameLambda;
                    seekableTransitionState$runAnimations$1.L$0 = seekableTransitionState;
                    seekableTransitionState$runAnimations$1.label = 1;
                    if (MonotonicFrameClockKt.getMonotonicFrameClock(seekableTransitionState$runAnimations$1.getContext()).withFrameNanos(function1, seekableTransitionState$runAnimations$1) != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            Transition transition = seekableTransitionState.transition;
            if (transition != null) {
                transition.clearInitialAnimations$animation_core();
            }
            mutableObjectList.clear();
            if (seekableTransitionState.currentAnimation != null) {
                seekableTransitionState.currentAnimation = null;
                seekableTransitionState.setFraction(1.0f);
                seekableTransitionState.seekToFraction();
            }
            seekableTransitionState.lastFrameTimeNanos = Long.MIN_VALUE;
            return Unit.INSTANCE;
        }
        if (i2 != 1 && i2 != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        seekableTransitionState = (SeekableTransitionState) seekableTransitionState$runAnimations$1.L$0;
        ResultKt.throwOnFailure(obj);
        do {
            if (!seekableTransitionState.initialValueAnimations.isNotEmpty() && seekableTransitionState.currentAnimation == null) {
                seekableTransitionState.lastFrameTimeNanos = Long.MIN_VALUE;
                return Unit.INSTANCE;
            }
            seekableTransitionState$runAnimations$1.L$0 = seekableTransitionState;
            seekableTransitionState$runAnimations$1.label = 2;
        } while (seekableTransitionState.animateOneFrame(seekableTransitionState$runAnimations$1) != coroutineSingletons);
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForComposition(SeekableTransitionState seekableTransitionState, ContinuationImpl continuationImpl) {
        SeekableTransitionState$waitForComposition$1 seekableTransitionState$waitForComposition$1;
        Object value;
        SeekableTransitionState seekableTransitionState2;
        Object obj;
        seekableTransitionState.getClass();
        if (continuationImpl instanceof SeekableTransitionState$waitForComposition$1) {
            seekableTransitionState$waitForComposition$1 = (SeekableTransitionState$waitForComposition$1) continuationImpl;
            int i = seekableTransitionState$waitForComposition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                seekableTransitionState$waitForComposition$1.label = i - Integer.MIN_VALUE;
            } else {
                seekableTransitionState$waitForComposition$1 = new SeekableTransitionState$waitForComposition$1(seekableTransitionState, continuationImpl);
            }
        }
        Object obj2 = seekableTransitionState$waitForComposition$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = seekableTransitionState$waitForComposition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            value = ((SnapshotMutableStateImpl) seekableTransitionState.targetState$delegate).getValue();
            MutexImpl mutexImpl = seekableTransitionState.compositionContinuationMutex;
            seekableTransitionState$waitForComposition$1.L$0 = seekableTransitionState;
            seekableTransitionState$waitForComposition$1.L$1 = value;
            seekableTransitionState$waitForComposition$1.label = 1;
            if (mutexImpl.lock(seekableTransitionState$waitForComposition$1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            obj = seekableTransitionState$waitForComposition$1.L$1;
            seekableTransitionState2 = (SeekableTransitionState) seekableTransitionState$waitForComposition$1.L$0;
            ResultKt.throwOnFailure(obj2);
            if (!Intrinsics.areEqual(obj2, obj)) {
                return Unit.INSTANCE;
            }
            seekableTransitionState2.lastFrameTimeNanos = Long.MIN_VALUE;
            throw new CancellationException("targetState while waiting for composition");
        }
        Object obj3 = seekableTransitionState$waitForComposition$1.L$1;
        SeekableTransitionState seekableTransitionState3 = (SeekableTransitionState) seekableTransitionState$waitForComposition$1.L$0;
        ResultKt.throwOnFailure(obj2);
        value = obj3;
        seekableTransitionState = seekableTransitionState3;
        seekableTransitionState$waitForComposition$1.L$0 = seekableTransitionState;
        seekableTransitionState$waitForComposition$1.L$1 = value;
        seekableTransitionState$waitForComposition$1.label = 2;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(seekableTransitionState$waitForComposition$1), 1);
        cancellableContinuationImpl.initCancellability();
        seekableTransitionState.compositionContinuation = cancellableContinuationImpl;
        seekableTransitionState.compositionContinuationMutex.unlock(null);
        Object result = cancellableContinuationImpl.getResult();
        if (result != coroutineSingletons) {
            seekableTransitionState2 = seekableTransitionState;
            obj = value;
            obj2 = result;
            if (!Intrinsics.areEqual(obj2, obj)) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForCompositionAfterTargetStateChange(SeekableTransitionState seekableTransitionState, ContinuationImpl continuationImpl) {
        SeekableTransitionState$waitForCompositionAfterTargetStateChange$1 seekableTransitionState$waitForCompositionAfterTargetStateChange$1;
        SeekableTransitionState seekableTransitionState2;
        Object obj;
        SeekableTransitionState seekableTransitionState3;
        seekableTransitionState.getClass();
        if (continuationImpl instanceof SeekableTransitionState$waitForCompositionAfterTargetStateChange$1) {
            seekableTransitionState$waitForCompositionAfterTargetStateChange$1 = (SeekableTransitionState$waitForCompositionAfterTargetStateChange$1) continuationImpl;
            int i = seekableTransitionState$waitForCompositionAfterTargetStateChange$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                seekableTransitionState$waitForCompositionAfterTargetStateChange$1.label = i - Integer.MIN_VALUE;
            } else {
                seekableTransitionState$waitForCompositionAfterTargetStateChange$1 = new SeekableTransitionState$waitForCompositionAfterTargetStateChange$1(seekableTransitionState, continuationImpl);
            }
        }
        Object result = seekableTransitionState$waitForCompositionAfterTargetStateChange$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = seekableTransitionState$waitForCompositionAfterTargetStateChange$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(result);
            Object value = ((SnapshotMutableStateImpl) seekableTransitionState.targetState$delegate).getValue();
            MutexImpl mutexImpl = seekableTransitionState.compositionContinuationMutex;
            seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$0 = seekableTransitionState;
            seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$1 = value;
            seekableTransitionState$waitForCompositionAfterTargetStateChange$1.label = 1;
            if (mutexImpl.lock(seekableTransitionState$waitForCompositionAfterTargetStateChange$1) != coroutineSingletons) {
                seekableTransitionState2 = seekableTransitionState;
                obj = value;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            obj = seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$1;
            seekableTransitionState3 = (SeekableTransitionState) seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$0;
            ResultKt.throwOnFailure(result);
            if (!Intrinsics.areEqual(result, obj)) {
                seekableTransitionState3.lastFrameTimeNanos = Long.MIN_VALUE;
                throw new CancellationException("snapTo() was canceled because state was changed to " + result + " instead of " + obj);
            }
            return Unit.INSTANCE;
        }
        obj = seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$1;
        seekableTransitionState2 = (SeekableTransitionState) seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$0;
        ResultKt.throwOnFailure(result);
        boolean zAreEqual = Intrinsics.areEqual(obj, seekableTransitionState2.composedTargetState);
        MutexImpl mutexImpl2 = seekableTransitionState2.compositionContinuationMutex;
        if (zAreEqual) {
            mutexImpl2.unlock(null);
            return Unit.INSTANCE;
        }
        seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$0 = seekableTransitionState2;
        seekableTransitionState$waitForCompositionAfterTargetStateChange$1.L$1 = obj;
        seekableTransitionState$waitForCompositionAfterTargetStateChange$1.label = 2;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(seekableTransitionState$waitForCompositionAfterTargetStateChange$1), 1);
        cancellableContinuationImpl.initCancellability();
        seekableTransitionState2.compositionContinuation = cancellableContinuationImpl;
        mutexImpl2.unlock(null);
        result = cancellableContinuationImpl.getResult();
        if (result != coroutineSingletons) {
            seekableTransitionState3 = seekableTransitionState2;
            if (!Intrinsics.areEqual(result, obj)) {
            }
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }

    public static Object animateTo$default(SeekableTransitionState seekableTransitionState, Object obj, Continuation continuation) {
        Transition transition = seekableTransitionState.transition;
        if (transition == null) {
            return Unit.INSTANCE;
        }
        Object objMutate$default = MutatorMutex.mutate$default(seekableTransitionState.mutatorMutex, new SeekableTransitionState$animateTo$2(transition, seekableTransitionState, obj, null, null), continuation);
        return objMutate$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objMutate$default : Unit.INSTANCE;
    }

    public final Object animateOneFrame(ContinuationImpl continuationImpl) {
        float durationScale = SuspendAnimationKt.getDurationScale(continuationImpl.getContext());
        if (durationScale > 0.0f) {
            this.durationScale = durationScale;
            Object objWithFrameNanos = MonotonicFrameClockKt.getMonotonicFrameClock(continuationImpl.getContext()).withFrameNanos(this.animateOneFrameLambda, continuationImpl);
            return objWithFrameNanos == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithFrameNanos : Unit.INSTANCE;
        }
        Transition transition = this.transition;
        if (transition != null) {
            transition.clearInitialAnimations$animation_core();
        }
        this.initialValueAnimations.clear();
        if (this.currentAnimation != null) {
            this.currentAnimation = null;
            setFraction(1.0f);
            seekToFraction();
        }
        return Unit.INSTANCE;
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final Object getCurrentState() {
        return ((SnapshotMutableStateImpl) this.currentState$delegate).getValue();
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final Object getTargetState() {
        return ((SnapshotMutableStateImpl) this.targetState$delegate).getValue();
    }

    public final Object seekTo(float f, NavBackStackEntry navBackStackEntry, Continuation continuation) {
        if (0.0f > f || f > 1.0f) {
            PreconditionsKt.throwIllegalArgumentException("Expecting fraction between 0 and 1. Got " + f);
        }
        Transition transition = this.transition;
        if (transition == null) {
            return Unit.INSTANCE;
        }
        Object objMutate$default = MutatorMutex.mutate$default(this.mutatorMutex, new AnonymousClass3(navBackStackEntry, ((SnapshotMutableStateImpl) this.targetState$delegate).getValue(), this, transition, f, null), continuation);
        return objMutate$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objMutate$default : Unit.INSTANCE;
    }

    public final void seekToFraction() {
        Transition transition = this.transition;
        if (transition == null) {
            return;
        }
        transition.seekAnimations$animation_core(MathKt__MathJVMKt.roundToLong(((SnapshotMutableFloatStateImpl) this.fraction$delegate).getFloatValue() * ((Number) transition.totalDurationNanos$delegate.getValue()).longValue()));
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void setCurrentState$animation_core(Object obj) {
        ((SnapshotMutableStateImpl) this.currentState$delegate).setValue(obj);
    }

    public final void setFraction(float f) {
        ((SnapshotMutableFloatStateImpl) this.fraction$delegate).setFloatValue(f);
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void transitionConfigured$animation_core(Transition transition) {
        Transition transition2 = this.transition;
        if (transition2 != null && !transition.equals(transition2)) {
            PreconditionsKt.throwIllegalStateException("An instance of SeekableTransitionState has been used in different Transitions. Previous instance: " + this.transition + ", new instance: " + transition);
        }
        this.transition = transition;
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void transitionRemoved$animation_core() {
        this.transition = null;
        ((SnapshotStateObserver) TransitionKt.SeekableStateObserver$delegate.getValue()).clear(this);
    }
}
