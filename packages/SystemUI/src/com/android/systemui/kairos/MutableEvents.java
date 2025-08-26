package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.Network;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DeferredCoroutine;

/* loaded from: classes2.dex */
public final class MutableEvents extends Events {
    public final InputNode impl;
    public final Network network;
    public final AtomicReference storage;

    /* renamed from: com.android.systemui.kairos.MutableEvents$emit$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $value;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Object obj, Continuation continuation) {
            super(2, continuation);
            this.$value = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = MutableEvents.this.new AnonymousClass2(this.$value, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                DeferredCoroutine deferredCoroutineAsync$default = BuildersKt.async$default(coroutineScope, CoroutineStart.LAZY, new MutableEvents$emit$2$newEmit$1(ref$ObjectRef, MutableEvents.this, this.$value, null), 1);
                ref$ObjectRef.element = MutableEvents.this.storage.getAndSet(deferredCoroutineAsync$default);
                this.label = 1;
                if (deferredCoroutineAsync$default.awaitInternal(this) == coroutineSingletons) {
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

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ MutableEvents(Network network, InputNode inputNode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            inputNode = new InputNode(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
        this(network, inputNode);
    }

    public final Object emit(Object obj, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(obj, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(MutableEvents.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }

    public MutableEvents(Network network, InputNode inputNode) {
        super(null);
        this.network = network;
        this.impl = inputNode;
        this.storage = new AtomicReference(null);
    }
}
