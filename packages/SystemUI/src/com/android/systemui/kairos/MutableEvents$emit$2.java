package com.android.systemui.kairos;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DeferredCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MutableEvents$emit$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $value;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MutableEvents this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableEvents$emit$2(MutableEvents mutableEvents, Object obj, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mutableEvents;
        this.$value = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MutableEvents$emit$2 mutableEvents$emit$2 = new MutableEvents$emit$2(this.this$0, this.$value, continuation);
        mutableEvents$emit$2.L$0 = obj;
        return mutableEvents$emit$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MutableEvents$emit$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            DeferredCoroutine async$default = BuildersKt.async$default(coroutineScope, CoroutineStart.LAZY, new MutableEvents$emit$2$newEmit$1(ref$ObjectRef, this.this$0, this.$value, null), 1);
            ref$ObjectRef.element = this.this$0.storage.getAndSet(async$default);
            this.label = 1;
            if (async$default.awaitInternal(this) == coroutineSingletons) {
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
