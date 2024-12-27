package com.android.systemui.biometrics.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class PromptRepositoryImpl$isShowing$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PromptRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptRepositoryImpl$isShowing$1(PromptRepositoryImpl promptRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = promptRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PromptRepositoryImpl$isShowing$1 promptRepositoryImpl$isShowing$1 = new PromptRepositoryImpl$isShowing$1(this.this$0, continuation);
        promptRepositoryImpl$isShowing$1.L$0 = obj;
        return promptRepositoryImpl$isShowing$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PromptRepositoryImpl$isShowing$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.biometrics.AuthController$Callback, com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$isShowing$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new AuthController.Callback() { // from class: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$isShowing$1$callback$1
                @Override // com.android.systemui.biometrics.AuthController.Callback
                public final void onBiometricPromptDismissed() {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Boolean bool = Boolean.FALSE;
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, bool, "PromptRepositoryImpl", "unset isShowing");
                }

                @Override // com.android.systemui.biometrics.AuthController.Callback
                public final void onBiometricPromptShown() {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Boolean bool = Boolean.TRUE;
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, bool, "PromptRepositoryImpl", "set isShowing");
                }
            };
            this.this$0.authController.addCallback(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(this.this$0.authController.isShowing());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "PromptRepositoryImpl", "update isShowing");
            final PromptRepositoryImpl promptRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.biometrics.data.repository.PromptRepositoryImpl$isShowing$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    PromptRepositoryImpl.this.authController.removeCallback(r1);
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
