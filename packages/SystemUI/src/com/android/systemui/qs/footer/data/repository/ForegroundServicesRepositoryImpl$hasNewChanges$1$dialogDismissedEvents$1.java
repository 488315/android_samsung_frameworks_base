package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FgsManagerController $fgsManagerController;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1(FgsManagerController fgsManagerController, Continuation continuation) {
        super(2, continuation);
        this.$fgsManagerController = fgsManagerController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 = new ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1(this.$fgsManagerController, continuation);
        foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1.L$0 = obj;
        return foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 = new ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1(producerScope);
            FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) this.$fgsManagerController;
            synchronized (fgsManagerControllerImpl.lock) {
                fgsManagerControllerImpl.onDialogDismissedListeners.add(foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1);
            }
            final FgsManagerController fgsManagerController = this.$fgsManagerController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    FgsManagerController fgsManagerController2 = FgsManagerController.this;
                    ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$12 = foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1;
                    FgsManagerControllerImpl fgsManagerControllerImpl2 = (FgsManagerControllerImpl) fgsManagerController2;
                    synchronized (fgsManagerControllerImpl2.lock) {
                        fgsManagerControllerImpl2.onDialogDismissedListeners.remove(foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$12);
                    }
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
