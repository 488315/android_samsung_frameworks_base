package com.android.systemui.statusbar.policy.data.repository;

import android.app.NotificationManager;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ZenModeRepositoryImpl$consolidatedNotificationPolicy$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ZenModeRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZenModeRepositoryImpl$consolidatedNotificationPolicy$1(ZenModeRepositoryImpl zenModeRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = zenModeRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ZenModeRepositoryImpl$consolidatedNotificationPolicy$1 zenModeRepositoryImpl$consolidatedNotificationPolicy$1 = new ZenModeRepositoryImpl$consolidatedNotificationPolicy$1(this.this$0, continuation);
        zenModeRepositoryImpl$consolidatedNotificationPolicy$1.L$0 = obj;
        return zenModeRepositoryImpl$consolidatedNotificationPolicy$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ZenModeRepositoryImpl$consolidatedNotificationPolicy$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.data.repository.ZenModeRepositoryImpl$consolidatedNotificationPolicy$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ZenModeController.Callback() { // from class: com.android.systemui.statusbar.policy.data.repository.ZenModeRepositoryImpl$consolidatedNotificationPolicy$1$callback$1
                @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
                public final void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(policy);
                }
            };
            ((ZenModeControllerImpl) this.this$0.zenModeController).addCallback(r1);
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(((ZenModeControllerImpl) this.this$0.zenModeController).mConsolidatedNotificationPolicy);
            final ZenModeRepositoryImpl zenModeRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.data.repository.ZenModeRepositoryImpl$consolidatedNotificationPolicy$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((ZenModeControllerImpl) ZenModeRepositoryImpl.this.zenModeController).removeCallback(r1);
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
