package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StatusBarStateController $statusBarStateController;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1(StatusBarStateController statusBarStateController, Continuation continuation) {
        super(2, continuation);
        this.$statusBarStateController = statusBarStateController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1 keyguardRepositoryImpl$_preSceneLinearDozeAmount$1 = new KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1(this.$statusBarStateController, continuation);
        keyguardRepositoryImpl$_preSceneLinearDozeAmount$1.L$0 = obj;
        return keyguardRepositoryImpl$_preSceneLinearDozeAmount$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1$callback$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onDozeAmountChanged(float f, float f2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Float fValueOf = Float.valueOf(f);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope, fValueOf, "KeyguardRepositoryImpl", "updated dozeAmount");
                }
            };
            this.$statusBarStateController.addCallback(stateListener);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Float f = new Float(this.$statusBarStateController.getDozeAmount());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, f, "KeyguardRepositoryImpl", "initial dozeAmount");
            KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 keyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 = new KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0(1, this.$statusBarStateController, stateListener);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, keyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
