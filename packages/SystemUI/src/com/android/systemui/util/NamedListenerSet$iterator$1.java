package com.android.systemui.util;

import com.android.systemui.util.NamedListenerSet;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

final class NamedListenerSet$iterator$1 extends RestrictedSuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ NamedListenerSet<Object> this$0;

    public NamedListenerSet$iterator$1(NamedListenerSet<Object> namedListenerSet, Continuation continuation) {
        super(2, continuation);
        this.this$0 = namedListenerSet;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NamedListenerSet$iterator$1 namedListenerSet$iterator$1 = new NamedListenerSet$iterator$1(this.this$0, continuation);
        namedListenerSet$iterator$1.L$0 = obj;
        return namedListenerSet$iterator$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CopyOnWriteArrayList copyOnWriteArrayList;
        Iterator it;
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            copyOnWriteArrayList = ((NamedListenerSet) this.this$0).listeners;
            it = copyOnWriteArrayList.iterator();
            sequenceScope = sequenceScope2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (Iterator) this.L$1;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            Object listener = ((NamedListenerSet.NamedListener) it.next()).getListener();
            this.L$0 = sequenceScope;
            this.L$1 = it;
            this.label = 1;
            if (sequenceScope.yield(listener, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
        return ((NamedListenerSet$iterator$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
