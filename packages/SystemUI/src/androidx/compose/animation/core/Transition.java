package androidx.compose.animation.core;

import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableLongState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotLongStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableLongStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Transition<S> {
    public final SnapshotStateList _animations;
    public final MutableLongState _playTimeNanos$delegate;
    public final SnapshotStateList _transitions;
    public final MutableState isSeeking$delegate;
    public final String label;
    public final Transition parentTransition;
    public final MutableState segment$delegate;
    public final MutableLongState startTimeNanos$delegate;
    public final MutableState targetState$delegate;
    public final State totalDurationNanos$delegate;
    public final TransitionState transitionState;
    public final MutableState updateChildrenNeeded$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeferredAnimation<T, V extends AnimationVector> {
        public final MutableState data$delegate = SnapshotStateKt.mutableStateOf$default(null);
        public final String label;
        public final TwoWayConverter typeConverter;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class DeferredAnimationData<T, V extends AnimationVector> implements State<T> {
            public final TransitionAnimationState animation;
            public Function1 targetValueByState;
            public Function1 transitionSpec;

            public DeferredAnimationData(TransitionAnimationState<T, V> transitionAnimationState, Function1 function1, Function1 function12) {
                this.animation = transitionAnimationState;
                this.transitionSpec = function1;
                this.targetValueByState = function12;
            }

            @Override // androidx.compose.runtime.State
            public final Object getValue() {
                updateAnimationStates(Transition.this.getSegment());
                return ((SnapshotMutableStateImpl) this.animation.value$delegate).getValue();
            }

            public final void updateAnimationStates(Segment segment) {
                Object mo779invoke = this.targetValueByState.mo779invoke(segment.getTargetState());
                boolean isSeeking = Transition.this.isSeeking();
                TransitionAnimationState transitionAnimationState = this.animation;
                if (isSeeking) {
                    transitionAnimationState.updateInitialAndTargetValue$animation_core(this.targetValueByState.mo779invoke(segment.getInitialState()), mo779invoke, (FiniteAnimationSpec) this.transitionSpec.mo779invoke(segment));
                } else {
                    transitionAnimationState.updateTargetValue$animation_core(mo779invoke, (FiniteAnimationSpec) this.transitionSpec.mo779invoke(segment));
                }
            }
        }

        public DeferredAnimation(TwoWayConverter<T, V> twoWayConverter, String str) {
            this.typeConverter = twoWayConverter;
            this.label = str;
        }

        public final DeferredAnimationData animate(Function1 function1, Function1 function12) {
            MutableState mutableState = this.data$delegate;
            DeferredAnimationData deferredAnimationData = (DeferredAnimationData) ((SnapshotMutableStateImpl) mutableState).getValue();
            Transition transition = Transition.this;
            if (deferredAnimationData == null) {
                Object mo779invoke = function12.mo779invoke(transition.transitionState.getCurrentState());
                AnimationVector animationVector = (AnimationVector) ((TwoWayConverterImpl) this.typeConverter).convertToVector.mo779invoke(function12.mo779invoke(transition.transitionState.getCurrentState()));
                animationVector.reset$animation_core();
                deferredAnimationData = new DeferredAnimationData(new TransitionAnimationState(mo779invoke, animationVector, this.typeConverter, this.label), function1, function12);
                ((SnapshotMutableStateImpl) mutableState).setValue(deferredAnimationData);
                transition._animations.add(deferredAnimationData.animation);
            }
            deferredAnimationData.targetValueByState = function12;
            deferredAnimationData.transitionSpec = function1;
            deferredAnimationData.updateAnimationStates(transition.getSegment());
            return deferredAnimationData;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Segment<S> {
        Object getInitialState();

        Object getTargetState();

        default boolean isTransitioningTo(Object obj, Object obj2) {
            return Intrinsics.areEqual(obj, getInitialState()) && Intrinsics.areEqual(obj2, getTargetState());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SegmentImpl<S> implements Segment<S> {
        public final Object initialState;
        public final Object targetState;

        public SegmentImpl(S s, S s2) {
            this.initialState = s;
            this.targetState = s2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Segment)) {
                return false;
            }
            Segment segment = (Segment) obj;
            if (Intrinsics.areEqual(this.initialState, segment.getInitialState())) {
                return Intrinsics.areEqual(this.targetState, segment.getTargetState());
            }
            return false;
        }

        @Override // androidx.compose.animation.core.Transition.Segment
        public final Object getInitialState() {
            return this.initialState;
        }

        @Override // androidx.compose.animation.core.Transition.Segment
        public final Object getTargetState() {
            return this.targetState;
        }

        public final int hashCode() {
            Object obj = this.initialState;
            int hashCode = (obj != null ? obj.hashCode() : 0) * 31;
            Object obj2 = this.targetState;
            return hashCode + (obj2 != null ? obj2.hashCode() : 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransitionAnimationState<T, V extends AnimationVector> implements State<T> {
        public final MutableState animation$delegate;
        public final MutableState animationSpec$delegate;
        public final MutableLongState durationNanos$delegate;
        public TargetBasedAnimation initialValueAnimation;
        public SeekableTransitionState.SeekingAnimationState initialValueState;
        public final SpringSpec interruptionSpec;
        public final MutableState isFinished$delegate;
        public boolean isSeeking;
        public final MutableFloatState resetSnapValue$delegate;
        public final MutableState targetValue$delegate;
        public final TwoWayConverter typeConverter;
        public boolean useOnlyInitialValue;
        public final MutableState value$delegate;
        public AnimationVector velocityVector;

        public TransitionAnimationState(T t, V v, TwoWayConverter<T, V> twoWayConverter, String str) {
            this.typeConverter = twoWayConverter;
            MutableState mutableStateOf$default = SnapshotStateKt.mutableStateOf$default(t);
            this.targetValue$delegate = mutableStateOf$default;
            Object obj = null;
            MutableState mutableStateOf$default2 = SnapshotStateKt.mutableStateOf$default(AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7));
            this.animationSpec$delegate = mutableStateOf$default2;
            this.animation$delegate = SnapshotStateKt.mutableStateOf$default(new TargetBasedAnimation((FiniteAnimationSpec) ((SnapshotMutableStateImpl) mutableStateOf$default2).getValue(), twoWayConverter, t, ((SnapshotMutableStateImpl) mutableStateOf$default).getValue(), v));
            this.isFinished$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
            this.resetSnapValue$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(-1.0f);
            this.value$delegate = SnapshotStateKt.mutableStateOf$default(t);
            this.velocityVector = v;
            this.durationNanos$delegate = SnapshotLongStateKt.mutableLongStateOf(getAnimation().getDurationNanos());
            Float f = (Float) VisibilityThresholdsKt.VisibilityThresholdMap.get(twoWayConverter);
            if (f != null) {
                float floatValue = f.floatValue();
                AnimationVector animationVector = (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo779invoke(t);
                int size$animation_core = animationVector.getSize$animation_core();
                for (int i = 0; i < size$animation_core; i++) {
                    animationVector.set$animation_core(floatValue, i);
                }
                obj = ((TwoWayConverterImpl) this.typeConverter).convertFromVector.mo779invoke(animationVector);
            }
            this.interruptionSpec = AnimationSpecKt.spring$default(0.0f, 0.0f, obj, 3);
        }

        public final TargetBasedAnimation getAnimation() {
            return (TargetBasedAnimation) ((SnapshotMutableStateImpl) this.animation$delegate).getValue();
        }

        @Override // androidx.compose.runtime.State
        public final Object getValue() {
            return ((SnapshotMutableStateImpl) this.value$delegate).getValue();
        }

        public final void seekTo$animation_core(long j) {
            if (((SnapshotMutableFloatStateImpl) this.resetSnapValue$delegate).getFloatValue() == -1.0f) {
                this.isSeeking = true;
                if (Intrinsics.areEqual(getAnimation().mutableTargetValue, getAnimation().mutableInitialValue)) {
                    setValue$animation_core(getAnimation().mutableTargetValue);
                } else {
                    setValue$animation_core(getAnimation().getValueFromNanos(j));
                    this.velocityVector = getAnimation().getVelocityVectorFromNanos(j);
                }
            }
        }

        public final void setValue$animation_core(Object obj) {
            ((SnapshotMutableStateImpl) this.value$delegate).setValue(obj);
        }

        public final String toString() {
            return "current value: " + ((SnapshotMutableStateImpl) this.value$delegate).getValue() + ", target: " + ((SnapshotMutableStateImpl) this.targetValue$delegate).getValue() + ", spec: " + ((FiniteAnimationSpec) ((SnapshotMutableStateImpl) this.animationSpec$delegate).getValue());
        }

        public final void updateAnimation(Object obj, boolean z) {
            TargetBasedAnimation targetBasedAnimation = this.initialValueAnimation;
            Object obj2 = targetBasedAnimation != null ? targetBasedAnimation.mutableTargetValue : null;
            SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) this.targetValue$delegate;
            boolean areEqual = Intrinsics.areEqual(obj2, snapshotMutableStateImpl.getValue());
            MutableLongState mutableLongState = this.durationNanos$delegate;
            MutableState mutableState = this.animation$delegate;
            AnimationSpec animationSpec = this.interruptionSpec;
            if (areEqual) {
                ((SnapshotMutableStateImpl) mutableState).setValue(new TargetBasedAnimation((AnimationSpec<Object>) animationSpec, (TwoWayConverter<Object, AnimationVector>) this.typeConverter, obj, obj, this.velocityVector.newVector$animation_core()));
                this.useOnlyInitialValue = true;
                ((SnapshotMutableLongStateImpl) mutableLongState).setLongValue(getAnimation().getDurationNanos());
                return;
            }
            MutableState mutableState2 = this.animationSpec$delegate;
            if (!z || this.isSeeking) {
                animationSpec = (FiniteAnimationSpec) ((SnapshotMutableStateImpl) mutableState2).getValue();
            } else if (((FiniteAnimationSpec) ((SnapshotMutableStateImpl) mutableState2).getValue()) instanceof SpringSpec) {
                animationSpec = (FiniteAnimationSpec) ((SnapshotMutableStateImpl) mutableState2).getValue();
            }
            Transition transition = Transition.this;
            ((SnapshotMutableStateImpl) mutableState).setValue(new TargetBasedAnimation((AnimationSpec<Object>) (transition.getPlayTimeNanos() <= 0 ? animationSpec : new StartDelayAnimationSpec(animationSpec, transition.getPlayTimeNanos())), (TwoWayConverter<Object, AnimationVector>) this.typeConverter, obj, snapshotMutableStateImpl.getValue(), this.velocityVector));
            ((SnapshotMutableLongStateImpl) mutableLongState).setLongValue(getAnimation().getDurationNanos());
            this.useOnlyInitialValue = false;
            Boolean bool = Boolean.TRUE;
            MutableState mutableState3 = transition.updateChildrenNeeded$delegate;
            ((SnapshotMutableStateImpl) mutableState3).setValue(bool);
            if (transition.isSeeking()) {
                SnapshotStateList snapshotStateList = transition._animations;
                int size = snapshotStateList.size();
                long j = 0;
                for (int i = 0; i < size; i++) {
                    TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
                    j = Math.max(j, ((SnapshotMutableLongStateImpl) transitionAnimationState.durationNanos$delegate).getLongValue());
                    transitionAnimationState.seekTo$animation_core(0L);
                }
                ((SnapshotMutableStateImpl) mutableState3).setValue(Boolean.FALSE);
            }
        }

        public final void updateInitialAndTargetValue$animation_core(Object obj, Object obj2, FiniteAnimationSpec finiteAnimationSpec) {
            ((SnapshotMutableStateImpl) this.targetValue$delegate).setValue(obj2);
            ((SnapshotMutableStateImpl) this.animationSpec$delegate).setValue(finiteAnimationSpec);
            if (Intrinsics.areEqual(getAnimation().mutableInitialValue, obj) && Intrinsics.areEqual(getAnimation().mutableTargetValue, obj2)) {
                return;
            }
            updateAnimation(obj, false);
        }

        public final void updateTargetValue$animation_core(Object obj, FiniteAnimationSpec finiteAnimationSpec) {
            if (this.useOnlyInitialValue) {
                TargetBasedAnimation targetBasedAnimation = this.initialValueAnimation;
                if (Intrinsics.areEqual(obj, targetBasedAnimation != null ? targetBasedAnimation.mutableTargetValue : null)) {
                    return;
                }
            }
            MutableState mutableState = this.targetValue$delegate;
            boolean areEqual = Intrinsics.areEqual(((SnapshotMutableStateImpl) mutableState).getValue(), obj);
            MutableFloatState mutableFloatState = this.resetSnapValue$delegate;
            if (areEqual && ((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue() == -1.0f) {
                return;
            }
            ((SnapshotMutableStateImpl) mutableState).setValue(obj);
            ((SnapshotMutableStateImpl) this.animationSpec$delegate).setValue(finiteAnimationSpec);
            SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
            Object value = snapshotMutableFloatStateImpl.getFloatValue() == -3.0f ? obj : ((SnapshotMutableStateImpl) this.value$delegate).getValue();
            MutableState mutableState2 = this.isFinished$delegate;
            updateAnimation(value, !((Boolean) ((SnapshotMutableStateImpl) mutableState2).getValue()).booleanValue());
            ((SnapshotMutableStateImpl) mutableState2).setValue(Boolean.valueOf(snapshotMutableFloatStateImpl.getFloatValue() == -3.0f));
            if (snapshotMutableFloatStateImpl.getFloatValue() >= 0.0f) {
                setValue$animation_core(getAnimation().getValueFromNanos((long) (snapshotMutableFloatStateImpl.getFloatValue() * getAnimation().getDurationNanos())));
            } else if (snapshotMutableFloatStateImpl.getFloatValue() == -3.0f) {
                setValue$animation_core(obj);
            }
            this.useOnlyInitialValue = false;
            ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(-1.0f);
        }
    }

    public Transition(TransitionState<S> transitionState, Transition<?> transition, String str) {
        this.transitionState = transitionState;
        this.parentTransition = transition;
        this.label = str;
        this.targetState$delegate = SnapshotStateKt.mutableStateOf$default(transitionState.getCurrentState());
        this.segment$delegate = SnapshotStateKt.mutableStateOf$default(new SegmentImpl(transitionState.getCurrentState(), transitionState.getCurrentState()));
        this._playTimeNanos$delegate = SnapshotLongStateKt.mutableLongStateOf(0L);
        this.startTimeNanos$delegate = SnapshotLongStateKt.mutableLongStateOf(Long.MIN_VALUE);
        Boolean bool = Boolean.FALSE;
        this.updateChildrenNeeded$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this._animations = new SnapshotStateList();
        this._transitions = new SnapshotStateList();
        this.isSeeking$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.totalDurationNanos$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: androidx.compose.animation.core.Transition$totalDurationNanos$2
            final /* synthetic */ Transition<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Long.valueOf(this.this$0.calculateTotalDurationNanos());
            }
        });
        transitionState.transitionConfigured$animation_core(this);
    }

    public final void animateTo$animation_core(final Object obj, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1493585151);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(obj) : composerImpl.changedInstance(obj) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(this) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 19) != 18)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.animation.core.Transition.animateTo (Transition.kt:1177)");
            }
            if (isSeeking()) {
                composerImpl.startReplaceGroup(1824242331);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1822758547);
                updateTarget$animation_core(obj);
                if (Intrinsics.areEqual(obj, this.transitionState.getCurrentState())) {
                    if (!(((SnapshotMutableLongStateImpl) this.startTimeNanos$delegate).getLongValue() != Long.MIN_VALUE) && !((Boolean) ((SnapshotMutableStateImpl) this.updateChildrenNeeded$delegate).getValue()).booleanValue()) {
                        composerImpl.startReplaceGroup(1824232411);
                        composerImpl.end(false);
                        composerImpl.end(false);
                    }
                }
                composerImpl.startReplaceGroup(1822989838);
                Object rememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (rememberedValue == composer$Companion$Empty$1) {
                    rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
                boolean changedInstance = composerImpl.changedInstance(coroutineScope) | ((i2 & 112) == 32);
                Object rememberedValue2 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                    rememberedValue2 = new Function1() { // from class: androidx.compose.animation.core.Transition$animateTo$1$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: androidx.compose.animation.core.Transition$animateTo$1$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            float F$0;
                            private /* synthetic */ Object L$0;
                            int label;
                            final /* synthetic */ Transition<Object> this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(Transition<Object> transition, Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = transition;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                                anonymousClass1.L$0 = obj;
                                return anonymousClass1;
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                final float durationScale;
                                CoroutineScope coroutineScope;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                                    durationScale = SuspendAnimationKt.getDurationScale(coroutineScope2.getCoroutineContext());
                                    coroutineScope = coroutineScope2;
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    durationScale = this.F$0;
                                    coroutineScope = (CoroutineScope) this.L$0;
                                    ResultKt.throwOnFailure(obj);
                                }
                                while (CoroutineScopeKt.isActive(coroutineScope)) {
                                    final Transition<Object> transition = this.this$0;
                                    Function1 function1 = new Function1() { // from class: androidx.compose.animation.core.Transition.animateTo.1.1.1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo779invoke(Object obj2) {
                                            long longValue = ((Number) obj2).longValue();
                                            if (!transition.isSeeking()) {
                                                Transition<Object> transition2 = transition;
                                                float f = durationScale;
                                                long longValue2 = ((SnapshotMutableLongStateImpl) transition2.startTimeNanos$delegate).getLongValue();
                                                MutableLongState mutableLongState = transition2.startTimeNanos$delegate;
                                                if (longValue2 == Long.MIN_VALUE) {
                                                    ((SnapshotMutableLongStateImpl) mutableLongState).setLongValue(longValue);
                                                    ((SnapshotMutableStateImpl) transition2.transitionState.isRunning$delegate).setValue(Boolean.TRUE);
                                                }
                                                long longValue3 = longValue - ((SnapshotMutableLongStateImpl) mutableLongState).getLongValue();
                                                if (f != 0.0f) {
                                                    longValue3 = MathKt__MathJVMKt.roundToLong(longValue3 / f);
                                                }
                                                transition2.setPlayTimeNanos(longValue3);
                                                transition2.onFrame$animation_core(longValue3, f == 0.0f);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    this.L$0 = coroutineScope;
                                    this.F$0 = durationScale;
                                    this.label = 1;
                                    if (MonotonicFrameClockKt.getMonotonicFrameClock(getContext()).withFrameNanos(function1, this) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            BuildersKt.launch$default(CoroutineScope.this, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(this, null), 1);
                            return new DisposableEffectResult() { // from class: androidx.compose.animation.core.Transition$animateTo$1$1$invoke$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
                EffectsKt.DisposableEffect(coroutineScope, this, (Function1) rememberedValue2, composerImpl);
                composerImpl.end(false);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(this) { // from class: androidx.compose.animation.core.Transition$animateTo$2
                final /* synthetic */ Transition<Object> $tmp1_rcvr;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                    this.$tmp1_rcvr = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    this.$tmp1_rcvr.animateTo$animation_core(obj, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final long calculateTotalDurationNanos() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            j = Math.max(j, ((SnapshotMutableLongStateImpl) ((TransitionAnimationState) snapshotStateList.get(i)).durationNanos$delegate).getLongValue());
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            j = Math.max(j, ((Transition) snapshotStateList2.get(i2)).calculateTotalDurationNanos());
        }
        return j;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void clearInitialAnimations$animation_core() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
            transitionAnimationState.initialValueAnimation = null;
            transitionAnimationState.initialValueState = null;
            transitionAnimationState.useOnlyInitialValue = false;
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Transition) snapshotStateList2.get(i2)).clearInitialAnimations$animation_core();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getHasInitialValueAnimations() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            if (((TransitionAnimationState) snapshotStateList.get(i)).initialValueState != null) {
                return true;
            }
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (((Transition) snapshotStateList2.get(i2)).getHasInitialValueAnimations()) {
                return true;
            }
        }
        return false;
    }

    public final long getPlayTimeNanos() {
        Transition transition = this.parentTransition;
        return transition != null ? transition.getPlayTimeNanos() : ((SnapshotMutableLongStateImpl) this._playTimeNanos$delegate).getLongValue();
    }

    public final Segment getSegment() {
        return (Segment) ((SnapshotMutableStateImpl) this.segment$delegate).getValue();
    }

    public final Object getTargetState() {
        return ((SnapshotMutableStateImpl) this.targetState$delegate).getValue();
    }

    public final boolean isSeeking() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isSeeking$delegate).getValue()).booleanValue();
    }

    public final void onFrame$animation_core(long j, boolean z) {
        MutableLongState mutableLongState = this.startTimeNanos$delegate;
        long longValue = ((SnapshotMutableLongStateImpl) mutableLongState).getLongValue();
        TransitionState transitionState = this.transitionState;
        if (longValue == Long.MIN_VALUE) {
            ((SnapshotMutableLongStateImpl) mutableLongState).setLongValue(j);
            ((SnapshotMutableStateImpl) transitionState.isRunning$delegate).setValue(Boolean.TRUE);
        } else if (!((Boolean) ((SnapshotMutableStateImpl) transitionState.isRunning$delegate).getValue()).booleanValue()) {
            ((SnapshotMutableStateImpl) transitionState.isRunning$delegate).setValue(Boolean.TRUE);
        }
        ((SnapshotMutableStateImpl) this.updateChildrenNeeded$delegate).setValue(Boolean.FALSE);
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        boolean z2 = true;
        for (int i = 0; i < size; i++) {
            TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
            boolean booleanValue = ((Boolean) ((SnapshotMutableStateImpl) transitionAnimationState.isFinished$delegate).getValue()).booleanValue();
            MutableState mutableState = transitionAnimationState.isFinished$delegate;
            if (!booleanValue) {
                long durationNanos = z ? transitionAnimationState.getAnimation().getDurationNanos() : j;
                transitionAnimationState.setValue$animation_core(transitionAnimationState.getAnimation().getValueFromNanos(durationNanos));
                transitionAnimationState.velocityVector = transitionAnimationState.getAnimation().getVelocityVectorFromNanos(durationNanos);
                if (transitionAnimationState.getAnimation().isFinishedFromNanos(durationNanos)) {
                    ((SnapshotMutableStateImpl) mutableState).setValue(Boolean.TRUE);
                }
            }
            if (!((Boolean) ((SnapshotMutableStateImpl) mutableState).getValue()).booleanValue()) {
                z2 = false;
            }
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            Transition transition = (Transition) snapshotStateList2.get(i2);
            Object value = ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue();
            TransitionState transitionState2 = transition.transitionState;
            if (!Intrinsics.areEqual(value, transitionState2.getCurrentState())) {
                transition.onFrame$animation_core(j, z);
            }
            if (!Intrinsics.areEqual(((SnapshotMutableStateImpl) transition.targetState$delegate).getValue(), transitionState2.getCurrentState())) {
                z2 = false;
            }
        }
        if (z2) {
            onTransitionEnd$animation_core();
        }
    }

    public final void onTransitionEnd$animation_core() {
        ((SnapshotMutableLongStateImpl) this.startTimeNanos$delegate).setLongValue(Long.MIN_VALUE);
        TransitionState transitionState = this.transitionState;
        if (transitionState instanceof MutableTransitionState) {
            transitionState.setCurrentState$animation_core(((SnapshotMutableStateImpl) this.targetState$delegate).getValue());
        }
        setPlayTimeNanos(0L);
        ((SnapshotMutableStateImpl) transitionState.isRunning$delegate).setValue(Boolean.FALSE);
        SnapshotStateList snapshotStateList = this._transitions;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            ((Transition) snapshotStateList.get(i)).onTransitionEnd$animation_core();
        }
    }

    public final void resetAnimationFraction$animation_core(float f) {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
            transitionAnimationState.getClass();
            if (f == -4.0f || f == -5.0f) {
                TargetBasedAnimation targetBasedAnimation = transitionAnimationState.initialValueAnimation;
                if (targetBasedAnimation != null) {
                    transitionAnimationState.getAnimation().setMutableInitialValue$animation_core(targetBasedAnimation.mutableTargetValue);
                    transitionAnimationState.initialValueState = null;
                    transitionAnimationState.initialValueAnimation = null;
                }
                Object obj = f == -4.0f ? transitionAnimationState.getAnimation().mutableInitialValue : transitionAnimationState.getAnimation().mutableTargetValue;
                transitionAnimationState.getAnimation().setMutableInitialValue$animation_core(obj);
                transitionAnimationState.getAnimation().setMutableTargetValue$animation_core(obj);
                transitionAnimationState.setValue$animation_core(obj);
                ((SnapshotMutableLongStateImpl) transitionAnimationState.durationNanos$delegate).setLongValue(transitionAnimationState.getAnimation().getDurationNanos());
            } else {
                ((SnapshotMutableFloatStateImpl) transitionAnimationState.resetSnapValue$delegate).setFloatValue(f);
            }
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Transition) snapshotStateList2.get(i2)).resetAnimationFraction$animation_core(f);
        }
    }

    public final void resetAnimations() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            ((SnapshotMutableFloatStateImpl) ((TransitionAnimationState) snapshotStateList.get(i)).resetSnapValue$delegate).setFloatValue(-2.0f);
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Transition) snapshotStateList2.get(i2)).resetAnimations();
        }
    }

    public final void seek(Object obj, Object obj2) {
        ((SnapshotMutableLongStateImpl) this.startTimeNanos$delegate).setLongValue(Long.MIN_VALUE);
        TransitionState transitionState = this.transitionState;
        ((SnapshotMutableStateImpl) transitionState.isRunning$delegate).setValue(Boolean.FALSE);
        boolean isSeeking = isSeeking();
        MutableState mutableState = this.targetState$delegate;
        if (!isSeeking || !Intrinsics.areEqual(transitionState.getCurrentState(), obj) || !Intrinsics.areEqual(((SnapshotMutableStateImpl) mutableState).getValue(), obj2)) {
            if (!Intrinsics.areEqual(transitionState.getCurrentState(), obj) && (transitionState instanceof MutableTransitionState)) {
                transitionState.setCurrentState$animation_core(obj);
            }
            ((SnapshotMutableStateImpl) mutableState).setValue(obj2);
            ((SnapshotMutableStateImpl) this.isSeeking$delegate).setValue(Boolean.TRUE);
            ((SnapshotMutableStateImpl) this.segment$delegate).setValue(new SegmentImpl(obj, obj2));
        }
        SnapshotStateList snapshotStateList = this._transitions;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            Transition transition = (Transition) snapshotStateList.get(i);
            if (transition.isSeeking()) {
                transition.seek(transition.transitionState.getCurrentState(), ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue());
            }
        }
        SnapshotStateList snapshotStateList2 = this._animations;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((TransitionAnimationState) snapshotStateList2.get(i2)).seekTo$animation_core(0L);
        }
    }

    public final void seekAnimations$animation_core(long j) {
        MutableLongState mutableLongState = this.startTimeNanos$delegate;
        if (((SnapshotMutableLongStateImpl) mutableLongState).getLongValue() == Long.MIN_VALUE) {
            ((SnapshotMutableLongStateImpl) mutableLongState).setLongValue(j);
        }
        setPlayTimeNanos(j);
        ((SnapshotMutableStateImpl) this.updateChildrenNeeded$delegate).setValue(Boolean.FALSE);
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            ((TransitionAnimationState) snapshotStateList.get(i)).seekTo$animation_core(j);
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            Transition transition = (Transition) snapshotStateList2.get(i2);
            if (!Intrinsics.areEqual(((SnapshotMutableStateImpl) transition.targetState$delegate).getValue(), transition.transitionState.getCurrentState())) {
                transition.seekAnimations$animation_core(j);
            }
        }
    }

    public final void setInitialAnimations$animation_core(SeekableTransitionState.SeekingAnimationState seekingAnimationState) {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
            if (!Intrinsics.areEqual(transitionAnimationState.getAnimation().mutableTargetValue, transitionAnimationState.getAnimation().mutableInitialValue)) {
                transitionAnimationState.initialValueAnimation = transitionAnimationState.getAnimation();
                transitionAnimationState.initialValueState = seekingAnimationState;
            }
            SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) transitionAnimationState.value$delegate;
            ((SnapshotMutableStateImpl) transitionAnimationState.animation$delegate).setValue(new TargetBasedAnimation(transitionAnimationState.interruptionSpec, (TwoWayConverter<Object, AnimationVector>) transitionAnimationState.typeConverter, snapshotMutableStateImpl.getValue(), snapshotMutableStateImpl.getValue(), transitionAnimationState.velocityVector.newVector$animation_core()));
            ((SnapshotMutableLongStateImpl) transitionAnimationState.durationNanos$delegate).setLongValue(transitionAnimationState.getAnimation().getDurationNanos());
            transitionAnimationState.useOnlyInitialValue = true;
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Transition) snapshotStateList2.get(i2)).setInitialAnimations$animation_core(seekingAnimationState);
        }
    }

    public final void setPlayTimeNanos(long j) {
        if (this.parentTransition == null) {
            ((SnapshotMutableLongStateImpl) this._playTimeNanos$delegate).setLongValue(j);
        }
    }

    public final String toString() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        String str = "Transition animation values: ";
        for (int i = 0; i < size; i++) {
            str = str + ((TransitionAnimationState) snapshotStateList.get(i)) + ", ";
        }
        return str;
    }

    public final void updateInitialValues$animation_core() {
        TargetBasedAnimation targetBasedAnimation;
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
            SeekableTransitionState.SeekingAnimationState seekingAnimationState = transitionAnimationState.initialValueState;
            if (seekingAnimationState != null && (targetBasedAnimation = transitionAnimationState.initialValueAnimation) != null) {
                long roundToLong = MathKt__MathJVMKt.roundToLong(seekingAnimationState.durationNanos * seekingAnimationState.value);
                Object valueFromNanos = targetBasedAnimation.getValueFromNanos(roundToLong);
                if (transitionAnimationState.useOnlyInitialValue) {
                    transitionAnimationState.getAnimation().setMutableTargetValue$animation_core(valueFromNanos);
                }
                transitionAnimationState.getAnimation().setMutableInitialValue$animation_core(valueFromNanos);
                ((SnapshotMutableLongStateImpl) transitionAnimationState.durationNanos$delegate).setLongValue(transitionAnimationState.getAnimation().getDurationNanos());
                if (((SnapshotMutableFloatStateImpl) transitionAnimationState.resetSnapValue$delegate).getFloatValue() == -2.0f || transitionAnimationState.useOnlyInitialValue) {
                    transitionAnimationState.setValue$animation_core(valueFromNanos);
                } else {
                    transitionAnimationState.seekTo$animation_core(Transition.this.getPlayTimeNanos());
                }
                if (roundToLong >= seekingAnimationState.durationNanos) {
                    transitionAnimationState.initialValueState = null;
                    transitionAnimationState.initialValueAnimation = null;
                } else {
                    seekingAnimationState.isComplete = false;
                }
            }
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Transition) snapshotStateList2.get(i2)).updateInitialValues$animation_core();
        }
    }

    public final void updateTarget$animation_core(Object obj) {
        MutableState mutableState = this.targetState$delegate;
        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) mutableState;
        if (Intrinsics.areEqual(snapshotMutableStateImpl.getValue(), obj)) {
            return;
        }
        ((SnapshotMutableStateImpl) this.segment$delegate).setValue(new SegmentImpl(snapshotMutableStateImpl.getValue(), obj));
        TransitionState transitionState = this.transitionState;
        if (!Intrinsics.areEqual(transitionState.getCurrentState(), snapshotMutableStateImpl.getValue())) {
            transitionState.setCurrentState$animation_core(snapshotMutableStateImpl.getValue());
        }
        ((SnapshotMutableStateImpl) mutableState).setValue(obj);
        if (((SnapshotMutableLongStateImpl) this.startTimeNanos$delegate).getLongValue() == Long.MIN_VALUE) {
            ((SnapshotMutableStateImpl) this.updateChildrenNeeded$delegate).setValue(Boolean.TRUE);
        }
        resetAnimations();
    }

    public /* synthetic */ Transition(TransitionState transitionState, Transition transition, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionState, transition, (i & 4) != 0 ? null : str);
    }

    public /* synthetic */ Transition(TransitionState transitionState, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionState, (i & 2) != 0 ? null : str);
    }

    public Transition(TransitionState<S> transitionState, String str) {
        this(transitionState, null, str);
    }

    public Transition(S s, String str) {
        this(new MutableTransitionState(s), null, str);
    }

    public /* synthetic */ Transition(MutableTransitionState mutableTransitionState, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableTransitionState, (i & 2) != 0 ? null : str);
    }

    public Transition(MutableTransitionState<S> mutableTransitionState, String str) {
        this(mutableTransitionState, null, str);
    }
}
