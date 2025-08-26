package com.android.systemui.unfold.data.repository;

import android.os.PowerManager;
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

/* loaded from: classes3.dex */
final class ScreenTimeoutPolicyRepository$screenTimeoutActive$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ScreenTimeoutPolicyRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenTimeoutPolicyRepository$screenTimeoutActive$1(ScreenTimeoutPolicyRepository screenTimeoutPolicyRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenTimeoutPolicyRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScreenTimeoutPolicyRepository$screenTimeoutActive$1 screenTimeoutPolicyRepository$screenTimeoutActive$1 = new ScreenTimeoutPolicyRepository$screenTimeoutActive$1(this.this$0, continuation);
        screenTimeoutPolicyRepository$screenTimeoutActive$1.L$0 = obj;
        return screenTimeoutPolicyRepository$screenTimeoutActive$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenTimeoutPolicyRepository$screenTimeoutActive$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.PowerManager$ScreenTimeoutPolicyListener, com.android.systemui.unfold.data.repository.ScreenTimeoutPolicyRepository$screenTimeoutActive$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new PowerManager.ScreenTimeoutPolicyListener() { // from class: com.android.systemui.unfold.data.repository.ScreenTimeoutPolicyRepository$screenTimeoutActive$1$listener$1
                public final void onScreenTimeoutPolicyChanged(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(i2 == 0));
                }
            };
            ScreenTimeoutPolicyRepository screenTimeoutPolicyRepository = this.this$0;
            screenTimeoutPolicyRepository.powerManager.addScreenTimeoutPolicyListener(0, screenTimeoutPolicyRepository.executor, r1);
            final ScreenTimeoutPolicyRepository screenTimeoutPolicyRepository2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.data.repository.ScreenTimeoutPolicyRepository$screenTimeoutActive$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    screenTimeoutPolicyRepository2.powerManager.removeScreenTimeoutPolicyListener(0, r1);
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
