package androidx.activity.compose;

import androidx.activity.OnBackPressedCallback;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OnBackInstance$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ OnBackPressedCallback $callback;
    final /* synthetic */ Function2 $onBack;
    Object L$0;
    int label;
    final /* synthetic */ OnBackInstance this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.activity.compose.OnBackInstance$job$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ Ref$BooleanRef $completed;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(3, continuation);
            this.$completed = ref$BooleanRef;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new AnonymousClass1(this.$completed, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$completed.element = true;
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OnBackInstance$job$1(OnBackPressedCallback onBackPressedCallback, Function2 function2, OnBackInstance onBackInstance, Continuation continuation) {
        super(2, continuation);
        this.$callback = onBackPressedCallback;
        this.$onBack = function2;
        this.this$0 = onBackInstance;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new OnBackInstance$job$1(this.$callback, this.$onBack, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OnBackInstance$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref$BooleanRef ref$BooleanRef;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$callback.isEnabled) {
                Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                Function2 function2 = this.$onBack;
                FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 flowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(new ChannelAsFlow(this.this$0.channel, true, null, 0, null, 28, null), new AnonymousClass1(ref$BooleanRef2, null));
                this.L$0 = ref$BooleanRef2;
                this.label = 1;
                if (function2.invoke(flowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                ref$BooleanRef = ref$BooleanRef2;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ref$BooleanRef = (Ref$BooleanRef) this.L$0;
        ResultKt.throwOnFailure(obj);
        if (!ref$BooleanRef.element) {
            throw new IllegalStateException("You must collect the progress flow");
        }
        return Unit.INSTANCE;
    }
}
