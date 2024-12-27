package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
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

final class MobileConnectionsRepositoryImpl$isAnySimSecure$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    public MobileConnectionsRepositoryImpl$isAnySimSecure$1(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$isAnySimSecure$1 mobileConnectionsRepositoryImpl$isAnySimSecure$1 = new MobileConnectionsRepositoryImpl$isAnySimSecure$1(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$isAnySimSecure$1.L$0 = obj;
        return mobileConnectionsRepositoryImpl$isAnySimSecure$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$isAnySimSecure$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
            final ?? r1 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isAnySimSecure$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onSimStateChanged(int i2, int i3, int i4) {
                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = MobileConnectionsRepositoryImpl.this;
                    MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl2.logger;
                    mobileInputLogger.getClass();
                    mobileInputLogger.buffer.log("MobileInputLog", LogLevel.INFO, "onSimStateChanged", null);
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(mobileConnectionsRepositoryImpl2.keyguardUpdateMonitor.isSimPinSecure()));
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(r1);
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.FALSE);
            final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isAnySimSecure$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionsRepositoryImpl.this.keyguardUpdateMonitor.removeCallback(r1);
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
