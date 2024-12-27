package com.android.systemui.shade.data.repository;

import com.android.systemui.privacy.PrivacyItemController;
import java.util.List;
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

final class PrivacyChipRepositoryImpl$privacyItems$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PrivacyChipRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrivacyChipRepositoryImpl$privacyItems$1(PrivacyChipRepositoryImpl privacyChipRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = privacyChipRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PrivacyChipRepositoryImpl$privacyItems$1 privacyChipRepositoryImpl$privacyItems$1 = new PrivacyChipRepositoryImpl$privacyItems$1(this.this$0, continuation);
        privacyChipRepositoryImpl$privacyItems$1.L$0 = obj;
        return privacyChipRepositoryImpl$privacyItems$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PrivacyChipRepositoryImpl$privacyItems$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.privacy.PrivacyItemController$Callback, com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl$privacyItems$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new PrivacyItemController.Callback() { // from class: com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl$privacyItems$1$callback$1
                @Override // com.android.systemui.privacy.PrivacyItemController.Callback
                public final void onPrivacyItemsChanged(List list) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(list);
                }
            };
            this.this$0.privacyItemController.addCallback(r1);
            final PrivacyChipRepositoryImpl privacyChipRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl$privacyItems$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    PrivacyChipRepositoryImpl.this.privacyItemController.removeCallback(r1);
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
