package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl$events$1$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScope$toState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $initial;
    final /* synthetic */ StateFlow $this_toState;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.kairos.BuildScope$toState$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $initial;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Object obj, Continuation continuation) {
            super(2, continuation);
            this.$initial = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$initial, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(Intrinsics.areEqual(this.L$0, this.$initial));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScope$toState$1(StateFlow stateFlow, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$this_toState = stateFlow;
        this.$initial = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildScope$toState$1 buildScope$toState$1 = new BuildScope$toState$1(this.$this_toState, this.$initial, continuation);
        buildScope$toState$1.L$0 = obj;
        return buildScope$toState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScope$toState$1) create((EventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final EventProducerScope eventProducerScope = (EventProducerScope) this.L$0;
            FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1 flowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1 = new FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1(this.$this_toState, new AnonymousClass1(this.$initial, null));
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.kairos.BuildScope$toState$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object emit = ((BuildScopeImpl$events$1$1) EventProducerScope.this).$events.emit(obj2, continuation);
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (emit != coroutineSingletons2) {
                        emit = Unit.INSTANCE;
                    }
                    return emit == coroutineSingletons2 ? emit : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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
