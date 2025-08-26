package com.android.systemui.util.composable.kairos;

import com.android.systemui.KairosActivatable;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.KairosNetwork;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class RememberKairosActivatableKt$rememberKairosActivatable$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KairosActivatable $instance;
    final /* synthetic */ KairosNetwork $kairosNetwork;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RememberKairosActivatableKt$rememberKairosActivatable$1$1(KairosNetwork kairosNetwork, KairosActivatable kairosActivatable, Continuation continuation) {
        super(2, continuation);
        this.$kairosNetwork = kairosNetwork;
        this.$instance = kairosActivatable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$1(KairosActivatable kairosActivatable, BuildScope buildScope) {
        kairosActivatable.activate(buildScope);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RememberKairosActivatableKt$rememberKairosActivatable$1$1(this.$kairosNetwork, this.$instance, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KairosNetwork kairosNetwork = this.$kairosNetwork;
            final KairosActivatable kairosActivatable = this.$instance;
            Function1 function1 = new Function1() { // from class: com.android.systemui.util.composable.kairos.RememberKairosActivatableKt$rememberKairosActivatable$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    return RememberKairosActivatableKt$rememberKairosActivatable$1$1.invokeSuspend$lambda$1(kairosActivatable, (BuildScope) obj2);
                }
            };
            this.label = 1;
            if (kairosNetwork.activateSpec(function1, this) == coroutineSingletons) {
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
        return ((RememberKairosActivatableKt$rememberKairosActivatable$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
