package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
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
final class SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SensitiveNotificationProtectionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1(SensitiveNotificationProtectionInteractor sensitiveNotificationProtectionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sensitiveNotificationProtectionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1 sensitiveNotificationProtectionInteractor$isSensitiveStateActive$1 = new SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1(this.this$0, continuation);
        sensitiveNotificationProtectionInteractor$isSensitiveStateActive$1.L$0 = obj;
        return sensitiveNotificationProtectionInteractor$isSensitiveStateActive$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.domain.interactor.SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1$listener$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final SensitiveNotificationProtectionInteractor sensitiveNotificationProtectionInteractor = this.this$0;
            final ?? r1 = new Runnable() { // from class: com.android.systemui.statusbar.policy.domain.interactor.SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1$listener$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionInteractor.controller).isSensitiveStateActive()));
                }
            };
            ((SensitiveNotificationProtectionControllerImpl) this.this$0.controller).mListeners.addIfAbsent(r1);
            ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(((SensitiveNotificationProtectionControllerImpl) this.this$0.controller).isSensitiveStateActive()));
            final SensitiveNotificationProtectionInteractor sensitiveNotificationProtectionInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.domain.interactor.SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionInteractor2.controller).mListeners.remove(r1);
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
