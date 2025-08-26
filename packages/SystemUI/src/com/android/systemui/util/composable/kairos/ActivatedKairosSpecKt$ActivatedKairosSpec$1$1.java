package com.android.systemui.util.composable.kairos;

import androidx.compose.runtime.MutableState;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class ActivatedKairosSpecKt$ActivatedKairosSpec$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $buildSpec;
    final /* synthetic */ KairosNetwork $kairosNetwork;
    final /* synthetic */ MutableState<Object> $state$delegate;
    final /* synthetic */ Object $uninit;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivatedKairosSpecKt$ActivatedKairosSpec$1$1(KairosNetwork kairosNetwork, Function1 function1, MutableState<Object> mutableState, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$kairosNetwork = kairosNetwork;
        this.$buildSpec = function1;
        this.$state$delegate = mutableState;
        this.$uninit = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object invokeSuspend$lambda$0(Function1 function1, MutableState mutableState, Object obj, BuildScope buildScope) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        buildScopeImpl.getClass();
        return BuildScopeKt.launchEffect(buildScope, new ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1(function1.mo781invoke(buildScopeImpl), mutableState, obj, null));
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivatedKairosSpecKt$ActivatedKairosSpec$1$1(this.$kairosNetwork, this.$buildSpec, this.$state$delegate, this.$uninit, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KairosNetwork kairosNetwork = this.$kairosNetwork;
            final Function1 function1 = this.$buildSpec;
            final MutableState<Object> mutableState = this.$state$delegate;
            final Object obj2 = this.$uninit;
            Function1 function12 = new Function1() { // from class: com.android.systemui.util.composable.kairos.ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj3) {
                    return ActivatedKairosSpecKt$ActivatedKairosSpec$1$1.invokeSuspend$lambda$0(function1, mutableState, obj2, (BuildScope) obj3);
                }
            };
            this.label = 1;
            if (kairosNetwork.activateSpec(function12, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ActivatedKairosSpecKt$ActivatedKairosSpec$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
