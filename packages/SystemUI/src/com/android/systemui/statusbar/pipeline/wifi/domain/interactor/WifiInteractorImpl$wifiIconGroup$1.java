package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.statusbar.connectivity.WifiIcons;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.WifiSignalIconResource;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class WifiInteractorImpl$wifiIconGroup$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ WifiSignalIconResource $wifiSignalIconResource;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiInteractorImpl$wifiIconGroup$1(WifiSignalIconResource wifiSignalIconResource, Continuation continuation) {
        super(3, continuation);
        this.$wifiSignalIconResource = wifiSignalIconResource;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        WifiInteractorImpl$wifiIconGroup$1 wifiInteractorImpl$wifiIconGroup$1 = new WifiInteractorImpl$wifiIconGroup$1(this.$wifiSignalIconResource, (Continuation) obj3);
        wifiInteractorImpl$wifiIconGroup$1.L$0 = (WifiNetworkModel) obj;
        wifiInteractorImpl$wifiIconGroup$1.Z$0 = booleanValue;
        return wifiInteractorImpl$wifiIconGroup$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SignalIcon$IconGroup signalIcon$IconGroup;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$0;
        boolean z = this.Z$0;
        if (!(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
            return null;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("get wifiIconGroup ePDGConnected : ", "WifiInteractor", z);
        WifiSignalIconResource wifiSignalIconResource = this.$wifiSignalIconResource;
        wifiSignalIconResource.getClass();
        WifiNetworkModel.WifiNetworkType wifiNetworkType = WifiNetworkModel.WifiNetworkType.FIVEG;
        WifiNetworkModel.WifiNetworkType wifiNetworkType2 = ((WifiNetworkModel.Active) wifiNetworkModel).wifiNetworkType;
        CarrierInfraMediator carrierInfraMediator = wifiSignalIconResource.carrierInfraMediator;
        if (wifiNetworkType2 == wifiNetworkType) {
            SamsungWifiIcons.Companion.getClass();
            signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_5;
        } else if (wifiNetworkType2 == WifiNetworkModel.WifiNetworkType.SIXG) {
            SamsungWifiIcons.Companion.getClass();
            signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_6G;
        } else if (wifiNetworkType2 == WifiNetworkModel.WifiNetworkType.SIXGE) {
            SamsungWifiIcons.Companion.getClass();
            signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_6GE;
        } else if (wifiNetworkType2 == WifiNetworkModel.WifiNetworkType.SEVENG) {
            SamsungWifiIcons.Companion.getClass();
            signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_7G;
        } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_WIFI_CALLING_ICON, 0, new Object[0]) && z) {
            SamsungWifiIcons.Companion.getClass();
            signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_WIFI_CALLING;
        } else {
            signalIcon$IconGroup = WifiIcons.UNMERGED_WIFI;
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA, 0, new Object[0])) {
            signalIcon$IconGroup.activityIcons = null;
        }
        return signalIcon$IconGroup;
    }
}
