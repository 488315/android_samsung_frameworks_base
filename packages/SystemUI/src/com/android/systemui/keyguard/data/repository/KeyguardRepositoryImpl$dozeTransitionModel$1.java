package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class KeyguardRepositoryImpl$dozeTransitionModel$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$dozeTransitionModel$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$dozeTransitionModel$1 keyguardRepositoryImpl$dozeTransitionModel$1 = new KeyguardRepositoryImpl$dozeTransitionModel$1(this.this$0, continuation);
        keyguardRepositoryImpl$dozeTransitionModel$1.L$0 = obj;
        return keyguardRepositoryImpl$dozeTransitionModel$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$dozeTransitionModel$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1 keyguardRepositoryImpl$dozeTransitionModel$1$callback$1 = new KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1(producerScope, this.this$0);
            DozeTransitionListener dozeTransitionListener = this.this$0.dozeTransitionListener;
            synchronized (dozeTransitionListener) {
                dozeTransitionListener.callbacks.add(keyguardRepositoryImpl$dozeTransitionModel$1$callback$1);
            }
            ChannelExt channelExt = ChannelExt.INSTANCE;
            KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            DozeStateModel dozeStateModelAccess$dozeMachineStateToModel = KeyguardRepositoryImpl.access$dozeMachineStateToModel(keyguardRepositoryImpl, keyguardRepositoryImpl.dozeTransitionListener.oldState);
            KeyguardRepositoryImpl keyguardRepositoryImpl2 = this.this$0;
            DozeTransitionModel dozeTransitionModel = new DozeTransitionModel(dozeStateModelAccess$dozeMachineStateToModel, KeyguardRepositoryImpl.access$dozeMachineStateToModel(keyguardRepositoryImpl2, keyguardRepositoryImpl2.dozeTransitionListener.newState));
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, dozeTransitionModel, "KeyguardRepositoryImpl", "initial doze transition model");
            KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 keyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 = new KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0(2, this.this$0, keyguardRepositoryImpl$dozeTransitionModel$1$callback$1);
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
