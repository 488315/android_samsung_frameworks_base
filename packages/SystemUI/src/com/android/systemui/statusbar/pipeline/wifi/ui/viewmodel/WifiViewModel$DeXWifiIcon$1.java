package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.DeXStatusBarWifiIconModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.DeXStatusBarWifiIconModelKt;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
final class WifiViewModel$DeXWifiIcon$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TaskbarIndicatorController $taskbarIndicatorController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WifiViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiViewModel$DeXWifiIcon$1(TaskbarIndicatorController taskbarIndicatorController, WifiViewModel wifiViewModel, Continuation continuation) {
        super(2, continuation);
        this.$taskbarIndicatorController = taskbarIndicatorController;
        this.this$0 = wifiViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiViewModel$DeXWifiIcon$1 wifiViewModel$DeXWifiIcon$1 = new WifiViewModel$DeXWifiIcon$1(this.$taskbarIndicatorController, this.this$0, continuation);
        wifiViewModel$DeXWifiIcon$1.L$0 = obj;
        return wifiViewModel$DeXWifiIcon$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiViewModel$DeXWifiIcon$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.phone.StatusBarSignalPolicy$DesktopCallback, com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$DeXWifiIcon$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final TaskbarIndicatorController taskbarIndicatorController = this.$taskbarIndicatorController;
            final WifiViewModel wifiViewModel = this.this$0;
            final ?? r1 = new StatusBarSignalPolicy.DesktopCallback() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$DeXWifiIcon$1$callback$1
                @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.DesktopCallback
                public final void updateDesktopStatusBarIcons() {
                    WifiViewModel wifiViewModel2 = wifiViewModel;
                    boolean z = ((DeXStatusBarWifiIconModel) wifiViewModel2.updateDeXWifiIconModel.$$delegate_0.getValue()).isVisible;
                    ReadonlyStateFlow readonlyStateFlow = wifiViewModel2.updateDeXWifiIconModel;
                    taskbarIndicatorController.setWifiIcon(z, ((DeXStatusBarWifiIconModel) readonlyStateFlow.$$delegate_0.getValue()).wifiId, ((DeXStatusBarWifiIconModel) readonlyStateFlow.$$delegate_0.getValue()).activityId);
                }
            };
            this.$taskbarIndicatorController.setDesktopStatusBarIconCallback(r1);
            final TaskbarIndicatorController taskbarIndicatorController2 = this.$taskbarIndicatorController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$DeXWifiIcon$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeXStatusBarWifiIconModel deXStatusBarWifiIconModel = DeXStatusBarWifiIconModelKt.DEFAULT_DEX_STATUS_BAR_WIFI_ICON_MODEL;
                    boolean z = deXStatusBarWifiIconModel.isVisible;
                    TaskbarIndicatorController taskbarIndicatorController3 = taskbarIndicatorController2;
                    taskbarIndicatorController3.setWifiIcon(z, deXStatusBarWifiIconModel.wifiId, deXStatusBarWifiIconModel.activityId);
                    WifiViewModel$DeXWifiIcon$1$callback$1 wifiViewModel$DeXWifiIcon$1$callback$1 = r1;
                    List list = taskbarIndicatorController3.mDesktopStatusBarIconCallback;
                    if (list != null) {
                        TypeIntrinsics.asMutableCollection(list).remove(wifiViewModel$DeXWifiIcon$1$callback$1);
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
