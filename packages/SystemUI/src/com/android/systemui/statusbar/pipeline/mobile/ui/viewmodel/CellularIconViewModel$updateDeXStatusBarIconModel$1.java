package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

final class CellularIconViewModel$updateDeXStatusBarIconModel$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$updateDeXStatusBarIconModel$1(CellularIconViewModel cellularIconViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        CellularIconViewModel$updateDeXStatusBarIconModel$1 cellularIconViewModel$updateDeXStatusBarIconModel$1 = new CellularIconViewModel$updateDeXStatusBarIconModel$1(this.this$0, (Continuation) obj6);
        cellularIconViewModel$updateDeXStatusBarIconModel$1.Z$0 = booleanValue;
        cellularIconViewModel$updateDeXStatusBarIconModel$1.L$0 = (SignalIconModel) obj2;
        cellularIconViewModel$updateDeXStatusBarIconModel$1.L$1 = (Icon.Resource) obj3;
        cellularIconViewModel$updateDeXStatusBarIconModel$1.L$2 = (Icon.Resource) obj4;
        cellularIconViewModel$updateDeXStatusBarIconModel$1.L$3 = (Icon.Resource) obj5;
        return cellularIconViewModel$updateDeXStatusBarIconModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        SignalIconModel signalIconModel = (SignalIconModel) this.L$0;
        Icon.Resource resource = (Icon.Resource) this.L$1;
        Icon.Resource resource2 = (Icon.Resource) this.L$2;
        Icon.Resource resource3 = (Icon.Resource) this.L$3;
        CellularIconViewModel cellularIconViewModel = this.this$0;
        DeXStatusBarIconModel deXStatusBarIconModel = new DeXStatusBarIconModel(z, cellularIconViewModel.slotId, cellularIconViewModel.subscriptionId, signalIconModel instanceof SignalIconModel.Cellular ? ((SignalIconModel.Cellular) signalIconModel).iconId : 0, resource != null ? resource.res : 0, z, resource2 != null ? resource2.res : 0, resource3 != null ? resource3.res : 0);
        CellularIconViewModel.access$sendDeXStatusBarIconModel(this.this$0, deXStatusBarIconModel);
        return deXStatusBarIconModel;
    }
}
