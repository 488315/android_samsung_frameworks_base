package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryKairosImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1 mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1 = new MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1(this.this$0, continuation);
        mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1.L$0 = obj;
        return mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.this$0;
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onSimStateChanged(int i2, int i3, int i4) {
                    MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl2 = mobileConnectionsRepositoryKairosImpl;
                    MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryKairosImpl2.logger;
                    mobileInputLogger.getClass();
                    LogBuffer.log$default(mobileInputLogger.buffer, "MobileInputLog", LogLevel.INFO, "onSimStateChanged");
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(mobileConnectionsRepositoryKairosImpl2.keyguardUpdateMonitor.isSimPinSecure()));
                }
            };
            this.this$0.keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
            MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0 mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0 = new MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0(this.this$0, keyguardUpdateMonitorCallback, 0);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, mobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
