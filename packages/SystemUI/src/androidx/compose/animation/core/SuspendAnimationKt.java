package androidx.compose.animation.core;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.MotionDurationScale;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FloatCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SuspendAnimationKt {
    public static final Object animate(float f, float f2, float f3, AnimationSpec animationSpec, final Function2 function2, SuspendLambda suspendLambda) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        final TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
        Float f4 = new Float(f);
        Float f5 = new Float(f2);
        TwoWayConverterImpl twoWayConverterImpl = (TwoWayConverterImpl) twoWayConverter;
        AnimationVector animationVector = (AnimationVector) twoWayConverterImpl.convertToVector.mo779invoke(new Float(f3));
        if (animationVector == null) {
            animationVector = ((AnimationVector) twoWayConverterImpl.convertToVector.mo779invoke(f4)).newVector$animation_core();
        }
        AnimationVector animationVector2 = animationVector;
        Object animate = animate(new AnimationState(twoWayConverter, f4, animationVector2, 0L, 0L, false, 56, null), new TargetBasedAnimation((AnimationSpec<Float>) animationSpec, (TwoWayConverter<Float, AnimationVector>) twoWayConverter, f4, f5, animationVector2), Long.MIN_VALUE, new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt$animate$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                AnimationScope animationScope = (AnimationScope) obj;
                Function2.this.invoke(((SnapshotMutableStateImpl) animationScope.value$delegate).getValue(), ((TwoWayConverterImpl) twoWayConverter).convertFromVector.mo779invoke(animationScope.velocityVector));
                return Unit.INSTANCE;
            }
        }, suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (animate != coroutineSingletons) {
            animate = Unit.INSTANCE;
        }
        return animate == coroutineSingletons ? animate : Unit.INSTANCE;
    }

    public static /* synthetic */ Object animate$default(float f, AnimationSpec animationSpec, Function2 function2, SuspendLambda suspendLambda, int i) {
        if ((i & 8) != 0) {
            animationSpec = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
        }
        return animate(0.0f, f, 0.0f, animationSpec, function2, suspendLambda);
    }

    public static final Object animateDecay(AnimationState animationState, DecayAnimationSpec decayAnimationSpec, boolean z, Function1 function1, ContinuationImpl continuationImpl) {
        Object animate = animate(animationState, new DecayAnimation((DecayAnimationSpec<Object>) decayAnimationSpec, (TwoWayConverter<Object, AnimationVector>) animationState.typeConverter, ((SnapshotMutableStateImpl) animationState.value$delegate).getValue(), animationState.velocityVector), z ? animationState.lastFrameTimeNanos : Long.MIN_VALUE, function1, continuationImpl);
        return animate == CoroutineSingletons.COROUTINE_SUSPENDED ? animate : Unit.INSTANCE;
    }

    public static final Object animateTo(AnimationState animationState, Object obj, AnimationSpec animationSpec, boolean z, Function1 function1, ContinuationImpl continuationImpl) {
        Object animate = animate(animationState, new TargetBasedAnimation((AnimationSpec<Object>) animationSpec, (TwoWayConverter<Object, AnimationVector>) animationState.typeConverter, ((SnapshotMutableStateImpl) animationState.value$delegate).getValue(), obj, animationState.velocityVector), z ? animationState.lastFrameTimeNanos : Long.MIN_VALUE, function1, continuationImpl);
        return animate == CoroutineSingletons.COROUTINE_SUSPENDED ? animate : Unit.INSTANCE;
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
            function1 = new Function1() { // from class: androidx.compose.animation.core.SuspendAnimationKt$animateTo$2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj2) {
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
        function1.mo779invoke(animationScope);
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
    /* JADX WARN: Removed duplicated region for block: B:17:0x0117 A[Catch: CancellationException -> 0x0041, TRY_LEAVE, TryCatch #0 {CancellationException -> 0x0041, blocks: (B:13:0x003c, B:15:0x0100, B:17:0x0117, B:22:0x013a, B:24:0x014a, B:31:0x014f), top: B:12:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0169 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0028  */
    /* JADX WARN: Type inference failed for: r12v0, types: [T, androidx.compose.animation.core.AnimationScope] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object animate(final androidx.compose.animation.core.AnimationState r23, androidx.compose.animation.core.Animation r24, long r25, final kotlin.jvm.functions.Function1 r27, kotlin.coroutines.jvm.internal.ContinuationImpl r28) {
        /*
            Method dump skipped, instructions count: 403
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.SuspendAnimationKt.animate(androidx.compose.animation.core.AnimationState, androidx.compose.animation.core.Animation, long, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
