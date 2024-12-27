package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.StatusBarState;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardRepositoryImpl$statusBarState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StatusBarStateController $statusBarStateController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$statusBarState$1(StatusBarStateController statusBarStateController, KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$statusBarStateController = statusBarStateController;
        this.this$0 = keyguardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$statusBarState$1 keyguardRepositoryImpl$statusBarState$1 = new KeyguardRepositoryImpl$statusBarState$1(this.$statusBarStateController, this.this$0, continuation);
        keyguardRepositoryImpl$statusBarState$1.L$0 = obj;
        return keyguardRepositoryImpl$statusBarState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$statusBarState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1$callback$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final ?? r1 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1$callback$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onStateChanged(int i2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    int i3 = KeyguardRepositoryImpl.$r8$clinit;
                    keyguardRepositoryImpl.getClass();
                    StatusBarState statusBarStateIntToObject = KeyguardRepositoryImpl.statusBarStateIntToObject(i2);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, statusBarStateIntToObject, "KeyguardRepositoryImpl", "state");
                }
            };
            this.$statusBarStateController.addCallback(r1);
            final StatusBarStateController statusBarStateController = this.$statusBarStateController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1.1
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
