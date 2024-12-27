package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class UdfpsOverlayInteractor$udfpsOverlayParams$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UdfpsOverlayInteractor this$0;

    public UdfpsOverlayInteractor$udfpsOverlayParams$1(UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsOverlayInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UdfpsOverlayInteractor$udfpsOverlayParams$1 udfpsOverlayInteractor$udfpsOverlayParams$1 = new UdfpsOverlayInteractor$udfpsOverlayParams$1(this.this$0, continuation);
        udfpsOverlayInteractor$udfpsOverlayParams$1.L$0 = obj;
        return udfpsOverlayInteractor$udfpsOverlayParams$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsOverlayInteractor$udfpsOverlayParams$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new AuthController.Callback() { // from class: com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor$udfpsOverlayParams$1$callback$1
                @Override // com.android.systemui.biometrics.AuthController.Callback
                public final void onUdfpsLocationChanged(UdfpsOverlayParams udfpsOverlayParams) {
                    ChannelExt.INSTANCE.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, udfpsOverlayParams, "UdfpsOverlayInteractor", "update udfpsOverlayParams");
                }
            };
            this.this$0.authController.addCallback(r1);
            final UdfpsOverlayInteractor udfpsOverlayInteractor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor$udfpsOverlayParams$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UdfpsOverlayInteractor.this.authController.removeCallback(r1);
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
