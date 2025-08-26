package androidx.compose.animation.core;

import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.collection.MutableVector;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes.dex */
final class InfiniteTransition$run$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<State<Long>> $toolingOverride;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ InfiniteTransition this$0;

    /* renamed from: androidx.compose.animation.core.InfiniteTransition$run$1$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ float F$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
            anonymousClass3.F$0 = ((Number) obj).floatValue();
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.F$0 > 0.0f);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InfiniteTransition$run$1$1(MutableState<State<Long>> mutableState, InfiniteTransition infiniteTransition, Continuation continuation) {
        super(2, continuation);
        this.$toolingOverride = mutableState;
        this.this$0 = infiniteTransition;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        InfiniteTransition$run$1$1 infiniteTransition$run$1$1 = new InfiniteTransition$run$1$1(this.$toolingOverride, this.this$0, continuation);
        infiniteTransition$run$1$1.L$0 = obj;
        return infiniteTransition$run$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((InfiniteTransition$run$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Path cross not found for [B:11:0x003e, B:16:0x005b], limit reached: 18 */
    /* JADX WARN: Path cross not found for [B:16:0x005b, B:11:0x003e], limit reached: 18 */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0054 A[PHI: r1 r8
      0x0054: PHI (r1v2 kotlin.jvm.internal.Ref$FloatRef) = (r1v3 kotlin.jvm.internal.Ref$FloatRef), (r1v5 kotlin.jvm.internal.Ref$FloatRef) binds: [B:12:0x0051, B:9:0x0021] A[DONT_GENERATE, DONT_INLINE]
      0x0054: PHI (r8v3 kotlinx.coroutines.CoroutineScope) = (r8v4 kotlinx.coroutines.CoroutineScope), (r8v5 kotlinx.coroutines.CoroutineScope) binds: [B:12:0x0051, B:9:0x0021] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005b  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0059 -> B:11:0x003e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0074 -> B:11:0x003e). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        final CoroutineScope coroutineScope;
        final Ref$FloatRef ref$FloatRef;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            coroutineScope = (CoroutineScope) this.L$0;
            ref$FloatRef = new Ref$FloatRef();
            ref$FloatRef.element = 1.0f;
        } else if (i == 1) {
            ref$FloatRef = (Ref$FloatRef) this.L$1;
            CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            coroutineScope = coroutineScope2;
            if (ref$FloatRef.element == 0.0f) {
                SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new Function0() { // from class: androidx.compose.animation.core.InfiniteTransition$run$1$1.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Float.valueOf(SuspendAnimationKt.getDurationScale(coroutineScope.getCoroutineContext()));
                    }
                });
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(null);
                this.L$0 = coroutineScope;
                this.L$1 = ref$FloatRef;
                this.label = 2;
                if (FlowKt.first(safeFlowSnapshotFlow, anonymousClass3, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$FloatRef = (Ref$FloatRef) this.L$1;
            CoroutineScope coroutineScope3 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            coroutineScope = coroutineScope3;
        }
        final MutableState<State<Long>> mutableState = this.$toolingOverride;
        final InfiniteTransition infiniteTransition = this.this$0;
        Function1 function1 = new Function1() { // from class: androidx.compose.animation.core.InfiniteTransition$run$1$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                long jLongValue = ((Number) obj2).longValue();
                State state = (State) mutableState.getValue();
                long jLongValue2 = state != null ? ((Number) state.getValue()).longValue() : jLongValue;
                if (infiniteTransition.startTimeNanos == Long.MIN_VALUE || ref$FloatRef.element != SuspendAnimationKt.getDurationScale(coroutineScope.getCoroutineContext())) {
                    InfiniteTransition infiniteTransition2 = infiniteTransition;
                    infiniteTransition2.startTimeNanos = jLongValue;
                    MutableVector mutableVector = infiniteTransition2._animations;
                    Object[] objArr = mutableVector.content;
                    int i2 = mutableVector.size;
                    for (int i3 = 0; i3 < i2; i3++) {
                        ((InfiniteTransition.TransitionAnimationState) objArr[i3]).startOnTheNextFrame = true;
                    }
                    ref$FloatRef.element = SuspendAnimationKt.getDurationScale(coroutineScope.getCoroutineContext());
                }
                float f = ref$FloatRef.element;
                if (f == 0.0f) {
                    MutableVector mutableVector2 = infiniteTransition._animations;
                    Object[] objArr2 = mutableVector2.content;
                    int i4 = mutableVector2.size;
                    for (int i5 = 0; i5 < i4; i5++) {
                        InfiniteTransition.TransitionAnimationState transitionAnimationState = (InfiniteTransition.TransitionAnimationState) objArr2[i5];
                        ((SnapshotMutableStateImpl) transitionAnimationState.value$delegate).setValue(transitionAnimationState.animation.mutableTargetValue);
                        transitionAnimationState.startOnTheNextFrame = true;
                    }
                } else {
                    InfiniteTransition infiniteTransition3 = infiniteTransition;
                    long j = (long) ((jLongValue2 - infiniteTransition3.startTimeNanos) / f);
                    MutableVector mutableVector3 = infiniteTransition3._animations;
                    Object[] objArr3 = mutableVector3.content;
                    int i6 = mutableVector3.size;
                    boolean z = true;
                    for (int i7 = 0; i7 < i6; i7++) {
                        InfiniteTransition.TransitionAnimationState transitionAnimationState2 = (InfiniteTransition.TransitionAnimationState) objArr3[i7];
                        if (!transitionAnimationState2.isFinished) {
                            ((SnapshotMutableStateImpl) InfiniteTransition.this.refreshChildNeeded$delegate).setValue(Boolean.FALSE);
                            if (transitionAnimationState2.startOnTheNextFrame) {
                                transitionAnimationState2.startOnTheNextFrame = false;
                                transitionAnimationState2.playTimeNanosOffset = j;
                            }
                            long j2 = j - transitionAnimationState2.playTimeNanosOffset;
                            ((SnapshotMutableStateImpl) transitionAnimationState2.value$delegate).setValue(transitionAnimationState2.animation.getValueFromNanos(j2));
                            transitionAnimationState2.isFinished = transitionAnimationState2.animation.isFinishedFromNanos(j2);
                        }
                        if (!transitionAnimationState2.isFinished) {
                            z = false;
                        }
                    }
                    ((SnapshotMutableStateImpl) infiniteTransition3.isRunning$delegate).setValue(Boolean.valueOf(!z));
                }
                return Unit.INSTANCE;
            }
        };
        this.L$0 = coroutineScope;
        this.L$1 = ref$FloatRef;
        this.label = 1;
        if (InfiniteAnimationPolicyKt.withInfiniteAnimationFrameNanos(function1, this) != coroutineSingletons) {
            if (ref$FloatRef.element == 0.0f) {
            }
            final MutableState<State<Long>> mutableState2 = this.$toolingOverride;
            final InfiniteTransition infiniteTransition2 = this.this$0;
            Function1 function12 = new Function1() { // from class: androidx.compose.animation.core.InfiniteTransition$run$1$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    long jLongValue = ((Number) obj2).longValue();
                    State state = (State) mutableState2.getValue();
                    long jLongValue2 = state != null ? ((Number) state.getValue()).longValue() : jLongValue;
                    if (infiniteTransition2.startTimeNanos == Long.MIN_VALUE || ref$FloatRef.element != SuspendAnimationKt.getDurationScale(coroutineScope.getCoroutineContext())) {
                        InfiniteTransition infiniteTransition22 = infiniteTransition2;
                        infiniteTransition22.startTimeNanos = jLongValue;
                        MutableVector mutableVector = infiniteTransition22._animations;
                        Object[] objArr = mutableVector.content;
                        int i2 = mutableVector.size;
                        for (int i3 = 0; i3 < i2; i3++) {
                            ((InfiniteTransition.TransitionAnimationState) objArr[i3]).startOnTheNextFrame = true;
                        }
                        ref$FloatRef.element = SuspendAnimationKt.getDurationScale(coroutineScope.getCoroutineContext());
                    }
                    float f = ref$FloatRef.element;
                    if (f == 0.0f) {
                        MutableVector mutableVector2 = infiniteTransition2._animations;
                        Object[] objArr2 = mutableVector2.content;
                        int i4 = mutableVector2.size;
                        for (int i5 = 0; i5 < i4; i5++) {
                            InfiniteTransition.TransitionAnimationState transitionAnimationState = (InfiniteTransition.TransitionAnimationState) objArr2[i5];
                            ((SnapshotMutableStateImpl) transitionAnimationState.value$delegate).setValue(transitionAnimationState.animation.mutableTargetValue);
                            transitionAnimationState.startOnTheNextFrame = true;
                        }
                    } else {
                        InfiniteTransition infiniteTransition3 = infiniteTransition2;
                        long j = (long) ((jLongValue2 - infiniteTransition3.startTimeNanos) / f);
                        MutableVector mutableVector3 = infiniteTransition3._animations;
                        Object[] objArr3 = mutableVector3.content;
                        int i6 = mutableVector3.size;
                        boolean z = true;
                        for (int i7 = 0; i7 < i6; i7++) {
                            InfiniteTransition.TransitionAnimationState transitionAnimationState2 = (InfiniteTransition.TransitionAnimationState) objArr3[i7];
                            if (!transitionAnimationState2.isFinished) {
                                ((SnapshotMutableStateImpl) InfiniteTransition.this.refreshChildNeeded$delegate).setValue(Boolean.FALSE);
                                if (transitionAnimationState2.startOnTheNextFrame) {
                                    transitionAnimationState2.startOnTheNextFrame = false;
                                    transitionAnimationState2.playTimeNanosOffset = j;
                                }
                                long j2 = j - transitionAnimationState2.playTimeNanosOffset;
                                ((SnapshotMutableStateImpl) transitionAnimationState2.value$delegate).setValue(transitionAnimationState2.animation.getValueFromNanos(j2));
                                transitionAnimationState2.isFinished = transitionAnimationState2.animation.isFinishedFromNanos(j2);
                            }
                            if (!transitionAnimationState2.isFinished) {
                                z = false;
                            }
                        }
                        ((SnapshotMutableStateImpl) infiniteTransition3.isRunning$delegate).setValue(Boolean.valueOf(!z));
                    }
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = coroutineScope;
            this.L$1 = ref$FloatRef;
            this.label = 1;
            if (InfiniteAnimationPolicyKt.withInfiniteAnimationFrameNanos(function12, this) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
