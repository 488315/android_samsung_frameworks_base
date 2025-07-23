package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Output;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class Network$evalOutputs$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ EvalScope $evalScope;
    final /* synthetic */ Ref$BooleanRef $launchedAny;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ Network this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.kairos.internal.Network$evalOutputs$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ EvalScope $evalScope;
        final /* synthetic */ ArrayDeque $outputs;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.kairos.internal.Network$evalOutputs$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01101 extends SuspendLambda implements Function2 {
            final /* synthetic */ EvalScope $evalScope;
            final /* synthetic */ Output $output;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01101(Output output, EvalScope evalScope, Continuation continuation) {
                super(2, continuation);
                this.$output = output;
                this.$evalScope = evalScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01101(this.$output, this.$evalScope, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Output output = this.$output;
                EvalScope evalScope = this.$evalScope;
                Object obj2 = output.result;
                Output.NoResult noResult = Output.NoResult.INSTANCE;
                if (obj2 == noResult) {
                    throw new IllegalStateException("output visited with null upstream result");
                }
                output.result = noResult;
                output.onEmit.invoke(evalScope, obj2);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ArrayDeque arrayDeque, EvalScope evalScope, Continuation continuation) {
            super(2, continuation);
            this.$outputs = arrayDeque;
            this.$evalScope = evalScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$outputs, this.$evalScope, continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            while (!this.$outputs.isEmpty()) {
                BuildersKt.launch$default(coroutineScope, null, null, new C01101((Output) this.$outputs.removeFirst(), this.$evalScope, null), 3);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Network$evalOutputs$2(Network network, Ref$BooleanRef ref$BooleanRef, EvalScope evalScope, Continuation continuation) {
        super(2, continuation);
        this.this$0 = network;
        this.$launchedAny = ref$BooleanRef;
        this.$evalScope = evalScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        Network$evalOutputs$2 network$evalOutputs$2 = new Network$evalOutputs$2(this.this$0, this.$launchedAny, this.$evalScope, continuation);
        network$evalOutputs$2.L$0 = obj;
        return network$evalOutputs$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Network$evalOutputs$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        for (Map.Entry entry : this.this$0.outputsByDispatcher.entrySet()) {
            ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) entry.getKey();
            ArrayDeque arrayDeque = (ArrayDeque) entry.getValue();
            if (!arrayDeque.isEmpty()) {
                this.$launchedAny.element = true;
                BuildersKt.launch$default(coroutineScope, continuationInterceptor, null, new AnonymousClass1(arrayDeque, this.$evalScope, null), 2);
            }
        }
        return Unit.INSTANCE;
    }
}
