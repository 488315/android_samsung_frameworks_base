package androidx.compose.animation.core;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SeekableTransitionState$animateTo$2 extends SuspendLambda implements Function1 {
    final /* synthetic */ FiniteAnimationSpec<Float> $animationSpec;
    final /* synthetic */ Object $targetState;
    final /* synthetic */ Transition<Object> $transition;
    int label;
    final /* synthetic */ SeekableTransitionState<Object> this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:17:0x01e7, code lost:
        
            if (androidx.compose.animation.core.SeekableTransitionState.access$waitForComposition(r2, r20) == r1) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x01d5, code lost:
        
            if (androidx.compose.animation.core.SeekableTransitionState.access$runAnimations(r2, r20) == r1) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00d7, code lost:
        
            if (androidx.compose.animation.core.SeekableTransitionState.access$waitForCompositionAfterTargetStateChange(r2, r20) == r1) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x00cb, code lost:
        
            if (r2 == r1) goto L84;
         */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00ed  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r21) {
            /*
                Method dump skipped, instructions count: 505
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.SeekableTransitionState$animateTo$2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
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
    public final Object mo779invoke(Object obj) {
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
