package com.android.systemui.communal.data.repository;

import android.app.UiModeManager;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CarProjectionRepositoryImpl$projectionActive$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CarProjectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarProjectionRepositoryImpl$projectionActive$1(CarProjectionRepositoryImpl carProjectionRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = carProjectionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CarProjectionRepositoryImpl$projectionActive$1 carProjectionRepositoryImpl$projectionActive$1 = new CarProjectionRepositoryImpl$projectionActive$1(this.this$0, continuation);
        carProjectionRepositoryImpl$projectionActive$1.L$0 = obj;
        return carProjectionRepositoryImpl$projectionActive$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CarProjectionRepositoryImpl$projectionActive$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.app.UiModeManager$OnProjectionStateChangedListener, com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$projectionActive$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new UiModeManager.OnProjectionStateChangedListener() { // from class: com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$projectionActive$1$listener$1
                public final void onProjectionStateChanged(int i2, Set set) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            CarProjectionRepositoryImpl carProjectionRepositoryImpl = this.this$0;
            carProjectionRepositoryImpl.uiModeManager.addOnProjectionStateChangedListener(1, ExecutorsKt.asExecutor(carProjectionRepositoryImpl.bgDispatcher), r1);
            final CarProjectionRepositoryImpl carProjectionRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$projectionActive$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CarProjectionRepositoryImpl.this.uiModeManager.removeOnProjectionStateChangedListener(r1);
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
