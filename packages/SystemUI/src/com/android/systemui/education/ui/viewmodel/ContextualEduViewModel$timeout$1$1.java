package com.android.systemui.education.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class ContextualEduViewModel$timeout$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $emitAfterTimeout;
    final /* synthetic */ Object $it;
    final /* synthetic */ long $timeoutMillis;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextualEduViewModel$timeout$1$1(Object obj, long j, Object obj2, Continuation continuation) {
        super(2, continuation);
        this.$it = obj;
        this.$timeoutMillis = j;
        this.$emitAfterTimeout = obj2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ContextualEduViewModel$timeout$1$1 contextualEduViewModel$timeout$1$1 = new ContextualEduViewModel$timeout$1$1(this.$it, this.$timeoutMillis, this.$emitAfterTimeout, continuation);
        contextualEduViewModel$timeout$1$1.L$0 = obj;
        return contextualEduViewModel$timeout$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ContextualEduViewModel$timeout$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (r1.emit(r7, r6) != r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            Object obj2 = this.$it;
            this.L$0 = flowCollector2;
            this.label = 1;
            if (flowCollector2.emit(obj2, this) != coroutineSingletons) {
                flowCollector = flowCollector2;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            Object obj3 = this.$emitAfterTimeout;
            this.L$0 = null;
            this.label = 3;
        }
        long j = this.$timeoutMillis;
        this.L$0 = flowCollector;
        this.label = 2;
        if (DelayKt.delay(j, this) != coroutineSingletons) {
            Object obj32 = this.$emitAfterTimeout;
            this.L$0 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
