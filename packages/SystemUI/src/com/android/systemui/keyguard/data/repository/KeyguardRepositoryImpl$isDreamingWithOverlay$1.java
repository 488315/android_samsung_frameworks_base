package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRepositoryImpl$isDreamingWithOverlay$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$isDreamingWithOverlay$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$isDreamingWithOverlay$1 keyguardRepositoryImpl$isDreamingWithOverlay$1 = new KeyguardRepositoryImpl$isDreamingWithOverlay$1(this.this$0, continuation);
        keyguardRepositoryImpl$isDreamingWithOverlay$1.L$0 = obj;
        return keyguardRepositoryImpl$isDreamingWithOverlay$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$isDreamingWithOverlay$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 = new KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1(producerScope);
            this.this$0.dreamOverlayCallbackController.callbacks.add(keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(this.this$0.dreamOverlayCallbackController.isDreaming);
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "KeyguardRepositoryImpl", "initial isDreamingWithOverlay");
            KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 keyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 = new KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0(4, this.this$0, keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1);
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
