package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.KeyguardUpdateMonitorCallback;
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
final class KeyguardRepositoryImpl$isEncryptedOrLockdown$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$isEncryptedOrLockdown$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$isEncryptedOrLockdown$1 keyguardRepositoryImpl$isEncryptedOrLockdown$1 = new KeyguardRepositoryImpl$isEncryptedOrLockdown$1(this.this$0, continuation);
        keyguardRepositoryImpl$isEncryptedOrLockdown$1.L$0 = obj;
        return keyguardRepositoryImpl$isEncryptedOrLockdown$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$isEncryptedOrLockdown$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isEncryptedOrLockdown$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onStrongAuthStateChanged(int i2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Integer valueOf = Integer.valueOf(i2);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "KeyguardRepositoryImpl", "strong auth state change");
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
            KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 keyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 = new KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0(5, this.this$0, keyguardUpdateMonitorCallback);
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
