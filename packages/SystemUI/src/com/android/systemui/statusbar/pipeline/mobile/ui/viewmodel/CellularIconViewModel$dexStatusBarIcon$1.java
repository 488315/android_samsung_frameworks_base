package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModelKt;
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
/* loaded from: classes3.dex */
final class CellularIconViewModel$dexStatusBarIcon$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$dexStatusBarIcon$1(CellularIconViewModel cellularIconViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CellularIconViewModel$dexStatusBarIcon$1 cellularIconViewModel$dexStatusBarIcon$1 = new CellularIconViewModel$dexStatusBarIcon$1(this.this$0, continuation);
        cellularIconViewModel$dexStatusBarIcon$1.L$0 = obj;
        return cellularIconViewModel$dexStatusBarIcon$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CellularIconViewModel$dexStatusBarIcon$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final CellularIconViewModel cellularIconViewModel = this.this$0;
            final StatusBarSignalPolicy.DesktopCallback desktopCallback = new StatusBarSignalPolicy.DesktopCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$dexStatusBarIcon$1$callback$1
                @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.DesktopCallback
                public final void updateDesktopStatusBarIcons() {
                    CellularIconViewModel cellularIconViewModel2 = CellularIconViewModel.this;
                    CellularIconViewModel.access$sendDeXStatusBarIconModel(cellularIconViewModel2, (DeXStatusBarIconModel) cellularIconViewModel2.updateDeXStatusBarIconModel.$$delegate_0.getValue());
                }
            };
            this.this$0.desktopManager.setDesktopStatusBarIconCallback(desktopCallback);
            final CellularIconViewModel cellularIconViewModel2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$dexStatusBarIcon$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CellularIconViewModel.access$sendDeXStatusBarIconModel(CellularIconViewModel.this, DeXStatusBarIconModelKt.DEFAULT_DEX_STATUS_BAR_ICON_MODEL);
                    CellularIconViewModel.this.desktopManager.removeDesktopStatusBarIconCallback(desktopCallback);
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
