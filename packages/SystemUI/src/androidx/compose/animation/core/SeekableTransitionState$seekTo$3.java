package androidx.compose.animation.core;

import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SeekableTransitionState$seekTo$3 extends SuspendLambda implements Function1 {
    final /* synthetic */ float $fraction;
    final /* synthetic */ Object $oldTargetState;
    final /* synthetic */ Object $targetState;
    final /* synthetic */ Transition<Object> $transition;
    int label;
    final /* synthetic */ SeekableTransitionState<Object> this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.animation.core.SeekableTransitionState$seekTo$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ float $fraction;
        final /* synthetic */ Object $oldTargetState;
        final /* synthetic */ Object $targetState;
        final /* synthetic */ Transition<Object> $transition;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SeekableTransitionState<Object> this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                SeekableTransitionState.Companion companion = SeekableTransitionState.Companion;
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
            SeekableTransitionState.Companion companion2 = SeekableTransitionState.Companion;
            seekableTransitionState5.seekToFraction();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekableTransitionState$seekTo$3(Object obj, Object obj2, SeekableTransitionState<Object> seekableTransitionState, Transition<Object> transition, float f, Continuation continuation) {
        super(1, continuation);
        this.$targetState = obj;
        this.$oldTargetState = obj2;
        this.this$0 = seekableTransitionState;
        this.$transition = transition;
        this.$fraction = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new SeekableTransitionState$seekTo$3(this.$targetState, this.$oldTargetState, this.this$0, this.$transition, this.$fraction, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((SeekableTransitionState$seekTo$3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
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
