package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class KeyguardRepositoryImpl$linearDozeAmount$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StatusBarStateController $statusBarStateController;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$linearDozeAmount$1(StatusBarStateController statusBarStateController, Continuation continuation) {
        super(2, continuation);
        this.$statusBarStateController = statusBarStateController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$linearDozeAmount$1 keyguardRepositoryImpl$linearDozeAmount$1 = new KeyguardRepositoryImpl$linearDozeAmount$1(this.$statusBarStateController, continuation);
        keyguardRepositoryImpl$linearDozeAmount$1.L$0 = obj;
        return keyguardRepositoryImpl$linearDozeAmount$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$linearDozeAmount$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1$callback$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1$callback$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onDozeAmountChanged(float f, float f2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Float valueOf = Float.valueOf(f);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "KeyguardRepositoryImpl", "updated dozeAmount");
                }
            };
            this.$statusBarStateController.addCallback(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Float f = new Float(this.$statusBarStateController.getDozeAmount());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, f, "KeyguardRepositoryImpl", "initial dozeAmount");
            final StatusBarStateController statusBarStateController = this.$statusBarStateController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    StatusBarStateController.this.removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
