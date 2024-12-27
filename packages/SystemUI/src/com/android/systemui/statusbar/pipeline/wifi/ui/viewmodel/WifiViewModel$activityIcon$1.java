package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class WifiViewModel$activityIcon$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public WifiViewModel$activityIcon$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        WifiViewModel$activityIcon$1 wifiViewModel$activityIcon$1 = new WifiViewModel$activityIcon$1((Continuation) obj4);
        wifiViewModel$activityIcon$1.L$0 = (DataActivityModel) obj;
        wifiViewModel$activityIcon$1.L$1 = (SignalIcon$IconGroup) obj2;
        wifiViewModel$activityIcon$1.L$2 = (WifiNetworkModel) obj3;
        return wifiViewModel$activityIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int[] iArr;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataActivityModel dataActivityModel = (DataActivityModel) this.L$0;
        SignalIcon$IconGroup signalIcon$IconGroup = (SignalIcon$IconGroup) this.L$1;
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$2;
        if (wifiNetworkModel instanceof WifiNetworkModel.Active) {
            WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
            if (((new Integer(active.receivedInetCondition).equals(new Integer(-1)) && active.isValidated) || new Integer(active.receivedInetCondition).equals(new Integer(1))) && signalIcon$IconGroup != null && (iArr = signalIcon$IconGroup.activityIcons) != null && dataActivityModel != null) {
                boolean z = dataActivityModel.hasActivityOut;
                boolean z2 = dataActivityModel.hasActivityIn;
                return new Icon.Resource((z2 && z) ? iArr[3] : z2 ? iArr[1] : z ? iArr[2] : iArr[0], null);
            }
        }
        return null;
    }
}
