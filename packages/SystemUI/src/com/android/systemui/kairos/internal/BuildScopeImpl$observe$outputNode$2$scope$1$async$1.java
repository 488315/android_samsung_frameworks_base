package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.KairosCoroutineScope;
import com.android.systemui.kairos.LocalNetwork;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScopeImpl$observe$outputNode$2$scope$1$async$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ LocalNetwork $localNetwork;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.kairos.internal.BuildScopeImpl$observe$outputNode$2$scope$1$async$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements KairosCoroutineScope, CoroutineScope {
        public final /* synthetic */ CoroutineScope $$delegate_0;
        public final /* synthetic */ LocalNetwork $localNetwork;

        public AnonymousClass1(CoroutineScope coroutineScope, LocalNetwork localNetwork) {
            this.$localNetwork = localNetwork;
            this.$$delegate_0 = coroutineScope;
        }

        @Override // kotlinx.coroutines.CoroutineScope
        public final CoroutineContext getCoroutineContext() {
            return this.$$delegate_0.getCoroutineContext();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScopeImpl$observe$outputNode$2$scope$1$async$1(Function2 function2, LocalNetwork localNetwork, Continuation continuation) {
        super(2, continuation);
        this.$block = function2;
        this.$localNetwork = localNetwork;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildScopeImpl$observe$outputNode$2$scope$1$async$1 buildScopeImpl$observe$outputNode$2$scope$1$async$1 = new BuildScopeImpl$observe$outputNode$2$scope$1$async$1(this.$block, this.$localNetwork, continuation);
        buildScopeImpl$observe$outputNode$2$scope$1$async$1.L$0 = obj;
        return buildScopeImpl$observe$outputNode$2$scope$1$async$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScopeImpl$observe$outputNode$2$scope$1$async$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        Function2 function2 = this.$block;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(coroutineScope, this.$localNetwork);
        this.label = 1;
        Object invoke = function2.invoke(anonymousClass1, this);
        return invoke == coroutineSingletons ? coroutineSingletons : invoke;
    }
}
