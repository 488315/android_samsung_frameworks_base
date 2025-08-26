package com.android.systemui.shade.data.repository;

import com.android.systemui.privacy.PrivacyConfig;
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
final class PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PrivacyChipRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1(PrivacyChipRepositoryImpl privacyChipRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = privacyChipRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1 privacyChipRepositoryImpl$isMicCameraIndicationEnabled$1 = new PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1(this.this$0, continuation);
        privacyChipRepositoryImpl$isMicCameraIndicationEnabled$1.L$0 = obj;
        return privacyChipRepositoryImpl$isMicCameraIndicationEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            PrivacyConfig.Callback callback = new PrivacyConfig.Callback() { // from class: com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1$callback$1
                @Override // com.android.systemui.privacy.PrivacyConfig.Callback
                public final void onFlagMicCameraChanged(boolean z) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            this.this$0.privacyConfig.addCallback(callback);
            PrivacyChipRepositoryImpl$privacyItems$1$$ExternalSyntheticLambda0 privacyChipRepositoryImpl$privacyItems$1$$ExternalSyntheticLambda0 = new PrivacyChipRepositoryImpl$privacyItems$1$$ExternalSyntheticLambda0(this.this$0, callback, 2);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, privacyChipRepositoryImpl$privacyItems$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
