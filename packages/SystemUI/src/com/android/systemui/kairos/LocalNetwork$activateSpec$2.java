package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.StateScopeImpl;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LocalNetwork$activateSpec$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $spec;
    final /* synthetic */ CoalescingMutableEvents $stopEmitter;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LocalNetwork this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalNetwork$activateSpec$2(LocalNetwork localNetwork, CoalescingMutableEvents coalescingMutableEvents, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = localNetwork;
        this.$stopEmitter = coalescingMutableEvents;
        this.$spec = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LocalNetwork$activateSpec$2 localNetwork$activateSpec$2 = new LocalNetwork$activateSpec$2(this.this$0, this.$stopEmitter, this.$spec, continuation);
        localNetwork$activateSpec$2.L$0 = obj;
        return localNetwork$activateSpec$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LocalNetwork$activateSpec$2) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        EvalScope evalScope = (EvalScope) this.L$0;
        final CoalescingMutableEvents coalescingMutableEvents = this.$stopEmitter;
        final LocalNetwork localNetwork = this.this$0;
        BuildScopeImpl buildScopeImpl = new BuildScopeImpl(new StateScopeImpl(evalScope, LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.kairos.LocalNetwork$activateSpec$2$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MergeKt.mergeLeft(CoalescingMutableEvents.this, localNetwork.endSignal);
            }
        })), this.this$0.scope);
        final Function1 function1 = this.$spec;
        final CoalescingMutableEvents coalescingMutableEvents2 = this.$stopEmitter;
        return BuildScopeKt.launchScope(buildScopeImpl, new Function1() { // from class: com.android.systemui.kairos.LocalNetwork$activateSpec$2$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                BuildScope buildScope = (BuildScope) obj2;
                BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                buildScopeImpl2.getClass();
                Function1.this.mo779invoke(buildScopeImpl2);
                return BuildScopeKt.launchEffect(buildScope, new LocalNetwork$activateSpec$2$1$1(coalescingMutableEvents2, null));
            }
        });
    }
}
