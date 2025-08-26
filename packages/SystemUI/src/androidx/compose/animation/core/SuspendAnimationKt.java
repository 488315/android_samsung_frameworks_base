package androidx.compose.animation.core;

import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.MotionDurationScale;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public abstract class SuspendAnimationKt {

    /* renamed from: androidx.compose.animation.core.SuspendAnimationKt$animate$4, reason: invalid class name */
    final class AnonymousClass4<T, V extends AnimationVector> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass4(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SuspendAnimationKt.animate(null, null, 0L, null, this);
        }
    }

    public static final Object animate(float f, float f2, float f3, AnimationSpec animationSpec, final Function2 function2, SuspendLambda suspendLambda) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        final TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
        Float f4 = new Float(f);
        Float f5 = new Float(f2);
        TwoWayConverterImpl twoWayConverterImpl = (TwoWayConverterImpl) twoWayConverter;
        AnimationVector animationVectorNewVector$animation_core = (AnimationVector) twoWayConverterImpl.convertToVector.mo781invoke(new Float(f3));
        if (animationVectorNewVector$animation_core == null) {
            animationVectorNewVector$animation_core = ((AnimationVector) twoWayConverterImpl.convertToVector.mo781invoke(f4)).newVector$animation_core();
        }
        AnimationVector animationVector = animationVectorNewVector$animation_core;
        Object objAnimate = animate(new AnimationState(twoWayConverter, f4, animationVector, 0L, 0L, false, 56, null), new TargetBasedAnimation((AnimationSpec<Float>) animationSpec, (TwoWayConverter<Float, AnimationVector>) twoWayConverter, f4, f5, animationVector), Long.MIN_VALUE, new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt.animate.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AnimationScope animationScope = (AnimationScope) obj;
                function2.invoke(((SnapshotMutableStateImpl) animationScope.value$delegate).getValue(), ((TwoWayConverterImpl) twoWayConverter).convertFromVector.mo781invoke(animationScope.velocityVector));
                return Unit.INSTANCE;
            }
        }, suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objAnimate != coroutineSingletons) {
            objAnimate = Unit.INSTANCE;
        }
        return objAnimate == coroutineSingletons ? objAnimate : Unit.INSTANCE;
    }

    public static /* synthetic */ Object animate$default(float f, AnimationSpec animationSpec, Function2 function2, SuspendLambda suspendLambda, int i) {
        if ((i & 8) != 0) {
            animationSpec = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
        }
        return animate(0.0f, f, 0.0f, animationSpec, function2, suspendLambda);
    }

    public static final Object animateDecay(AnimationState animationState, DecayAnimationSpec decayAnimationSpec, boolean z, Function1 function1, ContinuationImpl continuationImpl) {
        Object objAnimate = animate(animationState, new DecayAnimation((DecayAnimationSpec<Object>) decayAnimationSpec, (TwoWayConverter<Object, AnimationVector>) animationState.typeConverter, ((SnapshotMutableStateImpl) animationState.value$delegate).getValue(), animationState.velocityVector), z ? animationState.lastFrameTimeNanos : Long.MIN_VALUE, function1, continuationImpl);
        return objAnimate == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnimate : Unit.INSTANCE;
    }

    public static final Object animateTo(AnimationState animationState, Object obj, AnimationSpec animationSpec, boolean z, Function1 function1, ContinuationImpl continuationImpl) {
        Object objAnimate = animate(animationState, new TargetBasedAnimation((AnimationSpec<Object>) animationSpec, (TwoWayConverter<Object, AnimationVector>) animationState.typeConverter, ((SnapshotMutableStateImpl) animationState.value$delegate).getValue(), obj, animationState.velocityVector), z ? animationState.lastFrameTimeNanos : Long.MIN_VALUE, function1, continuationImpl);
        return objAnimate == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnimate : Unit.INSTANCE;
    }

    public static /* synthetic */ Object animateTo$default(AnimationState animationState, Object obj, AnimationSpec animationSpec, boolean z, Function1 function1, ContinuationImpl continuationImpl, int i) {
        if ((i & 2) != 0) {
            animationSpec = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
        }
        AnimationSpec animationSpec2 = animationSpec;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 8) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt.animateTo.2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                    return Unit.INSTANCE;
                }
            };
        }
        return animateTo(animationState, obj, animationSpec2, z2, function1, continuationImpl);
    }

    public static final void doAnimationFrameWithScale(AnimationScope animationScope, long j, float f, Animation animation, AnimationState animationState, Function1 function1) {
        long durationNanos = f == 0.0f ? animation.getDurationNanos() : (long) ((j - animationScope.startTimeNanos) / f);
        animationScope.lastFrameTimeNanos = j;
        ((SnapshotMutableStateImpl) animationScope.value$delegate).setValue(animation.getValueFromNanos(durationNanos));
        animationScope.velocityVector = animation.getVelocityVectorFromNanos(durationNanos);
        if (animation.isFinishedFromNanos(durationNanos)) {
            animationScope.finishedTimeNanos = animationScope.lastFrameTimeNanos;
            ((SnapshotMutableStateImpl) animationScope.isRunning$delegate).setValue(Boolean.FALSE);
        }
        updateState(animationScope, animationState);
        function1.mo781invoke(animationScope);
    }

    public static final float getDurationScale(CoroutineContext coroutineContext) {
        MotionDurationScale motionDurationScale = (MotionDurationScale) coroutineContext.get(MotionDurationScale.Key);
        float scaleFactor = motionDurationScale != null ? motionDurationScale.getScaleFactor() : 1.0f;
        if (!(scaleFactor >= 0.0f)) {
            PreconditionsKt.throwIllegalStateException("negative scale factor");
        }
        return scaleFactor;
    }

    public static final void updateState(AnimationScope animationScope, AnimationState animationState) {
        ((SnapshotMutableStateImpl) animationState.value$delegate).setValue(((SnapshotMutableStateImpl) animationScope.value$delegate).getValue());
        AnimationVector animationVector = animationState.velocityVector;
        AnimationVector animationVector2 = animationScope.velocityVector;
        int size$animation_core = animationVector.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            animationVector.set$animation_core(animationVector2.get$animation_core(i), i);
        }
        animationState.finishedTimeNanos = animationScope.finishedTimeNanos;
        animationState.lastFrameTimeNanos = animationScope.lastFrameTimeNanos;
        animationState.isRunning = ((Boolean) ((SnapshotMutableStateImpl) animationScope.isRunning$delegate).getValue()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /* JADX WARN: Type inference failed for: r12v0, types: [T, androidx.compose.animation.core.AnimationScope] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object animate(final AnimationState animationState, Animation animation, long j, final Function1 function1, ContinuationImpl continuationImpl) {
        AnonymousClass4 anonymousClass4;
        final Ref$ObjectRef ref$ObjectRef;
        final AnimationState animationState2;
        AnimationState animationState3;
        Ref$ObjectRef ref$ObjectRef2;
        Object objWithFrameNanos;
        Function1 function12;
        AnimationScope animationScope;
        AnimationScope animationScope2;
        Object objWithFrameNanos2;
        final Animation animation2 = animation;
        if (continuationImpl instanceof AnonymousClass4) {
            anonymousClass4 = (AnonymousClass4) continuationImpl;
            int i = anonymousClass4.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass4.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass4 = new AnonymousClass4(continuationImpl);
            }
        }
        AnonymousClass4 anonymousClass42 = anonymousClass4;
        Object obj = anonymousClass42.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass42.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Object valueFromNanos = animation2.getValueFromNanos(0L);
            final AnimationVector velocityVectorFromNanos = animation2.getVelocityVectorFromNanos(0L);
            ref$ObjectRef = new Ref$ObjectRef();
            if (j == Long.MIN_VALUE) {
                try {
                    final float durationScale = getDurationScale(anonymousClass42.getContext());
                    animationState2 = animationState;
                    try {
                        final Function1 function13 = new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt.animate.6
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            /* JADX WARN: Type inference failed for: r0v0, types: [T, androidx.compose.animation.core.AnimationScope] */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                long jLongValue = ((Number) obj2).longValue();
                                Ref$ObjectRef<AnimationScope<Object, AnimationVector>> ref$ObjectRef3 = ref$ObjectRef;
                                Object obj3 = valueFromNanos;
                                TwoWayConverter typeConverter = animation2.getTypeConverter();
                                AnimationVector animationVector = velocityVectorFromNanos;
                                Object targetValue = animation2.getTargetValue();
                                final AnimationState<Object, AnimationVector> animationState4 = animationState2;
                                ?? animationScope3 = new AnimationScope(obj3, typeConverter, animationVector, jLongValue, targetValue, jLongValue, true, new Function0() { // from class: androidx.compose.animation.core.SuspendAnimationKt.animate.6.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        animationState4.isRunning = false;
                                        return Unit.INSTANCE;
                                    }
                                });
                                SuspendAnimationKt.doAnimationFrameWithScale(animationScope3, jLongValue, durationScale, animation2, animationState2, function1);
                                ref$ObjectRef3.element = animationScope3;
                                return Unit.INSTANCE;
                            }
                        };
                        ref$ObjectRef2 = ref$ObjectRef;
                        try {
                            anonymousClass42.L$0 = animationState2;
                            anonymousClass42.L$1 = animation2;
                            anonymousClass42.L$2 = function1;
                            anonymousClass42.L$3 = ref$ObjectRef2;
                            anonymousClass42.label = 1;
                            if (animation2.isInfinite()) {
                                objWithFrameNanos = InfiniteAnimationPolicyKt.withInfiniteAnimationFrameNanos(function13, anonymousClass42);
                            } else {
                                objWithFrameNanos = MonotonicFrameClockKt.getMonotonicFrameClock(anonymousClass42.getContext()).withFrameNanos(new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt$callWithFrameNanos$2
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        return function13.mo781invoke(Long.valueOf(((Number) obj2).longValue()));
                                    }
                                }, anonymousClass42);
                            }
                            if (objWithFrameNanos != coroutineSingletons) {
                                animationState3 = animationState2;
                                function12 = function1;
                                ref$ObjectRef = ref$ObjectRef2;
                            }
                            return coroutineSingletons;
                        } catch (CancellationException e) {
                            e = e;
                            animationState3 = animationState2;
                            ref$ObjectRef = ref$ObjectRef2;
                            animationScope = (AnimationScope) ref$ObjectRef.element;
                            if (animationScope != null) {
                            }
                            animationScope2 = (AnimationScope) ref$ObjectRef.element;
                            if (animationScope2 != null) {
                            }
                            throw e;
                        }
                    } catch (CancellationException e2) {
                        e = e2;
                        animationState3 = animationState2;
                        animationScope = (AnimationScope) ref$ObjectRef.element;
                        if (animationScope != null) {
                        }
                        animationScope2 = (AnimationScope) ref$ObjectRef.element;
                        if (animationScope2 != null) {
                        }
                        throw e;
                    }
                } catch (CancellationException e3) {
                    e = e3;
                    animationState2 = animationState;
                }
            } else {
                ref$ObjectRef2 = ref$ObjectRef;
                try {
                    ?? animationScope3 = new AnimationScope(valueFromNanos, animation2.getTypeConverter(), velocityVectorFromNanos, j, animation2.getTargetValue(), j, true, new Function0() { // from class: androidx.compose.animation.core.SuspendAnimationKt.animate.7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            animationState.isRunning = false;
                            return Unit.INSTANCE;
                        }
                    });
                    doAnimationFrameWithScale(animationScope3, j, getDurationScale(anonymousClass42.getContext()), animation2, animationState, function1);
                    ref$ObjectRef2.element = animationScope3;
                    animationState3 = animationState;
                    animation2 = animation;
                    function12 = function1;
                    ref$ObjectRef = ref$ObjectRef2;
                } catch (CancellationException e4) {
                    e = e4;
                    animationState3 = animationState;
                    ref$ObjectRef = ref$ObjectRef2;
                    animationScope = (AnimationScope) ref$ObjectRef.element;
                    if (animationScope != null) {
                    }
                    animationScope2 = (AnimationScope) ref$ObjectRef.element;
                    if (animationScope2 != null) {
                        animationState3.isRunning = false;
                    }
                    throw e;
                }
            }
        } else {
            if (i2 != 1 && i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$ObjectRef = (Ref$ObjectRef) anonymousClass42.L$3;
            function12 = (Function1) anonymousClass42.L$2;
            animation2 = (Animation) anonymousClass42.L$1;
            animationState3 = (AnimationState) anonymousClass42.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (CancellationException e5) {
                e = e5;
                animationScope = (AnimationScope) ref$ObjectRef.element;
                if (animationScope != null) {
                    ((SnapshotMutableStateImpl) animationScope.isRunning$delegate).setValue(Boolean.FALSE);
                }
                animationScope2 = (AnimationScope) ref$ObjectRef.element;
                if (animationScope2 != null && animationScope2.lastFrameTimeNanos == animationState3.lastFrameTimeNanos) {
                    animationState3.isRunning = false;
                }
                throw e;
            }
        }
        do {
            T t = ref$ObjectRef.element;
            t.getClass();
            if (((Boolean) ((SnapshotMutableStateImpl) ((AnimationScope) t).isRunning$delegate).getValue()).booleanValue()) {
                final float durationScale2 = getDurationScale(anonymousClass42.getContext());
                final Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                final Function1 function14 = function12;
                final Animation animation3 = animation2;
                final AnimationState animationState4 = animationState3;
                try {
                    final Function1 function15 = new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt.animate.9
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            long jLongValue = ((Number) obj2).longValue();
                            AnimationScope<Object, AnimationVector> animationScope4 = ref$ObjectRef3.element;
                            animationScope4.getClass();
                            SuspendAnimationKt.doAnimationFrameWithScale(animationScope4, jLongValue, durationScale2, animation3, animationState4, function14);
                            return Unit.INSTANCE;
                        }
                    };
                    ref$ObjectRef = ref$ObjectRef3;
                    animation2 = animation3;
                    animationState3 = animationState4;
                    function12 = function14;
                    anonymousClass42.L$0 = animationState3;
                    anonymousClass42.L$1 = animation2;
                    anonymousClass42.L$2 = function12;
                    anonymousClass42.L$3 = ref$ObjectRef;
                    anonymousClass42.label = 2;
                    if (animation2.isInfinite()) {
                        objWithFrameNanos2 = InfiniteAnimationPolicyKt.withInfiniteAnimationFrameNanos(function15, anonymousClass42);
                    } else {
                        objWithFrameNanos2 = MonotonicFrameClockKt.getMonotonicFrameClock(anonymousClass42.getContext()).withFrameNanos(new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt$callWithFrameNanos$2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                return function15.mo781invoke(Long.valueOf(((Number) obj2).longValue()));
                            }
                        }, anonymousClass42);
                    }
                } catch (CancellationException e6) {
                    e = e6;
                    ref$ObjectRef = ref$ObjectRef3;
                    animationState3 = animationState4;
                    animationScope = (AnimationScope) ref$ObjectRef.element;
                    if (animationScope != null) {
                    }
                    animationScope2 = (AnimationScope) ref$ObjectRef.element;
                    if (animationScope2 != null) {
                    }
                    throw e;
                }
            } else {
                return Unit.INSTANCE;
            }
        } while (objWithFrameNanos2 != coroutineSingletons);
        return coroutineSingletons;
    }
}
