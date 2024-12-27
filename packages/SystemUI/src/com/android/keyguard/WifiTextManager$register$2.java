package com.android.keyguard;

import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class WifiTextManager$register$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $update;
    int label;
    final /* synthetic */ WifiTextManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiTextManager$register$2(WifiTextManager wifiTextManager, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiTextManager;
        this.$update = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiTextManager$register$2(this.this$0, this.$update, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiTextManager$register$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final WifiTextManager wifiTextManager = this.this$0;
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = ((WifiInteractorImpl) wifiTextManager.wifiInteractor).wifiNetwork;
            final Function2 function2 = this.$update;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.WifiTextManager$register$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean z = ((WifiNetworkModel) obj2) instanceof WifiNetworkModel.Active;
                    WifiTextManager wifiTextManager2 = WifiTextManager.this;
                    wifiTextManager2.connected = z;
                    function2.invoke(wifiTextManager2.ssid, Boolean.valueOf(z));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.collect(flowCollector, this) == coroutineSingletons) {
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
