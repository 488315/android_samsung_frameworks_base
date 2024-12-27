package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.DeXStatusBarWifiIconModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import com.android.systemui.util.DesktopManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class WifiViewModel$updateDeXWifiIconModel$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DesktopManager $desktopManager;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiViewModel$updateDeXWifiIconModel$1(DesktopManager desktopManager, Continuation continuation) {
        super(3, continuation);
        this.$desktopManager = desktopManager;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiViewModel$updateDeXWifiIconModel$1 wifiViewModel$updateDeXWifiIconModel$1 = new WifiViewModel$updateDeXWifiIconModel$1(this.$desktopManager, (Continuation) obj3);
        wifiViewModel$updateDeXWifiIconModel$1.L$0 = (WifiIcon) obj;
        wifiViewModel$updateDeXWifiIconModel$1.L$1 = (Icon.Resource) obj2;
        return wifiViewModel$updateDeXWifiIconModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WifiIcon wifiIcon = (WifiIcon) this.L$0;
        Icon.Resource resource = (Icon.Resource) this.L$1;
        boolean z = wifiIcon instanceof WifiIcon.Visible;
        DeXStatusBarWifiIconModel deXStatusBarWifiIconModel = new DeXStatusBarWifiIconModel(z, z ? ((WifiIcon.Visible) wifiIcon).icon.res : 0, resource != null ? resource.res : 0);
        this.$desktopManager.setWifiIcon(deXStatusBarWifiIconModel.isVisible, deXStatusBarWifiIconModel.wifiId, deXStatusBarWifiIconModel.activityId);
        return deXStatusBarWifiIconModel;
    }
}
