package com.android.systemui.keyguard.bouncer.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.common.coroutine.ChannelExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$faceLockedOut$1", m277f = "BouncerMessageRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setShuttingDownAnimationSub}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BouncerMessageRepositoryImpl$faceLockedOut$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardUpdateMonitor $updateMonitor;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageRepositoryImpl$faceLockedOut$1(KeyguardUpdateMonitor keyguardUpdateMonitor, Continuation<? super BouncerMessageRepositoryImpl$faceLockedOut$1> continuation) {
        super(2, continuation);
        this.$updateMonitor = keyguardUpdateMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageRepositoryImpl$faceLockedOut$1 bouncerMessageRepositoryImpl$faceLockedOut$1 = new BouncerMessageRepositoryImpl$faceLockedOut$1(this.$updateMonitor, continuation);
        bouncerMessageRepositoryImpl$faceLockedOut$1.L$0 = obj;
        return bouncerMessageRepositoryImpl$faceLockedOut$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageRepositoryImpl$faceLockedOut$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$faceLockedOut$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardUpdateMonitor keyguardUpdateMonitor = this.$updateMonitor;
            final ?? r1 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$faceLockedOut$1$callback$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
                    if (biometricSourceType == BiometricSourceType.FACE) {
                        ChannelExt channelExt = ChannelExt.INSTANCE;
                        KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardUpdateMonitor;
                        keyguardUpdateMonitor2.getClass();
                        Boolean valueOf = Boolean.valueOf(keyguardUpdateMonitor2.mFaceLockedOutPermanent);
                        channelExt.getClass();
                        ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "BouncerDetailedMessageRepository", "face lock out state changed.");
                    }
                }
            };
            this.$updateMonitor.registerCallback(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.$updateMonitor;
            keyguardUpdateMonitor2.getClass();
            Boolean valueOf = Boolean.valueOf(keyguardUpdateMonitor2.mFaceLockedOutPermanent);
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "BouncerDetailedMessageRepository", "face lockout initial value");
            final KeyguardUpdateMonitor keyguardUpdateMonitor3 = this.$updateMonitor;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$faceLockedOut$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyguardUpdateMonitor.this.removeCallback(r1);
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
