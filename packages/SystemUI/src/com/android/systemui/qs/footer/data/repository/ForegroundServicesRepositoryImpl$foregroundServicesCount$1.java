package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.qs.SecFgsManagerController;
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
/* loaded from: classes2.dex */
final class ForegroundServicesRepositoryImpl$foregroundServicesCount$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FgsManagerController $fgsManagerController;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForegroundServicesRepositoryImpl$foregroundServicesCount$1(FgsManagerController fgsManagerController, Continuation continuation) {
        super(2, continuation);
        this.$fgsManagerController = fgsManagerController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ForegroundServicesRepositoryImpl$foregroundServicesCount$1 foregroundServicesRepositoryImpl$foregroundServicesCount$1 = new ForegroundServicesRepositoryImpl$foregroundServicesCount$1(this.$fgsManagerController, continuation);
        foregroundServicesRepositoryImpl$foregroundServicesCount$1.L$0 = obj;
        return foregroundServicesRepositoryImpl$foregroundServicesCount$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ForegroundServicesRepositoryImpl$foregroundServicesCount$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$foregroundServicesCount$1$listener$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new FgsManagerController.OnNumberOfPackagesChangedListener() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$foregroundServicesCount$1$listener$1
                @Override // com.android.systemui.qs.FgsManagerController.OnNumberOfPackagesChangedListener
                public final void onNumberOfPackagesChanged(int i2) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, Integer.valueOf(i2), "ForegroundServicesRepositoryImpl");
                }
            };
            FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) this.$fgsManagerController;
            synchronized (fgsManagerControllerImpl.lock) {
                fgsManagerControllerImpl.onNumberOfPackagesChangedListeners.add(r1);
            }
            if (fgsManagerControllerImpl.secFgsManagerController != null) {
                SecFgsManagerController.log("addOnNumberOfPackagesChangedListener");
            }
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, Integer.valueOf(((FgsManagerControllerImpl) this.$fgsManagerController).getNumRunningPackages()), "ForegroundServicesRepositoryImpl");
            final FgsManagerController fgsManagerController = this.$fgsManagerController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$foregroundServicesCount$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    FgsManagerController fgsManagerController2 = FgsManagerController.this;
                    ForegroundServicesRepositoryImpl$foregroundServicesCount$1$listener$1 foregroundServicesRepositoryImpl$foregroundServicesCount$1$listener$1 = r1;
                    FgsManagerControllerImpl fgsManagerControllerImpl2 = (FgsManagerControllerImpl) fgsManagerController2;
                    if (fgsManagerControllerImpl2.secFgsManagerController != null) {
                        SecFgsManagerController.log("removeOnNumberOfPackagesChangedListener");
                    }
                    synchronized (fgsManagerControllerImpl2.lock) {
                        fgsManagerControllerImpl2.onNumberOfPackagesChangedListeners.remove(foregroundServicesRepositoryImpl$foregroundServicesCount$1$listener$1);
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
