package com.android.systemui.kairos;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.CompletableLazy;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.GraphKt;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.Network;
import com.android.systemui.kairos.internal.TransactionCache;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class CoalescingMutableEvents extends Events {
    public final Function2 coalesce;
    public final Function0 getInitialValue;
    public final InputNode impl;
    public final String name;
    public final Network network;
    public final AtomicReference storage;

    /* renamed from: com.android.systemui.kairos.CoalescingMutableEvents$emit$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = CoalescingMutableEvents.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            EvalScope evalScope = (EvalScope) this.L$0;
            CoalescingMutableEvents coalescingMutableEvents = CoalescingMutableEvents.this;
            Lazy lazy = (Lazy) ((Pair) coalescingMutableEvents.storage.getAndSet(new Pair(Boolean.FALSE, LazyKt__LazyJVMKt.lazy(new CoalescingMutableEvents$$ExternalSyntheticLambda0(coalescingMutableEvents, 1))))).component2();
            InputNode inputNode = CoalescingMutableEvents.this.impl;
            Object value = lazy.getValue();
            TransactionCache transactionCache = inputNode.transactionCache;
            transactionCache.getClass();
            transactionCache.epoch = evalScope.getEpoch();
            evalScope.getTransactionStore().set(transactionCache.key, value);
            if (!GraphKt.scheduleAll(inputNode.downstreamSet, evalScope)) {
                evalScope.scheduleDeactivation(inputNode);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ CoalescingMutableEvents(String str, Function2 function2, Network network, Function0 function0, InputNode inputNode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 16) != 0) {
            inputNode = new InputNode(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
        this(str, function2, network, function0, inputNode);
    }

    public final void emit(final Object obj) {
        if (((Boolean) ((Pair) this.storage.getAndUpdate(new UnaryOperator() { // from class: com.android.systemui.kairos.CoalescingMutableEvents.emit.1
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return new Pair(Boolean.TRUE, new CompletableLazy(CoalescingMutableEvents.this.coalesce.invoke((Lazy) ((Pair) obj2).component2(), obj), null, 2, null));
            }
        })).component1()).booleanValue()) {
            return;
        }
        String str = this.name;
        String strM = str != null ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", str, ")") : null;
        if (strM == null) {
            strM = "";
        }
        this.network.transaction(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("CoalescingMutableEvents", strM, ".emit"), new AnonymousClass3(null));
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(CoalescingMutableEvents.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }

    public CoalescingMutableEvents(String str, Function2 function2, Network network, Function0 function0, InputNode inputNode) {
        super(null);
        this.name = str;
        this.coalesce = function2;
        this.network = network;
        this.getInitialValue = function0;
        this.impl = inputNode;
        this.storage = new AtomicReference(new Pair(Boolean.FALSE, LazyKt__LazyJVMKt.lazy(new CoalescingMutableEvents$$ExternalSyntheticLambda0(this, 0))));
    }
}
