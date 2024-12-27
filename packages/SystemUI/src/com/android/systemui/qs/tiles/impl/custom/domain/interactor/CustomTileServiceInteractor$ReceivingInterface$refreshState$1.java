package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class CustomTileServiceInteractor$ReceivingInterface$refreshState$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomTileServiceInteractor.ReceivingInterface this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileServiceInteractor$ReceivingInterface$refreshState$1(CustomTileServiceInteractor.ReceivingInterface receivingInterface, Continuation continuation) {
        super(2, continuation);
        this.this$0 = receivingInterface;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileServiceInteractor$ReceivingInterface$refreshState$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileServiceInteractor$ReceivingInterface$refreshState$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl sharedFlowImpl = this.this$0.mutableRefreshEvents;
            Unit unit = Unit.INSTANCE;
            this.label = 1;
            if (sharedFlowImpl.emit(unit, this) == coroutineSingletons) {
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
