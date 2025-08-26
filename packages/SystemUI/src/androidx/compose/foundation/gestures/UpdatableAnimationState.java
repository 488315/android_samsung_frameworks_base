package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.animation.core.VectorizedAnimationSpec;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.ui.MotionDurationScale;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public final class UpdatableAnimationState {
    public static final Companion Companion = new Companion(null);
    public static final AnimationVector1D ZeroVector = new AnimationVector1D(0.0f);
    public boolean isRunning;
    public long lastFrameTime;
    public AnimationVector1D lastVelocity;
    public float value;
    public final VectorizedAnimationSpec vectorizedSpec;

    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.UpdatableAnimationState$animateToZero$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        float F$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UpdatableAnimationState.this.animateToZero(null, null, this);
        }
    }

    public UpdatableAnimationState(AnimationSpec<Float> animationSpec) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        this.vectorizedSpec = animationSpec.vectorize(VectorConvertersKt.FloatToVector);
        this.lastFrameTime = Long.MIN_VALUE;
        this.lastVelocity = ZeroVector;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e4, code lost:
    
        if (androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r0.getContext()).withFrameNanos(r15, r0) == r1) goto L47;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0082 A[Catch: all -> 0x00f3, PHI: r12 r13 r14 r15
      0x0082: PHI (r12v5 'this' androidx.compose.foundation.gestures.UpdatableAnimationState) = 
      (r12v0 'this' androidx.compose.foundation.gestures.UpdatableAnimationState A[IMMUTABLE_TYPE, THIS])
      (r12v6 'this' androidx.compose.foundation.gestures.UpdatableAnimationState)
     binds: [B:31:0x0080, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE]
      0x0082: PHI (r13v5 kotlin.jvm.functions.Function1) = (r13v23 kotlin.jvm.functions.Function1), (r13v24 kotlin.jvm.functions.Function1) binds: [B:31:0x0080, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE]
      0x0082: PHI (r14v7 kotlin.jvm.functions.Function0) = (r14v0 kotlin.jvm.functions.Function0), (r14v8 kotlin.jvm.functions.Function0) binds: [B:31:0x0080, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE]
      0x0082: PHI (r15v14 float) = (r15v7 float), (r15v15 float) binds: [B:31:0x0080, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #0 {all -> 0x00f3, blocks: (B:38:0x00b7, B:32:0x0082, B:35:0x0099), top: B:55:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0094 A[PHI: r12 r13 r14
      0x0094: PHI (r12v3 'this' androidx.compose.foundation.gestures.UpdatableAnimationState) = 
      (r12v5 'this' androidx.compose.foundation.gestures.UpdatableAnimationState)
      (r12v6 'this' androidx.compose.foundation.gestures.UpdatableAnimationState)
     binds: [B:33:0x0092, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE]
      0x0094: PHI (r13v3 kotlin.jvm.functions.Function1) = (r13v25 kotlin.jvm.functions.Function1), (r13v26 kotlin.jvm.functions.Function1) binds: [B:33:0x0092, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE]
      0x0094: PHI (r14v1 kotlin.jvm.functions.Function0) = (r14v7 kotlin.jvm.functions.Function0), (r14v8 kotlin.jvm.functions.Function0) binds: [B:33:0x0092, B:40:0x00bc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0099 A[Catch: all -> 0x00f3, TryCatch #0 {all -> 0x00f3, blocks: (B:38:0x00b7, B:32:0x0082, B:35:0x0099), top: B:55:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x00b4 -> B:55:0x00b7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object animateToZero(Function1 function1, Function0 function0, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        final float scaleFactor;
        Function1 function12;
        Function0 function02;
        final Function1 function13;
        float f;
        UpdatableAnimationState updatableAnimationState;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        AnimationVector1D animationVector1D = ZeroVector;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.isRunning) {
                    InlineClassHelperKt.throwIllegalStateException("animateToZero called while previous animation is running");
                }
                MotionDurationScale motionDurationScale = (MotionDurationScale) anonymousClass1.getContext().get(MotionDurationScale.Key);
                scaleFactor = motionDurationScale != null ? motionDurationScale.getScaleFactor() : 1.0f;
                this.isRunning = true;
                function13 = function1;
                Companion companion = Companion;
                f = this.value;
                companion.getClass();
                function12 = function13;
                if (Math.abs(f) < 0.01f) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                function02 = (Function0) anonymousClass1.L$1;
                UpdatableAnimationState updatableAnimationState2 = (UpdatableAnimationState) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                function1 = updatableAnimationState2;
                function02.invoke();
                updatableAnimationState = function1;
                updatableAnimationState.lastFrameTime = Long.MIN_VALUE;
                updatableAnimationState.lastVelocity = animationVector1D;
                updatableAnimationState.isRunning = false;
                return Unit.INSTANCE;
            }
            float f2 = anonymousClass1.F$0;
            Function0 function03 = (Function0) anonymousClass1.L$2;
            Function1 function14 = (Function1) anonymousClass1.L$1;
            UpdatableAnimationState updatableAnimationState3 = (UpdatableAnimationState) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                function0 = function03;
                Function1 function15 = function14;
                scaleFactor = f2;
                this = updatableAnimationState3;
                try {
                    function0.invoke();
                    function13 = function15;
                    function12 = function15;
                    if (scaleFactor == 0.0f) {
                        Companion companion2 = Companion;
                        f = this.value;
                        companion2.getClass();
                        function12 = function13;
                        if (Math.abs(f) < 0.01f) {
                            Function1 function16 = new Function1() { // from class: androidx.compose.foundation.gestures.UpdatableAnimationState.animateToZero.4
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    long jRoundToLong;
                                    long jLongValue = ((Number) obj2).longValue();
                                    UpdatableAnimationState updatableAnimationState4 = UpdatableAnimationState.this;
                                    if (updatableAnimationState4.lastFrameTime == Long.MIN_VALUE) {
                                        updatableAnimationState4.lastFrameTime = jLongValue;
                                    }
                                    AnimationVector1D animationVector1D2 = new AnimationVector1D(updatableAnimationState4.value);
                                    if (scaleFactor == 0.0f) {
                                        UpdatableAnimationState updatableAnimationState5 = UpdatableAnimationState.this;
                                        VectorizedAnimationSpec vectorizedAnimationSpec = updatableAnimationState5.vectorizedSpec;
                                        AnimationVector1D animationVector1D3 = new AnimationVector1D(updatableAnimationState5.value);
                                        UpdatableAnimationState.Companion.getClass();
                                        jRoundToLong = vectorizedAnimationSpec.getDurationNanos(animationVector1D3, UpdatableAnimationState.ZeroVector, UpdatableAnimationState.this.lastVelocity);
                                    } else {
                                        jRoundToLong = MathKt__MathJVMKt.roundToLong((jLongValue - UpdatableAnimationState.this.lastFrameTime) / r10);
                                    }
                                    long j = jRoundToLong;
                                    VectorizedAnimationSpec vectorizedAnimationSpec2 = UpdatableAnimationState.this.vectorizedSpec;
                                    UpdatableAnimationState.Companion.getClass();
                                    AnimationVector1D animationVector1D4 = UpdatableAnimationState.ZeroVector;
                                    float f3 = ((AnimationVector1D) vectorizedAnimationSpec2.getValueFromNanos(j, animationVector1D2, animationVector1D4, UpdatableAnimationState.this.lastVelocity)).value;
                                    UpdatableAnimationState updatableAnimationState6 = UpdatableAnimationState.this;
                                    updatableAnimationState6.lastVelocity = (AnimationVector1D) updatableAnimationState6.vectorizedSpec.getVelocityFromNanos(j, animationVector1D2, animationVector1D4, updatableAnimationState6.lastVelocity);
                                    UpdatableAnimationState updatableAnimationState7 = UpdatableAnimationState.this;
                                    updatableAnimationState7.lastFrameTime = jLongValue;
                                    float f4 = updatableAnimationState7.value - f3;
                                    updatableAnimationState7.value = f3;
                                    function13.mo781invoke(Float.valueOf(f4));
                                    return Unit.INSTANCE;
                                }
                            };
                            anonymousClass1.L$0 = this;
                            anonymousClass1.L$1 = function13;
                            anonymousClass1.L$2 = function0;
                            anonymousClass1.F$0 = scaleFactor;
                            anonymousClass1.label = 1;
                            function15 = function13;
                            if (MonotonicFrameClockKt.getMonotonicFrameClock(anonymousClass1.getContext()).withFrameNanos(function16, anonymousClass1) == coroutineSingletons) {
                            }
                            function0.invoke();
                            function13 = function15;
                            function12 = function15;
                            if (scaleFactor == 0.0f) {
                            }
                        }
                        return coroutineSingletons;
                    }
                    final Function1 function17 = function12;
                    UpdatableAnimationState updatableAnimationState4 = this;
                    function02 = function0;
                    if (Math.abs(updatableAnimationState4.value) == 0.0f) {
                        updatableAnimationState = updatableAnimationState4;
                    } else {
                        Function1 function18 = new Function1() { // from class: androidx.compose.foundation.gestures.UpdatableAnimationState.animateToZero.5
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                ((Number) obj2).longValue();
                                UpdatableAnimationState updatableAnimationState5 = UpdatableAnimationState.this;
                                float f3 = updatableAnimationState5.value;
                                updatableAnimationState5.value = 0.0f;
                                function17.mo781invoke(Float.valueOf(f3));
                                return Unit.INSTANCE;
                            }
                        };
                        anonymousClass1.L$0 = updatableAnimationState4;
                        anonymousClass1.L$1 = function02;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.label = 2;
                        function1 = updatableAnimationState4;
                    }
                    updatableAnimationState.lastFrameTime = Long.MIN_VALUE;
                    updatableAnimationState.lastVelocity = animationVector1D;
                    updatableAnimationState.isRunning = false;
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    function1 = this;
                    th = th;
                    function1.lastFrameTime = Long.MIN_VALUE;
                    function1.lastVelocity = animationVector1D;
                    function1.isRunning = false;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                function1 = updatableAnimationState3;
                function1.lastFrameTime = Long.MIN_VALUE;
                function1.lastVelocity = animationVector1D;
                function1.isRunning = false;
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
