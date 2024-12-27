package com.android.systemui.dreams.homecontrols.domain.interactor;

import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class HomeControlsComponentInteractor$allAvailableServices$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HomeControlsComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsComponentInteractor$allAvailableServices$1(HomeControlsComponentInteractor homeControlsComponentInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsComponentInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HomeControlsComponentInteractor$allAvailableServices$1 homeControlsComponentInteractor$allAvailableServices$1 = new HomeControlsComponentInteractor$allAvailableServices$1(this.this$0, continuation);
        homeControlsComponentInteractor$allAvailableServices$1.L$0 = obj;
        return homeControlsComponentInteractor$allAvailableServices$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsComponentInteractor$allAvailableServices$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.management.ControlsListingController$ControlsListingCallback, com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$allAvailableServices$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$allAvailableServices$1$listener$1
                @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
                public final void onServicesUpdated(List list) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(list);
                }
            };
            ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) this.this$0.controlsListingController;
            controlsListingControllerImpl.getClass();
            controlsListingControllerImpl.addCallback((ControlsListingController.ControlsListingCallback) r1);
            final HomeControlsComponentInteractor homeControlsComponentInteractor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$allAvailableServices$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((ControlsListingControllerImpl) HomeControlsComponentInteractor.this.controlsListingController).removeCallback(r1);
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
